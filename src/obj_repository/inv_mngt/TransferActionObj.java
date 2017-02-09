package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TransferActionObj {
	WebDriver webdriver;

	By SelStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlTransferStatus']");

	By TxtBoxIDLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBoxID']");
	By TxtPalletIDLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPalletID']");

	By BtnSearchLocator = By.xpath(".//input[@id='btnSearch']");	
	
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gvTransferSurvey']/tbody");
	



	public TransferActionObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLinkSelect(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_gvTransferSurvey_lbtnSelect_" + Integer.toString(idx) + "']"));

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

	public WebElement getSelStatus() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelStatusLocator);

		return retEle;
	}
	public WebElement getTxtBoxID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxIDLocator);

		return retEle;
	}
	public WebElement getTxtPalletID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPalletIDLocator);

		return retEle;
	}


	public By getSelStatusLocator() {
		return SelStatusLocator;
	}


	public By getTxtBoxIDLocator() {
		return TxtBoxIDLocator;
	}


	public By getTxtPalletIDLocator() {
		return TxtPalletIDLocator;
	}


	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}


	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
}
