package obj_repository.shipping;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PreshipObj {
	WebDriver webdriver;

	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtOrderNumberlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrderNumber']");	
	By TxtBoxIDlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBoxID']");

	By SelStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStatus']");

	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']");		
	By BtnSendtoShippingLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSendtoShipping']");		
	By BtnReturntoStockLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnReturntoStock']");		

	By TblSearchResultLocator = By.xpath(".//div[@id='ContentPlaceHolder1_pnlResult']/table/tbody");	

	By LblErrorMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	
	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	

	
	public PreshipObj(WebDriver driver) {
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
	public WebElement getBtnReturntoStock() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnReturntoStockLocator);

		return retEle;
	}
	public WebElement getBtnSendtoShipping() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSendtoShippingLocator);

		return retEle;
	}

	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	public WebElement getSelStatus() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelStatusLocator);

		return retEle;
	}		
	public WebElement getTxtBoxID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxIDlocator);

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
	public WebElement getLinkEdit(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ctl00_ContentPlaceHolder1_rptOrder_ctl0" + Integer.toString(idx) + "_lbtnVerify']"));

		return retEle;
	}
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}

	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}

	public By getBtnSendtoShippingLocator() {
		return BtnSendtoShippingLocator;
	}

	public By getLblErrorMessageLocator() {
		return LblErrorMessageLocator;
	}

	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}

	public By getBtnReturntoStockLocator() {
		return BtnReturntoStockLocator;
	}


}
