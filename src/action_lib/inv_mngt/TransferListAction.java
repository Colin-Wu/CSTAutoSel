package action_lib.inv_mngt;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.inv_mngt.TransferListObj;

import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class TransferListAction {
	WebDriver webdriver;

	public TransferListAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	
	public String FillTransferList (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;
		String ret = "-1";
		String vStockGroup = TestSetting.StockGroup;
		
		int BoxCnt = InputArr.size();
			
		for (int i = 0; i < BoxCnt; i++) {
			TransferListObj Obj = new TransferListObj(webdriver);
			
		
			@SuppressWarnings("unchecked")
			HashMap<String, String> BoxPara = (HashMap<String, String>) InputArr.get(i);
			
			String inBoxID = BoxPara.get("BoxID").toString();
			String inPalletID = BoxPara.get("PalletID").toString();
			String inNewLocation = BoxPara.get("NewLocation").toString();
			//String inCreatPallet = BoxPara.get("CreatPallet").toString();

			WebElement TxtScannedBoxID = Obj.getTxtScannedBoxID(i);
			TxtScannedBoxID.sendKeys(inBoxID);    
			
			if (!inPalletID.equals("")) {
				WebElement TxtScannedPalletID = Obj.getTxtScannedPalletID(i);
				TxtScannedPalletID.sendKeys(inPalletID);  			
			}

			
			WebElement TxtNewLocation = Obj.getTxtNewLocationScan(i);
			TxtNewLocation.sendKeys(inNewLocation); 
			
			WebElement TxtNewSGScan = Obj.getTxtNewSGScan(i);
			TxtNewSGScan.sendKeys(vStockGroup); 
			
		}

		ret = "0";
		return ret;
	}
	
	public String Save () throws NoSuchElementException {
		
		String ret = "-1";	
		
		TransferListObj Obj = new TransferListObj(webdriver);
	
		WebElement BtnSave = Obj.getBtnSave();

		BtnSave.click();
		
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: "+lblMessage.getText());

			if (CommUtil.isMatchByReg(lblMessage.getText(), "Save successfully")) {
				ret = "0";
			} else {
				CommUtil.logger.info(" > Msg not handled.");
				ret = "1";			
			}
		}

		ret = "0";
		return ret;
		
	}	
}
