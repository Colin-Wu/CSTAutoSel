package obj_repository.cst_main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.NoSuchElementException;

public class CST_MainObj {
	
	WebDriver webdriver;
	By CmdsignOutLocator = By.xpath(".//input[@id='cmdsignOut']");
	By CmdOrderAdminLocator = By.xpath(".//input[@id='cmdOrderAdmin']");
	By CmdInvManagementLocator = By.xpath(".//input[@id='cmdInvManagement']");	
	
	public CST_MainObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	
	public WebElement getCmdsignOut () throws NoSuchElementException {
		
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdsignOutLocator);

		return retEle;
	}

	public WebElement getCmdOrderAdmin() throws NoSuchElementException {

		WebElement retEle = null;
		
       	retEle = webdriver.findElement(CmdOrderAdminLocator);

		return retEle;
	}

	public WebElement getCmdInvManagement() throws NoSuchElementException {
		
		WebElement retEle = null;
		
       	retEle = webdriver.findElement(CmdInvManagementLocator);

		return retEle;
	}

	public By getCmdsignOutLocator() {
		return CmdsignOutLocator;
	}

	public By getCmdOrderAdminLocator() {
		return CmdOrderAdminLocator;
	}

	public By getCmdInvManagementLocator() {
		return CmdInvManagementLocator;
	}
}
