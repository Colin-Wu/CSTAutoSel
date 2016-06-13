package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.odr_mngt.POMngtObj;

public class POMngtAction {
	
	public static int SearchPO (HashMap<String, ?> InputObj) {
		int isFound = 0;	
		
		String SearchProjectCode = InputObj.get("SearchProjectCode").toString();
		String SearchCustomerPO = InputObj.get("SearchCustomerPO").toString();
		
		POMngtObj Obj = new POMngtObj(TestSetting.driver);
	
		WebElement txtCustomerPO = Obj.getTxtCustomerPO();
		WebElement txtProjectCode = Obj.getTxtProjectCode();
		WebElement btnSearchPO = Obj.getBtnSearchPO();

		txtCustomerPO.clear();
		txtCustomerPO.sendKeys(SearchCustomerPO);

		txtProjectCode.clear();
		txtProjectCode.sendKeys(SearchProjectCode);
		
		btnSearchPO.click();
		
		return isFound;
	}
}
