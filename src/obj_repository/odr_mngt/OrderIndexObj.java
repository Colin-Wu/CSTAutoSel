package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderIndexObj {
	
	WebDriver webdriver;
	By CmdPOManagementLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdPOManagement']");
	By CmdOrderAdminLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdOrderManagement']");
	By CmdOrderUpdateLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdOrderUpdate']");
	By CmdTemplateAdminLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hplTemplateAdmin']");
	
	public OrderIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdPOManagement() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdPOManagementLocator);

		return retEle;
	}	
	
	public WebElement getCmdOrderAdmin() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdOrderAdminLocator);

		return retEle;
	}	
	
	public WebElement getCmdOrderUpdate() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdOrderUpdateLocator);

		return retEle;
	}	
	
	public WebElement getCmdTemplateAdmin() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdTemplateAdminLocator);

		return retEle;
	}	
	
}
