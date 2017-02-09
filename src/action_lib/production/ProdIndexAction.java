package action_lib.production;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.production.ProdIndexObj;

public class ProdIndexAction {
	WebDriver webdriver;
	
	public ProdIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GoToRepairTechQueue () throws NoSuchElementException {
		
		ProdIndexObj prodidxObj = new ProdIndexObj(webdriver);
	
		WebElement CmdRepairTechQue = prodidxObj.getCmdRepairTechQue();

		CmdRepairTechQue.click();
		
	}

}
