package action_lib.odr_mngt;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import obj_repository.odr_mngt.OrderIndexObj;

public class OrderIndexAction {

	WebDriver webdriver;
	
	public OrderIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GotoPOMngt () throws NoSuchElementException {
		
		OrderIndexObj ordIdxObj = new OrderIndexObj(webdriver);
	
		WebElement cmdPOManagement = ordIdxObj.getCmdPOManagement();

		cmdPOManagement.click();
		
	}
}
