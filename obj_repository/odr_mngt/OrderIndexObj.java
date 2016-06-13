package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderIndexObj {
	
	public WebElement cmdPOManagement;
	
	public OrderIndexObj(WebDriver driver) {
		super();
		cmdPOManagement = driver.findElement(By.xpath(".//a[@id='ContentPlaceHolder1_cmdPOManagement']"));
	}

	public WebElement getCmdPOManagement() {
		return cmdPOManagement;
	}	
	
}
