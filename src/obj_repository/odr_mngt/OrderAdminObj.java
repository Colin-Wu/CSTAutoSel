package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderAdminObj {
	WebDriver webdriver;

	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtOrderNumberlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrderNumber']");
	By TxtPONumberlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPONumber']");

	By SelStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStatus']");

	By BtnSearchLocator = By.xpath(".//input[@id='cmdSearchOrder']");		
	By BtnNewOrderLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdAddNew']");	
	By BtnEditOrderConfirmYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogOrder_cmdYes']");	

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvOrderViews']/tbody");	

	By linkEditLocator = By.xpath(".//a[text()='Edit']");
	By linkViewLocator = By.xpath(".//a[text()='View']");

	
	public OrderAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnEditOrderConfirmYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnEditOrderConfirmYesLocator);

		return retEle;
	}
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	public WebElement getTxtProjectCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtProjectCodeLocator);

		return retEle;
	}	
	public WebElement getTxtOrderNumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtOrderNumberlocator);

		return retEle;
	}	
	public WebElement getTxtPONumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPONumberlocator);

		return retEle;
	}	
	public WebElement getSelStatus() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelStatusLocator);

		return retEle;
	}	
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}	
	public WebElement getBtnNewOrder() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNewOrderLocator);

		return retEle;
	}

	public By getLinkEditLocator() {
		return linkEditLocator;
	}
	public By getTxtProjectCodeLocator() {
		return TxtProjectCodeLocator;
	}
	public By getTxtOrderNumberlocator() {
		return TxtOrderNumberlocator;
	}
	public By getTxtPONumberlocator() {
		return TxtPONumberlocator;
	}
	public By getSelStatusLocator() {
		return SelStatusLocator;
	}
	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	public By getBtnNewOrderLocator() {
		return BtnNewOrderLocator;
	}
	public By getBtnEditOrderConfirmYesLocator() {
		return BtnEditOrderConfirmYesLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getLinkViewLocator() {
		return linkViewLocator;
	}
}
