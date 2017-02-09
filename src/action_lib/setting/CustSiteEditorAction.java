package action_lib.setting;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.setting.CustSiteEditorObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class CustSiteEditorAction {
	WebDriver webdriver;

	public CustSiteEditorAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String CheckPostCode (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//retVal: -1:error;0:success;1:Only 2 Characters for country code displayed;2:only 5 digital displayed;3:Only alpha numeric dash displayed
		String outRetVal = "-1";
		
		String inCountryCode = InputObj.get("CountryCode").toString();
		String inPostCode = InputObj.get("PostCode").toString();
		
		CustSiteEditorObj Obj = new CustSiteEditorObj(webdriver);
		
		WebElement TxtCountryCode = Obj.getTxtCountryCode();
		if (!TxtCountryCode.getAttribute("value").equals(inCountryCode)) {
			TxtCountryCode.clear();
			TxtCountryCode.sendKeys(inCountryCode);
			WebElement TxtPostCode = Obj.getTxtPostCode();
			TxtPostCode.click();
		}
		
		boolean isCountryCodeMsgExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblCountryCodeMessageLocator(), 1);
		if (isCountryCodeMsgExist) {
			WebElement LblCountryCodeMessage = Obj.getLblCountryCodeMessage();
			boolean isVisible = LblCountryCodeMessage.isDisplayed();
			if (isVisible) {
				CommUtil.logger.info(" > Msg: "+LblCountryCodeMessage.getText());
				if (CommUtil.isMatchByReg(LblCountryCodeMessage.getText(), "Only 2 characters")) {
					outRetVal = "1";
					return outRetVal;
				} else {
					CommUtil.logger.info(" > Unhandled message.");
					return outRetVal;
				}
			}
		}

		WebElement TxtPostCode = Obj.getTxtPostCode();
		if (!TxtPostCode.getAttribute("value").equals(inPostCode)) {
			TxtPostCode.clear();
			TxtPostCode.sendKeys(inPostCode);
			TxtCountryCode = Obj.getTxtCountryCode();
			TxtCountryCode.click();
		}	
		
		boolean isPostCodeMsgExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblPostCodeMessageLocator(), 1);
		if (isPostCodeMsgExist) {
			WebElement LblPostCodeMessage = Obj.getLblPostCodeMessage();
			boolean isVisible = LblPostCodeMessage.isDisplayed();
			if (isVisible) {
				CommUtil.logger.info(" > Msg: "+LblPostCodeMessage.getText());
				if (CommUtil.isMatchByReg(LblPostCodeMessage.getText(), "Only 5 digits")) {
					outRetVal = "2";
					return outRetVal;
				} else if (CommUtil.isMatchByReg(LblPostCodeMessage.getText(), "Only alpha numeric dash space")) {
					outRetVal = "3";
					return outRetVal;					
				} else {
					CommUtil.logger.info(" > Unhandled message.");
					return outRetVal;
				}
			}
		}
		outRetVal = "0";
		return outRetVal;
		
	}
}
