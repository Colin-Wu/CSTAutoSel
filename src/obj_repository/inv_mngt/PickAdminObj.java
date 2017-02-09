package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PickAdminObj {
	WebDriver webdriver;
	
	
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProject']");
	By TxtOrderNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrder']");
	By TxtUserNameLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtUserName']");
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvPickAdmin']/tbody");
	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']");
	
	By CmbAssignUserLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlPickUserForAll']");
	By linkSaveLinkLocator = By.xpath(".//a[starts-with(@id, 'ContentPlaceHolder1_grvPickAdmin_lbtnSave_')]");
	By CmbUserNameLocator = By.xpath(".//select[starts-with(@id, 'ContentPlaceHolder1_grvPickAdmin_ddlPickUser_')]");
	
	By YesBtnLocator = By.xpath(".//input[@id='ContentPlaceHolder1_saveConfirmDialog_cmdYes']");
	By LblSuccessLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");
	
	public PickAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	
	public WebElement getLblSuccessLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessLocator);

		return retEle;
	}
	
	public WebElement getAssignUserLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(CmbAssignUserLocator);

		return retEle;
	}
	
	public WebElement getYesBtnLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(YesBtnLocator);

		return retEle;
	}
	
	public WebElement getTxtProjectCodeLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtProjectCodeLocator);

		return retEle;
	}
	
	public WebElement getTxtOrderNumLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtOrderNumLocator);

		return retEle;
	}
	
	public WebElement getTxtUserNameLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtUserNameLocator);

		return retEle;
	}
	
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	
	public WebElement getBtnSearchLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);
		return retEle;
	}
	
	public By getSaveLinkLocator() {
		return linkSaveLinkLocator;
	}
	
	public By getYesBtnLocator() {
		return YesBtnLocator;
	}	
	
	public By getLblSuccessLocator() {
		return LblSuccessLocator;
	}
	public By getCmbUserNameLocator() {
		return CmbUserNameLocator;
	}

	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	
}
