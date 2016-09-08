package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderEntry3Obj {
	WebDriver webdriver;
	
	By TxtAddr1Locator = By.xpath(".//input[@id='ContentPlaceHolder1_txtAddress1']");
	By TxtAddr5Locator = By.xpath(".//input[@id='ContentPlaceHolder1_txtAddress5']");
	By TxtCityLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCity']");
	By TxtStateLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtState']");
	By TxtCountryLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCountry']");
	By TxtPostcodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtZip']");

	By ChkStockLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ckbStock']");

	By BtnNextLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdNext']");	

	public OrderEntry3Obj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnNext() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNextLocator);

		return retEle;
	}
	public WebElement getChkStock() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkStockLocator);

		return retEle;
	}
	public WebElement getTxtPostcode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPostcodeLocator);

		return retEle;
	}
	public WebElement getTxtCountry() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCountryLocator);

		return retEle;
	}
	public WebElement getTxtState() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtStateLocator);

		return retEle;
	}
	public WebElement getTxtCity() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCityLocator);

		return retEle;
	}
	public WebElement getTxtAddr5() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAddr5Locator);

		return retEle;
	}
	public WebElement getTxtAddr1() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAddr1Locator);

		return retEle;
	}
	public By getTxtAddr1Locator() {
		return TxtAddr1Locator;
	}
	public By getTxtAddr5Locator() {
		return TxtAddr5Locator;
	}
	public By getTxtCityLocator() {
		return TxtCityLocator;
	}
	public By getTxtStateLocator() {
		return TxtStateLocator;
	}
	public By getTxtCountryLocator() {
		return TxtCountryLocator;
	}
	public By getTxtPostcodeLocator() {
		return TxtPostcodeLocator;
	}
	public By getChkStockLocator() {
		return ChkStockLocator;
	}
	public By getBtnNextLocator() {
		return BtnNextLocator;
	}
}
