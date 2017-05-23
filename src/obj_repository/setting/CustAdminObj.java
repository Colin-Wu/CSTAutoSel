package obj_repository.setting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustAdminObj {
	WebDriver webdriver;

	By TxtCustomerCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerCode']");
	By TxtCustomerNamelocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerName']");

	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdSearchUser']");		

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvCustomer']/tbody");	

	
	public CustAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLinkSelect(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_grvCustomer_lbTest_" + Integer.toString(idx) + "']"));

		return retEle;
	}


	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	public WebElement getTxtCustomerCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCustomerCodeLocator);

		return retEle;
	}
	public WebElement getTxtCustomerName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCustomerNamelocator);

		return retEle;
	}
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}

	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getTxtCustomerCodeLocator() {
		return TxtCustomerCodeLocator;
	}
	public By getTxtCustomerNamelocator() {
		return TxtCustomerNamelocator;
	}


}
