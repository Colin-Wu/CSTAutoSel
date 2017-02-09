package test_case.test379;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.DockReceivingAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.odr_mngt.OrderEntry1Action;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_6 {

	
	public static String run (WebDriver webdriver) throws Exception {
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr_ExtRepair = TestSetting.OrdAttr_ExtRepairNoHP;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String vShipVia = TestSetting.ShipVia;
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
		
		CommUtil.logger.info(" > GetAvailablePart for repair order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		InputObj.put("caseid", "82_6");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
			
		if (retArr.size() > 0) {
			int boxidx = 0;
			HashMap<String, String> Retobj = retArr.get(boxidx);
			String inExpectedSendoutSN = Retobj.get("SN");
/*        	while (!TestSetting.reslist.addSN("82_6",inExpectedSendoutSN)) {
        		boxidx++;
        		Retobj = retArr.get(boxidx);
        		inExpectedSendoutSN = Retobj.get("SN");
			}*/
			
			CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
			
			HashMap<String, String> retObj = null;
			HashMap<String, Serializable> CreateEEXOrders_CAInputObj = new HashMap<String, Serializable>();
			CreateEEXOrders_CAInputObj.put("Customer", vCustomer);
			CreateEEXOrders_CAInputObj.put("ProjectCode", vProject);
			CreateEEXOrders_CAInputObj.put("Site", vSite);
			CreateEEXOrders_CAInputObj.put("Attribute", vOrdAttr_ExtRepair);
			CreateEEXOrders_CAInputObj.put("TemplateName", "");
			CreateEEXOrders_CAInputObj.put("ReqEmail", "11@22.com");
			CreateEEXOrders_CAInputObj.put("ProdLine", "");
			
			HashMap<String, String> PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", inExpectedSendoutSN);
			PartAddMap.put("StockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "");
			PartAddMap.put("ReqQty", "");
			
			ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateEEXOrders_CAInputObj.put("ExpectedSendoutPart", InputArr);

			PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", "");
			PartAddMap.put("ExpectedSN", inExpectedReceiveSN);
			PartAddMap.put("ReceiveStockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "");
			
			InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateEEXOrders_CAInputObj.put("ExpectedReceivePart", InputArr);

			CommUtil.logger.info(" > Create EEX order.");
			retObj = CA.CreateEEXOrders_CA(CreateEEXOrders_CAInputObj);

			if (retObj != null) {
				
				String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
				
				if (TotalOrderCnt.equals("3")) {

					String ParentOrdNum = retObj.get("ParentOrdNum").toString();
					String C1Num = retObj.get("C1Num").toString();
					String C1PO = retObj.get("C1PO").toString();
					String C2Num = retObj.get("C2Num").toString();
					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum+",Child1 order:"+C1Num+",Child2 order:"+C2Num);
					
			
					CommUtil.logger.info(" > Create Doc Receiving for Child1.");
					CommUtil.logger.info(" > MenuInventory");
					CST_MainAction mainpage = new CST_MainAction(webdriver);
					mainpage.MenuInventory();
					
					CommUtil.logger.info(" > GotoDockReceiving");
					InvIndexAction invidxpage = new InvIndexAction(webdriver);
					invidxpage.GotoDockReceiving();		
					
					CommUtil.logger.info(" > NewDocReceiving");
					DockReceivingAction dockRecvepage = new DockReceivingAction(webdriver);
					InputObj = new HashMap<String, String>();
					InputObj.put("TrackNum", C1PO);
					InputObj.put("PONum", C1PO);
					InputObj.put("Carrier", vShipVia);
					InputObj.put("ProjectCode", vProject);
					InputObj.put("BoxCnt","1");
					InputObj.put("PalletCnt", "1");
					retVal = dockRecvepage.NewDocReceiving(InputObj);		
					if (!retVal.equals("0")) {
						retVal = "-1";
						CommUtil.logger.info("NewDocReceiving - Failure. Code:"+retVal);
						return retVal;
					}
					
					HashMap<String, String> GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "1");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > Cancel Child1.");					
					CommUtil.logger.info(" > Go to Child1 entry1.");
			    	String retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}					
					
					OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
					HashMap<String, String> CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "1");
					//CancelOrderEntry1InputObject.put("HasCommitedPart", "");
					
					CommUtil.logger.info("> CancelOrderEntry1");
					retstr = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retstr.equals("0")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retstr);
						return retVal;	
					}
					
					
				    CommUtil.logger.info(" > Detail receive, check message:This repair order has been canceled, Please contact PS.");
				    CommUtil.logger.info(" > MenuInventroy");
				    mainpage = new CST_MainAction(webdriver);
					mainpage.MenuInventory();
					
					CommUtil.logger.info(" > InvIndex");
					
					InvIndexAction invIndexpage = new InvIndexAction(webdriver);
					invIndexpage.GotoDockReceiving();

					CommUtil.logger.info(" > DocReceivingGoToDetail");
					retVal=dockRecvepage.DocReceivingGoToDetail(C1PO);
					
					if(retVal.equals("1")){
						CommUtil.logger.info("> DocReceivingGoToDetail: Success.- Blocked from detail receiving");
					} else {
						CommUtil.logger.info("DocReceivingGoToDetail : Failed. Code:"+retVal);
					}
				}
			}
			
		}		
		
		return retVal;
	}
}
