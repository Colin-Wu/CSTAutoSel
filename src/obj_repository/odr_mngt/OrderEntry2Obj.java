package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderEntry2Obj {
	WebDriver webdriver;

	By TxtSearchPartNumLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtPartNumber']");
	By TxtSearchSNLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtSN']");
	By TxtSearchBOMLocator = By.xpath(".//input[@id='ContentPlaceHolder1_txtBOMName']");
	By TxtExpectedSNLocator = By.xpath(".//input[@id='ContentPlaceHolder1_grvRepair_txtSN_0']");
	By TxtAvailableQTYLocator = By.xpath(".//input[@id='ContentPlaceHolder1_grvStardard_txtAvailableQTY_0']");
	By TxtReqQTYLocator = By.xpath(".//input[@id='ContentPlaceHolder1_grvStardard_txtQTY_0']");

	By BtnFindLocator = By.xpath(".//input[@id='cmdFind']");	
	By BtnNextLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdNext']");	
	
	By LinkRepairAddlocator = By.xpath(".//a[@id='ContentPlaceHolder1_grvRepair_lkbAdd_0']");
	By LinkStandardAddlocator = By.xpath(".//a[@id='ContentPlaceHolder1_grvStardard_lkbAdd_0']");

	By TblAddListLocator = By.xpath(".//table[contains(@id,'ContentPlaceHolder1_grv')][contains(@id,'Views')]/tbody");
	
	By LblMessageLocator = By.xpath(".//span[@id='ContentPlaceHolder1_lblError']");	

	By SelStockGroupLocator = By.xpath(".//select[@id='ContentPlaceHolder1_grvStardard_ddlStockGroups_0']");
	By SelReceiveStockGroupLocator = By.xpath(".//select[@id='ContentPlaceHolder1_grvRepair_ddlStockGroups_0']");

	public OrderEntry2Obj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getTxtReqQTY() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtReqQTYLocator);

		return retEle;
	}
	public WebElement getTxtAvailableQTY() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtAvailableQTYLocator);

		return retEle;
	}
	public WebElement getChkConfig(int idx) throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_grvStandardViews_ckbConfig_" + Integer.toString(idx) + "']"));

		return retEle;
	}
	public WebElement getSelReceiveStockGroup() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelReceiveStockGroupLocator);

		return retEle;
	}
	public WebElement getSelStockGroup() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(SelStockGroupLocator);

		return retEle;
	}
	public WebElement getTxtSearchBOM() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchBOMLocator);

		return retEle;
	}
	public WebElement getTxtSearchSN() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchSNLocator);

		return retEle;
	}
	public WebElement getLblMessage() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LblMessageLocator);

		return retEle;
	}
	public WebElement getBtnNext() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNextLocator);

		return retEle;
	}
	public WebElement getTblAddList() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblAddListLocator);

		return retEle;
	}
	public WebElement getLinkStandardAdd() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LinkStandardAddlocator);

		return retEle;
	}
	public WebElement getLinkRepairAdd() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(LinkRepairAddlocator);

		return retEle;
	}
	public WebElement getTxtExpectedSN() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtExpectedSNLocator);

		return retEle;
	}	
	public WebElement getTxtSearchPartNum() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TxtSearchPartNumLocator);

		return retEle;
	}
	public WebElement getBtnFind() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnFindLocator);

		return retEle;
	}
	public By getTxtSearchPartNumLocator() {
		return TxtSearchPartNumLocator;
	}
	public By getTxtExpectedSNLocator() {
		return TxtExpectedSNLocator;
	}
	public By getBtnFindLocator() {
		return BtnFindLocator;
	}
	public By getLinkAddlocator() {
		return LinkRepairAddlocator;
	}
	public By getTblAddListLocator() {
		return TblAddListLocator;
	}
	public By getBtnNextLocator() {
		return BtnNextLocator;
	}
	public By getLblMessageLocator() {
		return LblMessageLocator;
	}
	public By getLinkRepairAddlocator() {
		return LinkRepairAddlocator;
	}
	public By getLinkStandardAddlocator() {
		return LinkStandardAddlocator;
	}
	public By getTxtAvailableQTYLocator() {
		return TxtAvailableQTYLocator;
	}
	public By getTxtSearchSNLocator() {
		return TxtSearchSNLocator;
	}
	public By getTxtSearchBOMLocator() {
		return TxtSearchBOMLocator;
	}
	public By getSelStockGroupLocator() {
		return SelStockGroupLocator;
	}
	public By getSelReceiveStockGroupLocator() {
		return SelReceiveStockGroupLocator;
	}
	public By getTxtReqQTYLocator() {
		return TxtReqQTYLocator;
	}

}
