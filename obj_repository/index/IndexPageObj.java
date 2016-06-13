package obj_repository.index;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPageObj {
		
	public WebElement TxtUserName;
	public WebElement TxtUserPassword;
	public WebElement BtnLogin;
	
	public IndexPageObj(WebDriver driver) {
		super();
		//TxtUserName = driver.findElement(By.id("txtUsername"));

		TxtUserName = driver.findElement(By.xpath(".//input[@id='txtUsername']"));
		TxtUserPassword = driver.findElement(By.xpath(".//input[@id='txtPassword']"));
		BtnLogin = driver.findElement(By.xpath(".//input[@name='cmdLogin']"));
	}	
	
	
	public WebElement getTxtUserName() {
		return TxtUserName;
	}
	public WebElement getTxtUserPassword() {
		return TxtUserPassword;
	}
	public WebElement getBtnLogin() {
		return BtnLogin;
	}

}
