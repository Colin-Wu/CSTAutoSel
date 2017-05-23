package obj_repository.production;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProdIndexObj {
	WebDriver webdriver;
	By CmdRepairTechQueLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdRepairTechQue']");

	
	public ProdIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdRepairTechQue() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdRepairTechQueLocator);

		return retEle;
	}	
}
