package action_lib.cst_main;

import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.cst_main.CST_MainObj;

public class CST_MainAction {
	public static void logout () {
		
		CST_MainObj mainObj = new CST_MainObj(TestSetting.driver);
	
		WebElement CmdsignOut = mainObj.getCmdsignOut();

		CmdsignOut.click();
		
	}
	
	public static void MenuOrdMngt () {
		
		CST_MainObj mainObj = new CST_MainObj(TestSetting.driver);
	
		WebElement cmdOrderAdmin = mainObj.getCmdOrderAdmin();

		cmdOrderAdmin.click();
		
	}
}
