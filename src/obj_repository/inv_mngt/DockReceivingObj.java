package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DockReceivingObj {
	
	WebDriver webdriver;

	By BtnNewDockRcvLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnNewDockRcv']");	
	By BtnSaveLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSave']");	
	By BtnSearchLocator = By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']");	

	By TxtTrackingNumberLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtTrackingNumber_0']");
	By TxtPONumberLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtPONumber_0']");
	By TxtCarrierLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtCarrier_0']");
	By TxtBoxCountLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtBoxCount_0']");
	By TxtPalletCountLocator = By.xpath(".//input[@id='ContentPlaceHolder1_rptNew_txtPalletCount_0']");

	By TxtSearchProjectCodeLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectCode']");
	By TxtSearchPONumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPONumber']");
	By TxtSearchTrackNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtTrackingNumber']");

	By TblSearchResultLocator = By.xpath(".//table[@id='ContentPlaceHolder1_gv_Entities']/tbody");	
	By linkEditDetailLocator = By.xpath(".//a[text()='Edit Details']");
	
	
	By SelProjectCodeLocator = By.xpath(".//select[@id='ContentPlaceHolder1_rptNew_ddlCustomerProject_0']");

	//By LblSuccessMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccessNew']");	
	By LblSuccessMessageLocator = By.xpath(".//span[starts-with(@id, 'ContentPlaceHolder1_lblSuccess')]");	
	By LblErrMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	
	//By LblSuccessMessageSaveLineLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblSuccess']");	

	By TxtTranckNumLocator =  By.xpath(".//input[@id='ContentPlaceHolder1_txtTrackingNumber']");
	
	By BtnEditDetailsLocator =  By.xpath(".//a[@id='ContentPlaceHolder1_gv_Entities_lbtnEditDetails_0']");
	
	 By txtPartNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_txtPartNumberForSearch']");
     By txtQtyLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_txtSQty']");
	 By CmbDetailReceiveDispositionLocator =By.xpath(".//select[@id='ContentPlaceHolder1_ucDetailReceiving_ddlDispostion']");
	 By BtnCreatePalletIDLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_btnCreatePalletID']");
	 By BtnGoLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_btnGo']");
		
	 By BtnYesDialogBoxLocator =  By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_ccConfirmDialogBack_cmdYes']");
	 By TblDetailReceivingINVPartsLocator = By.xpath(".//div[@id='ContentPlaceHolder1_ucDetailReceiving_divINVParts']/table/tbody");
	 By BtnSaveDetailReceivingLocator =By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_btnSave']"); 
	 By LblSuccessMessageDetailReceivingLocator = By.xpath(".//span[@id='ContentPlaceHolder1_ucDetailReceiving_lblSuccess']");	
	 By txtPalletIdLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_txtPalletID']");

	 By BtnNextLocator =  By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_btnNext']");

	 By BtnYesDialogBoxConfirmPOLocator =  By.xpath(".//input[@id='ContentPlaceHolder1_ccConfirmDialogPO_cmdYes']");
	 
	public DockReceivingObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
/*	public WebElement getLblSuccessMessageSaveLine() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(LblSuccessMessageSaveLineLocator);
		return retEle;
	}*/
	public WebElement getBtnYesDialogBoxConfirmPO() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(BtnYesDialogBoxConfirmPOLocator);
		return retEle;
	}
	public WebElement getSelProjectCode(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//select[@id='ContentPlaceHolder1_gv_Entities_ddlCustomerProject_" + idx + "']"));

		return retEle;
	}	
	public WebElement getTxtPalletCount(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gv_Entities_txtPalletCount_" + idx + "']"));

		return retEle;
	}	
	public WebElement getTxtBoxCount(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gv_Entities_txtBoxCount_" + idx + "']"));

		return retEle;
	}	
	public WebElement getTxtCarrier(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gv_Entities_txtCarrier_" + idx + "']"));

		return retEle;
	}	
	public WebElement getTxtPONumber(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gv_Entities_txtPONumber_" + idx + "']"));

		return retEle;
	}	
	public WebElement getTxtTrackingNumber(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_gv_Entities_txtTrackingNumber_" + idx + "']"));

		return retEle;
	}	
	public WebElement getLinkSaveLine(String idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_gv_Entities_lbtnSaveLine_" + idx + "']"));

		return retEle;
	}
	public WebElement getLinkEditLine(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_gv_Entities_lbtnEditLine_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getBtnNext() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(BtnNextLocator);
		return retEle;
	}	
	public WebElement gettxtPalletId() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(txtPalletIdLocator);
		return retEle;
	}
	
	public WebElement getLblSuccessDetailReceivingMessage() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(LblSuccessMessageDetailReceivingLocator);
		return retEle;
	}
	
	public WebElement getTblDetailReceivingINVParts() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(TblDetailReceivingINVPartsLocator);
		return retEle;
	}
	
	public WebElement getUniqueSerialNum(int index)throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_ucDetailReceiving_rptINVParts_txtSerialNumber_"+index+"']"));
		return retEle;
	}
	
	public WebElement getBtnSaveDetailReceiving() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(BtnSaveDetailReceivingLocator);
		return retEle;
	}
	
	public WebElement getBtnYesDialogBox() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(BtnYesDialogBoxLocator);
		return retEle;
	}
	
	public WebElement getBtnGo() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(BtnGoLocator);
		return retEle;
	}
	
	public WebElement getBtnCreatePalletID() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnCreatePalletIDLocator);

		return retEle;
	}
	
	public WebElement getCmbDetailReceiveDisposition() throws NoSuchElementException  {
		WebElement retEle = null;
		retEle = webdriver.findElement(CmbDetailReceiveDispositionLocator);
		return retEle;
	}
	
	public WebElement getTxtQty() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(txtQtyLocator);

		return retEle;
	}
	
	public WebElement getTxtPartNum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(txtPartNumLocator);

		return retEle;
	}
	
	public WebElement getBtnEditDetials() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnEditDetailsLocator);

		return retEle;
	}
	
	public WebElement getTxtTrackNum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtTranckNumLocator);

		return retEle;
	}
	
	public WebElement getLblErrMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblErrMessageLocator);

		return retEle;
	}
	
	public WebElement getTxtSearchProjectCode() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchProjectCodeLocator);

		return retEle;
	}
	public WebElement getTxtSearchPONum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchPONumLocator);

		return retEle;
	}
	public WebElement getTxtSearchTrackNum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchTrackNumLocator);

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
	public WebElement getLblSuccessMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblSuccessMessageLocator);

		return retEle;
	}
	public WebElement getBtnSave() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnSaveLocator);

		return retEle;
	}	
	public WebElement getTxtPalletCount() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPalletCountLocator);

		return retEle;
	}
	public WebElement getTxtBoxCount() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtBoxCountLocator);

		return retEle;
	}
	public Select getSelProjectCode() throws NoSuchElementException  {
		Select retEle = null;

		retEle = new Select(webdriver.findElement(SelProjectCodeLocator));

		return retEle;
	}
	public WebElement getTxtCarrier() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtCarrierLocator);

		return retEle;
	}	
	public WebElement getTxtPONumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPONumberLocator);

		return retEle;
	}	
	public WebElement getBtnNewDockRcv() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNewDockRcvLocator);

		return retEle;
	}	
	public WebElement getTxtTrackingNumber() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtTrackingNumberLocator);

		return retEle;
	}
	public By getBtnNewDockRcvLocator() {
		return BtnNewDockRcvLocator;
	}
	public By getBtnSaveLocator() {
		return BtnSaveLocator;
	}
	public By getTxtTrackingNumberLocator() {
		return TxtTrackingNumberLocator;
	}
	public By getTxtPONumberLocator() {
		return TxtPONumberLocator;
	}
	public By getTxtCarrierLocator() {
		return TxtCarrierLocator;
	}
	public By getTxtBoxCountLocator() {
		return TxtBoxCountLocator;
	}
	public By getTxtPalletCountLocator() {
		return TxtPalletCountLocator;
	}
	public By getSelProjectCodeLocator() {
		return SelProjectCodeLocator;
	}
	public By getLblSuccessMessageLocator() {
		return LblSuccessMessageLocator;
	}
	
	public By getLblSuccessMessageDetailReceivingLocator() {
		return LblSuccessMessageDetailReceivingLocator;
	}
	public By getBtnSearchLocator() {
		return BtnSearchLocator;
	}
	public By getTxtSearchProjectCodeLocator() {
		return TxtSearchProjectCodeLocator;
	}
	public By getTxtSearchPONumLocator() {
		return TxtSearchPONumLocator;
	}
	public By getTxtSearchTrackNumLocator() {
		return TxtSearchTrackNumLocator;
	}
	public By getTblSearchResultLocator() {
		return TblSearchResultLocator;
	}
	public By getLinkEditDetailLocator() {
		return linkEditDetailLocator;
	}
	public By getLblErrMessageLocator() {
		return LblErrMessageLocator;
	}
	public By getBtnNextLocator() {
		return BtnNextLocator;
	}
	public By getBtnYesDialogBoxConfirmPOLocator() {
		return BtnYesDialogBoxConfirmPOLocator;
	}
/*	public By getLblSuccessMessageSaveLineLocator() {
		return LblSuccessMessageSaveLineLocator;
	}	*/
}
