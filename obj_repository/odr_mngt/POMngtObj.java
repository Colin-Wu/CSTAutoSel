package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class POMngtObj {
	public WebElement txtProjectCode;
	public WebElement txtCustomerPO;
	public WebElement btnSearchPO;
	
	public POMngtObj(WebDriver driver) {
		super();
		txtProjectCode = driver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectID']"));
		txtCustomerPO = driver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_txtCustomerPO']"));
		btnSearchPO = driver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_btnSearch']"));
	}

	public WebElement getTxtProjectCode() {
		return txtProjectCode;
	}

	public WebElement getTxtCustomerPO() {
		return txtCustomerPO;
	}

	public WebElement getBtnSearchPO() {
		return btnSearchPO;
	}
	
}
