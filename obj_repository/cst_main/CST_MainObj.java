package obj_repository.cst_main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CST_MainObj {
	//Sign out Menu
	public WebElement CmdsignOut;
	//Order Management Menu
	public WebElement cmdOrderAdmin;
	//Inventory Management Menu
	public WebElement cmdInvManagement;
	
	
	public CST_MainObj(WebDriver driver) {
		super();
		CmdsignOut = driver.findElement(By.xpath(".//input[@id='cmdsignOut']"));
		cmdOrderAdmin = driver.findElement(By.xpath(".//input[@id='cmdOrderAdmin']"));
		cmdInvManagement = driver.findElement(By.xpath(".//input[@id='cmdInvManagement']"));
	}
	
	public WebElement getCmdsignOut() {
		return CmdsignOut;
	}

	public WebElement getCmdOrderAdmin() {
		return cmdOrderAdmin;
	}

	public WebElement getCmdInvManagement() {
		return cmdInvManagement;
	}
}
