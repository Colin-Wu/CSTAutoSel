package obj_repository.shipping;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShippingObj {
	WebDriver webdriver;

	By TxtContactnameLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtContactname']");
	By TxtContactPhoneLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtContactPhone']");
	By TxtAddress5Locator = By.xpath(".//input[@id='ContentPlaceHolder1_txtAddress5']");

	By BtnAddTrackingLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnAddTracking']");		
	By BtnShipLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnShip']");		

	By ChkComplete0Locator = By.xpath(".//input[@id='ContentPlaceHolder1_ckbInstructionComplete']");
	By ChkComplete1Locator = By.xpath(".//input[@id='ContentPlaceHolder1_ckbInstructionComplete1']");
	By ChkComplete2Locator = By.xpath(".//input[@id='ContentPlaceHolder1_ckbInstructionComplete2']");
	By ChkComplete3Locator = By.xpath(".//input[@id='ContentPlaceHolder1_ckbInstructionComplete3']");

	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	
	By LblTrackingSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblTrackingSuccess']");	

	By TblTrackingLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gvwOrderTracking']/tbody");	

	public ShippingObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getTblTracking() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblTrackingLocator);

		return retEle;
	}
	public WebElement getLblTrackingSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblTrackingSuccessMessageLocator);

		return retEle;
	}	
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}
	
	public WebElement getBtnShip() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnShipLocator);

		return retEle;
	}
	public WebElement getChkComplete3() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkComplete3Locator);

		return retEle;
	}
	public WebElement getChkComplete2() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkComplete2Locator);

		return retEle;
	}
	public WebElement getChkComplete1() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkComplete1Locator);

		return retEle;
	}
	public WebElement getChkComplete0() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkComplete0Locator);

		return retEle;
	}
	public WebElement getLinkSave(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_gvwOrderTracking_lbtnSave_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getTxtTrackingNumber(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvwOrderTracking_txtTrackingNumber_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	
	public WebElement getBtnAddTracking() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnAddTrackingLocator);

		return retEle;
	}
	public WebElement getTxtAddress5() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAddress5Locator);

		return retEle;
	}
	public WebElement getTxtContactPhone() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtContactPhoneLocator);

		return retEle;
	}
	public WebElement getTxtContactname() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtContactnameLocator);

		return retEle;
	}
	public By getTxtContactnameLocator() {
		return TxtContactnameLocator;
	}
	public By getTxtContactPhoneLocator() {
		return TxtContactPhoneLocator;
	}
	public By getTxtAddress5Locator() {
		return TxtAddress5Locator;
	}
	public By getBtnAddTrackingLocator() {
		return BtnAddTrackingLocator;
	}
	public By getBtnShipLocator() {
		return BtnShipLocator;
	}
	public By getChkComplete0Locator() {
		return ChkComplete0Locator;
	}
	public By getChkComplete1Locator() {
		return ChkComplete1Locator;
	}
	public By getChkComplete2Locator() {
		return ChkComplete2Locator;
	}
	public By getChkComplete3Locator() {
		return ChkComplete3Locator;
	}

	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
	public By getLblTrackingSuccessMessageLocator() {
		return LblTrackingSuccessMessageLocator;
	}
	public By getTblTrackingLocator() {
		return TblTrackingLocator;
	}

}
