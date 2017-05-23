package test_case.test381;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case89_3 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_DisposalOfMtrl;
		String vReqEmail = "11@22.com";
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String vUserName = TestSetting.UserName;
		
		String retVal = "-1";
		
		CommUtil.logger.info(" > GetAvailablePart for Disposal order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		InputObj.put("caseid", "89_3");
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
/*        	while (!TestSetting.reslist.addSN("89_3",inExpectedSendoutSN)) {
        		boxidx++;
        		Retobj = retArr.get(boxidx);
        		inExpectedSendoutSN = Retobj.get("SN");
			}*/
  
			String BoxID = Retobj.get("BoxID");
			CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
			
			HashMap<String, String> retObj = null;
			HashMap<String, Serializable> CreateDisposalOrders_CAInputObj = new HashMap<String, Serializable>();
			CreateDisposalOrders_CAInputObj.put("Customer", vCustomer);
			CreateDisposalOrders_CAInputObj.put("ProjectCode", vProject);
			CreateDisposalOrders_CAInputObj.put("Site", vSite);
			CreateDisposalOrders_CAInputObj.put("Attribute", vOrdAttr);
			CreateDisposalOrders_CAInputObj.put("TemplateName", "");
			CreateDisposalOrders_CAInputObj.put("ReqEmail", vReqEmail);
			CreateDisposalOrders_CAInputObj.put("ProdLine", "");
			
			HashMap<String, String> PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", inExpectedSendoutSN);
			PartAddMap.put("StockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "0");
			PartAddMap.put("ReqQty", "");
			
			ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateDisposalOrders_CAInputObj.put("ExpectedSendoutPart", InputArr);
	
			CommUtil.logger.info(" > Create Disposal order.");
			retObj = CA.CreateDisposalOrders_CA(CreateDisposalOrders_CAInputObj);
	
			if (retObj != null) {
				
				String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
				
				if (TotalOrderCnt.equals("1")) {
	
					String ParentOrdNum = retObj.get("ParentOrdNum").toString();
					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum);
					
					HashMap<String, Serializable> InputObject = new HashMap<String, Serializable>();
					InputObject.put("ProjectCode", vProject);					
					InputObject.put("OrdNum", ParentOrdNum);					
					InputObject.put("UserName", vUserName);	
					InputObject.put("QAMode", "1");		
							
					HashMap<String, String> boxMap = new HashMap<String, String>();
					boxMap.put("Boxid", BoxID);
					boxMap.put("SN", inExpectedSendoutSN);
					
					ArrayList<HashMap<String, String>> boxArr = new ArrayList<HashMap<String, String>>();
					boxArr.add(boxMap);				
					InputObject.put("Boxids", boxArr);
	
					HashMap<String, String> trackMap = new HashMap<String, String>();
					trackMap.put("isFedex", "0");
					trackMap.put("TrackingNum", ParentOrdNum);
					
					ArrayList<HashMap<String, String>> TrackInputArr = new ArrayList<HashMap<String, String>>();
					TrackInputArr.add(trackMap);
					InputObject.put("TrackArr", TrackInputArr);
					CommUtil.logger.info(" > Complete Disposal order.");
					retVal = CA.CompleteDeploymentOrder_CA(InputObject);
					if(retVal.equals("0")) {
						CommUtil.logger.info("CompleteDeploymentOrder_CA - Success.");
					} else {
						CommUtil.logger.info("CompleteDeploymentOrder_CA - Failure. Code:"+retVal);
						return retVal;	
					}
			
				}
			}
		}
					
		return retVal;
	}

}
