package test_case;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.index.IndexPageAction;
import action_lib.inv_mngt.DockReceivingAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case82_2 {
	
	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";
		
		String ProjectPart1 = TestSetting.ProjectPart1;
		String QTY = "1";
		
		String TrackNum = CommUtil.genDateTimeStamp();
		String Carrier = TestSetting.ShipVia;
		String ProjectCode = TestSetting.Project;
		String BoxCnt = "1";
		String PalletCnt = "1";
		
		CommUtil.logger.info(Case82_2.class.getName() + " > Login");	
		IndexPageAction idxpage = new IndexPageAction(webdriver);
		idxpage.login();
		
		CommUtil.logger.info(Case82_2.class.getName() + " > CreateStdPO_CA");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("ProjectCode", ProjectCode);
		InputObj.put("PartNum", ProjectPart1);		
		InputObj.put("Qty", QTY);		
		InputObj.put("OrdNum", "");		
		CommonAction CA = new CommonAction(webdriver);
		HashMap<String, String> retObj = CA.CreateStdPO_CA(InputObj);
		retVal = retObj.get("RetVal").toString();
		String PONum = retObj.get("PONum").toString();	

		CommUtil.logger.info(Case82_2.class.getName() + " > Search and delete PO");	
		InputObj = new HashMap<String, String>();
		InputObj.put("SearchProjectCode", ProjectCode);
		InputObj.put("SearchCustomerPO", PONum);		
		String isFound = CA.SearchPO_CA(InputObj);
		if (isFound.equals("1")) {
			POMngtAction pomngtpage = new POMngtAction(webdriver);
			retVal = pomngtpage.DeletePO(InputObj);
			
		}
		
		CommUtil.logger.info(Case82_2.class.getName() + " > Verify can't receiving after PO deleted.");
		CommUtil.logger.info(Case82_2.class.getName() + " > MenuInventory");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();;
		
		CommUtil.logger.info(Case82_2.class.getName() + " > GotoDockReceiving");
		InvIndexAction invidxpage = new InvIndexAction(webdriver);
		invidxpage.GotoDockReceiving();

		CommUtil.logger.info(Case82_2.class.getName() + " > NewDocReceiving");
		DockReceivingAction dockRecvepage = new DockReceivingAction(webdriver);
		InputObj = new HashMap<String, String>();
		InputObj.put("TrackNum", TrackNum);
		InputObj.put("PONum", PONum);
		InputObj.put("Carrier", Carrier);
		InputObj.put("ProjectCode", ProjectCode);
		InputObj.put("BoxCnt",BoxCnt);
		InputObj.put("PalletCnt", PalletCnt);
		retVal = dockRecvepage.NewDocReceiving(InputObj);

		CommUtil.logger.info(Case82_2.class.getName() + " > Logout");	
		mainpage.logout();
		
		return retVal;
	}
}
