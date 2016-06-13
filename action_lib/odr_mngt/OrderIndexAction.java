package action_lib.odr_mngt;

import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.odr_mngt.OrderIndexObj;

public class OrderIndexAction {
	
	public static void GotoPOMngt () {
		
		OrderIndexObj ordIdxObj = new OrderIndexObj(TestSetting.driver);
	
		WebElement cmdPOManagement = ordIdxObj.getCmdPOManagement();

		cmdPOManagement.click();
		
	}
}
