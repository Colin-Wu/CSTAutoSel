package obj_repository.production;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EvalQuoteObj {
	WebDriver webdriver;

	By BtnAddNewLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdAddNew']");		
	By BtnGotoRepairDetailLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdGotoRepairDetail']");		

	By TblOrdLineLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvOrderLine']/tbody");	
	
	By LblErrorMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	
	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	

	By BtnConfirmYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogNewQuote_cmdYes']");	
	By BtnConfirmNoLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogNewQuote_cmdNo']");	

	public EvalQuoteObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
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
	public WebElement getLinkSave(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_grvOrderLine_lkbSave_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getSelBillable(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//select[@id='ContentPlaceHolder1_grvOrderLine_ddlBillable_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getSelType(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//select[@id='ContentPlaceHolder1_grvOrderLine_ddlBillingCode_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public By getSelTypeLocator(int idx) {
		return By.xpath(".//select[@id='ContentPlaceHolder1_grvOrderLine_ddlBillingCode_" + Integer.toString(idx) + "']");
	}	
	public WebElement getTxtPartNum(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_grvOrderLine_txtPartNumber_" + Integer.toString(idx) + "_txtTextBox_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTxtQty(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_grvOrderLine_txtRequiredQTY_" + Integer.toString(idx) + "_txtTextBox_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTblOrdLine() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblOrdLineLocator);

		return retEle;
	}
	public WebElement getBtnAddNew() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnAddNewLocator);

		return retEle;
	}
	public WebElement getBtnGotoRepairDetail() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnGotoRepairDetailLocator);

		return retEle;
	}
	public By getLblErrorMessageLocator() {
		return LblErrorMessageLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
	public By getBtnConfirmNoLocator() {
		return BtnConfirmNoLocator;
	}


}
