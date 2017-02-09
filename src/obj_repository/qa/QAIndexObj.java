package obj_repository.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class QAIndexObj {
	WebDriver webdriver;
	By CmdQADeploymentLocator = By.xpath(".//a[@id='ContentPlaceHolder1_hlkQA']");

	
	public QAIndexObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	

	public WebElement getCmdQADeployment() throws NoSuchElementException  {
		WebElement retEle = null;

       	retEle = webdriver.findElement(CmdQADeploymentLocator);

		return retEle;
	}	
}
