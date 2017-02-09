package test_case.test380;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.inv_mngt.InventoryLookupAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case93_4 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_RepairStock;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String vQty = "1";
		String vShipVia = TestSetting.ShipVia;
		String vRepairLocation = TestSetting.RepairLocation;
		String vStockLocation = TestSetting.StockLocation;
		String vHPPart1 = TestSetting.HPPart1;
		String vHPStockGroup = TestSetting.HPStockGroup;
		String vHPProject = TestSetting.HPProject;
			

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
							
				HashMap<String, String> ReceiveToPutaway_CAInput=new HashMap<String, String>();
			      
				ReceiveToPutaway_CAInput.put("ProjectCode", vProject);
				ReceiveToPutaway_CAInput.put("PartNum", vProjectPart1);
				ReceiveToPutaway_CAInput.put("Qty", vQty);
				ReceiveToPutaway_CAInput.put("PONum", ParentPO);
				ReceiveToPutaway_CAInput.put("Carrier",vShipVia);
				ReceiveToPutaway_CAInput.put("BoxCnt", "1");
				ReceiveToPutaway_CAInput.put("PalletCnt","1");
				ReceiveToPutaway_CAInput.put("SN", inExpectedReceiveSN);
				ReceiveToPutaway_CAInput.put("BoxID", "");
				ReceiveToPutaway_CAInput.put("Location", vRepairLocation);
				ReceiveToPutaway_CAInput.put("Disposition", "Good");
				ReceiveToPutaway_CAInput.put("TrackNum", inExpectedReceiveSN);
		        
		        CommUtil.logger.info("> receive and putaway Parent order PO.");

		        ArrayList<HashMap<String, String>> rstArr = CA.ReceiveToPutaway_CA(ReceiveToPutaway_CAInput);
		        if (rstArr == null) {
					CommUtil.logger.info("ReceiveToPutaway_CA - Failure. ");
					return retVal;		        	
		        }	
		        CommUtil.logger.info("> add non warranty part.");
				CommUtil.logger.info(" > AddHPPartToRepair_CA");				
				HashMap<String, Serializable> AddHPPartToRepair_CAInputObj = new HashMap<String, Serializable>();
				AddHPPartToRepair_CAInputObj.put("RepairDispName", "");					
				AddHPPartToRepair_CAInputObj.put("ProjectCode", vProject);					
				AddHPPartToRepair_CAInputObj.put("OrdNum", ParentOrdNum);					
				AddHPPartToRepair_CAInputObj.put("BoxID", "");					
				AddHPPartToRepair_CAInputObj.put("SN", "");					

				HashMap<String, String> OrdLines = new HashMap<String, String>();
				OrdLines.put("Type", "Non-Warranty");
				OrdLines.put("PartNum", vHPPart1);
				OrdLines.put("Qty", "1");
				OrdLines.put("Billable", "No");

				
				InputArr = new ArrayList<HashMap<String, String>>();
				InputArr.add(OrdLines);
				
				AddHPPartToRepair_CAInputObj.put("OrdLines", InputArr);
				
				String retStr = CA.AddHPPartToRepair_CA(AddHPPartToRepair_CAInputObj);
				if(retStr.equals("0")) {
					CommUtil.logger.info("AddHPPartToRepair_CA - Success.");
				} else {
					CommUtil.logger.info("AddHPPartToRepair_CA - Failure. Code:"+retStr);
					return retVal;	
				}			
			
		        CommUtil.logger.info("> create earmarked PO and detail receive.");
			
    			HashMap<String, String> InputGeneratePart=new HashMap<String, String>();
    		      
    			String HPPartSN = CommUtil.genDateTimeStamp();
    			InputGeneratePart.put("ProjectCode", vHPProject);
    			InputGeneratePart.put("PartNum", vHPPart1);
    			InputGeneratePart.put("Qty", "1");
    			InputGeneratePart.put("OrdNum", ParentOrdNum);
    			InputGeneratePart.put("PONum", "");
    			InputGeneratePart.put("Carrier", vShipVia);
    			InputGeneratePart.put("BoxCnt", "1");
    			InputGeneratePart.put("PalletCnt","1");
    			InputGeneratePart.put("SN", HPPartSN);
    			InputGeneratePart.put("BoxID", "");
    			InputGeneratePart.put("Location", vStockLocation);
    			InputGeneratePart.put("Disposition", "Good");
    			InputGeneratePart.put("Mode", "1");
    			InputGeneratePart.put("StockGroup", vHPStockGroup);
    			
    			CommUtil.logger.info("Calling..GeneratePart_CA");
    			String retstr = CA.GeneratePart_CA(InputGeneratePart);			
				if (retstr.equals("-1")) {
					CommUtil.logger.info(">GeneratePart_CA.Error..");
					return retVal;	
				}	
				
		        CommUtil.logger.info("> check putaway to stock location successfully.");
				InventoryLookupAction InvLookup = new InventoryLookupAction(webdriver);
				HashMap<String, String> InventoryLookupObj = new HashMap<String, String>();
				InventoryLookupObj.put("PartNum",vHPPart1);
				InventoryLookupObj.put("SearchSN",HPPartSN);
				InventoryLookupObj.put("SearchPONum","");
				InventoryLookupObj.put("ProjectCode",vHPProject);
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
					ArrayList<HashMap<String, String>> rstArr2 = (ArrayList<HashMap<String, String>>) OutPutInventoryObj.get("RstArr");
					retObj = new HashMap<String, String>(); 
					retObj = rstArr2.get(0);
					String Location = retObj.get("Location");
					if (CommUtil.isMatchByReg(Location.toUpperCase(), vStockLocation.toUpperCase())) {
						retVal = "0";
						CommUtil.logger.info("> HP part(Non-Warranty) successfully putaway to stock location.");
					} else {
						CommUtil.logger.info("> HP part(Non-Warranty) failed to putaway to stock location.");
					}
					
					
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchInventory Item not found. " +InventoryLookupObj);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchInventory Item found.Error..");
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
