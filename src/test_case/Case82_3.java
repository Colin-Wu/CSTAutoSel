package test_case;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_3 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
			
	
		CommUtil.logger.info(" > SearchPO");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("SearchProjectCode", TestSetting.Project);
		InputObj.put("SearchCustomerPO", TestSetting.RepairPONum);		
		CommonAction CA = new CommonAction(webdriver);
		String isFound = CA.SearchPO_CA(InputObj);
			
		if (isFound.equals("1")) {
			POMngtAction pomngtpage = new POMngtAction(webdriver);
			retVal = pomngtpage.DeletePO(InputObj);
			
		}

	
		return retVal;
	}
}
