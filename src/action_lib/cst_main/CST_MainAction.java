package action_lib.cst_main;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.cst_main.CST_MainObj;

public class CST_MainAction {
	
	WebDriver webdriver;
	
	public CST_MainAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}	
	
	public void logout () throws NoSuchElementException {
		
		CST_MainObj mainObj = new CST_MainObj(webdriver);
	
		WebElement CmdsignOut = mainObj.getCmdsignOut();

		CmdsignOut.click();
		
	}
	public void MenuShipping () throws NoSuchElementException {
		
		CST_MainObj mainObj = new CST_MainObj(webdriver);
	
		WebElement CmdShipping = mainObj.getCmdShipping();

		CmdShipping.click();
		
	}	
	public void MenuInventory () throws NoSuchElementException {
		
		CST_MainObj mainObj = new CST_MainObj(webdriver);
	
		WebElement CmdInvManagement = mainObj.getCmdInvManagement();

		CmdInvManagement.click();
		
	}	
	public void MenuOrdMngt () throws NoSuchElementException {
		
		CST_MainObj mainObj = new CST_MainObj(webdriver);
	
		WebElement cmdOrderAdmin = mainObj.getCmdOrderAdmin();

		cmdOrderAdmin.click();
		
	}
}
