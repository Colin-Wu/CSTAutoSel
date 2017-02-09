package test_case.test380;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderAdminAction;
import action_lib.odr_mngt.OrderEntry1Action;
import config.TestSetting;
import script_lib.CommUtil;

public class Case92_1 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_RepairStock;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
			

		CommonAction CA = new CommonAction(webdriver);
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
		CommUtil.logger.info(" > Expected SN:" + inExpectedReceiveSN);	
		
		HashMap<String, String> retObj = null;
		HashMap<String, Serializable> CreateRepairOrders_CAInputObj = new HashMap<String, Serializable>();
		CreateRepairOrders_CAInputObj.put("Customer", vCustomer);
		CreateRepairOrders_CAInputObj.put("ProjectCode", vProject);
		CreateRepairOrders_CAInputObj.put("Site", vSite);
		CreateRepairOrders_CAInputObj.put("Attribute", vOrdAttr);
		CreateRepairOrders_CAInputObj.put("TemplateName", "");
		CreateRepairOrders_CAInputObj.put("ReqEmail", "11@22.com");
		CreateRepairOrders_CAInputObj.put("ProdLine", "");
		
		HashMap<String, String> PartAddMap = new HashMap<String, String>();
		PartAddMap.put("SearchPartNum", vProjectPart1);
		PartAddMap.put("SearchBOM", "");
		PartAddMap.put("SearchSN", "");
		PartAddMap.put("ExpectedSN", inExpectedReceiveSN);
		PartAddMap.put("ReceiveStockGroup", vStockGroup);
		PartAddMap.put("IsConfig", "");
		
		ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
		InputArr.add(PartAddMap);
		
		CreateRepairOrders_CAInputObj.put("ExpectedPart", InputArr);
		
		CommUtil.logger.info(" > Create Repair order.");
		retObj = CA.CreateRepairOrders_CA(CreateRepairOrders_CAInputObj);
		
		if (retObj != null) {
			
			String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
			
			if (TotalOrderCnt.equals("1")) {

				String ParentOrdNum = retObj.get("ParentOrdNum").toString();

				CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum);
							
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
					
					if (OrdStatus.equals("Cancel")) {
						retVal = "0";
						CommUtil.logger.info("Parent Order cancel successfully.");
					} else {
						CommUtil.logger.info("Parent Order status incorrect. status:"+OrdStatus);
						return retVal;	
					}
					
				} else {
					CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
					return retVal;	
				}	
	        	
	        	
			} else {
				retVal = "-1";
				CommUtil.logger.info(" > CreateRepairOrders_CA return order count does not equal 1. returned cnt:" + TotalOrderCnt);
				return retVal;						
			}
			
			
			
		} else {
			retVal = "-1";
			CommUtil.logger.info(" > CreateRepairOrders_CA return null");
			return retVal;	
		}
			
				
		return retVal;
	}

}
