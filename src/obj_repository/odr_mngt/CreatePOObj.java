package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreatePOObj {
	WebDriver webdriver;
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtCustomerPOlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerPO']");
	By TxtOrderNumlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrderNumber']");
	By TxtVendorlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtVendorName']");

	By LinkNewPOlocator = By.xpath(".//a[@id='ContentPlaceHolder1_lbtnCreateNewPONumber']");

	By TblPartListLocator = By.xpath(".//table[@id='tablePartList']");
	By TxtPartNumLocator = By.xpath(".//input[contains(@id,'ContentPlaceHolder1_rptPart_txtPartNumber_')]");
	By TxtQtyLocator = By.xpath(".//input[contains(@id,'ContentPlaceHolder1_rptPart_txtQTY_')]");

	By BtnSaveLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSave']");	
	By BtnConfirmYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ConfirmDialogForCancel_cmdYes']");	

	
	public CreatePOObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnConfirmYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmYesLocator);

		return retEle;
	}
	public WebElement getBtnSave() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}	
	public WebElement getTxtVendor() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtVendorlocator);

		return retEle;
	}	
	public WebElement getLinkNewPO() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LinkNewPOlocator);

		return retEle;
	}
	
	public WebElement getTxtProjectCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtProjectCodeLocator);

		return retEle;
	}

	public WebElement getTxtCustomerPO() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(TxtCustomerPOlocator);

		return retEle;
	}
	public WebElement getTxtOrderNum() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(TxtOrderNumlocator);

		return retEle;
	}
	public WebElement getTblPartList() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(TblPartListLocator);

		return retEle;
	}


	public By getTxtProjectCodeLocator() {
		return TxtProjectCodeLocator;
	}


	public By getTxtCustomerPOlocator() {
		return TxtCustomerPOlocator;
	}


	public By getTxtOrderNumlocator() {
		return TxtOrderNumlocator;
	}


	public By getTblPartListLocator() {
		return TblPartListLocator;
	}


	public By getTxtPartNumLocator() {
		return TxtPartNumLocator;
	}

	public By getLinkNewPOlocator() {
		return LinkNewPOlocator;
	}

	public By getTxtQtyLocator() {
		return TxtQtyLocator;
	}
	public By getBtnSaveLocator() {
		return BtnSaveLocator;
	}
	public By getBtnConfirmYesLocator() {
		return BtnConfirmYesLocator;
	}
}
