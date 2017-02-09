package obj_repository.setting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LocationEditorObj {
	WebDriver webdriver;

	By SelLocationTypeLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlLocationType']");
	By SelStockRoomLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStockRoom']");
	
	By TxtLocationNameLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtLocationName']");
	By TxtRowLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtRow']");
	By TxtRackLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtRack']");
	By TxtShelfLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtShelf']");
	By TxtBinLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBin']");

	By BtnSaveLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdApplyChanges']");	
	
	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	
	By LblErrorMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	
	
	public LocationEditorObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLblErrorMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblErrorMessageLocator);

		return retEle;
	}
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}
	public WebElement getBtnSave() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}
	public WebElement getTxtBin() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBinLocator);

		return retEle;
	}
	public WebElement getTxtShelf() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtShelfLocator);

		return retEle;
	}
	public WebElement getTxtRack() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtRackLocator);

		return retEle;
	}
	public WebElement getTxtRow() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtRowLocator);

		return retEle;
	}
	public WebElement getSelStockRoom() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelStockRoomLocator);

		return retEle;
	}
	public WebElement getTxtLocationName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtLocationNameLocator);

		return retEle;
	}
	public WebElement getSelLocationType() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelLocationTypeLocator);

		return retEle;
	}
	public By getSelLocationTypeLocator() {
		return SelLocationTypeLocator;
	}
	public By getSelStockRoomLocator() {
		return SelStockRoomLocator;
	}
	public By getTxtLocationNameLocator() {
		return TxtLocationNameLocator;
	}
	public By getTxtRowLocator() {
		return TxtRowLocator;
	}
	public By getTxtRackLocator() {
		return TxtRackLocator;
	}
	public By getTxtShelfLocator() {
		return TxtShelfLocator;
	}
	public By getTxtBinLocator() {
		return TxtBinLocator;
	}
	public By getBtnSaveLocator() {
		return BtnSaveLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
	public By getLblErrorMessageLocator() {
		return LblErrorMessageLocator;
	}
}
