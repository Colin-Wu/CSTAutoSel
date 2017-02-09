package action_lib.inv_mngt;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.TransferAdminObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class TransferAdminAction {
	WebDriver webdriver;
	
	public TransferAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public HashMap<String, String> GetResultItemByBoxID (String Boxid) throws NoSuchElementException  {
		String IsFound = "-1";
		String vRow = "";
		String vTransNum = "";
		
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("IsFound", IsFound);
		RetObj.put("Row", vRow);
		RetObj.put("TransNum", vTransNum);
		
		TransferAdminObj Obj= new TransferAdminObj(webdriver);
		List<WebElement> LblBoxIDs = Obj.getLblBoxIDs();
	
		for (int i = 0; i < LblBoxIDs.size(); i++) {
			WebElement LblBoxid = LblBoxIDs.get(i);

			if (CommUtil.isMatchByReg(LblBoxid.getText(), ".*"+Boxid)) {
				
				IsFound = "1";
		        Pattern pattern = Pattern.compile("(?<=ContentPlaceHolder1_gvLines_rptTransferLine_)(\\d+)(?=_lblBoxID)");
		        Matcher matcher = pattern.matcher(LblBoxid.getAttribute("id"));
		        while(matcher.find())
		        	vRow = matcher.group();

		        
		        WebElement LblTransferNumber = Obj.getLblTransferNumber(vRow);
		        vTransNum = LblTransferNumber.getText();
			}
		}
		
		RetObj.put("IsFound", IsFound);
		RetObj.put("Row", vRow);
		RetObj.put("TransNum", vTransNum);
		
		return RetObj;	

	}
	
	public String AssignUserForTransfer (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String ret = "-1";
		
		String inUsername = InputObj.get("Username").toString();
		String inRow = InputObj.get("Row").toString();
		
		TransferAdminObj Obj= new TransferAdminObj(webdriver);
		WebElement WebEleSelUsername = Obj.getSelUsername(inRow);
		Select SelUsername = new Select(WebEleSelUsername);
	

		boolean isHasVal = SeleniumUtil.isSelectHasOption(SelUsername, inUsername);
		if (!isHasVal) {
			CommUtil.logger.info(" > Username option not found in UI. "+inUsername);
			return ret;
		}			
		SelUsername.selectByVisibleText(inUsername);	

		
		WebElement LinkSave = Obj.getLinkSave(inRow);
		LinkSave.click();
		SeleniumUtil.waitPageRefresh(LinkSave);
		
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

		return ret;
	}
	
	public String SearchTransferAdmin (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String inTransType = InputObj.get("TransType").toString();
		String inTransByType = InputObj.get("TransByType").toString();
		String inUsername = InputObj.get("Username").toString();
		String inStatus = InputObj.get("Status").toString();
		String inTransNum = InputObj.get("TransNum").toString();
	 
		String outIsFound = "-1";
		TransferAdminObj Obj= new TransferAdminObj(webdriver);

		if (!inTransType.equals("")) {
			if (inTransType.equals("Project")) {
				WebElement  RdoTransTypeProject = Obj.getRdoTransTypeProject();
				boolean isRdoTransTypeProjectChecked =Boolean.parseBoolean(RdoTransTypeProject.getAttribute("checked"));

				if (!isRdoTransTypeProjectChecked) {
					RdoTransTypeProject.click();
					SeleniumUtil.waitPageRefresh(RdoTransTypeProject);
				}
							

			} else if (inTransType.equals("Location")) {
				WebElement  RdoTransTypeLocation = Obj.getRdoTransTypeLocation();
				boolean isRdoTransTypeLocationChecked =Boolean.parseBoolean(RdoTransTypeLocation.getAttribute("checked"));

				if (!isRdoTransTypeLocationChecked) {
					RdoTransTypeLocation.click();
					SeleniumUtil.waitPageRefresh(RdoTransTypeLocation);
				}
			} else {
				CommUtil.logger.info(" > TransType not defined:" + inTransType);
				return outIsFound;
			}		
			
		}
		
		if (!inTransByType.equals("")) {
			WebElement WebEleSelTransBy = Obj.getSelTransBy();
			Select SelTransBy = new Select(WebEleSelTransBy);
			
	        if (!SelTransBy.getFirstSelectedOption().getAttribute("value").equals(inTransByType)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelTransBy, inTransByType);
				if (!isHasVal) {
					CommUtil.logger.info(" > Transfer by option not found in UI. "+inTransByType);
					return outIsFound;
				}			
				SelTransBy.selectByVisibleText(inTransByType);	
	        }
		}
		
		if (!inStatus.equals("")) {
			WebElement WebEleSelStatus = Obj.getSelStatus();
			Select SelStatus = new Select(WebEleSelStatus);
			
	        if (!SelStatus.getFirstSelectedOption().getAttribute("value").equals(inStatus)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStatus, inStatus);
				if (!isHasVal) {
					CommUtil.logger.info(" > Status option not found in UI. "+inStatus);
					return outIsFound;
				}			
				SelStatus.selectByVisibleText(inStatus);	
	        }
		}
		
		WebElement TxtUserName = Obj.getTxtUserName();
		if (!TxtUserName.getAttribute("value").equals(inUsername)) {
			TxtUserName.clear();
			TxtUserName.sendKeys(inUsername);
		}
		
		WebElement TxtTransferNo = Obj.getTxtTransferNo();
		if (!TxtTransferNo.getAttribute("value").equals(inTransNum)) {
			TxtTransferNo.clear();
			TxtTransferNo.sendKeys(inTransNum);
		}
				
		WebElement BtnSearch = Obj.getBtnSearch();
		BtnSearch.click();
		//SeleniumUtil.waitPageRefresh(BtnSearch);
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
        WebElement tblResult = Obj.getTblSearchResult();
        int tblRow = SeleniumUtil.getTableRows(tblResult);
	
		if (tblRow >= 3) {
			CommUtil.logger.info(" > Rec is found.");
			outIsFound = "1";
		} else {
			CommUtil.logger.info(" > Rec is not found.");
			outIsFound = "0";
		}
		
		return outIsFound;
	}

}
