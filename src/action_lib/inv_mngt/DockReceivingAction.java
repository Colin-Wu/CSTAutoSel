package action_lib.inv_mngt;


import java.util.HashMap;

import org.openqa.selenium.By;
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
	
	public String SearchDocReceiving (HashMap<String, ?> InputObj) throws NoSuchElementException {
		String isFound = "-1";	
		//RetVal:-1:error;0: successful;1:Project code not found in the list
		String TrackNum = InputObj.get("TrackNum").toString();
		String PONum = InputObj.get("PONum").toString();
		String ProjectCode = InputObj.get("ProjectCode").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);		

		WebElement TxtSearchTrackNum = Obj.getTxtSearchTrackNum();
		TxtSearchTrackNum.sendKeys(TrackNum);	
		
		WebElement TxtSearchPONum = Obj.getTxtSearchPONum();
		TxtSearchPONum.sendKeys(PONum);
		
		WebElement TxtSearchProjectCode = Obj.getTxtSearchProjectCode();
		TxtSearchProjectCode.sendKeys(ProjectCode);
		
		WebElement BtnSearch = Obj.getBtnSearch();
		BtnSearch.click();
		
		WebElement tblResult = Obj.getTblSearchResult();
		
		int tblRow = SeleniumUtil.getTableRows(tblResult);
		
		if (tblRow > 1) {
			CommUtil.logger.info(" > Doc Receiving is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Doc Receiving is not Found.");
		}
				
		return isFound;
	}
	public String SearchResultGotoDetail (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: successful;1:PO is cancelled.
		String ret = "-1";	
		
		int delCol = 0;
		
		String TrackNum = InputObj.get("TrackNum").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		
		WebElement tblResult = Obj.getTblSearchResult();
			
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Tracking Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, TrackNum);

		By locator = Obj.getLinkEditDetailLocator();
		
		WebElement editDetaillink = SeleniumUtil.getCellElement(webdriver, tblResult, delCol, searchrow, locator);
		
		if (editDetaillink != null) {
			CommUtil.logger.info(" > EditDetail link clicked. TrackNum:" + TrackNum);
			editDetaillink.click();
		}
		
		SeleniumUtil.waitPageRefresh(editDetaillink);
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrMessageLocator(), 0);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblErrMessage();	

			if (CommUtil.isMatchByReg(lblMessage.getText(), "This PO has been canceled, please contact PS\\.")) {
				CommUtil.logger.info(" > Msg: This PO has been canceled, please contact PS.");
				ret = "1";
			}
		} else {
/*			boolean isBtnYesexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnConfirmYesLocator(), 0);
			if (isBtnYesexist) {
				WebElement BtnConfirmYes = Obj.getBtnConfirmYes();
				BtnConfirmYes.click();
				
				SeleniumUtil.waitPageRefresh(BtnConfirmYes);
				
				isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
				if (isLblMsgexist) {
					
					WebElement lblSuccessMessage = Obj.getLblSuccessMessage();	

					if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "PO delete successfully\\.")) {
						CommUtil.logger.info(" > PO delete successfully.");
						ret = "0";
					}
				}
				
				
			}*/
			
		}	
		
		return ret;
	}

}
