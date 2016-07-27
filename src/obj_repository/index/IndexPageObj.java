package obj_repository.index;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPageObj {
		
	WebDriver webdriver;
	By TxtUserNameLocator = By.xpath(".//input[@id='txtUsername']");
	By TxtUserPasswordLocator = By.xpath(".//input[@id='txtPassword']");
	By BtnLoginLocator = By.xpath(".//input[@name='cmdLogin']");
	
	public IndexPageObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getTxtUserName()  throws NoSuchElementException {
		WebElement retEle = null;

       	retEle = webdriver.findElement(TxtUserNameLocator);

		return retEle;
	}
	public WebElement getTxtUserPassword()  throws NoSuchElementException {
		WebElement retEle = null;

       	retEle = webdriver.findElement(TxtUserPasswordLocator);

		return retEle;
	}
	public WebElement getBtnLogin()  throws NoSuchElementException {
		WebElement retEle = null;

       	retEle = webdriver.findElement(BtnLoginLocator);

		return retEle;
	}


	public By getTxtUserNameLocator() {
		return TxtUserNameLocator;
	}


	public By getTxtUserPasswordLocator() {
		return TxtUserPasswordLocator;
	}


	public By getBtnLoginLocator() {
		return BtnLoginLocator;
	}

}
