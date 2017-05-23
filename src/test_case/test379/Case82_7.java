package test_case.test379;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.inv_mngt.InventoryLookupAction;
import action_lib.inv_mngt.ReceivingPutawayAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_7 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr_ExtRepair = TestSetting.OrdAttr_ExtRepairNoHP;
		String vShipVia = TestSetting.ShipVia;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vQty = "1";
		String vStockGroup = TestSetting.StockGroup;
			
/*		CommUtil.logger.info(" > GetAvailablePart for repair order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
			
		if (retArr.size() > 0) {
			HashMap<String, String> Retobj = retArr.get(0);
			String inExpectedReceiveSN = Retobj.get("SN");*/
		CommonAction CA = new CommonAction(webdriver);
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
		CommUtil.logger.info(" > Expected SN:" + inExpectedReceiveSN);	
		
		HashMap<String, String> retObj = null;
		HashMap<String, Serializable> CreateRepairOrders_CAInputObj = new HashMap<String, Serializable>();
		CreateRepairOrders_CAInputObj.put("Customer", vCustomer);
		CreateRepairOrders_CAInputObj.put("ProjectCode", vProject);
		CreateRepairOrders_CAInputObj.put("Site", vSite);
		CreateRepairOrders_CAInputObj.put("Attribute", vOrdAttr_ExtRepair);
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
			
			if (TotalOrderCnt.equals("2")) {

				String ParentOrdNum = retObj.get("ParentOrdNum").toString();
				String C1Num = retObj.get("C1Num").toString();
				String C1PO = retObj.get("C1PO").toString();
				CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum+",Child1 order:"+C1Num);
				
				HashMap<String, String> ReceiveToDetailReceivingInputObj = new HashMap<String, String>();
				ReceiveToDetailReceivingInputObj.put("PONum", C1PO);
				ReceiveToDetailReceivingInputObj.put("Carrier", vShipVia);
				ReceiveToDetailReceivingInputObj.put("ProjectCode", vProject);
				ReceiveToDetailReceivingInputObj.put("BoxCnt", "1");
				ReceiveToDetailReceivingInputObj.put("PalletCnt", "1");
				ReceiveToDetailReceivingInputObj.put("TrackNum", inExpectedReceiveSN);
				ReceiveToDetailReceivingInputObj.put("PartNum", vProjectPart1);
				ReceiveToDetailReceivingInputObj.put("Qty", vQty);
				ReceiveToDetailReceivingInputObj.put("SN", inExpectedReceiveSN);
				ReceiveToDetailReceivingInputObj.put("Disposition", "");
				
				CommUtil.logger.info(" > Detail receive Child 1 PO.");
		    	String OutPalletID= CA.ReceiveToDetailReceiving_CA(ReceiveToDetailReceivingInputObj);
				if (OutPalletID.equals("-1")) {
					CommUtil.logger.info(">ReceiveToDetailReceiving_CA.Error..");
					return retVal;	
				}	
				
				CommUtil.logger.info(" > Putaway to stock location, check message.");
				CommUtil.logger.info(" > MenuInventory");
				CST_MainAction mainpage = new CST_MainAction(webdriver);
				mainpage.MenuInventory();
				
				CommUtil.logger.info(" > GotoReceivingPutaway");
				InvIndexAction invidxpage = new InvIndexAction(webdriver);
				invidxpage.GotoReceivingPutaway();

				ReceivingPutawayAction ReceivingPutaway= new ReceivingPutawayAction(webdriver);

				HashMap<String, String> ReceivingPutawayInPutObj = new HashMap<String, String>();
				ReceivingPutawayInPutObj.put("Status","PendPutaway");
				ReceivingPutawayInPutObj.put("SN","");
				ReceivingPutawayInPutObj.put("ProjectCode",vProject);
				ReceivingPutawayInPutObj.put("PalletID",OutPalletID);
				ReceivingPutawayInPutObj.put("BoxID","");
				CommUtil.logger.info("> SearchPutaway");
				String outIsFound= ReceivingPutaway.SearchPutaway(ReceivingPutawayInPutObj);

				if (outIsFound.equals("1")) {
					CommUtil.logger.info("> SearchPutaway-PendPutaway Item found.");
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchPutaway-PendPutaway Item not found. SN: " + inExpectedReceiveSN+" Projectcode : "+vProject+" PalletID : "+OutPalletID);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchPutaway-PendPutaway Item found.Error..");
					return retVal;	
				}

				CommUtil.logger.info("> SaveToPutawayready : Cannot receive to stock location");
				String OutRetVal= ReceivingPutaway.SaveToPutawayready(TestSetting.StockLocation);

				if(OutRetVal.equals("2")) {
					CommUtil.logger.info("SaveToPutawayready - Success.");
				} else {
					CommUtil.logger.info("SaveToPutawayready - Failure. Code:"+OutRetVal);
					return retVal;	
				}
				
				CommUtil.logger.info("> SaveToPutawayready : receive to repair location");
				OutRetVal= ReceivingPutaway.SaveToPutawayready(TestSetting.RepairLocation);

				if(OutRetVal.equals("0")) {
					CommUtil.logger.info("SaveToPutawayready - Success.");
				} else {
					CommUtil.logger.info("SaveToPutawayready - Failure. Code:"+OutRetVal);
					return retVal;	
				}
				
				
				CommUtil.logger.info("> Putaway to repair location.");
				ReceivingPutaway= new ReceivingPutawayAction(webdriver);

				ReceivingPutawayInPutObj = new HashMap<String, String>();
				ReceivingPutawayInPutObj.put("Status","PutawayReady");
				ReceivingPutawayInPutObj.put("SN","");
				ReceivingPutawayInPutObj.put("ProjectCode",vProject);
				ReceivingPutawayInPutObj.put("PalletID",OutPalletID);
				ReceivingPutawayInPutObj.put("BoxID","");
				CommUtil.logger.info("> SearchPutaway");
				outIsFound= ReceivingPutaway.SearchPutaway(ReceivingPutawayInPutObj);

				if (outIsFound.equals("1")) {
					CommUtil.logger.info("> SearchPutaway-PutawayReady Item found.");
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchPutaway-PutawayReady Item not found. SN: " + inExpectedReceiveSN+" Projectcode : "+vProject+" PalletID : "+OutPalletID);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchPutaway-PutawayReady Item found.Error..");
					return retVal;	
				}

				CommUtil.logger.info("> SaveToPutaway");
				String outRetVal= ReceivingPutaway.SaveToPutaway(vQty);

				if (outRetVal.equals("0")) {
					CommUtil.logger.info("SaveToPutaway - Success");
				} else {
					CommUtil.logger.info("SaveToPutaway - Failure. Code:"+outRetVal);
					return retVal;	
				}
				
				CommUtil.logger.info("> Check status in Inventory.");
				CommUtil.logger.info("> MenuInventory.");
				mainpage= new CST_MainAction(webdriver);
				mainpage.MenuInventory();
				
				CommUtil.logger.info("> GotoInventoryLookup.");
				invidxpage = new InvIndexAction(webdriver);
				invidxpage.GotoInventoryLookup();

				CommUtil.logger.info("> SearchInventory");
				InventoryLookupAction InvLookup = new InventoryLookupAction(webdriver);
				HashMap<String, String> InventoryLookupObj = new HashMap<String, String>();
				InventoryLookupObj.put("PartNum",vProjectPart1);
				InventoryLookupObj.put("SearchSN",inExpectedReceiveSN);
				InventoryLookupObj.put("SearchPONum",C1PO);
				InventoryLookupObj.put("ProjectCode",vProject);
				InventoryLookupObj.put("SearchStatus","");
				InventoryLookupObj.put("PalletID",OutPalletID);
				InventoryLookupObj.put("StockGroup",TestSetting.StockGroup);
				InventoryLookupObj.put("Qty", vQty);    
				InventoryLookupObj.put("SearchBoxid","");

				HashMap<Object, Object> OutPutInventoryObj=InvLookup.SearchInventory(InventoryLookupObj);
				outIsFound = OutPutInventoryObj.get("IsFound").toString();
				
				if (outIsFound.equals("1")) {
					CommUtil.logger.info("> SearchInventory Item found.");
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchInventory Item not found. SN: " + inExpectedReceiveSN+" Projectcode : "+vProject+" PalletID : "+OutPalletID);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchInventory Item found.Error..");
					return retVal;	
				}					
				
				@SuppressWarnings("unchecked")
				ArrayList<HashMap<String, String>> rstArr = (ArrayList<HashMap<String, String>>) OutPutInventoryObj.get("RstArr");
				retObj = new HashMap<String, String>(); 
				retObj = rstArr.get(0);
				String partStatus = retObj.get("PartStatus");
				if (CommUtil.isMatchByReg(partStatus, "REPAIR")) {
					CommUtil.logger.info("> SearchInventory status matched. status:"+partStatus);
					retVal = "0";
				} else {
					CommUtil.logger.info("> SearchInventory status not matched. status:"+partStatus);
					return retVal;	
				}
				
				

			} else {
				retVal = "-1";
				CommUtil.logger.info(" > CreateRepairOrders_CA return order count does not equal 2. returned cnt:" + TotalOrderCnt);
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
