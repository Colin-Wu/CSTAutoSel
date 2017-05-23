package test_case.test381;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import config.TestSetting;
import script_lib.CommUtil;


public class Case89_1 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_DisposalOfMtrl;
		String vReqEmail = "11@22.com";
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String vShipVia = TestSetting.ShipVia;
		String vStockLocation = TestSetting.StockLocation;
		String vUserName = TestSetting.UserName;
		
		String retVal = "-1";
		
		CommonAction CA = new CommonAction(webdriver);
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
			
		CommUtil.logger.info(" > Create a standard PO.");	
		HashMap<String, String> CreateStdPO_CAInputObj = new HashMap<String, String>();
		CreateStdPO_CAInputObj.put("ProjectCode", vProject);
		CreateStdPO_CAInputObj.put("PartNum", vProjectPart1);		
		CreateStdPO_CAInputObj.put("Qty", "1");		
		CreateStdPO_CAInputObj.put("OrdNum", "");	
		CreateStdPO_CAInputObj.put("StockGroup", vStockGroup);		
		HashMap<String, String> retObj = CA.CreateStdPO_CA(CreateStdPO_CAInputObj);
		String retStr = retObj.get("RetVal").toString();
		String PONum = retObj.get("PONum").toString();	
		if (!retStr.equals("0")) {
			CommUtil.logger.info("CreateStdPO_CA - Failure. Code:"+retStr);
			return retVal;
		}
		
		
		CommUtil.logger.info(" > Receive a DOA part.");	
		HashMap<String, String> ReceiveToPutaway_CAInput=new HashMap<String, String>();
	      
		ReceiveToPutaway_CAInput.put("ProjectCode", vProject);
		ReceiveToPutaway_CAInput.put("PartNum", vProjectPart1);
		ReceiveToPutaway_CAInput.put("Qty", "1");
		ReceiveToPutaway_CAInput.put("PONum", PONum);
		ReceiveToPutaway_CAInput.put("Carrier",vShipVia);
		ReceiveToPutaway_CAInput.put("BoxCnt", "1");
		ReceiveToPutaway_CAInput.put("PalletCnt","1");
		ReceiveToPutaway_CAInput.put("SN", inExpectedReceiveSN);
		ReceiveToPutaway_CAInput.put("BoxID", "");
		ReceiveToPutaway_CAInput.put("Location", vStockLocation);
		ReceiveToPutaway_CAInput.put("Disposition", "Damaged");
		ReceiveToPutaway_CAInput.put("TrackNum", inExpectedReceiveSN);

		ArrayList<HashMap<String, String>> rstArr =  CA.ReceiveToPutaway_CA(ReceiveToPutaway_CAInput);
        if (rstArr == null) {
			CommUtil.logger.info("ReceiveToPutaway_CA - Failure. ");
			return retVal;		        	
        }
		retObj = new HashMap<String, String>(); 
		retObj = rstArr.get(0);
		String PartStatus = retObj.get("PartStatus");
		String BoxID = retObj.get("BoxID");
		//lock the boxid
		TestSetting.reslist.addBoxid("89_1", BoxID);
		if (PartStatus.equals("DOA")) {
			CommUtil.logger.info("DOA received. - Success.");
		} else {
			CommUtil.logger.info("ReceiveToPutaway_CA - Failure. Code:"+PartStatus);
			return retVal;	
		}	
		
		String inExpectedSendoutSN = inExpectedReceiveSN;
		
		CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
		
		retObj = null;
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
					
		return retVal;
	}
}
