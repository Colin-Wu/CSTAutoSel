package tools;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import config.TestSetting;

public class Genpart {
		// generate parts
	public static String run (WebDriver webdriver) throws Exception {
		String retVal = "-1";

		
		CommonAction CA = new CommonAction(webdriver);
		CA.GenerateStandardPart_CA(TestSetting.DefaultGenQty);
		
		return retVal;
	}
}
