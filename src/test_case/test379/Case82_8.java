package test_case.test379;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderEntry1Action;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_8 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vHPProject = TestSetting.HPProject;
		String vSite = TestSetting.Site;
		String vOrdAttr_IntRepairDFS = TestSetting.OrdAttr_IntRepairDFS;
		String vUserName = TestSetting.UserName;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vHPPart1 = TestSetting.HPPart1;
		String vHPStockGroup = TestSetting.HPStockGroup;
		String vStockGroup = TestSetting.StockGroup;
		
			
		CommUtil.logger.info(" > GetAvailablePart for repair order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		InputObj.put("caseid", "82_8");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
		if (retArr == null) {
			return retVal;
		}		
		if (retArr.size() > 0) {
			int boxidx = 0;
			HashMap<String, String> Retobj = retArr.get(boxidx);
			String inExpectedReceiveSN = Retobj.get("SN");
/*        	while (!TestSetting.reslist.addSN("82_8",inExpectedReceiveSN)) {
        		boxidx++;
        		Retobj = retArr.get(boxidx);
        		inExpectedReceiveSN = Retobj.get("SN");
			}
*/
			CommUtil.logger.info(" > Expected SN:" + inExpectedReceiveSN);	
			
			HashMap<String, String> retObj = null;
			HashMap<String, Serializable> CreateRepairOrders_CAInputObj = new HashMap<String, Serializable>();
			CreateRepairOrders_CAInputObj.put("Customer", vCustomer);
			CreateRepairOrders_CAInputObj.put("ProjectCode", vProject);
			CreateRepairOrders_CAInputObj.put("Site", vSite);
			CreateRepairOrders_CAInputObj.put("Attribute", vOrdAttr_IntRepairDFS);
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
					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum);
					
					CommUtil.logger.info(" > PickParts_CA");				
					HashMap<String, String> PickParts_CAInputObj = new HashMap<String, String>();
					PickParts_CAInputObj.put("ProjectCode", vProject);					
					PickParts_CAInputObj.put("OrdNum", ParentOrdNum);					
					PickParts_CAInputObj.put("UserName", vUserName);					
					PickParts_CAInputObj.put("BoxID", "");					
					PickParts_CAInputObj.put("Qty", "");					
					PickParts_CAInputObj.put("QA", "");					
					
					HashMap<Object, Object> pickretObj = CA.PickParts_CA(PickParts_CAInputObj);
					String retStr= (String) pickretObj.get("Ret");
					if(retStr.equals("0")) {
						CommUtil.logger.info("PickParts_CA - Success.");
					} else {
						CommUtil.logger.info("PickParts_CA - Failure. Code:"+retStr);
						return retVal;	
					}					

					CommUtil.logger.info(" > AddHPPartToRepair_CA");				
					HashMap<String, Serializable> AddHPPartToRepair_CAInputObj = new HashMap<String, Serializable>();
					AddHPPartToRepair_CAInputObj.put("RepairDispName", "");					
					AddHPPartToRepair_CAInputObj.put("ProjectCode", vProject);					
					AddHPPartToRepair_CAInputObj.put("OrdNum", ParentOrdNum);					
					AddHPPartToRepair_CAInputObj.put("BoxID", "");					
					AddHPPartToRepair_CAInputObj.put("SN", "");					

					HashMap<String, String> OrdLines = new HashMap<String, String>();
					OrdLines.put("Type", "Warranty");
					OrdLines.put("PartNum", vHPPart1);
					OrdLines.put("Qty", "1");
					OrdLines.put("Billable", "No");

					
					InputArr = new ArrayList<HashMap<String, String>>();
					InputArr.add(OrdLines);
					
					AddHPPartToRepair_CAInputObj.put("OrdLines", InputArr);
					
					retStr = CA.AddHPPartToRepair_CA(AddHPPartToRepair_CAInputObj);
					if(retStr.equals("0")) {
						CommUtil.logger.info("AddHPPartToRepair_CA - Success.");
					} else {
						CommUtil.logger.info("AddHPPartToRepair_CA - Failure. Code:"+retStr);
						return retVal;	
					}			
					
					CommUtil.logger.info(" > Create an earmarked PO.");	
					HashMap<String, String> CreateStdPO_CAInputObj = new HashMap<String, String>();
					CreateStdPO_CAInputObj.put("ProjectCode", vHPProject);
					CreateStdPO_CAInputObj.put("PartNum", vHPPart1);		
					CreateStdPO_CAInputObj.put("Qty", "1");		
					CreateStdPO_CAInputObj.put("OrdNum", ParentOrdNum);	
					CreateStdPO_CAInputObj.put("StockGroup", vHPStockGroup);		
					retObj = CA.CreateStdPO_CA(CreateStdPO_CAInputObj);
					retStr = retObj.get("RetVal").toString();
					//String PONum = retObj.get("PONum").toString();	
					if (!retStr.equals("0")) {
						retVal = "-1";
						CommUtil.logger.info("CreateStdPO_CA - Failure. Code:"+retStr);
						return retVal;
					}
					
					HashMap<String, String> GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "0");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					
					CommUtil.logger.info(" > Go to parent entry1.");
					retStr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retStr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}
					
					OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
					HashMap<String, String> CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "0");
					
					CommUtil.logger.info("> Check cancel button is invisible");
					retVal = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retVal.equals("1")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success: Cancel button invisible.");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retVal);
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
			
		}			
		return retVal;
	}
}
