package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ReceivingPutawayObj {
	WebDriver webdriver;
	By TxtSerialNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSN']");
	By TxtBoxIdLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBoxID']");
	By TxtProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtPalletIdLocator = By.xpath(".//input[@id='txtPalletID']");
	By CmbStatusLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlStatus']");
	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_grvRecPutaways']/tbody");
	
	By BtnSearchLocator = By.xpath(".//input[@id='cmdSearchPutaway']");
	
	By BtnFinishLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdFinish']");
	By LblPutAwaySuccessMessageReadyLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");
	
	By BtnSaveLocator = By.xpath(".//a[@id='ContentPlaceHolder1_grvRecPutaways_lbtnSave_0']");
	By ChkDefaultLocationLocator = By.xpath(".//input[@id='ContentPlaceHolder1_grvRecPutaways_chbDefault_0']");
	
	By BtnSaveAllLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdSaveAll']");
	
	By TxtWareHouseLocationLocator =  By.xpath(".//input[@id='ContentPlaceHolder1_grvRecPutaways_txtLocNumber_0']");
	
	By LblErrorMessageLocator =  By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");

	By ImgLoadingLocator =  By.xpath(".//img[@src='/Images/loading.gif']");

	
	public ReceivingPutawayObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getImgLoading() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ImgLoadingLocator);

		return retEle;
	}	
	public WebElement getLblErrorMessageLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblErrorMessageLocator);

		return retEle;
	}
	
	public WebElement getTxtWareHouseLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtWareHouseLocationLocator);

		return retEle;
	}
	
	
	public WebElement getBtnSaveAllLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveAllLocator);

		return retEle;
	}
	
	public WebElement getChkDefaultLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkDefaultLocationLocator);

		return retEle;
	}
	
	public WebElement getBtnSave() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}
	
	public WebElement getTxtSerialNum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSerialNumLocator);

		return retEle;
	}
	
	public WebElement getTxtBoxId() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxIdLocator);

		return retEle;
	}
	
	public WebElement getTxtProjectCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtProjectCodeLocator);

		return retEle;
	}
	
	public WebElement getTxtPalletId() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPalletIdLocator);

		return retEle;
	}
	
	public Select getCmbStatus() throws NoSuchElementException  {
		Select CmbStatus = null;
		
		CmbStatus = new Select(webdriver.findElement(CmbStatusLocator)); 
		
		return CmbStatus;
	}
    
	public WebElement getBtnSearch() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSearchLocator);

		return retEle;
	}
	
	public WebElement getTblSearchResult() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSearchResultLocator);

		return retEle;
	}
	
	public WebElement getBtnFinish() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnFinishLocator);

		return retEle;
	}
	
	public WebElement getSuccessMessagePutAwayReady() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblPutAwaySuccessMessageReadyLocator);

		return retEle;
	}
	
	public By getLblPutAwayReadySuccessMessageLocator() {
		return LblPutAwaySuccessMessageReadyLocator;
	}	
	
	public By getBtnSaveAllLocator() {
		return BtnSaveAllLocator;
	}	
	
	public By getLblErrorMesgLocator() {
		return LblErrorMessageLocator;
	}	
	
	public String getBtnSaveAllId()
	{
		return "ContentPlaceHolder1_cmdSaveAll";
	}
	
	public String getLblSuccessId()
	{
		return "ContentPlaceHolder1_lblSuccess";
	}
	
	public String getLblErrorId()
	{
		return "ContentPlaceHolder1_lblError";
	}
	
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getImgLoadingLocator() {
		return ImgLoadingLocator;
	}	
}
