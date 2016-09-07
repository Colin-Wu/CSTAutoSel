package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.OrderEntry2Obj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class OrderEntry2Action {
	WebDriver webdriver;

	public OrderEntry2Action(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String CreateOrderEntry2 (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//Mode:0,Standard;1,Repair(Expected SN);2,Repair only click add and next;
		//RetVal:-1:error;0: success;1:Expected SN is invalid;
		String ret = "-1";
		
		String Mode = InputObj.get("Mode").toString();
		String SearchPartNum = InputObj.get("SearchPartNum").toString();
		String SearchBOM = InputObj.get("SearchBOM").toString();
		String SearchSN = InputObj.get("SearchSN").toString();
		String ExpectedSN = InputObj.get("ExpectedSN").toString();
		String IsConfig = InputObj.get("IsConfig").toString();		
		
		OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
		
		if (Mode.equals("0")) {
			
		} else if(Mode.equals("1")) {
			
			WebElement TxtSearchPartNum = Obj.getTxtSearchPartNum();
			TxtSearchPartNum.sendKeys(SearchPartNum);	
			
			WebElement  BtnFind = Obj.getBtnFind();
			BtnFind.click();
			
			WebElement TxtExpectedSN = Obj.getTxtExpectedSN();
			TxtExpectedSN.sendKeys(ExpectedSN);	
			

			WebElement  LinkAdd = Obj.getLinkAdd();
			LinkAdd.click();

			SeleniumUtil.waitPageRefresh(LinkAdd);
			
			boolean isLblMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
			if (isLblMessageExist) {
				WebElement lblMessage = Obj.getLblMessage();	

				if (CommUtil.isMatchByReg(lblMessage.getText(), "SN is invalid\\.")) {
					CommUtil.logger.info(" > Msg: SN is invalid. Expected SN:" + ExpectedSN);
					ret = "1";
					return ret;
				}
			}
		
		} else if(Mode.equals("2")) {
			WebElement  LinkAdd = Obj.getLinkAdd();
			LinkAdd.click();

			SeleniumUtil.waitPageRefresh(LinkAdd);
		}

		boolean isTblAddListExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getTblAddListLocator(), 0);
		if (isTblAddListExist) {
			WebElement  BtnNext = Obj.getBtnNext();
			BtnNext.click();
		}		
		
		ret = "0";
		return ret;
	}

}
