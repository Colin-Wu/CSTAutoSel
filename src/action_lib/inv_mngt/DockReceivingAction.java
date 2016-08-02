package action_lib.inv_mngt;


import java.util.HashMap;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.DockReceivingObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;


public class DockReceivingAction {
	WebDriver webdriver;

	public DockReceivingAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String NewDocReceiving (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: successful;1:Project code not found in the list
		String ret = "-1";	
		String TrackNum = InputObj.get("TrackNum").toString();
		String PONum = InputObj.get("PONum").toString();
		String Carrier = InputObj.get("Carrier").toString();
		String ProjectCode = InputObj.get("ProjectCode").toString();
		String BoxCnt = InputObj.get("BoxCnt").toString();
		String PalletCnt = InputObj.get("PalletCnt").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
	
		WebElement BtnNewDockRcv = Obj.getBtnNewDockRcv();
		BtnNewDockRcv.click();
		
		SeleniumUtil.waitPageRefresh(BtnNewDockRcv);
	
		WebElement TxtTrackingNumber = Obj.getTxtTrackingNumber();
		TxtTrackingNumber.sendKeys(TrackNum);	
		
		WebElement TxtPONumber = Obj.getTxtPONumber();
		TxtPONumber.sendKeys(PONum);	
		WebElement TxtCarrier = Obj.getTxtCarrier();
		TxtCarrier.click();
		
		SeleniumUtil.waitPageRefresh(TxtPONumber);
		
		TxtCarrier = Obj.getTxtCarrier();
		TxtCarrier.sendKeys(Carrier);	
		
		Select SelProjectCode = Obj.getSelProjectCode();
		boolean isHasVal = SeleniumUtil.isSelectHasOption(SelProjectCode, ProjectCode);
		if (!isHasVal) {
			CommUtil.logger.info(" > Project code not found in project list");
			ret = "1";
			return ret;
		}
		
		SelProjectCode.selectByVisibleText(ProjectCode);
		
		WebElement TxtBoxCount = Obj.getTxtBoxCount();
		TxtBoxCount.sendKeys(BoxCnt);	
		
		WebElement TxtPalletCount = Obj.getTxtPalletCount();
		TxtPalletCount.sendKeys(PalletCnt);
		
		WebElement BtnSave = Obj.getBtnSave();
		BtnSave.click();
		
		SeleniumUtil.waitPageRefresh(BtnSave);

		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		if (isLblMsgexist) {
			
			WebElement lblSuccessMessage = Obj.getLblSuccessMessage();	

			if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Dock Entry has been saved\\.")) {
				CommUtil.logger.info(" > Dock Entry has been saved\\.");
				ret = "0";
			}
		}
		
		return ret;
	}
}
