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
	
	public HashMap<String, String> DetailReceiving(HashMap<String, ?> InputObj) throws NoSuchElementException {
	
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("RetVal","");
		RetObj.put("PalletID", "");
		
		String inPartNum = InputObj.get("PartNum").toString();
		String inQty = InputObj.get("Qty").toString();
		String inSN = InputObj.get("SN").toString();
		String inDisposition = InputObj.get("Disposition").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		WebElement TxtPartNum = Obj.getTxtPartNum();
		TxtPartNum.sendKeys(inPartNum);
		
		WebElement TxtQty = Obj.getTxtQty();
		TxtQty.sendKeys(inQty);
		
		Select CmbDisposition = Obj.getCmbDetailReceiveDisposition();
		boolean isHasVal = SeleniumUtil.isSelectHasOption(CmbDisposition, inDisposition);
		 
		if (!isHasVal) {
	      CommUtil.logger.info(" > Disposition option not found in UI.");
	      RetObj.put("PalletID","");
		  RetObj.put("RetVal", "-1");
		  return RetObj;
		}
		
		CmbDisposition.selectByVisibleText(inDisposition); 
		WebElement BtnCreatePalletID = Obj.getBtnCreatePalletID();
		BtnCreatePalletID.click();
		
		SeleniumUtil.waitPageRefresh(BtnCreatePalletID);
        
		WebElement BtnDialogBoxYes = Obj.getBtnYesDialogBox();
		BtnDialogBoxYes.click();
		SeleniumUtil.waitPageRefresh(BtnDialogBoxYes);
		
		WebElement BtnGo = Obj.getBtnGo();
		BtnGo.click();
		SeleniumUtil.waitPageRefresh(BtnGo);
		
        WebElement tblResult = Obj.getTblDetailReceivingINVParts();
		
		int tblRow = SeleniumUtil.getTableRows(tblResult);
		
		CommUtil.logger.info("Number of Rows : " + tblRow);
		// Iterate over the rows set the UniqueSerialNum 
		//
		//
        //		
		// Iterate over the rows set the UniqueSerialNum 
		
		/*WebElement BtnSave = Obj.getBtnSaveDetailReceiving();
		BtnSave.click();
		SeleniumUtil.waitPageRefresh(BtnSave);*/
		
		return RetObj;
	}
	
	
	public String DocReceivingGoToDetail(String TrackNum) throws NoSuchElementException{
		String RetValue = "-1";	
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		
		WebElement TxtTrackingNumber = Obj.getTxtTrackNum();
		TxtTrackingNumber.sendKeys(TrackNum);
		
		
		WebElement BtnSerach = Obj.getBtnSearch();
		BtnSerach.click();
		SeleniumUtil.waitPageRefresh(BtnSerach);
		
		WebElement tblDocReceivingResult = Obj.getTblSearchResult();
		int tblRow = SeleniumUtil.getTableRows(tblDocReceivingResult);
		
		if(tblRow < 2){
			RetValue= "-1";
		}
		
		else{
				WebElement BtnEditDetials = Obj.getBtnEditDetials();
				BtnEditDetials.click();
				SeleniumUtil.waitPageRefresh(BtnEditDetials);
				
				boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrMessageLocator(), 0);
				
				if(isLblMsgexist){
					WebElement lblMessage = Obj.getLblErrMessage();	
					CommUtil.logger.info("> Error Message : " + lblMessage.getText());
					RetValue="1";
			    }
			    else{
					RetValue="0";
			    }
		   }
		
		CommUtil.logger.info("Return Vale : "+RetValue);
		return  RetValue;
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
