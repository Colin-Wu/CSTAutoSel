package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TransferListObj {
	WebDriver webdriver;
	
	By BtnSaveLocator = By.xpath(".//input[@id='btnSave']");		

	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	

	public TransferListObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnSave() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}

	public WebElement getTxtScannedBoxID(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtScannedBoxID_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTxtScannedPalletID(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtScannedPalletID_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTxtNewLocationScan(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtNewLocationScan_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getTxtNewSGScan(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtNewSGScan_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public By getBtnSaveLocator() {
		return BtnSaveLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
}
