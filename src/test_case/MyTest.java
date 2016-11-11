package test_case;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.inv_mngt.PickAdminAction;
import action_lib.inv_mngt.PickEditAction;
import action_lib.inv_mngt.PickQueueAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;
import script_lib.CommUtil;

public class MyTest {
	
	public static String run (WebDriver webdriver) throws Exception {
		 CST_MainAction mainpage = new CST_MainAction(webdriver);
		 mainpage.MenuInventory();
		
		 /// Pick Queue Start check
		/* InvIndexAction obj = new InvIndexAction(webdriver);
		 obj.GotoPickQueue();
		
		 PickQueueAction pickAction= new PickQueueAction(webdriver);
		
		 HashMap<String, String> InputObj = new HashMap<String, String>();
		
		 InputObj.put("ProjectCode", "BIAH");
		 InputObj.put("OrdNum", "107434");
		 InputObj.put("UserName", "");
		 String outIsFound = pickAction.SearchPickQueue(InputObj);
		 CommUtil.logger.info(" > PickQueue Rec Found : "+outIsFound);
		 
		 if(outIsFound.equals("1"))
		 {
		    String found = pickAction.PickQueueGoToPick("107434");
		    CommUtil.logger.info(" > Found : "+found);
		 }*/
		 /// Pick Queue End check
		 
		 /// Pick Admin Start
		/* InvIndexAction invIndex = new InvIndexAction(webdriver);
		 invIndex.GotoPickAdmin();
		 HashMap<String, String> InputObj = new HashMap<String, String>();
			
		 InputObj.put("ProjectCode", "BIAH");
		 InputObj.put("OrdNum", "107434");
		 InputObj.put("UserName", "SPrabhu");
		 
		 PickAdminAction pickAdminAction= new PickAdminAction(webdriver);
		 String returnVal= pickAdminAction.SearchPickAdmin(InputObj);
		
		 if(returnVal.equals("1"))
		 {
			String retVal= pickAdminAction.SavePickAdmin(InputObj); 
		 }*/
		 /// Pick Admin End
		 
		 
		 //String retVal = "-1";
		
		/* InvIndexAction obj = new InvIndexAction(webdriver);
		 obj.GotoPickQueue();
		
		 PickQueueAction pickAction= new PickQueueAction(webdriver);
		
		 HashMap<String, String> InputObj = new HashMap<String, String>();
		
		 InputObj.put("ProjectCode", "BIAH");
		 InputObj.put("OrdNum", "107526");
		 InputObj.put("UserName", "SPrabhu");
		 String outIsFound = pickAction.SearchPickQueue(InputObj);
		 CommUtil.logger.info(" > PickQueue Rec Found : "+outIsFound);
		 
		 if(outIsFound.equals("1"))
		 {
		    String found = pickAction.PickQueueGoToPick("107526");
		    CommUtil.logger.info(" > Found : "+found);
		    
		    PickEditAction pickEditAction = new PickEditAction(webdriver);
		    HashMap<String, String> PickEditInputObj = new HashMap<String, String>();
		    PickEditInputObj.put("BoxID", "9900010334");
		    PickEditInputObj.put("Qty","1");
		    String result= pickEditAction.SavePick(PickEditInputObj);
		    CommUtil.logger.info(" > Result : "+result);
		 }*/
			
		  /// Pick Queue End check
		  
		   /*********************************************************************************/
			CommUtil.logger.info(" > GetAvailablePart_CA");	
			HashMap<String, String> InputObj = new HashMap<String, String>();
			InputObj.put("Qty", "1");
			//InputObj.put("SearchCustomerPO", TestSetting.StandardPONum);		
			CommonAction CA = new CommonAction(webdriver);
			HashMap<String, String> OutPutObj= CA.GetAvailablePart_CA(InputObj);
				
			if (OutPutObj != null) {
				CommUtil.logger.info(" > SN : " + OutPutObj.get("SN").toString());
				CommUtil.logger.info(" > Box ID :" + OutPutObj.get("BoxID").toString());
			}
		   /*********************************************************************************/
			
		   ///////////////////////////////////////////////////////////////////////////////////////
		 /* CommUtil.logger.info(" > ReceiveToPutaway_CA");	
			HashMap<String, String> InputReceivePutObj = new HashMap<String, String>();
			InputReceivePutObj.put("PONum", TestSetting.StandardPONum);
			InputReceivePutObj.put("Carrier", "1");
			InputReceivePutObj.put("ProjectCode", TestSetting.Project);
			InputReceivePutObj.put("BoxCnt", "1");
			InputReceivePutObj.put("PalletCnt", "1");
			InputReceivePutObj.put("TrackNum", "Tytrseot2177"); // Change Track Number
			InputReceivePutObj.put("PartNum", TestSetting.ProjectPart1);
			InputReceivePutObj.put("Qty", "1");
			InputReceivePutObj.put("SN", "220170810911"); // // Change Serial Number
			InputReceivePutObj.put("BoxID", "");
			InputReceivePutObj.put("Location", TestSetting.StockLocation);
			InputReceivePutObj.put("Disposition", "Good");
			//InputObj.put("SearchCustomerPO", TestSetting.StandardPONum);		
			CommonAction CA = new CommonAction(webdriver);
			String OutPutReceivePutawayObj= CA.ReceiveToPutaway_CA(InputReceivePutObj);
			
		    CommUtil.logger.info(" > PartStatus : " +OutPutReceivePutawayObj);*/
		    
		    ///////////////////////////////////////////////////////////////////////////////////////
		    
		 
		   /********************************************************/
		  /*CommonAction CA = new CommonAction(webdriver);
		   CA.GenerateStandardPart_CA("1");*/
		   /********************************************************/
	       return "0";
	}
}
