package action_lib.inv_mngt;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.ReceivingPutawayObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class ReceivingPutawayAction {
	
   WebDriver webdriver;
	
	public ReceivingPutawayAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}

	public String SearchPutaway(HashMap<String, ?> InputObj) throws NoSuchElementException {
		String IsFound = "-1";	
		String Status = InputObj.get("Status").toString();
		String SN = InputObj.get("SN").toString();
		String ProjectCode = InputObj.get("ProjectCode").toString();
		String PalletID = InputObj.get("PalletID").toString();
		String BoxID = InputObj.get("BoxID").toString();
		
		ReceivingPutawayObj Obj = new ReceivingPutawayObj(webdriver);
		WebElement TxtProjectCode = Obj.getTxtProjectCode();
		TxtProjectCode.sendKeys(ProjectCode);
		
		CommUtil.logger.info(" > Project Code ");
		
		WebElement TxtPalletId = Obj.getTxtPalletId();
		TxtPalletId.sendKeys(PalletID);
		
		WebElement TxtBoxId = Obj.getTxtBoxId();
		TxtBoxId.sendKeys(BoxID);
		
		WebElement TxtSerialNum = Obj.getTxtSerialNum();
		TxtSerialNum.sendKeys(SN);
		
		Select CmbStatus = Obj.getCmbStatus(); 
		
		boolean isHasVal = SeleniumUtil.isSelectHasOption(CmbStatus, Status);
		
		//SeleniumUtil.waitPageRefresh(TxtSerialNum);
		
		CommUtil.logger.info(" > Option " + Status + " Found");
			
		if (!isHasVal) {
			CommUtil.logger.info(" > Status not found in UI");
			IsFound = "-1";
			return IsFound;
		}
		CmbStatus.selectByVisibleText(Status);
		
		WebElement BtnSearch = Obj.getBtnSearch();
		BtnSearch.click();
		
		//SeleniumUtil.waitPageRefresh(BtnSearch);
		
		WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		
		if (tblRow > 1) {
			CommUtil.logger.info(" > Putaway is found.");
			IsFound = "1";
		} else {
			CommUtil.logger.info(" > Putaway is not Found.");
		}
				
		return IsFound;
	}
	
	public String SaveToPutaway() throws NoSuchElementException {
		String retVal = "-1";	
		
		ReceivingPutawayObj Obj = new ReceivingPutawayObj(webdriver);
		WebElement BtnFinish = Obj.getBtnFinish();
		BtnFinish.click();
		
		SeleniumUtil.waitPageRefresh(BtnFinish);
		
		CommUtil.logger.info(" > Finish Button clicked : ");
		
		boolean isLblSuccessMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblPutAwayReadySuccessMessageLocator(), 0);
       
    	CommUtil.logger.info(" > Success Message Label : " + isLblSuccessMsgexist );
    	
		if (isLblSuccessMsgexist) {
			
			WebElement lblSuccessMessage = Obj.getSuccessMessagePutAwayReady();	

			if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Saved successfully\\.")) {
				CommUtil.logger.info(" > Saved successfully\\.");
				retVal = "1";
			}
		 }
		
		boolean isLblErrorMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrorMesgLocator(), 0);
		  
		  if (isLblErrorMsgexist) {
				
				WebElement lblErrorMessage = Obj.getLblErrorMessageLocation();	

				CommUtil.logger.info(" >"+lblErrorMessage.getText());
				retVal = "-1";
			}
		  
		 // This is just for logging purpose. - Code block end
		 
		 CommUtil.logger.info(" Return Value" + retVal);
		 return retVal;
	}
	
	public String SaveToPutawayready(String Location) throws NoSuchElementException {
		String retVal = "-1";	
		
		ReceivingPutawayObj Obj = new ReceivingPutawayObj(webdriver);
        WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
        
        CommUtil.logger.info(" > Rec Count is : " + tblRow );
        
        if(tblRow == 2)
        {
        	WebElement TxtWarehouseLocation = Obj.getTxtWareHouseLocation();
        	TxtWarehouseLocation.sendKeys(Location);	
    		
        	WebElement BtnSave = Obj.getBtnSave();
        	BtnSave.click();
        
        	SeleniumUtil.waitPageRefresh(BtnSave);
        }
        else if (tblRow > 2)
        {
        	WebElement TxtWarehouseLocation = Obj.getTxtWareHouseLocation();
        	TxtWarehouseLocation.sendKeys(Location);
        	
        	WebElement ChkDefaultBtnLocation = Obj.getChkDefaultLocation();
        	ChkDefaultBtnLocation.click();
        	
        	SeleniumUtil.waitPageRefresh(ChkDefaultBtnLocation);
        	boolean BtnSaveAllexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnSaveAllLocator(),0);
        	CommUtil.logger.info(" > BtnSaveAllexist: " + BtnSaveAllexist );
        	 
        	if(BtnSaveAllexist)
        	{
        		WebElement BtnSaveAll = Obj.getBtnSaveAllLocation();
        		BtnSaveAll.click();
        		SeleniumUtil.waitPageRefresh(BtnSaveAll);
        	}
        }
        else
        {
        	retVal="3";
        	return retVal;
        }
         
        boolean isLblSuccessMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblPutAwayReadySuccessMessageLocator(), 0);
        
		if (isLblSuccessMsgexist) {
			
			WebElement lblSuccessMessage = Obj.getSuccessMessagePutAwayReady();	
			
			CommUtil.logger.info("> "+lblSuccessMessage.getText());

			if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Save successfully")) {
				CommUtil.logger.info(" > Save successfully.");
				retVal = "0";
			}
		}
		
		   boolean isLblErrorMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrorMesgLocator(), 0);
		
			if (isLblErrorMsgexist) {
				
				WebElement lblErrorMessage = Obj.getLblErrorMessageLocation();	

				CommUtil.logger.info(" >"+lblErrorMessage.getText());
				
				if (CommUtil.isMatchByReg(lblErrorMessage.getText(), "The Warehouse Location entered is a repair location or not available, please update\\.")) {
					CommUtil.logger.info(" > The Warehouse Location entered is a repair location or not available, please update\\.");
					retVal = "1";
				}
				
				if (CommUtil.isMatchByReg(lblErrorMessage.getText(), "The Warehouse Location entered is not a repair location or not available, please update\\.")) {
					CommUtil.logger.info(" > The Warehouse Location entered is not a repair location or not available, please update\\.");
					retVal = "2";
				}
			}
	    
		CommUtil.logger.info(" > Return Value :" + retVal);
    	return retVal;
	}
}
