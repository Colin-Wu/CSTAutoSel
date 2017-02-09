package action_lib.setting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.setting.SettingIndexObj;

public class SettingIndexAction {
	WebDriver webdriver;
	
	public SettingIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GotoCustAdmin () throws NoSuchElementException {
		
		SettingIndexObj Obj = new SettingIndexObj(webdriver);
	
		WebElement CmdCustomerAdmin = Obj.getCmdCustomerAdmin();

		CmdCustomerAdmin.click();
		
	}
	public void GotoLocationAdmin () throws NoSuchElementException {
		
		SettingIndexObj Obj = new SettingIndexObj(webdriver);
	
		WebElement CmdLocationAdmin = Obj.getCmdLocationAdmin();

		CmdLocationAdmin.click();
		
	}
}
