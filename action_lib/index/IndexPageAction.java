package action_lib.index;

import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.index.IndexPageObj;

public class IndexPageAction {
	public static void login () {
		
		IndexPageObj idxPgObj = new IndexPageObj(TestSetting.driver);

		
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
