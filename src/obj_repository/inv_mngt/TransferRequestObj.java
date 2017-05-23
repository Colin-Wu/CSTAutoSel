package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TransferRequestObj {
	WebDriver webdriver;
	
	By RdoTransTypeLocationLocator = By.xpath(".//input[@type='radio'][@value='Location']");
	By RdoTransTypeProjectLocator = By.xpath(".//input[@type='radio'][@value='Project']");
	
	By SelTransByLocator = By.xpath(".//select[@id='ddlType']");
	By SelFromProjLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlSourceProject']");
	By SelToProjLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlTargetProject']");

	By TxtAreaCmtLocator = By.xpath(".//textarea[@id='ContentPlaceHolder1_txtCommnets']");

	By TxtCustomerLocator = By.xpath(".//input[@id='txtCustomer']");

	By BtnNextLocator = By.xpath(".//input[@id='btnNext']");	
	
	public TransferRequestObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnNext() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNextLocator);

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
	public WebElement getSelFromProj() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelFromProjLocator);

		return retEle;
	}
	public WebElement getSelToProj() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelToProjLocator);

		return retEle;
	}
	public WebElement getTxtAreaCmt() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAreaCmtLocator);

		return retEle;
	}
	public WebElement getTxtCustomer() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCustomerLocator);

		return retEle;
	}
}
