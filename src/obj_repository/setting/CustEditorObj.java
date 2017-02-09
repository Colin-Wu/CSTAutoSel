package obj_repository.setting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustEditorObj {
	WebDriver webdriver;

	By TxtCountryCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerCountry']");
	By TxtPostCodelocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerZip']");

	By TxtSiteNamelocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSiteName']");

	By LblCountryCodeMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_revCountryCode']");	
	By LblPostCodeMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_regCustomerZip']");	

	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdSearchUser']");		

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvCustomerSite']/tbody");	
	
	By linkSelLocator = By.xpath(".//a[text()='Select']");

	public CustEditorObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLinkSelect(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@href='javascript:__doPostBack('ctl00$ContentPlaceHolder1$grvCustomerSite','Select$" + Integer.toString(idx) + "')']"));

		return retEle;
	}
	public WebElement getTxtSiteName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSiteNamelocator);

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
	public WebElement getLblPostCodeMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblPostCodeMessageLocator);

		return retEle;
	}
	public WebElement getLblCountryCodeMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblCountryCodeMessageLocator);

		return retEle;
	}
	public WebElement getTxtCountryCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCountryCodeLocator);

		return retEle;
	}
	public WebElement getTxtPostCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPostCodelocator);

		return retEle;
	}
	public By getTxtCountryCodeLocator() {
		return TxtCountryCodeLocator;
	}
	public By getTxtPostCodelocator() {
		return TxtPostCodelocator;
	}
	public By getLblCountryCodeMessageLocator() {
		return LblCountryCodeMessageLocator;
	}
	public By getLblPostCodeMessageLocator() {
		return LblPostCodeMessageLocator;
	}
	public By getTxtSiteNamelocator() {
		return TxtSiteNamelocator;
	}
	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getLinkSelLocator() {
		return linkSelLocator;
	}
}
