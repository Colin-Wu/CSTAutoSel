package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderAdminObj {
	WebDriver webdriver;

	By BtnNewOrderLocator = By.xpath(".//input[@id='ContentPlaceHolder1_cmdAddNew']");	

	
	public OrderAdminObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	
	public WebElement getBtnNewOrder() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(BtnNewOrderLocator);

		return retEle;
	}
}
