package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InventoryLookupObj {
	
	WebDriver webdriver;
	By TxtPartNumberLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSKU']");
	By TxtSerialLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSerial']");
	By TxtPOLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPO']");
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By CmbStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStatusFilter']");
	By CmbStockGroupLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStockFilter']");
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvInventory']");
	
	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdSearchInv']");
	
	public InventoryLookupObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}

	public WebElement getTxtPartNumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPartNumberLocator);

		return retEle;
	}
	public WebElement getTxtSerial() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSerialLocator);

		return retEle;
	}
	public WebElement getTxtPONumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPOLocator);

		return retEle;
	}
	public WebElement getTxtProjectCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtProjectCodeLocator);

		return retEle;
	}
	
	public Select getCmbStatus() throws NoSuchElementException  {
		Select CmbStatus = null;
		
		CmbStatus = new Select(webdriver.findElement(CmbStatusLocator)); 
		
		return CmbStatus;
	}
	
	public Select getStockGroup() throws NoSuchElementException  {
		Select CmbStockGroup = null;
		
		CmbStockGroup = new Select(webdriver.findElement(CmbStockGroupLocator)); 
		
		return CmbStockGroup;
	}
	
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}
	
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	
}
