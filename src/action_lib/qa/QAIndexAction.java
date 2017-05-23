package action_lib.qa;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.qa.QAIndexObj;

public class QAIndexAction {
	WebDriver webdriver;
	
	public QAIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GoToQADeployment () throws NoSuchElementException {
		
		QAIndexObj QAIdxObj = new QAIndexObj(webdriver);
	
		WebElement CmdQADeployment = QAIdxObj.getCmdQADeployment();

		CmdQADeployment.click();
		
	}
}
