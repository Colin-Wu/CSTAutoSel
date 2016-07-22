package test_case;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.index.IndexPageAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_1 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		
		CommUtil.logger.info(Case82_1.class.getName() + " > Login");	
		IndexPageAction idxpage = new IndexPageAction(webdriver);
		idxpage.login();
		
		CommUtil.logger.info(Case82_1.class.getName() + " > SearchPO");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("SearchProjectCode", TestSetting.Project);
		InputObj.put("SearchCustomerPO", TestSetting.StandardPONum);		
		CommonAction CA = new CommonAction(webdriver);
		String isFound = CA.SearchPO_CA(InputObj);
			
		if (isFound.equals("1")) {
			POMngtAction pomngtpage = new POMngtAction(webdriver);
			retVal = pomngtpage.DeletePO(InputObj);
			
		}

		CommUtil.logger.info(Case82_1.class.getName() + " > Logout");	
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.logout();
		
		return retVal;
	}
}
