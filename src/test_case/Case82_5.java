package test_case;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.DockReceivingAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;
import script_lib.CommUtil;
import action_lib.inv_mngt.InventoryLookupAction;
import action_lib.inv_mngt.ReceivingPutawayAction;
public class Case82_5 {

	public static String run (WebDriver webdriver) throws Exception {
		
		String retVal = "-1";

		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		InvIndexAction obj = new InvIndexAction(webdriver);
		obj.GotoReceivingPutaway();
		
		ReceivingPutawayAction objReceivingPutawayObj = new ReceivingPutawayAction(webdriver);
		
		HashMap<String, String> InputObj = new HashMap<String, String>();
		
		 InputObj.put("Status", "PendPutaway");
		 InputObj.put("SN", "");
		 InputObj.put("ProjectCode", "");
		 InputObj.put("PalletID", "");
		 InputObj.put("BoxID", "88511");
		
		 objReceivingPutawayObj.SearchPutaway(InputObj);
		 CommUtil.logger.info(" > Calling SavetoPutawayready.... ");
		 
		/// objReceivingPutawayObj.SaveToPutaway();
		 
		 objReceivingPutawayObj.SaveToPutawayready("0000");
		/*HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("ProjectCode", "BIAH");
		InputObj.put("PartNum", "76000527");		
		InputObj.put("SearchSN", "V72024718");		
		InputObj.put("SearchPONum", "CRASH13-17ERRTN");	
		InputObj.put("SearchStatus", "PendPutaway");	
		InputObj.put("StockGroup", "BIAH - BIAH S1");	
		InventoryLookupAction ob = new InventoryLookupAction(webdriver);
		HashMap<String, String> retObj = ob.SearchInventory(InputObj);*/
		
		
		/// New test case
		
		/*CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		InvIndexAction obj = new InvIndexAction(webdriver);
		obj.GotoDockReceiving();
		
		DockReceivingAction Obj = new DockReceivingAction(webdriver);
		retVal=Obj.DocReceivingGoToDetail("96748");
		CommUtil.logger.info("> Return value : " + retVal);
		
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("ProjectCode", "BIAH");
		InputObj.put("Qty", "2");		
		InputObj.put("SN", "test16090101");		
		InputObj.put("Disposition", "Good");	
		
		HashMap<String, String> retObj = Obj.DetailReceiving(InputObj);*/
		
		return retVal;

	}
}
