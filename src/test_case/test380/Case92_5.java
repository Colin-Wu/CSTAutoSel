package test_case.test380;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderAdminAction;
import action_lib.odr_mngt.OrderEntry1Action;
import action_lib.odr_mngt.OrderSummaryAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case92_5 {

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
		InputObj.put("caseid", "92_5");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
		if (retArr == null) {
			return retVal;
		}		
		if (retArr.size() > 0) {
/*			HashMap<String, String> Retobj = retArr.get(0);
			String inExpectedSendoutSN = Retobj.get("SN");*/
			int boxidx = 0;
			HashMap<String, String> Retobj = retArr.get(boxidx);
			String inExpectedSendoutSN = Retobj.get("SN");
/*        	while (!TestSetting.reslist.addSN("92_5",inExpectedSendoutSN)) {
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
					GotoOrderEntry1InputObj.put("Mode", "1");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > Cancel Child order.");					
					CommUtil.logger.info(" > Go to Child entry1.");
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
						CommUtil.logger.info("CancelOrderEntry1 - Success: Child order canceled.");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retstr);
						return retVal;	
					}
					
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					HashMap<String, String> SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", ParentOrdNum);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					String isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
					
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		OrderAdminAction orderAdminpage = new OrderAdminAction(webdriver);
					HashMap<String, String> GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", ParentOrdNum);
					HashMap<String, String> RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					String OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("AdminHold")) {
							CommUtil.logger.info("Parent Order wasn't cancelled. Status:"+OrdStatus);
						} else {
							CommUtil.logger.info("Parent Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
					GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "1");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > Edit parent again, turn to \"Active\"");					
			    	retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}					
				
					
					OrderSummaryAction OrderSummarypage = new OrderSummaryAction(webdriver);
		
					retObj = OrderSummarypage.GetOrderSummary();
		
					String Pstatus = retObj.get("Pstatus").toString();
					String C1Status = retObj.get("C1Status").toString();
		
					if (Pstatus.equals("Active")) {
						CommUtil.logger.info("Parent Order is Active.");
					} else {
						CommUtil.logger.info("Parent Order status incorrect. status:"+Pstatus);
						return retVal;	
					}					
					
					if (C1Status.equals("Cancel")) {
						CommUtil.logger.info("Child Order is canceled.");
					} else {
						CommUtil.logger.info("Child Order status incorrect. status:"+C1Status);
						return retVal;	
					}					
					
							        	
					GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "0");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > Cancel parent order.");					
					CommUtil.logger.info(" > Go to Parent entry1.");
			    	retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}					
				
					OrderEntry1page = new OrderEntry1Action(webdriver);
					CancelOrderEntry1InputObject = new HashMap<String, String>();
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
					
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", ParentOrdNum);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
					
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", ParentOrdNum);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("Cancel")) {
							CommUtil.logger.info("Parent Order cancel successfully.");
						} else {
							CommUtil.logger.info("Parent Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", C1Num);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					//isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
					orderAdminpage = new OrderAdminAction(webdriver);
					isFound = orderAdminpage.SearchOrder(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", C1Num);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("Cancel")) {
							retVal = "0";
							CommUtil.logger.info("Child Order is still cancelled");
						} else {
							CommUtil.logger.info("Child Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
				}
			}
			
		}		
		
		return retVal;
	}

}
