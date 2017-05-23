package test_case.test381;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.inv_mngt.InventoryLookupAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case89_4 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String ret = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vUserName = TestSetting.UserName;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_RepairStock;
		String vShipVia = TestSetting.ShipVia;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vQty = "1";
		String vStockGroup = TestSetting.StockGroup;
		String vReqEmail = "11@22.com";
		
		
		String vBoxid = "";
			

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
		CreateRepairOrders_CAInputObj.put("ReqEmail", vReqEmail);
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
				String ParentPO = retObj.get("ParentPO").toString();

				CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum);
							
				
				HashMap<String, Serializable> InputObject = new HashMap<String, Serializable>();
				InputObject.put("PickMode", "1");					
				InputObject.put("ShipMode", "1");								
				
				InputObject.put("ProjectCode", vProject);					
				InputObject.put("OrdNum", ParentOrdNum);									
				InputObject.put("PONum", ParentPO);					
				InputObject.put("PartNum", vProjectPart1);					
				InputObject.put("Carrier", vShipVia);					
				InputObject.put("SN", inExpectedReceiveSN);	
				InputObject.put("TrackNum", ParentPO);

				InputObject.put("BoxID", "");	
				
				CommUtil.logger.info(" > Complete Parent order.");
				CommUtil.logger.info(" > CompleteStandardRepairOrder_CA"+InputObject);
				String retVal = CA.CompleteStandardRepairOrder_CA(InputObject);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CompleteStandardRepairOrder_CA - Success.");
				} else {
					CommUtil.logger.info("CompleteStandardRepairOrder_CA - Failure. Code:"+retVal);
					return ret;	
				}
				
				CommUtil.logger.info("> check status is refurb.");
				CommUtil.logger.info("> MenuInventory.");
				CST_MainAction mainpage= new CST_MainAction(webdriver);
				mainpage.MenuInventory();
				
				CommUtil.logger.info("> GotoInventoryLookup.");
				InvIndexAction invidxpage = new InvIndexAction(webdriver);
				invidxpage.GotoInventoryLookup();

				CommUtil.logger.info("> SearchInventory");
				InventoryLookupAction InvLookup = new InventoryLookupAction(webdriver);
				HashMap<String, String> InventoryLookupObj = new HashMap<String, String>();
				InventoryLookupObj.put("PartNum",vProjectPart1);
				InventoryLookupObj.put("SearchSN",inExpectedReceiveSN);
				InventoryLookupObj.put("SearchPONum","");
				InventoryLookupObj.put("ProjectCode",vProject);
				InventoryLookupObj.put("SearchStatus","");
				InventoryLookupObj.put("PalletID","");
				InventoryLookupObj.put("StockGroup","");
				InventoryLookupObj.put("Qty", vQty);    
				InventoryLookupObj.put("SearchBoxid","");

				HashMap<Object, Object> OutPutInventoryObj=InvLookup.SearchInventory(InventoryLookupObj);
				String outIsFound = OutPutInventoryObj.get("IsFound").toString();
				
				if (outIsFound.equals("1")) {
					CommUtil.logger.info("> SearchInventory Item found.");
					@SuppressWarnings("unchecked")
					ArrayList<HashMap<String, String>> rstArr = (ArrayList<HashMap<String, String>>) OutPutInventoryObj.get("RstArr");
					retObj = new HashMap<String, String>(); 
					retObj = rstArr.get(0);
					vBoxid = retObj.get("BoxID");
					//lock the boxid
					TestSetting.reslist.addBoxid("89_4", vBoxid);
					String PartStatus = retObj.get("PartStatus");
					if (PartStatus.equals("REFURB")) {
						CommUtil.logger.info("REFURB received. - Success.");
					} else {
						CommUtil.logger.info("SearchInventory - Failure. Code:"+PartStatus);
						return retVal;	
					}	
					
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchInventory Item not found. " +InventoryLookupObj);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchInventory Item found.Error..");
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
						
						InputObject = new HashMap<String, Serializable>();
						InputObject.put("ProjectCode", vProject);					
						InputObject.put("OrdNum", ParentOrdNum);					
						InputObject.put("UserName", vUserName);	
						InputObject.put("QAMode", "1");		
								
						HashMap<String, String> boxMap = new HashMap<String, String>();
						boxMap.put("Boxid", vBoxid);
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
						ret = CA.CompleteDeploymentOrder_CA(InputObject);
						if(ret.equals("0")) {
							CommUtil.logger.info("CompleteDeploymentOrder_CA - Success.");
						} else {
							CommUtil.logger.info("CompleteDeploymentOrder_CA - Failure. Code:"+ret);
							return ret;	
						}
				
					}
				}
			} else {
				ret = "-1";
				CommUtil.logger.info(" > CreateRepairOrders_CA return order count does not equal 1. returned cnt:" + TotalOrderCnt);
				return ret;						
			}
			
			
			
		} else {
			ret = "-1";
			CommUtil.logger.info(" > CreateRepairOrders_CA return null");
			return ret;	
		}
			
				
		return ret;
	}

}
