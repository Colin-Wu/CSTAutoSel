package obj_repository.setting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SettingIndexObj {
	WebDriver webdriver;
	By CmdCustomerAdminLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hplCustomerAdmin']");
	By CmdLocationAdminLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hplLocationAdmin']");

	
	public SettingIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdCustomerAdmin() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdCustomerAdminLocator);

		return retEle;
	}	
	public WebElement getCmdLocationAdmin() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdLocationAdminLocator);

		return retEle;
	}	
}
