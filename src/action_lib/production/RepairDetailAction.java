package action_lib.production;


import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import config.TestSetting;

import obj_repository.production.RepairDetailObj;
import obj_repository.production.RepairTechQueueObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class RepairDetailAction {
	WebDriver webdriver;

	public RepairDetailAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	public String RepairComplete (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String ret = "-1";	
		
	
		RepairDetailObj Obj = new RepairDetailObj(webdriver);
		
		WebElement BtnRepairComplete = Obj.getBtnRepairComplete();

		BtnRepairComplete.click();
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), TestSetting.Timeout_Sec);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblMessage();	
			CommUtil.logger.info(" > Msg: "+lblMessage.getText());

			if (CommUtil.isMatchByReg(lblMessage.getText(), "Save successfully,Do you wish to redirect to repair queue\\?")) {
				WebElement BtnConfirmNo = Obj.getBtnConfirmNo();
				BtnConfirmNo.click();
			} else if (CommUtil.isMatchByReg(lblMessage.getText(), "Do you wish to update the order to internal repair order\\?")) {
				WebElement BtnConfirmYes = Obj.getBtnConfirmYes();
				BtnConfirmYes.click();
				
				SeleniumUtil.waitWebElementVisible(webdriver, Obj.getBtnConfirmNoLocator());
				
				isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), TestSetting.Timeout_Sec);
				if (isLblMsgexist) {
					lblMessage = Obj.getLblMessage();	
					CommUtil.logger.info(" > Msg: "+lblMessage.getText());
					
					if (CommUtil.isMatchByReg(lblMessage.getText(), "Save successfully,Do you wish to redirect to repair queue\\?")) {
						WebElement BtnConfirmNo = Obj.getBtnConfirmNo();
						BtnConfirmNo.click();
					} else {
						CommUtil.logger.info(" > Unhandled message.");
						return ret;
					}
				}
				
			}
		} else {
			
			CommUtil.logger.info(" > Message not found.");
			return ret;
		}
		ret = "0";
		return ret;
		
	}
	public String ShipToVendor (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String ret = "-1";	
		
	
		RepairDetailObj Obj = new RepairDetailObj(webdriver);
		
		WebElement BtnShipToVendor = Obj.getBtnShipToVendor();

		BtnShipToVendor.click();
		
		RepairTechQueueObj repairtechobj = new RepairTechQueueObj(webdriver);
		SeleniumUtil.waitWebElementVisible(webdriver, repairtechobj.getBtnSearchLocator());

		ret = "0";
		return ret;
		
	}
	public String FillRepairDetailInfo () throws NoSuchElementException {

		String ret = "-1";	
		
		String inRepairCode = "C-Repair as Reported";
		String inDispCode = "G-Good Material";

		
		RepairDetailObj Obj = new RepairDetailObj(webdriver);
	
		WebElement WebEleSelRepairCode = Obj.getSelRepairCode();
		Select SelRepairCode = new Select(WebEleSelRepairCode);
		
        if (!SelRepairCode.getFirstSelectedOption().getAttribute("value").equals(inRepairCode)) {

			boolean isHasVal = SeleniumUtil.isSelectHasOption(SelRepairCode, inRepairCode);
			if (!isHasVal) {
				CommUtil.logger.info(" > RepairCode option not found in UI. RepairCode:"+inRepairCode);
				return ret;
			}
			
			SelRepairCode.selectByVisibleText(inRepairCode);
        }

		WebElement WebEleSelDispostionCode = Obj.getSelDispostionCode();
		Select SelDispostionCode = new Select(WebEleSelDispostionCode);
		
        if (!SelDispostionCode.getFirstSelectedOption().getAttribute("value").equals(inDispCode)) {

			boolean isHasVal = SeleniumUtil.isSelectHasOption(SelDispostionCode, inDispCode);
			if (!isHasVal) {
				CommUtil.logger.info(" > DispostionCode option not found in UI. DispostionCode:"+inDispCode);
				return ret;
			}
			
			SelDispostionCode.selectByVisibleText(inDispCode);
        }

        ret = "0";
		return ret;
		
	}	
}
