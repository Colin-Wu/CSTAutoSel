package test_case.test381;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.odr_mngt.OrderAdminAction;
import action_lib.odr_mngt.OrderEntry1Action;
import action_lib.odr_mngt.OrderEntry2Action;
import action_lib.odr_mngt.OrderIndexAction;
import action_lib.odr_mngt.TemplateAdminAction;
import config.TestSetting;
import obj_repository.odr_mngt.OrderEntry2Obj;
import obj_repository.odr_mngt.OrderEntry3Obj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class Case88_1 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_PPS;
		String vReqEmail = "11@22.com";
		String vTemplateName = "Standard_PPS_Tmplt";
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		
		String retVal = "-1";
		
		CommonAction CA = new CommonAction(webdriver);
			
		CommUtil.logger.info("> MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();

		CommUtil.logger.info("> GotoOrdAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);	
		ordidxpage.GotoOrdAdmin();

		CommUtil.logger.info("> NewOrder");
		OrderAdminAction ordAdminpage = new OrderAdminAction(webdriver);	
		ordAdminpage.NewOrder();

		String vOrdType = TestSetting.OrdType_Standard;
		String vMode = "0";
		String vPO = "";
		String vPOLink = "0";
		
		CommUtil.logger.info("> Creating standard order.");
		OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
		HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", vMode);
		CreateOrderEntry1InputObject.put("Customer", vCustomer);
		CreateOrderEntry1InputObject.put("ProjectCode", vProject);
		CreateOrderEntry1InputObject.put("Site", vSite);
		CreateOrderEntry1InputObject.put("OrdType", vOrdType);
		CreateOrderEntry1InputObject.put("Attribute", vOrdAttr);
		CreateOrderEntry1InputObject.put("TemplateName", vTemplateName);
		CreateOrderEntry1InputObject.put("ReqEmail", vReqEmail);
		CreateOrderEntry1InputObject.put("PO", vPO);
		CreateOrderEntry1InputObject.put("POLink", vPOLink);
		CreateOrderEntry1InputObject.put("ProdLine", "");
		
		CommUtil.logger.info("> CreateOrderEntry1");
		String retStr = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retStr.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retStr);
			return retVal;
		}		

		CommUtil.logger.info("> at entry2, can't search by SN;");
		OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
		SeleniumUtil.waitWebElementVisible(webdriver, Obj.getBtnNextLocator());
		boolean SNExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getTxtSearchSNLocator(), 1);
		if (!SNExist) {
			CommUtil.logger.info("Check SN visibility - Success: not displayed.");
		} else {
			CommUtil.logger.info("Check SN visibility - Failure. Code:"+SNExist);
			return retVal;			
		}
		
		CommUtil.logger.info("> search part, qty input 1000, still be able to successfully move to entry3.");
		HashMap<String, String> PartAddMap = new HashMap<String, String>();
		PartAddMap.put("SearchPartNum", vProjectPart1);
		PartAddMap.put("SearchBOM", "");
		PartAddMap.put("SearchSN", "");
		PartAddMap.put("StockGroup", vStockGroup);
		PartAddMap.put("IsConfig", "");
		PartAddMap.put("ReqQty", "1000");
		
		ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
		InputArr.add(PartAddMap);
		
		OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
		
		HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
		InputEntry2Obj.put("Mode", "0");
		InputEntry2Obj.put("PartArr", InputArr);
		
		CommUtil.logger.info("> CreateOrderEntry2");
		retStr = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retStr.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retStr);
			return retVal;
		}	
		
		//wait entry3 page.
		OrderEntry3Obj entry3Obj = new OrderEntry3Obj(webdriver);	
		entry3Obj.getTxtAddr5();	
		
		CommUtil.logger.info("> Delete the template to recover the data");
		CommUtil.logger.info("> SearchTemplate_CA.");
		HashMap<String, String> SearchTemplate_CAInputObj = new HashMap<String, String>();
		SearchTemplate_CAInputObj.put("ProjectCode", vProject);
		SearchTemplate_CAInputObj.put("TmpltName", vTemplateName);
		String isFound = CA.SearchTemplate_CA(SearchTemplate_CAInputObj);
    	if(!isFound.equals("1")) {
    		CommUtil.logger.info(">SearchTemplate_CA Failure. Code:" +isFound);
    		return retVal;
    	}
    	
		CommUtil.logger.info("> DeleteTemplate");
		TemplateAdminAction TemplateAdminpage = new TemplateAdminAction(webdriver);
		retVal = TemplateAdminpage.DeleteTemplate(vTemplateName);
		if (retVal.equals("0")) {
			CommUtil.logger.info(">Template deleted.");
		} else {
			CommUtil.logger.info(">DeleteTemplate Error..");
			return retVal;	
		}	
		
		
		return retVal;
	}
}
