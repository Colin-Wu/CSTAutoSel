package obj_repository.inv_mngt;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TransferAdminObj {
	WebDriver webdriver;
	
	By RdoTransTypeLocationLocator = By.xpath(".//input[@type='radio'][@value='Location']");
	By RdoTransTypeProjectLocator = By.xpath(".//input[@type='radio'][@value='Project']");
	
	By SelTransByLocator = By.xpath(".//select[@id='ddlType']");
	By SelStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStatus']");

	By TxtUserNameLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtUserName']");
	By TxtTransferNoLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtTransfer']");

	By BtnSearchLocator = By.xpath(".//input[@id='btnSearch']");	
	
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gvLines']/tbody");
	
	By LblBoxidLocator = By.xpath(".//span[starts-with(@id, 'ContentPlaceHolder1_gvLines_rptTransferLine_')][contains(@id,'_lblBoxID_')]");
	
	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	


	public TransferAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}
	public WebElement getLinkSave(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_gvLines_lbtnSave_" + idx + "']"));

		return retEle;
	}
	public WebElement getSelUsername(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//select[@id='ContentPlaceHolder1_gvLines_ddlUserName_" + idx + "']"));

		return retEle;
	}
	public WebElement getLblTransferNumber(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//span[@id='ContentPlaceHolder1_gvLines_lblTransferNumber_" + idx + "']"));

		return retEle;
	}
	public List<WebElement> getLblBoxIDs() throws NoSuchElementException  {
		List<WebElement> retEle = null;

		retEle = webdriver.findElements(LblBoxidLocator);

		return retEle;
	}
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}
	public WebElement getRdoTransTypeLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(RdoTransTypeLocationLocator);

		return retEle;
	}
	public WebElement getRdoTransTypeProject() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(RdoTransTypeProjectLocator);

		return retEle;
	}
	public WebElement getSelTransBy() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelTransByLocator);

		return retEle;
	}
	public WebElement getSelStatus() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelStatusLocator);

		return retEle;
	}
	public WebElement getTxtUserName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtUserNameLocator);

		return retEle;
	}
	public WebElement getTxtTransferNo() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtTransferNoLocator);

		return retEle;
	}
	public By getRdoTransTypeLocationLocator() {
		return RdoTransTypeLocationLocator;
	}
	public By getRdoTransTypeProjectLocator() {
		return RdoTransTypeProjectLocator;
	}
	public By getSelTransByLocator() {
		return SelTransByLocator;
	}
	public By getSelStatusLocator() {
		return SelStatusLocator;
	}
	public By getTxtUserNameLocator() {
		return TxtUserNameLocator;
	}
	public By getTxtTransferNoLocator() {
		return TxtTransferNoLocator;
	}
	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getLblBoxidLocator() {
		return LblBoxidLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}

}
