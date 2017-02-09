package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderUpdateObj {
	WebDriver webdriver;

	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProject']");
	By TxtOrderNumberlocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtOrderNumber']");

	By BtnSearchLocator = By.xpath(".//input[@id='btnSearch']");		

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvOrderUpdate']/tbody");	

	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	
	
	public OrderUpdateObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

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


	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}	


	public WebElement getLinkEdit(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_grvOrderUpdate_lbtnEdit_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getSelStatus(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//select[@id='ContentPlaceHolder1_grvOrderUpdate_ddlStatus_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getTxtPO(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_grvOrderUpdate_txtRMANumber_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getLinkSave(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_grvOrderUpdate_lbtnSave_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
}
