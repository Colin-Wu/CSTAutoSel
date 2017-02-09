package test_case.test380;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.DockReceivingAction;
import action_lib.inv_mngt.InvIndexAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case87_1 {
	
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
							
				HashMap<String, String> ReceiveToDetailReceivingInputObj = new HashMap<String, String>();
				ReceiveToDetailReceivingInputObj.put("PONum", ParentPO);
				ReceiveToDetailReceivingInputObj.put("Carrier", vShipVia);
				ReceiveToDetailReceivingInputObj.put("ProjectCode", vProject);
				ReceiveToDetailReceivingInputObj.put("BoxCnt", "1");
				ReceiveToDetailReceivingInputObj.put("PalletCnt", "1");
				ReceiveToDetailReceivingInputObj.put("TrackNum", inExpectedReceiveSN);
				ReceiveToDetailReceivingInputObj.put("PartNum", vProjectPart1);
				ReceiveToDetailReceivingInputObj.put("Qty", "1");
				ReceiveToDetailReceivingInputObj.put("SN", inExpectedReceiveSN);
				ReceiveToDetailReceivingInputObj.put("Disposition", "");
				
				CommUtil.logger.info(" > Detail receive Parent PO.");
		    	String OutPalletID= CA.ReceiveToDetailReceiving_CA(ReceiveToDetailReceivingInputObj);
				if (OutPalletID.equals("-1")) {
					CommUtil.logger.info(">ReceiveToDetailReceiving_CA.Error..");
					return retVal;	
				}
				
				CommUtil.logger.info(" > Go to dock receiving check whether PO and project code be greyed out.");

			    CommUtil.logger.info(" > MenuInventroy");
			    CST_MainAction mainpage = new CST_MainAction(webdriver);
				mainpage.MenuInventory();
				
				CommUtil.logger.info(" > InvIndex");				
				InvIndexAction invIndexpage = new InvIndexAction(webdriver);
				invIndexpage.GotoDockReceiving();

				CommUtil.logger.info(" > Search DocReceiving");
				DockReceivingAction dockRecvepage = new DockReceivingAction(webdriver);
				HashMap<String, String> SearchDocReceivingInputObj = new HashMap<String, String>();
				SearchDocReceivingInputObj.put("TrackNum", inExpectedReceiveSN);
				SearchDocReceivingInputObj.put("PONum", ParentPO);
				SearchDocReceivingInputObj.put("ProjectCode", vProject);
				retVal = dockRecvepage.SearchDocReceiving(SearchDocReceivingInputObj);
				if (!retVal.equals("1")) {
					retVal = "-1";
					CommUtil.logger.info("SearchDocReceiving - Failure. Code:"+retVal);
					return retVal;
				}	
					
				CommUtil.logger.info(" > GoToEditLine");
				HashMap<String, String> RetObj = dockRecvepage.GoToEditLine(inExpectedReceiveSN);
				retVal = RetObj.get("RetVal");
				String RowIdx = RetObj.get("RowIdx");
				if (!retVal.equals("0")) {
					retVal = "-1";
					CommUtil.logger.info("GoToEditLine - Failure. Code:"+retVal);
					return retVal;
				}	
				
				CommUtil.logger.info(" > GoToEditLine");
				HashMap<String, String> InputObject = new HashMap<String, String>();
				InputObject.put("TrackNum", "");
				InputObject.put("PONum", "");
				InputObject.put("Carrier", "");
				InputObject.put("ProjectCode", "");
				InputObject.put("BoxCnt", "");
				InputObject.put("PalletCnt", "");
				InputObject.put("RowIdx", RowIdx);
				retVal = dockRecvepage.FillEditLine(InputObject);
				if(retVal.equals("2")) {
					CommUtil.logger.info("FillEditLine - Success: PO and project code are disabled");
				} else {
					CommUtil.logger.info("FillEditLine - Failure. Code:"+retVal);
					return retVal;	
				}	
				
				
		    	HashMap<String, String> InputPutAway=new HashMap<String, String>();
		    	InputPutAway.put("SN",inExpectedReceiveSN);
		    	InputPutAway.put("ProjectCode",vProject);
		    	InputPutAway.put("PalletID",OutPalletID);
		    	InputPutAway.put("BoxID","");
		    	InputPutAway.put("Location",vRepairLocation);
		    	InputPutAway.put("PartNum",vProjectPart1);
		    	InputPutAway.put("PONum",ParentPO);
		    	InputPutAway.put("Qty",vQty);
		    	InputPutAway.put("StockGroup","");
		    	
		    	 CommUtil.logger.info(">PutawayToInv_CA  ..."+InputPutAway);
		    	
		    	HashMap<Object, Object> OutputInvputaway=CA.PutawayToInv_CA(InputPutAway);
		    	String outIsFound= OutputInvputaway.get("IsFound").toString();
		    	@SuppressWarnings("unchecked")
				ArrayList<HashMap<String, String>> rstArr= (ArrayList<HashMap<String, String>>) OutputInvputaway.get("RstArr");
		    
		    	if(outIsFound.equals("1")){
		    		CommUtil.logger.info(">PutawayToInv.Success: " +vProjectPart1+" Result Array size: "+rstArr.size());
    		
					CommUtil.logger.info(" > Check whether edit line link disabled.");

				    CommUtil.logger.info(" > MenuInventroy");
				    mainpage = new CST_MainAction(webdriver);
					mainpage.MenuInventory();
					
					CommUtil.logger.info(" > InvIndex");				
					invIndexpage = new InvIndexAction(webdriver);
					invIndexpage.GotoDockReceiving();

					CommUtil.logger.info(" > Search DocReceiving");
					dockRecvepage = new DockReceivingAction(webdriver);
					SearchDocReceivingInputObj = new HashMap<String, String>();
					SearchDocReceivingInputObj.put("TrackNum", inExpectedReceiveSN);
					SearchDocReceivingInputObj.put("PONum", ParentPO);
					SearchDocReceivingInputObj.put("ProjectCode", vProject);
					retVal = dockRecvepage.SearchDocReceiving(SearchDocReceivingInputObj);
					if (!retVal.equals("1")) {
						retVal = "-1";
						CommUtil.logger.info("SearchDocReceiving - Failure. Code:"+retVal);
						return retVal;
					}	
						
					CommUtil.logger.info(" > GoToEditLine");
					RetObj = dockRecvepage.GoToEditLine(inExpectedReceiveSN);
					retVal = RetObj.get("RetVal");
					RowIdx = RetObj.get("RowIdx");
					if(retVal.equals("1")) {
						CommUtil.logger.info("FillEditLine - Success: Edit line link disabled");
					} else {
						CommUtil.logger.info("FillEditLine - Failure. Code:"+retVal);
						return retVal;	
					}	

    		
		    	} else {
		    		CommUtil.logger.info(">ReceiveToPutaway.Part not found. PartNum: " +vProjectPart1 );
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
