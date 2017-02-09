package action_lib.inv_mngt;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.TransferRequestForBoxObj;
import obj_repository.inv_mngt.TransferRequestObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class TransferRequestForBoxAction {
	WebDriver webdriver;

	public TransferRequestForBoxAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	
	public String FillTransRequestForBox (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;
		String ret = "-1";
		
		int BoxCnt = InputArr.size();
			
		for (int i = 0; i < BoxCnt; i++) {
			TransferRequestForBoxObj Obj = new TransferRequestForBoxObj(webdriver);
			
			if (i != 0) {
				WebElement BtnAddNew = Obj.getBtnAddNew();
				BtnAddNew.click();
			}
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> BoxPara = (HashMap<String, String>) InputArr.get(i);
			
			String inBoxID = BoxPara.get("BoxID").toString();
			String inQty = BoxPara.get("Qty").toString();
			String inNewLocation = BoxPara.get("NewLocation").toString();
			//String inCreatPallet = BoxPara.get("CreatPallet").toString();

			WebElement TxtBoxid = Obj.getTxtBoxid(i);
			TxtBoxid.sendKeys(inBoxID);    
			WebElement TxtQtyTransfered = Obj.getTxtQtyTransfered(i);
			TxtQtyTransfered.click();
			SeleniumUtil.waitPageRefresh(TxtBoxid);
			
			boolean isLblMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
			if (isLblMessageExist) {
				WebElement lblMessage = Obj.getLblMessage();	
				CommUtil.logger.info(" > Msg: " + lblMessage.getText());

				if (CommUtil.isMatchByReg(lblMessage.getText(), "Project transfer is not allowed for Box: "+inBoxID+" since order is not completed yet\\.")) {
					ret = "1";
					return ret;
				} else {
					CommUtil.logger.info(" > Unhandled msg.");
					return ret;				
				}
			} 
			       
			TxtQtyTransfered = Obj.getTxtQtyTransfered(i);
			TxtQtyTransfered.sendKeys(inQty);  
			
			WebElement TxtNewLocation = Obj.getTxtNewLocation(i);
			TxtNewLocation.clear();
			TxtNewLocation.sendKeys(inNewLocation); 
			
			WebElement WebEleSelNewStockGroup = Obj.getNewStockGroup(i);
			Select SelNewStockGroup = new Select(WebEleSelNewStockGroup);
			SelNewStockGroup.selectByIndex(1);
			
		}

		ret = "0";
		return ret;
	}
	
	public String Save () throws NoSuchElementException {
		
		String ret = "-1";	
		
		TransferRequestForBoxObj Obj = new TransferRequestForBoxObj(webdriver);
	
		WebElement BtnSave = Obj.getBtnSave();

		BtnSave.click();
		
		TransferRequestObj TransferRequestObj = new TransferRequestObj(webdriver);
		TransferRequestObj.getTxtAreaCmt();
		
		ret = "0";
		return ret;
		
	}	
}
