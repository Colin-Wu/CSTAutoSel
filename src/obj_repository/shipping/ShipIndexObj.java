package obj_repository.shipping;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShipIndexObj {
	WebDriver webdriver;
	By CmdKittingLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hylPreship']");
	By CmdShippingLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hlkShipping']");

	
	public ShipIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdKitting() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdKittingLocator);

		return retEle;
	}	
	
	public WebElement getCmdShipping() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdShippingLocator);

		return retEle;
	}	
}
