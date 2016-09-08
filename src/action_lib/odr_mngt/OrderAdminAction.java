package action_lib.odr_mngt;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.OrderAdminObj;


public class OrderAdminAction {
	WebDriver webdriver;

	public OrderAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void NewOrder () throws NoSuchElementException {
		
		OrderAdminObj ordAdminObj = new OrderAdminObj(webdriver);
	
		WebElement BtnNewOrder = ordAdminObj.getBtnNewOrder();

		BtnNewOrder.click();
		
	}	
}
