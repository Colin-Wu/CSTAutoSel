package test_case.test380;

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

public class Case85_2 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String ret = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_RepairStock;
		String vShipVia = TestSetting.ShipVia;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vQty = "1";
		String vStockGroup = TestSetting.StockGroup;
		String vNewLocation = "00";
		
		String vTransType = "Project";
		String vTransByType = "";
		String vFromProj = vProject;
		String vToProj = "UFT2";
	
		String vSearchUsername = "";
		String vUsername = TestSetting.UserName;
		String vStatus = "";
		String vSearchTransNum = "";
		String vSearchPalletID = "";
		
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
				
				CommUtil.logger.info("> Get boxid in Inventory.");
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
				InventoryLookupObj.put("StockGroup",TestSetting.StockGroup);
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
					
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchInventory Item not found. " +InventoryLookupObj);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchInventory Item found.Error..");
					return retVal;	
				}	
				
				CommUtil.logger.info(" > Transfer to another project successfully.");
				CommUtil.logger.info(" > CompleteTransferRequest_CA");
				InputObject = new HashMap<String, Serializable>();
				InputObject.put("TransType", vTransType);
				InputObject.put("TransByType", vTransByType);
				InputObject.put("Customer", vCustomer);
				InputObject.put("FromProj", vFromProj);
				InputObject.put("ToProj", vToProj);
				
				HashMap<String, String> Boxids = new HashMap<String, String>();
				Boxids.put("BoxID", vBoxid);
				Boxids.put("Qty", "1");
				Boxids.put("NewLocation", vNewLocation);
				
				ArrayList<HashMap<String, String>> RequestBoxIds = new ArrayList<HashMap<String, String>>();
				RequestBoxIds.add(Boxids);			
				InputObject.put("RequestBoxIds", RequestBoxIds);
				
				InputObject.put("SearchUsername", vSearchUsername);
				InputObject.put("Username", vUsername);
				InputObject.put("Status", vStatus);
				InputObject.put("SearchTransNum", vSearchTransNum);
				InputObject.put("BoxId", vBoxid);
				InputObject.put("SearchPalletID", vSearchPalletID);
				
				Boxids = new HashMap<String, String>();
				Boxids.put("BoxID", vBoxid);
				Boxids.put("PalletID", "");
				Boxids.put("NewLocation", vNewLocation);
				
				ArrayList<HashMap<String, String>> ScanBoxIds = new ArrayList<HashMap<String, String>>();
				ScanBoxIds.add(Boxids);
				
				InputObject.put("ScanBoxIds", ScanBoxIds);
				ret = CA.CompleteTransferRequest_CA(InputObject);
				if(ret.equals("0")) {
					CommUtil.logger.info("CompleteTransferRequest_CA - Success.");
				} else {
					CommUtil.logger.info("CompleteTransferRequest_CA - Failure. Code:"+ret);
					return ret;	
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
