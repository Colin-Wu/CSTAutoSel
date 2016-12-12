package action_lib.shipping;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.shipping.ShipIndexObj;

public class ShipIndexAction {
	WebDriver webdriver;
	
	public ShipIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GoToKitting () throws NoSuchElementException {
		
		ShipIndexObj shipIdxObj = new ShipIndexObj(webdriver);
	
		WebElement cmdKitting = shipIdxObj.getCmdKitting();

		cmdKitting.click();
		
	}
	
	public void GoToShipping () throws NoSuchElementException {
		
		ShipIndexObj shipIdxObj = new ShipIndexObj(webdriver);
	
		WebElement cmdShipping = shipIdxObj.getCmdShipping();

		cmdShipping.click();
		
	}
}
