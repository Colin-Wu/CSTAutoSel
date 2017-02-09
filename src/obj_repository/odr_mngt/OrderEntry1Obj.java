package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderEntry1Obj {
	WebDriver webdriver;

	By SelCustomerLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlCustomers']");
	By SelProjectLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlProjects']");
	By SelOrderTypeLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlOrderTypes']");
	By SelOrderAttrLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlAttributes']");
	By SelSignatureLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlSignatures']");
	By SelProdLineLocator = By.xpath(".//select[@id='ContentPlaceHolder1_ddlProdLines']");

	By TxtSiteLocator = By.xpath(".//input[@id='txtSiteName']");
	By TxtQuickLookupLocator = By.xpath(".//input[@id='txtQuickLookUp']");
	By TxtReqShipDateLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtShipDate']");
	By TxtReqDeliveryDateLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtDeliveryDate']");
	By TxtReqEmailLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtRequestorEmail']");
	By TxtPOLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtRMANumber']");
	By TxtTmpltNameLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtTemplateName']");

	By LinkPOlocator = By.xpath(".//a[@id='ContentPlaceHolder1_btnRMA']");

	By BtnTodayLocator = By.xpath(".//input[@id='dpTodayInput']");	
	By BtnNextLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdNext']");	
	By BtnConfirmPOOKLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogRMA_cmdYes']");	
	By BtnConfirmPOCancelLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogRMA_cmdNo']");	
	By BtnCancelLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdCancelOrder']");	
	By BtnConfirmCancelYesLocator = By.xpath(".//input[@id='ContentPlaceHolder1_confirmDialogOrder_cmdYes']");	
	By BtnOKBoxRlsLocator = By.xpath(".//input[@id='cmdSure']");	

	By ChkTpltLocator = By.xpath(".//input[@id='ContentPlaceHolder1_ckbTemplate']");

	By FrameDateLocator = By.xpath(".//iframe");	
	By FrameBoxRlsLocator = By.xpath(".//iframe[@id='dialogwin']");	
	
	public OrderEntry1Obj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnOKBoxRls() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnOKBoxRlsLocator);

		return retEle;
	}
	public WebElement getFrameBoxRls() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(FrameBoxRlsLocator);

		return retEle;
	}
	public WebElement getBtnConfirmCancelYes() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmCancelYesLocator);

		return retEle;
	}
	public WebElement getBtnCancel() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnCancelLocator);

		return retEle;
	}
	public WebElement getBtnConfirmPOCancel() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmPOCancelLocator);

		return retEle;
	}
	public WebElement getBtnConfirmPOOK() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnConfirmPOOKLocator);

		return retEle;
	}
	public WebElement getBtnNext() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNextLocator);

		return retEle;
	}
	public WebElement getTxtTmpltName() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtTmpltNameLocator);

		return retEle;
	}
	public WebElement getSelProdLine() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelProdLineLocator);

		return retEle;
	}
	public WebElement getTxtPO() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtPOLocator);

		return retEle;
	}
	public WebElement getLinkPO() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LinkPOlocator);

		return retEle;
	}
	public WebElement getSelSignature() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelSignatureLocator);

		return retEle;
	}
	public WebElement getTxtReqEmail() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtReqEmailLocator);

		return retEle;
	}
	public WebElement getTxtReqDeliveryDate() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtReqDeliveryDateLocator);

		return retEle;
	}	
	public WebElement getFrameDate() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(FrameDateLocator);

		return retEle;
	}
	public WebElement getBtnToday() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnTodayLocator);

		return retEle;
	}	
	public WebElement getTxtReqShipDate() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtReqShipDateLocator);

		return retEle;
	}	
	public WebElement getTxtQuickLookup() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtQuickLookupLocator);

		return retEle;
	}	
	public WebElement getChkTplt() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(ChkTpltLocator);

		return retEle;
	}	

	public WebElement getSelOrderAttr() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelOrderAttrLocator);

		return retEle;
	}
	public WebElement getSelOrderType() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelOrderTypeLocator);

		return retEle;
	}

	public WebElement getTxtSite() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSiteLocator);

		return retEle;
	}	

	public WebElement getSelProject() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelProjectLocator);

		return retEle;
	}
	public WebElement getSelCustomer() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelCustomerLocator);

		return retEle;
	}
	public By getSelCustomerLocator() {
		return SelCustomerLocator;
	}
	public By getSelProjectLocator() {
		return SelProjectLocator;
	}
	public By getSelOrderTypeLocator() {
		return SelOrderTypeLocator;
	}
	public By getSelOrderAttrLocator() {
		return SelOrderAttrLocator;
	}
	public By getSelSignatureLocator() {
		return SelSignatureLocator;
	}
	public By getSelProdLineLocator() {
		return SelProdLineLocator;
	}
	public By getTxtSiteLocator() {
		return TxtSiteLocator;
	}
	public By getTxtQuickLookupLocator() {
		return TxtQuickLookupLocator;
	}
	public By getTxtReqShipDateLocator() {
		return TxtReqShipDateLocator;
	}
	public By getTxtReqDeliveryDateLocator() {
		return TxtReqDeliveryDateLocator;
	}
	public By getTxtReqEmailLocator() {
		return TxtReqEmailLocator;
	}
	public By getTxtPOLocator() {
		return TxtPOLocator;
	}
	public By getTxtTmpltNameLocator() {
		return TxtTmpltNameLocator;
	}
	public By getLinkPOlocator() {
		return LinkPOlocator;
	}
	public By getBtnTodayLocator() {
		return BtnTodayLocator;
	}
	public By getBtnNextLocator() {
		return BtnNextLocator;
	}
	public By getChkTpltLocator() {
		return ChkTpltLocator;
	}
	public By getFrameDateLocator() {
		return FrameDateLocator;
	}
	public By getBtnConfirmPOOKLocator() {
		return BtnConfirmPOOKLocator;
	}
	public By getBtnConfirmPOCancelLocator() {
		return BtnConfirmPOCancelLocator;
	}
	public By getBtnCancelLocator() {
		return BtnCancelLocator;
	}
	public By getFrameBoxRlsLocator() {
		return FrameBoxRlsLocator;
	}

}
