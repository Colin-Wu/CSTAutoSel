package test_case.test381;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case89_2 {

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
		String vShipVia = TestSetting.ShipVia;
		String vStockLocation = TestSetting.StockLocation;
		String vReqEmail = "11@22.com";
		
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
			CreateFROrders_CAInputObj.put("ReqEmail", vReqEmail);
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
					String C1PO = retObj.get("C1PO").toString();
					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum+",Child1 order:"+C1Num);

					CommUtil.logger.info(" > Receive a DEFECTIVE part.");	
					HashMap<String, String> ReceiveToPutaway_CAInput=new HashMap<String, String>();
				      
					ReceiveToPutaway_CAInput.put("ProjectCode", vProject);
					ReceiveToPutaway_CAInput.put("PartNum", vProjectPart1);
					ReceiveToPutaway_CAInput.put("Qty", "1");
					ReceiveToPutaway_CAInput.put("PONum", C1PO);
					ReceiveToPutaway_CAInput.put("Carrier",vShipVia);
					ReceiveToPutaway_CAInput.put("BoxCnt", "1");
					ReceiveToPutaway_CAInput.put("PalletCnt","1");
					ReceiveToPutaway_CAInput.put("SN", inExpectedReceiveSN);
					ReceiveToPutaway_CAInput.put("BoxID", "");
					ReceiveToPutaway_CAInput.put("Location", vStockLocation);
					ReceiveToPutaway_CAInput.put("Disposition", "Good");
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
					TestSetting.reslist.addBoxid("89_2", BoxID);
					
					if (PartStatus.equals("DEFECTIVE")) {
						CommUtil.logger.info("DEFECTIVE received. - Success.");
					} else {
						CommUtil.logger.info("ReceiveToPutaway_CA - Failure. Code:"+PartStatus);
						return retVal;	
					}	
					
					String inExpectedSendoutSN = inExpectedReceiveSN;
					
					CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
					
					retObj = null;
					vOrdAttr = TestSetting.OrdAttr_DisposalOfMtrl;
					HashMap<String, Serializable> CreateDisposalOrders_CAInputObj = new HashMap<String, Serializable>();
					CreateDisposalOrders_CAInputObj.put("Customer", vCustomer);
					CreateDisposalOrders_CAInputObj.put("ProjectCode", vProject);
					CreateDisposalOrders_CAInputObj.put("Site", vSite);
					CreateDisposalOrders_CAInputObj.put("Attribute", vOrdAttr);
					CreateDisposalOrders_CAInputObj.put("TemplateName", "");
					CreateDisposalOrders_CAInputObj.put("ReqEmail", vReqEmail);
					CreateDisposalOrders_CAInputObj.put("ProdLine", "");
					
					PartAddMap = new HashMap<String, String>();
					PartAddMap.put("SearchPartNum", vProjectPart1);
					PartAddMap.put("SearchBOM", "");
					PartAddMap.put("SearchSN", inExpectedSendoutSN);
					PartAddMap.put("StockGroup", vStockGroup);
					PartAddMap.put("IsConfig", "0");
					PartAddMap.put("ReqQty", "");
					
					InputArr = new ArrayList<HashMap<String, String>>();
					InputArr.add(PartAddMap);
					
					CreateDisposalOrders_CAInputObj.put("ExpectedSendoutPart", InputArr);

					CommUtil.logger.info(" > Create Disposal order.");
					retObj = CA.CreateDisposalOrders_CA(CreateDisposalOrders_CAInputObj);

					if (retObj != null) {
						
						TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
						
						if (TotalOrderCnt.equals("1")) {

							ParentOrdNum = retObj.get("ParentOrdNum").toString();
							CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum);
							
							HashMap<String, Serializable> InputObject = new HashMap<String, Serializable>();
							InputObject.put("ProjectCode", vProject);					
							InputObject.put("OrdNum", ParentOrdNum);					
							InputObject.put("UserName", vUserName);	
							InputObject.put("QAMode", "1");	
							
							InputObject.put("caseid", "89_2");	
									
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
			}
			
		}		
		
		return retVal;
	}

}
