package obj_repository.production;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RepairTechQueueObj {
	WebDriver webdriver;

	By TxtDisplayNamelocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtDisplayName']");
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtOrderNumberlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrderNumber']");
	By TxtBoxIDlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBoxID']");
	By TxtSNlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSerialNumber']");


	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']");		

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvRepairTechQue']/tbody");	

	By linkGoLocator = By.xpath(".//a[text()='Go']");

	
	public RepairTechQueueObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}

	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	public WebElement getTxtSN() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSNlocator);

		return retEle;
	}	
	public WebElement getTxtBoxID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxIDlocator);

		return retEle;
	}	
	public WebElement getTxtProjectCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtProjectCodeLocator);

		return retEle;
	}	
	public WebElement getTxtOrderNumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtOrderNumberlocator);

		return retEle;
	}	
	public WebElement getTxtDisplayName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtDisplayNamelocator);

		return retEle;
	}	
	public WebElement getlinkGo() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(linkGoLocator);

		return retEle;
	}	
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}

	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}

	public By getLinkGoLocator() {
		return linkGoLocator;
	}

	public By getTxtDisplayNamelocator() {
		return TxtDisplayNamelocator;
	}

	public By getTxtProjectCodeLocator() {
		return TxtProjectCodeLocator;
	}

	public By getTxtOrderNumberlocator() {
		return TxtOrderNumberlocator;
	}

	public By getTxtBoxIDlocator() {
		return TxtBoxIDlocator;
	}

	public By getTxtSNlocator() {
		return TxtSNlocator;
	}

	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}	
}
