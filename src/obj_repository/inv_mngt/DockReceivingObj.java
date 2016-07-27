package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DockReceivingObj {
	
	WebDriver webdriver;

	By BtnNewDockRcvLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnNewDockRcv']");	
	By BtnSaveLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSave']");	

	By TxtTrackingNumberLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtTrackingNumber_0']");
	By TxtPONumberLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtPONumber_0']");
	By TxtCarrierLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtCarrier_0']");
	By TxtBoxCountLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtBoxCount_0']");
	By TxtPalletCountLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtPalletCount_0']");

	By SelProjectCodeLocator = By.xpath(".//select[@id='ContentPlaceHolder1_rptNew_ddlCustomerProject_0']");

	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccessNew']");	

	public DockReceivingObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
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
	public WebElement getTxtPalletCount() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPalletCountLocator);

		return retEle;
	}
	public WebElement getTxtBoxCount() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxCountLocator);

		return retEle;
	}
	public Select getSelProjectCode() throws NoSuchElementException  {
		Select retEle = null;

		retEle = new Select(webdriver.findElement(SelProjectCodeLocator));

		return retEle;
	}
	public WebElement getTxtCarrier() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCarrierLocator);

		return retEle;
	}	
	public WebElement getTxtPONumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPONumberLocator);

		return retEle;
	}	
	public WebElement getBtnNewDockRcv() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNewDockRcvLocator);

		return retEle;
	}	
	public WebElement getTxtTrackingNumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtTrackingNumberLocator);

		return retEle;
	}
	public By getBtnNewDockRcvLocator() {
		return BtnNewDockRcvLocator;
	}
	public By getBtnSaveLocator() {
		return BtnSaveLocator;
	}
	public By getTxtTrackingNumberLocator() {
		return TxtTrackingNumberLocator;
	}
	public By getTxtPONumberLocator() {
		return TxtPONumberLocator;
	}
	public By getTxtCarrierLocator() {
		return TxtCarrierLocator;
	}
	public By getTxtBoxCountLocator() {
		return TxtBoxCountLocator;
	}
	public By getTxtPalletCountLocator() {
		return TxtPalletCountLocator;
	}
	public By getSelProjectCodeLocator() {
		return SelProjectCodeLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}	
}
