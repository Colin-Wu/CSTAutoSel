package test_case.test382;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.setting.CustAdminAction;
import action_lib.setting.CustEditorAction;
import action_lib.setting.CustSiteEditorAction;
import config.TestSetting;
import script_lib.CommUtil;


public class Case94_1 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String vCustomer = TestSetting.Customer;

		String vSite = TestSetting.Site;
		
		String retVal = "-1";
		
		CommonAction CA = new CommonAction(webdriver);
			
		CommUtil.logger.info("> SearchCust_CA.");
		HashMap<String, String> SearchCust_CAInputObj = new HashMap<String, String>();
		SearchCust_CAInputObj.put("CustCode", vCustomer);
		SearchCust_CAInputObj.put("CustName", "");
		String isFound = CA.SearchCust_CA(SearchCust_CAInputObj);
    	if(!isFound.equals("1")) {
    		CommUtil.logger.info(">SearchCust_CA Failure. Code:" +isFound);
    		return retVal;
    	}
    	
		CommUtil.logger.info("> SelectCust");
		CustAdminAction CustAdminpage = new CustAdminAction(webdriver);
		String retStr = CustAdminpage.SelectCust(vCustomer);
    	if(!retStr.equals("0")) {
    		CommUtil.logger.info(">SelectCust Failure. Code:" +retStr);
    		return retVal;
    	}
		

		HashMap<String, String> CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "A");
		CheckPostCodeInputObj.put("PostCode", "");
		CustEditorAction CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = CustEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("1")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}	
		
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "US");
		CheckPostCodeInputObj.put("PostCode", "123456a#");
		//CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = CustEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("2")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}	
		
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "CN");
		CheckPostCodeInputObj.put("PostCode", "123456a#- ");
		//CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = CustEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("3")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}	
		
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "CN");
		CheckPostCodeInputObj.put("PostCode", "123456a- ");
		//CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = CustEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("0")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}
    	
		
		CommUtil.logger.info("> SearchSite.");
		HashMap<String, String> SearchSiteInputObj = new HashMap<String, String>();
		SearchSiteInputObj.put("SiteName", vSite);
		//CustEditorpage = new CustEditorAction(webdriver);
		isFound = CustEditorpage.SearchSite(SearchSiteInputObj);
    	if(!isFound.equals("1")) {
    		CommUtil.logger.info(">SearchSite Failure. Code:" +isFound);
    		return retVal;
    	}	
		
		CommUtil.logger.info("> SelectSite");
		retStr = CustEditorpage.SelectSite(vSite);
    	if(!retStr.equals("0")) {
    		CommUtil.logger.info(">SelectSite Failure. Code:" +retStr);
    		return retVal;
    	}
		
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "A");
		CheckPostCodeInputObj.put("PostCode", "");
		CustSiteEditorAction siteEditorpage = new CustSiteEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = siteEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("1")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}	
    	
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "US");
		CheckPostCodeInputObj.put("PostCode", "123456a#");
		//CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = siteEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("2")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}	
		
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "CN");
		CheckPostCodeInputObj.put("PostCode", "123456a#- ");
		//CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retStr = siteEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retStr.equals("3")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}	
		
		CheckPostCodeInputObj = new HashMap<String, String>();
		CheckPostCodeInputObj.put("CountryCode", "CN");
		CheckPostCodeInputObj.put("PostCode", "123456a- ");
		//CustEditorpage = new CustEditorAction(webdriver);
		CommUtil.logger.info("> CheckPostCode:"+CheckPostCodeInputObj);
		retVal = siteEditorpage.CheckPostCode(CheckPostCodeInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CheckPostCode - Success");
		} else {
			CommUtil.logger.info("CheckPostCode - Failure. Code:"+retStr);
			return retVal;
		}

    	
		return retVal;
	}
}
