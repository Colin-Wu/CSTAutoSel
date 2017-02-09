package obj_repository.setting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LocationAdminObj {
	WebDriver webdriver;
	
	By BtnNewLocationLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdNewLocation']");	


	
	public LocationAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	public WebElement getBtnNewLocation() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNewLocationLocator);

		return retEle;
	}
}
