package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InvIndexObj {
	WebDriver webdriver;
	By cmdDocReceivingLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdDocReceiving']");	
	
	public InvIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdDocReceivingLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdDocReceivingLocator);

		return retEle;
	}	
}
