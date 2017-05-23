package test_case.test383;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.cst_main.CST_MainAction;
import action_lib.setting.LocationAdminAction;
import action_lib.setting.LocationEditorAction;
import action_lib.setting.SettingIndexAction;
import script_lib.CommUtil;

public class Case100_1 {
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
			
		CommUtil.logger.info(" > MenuInventory");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuSetting();
		
		CommUtil.logger.info(" > GotoLocationAdmin");
		SettingIndexAction setidxpage = new SettingIndexAction(webdriver);
		setidxpage.GotoLocationAdmin();	

		CommUtil.logger.info("> NewLocation");
		LocationAdminAction locAdminpage = new LocationAdminAction(webdriver);	
		locAdminpage.NewLocation();
		
		CommUtil.logger.info("> system doesn't allow duplicated Location and display error message");
		LocationEditorAction LocEditorpage = new LocationEditorAction(webdriver);
		HashMap<String, String> SaveLocationInputObj = new HashMap<String, String>();
		SaveLocationInputObj.put("LocType", "Prod Line");
		SaveLocationInputObj.put("LocName", "0000");
		SaveLocationInputObj.put("StockRoom", "BENT");
		
		CommUtil.logger.info("> SaveLocation");
		retVal = LocEditorpage.SaveLocation(SaveLocationInputObj);
		if(retVal.equals("3")) {
			CommUtil.logger.info("SaveLocation - Success");
		} else {
			CommUtil.logger.info("SaveLocation - Failure. Code:"+retVal);
			return retVal;
		}				
	
		return retVal;
	}
}
