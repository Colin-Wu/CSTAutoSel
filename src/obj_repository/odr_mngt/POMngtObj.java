package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class POMngtObj {
	WebDriver webdriver;
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectID']");
	By TxtCustomerPOlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerPO']");
	
	By BtnSearchPOLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']");	
	By BtnNewPOLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnAdd']");	
	By BtnConfirmYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogQtyAvailable_cmdYes']");	
	
	By TblSearchResultLocator = By.xpath(".//table[@id='tablePartList']/tbody");	
	By linkDelLocator = By.xpath(".//a[text()='Delete']");
	By LblNoResultLocator = By.xpath(".//div[@id='divNoResult']");	
	
	By LblMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	
	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	

	
	public POMngtObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLblNoResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblNoResultLocator);

		return retEle;
	}
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}
	public WebElement getBtnConfirmYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmYesLocator);

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

	public WebElement getBtnSearchPO() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(BtnSearchPOLocator);

		return retEle;
	}
	public WebElement getBtnNewPO() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(BtnNewPOLocator);

		return retEle;
	}
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}	
	public WebElement getLblMessage() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(LblMessageLocator);

		return retEle;
	}


	public By getTxtProjectCodeLocator() {
		return TxtProjectCodeLocator;
	}


	public By getTxtCustomerPOlocator() {
		return TxtCustomerPOlocator;
	}


	public By getBtnSearchPOLocator() {
		return BtnSearchPOLocator;
	}


	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}


	public By getLblMessageLocator() {
		return LblMessageLocator;
	}


	public By getLinkDelLocator() {
		return linkDelLocator;
	}


	public By getBtnNewPOLocator() {
		return BtnNewPOLocator;
	}

	public By getBtnConfirmYesLocator() {
		return BtnConfirmYesLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
	public By getLblNoResultLocator() {
		return LblNoResultLocator;
	}
}
