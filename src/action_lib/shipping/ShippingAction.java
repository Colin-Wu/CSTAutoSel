package action_lib.shipping;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.shipping.ShippingObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class ShippingAction {
	WebDriver webdriver;
	String vContactname = "Contactname";
	String vContactPhone = "123-456-1234";
	String vAddr5 = "Address5";

	public ShippingAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	public String AddTracking (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Expected SN is invalid;
		String ret = "-1";
		int iniIdx = 0;
		
		int TrackCnt = InputArr.size();
		
		ShippingObj Obj = new ShippingObj(webdriver);
		
		WebElement TblTracking = Obj.getTblTracking();
		
        int tblRow = SeleniumUtil.getTableRows(TblTracking);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Tracking record is Found. count:"+(tblRow-1));
			iniIdx = tblRow - 1;
		}		
			
		for (int i = 0; i < TrackCnt; i++,iniIdx++) {
			Obj = new ShippingObj(webdriver);
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> TrackingPara = (HashMap<String, String>) InputArr.get(i);
			
			String isFedex = TrackingPara.get("isFedex").toString();
			String TrackingNum = TrackingPara.get("TrackingNum").toString();

			WebElement  BtnAddTracking = Obj.getBtnAddTracking();
			BtnAddTracking.click();
			
			SeleniumUtil.waitPageRefreshByLoadingIcon2(webdriver);
			
			if (isFedex.equals("0")) {
				WebElement TxtTrackingNumber = Obj.getTxtTrackingNumber(iniIdx);
				TxtTrackingNumber.sendKeys(TrackingNum);
			} else if (isFedex.equals("1")) {
				// reserve for clicking Fedex link
			} else {
				CommUtil.logger.info(" > Invalid tracking number type. isFedex:"+isFedex);
				return ret;				
			}
			
			WebElement  LinkSave = Obj.getLinkSave(iniIdx);
			LinkSave.click();	
/*			try {
				// solve issue page does not wait
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				CommUtil.logger.info(" > Sleep exception");
				return ret;
			}*/
			SeleniumUtil.waitPageRefreshByLoadingIcon2(webdriver);
			
			boolean isLblTrackingSuccessMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblTrackingSuccessMessageLocator(), 0);
			if (isLblTrackingSuccessMessageExist) {
				WebElement LblTrackingSuccessMessage = Obj.getLblTrackingSuccessMessage();	
				CommUtil.logger.info(" > Msg: " + LblTrackingSuccessMessage.getText());
				
				if (!CommUtil.isMatchByReg(LblTrackingSuccessMessage.getText(), "Saving the tracking item data successfully\\.")) {
					ret = "1";
					return ret;
				}
			}
			
		}
		
		ret = "0";
		return ret;
	}	
	public String Ship (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success;1: other error
		String ret = "-1";	
		
		ArrayList<?> InputArr = (ArrayList<?>) InputObj.get("TrackArr");		
		
		ShippingObj Obj = new ShippingObj(webdriver);
		
		WebElement TxtContactname = Obj.getTxtContactname();
		if (TxtContactname.getAttribute("value").equals("")) {
			TxtContactname.sendKeys(vContactname);				
		}
					
		WebElement TxtContactPhone = Obj.getTxtContactPhone();
		if (TxtContactPhone.getAttribute("value").equals("")) {
			TxtContactPhone.sendKeys(vContactPhone);				
		}

		WebElement TxtAddress5 = Obj.getTxtAddress5();
		if (TxtAddress5.getAttribute("value").equals("")) {
			TxtAddress5.sendKeys(vAddr5);				
		}
		
		String retAddTracking = AddTracking(InputArr);
		
		if (!retAddTracking.equals("0")) {
			return retAddTracking;
		}	
		
		WebElement  ChkComplete0 = Obj.getChkComplete0();
		ChkComplete0.click();		

		WebElement  ChkComplete1 = Obj.getChkComplete1();
		ChkComplete1.click();	
		
		WebElement  ChkComplete2 = Obj.getChkComplete2();
		ChkComplete2.click();	
		
		WebElement  ChkComplete3 = Obj.getChkComplete3();
		ChkComplete3.click();	

		WebElement  BtnShip = Obj.getBtnShip();
		BtnShip.click();	
		
		SeleniumUtil.waitPageRefreshByLoadingIcon2(webdriver);
		
		boolean isLblSuccessMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		if (isLblSuccessMessageExist) {
			WebElement LblSuccessMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: " + LblSuccessMessage.getText());
			
			if (CommUtil.isMatchByReg(LblSuccessMessage.getText(), "Ship successfully\\.")) {
				ret = "0";
			}
		}
		
		return ret;
		
	}	
}
