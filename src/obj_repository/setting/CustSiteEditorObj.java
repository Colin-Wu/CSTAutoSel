package obj_repository.setting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustSiteEditorObj {
	WebDriver webdriver;

	By TxtCountryCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCountry']");
	By TxtPostCodelocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtZip']");


	By LblCountryCodeMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_revCountryCode']");	
	By LblPostCodeMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_regCustomerZip']");	


	public CustSiteEditorObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
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
}
