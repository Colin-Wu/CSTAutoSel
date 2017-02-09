package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TransferRequestForBoxObj {
	WebDriver webdriver;

	By BtnAddNewLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnAddNew']");		
	By BtnSaveLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSave']");	
	
	By LblMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	


	public TransferRequestForBoxObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLblMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblMessageLocator);

		return retEle;
	}
	public WebElement getBtnSave() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}
	public WebElement getBtnAddNew() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnAddNewLocator);

		return retEle;
	}
	public WebElement getTxtBoxid(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtBoxID_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTxtQtyTransfered(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtQtyTransfered_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTxtNewPallet(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtNewPallet_" + Integer.toString(idx) + "']"));

		return retEle;
	}	
	public WebElement getTxtNewLocation(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gvLines_txtNewLocation_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getNewStockGroup(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//select[@id='ContentPlaceHolder1_gvLines_ddlNewStockGroup_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public By getLblMessageLocator() {
		return LblMessageLocator;
	}
}
