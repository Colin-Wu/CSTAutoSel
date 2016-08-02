package action_lib.common_action;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import action_lib.cst_main.CST_MainAction;
import action_lib.odr_mngt.CreatePOAction;
import action_lib.odr_mngt.OrderIndexAction;
import action_lib.odr_mngt.POMngtAction;
import script_lib.CommUtil;



public class CommonAction {

	WebDriver webdriver;

	public CommonAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public HashMap<String, String> CreateStdPO_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoPOMngt");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoPOMngt();
			
		CommUtil.logger.info(" > NewPO");
		POMngtAction pomngtpage = new POMngtAction(webdriver);
		pomngtpage.NewPO();
		
		CommUtil.logger.info(" > CreateStandardPO");
		CreatePOAction createPOpage = new CreatePOAction(webdriver);
		HashMap<String, String> retObj = createPOpage.CreateStandardPO(InputObj);
		

		return retObj;
	}
	
	public String SearchPO_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoPOMngt");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoPOMngt();
			
		CommUtil.logger.info(" > SearchPO");
		POMngtAction pomngtpage = new POMngtAction(webdriver);
		isFound = pomngtpage.SearchPO(InputObj);
		
		return isFound;
	}
}
