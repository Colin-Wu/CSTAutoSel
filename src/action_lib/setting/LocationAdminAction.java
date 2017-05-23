package action_lib.setting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.setting.LocationAdminObj;

public class LocationAdminAction {
	WebDriver webdriver;

	public LocationAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void NewLocation () throws NoSuchElementException {
		
		LocationAdminObj Obj = new LocationAdminObj(webdriver);
	
		WebElement BtnNewLocation = Obj.getBtnNewLocation();

		BtnNewLocation.click();
		
	}	
}
