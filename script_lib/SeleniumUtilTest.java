package script_lib;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import action_lib.cst_main.CST_MainAction;
import action_lib.index.IndexPageAction;
import action_lib.odr_mngt.OrderIndexAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;

public class SeleniumUtilTest {

	@Test
	public void testGetTableRows() {
		
/*		TestSetting.initTest();
		IndexPageAction.login();
		
		CST_MainAction.MenuOrdMngt();
		
		OrderIndexAction.GotoPOMngt();
		
		HashMap<String, String> InputObj = new HashMap<String, String>();
		
		InputObj.put("SearchProjectCode", "UFT");
		InputObj.put("SearchCustomerPO", "");
		
		POMngtAction.SearchPO(InputObj);
		System.out.println("search");
		By locator = By.xpath(".//table[@id='tablePartList']");
		
		if (SeleniumUtil.isWebElementExist(locator)) {
			System.out.println("exist");
		}
		
		int row = SeleniumUtil.getTableRows(TestSetting.driver.findElement(By.xpath(".//table[@id='tablePartList']")));
		System.out.println(row);
		int col = SeleniumUtil.getTableCols(TestSetting.driver.findElement(By.xpath(".//table[@id='tablePartList']")));
		System.out.println(col);
		
		WebElement txtProjectCode = TestSetting.driver.findElement(By.xpath(".//input[@id='ContentPlaceHolder1_txtProjectID']")); 		
		SeleniumUtil.waitWebElementProperty(txtProjectCode, "value", "UFT1");*/
		try {
			ExcelUtil.loadExcelFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
		
	}

}
