package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PickQueueObj {

WebDriver webdriver;
	
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProject']");
	By TxtOrderNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrder']");
	By TxtUserNameLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtUserName']");
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gv_Entities']/tbody");
	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']");
	By linkPickDetailLocator = By.xpath(".//a[starts-with(@id, 'ContentPlaceHolder1_gv_Entities_btnPick_')]");
	
	public PickQueueObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
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
	
	public WebElement getTblSearchResultLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	
	public WebElement getBtnSearchLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);
		return retEle;
	}
	public By getLinkPickDetailLocator() {
		return linkPickDetailLocator;
	}
}
