package action_lib.odr_mngt;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.odr_mngt.OrderEntry2Obj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class OrderEntry2Action {
	WebDriver webdriver;

	public OrderEntry2Action(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String CreateOrderEntry2 (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//Mode:0,Standard;1,Repair(Expected SN);2,Repair only click add and next;3,only click next;4,repair fill expected SN without search;
		//RetVal:-1:error;0: success;1:Expected SN is invalid;2:Available Qty is 0
		String ret = "-1";
		
		String Mode = InputObj.get("Mode").toString();
		ArrayList<?> InputArr = (ArrayList<?>) InputObj.get("PartArr");		
		
		OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
		
		if (Mode.equals("0")) {
			
			if (InputArr!=null) {
				String retAddPart = AddStdParts(InputArr);
				
				if (!retAddPart.equals("0")) {
					return retAddPart;
				}
			}
		} else if(Mode.equals("1")) {
			if (InputArr!=null) {
				String retAddPart = AddRepairParts(InputArr);
				
				if (!retAddPart.equals("0")) {
					return retAddPart;
				}
			}
		} else if(Mode.equals("2")) {
			
			//SeleniumUtil.waitWebElementProperty(webdriver, Obj.getBtnNextLocator(), "visible", "true");
			SeleniumUtil.waitWebElementVisible(webdriver, Obj.getBtnNextLocator());
			boolean isLinkStandardAdExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLinkStandardAddlocator(), 0);
			if (isLinkStandardAdExist) {
				WebElement  LinkAdd = Obj.getLinkStandardAdd();
				LinkAdd.click();
				SeleniumUtil.waitPageRefresh(LinkAdd);
			} else {
				//to be delete start
/*				WebElement WebEleSelReceiveStockGroup = Obj.getSelReceiveStockGroup();
				Select SelReceiveStockGroup = new Select(WebEleSelReceiveStockGroup);
				SelReceiveStockGroup.selectByIndex(1);*/
				//to be delete end
				
				WebElement  LinkAdd = Obj.getLinkRepairAdd();
				LinkAdd.click();
				SeleniumUtil.waitPageRefresh(LinkAdd);				
			}
			
			boolean isLblMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
			if (isLblMessageExist) {
				WebElement lblMessage = Obj.getLblMessage();	
				CommUtil.logger.info(" > Msg: " + lblMessage.getText());
				ret = "-1";
				return ret;
			}


		} else if (Mode.equals("3")) {
			// solve the problem clicking next without showing page.
			//SeleniumUtil.waitWebElementProperty(webdriver, Obj.getTxtSearchPartNumLocator(), "visible", "true");
			SeleniumUtil.waitWebElementVisible(webdriver, Obj.getBtnNextLocator());
			
		} else if (Mode.equals("4")) {		
			@SuppressWarnings("unchecked")
			HashMap<String, String> PartPara = (HashMap<String, String>) InputArr.get(0);
			String ExpectedSN = PartPara.get("ExpectedSN").toString();
			String ReceiveStockGroup = PartPara.get("ReceiveStockGroup").toString();

			WebElement WebEleSelReceiveStockGroup = Obj.getSelReceiveStockGroup();
			Select SelReceiveStockGroup = new Select(WebEleSelReceiveStockGroup);
			
			boolean isHasVal = SeleniumUtil.isSelectHasOption(SelReceiveStockGroup, ReceiveStockGroup);
			if (!isHasVal) {
				CommUtil.logger.info(" > StockGroup option not found in UI. StockGroup:"+ReceiveStockGroup);
				ret = "-1";
				return ret;
			}
			
			SelReceiveStockGroup.selectByVisibleText(ReceiveStockGroup);
			
			WebElement TxtExpectedSN = Obj.getTxtExpectedSN();
			TxtExpectedSN.sendKeys(ExpectedSN);			

			WebElement  LinkAdd = Obj.getLinkRepairAdd();
			LinkAdd.click();

			SeleniumUtil.waitPageRefresh(LinkAdd);
			
			boolean isLblMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
			if (isLblMessageExist) {
				WebElement lblMessage = Obj.getLblMessage();	
				CommUtil.logger.info(" > Msg: " + lblMessage.getText());

				if (CommUtil.isMatchByReg(lblMessage.getText(), "SN is invalid\\.")) {
					ret = "1";
					return ret;
				} else {
					CommUtil.logger.info(" > Unhandled msg.");
					return ret;				
				}
			} 
		}

		boolean isTblAddListExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getTblAddListLocator(), 5);
		if (isTblAddListExist||InputArr.size() == 0) {
			WebElement  BtnNext = Obj.getBtnNext();
			BtnNext.click();
		}
		
		ret = "0";
		return ret;
	}
	public String CheckEntry2PartExist () throws NoSuchElementException  {
		//RetVal -1:error;0:Part exist;1:Part does not exist
		String ret = "-1";
		OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
		SeleniumUtil.waitWebElementVisible(webdriver, Obj.getBtnNextLocator());
		
		boolean isTblAddListExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getTblAddListLocator(), 0);
		if (isTblAddListExist) {
			WebElement  TblAddList = Obj.getTblAddList();
	        int tblRow = SeleniumUtil.getTableRows(TblAddList);
			if (tblRow > 1) {
				CommUtil.logger.info(" > Part info exist. Row:"+tblRow);
				ret = "0";
			} else {
				CommUtil.logger.info(" > Part info does not exist. Row:"+tblRow);
				ret = "1";
			} 
		} else {
			CommUtil.logger.info(" > Part table does not exist.");
		}
		
		return ret;
	}
	
	public String AddStdParts (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Expected SN is invalid;2:Available Qty is 0
		String ret = "-1";
		
		int PartCnt = InputArr.size();
			
		for (int i = 0; i < PartCnt; i++) {
			OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> PartPara = (HashMap<String, String>) InputArr.get(i);
			
			String SearchPartNum = PartPara.get("SearchPartNum").toString();
			//String SearchBOM = PartPara.get("SearchBOM").toString();
			String SearchSN = PartPara.get("SearchSN").toString();
			//String ExpectedSN = PartPara.get("ExpectedSN").toString();
			String StockGroup = PartPara.get("StockGroup").toString();
			String IsConfig = PartPara.get("IsConfig").toString();	
			String inReqQty = PartPara.get("ReqQty").toString();
			
			if (!SearchPartNum.equals("")) {
				WebElement TxtSearchPartNum = Obj.getTxtSearchPartNum();
				TxtSearchPartNum.sendKeys(SearchPartNum);	
			}
			
			if (!SearchSN.equals("")) {
				WebElement TxtSearchSN = Obj.getTxtSearchSN();
				TxtSearchSN.sendKeys(SearchSN);	
			}			
			
			WebElement  BtnFind = Obj.getBtnFind();
			BtnFind.click();
			
			if (SearchSN.equals("") && !StockGroup.equals("")) {
				WebElement WebEleSelStockGroup = Obj.getSelStockGroup();
				Select SelStockGroup = new Select(WebEleSelStockGroup);
				
				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStockGroup, StockGroup);
				if (!isHasVal) {
					CommUtil.logger.info(" > StockGroup option not found in UI. StockGroup:"+StockGroup);
					ret = "-1";
					return ret;
				}
				
				SelStockGroup.selectByVisibleText(StockGroup);
				
				boolean isAvailQtyExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getTxtAvailableQTYLocator(), 1);
				if (isAvailQtyExist) {
					SeleniumUtil.waitWebElementProperty(webdriver, Obj.getTxtAvailableQTYLocator(), "value", "");
					WebElement TxtAvailableQTY = Obj.getTxtAvailableQTY();
					String availableQty = TxtAvailableQTY.getAttribute("value");
					if (availableQty.equals("0")) {
						ret = "2";
						return ret;					
					}	
				}
				
				
				if (!inReqQty.equals("")) {
					WebElement TxtReqQTY = Obj.getTxtReqQTY();
					if (TxtReqQTY.isEnabled()) {
						TxtReqQTY.clear();
						TxtReqQTY.sendKeys(inReqQty);
					}
					
				}

			}
			
			//SeleniumUtil.waitPageRefresh(BtnFind);
			SeleniumUtil.waitWebElementProperty(webdriver, Obj.getBtnNextLocator(), "visible", "true");
			

			//CommUtil.logger.info(" > getLinkStandardAdd");
			WebElement  LinkAdd = Obj.getLinkStandardAdd();
			//CommUtil.logger.info(" > click");
			LinkAdd.click();

			SeleniumUtil.waitPageRefresh(LinkAdd);
			
			boolean isLblMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
			if (isLblMessageExist) {
				WebElement lblMessage = Obj.getLblMessage();	

				if (CommUtil.isMatchByReg(lblMessage.getText(), "No records found\\.")) {
					CommUtil.logger.info(" > Msg: No records found. Expected SN:" + SearchSN);
					ret = "1";
					return ret;
				}
			}
			
			if (!IsConfig.equals("")) {
				WebElement  ChkConfig = Obj.getChkConfig(i);
				boolean isChkConfigChecked =Boolean.parseBoolean(ChkConfig.getAttribute("checked"));
				
				if (IsConfig.equals("1")) {
					if (!isChkConfigChecked) {
						ChkConfig.click();
					}
				} else if (IsConfig.equals("0")) {
					if (isChkConfigChecked) {
						ChkConfig.click();
					}

				}

			}
		
		}	
		
		ret = "0";
		return ret;
	}
	public String AddRepairParts (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Expected SN is invalid;
		String ret = "-1";
		
		int PartCnt = InputArr.size();
			
		for (int i = 0; i < PartCnt; i++) {
			OrderEntry2Obj Obj = new OrderEntry2Obj(webdriver);
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> PartPara = (HashMap<String, String>) InputArr.get(i);
			
			String SearchPartNum = PartPara.get("SearchPartNum").toString();
			//String SearchBOM = PartPara.get("SearchBOM").toString();
			//String SearchSN = PartPara.get("SearchSN").toString();
			String ExpectedSN = PartPara.get("ExpectedSN").toString();
			//String IsConfig = PartPara.get("IsConfig").toString();	
			String ReceiveStockGroup = PartPara.get("ReceiveStockGroup").toString();
			
			WebElement TxtSearchPartNum = Obj.getTxtSearchPartNum();
			TxtSearchPartNum.sendKeys(SearchPartNum);	
			
			WebElement  BtnFind = Obj.getBtnFind();
			BtnFind.click();
			
			WebElement TxtExpectedSN = Obj.getTxtExpectedSN();
			TxtExpectedSN.sendKeys(ExpectedSN);	
			
			WebElement WebEleSelReceiveStockGroup = Obj.getSelReceiveStockGroup();
			Select SelReceiveStockGroup = new Select(WebEleSelReceiveStockGroup);
			
			boolean isHasVal = SeleniumUtil.isSelectHasOption(SelReceiveStockGroup, ReceiveStockGroup);
			if (!isHasVal) {
				CommUtil.logger.info(" > StockGroup option not found in UI. StockGroup:"+ReceiveStockGroup);
				ret = "-1";
				return ret;
			}
			
			SelReceiveStockGroup.selectByVisibleText(ReceiveStockGroup);
			

			WebElement  LinkAdd = Obj.getLinkRepairAdd();
			LinkAdd.click();

			SeleniumUtil.waitPageRefresh(LinkAdd);
			
			boolean isLblMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
			if (isLblMessageExist) {
				WebElement lblMessage = Obj.getLblMessage();	

				if (CommUtil.isMatchByReg(lblMessage.getText(), "SN is invalid\\.")) {
					CommUtil.logger.info(" > Msg: SN is invalid. Expected SN:" + ExpectedSN);
					ret = "1";
					return ret;
				}
			}
		
		}	
		
		ret = "0";
		return ret;
	}
}
