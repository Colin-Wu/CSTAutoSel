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
	By CmdShippingLocator = By.xpath(".//input[@id='cmdShipping']");	
	By cmdProductionLocator = By.xpath(".//input[@id='cmdProduction']");	
	By cmdQALocator = By.xpath(".//input[@id='cmdQA']");	
	By cmdSettingLocator = By.xpath(".//input[@id='cmdSystemAdmin']");	
	
	public CST_MainObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getCmdSetting () throws NoSuchElementException {
		
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdSettingLocator);

		return retEle;
	}	
	public WebElement getCmdQA () throws NoSuchElementException {
		
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdQALocator);

		return retEle;
	}	
	public WebElement getCmdsignOut () throws NoSuchElementException {
		
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdsignOutLocator);

		return retEle;
	}
	public WebElement getCmdProduction() throws NoSuchElementException {

		WebElement retEle = null;
		
       	retEle = webdriver.findElement(cmdProductionLocator);

		return retEle;
	}
	public WebElement getCmdShipping() throws NoSuchElementException {

		WebElement retEle = null;
		
       	retEle = webdriver.findElement(CmdShippingLocator);

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
