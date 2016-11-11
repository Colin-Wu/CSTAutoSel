package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PickEditObj {
	WebDriver webdriver;
	
	By TxtBoxIDLocator = By.xpath(".//input[starts-with(@id, 'ContentPlaceHolder1_gv_EntitiesConfirm_gv_InvItems_0_txtBoxID_')]");
	By TxtQtyLocator = By.xpath(".//input[starts-with(@id, 'ContentPlaceHolder1_gv_EntitiesConfirm_gv_InvItems_0_txtPickQTY_')]");
	
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gv_EntitiesConfirm_gv_InvItems_0']/tbody");
	By TblPickingAreaSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gv_EntitiesConfirm']/tbody");
	By BtnSaveLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSave']");
	By BtnYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogForPrintBox_cmdYes']");
	By LblSuccessLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");
	By ImageShowhideLocator = By.xpath(".//img[starts-with(@id, 'imgdiv')]");
	
	public PickEditObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	
	public WebElement getImageShowhideLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ImageShowhideLocator);

		return retEle;
	}
	
	public WebElement getLblSuccessLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessLocator);

		return retEle;
	}
	
	public WebElement getBtnYesLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnYesLocator);

		return retEle;
	}
	
	public WebElement getTblPickingAreaSearchResultLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblPickingAreaSearchResultLocator);

		return retEle;
	}
	
	public WebElement getTxtBoxIDLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxIDLocator);

		return retEle;
	}
	
	public WebElement getTxtQtyLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtQtyLocator);

		return retEle;
	}
	
	public WebElement getTblSearchResultLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	
	public WebElement getBtnSearchLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}
	public By getLblSuccessLocator() {
		return LblSuccessLocator;
	}
}
