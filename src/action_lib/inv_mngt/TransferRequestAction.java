package action_lib.inv_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.TransferRequestObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class TransferRequestAction {
	WebDriver webdriver;

	public TransferRequestAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	
	public String FillTransferRequest (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//retVal: -1:error;0:success;1:TransType not found;2:From Project not found in the list;3:To Project not found in the list;4:TransBy Type not found
		String ret = "-1";	
		String vCmt = "Transfer comment";
		
		String inTransType = InputObj.get("TransType").toString();
		String inTransByType = InputObj.get("TransByType").toString();
		String inCustomer = InputObj.get("Customer").toString();
		String inFromProj = InputObj.get("FromProj").toString();
		String inToProj = InputObj.get("ToProj").toString();

		
		TransferRequestObj Obj = new TransferRequestObj(webdriver);
		
		if (!inTransType.equals("")) {
			if (inTransType.equals("Project")) {
				WebElement  RdoTransTypeProject = Obj.getRdoTransTypeProject();
				boolean isRdoTransTypeProjectChecked =Boolean.parseBoolean(RdoTransTypeProject.getAttribute("checked"));

				if (!isRdoTransTypeProjectChecked) {
					RdoTransTypeProject.click();
					SeleniumUtil.waitPageRefresh(RdoTransTypeProject);
				}
				
				WebElement  TxtCustomer = Obj.getTxtCustomer();
				TxtCustomer.sendKeys(inCustomer);
				WebElement  TxtAreaCmt = Obj.getTxtAreaCmt();
				TxtAreaCmt.click();
				SeleniumUtil.waitPageRefresh(TxtCustomer);
				
				WebElement WebEleSelFromProj = Obj.getSelFromProj();
				Select SelFromProj = new Select(WebEleSelFromProj);
				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelFromProj, inFromProj);
				if (!isHasVal) {
					ret = "2";
					CommUtil.logger.info(" > FromProj option not found in UI. FromProj:"+inFromProj);
					return ret;
				}	
				SelFromProj.selectByVisibleText(inFromProj);
			
				WebElement WebEleSelToProj = Obj.getSelToProj();
				Select SelToProj = new Select(WebEleSelToProj);
				isHasVal = SeleniumUtil.isSelectHasOption(SelToProj, inToProj);
				if (!isHasVal) {
					ret = "3";
					CommUtil.logger.info(" > ToProj option not found in UI. ToProj:"+inToProj);
					return ret;
				}			
				SelToProj.selectByVisibleText(inToProj);			
				

			} else if (inTransType.equals("Location")) {
				WebElement  RdoTransTypeLocation = Obj.getRdoTransTypeLocation();
				boolean isRdoTransTypeLocationChecked =Boolean.parseBoolean(RdoTransTypeLocation.getAttribute("checked"));

				if (!isRdoTransTypeLocationChecked) {
					RdoTransTypeLocation.click();
					SeleniumUtil.waitPageRefresh(RdoTransTypeLocation);
				}
			} else {
				CommUtil.logger.info(" > TransType not defined:" + inTransType);
				ret = "1";
				return ret;
			}		
			
		}
		

		if (!inTransByType.equals("")) {
			WebElement WebEleSelTransBy = Obj.getSelTransBy();
			Select SelTransBy = new Select(WebEleSelTransBy);
			
	        if (!SelTransBy.getFirstSelectedOption().getAttribute("value").equals(inTransByType)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelTransBy, inTransByType);
				if (!isHasVal) {
					ret = "4";
					CommUtil.logger.info(" > Transfer by option not found in UI. :"+inTransByType);
					return ret;
				}			
				SelTransBy.selectByVisibleText(inTransByType);	
	        }
		}

		WebElement  TxtAreaCmt = Obj.getTxtAreaCmt();
		TxtAreaCmt.sendKeys(vCmt);

		ret = "0";
		return ret;
		
	}	
	
	public String GoNext () throws NoSuchElementException {
		
		String ret = "-1";	
		
		TransferRequestObj Obj = new TransferRequestObj(webdriver);
	
		WebElement BtnNext = Obj.getBtnNext();

		BtnNext.click();
		
		ret = "0";
		return ret;
		
	}	
}
