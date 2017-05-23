package action_lib.qa;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.qa.QAListObj;

import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class QAListAction {
	WebDriver webdriver;
	int waitloading = 10;

	public QAListAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String SearchOrdNum (HashMap<String, ?> InputObj) throws NoSuchElementException, AWTException {

		String isFound = "0";	
	
		String SearchOrdNum = InputObj.get("OrdNum").toString();

		QAListObj QAListPageObj = new QAListObj(webdriver);
		
		
		WebElement TxtOrderNumber = QAListPageObj.getTxtOrderID();
		if (!TxtOrderNumber.getAttribute("value").equals(SearchOrdNum)) {
			TxtOrderNumber.sendKeys(SearchOrdNum);
			TxtOrderNumber.sendKeys(Keys.TAB);

		}	
			
		SeleniumUtil.isWebElementExist(webdriver, QAListPageObj.getPnlWaitTitleLocator(), waitloading);
		SeleniumUtil.isWebElementNoExist(webdriver, QAListPageObj.getPnlWaitTitleLocator(), waitloading);

		if (SeleniumUtil.isWebElementExist(webdriver, QAListPageObj.getBtnOKLocator(), 0)) {
			SeleniumUtil.waitWebElementVisible(webdriver, QAListPageObj.getBtnOKLocator());
		
			WebElement BtnOK = QAListPageObj.getBtnOK();
			BtnOK.click();
			
			SeleniumUtil.isWebElementExist(webdriver, QAListPageObj.getPnlWaitTitleLocator(), waitloading);
			SeleniumUtil.isWebElementNoExist(webdriver, QAListPageObj.getPnlWaitTitleLocator(), waitloading);

		}
		
		
		WebElement tblResult = QAListPageObj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 0) {
			CommUtil.logger.info(" > QA Grid displayed.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > QA Grid not displayed.");
		} 
		
		return isFound;	

		
	}	
	
	public String ScanBoxids (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;
		String ret = "-1";
		
		int BoxCnt = InputArr.size();
		
		QAListObj QAListPageObj = new QAListObj(webdriver);

		SeleniumUtil.waitWebElementVisible(webdriver, QAListPageObj.getTblSearchResultLocator());
		
		WebElement tblResult = QAListPageObj.getTblSearchResult();		
        int tblRow = SeleniumUtil.getTableRows(tblResult);


			
		for (int i = 0; i < BoxCnt; i++) {
			
			tblResult = QAListPageObj.getTblSearchResult();
			String TblOldTxt = tblResult.getText();
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> BoxPara = (HashMap<String, String>) InputArr.get(i);
			
			String Boxid = BoxPara.get("Boxid").toString();
			String SN = BoxPara.get("SN").toString();
			
			WebElement currentRow = QAListPageObj.getRow(tblResult, tblRow-1);
			
			WebElement TxtBoxid = QAListPageObj.getTxtBoxid(currentRow);
			TxtBoxid.sendKeys(Boxid);	
			TxtBoxid.sendKeys(Keys.TAB);

			
			WebElement TxtManufacturingLableSN = QAListPageObj.getTxtManufacturingLableSN(currentRow);
			TxtManufacturingLableSN.sendKeys(SN);	
			TxtManufacturingLableSN.sendKeys(Keys.TAB);

			SeleniumUtil.waitWebElementProperty(webdriver, QAListPageObj.getTblSearchResultLocator(), TblOldTxt);
			
			//tblResult = QAListPageObj.getTblSearchResult();
			tblRow = SeleniumUtil.getTableRows(tblResult);
			
			
		
		}	
		
		ret = "0";
		return ret;
	}
	
	public String InitQA () throws NoSuchElementException {
		//RetVal:-1:error;0: success;
		String ret = "-1";
		
		QAListObj QAListPageObj = new QAListObj(webdriver);
		WebElement BtnInitialQA = QAListPageObj.getBtnInitialQA();
		BtnInitialQA.click();
		
		SeleniumUtil.isWebElementExist(webdriver, QAListPageObj.getPnlWaitTitleLocator(), waitloading);
		SeleniumUtil.isWebElementNoExist(webdriver, QAListPageObj.getPnlWaitTitleLocator(), waitloading);

		if (SeleniumUtil.isWebElementExist(webdriver, QAListPageObj.getBtnOKLocator(), 0)) {
			SeleniumUtil.waitWebElementVisible(webdriver, QAListPageObj.getBtnOKLocator());
		
			WebElement MsgRst = QAListPageObj.getMsgRst();
			CommUtil.logger.info(" > Msg:" +  MsgRst.getText());
			if (CommUtil.isMatchByReg(MsgRst.getText(), "QA Passed\\.")) {
				ret = "0";
			}
			
			WebElement BtnOK = QAListPageObj.getBtnOK();
			BtnOK.click();
			SeleniumUtil.isWebElementNoExist(webdriver, QAListPageObj.getBtnOKLocator(), waitloading);

		}
		
		return ret;
		
	}	
}
