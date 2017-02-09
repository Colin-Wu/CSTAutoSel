package test_case.test382;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.TemplateAdminAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case98_1 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String vCustomer = TestSetting.Customer;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_DisposalOfMtrl;
		String vReqEmail = "11@22.com";
		String vTemplateName = "Disposal_Tmplt";
		
		String retVal = "-1";
		
		CommonAction CA = new CommonAction(webdriver);

		HashMap<String, String> retObj = null;
		HashMap<String, Serializable> CreateDisposalOrders_CAInputObj = new HashMap<String, Serializable>();
		CreateDisposalOrders_CAInputObj.put("Customer", vCustomer);
		CreateDisposalOrders_CAInputObj.put("ProjectCode", vProject);
		CreateDisposalOrders_CAInputObj.put("Site", vSite);
		CreateDisposalOrders_CAInputObj.put("Attribute", vOrdAttr);
		CreateDisposalOrders_CAInputObj.put("TemplateName", vTemplateName);
		CreateDisposalOrders_CAInputObj.put("ReqEmail", vReqEmail);
		CreateDisposalOrders_CAInputObj.put("ProdLine", "");
		
	
		ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
		
		CreateDisposalOrders_CAInputObj.put("ExpectedSendoutPart", InputArr);

		CommUtil.logger.info(" > Create Disposal order template.");
		retObj = CA.CreateDisposalOrders_CA(CreateDisposalOrders_CAInputObj);

		if (retObj != null) {
			
			String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
			
			if (TotalOrderCnt.equals("1")) {

				CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt);
				
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

		
			}
		}
					
		return retVal;
	}

}
