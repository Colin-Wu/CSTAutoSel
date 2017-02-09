package test_case.test383;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderAdminAction;
import action_lib.odr_mngt.OrderEntry1Action;
import action_lib.odr_mngt.OrderEntry2Action;
import config.TestSetting;
import script_lib.CommUtil;

public class Case97_1 {
	public static String run (WebDriver webdriver) throws Exception {
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_NoRepair;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
		
		CommUtil.logger.info(" > GetAvailablePart for EEX order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		InputObj.put("caseid", "97_1");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
			
		if (retArr.size() > 0) {
/*			HashMap<String, String> Retobj = retArr.get(0);
			String inExpectedSendoutSN = Retobj.get("SN");*/
			
			int boxidx = 0;
			HashMap<String, String> Retobj = retArr.get(boxidx);
			String inExpectedSendoutSN = Retobj.get("SN");
/*        	while (!TestSetting.reslist.addSN("97_1",inExpectedSendoutSN)) {
        		boxidx++;
        		Retobj = retArr.get(boxidx);
        		inExpectedSendoutSN = Retobj.get("SN");
			}*/
			
			CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
			
			HashMap<String, String> retObj = null;
			HashMap<String, Serializable> CreateEEXOrders_CAInputObj = new HashMap<String, Serializable>();
			CreateEEXOrders_CAInputObj.put("Customer", vCustomer);
			CreateEEXOrders_CAInputObj.put("ProjectCode", vProject);
			CreateEEXOrders_CAInputObj.put("Site", vSite);
			CreateEEXOrders_CAInputObj.put("Attribute", vOrdAttr);
			CreateEEXOrders_CAInputObj.put("TemplateName", "");
			CreateEEXOrders_CAInputObj.put("ReqEmail", "11@22.com");
			CreateEEXOrders_CAInputObj.put("ProdLine", "");
			
			HashMap<String, String> PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", inExpectedSendoutSN);
			PartAddMap.put("StockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "0");
			PartAddMap.put("ReqQty", "");
			
			ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateEEXOrders_CAInputObj.put("ExpectedSendoutPart", InputArr);

			PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", "");
			PartAddMap.put("ExpectedSN", inExpectedReceiveSN);
			PartAddMap.put("ReceiveStockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "");
			
			InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateEEXOrders_CAInputObj.put("ExpectedReceivePart", InputArr);

			CommUtil.logger.info(" > Create EEX order.");
			retObj = CA.CreateEEXOrders_CA(CreateEEXOrders_CAInputObj);

			if (retObj != null) {
				
				String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
				
				if (TotalOrderCnt.equals("2")) {

					String ParentOrdNum = retObj.get("ParentOrdNum").toString();
					String C1Num = retObj.get("C1Num").toString();

					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum+",Child1 order:"+C1Num);

					HashMap<String, String> GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "0");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > Cancel parent order.");					
					CommUtil.logger.info(" > Go to Parent entry1.");
			    	String retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}					
				
					OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
					HashMap<String, String> CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "1");
					//CancelOrderEntry1InputObject.put("HasCommitedPart", "");
					
					CommUtil.logger.info("> CancelOrderEntry1");
					retstr = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retstr.equals("0")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success: Parent order canceled.");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retstr);
						return retVal;	
					}
					
					
					CommUtil.logger.info("> Search parent order.");
					HashMap<String, String> SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", ParentOrdNum);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					//isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
					OrderAdminAction orderAdminpage = new OrderAdminAction(webdriver);
					String isFound = orderAdminpage.SearchOrder(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
					
		        	CommUtil.logger.info("> ViewOrder");
					HashMap<String, String> ViewOrderInputObj = new HashMap<String, String>();
					ViewOrderInputObj.put("OrdNum", ParentOrdNum);
		    		retstr = orderAdminpage.ViewOrder(ViewOrderInputObj);
		        	if(!retstr.equals("0")) {
		        		CommUtil.logger.info(">ViewOrder Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info("> Move to entry2.");

		    		OrderEntry1page = new OrderEntry1Action(webdriver);
		    		HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
		    		CreateOrderEntry1InputObject.put("Mode", "1");
		    		CreateOrderEntry1InputObject.put("Customer", "");
		    		CreateOrderEntry1InputObject.put("ProjectCode", "");
		    		CreateOrderEntry1InputObject.put("Site", "");
		    		CreateOrderEntry1InputObject.put("OrdType", "");
		    		CreateOrderEntry1InputObject.put("Attribute", "");
		    		CreateOrderEntry1InputObject.put("TemplateName", "");
		    		CreateOrderEntry1InputObject.put("ReqEmail", "");
		    		CreateOrderEntry1InputObject.put("PO", "");
		    		CreateOrderEntry1InputObject.put("POLink", "1");
		    		CreateOrderEntry1InputObject.put("ProdLine", "");

		    		CommUtil.logger.info("> CreateOrderEntry1");
		    		retstr = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		    		if(retstr.equals("0")) {
		    			CommUtil.logger.info("CreateOrderEntry1 - Success");
		    		} else {
		    			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retstr);
		    			return retVal;
		    		}
					
		    		OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
		    		CommUtil.logger.info("> CheckEntry2PartExist");
		    		retstr = OrderEntry2page.CheckEntry2PartExist();
		    		if(retstr.equals("0")) {
		    			CommUtil.logger.info("CheckEntry2PartExist - Success");
		    		} else {
		    			CommUtil.logger.info("CheckEntry2PartExist - Failure. Code:"+retstr);
		    			return retVal;
		    		}		    		
		    		
					CommUtil.logger.info("> Search child order.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", C1Num);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}    		
		    		
		        	CommUtil.logger.info("> ViewOrder");
					ViewOrderInputObj = new HashMap<String, String>();
					ViewOrderInputObj.put("OrdNum", C1Num);
		    		retstr = orderAdminpage.ViewOrder(ViewOrderInputObj);
		        	if(!retstr.equals("0")) {
		        		CommUtil.logger.info(">ViewOrder Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info("> Move to entry2.");

		    		OrderEntry1page = new OrderEntry1Action(webdriver);
		    		CreateOrderEntry1InputObject = new HashMap<String, String>();
		    		CreateOrderEntry1InputObject.put("Mode", "1");
		    		CreateOrderEntry1InputObject.put("Customer", "");
		    		CreateOrderEntry1InputObject.put("ProjectCode", "");
		    		CreateOrderEntry1InputObject.put("Site", "");
		    		CreateOrderEntry1InputObject.put("OrdType", "");
		    		CreateOrderEntry1InputObject.put("Attribute", "");
		    		CreateOrderEntry1InputObject.put("TemplateName", "");
		    		CreateOrderEntry1InputObject.put("ReqEmail", "");
		    		CreateOrderEntry1InputObject.put("PO", "");
		    		CreateOrderEntry1InputObject.put("POLink", "1");
		    		CreateOrderEntry1InputObject.put("ProdLine", "");

		    		CommUtil.logger.info("> CreateOrderEntry1");
		    		retstr = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		    		if(retstr.equals("0")) {
		    			CommUtil.logger.info("CreateOrderEntry1 - Success");
		    		} else {
		    			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retstr);
		    			return retVal;
		    		}
					
		    		OrderEntry2page = new OrderEntry2Action(webdriver);
		    		CommUtil.logger.info("> CheckEntry2PartExist");
		    		retVal = OrderEntry2page.CheckEntry2PartExist();
		    		if(retVal.equals("0")) {
		    			CommUtil.logger.info("CheckEntry2PartExist - Success");
		    		} else {
		    			CommUtil.logger.info("CheckEntry2PartExist - Failure. Code:"+retVal);
		    			return retVal;
		    		}		 		    		
		    		
				}
			}
			
		}		
		
		return retVal;
	}

}
