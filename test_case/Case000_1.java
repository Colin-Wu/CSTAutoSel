package test_case;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.cst_main.CST_MainAction;
import action_lib.index.IndexPageAction;
import action_lib.odr_mngt.OrderIndexAction;
import action_lib.odr_mngt.POMngtAction;


public class Case000_1 {
	
	
	public static int run (WebDriver driver) {
		
		int retVal = 0;
	
		IndexPageAction.login();
		
		CST_MainAction.MenuOrdMngt();
		
		OrderIndexAction.GotoPOMngt();
		
		HashMap<String, String> InputObj = new HashMap<String, String>();
		
		InputObj.put("SearchProjectCode", "UFT");
		InputObj.put("SearchCustomerPO", "UFT51533");
		
		POMngtAction.SearchPO(InputObj);
		
		
		return retVal;
	}
	

}
