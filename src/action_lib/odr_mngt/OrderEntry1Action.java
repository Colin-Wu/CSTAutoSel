package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.odr_mngt.OrderEntry1Obj;
import script_lib.SeleniumUtil;

public class OrderEntry1Action {
	WebDriver webdriver;

	public OrderEntry1Action(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	public String CreateOrderEntry1 (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//Mode:0:Normal;1:Only click next
		//POLink:0:Do not click fill with order number link;1:click the link
		//RetVal:-1:error;0: success;
		String ret = "-1";
		
		String Mode = InputObj.get("Mode").toString();
		String Customer = InputObj.get("Customer").toString();
		String ProjectCode = InputObj.get("ProjectCode").toString();
		String Site = InputObj.get("Site").toString();
		String OrdType = InputObj.get("OrdType").toString();
		String Attribute = InputObj.get("Attribute").toString();
		String TemplateName = InputObj.get("TemplateName").toString();
		String ReqEmail = InputObj.get("ReqEmail").toString();
		String PO = InputObj.get("PO").toString();
		String POLink = InputObj.get("POLink").toString();
		String ProdLine = InputObj.get("ProdLine").toString();
		
		OrderEntry1Obj Obj = new OrderEntry1Obj(webdriver);
		
		if (Mode.equals("0")) {
			
			WebElement WebEleSelCustomer = Obj.getSelCustomer();
			Select SelCustomer = new Select(WebEleSelCustomer);
			SelCustomer.selectByVisibleText(Customer);
		
			SeleniumUtil.waitPageRefresh(WebEleSelCustomer);
	
			WebElement WebEleSelProject = Obj.getSelProject();
			Select SelProject = new Select(WebEleSelProject);
			SelProject.selectByVisibleText(ProjectCode);

			SeleniumUtil.waitPageRefresh(WebEleSelProject);

			WebElement TxtSite = Obj.getTxtSite();
			TxtSite.sendKeys(Site);	

			WebElement TxtQuickLookup = Obj.getTxtQuickLookup();
			TxtQuickLookup.click();
			SeleniumUtil.waitPageRefresh(TxtSite);
			
			WebElement WebEleSelOrderType = Obj.getSelOrderType();
			Select SelOrderType = new Select(WebEleSelOrderType);
			SelOrderType.selectByVisibleText(OrdType);

			SeleniumUtil.waitPageRefresh(WebEleSelOrderType);
			
			WebElement  WebEleOrderAttr = Obj.getSelOrderAttr();
			Select OrderAttr = new Select(WebEleOrderAttr);
			OrderAttr.selectByVisibleText(Attribute);
			
			SeleniumUtil.waitPageRefresh(WebEleOrderAttr);
			
			if (!TemplateName.equals("")) {
				WebElement  ChkTplt = Obj.getChkTplt();
				ChkTplt.click();
				WebElement TxtTmpltName = Obj.getTxtTmpltName();
				TxtTmpltName.sendKeys(TemplateName);					
			}			

			WebElement  TxtReqShipDate = Obj.getTxtReqShipDate();
			TxtReqShipDate.click();
			
			webdriver.switchTo().frame(Obj.getFrameDate());
			WebElement  BtnToday = Obj.getBtnToday();
			BtnToday.click();
			webdriver.switchTo().defaultContent();
			
			WebElement  TxtReqDeliveryDate = Obj.getTxtReqDeliveryDate();
			TxtReqDeliveryDate.click();
			
			webdriver.switchTo().frame(Obj.getFrameDate());
			BtnToday = Obj.getBtnToday();
			BtnToday.click();
			webdriver.switchTo().defaultContent();
					
			WebElement  TxtReqEmail = Obj.getTxtReqEmail();
			TxtReqEmail.sendKeys(ReqEmail);	
			
			WebElement WebEleSelSignature = Obj.getSelSignature();
			Select SelSignature = new Select(WebEleSelSignature);
			if (WebEleSelSignature.isEnabled()) {
				SelSignature.selectByIndex(1);
			}

			WebElement  LinkPO = Obj.getLinkPO();
			WebElement  TxtPO = Obj.getTxtPO();
			if (TxtPO.isEnabled() && !PO.equals("")) {
				TxtPO.sendKeys(PO);	
			} else if (!LinkPO.getAttribute("class").equals("aspNetDisabled") && POLink.equals("1")) {
				LinkPO.click();
				SeleniumUtil.waitPageRefresh(LinkPO);
			}

			WebElement  WebEleSelProdLine = Obj.getSelProdLine();
			Select SelProdLine = new Select(WebEleSelProdLine);
			if (WebEleSelProdLine.isEnabled() && !ProdLine.equals("")) {
				SelProdLine.selectByVisibleText(ProdLine);
			}
		
		} else if (Mode.equals("1")) {
			
			WebElement  LinkPO = Obj.getLinkPO();
			WebElement  TxtPO = Obj.getTxtPO();
			if (TxtPO.isEnabled() && !PO.equals("")) {
				TxtPO.sendKeys(PO);	
			} else if (!LinkPO.getAttribute("class").equals("aspNetDisabled")&& POLink.equals("1")) {
				LinkPO.click();
				SeleniumUtil.waitPageRefresh(LinkPO);
			}
			
		}
		WebElement  BtnNext = Obj.getBtnNext();
		BtnNext.click();
		
		if (POLink.equals("0")) {
			WebElement  BtnConfirmPOOK = Obj.getBtnConfirmPOOK();
			BtnConfirmPOOK.click();
		}
		
		ret = "0";
		
		return ret;
	}
}
