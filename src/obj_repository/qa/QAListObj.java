package obj_repository.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class QAListObj {
	WebDriver webdriver;

	By txtOrderIDLocator = By.xpath(".//input[@id='txtOrderID']");

	By PnlWaitTitleLocator = By.xpath(".//div[@class='panel-title'][text()='Please wait...']");
	By PnlConfirmTitleLocator = By.xpath(".//div[@class='panel-title'][text()='Confirm']");

	
	//By BtnOKLocator = By.xpath("//span[@class='l-btn-text'][text()='Ok']/parent::span/parent::a/parent::div/parent::div[contains(@style,'display: block')]/div[@class='dialog-button messager-button']");
	By BtnOKLocator = By.xpath("//div[contains(@style,'display: block')]/div[@class='dialog-button messager-button']/a/span/span[@class='l-btn-text'][text()='Ok']/parent::span/parent::a");
	By BtnInitialQALocator = By.xpath(".//input[@id='cmdInitialQA']");	

	By TblSearchResultLocator = By.xpath(".//div[@id='dgpanel']/div/div/div/div[@class='datagrid-view2']/div[@class='datagrid-body']/table/tbody");	

	By MsgRstLocator = By.xpath("//div[contains(@style,'display: block')]/div[@class='messager-body panel-body panel-body-noborder window-body']");

	
	public QAListObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getRow(WebElement tbl, int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		//retEle = webdriver.findElement(By.xpath(".//div[@id='dgpanel']/div/div/div/div[@class='datagrid-view2']/div[@class='datagrid-body']/table/tbody/tr[@id='datagrid-row-r1-2-" + Integer.toString(idx) + "']"));
		retEle = tbl.findElement(By.xpath(".//tr[@id='datagrid-row-r1-2-" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getTxtBoxid(WebElement webEle) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webEle.findElement(By.xpath(".//td[@field='BoxID']/div/table/tbody/tr/td/input"));

		return retEle;
	}
	public WebElement getTxtManufacturingLableSN(WebElement webEle) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webEle.findElement(By.xpath(".//td[@field='ManufacturingLableSN']/div/table/tbody/tr/td/input"));

		return retEle;
	}
	public WebElement getMsgRst() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(MsgRstLocator);

		return retEle;
	}
	public WebElement getBtnInitialQA() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnInitialQALocator);

		return retEle;
	}
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	public WebElement getTxtOrderID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(txtOrderIDLocator);

		return retEle;
	}
	public WebElement getPnlConfirmTitle() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(PnlConfirmTitleLocator);

		return retEle;
	}
	public WebElement getPnlWaitTitle() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(PnlWaitTitleLocator);

		return retEle;
	}
	public WebElement getBtnOK() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnOKLocator);

		return retEle;
	}


	public By getTxtOrderIDLocator() {
		return txtOrderIDLocator;
	}

	public By getPnlWaitTitleLocator() {
		return PnlWaitTitleLocator;
	}

	public By getBtnOKLocator() {
		return BtnOKLocator;
	}


	public By getPnlConfirmTitleLocator() {
		return PnlConfirmTitleLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getBtnInitialQALocator() {
		return BtnInitialQALocator;
	}
}
