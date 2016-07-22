package action_lib.inv_mngt;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.inv_mngt.InvIndexObj;


public class InvIndexAction {
	WebDriver webdriver;
	
	public InvIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GotoDockReceiving () throws NoSuchElementException {
		
		InvIndexObj invIdxObj = new InvIndexObj(webdriver);
	
		WebElement CmdDocReceiving = invIdxObj.getCmdDocReceivingLocator();

		CmdDocReceiving.click();
		
	}
}
