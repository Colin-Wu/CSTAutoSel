package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.OrderEntry3Obj;
import script_lib.SeleniumUtil;

public class OrderEntry3Action {
	WebDriver webdriver;
	String vAddr1 = "919 S. CALIFORNIA STREET";
	String vAddr5 = "Address5";
	String vCity = "CHICAGO";
	String vState = "IL";
	String vCountry = "US";
	String vPostcode = "60612";

	public OrderEntry3Action(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String CreateOrderEntry3 (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//ReturnToStock: 0:uncheck;1:check
		//RetVal:-1:error;0: success;
		String ret = "-1";
		boolean bFillBlanks = false;
		
		String ReturnToStock = InputObj.get("ReturnToStock").toString();
		
		OrderEntry3Obj Obj = new OrderEntry3Obj(webdriver);
		
		boolean isChkStockExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getChkStockLocator(), 0);
		
		WebElement TxtAddr5 = Obj.getTxtAddr5();		
		boolean isAddr5Enabled = TxtAddr5.isEnabled();
		if (!isChkStockExist) {
			if (isAddr5Enabled) {
				bFillBlanks = true;
			}
		} else {
			WebElement ChkStock = Obj.getChkStock();
			boolean isChkStockEnabled = ChkStock.isEnabled();
			boolean isChkStockChecked =Boolean.parseBoolean(ChkStock.getAttribute("checked"));
			
			if (!isChkStockEnabled) {
				if (isAddr5Enabled) {
					bFillBlanks = true;
				}
			} else {
				if (ReturnToStock.equals("1")) {
					if (!isChkStockChecked) {
						ChkStock.click();
					}
				} else if (ReturnToStock.equals("0")) {
					if (isChkStockChecked) {
						ChkStock.click();
					}
					if (isAddr5Enabled) {
						bFillBlanks = true;
					}
				} else {
					if (isAddr5Enabled) {
						bFillBlanks = true;
					}				
				}
			}
			
		}
		
		
		if (bFillBlanks) {
			
			WebElement TxtAddr1 = Obj.getTxtAddr1();
			if (TxtAddr1.getAttribute("value").equals("")) {
				TxtAddr1.sendKeys(vAddr1);				
			}
					
			TxtAddr5 = Obj.getTxtAddr5();
			if (TxtAddr5.getAttribute("value").equals("")) {
				TxtAddr5.sendKeys(vAddr5);			
			}
			
			WebElement TxtCity = Obj.getTxtCity();
			if (TxtCity.getAttribute("value").equals("")) {
				TxtCity.sendKeys(vCity);		
			}
			
			WebElement TxtState = Obj.getTxtState();
			if (TxtState.getAttribute("value").equals("")) {
				TxtState.sendKeys(vState);		
			}
			
			WebElement TxtCountry = Obj.getTxtCountry();
			if (TxtCountry.getAttribute("value").equals("")) {
				TxtCountry.sendKeys(vCountry);	
			}
			
			WebElement TxtPostcode = Obj.getTxtPostcode();
			if (TxtPostcode.getAttribute("value").equals("")) {
				TxtPostcode.sendKeys(vPostcode);	
			}

		}
		
		WebElement  BtnNext = Obj.getBtnNext();
		BtnNext.click();	
		ret = "0";
		
		return ret;
	}
	
}
