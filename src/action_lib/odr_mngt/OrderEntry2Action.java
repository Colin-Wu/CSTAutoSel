package action_lib.odr_mngt;

import java.util.ArrayList;
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
		ArrayList<?> InputArr = (ArrayList<?>) InputObj.get("PartArr");		
		
		OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
		
		if (Mode.equals("0")) {
			
		} else if(Mode.equals("1")) {
			
			String retAddPart = AddParts(InputArr);
			
			if (!retAddPart.equals("0")) {
				return retAddPart;
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

	public String AddParts (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Expected SN is invalid;
		String ret = "-1";
		
		int PartCnt = InputArr.size();
			
		for (int i = 0; i < PartCnt; i++) {
			OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> PartPara = (HashMap<String, String>) InputArr.get(i);
			
			String SearchPartNum = PartPara.get("SearchPartNum").toString();
			String SearchBOM = PartPara.get("SearchBOM").toString();
			String SearchSN = PartPara.get("SearchSN").toString();
			String ExpectedSN = PartPara.get("ExpectedSN").toString();
			String IsConfig = PartPara.get("IsConfig").toString();	
			
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
		
		}	
		
		ret = "0";
		return ret;
	}
}
