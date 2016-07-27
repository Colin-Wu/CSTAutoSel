package action_lib.index;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.index.IndexPageObj;

public class IndexPageAction {


	WebDriver webdriver;
	
	public IndexPageAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void login () throws NoSuchElementException {
		
		IndexPageObj idxPgObj = new IndexPageObj(webdriver);

		
		WebElement TxtUserName = idxPgObj.getTxtUserName();
		WebElement TxtUserPassword = idxPgObj.getTxtUserPassword();
		WebElement BtnLogin = idxPgObj.getBtnLogin();
		
		TxtUserName.clear();
		TxtUserName.sendKeys(TestSetting.UserName);

		TxtUserPassword.clear();
		TxtUserPassword.sendKeys(TestSetting.UserPassword);

		BtnLogin.click();
		
	}
}
