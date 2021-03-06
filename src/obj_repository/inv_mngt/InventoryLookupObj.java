package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InventoryLookupObj {
	
	WebDriver webdriver;
	By TxtBoxIDLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBoxID']");

	By TxtPartNumberLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSKU']");
	By TxtSerialLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSerial']");
	By TxtPOLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPO']");
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtPalletLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPallet']");
	
	By CmbStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStatusFilter']");
	By CmbStockGroupLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStockFilter']");
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvInventory']/tbody");
	
	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdSearchInv']");
	
	public InventoryLookupObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getTxtBoxID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxIDLocator);

		return retEle;
	}
	public WebElement getTxtPallet() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPalletLocator);

		return retEle;
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
	public By getTxtBoxIDLocator() {
		return TxtBoxIDLocator;
	}
	public By getTxtPartNumberLocator() {
		return TxtPartNumberLocator;
	}
	public By getTxtSerialLocator() {
		return TxtSerialLocator;
	}
	public By getTxtPOLocator() {
		return TxtPOLocator;
	}
	public By getTxtProjectCodeLocator() {
		return TxtProjectCodeLocator;
	}
	public By getTxtPalletLocator() {
		return TxtPalletLocator;
	}
	public By getCmbStatusLocator() {
		return CmbStatusLocator;
	}
	public By getCmbStockGroupLocator() {
		return CmbStockGroupLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	
}
