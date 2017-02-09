package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TemplateAdminObj {
	WebDriver webdriver;

	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtTemplateNamelocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtTemplateName']");

	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdSearchUser']");		

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvTemplate']/tbody");	
	
	By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	

	By BtnDeleteConfirmYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdDeleteConfirm']");	
	
	public TemplateAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getLinkDelect(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_grvTemplate_cmdDelete_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getBtnDeleteConfirmYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnDeleteConfirmYesLocator);

		return retEle;
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
	public WebElement getTxtTemplateName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtTemplateNamelocator);

		return retEle;
	}
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}
	public By getTxtProjectCodeLocator() {
		return TxtProjectCodeLocator;
	}
	public By getTxtTemplateNamelocator() {
		return TxtTemplateNamelocator;
	}
	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
	public By getBtnDeleteConfirmYesLocator() {
		return BtnDeleteConfirmYesLocator;
	}

}
