package action_lib.setting;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.setting.LocationEditorObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class LocationEditorAction {
	WebDriver webdriver;

	public LocationEditorAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String SaveLocation (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: successful;1:Loc Type does not exist;2:Stock Room does not exist;3:Duplicated location name
		String outRetVal = "-1";
		
		String inLocType = InputObj.get("LocType").toString();
		String inLocName = InputObj.get("LocName").toString();
		String inStockRoom = InputObj.get("StockRoom").toString();
/*		String inRow = InputObj.get("Row").toString();
		String inRack = InputObj.get("Rack").toString();
		String inShelf = InputObj.get("Shelf").toString();
		String inBin = InputObj.get("Bin").toString();*/
		
		LocationEditorObj Obj = new LocationEditorObj(webdriver);
		
		if (!inLocType.equals("")) {
			WebElement WebEleSelLocationType = Obj.getSelLocationType();
			Select SelLocationType = new Select(WebEleSelLocationType);
			
	        if (!SelLocationType.getFirstSelectedOption().getAttribute("value").equals(inLocType)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelLocationType, inLocType);
				if (!isHasVal) {
					CommUtil.logger.info(" > LocationType option not found in UI. LocationType:"+inLocType);
					outRetVal = "1";
					return outRetVal;
				}
				
				SelLocationType.selectByVisibleText(inLocType);
				SeleniumUtil.waitPageRefresh(WebEleSelLocationType);
	        }
		}
		
		if (!inStockRoom.equals("")) {
			WebElement WebEleSelStockRoom = Obj.getSelStockRoom();
			Select SelStockRoom = new Select(WebEleSelStockRoom);
			
	        if (!SelStockRoom.getFirstSelectedOption().getAttribute("value").equals(inStockRoom)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStockRoom, inStockRoom);
				if (!isHasVal) {
					CommUtil.logger.info(" > StockRoom option not found in UI. StockRoom:"+inStockRoom);
					outRetVal = "2";
					return outRetVal;
				}
				
				SelStockRoom.selectByVisibleText(inStockRoom);
	        }
		}
		
		WebElement TxtLocationName = Obj.getTxtLocationName();
		if (!TxtLocationName.getAttribute("value").equals(inLocName)) {
			TxtLocationName.clear();
			TxtLocationName.sendKeys(inLocName);
		}	
		
		WebElement BtnSave = Obj.getBtnSave();
		BtnSave.click();
		SeleniumUtil.waitPageRefresh(BtnSave);
		
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 1);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: "+lblMessage.getText());
			if (CommUtil.isMatchByReg(lblMessage.getText(), "Location added successfully\\.")) {
				outRetVal = "0";
			}
		} else {
			
			boolean isLblErrorMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrorMessageLocator(), 1);
			
			if (isLblErrorMsgexist) {
				WebElement lblErrorMessage = Obj.getLblErrorMessage();	
				CommUtil.logger.info(" > Msg: "+lblErrorMessage.getText());
				if (CommUtil.isMatchByReg(lblErrorMessage.getText(), "Location with this name already exists\\.")) {
					outRetVal = "3";
				}	
			} else {				
				CommUtil.logger.info(" > Message not found. ");
			}
			

		}
		
		
		return outRetVal;
		
	}
}
