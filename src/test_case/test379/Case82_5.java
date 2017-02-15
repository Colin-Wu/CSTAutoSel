package test_case.test379;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderEntry1Action;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_5 {

	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr_ExtRepair = TestSetting.OrdAttr_ExtRepair;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		
			
		CommUtil.logger.info(" > GetAvailablePart for repair order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		InputObj.put("caseid", "82_5");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
		if (retArr == null) {
			return retVal;
		}			
		if (retArr.size() > 0) {
			int boxidx = 0;
			HashMap<String, String> Retobj = retArr.get(boxidx);
			String inExpectedReceiveSN = Retobj.get("SN");
/*        	while (!TestSetting.reslist.addSN("82_5",inExpectedReceiveSN)) {
        		boxidx++;
        		Retobj = retArr.get(boxidx);
        		inExpectedReceiveSN = Retobj.get("SN");
			}*/
			
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
					
					HashMap<String, String> GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "0");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					
					CommUtil.logger.info(" > Go to parent entry1.");
			    	String retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}
					
					OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
					HashMap<String, String> CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "1");
					CancelOrderEntry1InputObject.put("HasCommitedPart", "");
					
					CommUtil.logger.info("> CancelOrderEntry1");
					retstr = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retstr.equals("0")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retVal);
						return retVal;	
					}				
					
					CommUtil.logger.info(" >  check PO delete successfully.");	
					HashMap<String, String> SearchPOInputObj = new HashMap<String, String>();
					SearchPOInputObj.put("SearchProjectCode", vProject);
					SearchPOInputObj.put("SearchCustomerPO", C1PO);		
					String isFound = CA.SearchPO_CA(SearchPOInputObj);
					if (!isFound.equals("1")) {
						CommUtil.logger.info(" >  Canceled Successfully");	
						retVal = "0";
						
					} else {
						retVal = "-1";
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
		} else {
			retVal = "-1";
			CommUtil.logger.info(" > GetAvailablePart_CA no available part.");
			return retVal;				
		}
			
				
		return retVal;
	}
}
