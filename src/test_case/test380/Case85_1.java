package test_case.test380;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.inv_mngt.TransferRequestAction;
import action_lib.inv_mngt.TransferRequestForBoxAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case85_1 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_RepairStock;
		String vShipVia = TestSetting.ShipVia;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vQty = "1";
		String vStockGroup = TestSetting.StockGroup;
		String vRepairLocation = TestSetting.RepairLocation;
			

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

				ArrayList<HashMap<String, String>> rstArr =  CA.ReceiveToPutaway_CA(ReceiveToPutaway_CAInput);
		        if (rstArr == null) {
					CommUtil.logger.info("ReceiveToPutaway_CA - Failure. ");
					return retVal;		        	
		        }
				retObj = new HashMap<String, String>(); 
				retObj = rstArr.get(0);
				String boxid = retObj.get("BoxID");
		
		        CommUtil.logger.info("> Can't transfer to another project.");
		        CommUtil.logger.info("> MenuInventory");
				CST_MainAction mainpage = new CST_MainAction(webdriver);
				mainpage.MenuInventory();
					
		        CommUtil.logger.info("> GotoTransferRequest");
				InvIndexAction invidx = new InvIndexAction(webdriver);
				invidx.GotoTransferRequest();
				
		        CommUtil.logger.info("> FillTransferRequest");
				TransferRequestAction trans = new TransferRequestAction(webdriver); 
				HashMap<String, String> InputObject = new HashMap<String, String>();
				InputObject.put("TransType", "Project");
				InputObject.put("TransByType", "");
				InputObject.put("Customer", vCustomer);
				InputObject.put("FromProj", vProject);
				InputObject.put("ToProj", "UFT2");	
				String retstr= trans.FillTransferRequest(InputObject);
				if (!retstr.equals("0")) {
					CommUtil.logger.info(">FillTransferRequest.Error..");
					return retVal;	
				}	
				
				retstr= trans.GoNext();
				if (!retstr.equals("0")) {
					CommUtil.logger.info(">GoNext.Error..");
					return retVal;	
				}	
							
				TransferRequestForBoxAction transbox = new TransferRequestForBoxAction(webdriver); 

				HashMap<String, String> Boxids = new HashMap<String, String>();
				Boxids.put("BoxID", boxid);
				Boxids.put("Qty", "1");
				Boxids.put("NewLocation", "00");
				
				InputArr = new ArrayList<HashMap<String, String>>();
				InputArr.add(Boxids);
				
				retVal = transbox.FillTransRequestForBox(InputArr);
				if(retVal.equals("1")) {
					CommUtil.logger.info("TransferRequestForBoxAction save - Success: Can't transfer the box.");
				} else {
					CommUtil.logger.info("TransferRequestForBoxAction save - Failure. Code:"+retVal);
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
