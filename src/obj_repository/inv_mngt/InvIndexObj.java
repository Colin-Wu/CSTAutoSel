package obj_repository.inv_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InvIndexObj {
	WebDriver webdriver;
	By cmdDocReceivingLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdDocReceiving']");
	By cmdReceivingPutawayLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdReceivingPutaway']");
	By cmdInventoryLookupLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hplInventoryLookUp']");
	By cmdPickAdminLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdPickAdmin']");
	By cmdPickQueueLocator = By.xpath(".//a[@id='ContentPlaceHolder1_cmdPickQue']");
	By cmdTransferRequestLocator = By.xpath(".//a[@id='ContentPlaceHolder1_HyperLink1']");
	By cmdTransferAdminLocator = By.xpath(".//a[@id='ContentPlaceHolder1_HyperLink2']");
	By cmdTransferActionLocator = By.xpath(".//a[@id='ContentPlaceHolder1_HyperLink3']");
	
	public InvIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdDocReceivingLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdDocReceivingLocator);

		return retEle;
	}	
	
	
	public WebElement getCmdReceivingPutawayLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdReceivingPutawayLocator);

		return retEle;
	}	
	
	
	public WebElement getCmdInventoryLookupLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdInventoryLookupLocator);

		return retEle;
	}	
	
	public WebElement getCmdPickAdminLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdPickAdminLocator);

		return retEle;
	}	
	
	public WebElement getCmdPickQueueLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdPickQueueLocator);

		return retEle;
	}	
	
	public WebElement getCmdTransferRequestLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdTransferRequestLocator);

		return retEle;
	}	
	
	public WebElement getCmdTransferAdminLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdTransferAdminLocator);

		return retEle;
	}	
	
	public WebElement getCmdTransferActionLocator() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(cmdTransferActionLocator);

		return retEle;
	}	
	
}
