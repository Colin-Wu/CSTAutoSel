package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.OrderEntry4Obj;
import script_lib.SeleniumUtil;

public class OrderEntry4Action {
	WebDriver webdriver;
	
	String vComments = "Comments";
	String vReportFailure = "Reported Failure";
	
	public OrderEntry4Action(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String CreateOrderEntry4 (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//Mode:0:Standard; 1:repair both comments and reported failure; 2: repair only reported failure; 3: only click next; 4: only click next when edit at parent order;5:Template order, need to click save
		//RetVal:-1:error;0: success;
		String ret = "-1";
		
		String Mode = InputObj.get("Mode").toString();
		
		OrderEntry4Obj Obj = new OrderEntry4Obj(webdriver);
		
		if (Mode.equals("0")) {
			
		} else if (Mode.equals("1")) {
			WebElement TxtAreaCmt = Obj.getTxtAreaCmt();
			if (TxtAreaCmt.getAttribute("value").equals("")) {
				TxtAreaCmt.sendKeys(vComments);	
			}
			
			WebElement TxtAreaReportFailure = Obj.getTxtAreaReportFailure();
			if (TxtAreaReportFailure.getAttribute("value").equals("")) {
				TxtAreaReportFailure.sendKeys(vReportFailure);	
			}
		}
		
		WebElement  BtnActivate = Obj.getBtnActivate();
		BtnActivate.click();	
		
		SeleniumUtil.waitPageRefresh(BtnActivate);
		
		if (Mode.equals("1")) {
			WebElement  BtnConfirmActYes = Obj.getBtnConfirmActYes();
			BtnConfirmActYes.click();	
		}
		
		ret = "0";		
		return ret;
	}
}
