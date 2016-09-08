package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderEntry4Obj {
	WebDriver webdriver;
	
	By TxtAreaCmtLocator = By.xpath(".//textarea[@id='ContentPlaceHolder1_txtComments']");
	By TxtAreaReportFailureLocator = By.xpath(".//textarea[@id='ContentPlaceHolder1_txtReported']");

	By BtnActivateLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdActivate']");	
	By BtnConfirmActYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogOrder_cmdYes']");	

	
	public OrderEntry4Obj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnConfirmActYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmActYesLocator);

		return retEle;
	}	
	public WebElement getBtnActivate() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnActivateLocator);

		return retEle;
	}
	public WebElement getTxtAreaReportFailure() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAreaReportFailureLocator);

		return retEle;
	}
	
	public WebElement getTxtAreaCmt() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAreaCmtLocator);

		return retEle;
	}

	public By getTxtAreaCmtLocator() {
		return TxtAreaCmtLocator;
	}

	public By getTxtAreaReportFailureLocator() {
		return TxtAreaReportFailureLocator;
	}

	public By getBtnActivateLocator() {
		return BtnActivateLocator;
	}
	public By getBtnConfirmActYesLocator() {
		return BtnConfirmActYesLocator;
	}
}
