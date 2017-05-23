package obj_repository.production;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RepairDetailObj {
	WebDriver webdriver;

	By SelRepairCodeLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlRepairCode']");
	By SelDispostionCodeLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlDispostionCode']");

	By BtnRepairCompleteLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdRepairComplete']");		
	By BtnShipToVendorLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdShipToVendor']");		
	By BtnConfirmNoLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogForRepairDetail_cmdNo']");		
	By BtnConfirmYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogForRepairDetail_cmdYes']");		

	By LblMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_confirmDialogForRepairDetail_lbMessage']");	

	public RepairDetailObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnShipToVendor() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnShipToVendorLocator);

		return retEle;
	}
	public WebElement getBtnConfirmYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmYesLocator);

		return retEle;
	}
	public WebElement getBtnConfirmNo() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmNoLocator);

		return retEle;
	}
	public WebElement getLblMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblMessageLocator);

		return retEle;
	}
	public WebElement getBtnRepairComplete() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnRepairCompleteLocator);

		return retEle;
	}
	public WebElement getSelRepairCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelRepairCodeLocator);

		return retEle;
	}
	public WebElement getSelDispostionCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelDispostionCodeLocator);

		return retEle;
	}
	public By getSelRepairCodeLocator() {
		return SelRepairCodeLocator;
	}
	public By getSelDispostionCodeLocator() {
		return SelDispostionCodeLocator;
	}
	public By getBtnRepairCompleteLocator() {
		return BtnRepairCompleteLocator;
	}
	public By getBtnConfirmNoLocator() {
		return BtnConfirmNoLocator;
	}
	public By getLblMessageLocator() {
		return LblMessageLocator;
	}
}
