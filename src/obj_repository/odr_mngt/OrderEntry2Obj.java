package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderEntry2Obj {
	WebDriver webdriver;

	By TxtSearchPartNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPartNumber']");
	By TxtExpectedSNLocator = By.xpath(".//input[@id='ContentPlaceHolder1_grvRepair_txtSN_0']");

	By BtnFindLocator = By.xpath(".//input[@id='cmdFind']");	
	By BtnNextLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdNext']");	
	
	By LinkAddlocator = By.xpath(".//a[@id='ContentPlaceHolder1_grvRepair_lkbAdd_0']");

	By TblAddListLocator = By.xpath(".//table[contains(@id,'ContentPlaceHolder1_grv')][contains(@id,'Views')]/tbody");
	
	By LblMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	


	public OrderEntry2Obj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLblMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblMessageLocator);

		return retEle;
	}
	public WebElement getBtnNext() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNextLocator);

		return retEle;
	}
	public WebElement getTblAddList() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblAddListLocator);

		return retEle;
	}
	public WebElement getLinkAdd() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LinkAddlocator);

		return retEle;
	}
	public WebElement getTxtExpectedSN() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtExpectedSNLocator);

		return retEle;
	}	
	public WebElement getTxtSearchPartNum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchPartNumLocator);

		return retEle;
	}
	public WebElement getBtnFind() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnFindLocator);

		return retEle;
	}
	public By getTxtSearchPartNumLocator() {
		return TxtSearchPartNumLocator;
	}
	public By getTxtExpectedSNLocator() {
		return TxtExpectedSNLocator;
	}
	public By getBtnFindLocator() {
		return BtnFindLocator;
	}
	public By getLinkAddlocator() {
		return LinkAddlocator;
	}
	public By getTblAddListLocator() {
		return TblAddListLocator;
	}
	public By getBtnNextLocator() {
		return BtnNextLocator;
	}
	public By getLblMessageLocator() {
		return LblMessageLocator;
	}

}
