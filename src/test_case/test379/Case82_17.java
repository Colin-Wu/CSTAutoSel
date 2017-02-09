package test_case.test379;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderEntry1Action;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_17 {

	public static String run (WebDriver webdriver) throws Exception {
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vUserName = TestSetting.UserName;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_NoRepair;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
		
		CommUtil.logger.info(" > GetAvailablePart for FR order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
			
		if (retArr.size() > 0) {
			//HashMap<String, String> Retobj = retArr.get(0);
			//String inExpectedSendoutSN = Retobj.get("SN");
			
			//CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
			
			HashMap<String, String> retObj = null;
			HashMap<String, Serializable> CreateFROrders_CAInputObj = new HashMap<String, Serializable>();
			CreateFROrders_CAInputObj.put("Customer", vCustomer);
			CreateFROrders_CAInputObj.put("ProjectCode", vProject);
			CreateFROrders_CAInputObj.put("Site", vSite);
			CreateFROrders_CAInputObj.put("Attribute", vOrdAttr);
			CreateFROrders_CAInputObj.put("TemplateName", "");
			CreateFROrders_CAInputObj.put("ReqEmail", "11@22.com");
			CreateFROrders_CAInputObj.put("ProdLine", "");
			
			HashMap<String, String> PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", "");
			PartAddMap.put("StockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "0");
			PartAddMap.put("ReqQty", "");
			
			ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateFROrders_CAInputObj.put("ExpectedSendoutPart", InputArr);

			PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", "");
			PartAddMap.put("ExpectedSN", inExpectedReceiveSN);
			PartAddMap.put("ReceiveStockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "");
			
			InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateFROrders_CAInputObj.put("ExpectedReceivePart", InputArr);

			CommUtil.logger.info(" > Create FR order.");
			retObj = CA.CreateFROrders_CA(CreateFROrders_CAInputObj);

			if (retObj != null) {
				
				String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
				
				if (TotalOrderCnt.equals("2")) {

					String ParentOrdNum = retObj.get("ParentOrdNum").toString();
					String C1Num = retObj.get("C1Num").toString();
					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum+",Child1 order:"+C1Num);

					HashMap<String, Serializable> InputObject = new HashMap<String, Serializable>();
					InputObject.put("ProjectCode", vProject);					
					InputObject.put("OrdNum", ParentOrdNum);					
					InputObject.put("UserName", vUserName);	
					InputObject.put("QAMode", "");		
					InputObject.put("caseid", "82_17");		
							
					ArrayList<HashMap<String, String>> boxArr = new ArrayList<HashMap<String, String>>();				
					InputObject.put("Boxids", boxArr);

					HashMap<String, String> trackMap = new HashMap<String, String>();
					trackMap.put("isFedex", "0");
					trackMap.put("TrackingNum", ParentOrdNum);
					
					ArrayList<HashMap<String, String>> TrackInputArr = new ArrayList<HashMap<String, String>>();
					TrackInputArr.add(trackMap);
					InputObject.put("TrackArr", TrackInputArr);
					CommUtil.logger.info(" > Complete parent order.");
					retVal = CA.CompleteDeploymentOrder_CA(InputObject);
					if(retVal.equals("0")) {
						CommUtil.logger.info("CompleteDeploymentOrder_CA - Success.");
					} else {
						CommUtil.logger.info("CompleteDeploymentOrder_CA - Failure. Code:"+retVal);
						return retVal;	
					}
					
					HashMap<String, String> GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "0");
					GotoOrderEntry1InputObj.put("OrdNum", C1Num);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > parent order cancel button invisible, child1 visible.");					
					CommUtil.logger.info(" > Go to Parent entry1.");
			    	String retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}					
				
					OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
					HashMap<String, String> CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "0");
					//CancelOrderEntry1InputObject.put("HasCommitedPart", "");
					
					CommUtil.logger.info("> CancelOrderEntry1");
					retstr = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retstr.equals("1")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success: Parent cancel button invisible.");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retstr);
						return retVal;	
					}
					
					GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "3");
					GotoOrderEntry1InputObj.put("OrdNum", C1Num);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");		
					CommUtil.logger.info(" > Go to Child1 entry1.");
			    	retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}
					
					OrderEntry1page = new OrderEntry1Action(webdriver);
					CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "0");
					//CancelOrderEntry1InputObject.put("HasCommitedPart", "");
					
					CommUtil.logger.info("> CancelOrderEntry1");
					retVal = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retVal.equals("0")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success: Child1 cancel button visible.");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retVal);
						return retVal;	
					}

				}
			}
			
		}		
		
		return retVal;
	}

}
