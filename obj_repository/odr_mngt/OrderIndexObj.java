package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderIndexObj {
	
	WebDriver webdriver;
	By CmdPOManagementLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdPOManagement']");	
	
	public OrderIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdPOManagement() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdPOManagementLocator);

		return retEle;
	}	
	
}
