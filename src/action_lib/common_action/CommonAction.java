package action_lib.common_action;


import java.awt.AWTException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.DockReceivingAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.inv_mngt.InventoryLookupAction;
import action_lib.inv_mngt.PickAdminAction;
import action_lib.inv_mngt.PickEditAction;
import action_lib.inv_mngt.PickQueueAction;
import action_lib.inv_mngt.ReceivingPutawayAction;
import action_lib.inv_mngt.TransferActionAction;
import action_lib.inv_mngt.TransferAdminAction;
import action_lib.inv_mngt.TransferListAction;
import action_lib.inv_mngt.TransferRequestAction;
import action_lib.inv_mngt.TransferRequestForBoxAction;
import action_lib.odr_mngt.CreatePOAction;
import action_lib.odr_mngt.OrderAdminAction;
import action_lib.odr_mngt.OrderEntry1Action;
import action_lib.odr_mngt.OrderEntry2Action;
import action_lib.odr_mngt.OrderEntry3Action;
import action_lib.odr_mngt.OrderEntry4Action;
import action_lib.odr_mngt.OrderIndexAction;
import action_lib.odr_mngt.OrderSummaryAction;
import action_lib.odr_mngt.OrderUpdateAction;
import action_lib.odr_mngt.POMngtAction;
import action_lib.odr_mngt.TemplateAdminAction;
import action_lib.production.EvalQuoteAction;
import action_lib.production.ProdIndexAction;
import action_lib.production.RepairDetailAction;
import action_lib.production.RepairTechQueueAction;
import action_lib.qa.QAIndexAction;
import action_lib.qa.QAListAction;
import action_lib.setting.CustAdminAction;
import action_lib.setting.SettingIndexAction;
import action_lib.shipping.PreshipAction;
import action_lib.shipping.ShipIndexAction;
import action_lib.shipping.ShippingAction;
import action_lib.shipping.ShippingListAction;
import config.TestSetting;
import script_lib.CommUtil;



public class CommonAction {

	WebDriver webdriver;

	public CommonAction() {
		super();
	}
	public CommonAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	public String CompleteStandardRepairOrder_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		String retVal = "-1";
		//0: pick from inventory; 1: receive from customer
		String inPickMode = InputObj.get("PickMode").toString();	
		//0:send to ship, 1: return to stock
		String inShipMode = InputObj.get("ShipMode").toString();
		
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();
		
		String vBoxid = "";

			
		if (inPickMode.equals("0")) {
			String inUserName = InputObj.get("UserName").toString();	
			
			CommUtil.logger.info(" > PickParts_CA");
			HashMap<String, String> PickParts_CAInputObj = new HashMap<String, String>();
			PickParts_CAInputObj.put("ProjectCode", inProjectCode);					
			PickParts_CAInputObj.put("OrdNum", inOrdNum);					
			PickParts_CAInputObj.put("UserName", inUserName);					
			PickParts_CAInputObj.put("BoxID", "");					
			PickParts_CAInputObj.put("Qty", "");					
			PickParts_CAInputObj.put("QA", "");		
			HashMap<Object, Object> retObj = this.PickParts_CA(PickParts_CAInputObj);
	        retVal= (String) retObj.get("Ret");
			if(retVal.equals("0")) {
				CommUtil.logger.info("PickParts_CA - Success.");
			} else {
				CommUtil.logger.info("PickParts_CA - Failure. Code:"+retVal);
				return ret;	
			}	
		} else if (inPickMode.equals("1")) {
			String inPONum=InputObj.get("PONum").toString();		
		    String inPartNum = InputObj.get("PartNum").toString();
			String inCarrier=InputObj.get("Carrier").toString();
			String inSN=InputObj.get("SN").toString();
			String inTrackNum=InputObj.get("TrackNum").toString();
			
	        String inQty = "1";
			String inBoxCnt="1";
			String inPalletCnt ="1";
			String inBoxID="";
			String inLocation=TestSetting.RepairLocation;
			String inDisposition = "Good";
			
			HashMap<String, String> ReceiveToPutaway_CAInput=new HashMap<String, String>();
		      
			ReceiveToPutaway_CAInput.put("ProjectCode", inProjectCode);
			ReceiveToPutaway_CAInput.put("PartNum", inPartNum);
			ReceiveToPutaway_CAInput.put("Qty", inQty);
			ReceiveToPutaway_CAInput.put("PONum", inPONum);
			ReceiveToPutaway_CAInput.put("Carrier",inCarrier );
			ReceiveToPutaway_CAInput.put("BoxCnt", inBoxCnt);
			ReceiveToPutaway_CAInput.put("PalletCnt",inPalletCnt);
			ReceiveToPutaway_CAInput.put("SN", inSN);
			ReceiveToPutaway_CAInput.put("BoxID", inBoxID);
			ReceiveToPutaway_CAInput.put("Location", inLocation);
			ReceiveToPutaway_CAInput.put("Disposition", inDisposition);
			ReceiveToPutaway_CAInput.put("TrackNum", inTrackNum);
	        
	        CommUtil.logger.info("> ReceiveToPutaway_CA");
	        ArrayList<HashMap<String, String>> rstArr =  this.ReceiveToPutaway_CA(ReceiveToPutaway_CAInput);
	        if (rstArr == null) {
				CommUtil.logger.info("ReceiveToPutaway_CA - Failure. ");
				return ret;		        	
	        }
	        
	        HashMap<String, String> retObj = new HashMap<String, String>(); 
			retObj = rstArr.get(0);
			vBoxid = retObj.get("BoxID");
		} else {
			CommUtil.logger.info("Invalid pick mode. PickMode:"+inPickMode);
			return ret;			
		}
		
	
		HashMap<String, Serializable> StandardRepairParts_CAInputObj = new HashMap<String, Serializable>();
		StandardRepairParts_CAInputObj.put("Mode", inShipMode);					
		StandardRepairParts_CAInputObj.put("RepairDispName", "");					
		StandardRepairParts_CAInputObj.put("ProjectCode", inProjectCode);					
		StandardRepairParts_CAInputObj.put("OrdNum", inOrdNum);					
		StandardRepairParts_CAInputObj.put("BoxID", "");					
		StandardRepairParts_CAInputObj.put("SN", "");					
		
		CommUtil.logger.info(" > StandardRepairParts_CA");
		retVal = this.StandardRepairParts_CA(StandardRepairParts_CAInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("StandardRepairParts_CA - Success.");
		} else {
			CommUtil.logger.info("StandardRepairParts_CA - Failure. Code:"+retVal);
			return ret;	
		}	
	
		if (inShipMode.equals("0")) {
			String inKittingStatus = "PendVR";
			String inShippingStatus = "SHWIP";
			ArrayList<?> TrackArr = (ArrayList<?>) InputObj.get("TrackArr");	
			
			CommUtil.logger.info(" > UpdateOrder_CA");
			HashMap<String, String> UpdateOrder_CAInputObj = new HashMap<String, String>();
			UpdateOrder_CAInputObj.put("ProjectCode", inProjectCode);					
			UpdateOrder_CAInputObj.put("OrdNum", inOrdNum);					
			UpdateOrder_CAInputObj.put("Status", inKittingStatus);					
			UpdateOrder_CAInputObj.put("PONum", "");					
	
			retVal = this.UpdateOrder_CA(UpdateOrder_CAInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("UpdateOrder_CA - Success.");
			} else {
				CommUtil.logger.info("UpdateOrder_CA - Failure. Code:"+retVal);
				return ret;	
			}
			

			CommUtil.logger.info(" > ShipOutPart_CA");
			HashMap<String, Serializable> ShipOutPart_CAInputObj = new HashMap<String, Serializable>();
			ShipOutPart_CAInputObj.put("ProjectCode", inProjectCode);
			ShipOutPart_CAInputObj.put("OrdNum", inOrdNum);
			ShipOutPart_CAInputObj.put("BoxID", "");
			ShipOutPart_CAInputObj.put("KittingStatus", inKittingStatus);
			ShipOutPart_CAInputObj.put("ShippingStatus", inShippingStatus);
			ShipOutPart_CAInputObj.put("TrackArr", TrackArr);
			retVal = this.ShipOutPart_CA(ShipOutPart_CAInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("ShipOutPart_CA - Success.");
			} else {
				CommUtil.logger.info("ShipOutPart_CA - Failure. Code:"+InputObj);
				return ret;	
			}				
			
		} else if (inShipMode.equals("1")) {
			String inKittingStatus = "RepairComplete";
			String inSN = InputObj.get("SN").toString();
			String inPartNum = InputObj.get("PartNum").toString();
			String inPONum = InputObj.get("PONum").toString();
			//String inBoxID = InputObj.get("BoxID").toString();
			String inBoxID = vBoxid;

			String inLocation=TestSetting.StockLocation;
			
			CommUtil.logger.info(" > ReturnToStockPart_CA");
			HashMap<String, Serializable> ReturnToStockPart_CAInputObj = new HashMap<String, Serializable>();
			ReturnToStockPart_CAInputObj.put("ProjectCode", inProjectCode);
			ReturnToStockPart_CAInputObj.put("OrdNum", inOrdNum);
			ReturnToStockPart_CAInputObj.put("KittingStatus", inKittingStatus);
			ReturnToStockPart_CAInputObj.put("SN",inSN);
			ReturnToStockPart_CAInputObj.put("PalletID","");
			ReturnToStockPart_CAInputObj.put("BoxID",inBoxID);
			ReturnToStockPart_CAInputObj.put("Location",inLocation);
			ReturnToStockPart_CAInputObj.put("PartNum",inPartNum);
			ReturnToStockPart_CAInputObj.put("PONum",inPONum);
			ReturnToStockPart_CAInputObj.put("Qty","1");
			retVal = this.ReturnToStockPart_CA(ReturnToStockPart_CAInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("ReturnToStockPart_CA - Success.");
			} else {
				CommUtil.logger.info("ReturnToStockPart_CA - Failure. Code:"+retVal);
				return ret;	
			}	
		} else {
			CommUtil.logger.info("Invalid ship mode. ShipMode:"+inShipMode);
			return ret;					
		}
		

		
		ret = "0";
		return ret;
	}
	public String CompleteDeploymentOrder_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		String retVal = "-1";
		
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();
		String inUserName = InputObj.get("UserName").toString();
		String inQAMode = InputObj.get("QAMode").toString();
		//String inQAMode = "1";
		ArrayList<?> BoxArr = (ArrayList<?>) InputObj.get("Boxids");	
	    String caseid = "";
		Object caseidobj = InputObj.get("caseid");
		
		if (caseidobj != null) {
			caseid = caseidobj.toString();
		}	
		
		String inKittingStatus = "KITWIP";
		String inShippingStatus = "SHWIP";
/*		String inKittingMode = InputObj.get("KittingMode").toString();
		String inKittingStatus = InputObj.get("KittingStatus").toString();
		String inShippingStatus = InputObj.get("ShippingStatus").toString();*/
		ArrayList<?> TrackArr = (ArrayList<?>) InputObj.get("TrackArr");	
		
		CommUtil.logger.info(" > PickParts_CA");
		HashMap<String, String> PickParts_CAInputObj = new HashMap<String, String>();
		PickParts_CAInputObj.put("ProjectCode", inProjectCode);					
		PickParts_CAInputObj.put("OrdNum", inOrdNum);					
		PickParts_CAInputObj.put("UserName", inUserName);					
		PickParts_CAInputObj.put("BoxID", "");					
		PickParts_CAInputObj.put("Qty", "");					
		PickParts_CAInputObj.put("QA", inQAMode);		
		PickParts_CAInputObj.put("caseid", caseid);		
		HashMap<Object, Object> retObj = TestSetting.CA.PickParts_CA(webdriver, PickParts_CAInputObj);
        retVal= (String) retObj.get("Ret");
		if(retVal.equals("0")) {
			CommUtil.logger.info("PickParts_CA - Success.");
		} else {
			CommUtil.logger.info("PickParts_CA - Failure. Code:"+retVal);
			return ret;	
		}		
	
/*		ArrayList<HashMap<String, String>> boxArr = new ArrayList<HashMap<String, String>>();
		if (BoxArr.size() == 0) {
			
			@SuppressWarnings("unchecked")
			List<String> Boxidlst = (List<String>) retObj.get("BoxIDs");
			
			for (int i=0; i < Boxidlst.size(); i++) {
				
				String boxid = Boxidlst.get(i);
	
				CommUtil.logger.info("> SearchInventory");
				InventoryLookupAction InvLookup = new InventoryLookupAction(webdriver);
				HashMap<String, String> InventoryLookupObj = new HashMap<String, String>();
				InventoryLookupObj.put("PartNum","");
				InventoryLookupObj.put("SearchSN","");
				InventoryLookupObj.put("SearchPONum","");
				InventoryLookupObj.put("ProjectCode",inProjectCode);
				InventoryLookupObj.put("SearchStatus","");
				InventoryLookupObj.put("PalletID","");
				InventoryLookupObj.put("StockGroup",TestSetting.StockGroup);
				InventoryLookupObj.put("Qty", "1"); 
				InventoryLookupObj.put("SearchBoxid", boxid);
				HashMap<Object, Object> OutPutInventoryObj=InvLookup.SearchInventory(InventoryLookupObj);
				String outIsFound = OutPutInventoryObj.get("IsFound").toString();
				
				if (outIsFound.equals("1")) {
					CommUtil.logger.info("> SearchInventory Item found.");
				} else if(outIsFound.equals("0")) {
					CommUtil.logger.info(">SearchInventory Item not found. SN: " + InventoryLookupObj);
					return retVal;
				} else {
					CommUtil.logger.info(">SearchInventory Item found.Error..");
					return retVal;	
				}	
				
				@SuppressWarnings("unchecked")
				ArrayList<HashMap<String, String>> rstArr = (ArrayList<HashMap<String, String>>) OutPutInventoryObj.get("RstArr");
				HashMap<String, String> lookupretObj = new HashMap<String, String>(); 
				lookupretObj = rstArr.get(0);
				String SN = lookupretObj.get("SN");
				
				HashMap<String, String> boxMap = new HashMap<String, String>();
				boxMap.put("Boxid", boxid);
				boxMap.put("SN", SN);
				
				boxArr.add(boxMap);
				
			}
						
		}*/
		
		if (inQAMode.equals("1")) {
			CommUtil.logger.info(" > CompleteQA_CA");
			HashMap<String, Serializable> CompleteQA_CAInputObj = new HashMap<String, Serializable>();
			CompleteQA_CAInputObj.put("Mode", inQAMode);					
			CompleteQA_CAInputObj.put("OrdNum", inOrdNum);
			if (BoxArr.size() != 0) {
				CompleteQA_CAInputObj.put("Boxids", BoxArr);
			} else {
	//			CompleteQA_CAInputObj.put("Boxids", boxArr);
			}
			retVal = this.CompleteQA_CA(CompleteQA_CAInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CompleteQA_CA - Success.");
			} else {
				CommUtil.logger.info("CompleteQA_CA - Failure. Code:"+InputObj);
				return ret;	
			}	
		}
	
		CommUtil.logger.info(" > ShipOutPart_CA");
		HashMap<String, Serializable> ShipOutPart_CAInputObj = new HashMap<String, Serializable>();
		ShipOutPart_CAInputObj.put("ProjectCode", inProjectCode);
		ShipOutPart_CAInputObj.put("OrdNum", inOrdNum);
		ShipOutPart_CAInputObj.put("BoxID", "");
		ShipOutPart_CAInputObj.put("KittingStatus", inKittingStatus);
		ShipOutPart_CAInputObj.put("ShippingStatus", inShippingStatus);
		ShipOutPart_CAInputObj.put("TrackArr", TrackArr);
		retVal = this.ShipOutPart_CA(ShipOutPart_CAInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("ShipOutPart_CA - Success.");
		} else {
			CommUtil.logger.info("ShipOutPart_CA - Failure. Code:"+InputObj);
			return ret;	
		}	
		
		ret = "0";
		return ret;
	}
	public String CompleteQA_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//mode:0:search ordernumber; 1: without searching order number
		String ret = "-1";
		String retVal = "-1";
		QAListAction qalstpage;
		
		String inMode = InputObj.get("Mode").toString();
		CommUtil.logger.info("> CompleteQA_CA: Mode:"+inMode);
		ArrayList<?> Boxids = (ArrayList<?>) InputObj.get("Boxids");	
		
		if (inMode.equals("0")) {
	
			CommUtil.logger.info("> MenuQA");
			CST_MainAction mainpage = new CST_MainAction(webdriver);
			mainpage.MenuQA();	
			
			CommUtil.logger.info("> GoToQADeployment");
			QAIndexAction qaidxpage = new QAIndexAction(webdriver);
			qaidxpage.GoToQADeployment();
			
			CommUtil.logger.info("> SearchOrdNum");
			qalstpage = new QAListAction(webdriver);

			try {
				retVal = qalstpage.SearchOrdNum(InputObj);
			} catch (AWTException e) {
				CommUtil.logger.info("SearchOrdNum - AWTexception.");
				return ret;
			}
			if(retVal.equals("1")) {
				CommUtil.logger.info("SearchOrdNum - Success");
			} else {
				CommUtil.logger.info("SearchOrdNum - Failure. Code:"+retVal);
				return ret;
			}	
		}
		
		CommUtil.logger.info("> ScanBoxids");
		qalstpage = new QAListAction(webdriver);
		retVal = qalstpage.ScanBoxids(Boxids);
		if(retVal.equals("0")) {
			CommUtil.logger.info("ScanBoxids - Success");
		} else {
			CommUtil.logger.info("ScanBoxids - Failure. Code:"+retVal);
			return ret;
		}	
		
		CommUtil.logger.info("> InitQA");
		retVal = qalstpage.InitQA();
		if(retVal.equals("0")) {
			CommUtil.logger.info("InitQA - Success");
		} else {
			CommUtil.logger.info("InitQA - Failure. Code:"+retVal);
			return ret;
		}			

		ret = "0";
		return ret;
	}
	
	public String RequestTransfer_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		String inTransType = InputObj.get("TransType").toString();
		String inTransByType = InputObj.get("TransByType").toString();
		String inCustomer = InputObj.get("Customer").toString();
		String inFromProj = InputObj.get("FromProj").toString();
		String inToProj = InputObj.get("ToProj").toString();
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> InputArr = (ArrayList<HashMap<String, String>>) InputObj.get("RequestBoxIds");
		
		CommUtil.logger.info("> MenuInventory");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
			
		CommUtil.logger.info("> GotoTransferRequest");
		InvIndexAction invidx = new InvIndexAction(webdriver);
		invidx.GotoTransferRequest();
		
		CommUtil.logger.info("> FillTransferRequest");
		TransferRequestAction trans = new TransferRequestAction(webdriver); 
		HashMap<String, String> InputObject = new HashMap<String, String>();
		InputObject.put("TransType", inTransType);
		InputObject.put("TransByType", inTransByType);
		InputObject.put("Customer", inCustomer);
		InputObject.put("FromProj", inFromProj);
		InputObject.put("ToProj", inToProj);	
		String retstr = trans.FillTransferRequest(InputObject);
		if (!retstr.equals("0")) {
			CommUtil.logger.info(">FillTransferRequest.Error..");
			return ret;	
		}	
		
		CommUtil.logger.info("> TransferRequest.GoNext");		
		retstr = trans.GoNext();
		if (!retstr.equals("0")) {
			CommUtil.logger.info(">TransferRequest.GoNext.Error..");
			return ret;	
		}	
		
		CommUtil.logger.info("> FillTransRequestForBox");		
		TransferRequestForBoxAction transbox = new TransferRequestForBoxAction(webdriver); 
		retstr = transbox.FillTransRequestForBox(InputArr);
		if (!retstr.equals("0")) {
			CommUtil.logger.info(">FillTransRequestForBox.Error..");
			return ret;	
		}
		
		CommUtil.logger.info("> TransferRequest.Save");		
		ret = transbox.Save();
		if(ret.equals("0")) {
			CommUtil.logger.info("TransferRequest.Save - Success");
		} else {
			CommUtil.logger.info("TransferRequest.Save - Failure. Code:"+ret);
			return ret;
		}			

		return ret;
	}
	
	public String TransferAdminAssignUser_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		String inTransType = InputObj.get("TransType").toString();
		String inTransByType = InputObj.get("TransByType").toString();
		String inSearchUsername = InputObj.get("SearchUsername").toString();
		String inUsername = InputObj.get("Username").toString();
		String inStatus = InputObj.get("Status").toString();
		String inTransNum = InputObj.get("TransNum").toString();
		String inBoxId = InputObj.get("BoxId").toString();

		
		CommUtil.logger.info("> MenuInventory");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		CommUtil.logger.info("> GotoTransferAdmin");
		InvIndexAction invidx = new InvIndexAction(webdriver);
		invidx.GotoTransferAdmin();
		
		CommUtil.logger.info("> SearchTransferAdmin");
		TransferAdminAction transadmin = new TransferAdminAction(webdriver); 
		HashMap<String, String> InputObject = new HashMap<String, String>();
		InputObject.put("TransType", inTransType);
		InputObject.put("TransByType", inTransByType);
		InputObject.put("Username", inSearchUsername);
		InputObject.put("Status", inStatus);
		InputObject.put("TransNum", inTransNum);	
		String retVal = transadmin.SearchTransferAdmin(InputObject);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchTransferAdmin - Success");
		} else {
			CommUtil.logger.info("SearchTransferAdmin - Failure. Code:"+retVal);
			return ret;
		}			
		
		
		CommUtil.logger.info("> GetResultItemByBoxID");
		HashMap<String, String> Retobj = transadmin.GetResultItemByBoxID(inBoxId);
		String IsFound = Retobj.get("IsFound").toString();
		String row = Retobj.get("Row").toString();
		String TransNum = Retobj.get("TransNum").toString();

		if (IsFound.equals("1")) {
			CommUtil.logger.info("GetResultItemByBoxID - Success");
			
			InputObject = new HashMap<String, String>();
			InputObject.put("Username", inUsername);
			InputObject.put("Row", row);
			
			CommUtil.logger.info("> AssignUserForTransfer");
			retVal = transadmin.AssignUserForTransfer(InputObject);
			if(retVal.equals("0")) {
				CommUtil.logger.info("AssignUserForTransfer - Success");
			} else {
				CommUtil.logger.info("AssignUserForTransfer - Failure. Code:"+retVal);
				return ret;
			}	
		} else {
			CommUtil.logger.info("GetResultItemByBoxID - Failure. Code:"+IsFound);
			return ret;
		}	

		ret = TransNum;
		return ret;
	}
	
	public String TransferAction_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		String inPalletID = InputObj.get("PalletID").toString();
		String inBoxID = InputObj.get("BoxID").toString();
		String inStatus = InputObj.get("Status").toString();
		String inTransNum = InputObj.get("TransNum").toString();
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> InputArr = (ArrayList<HashMap<String, String>>) InputObj.get("ScanBoxIds");
		
		CommUtil.logger.info("> MenuInventory");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		CommUtil.logger.info("> GotoTransferAction");
		InvIndexAction invidx = new InvIndexAction(webdriver);
		invidx.GotoTransferAction();
		
		TransferActionAction transaction = new TransferActionAction(webdriver); 
		HashMap<String, String> InputObject = new HashMap<String, String>();
		InputObject.put("PalletID", inPalletID);
		InputObject.put("BoxID", inBoxID);
		InputObject.put("Status", inStatus);
		CommUtil.logger.info("> SearchTransferAction");
		String retstr = transaction.SearchTransferAction(InputObject);
		if(retstr.equals("1")) {
			CommUtil.logger.info("SearchTransferAction - Success");
		} else {
			CommUtil.logger.info("SearchTransferAction - Failure. Code:"+retstr);
			return ret;
		}	
		
		CommUtil.logger.info("> SelectTransfer");
		retstr = transaction.SelectTransfer(inTransNum);
		if (!retstr.equals("0")) {
			CommUtil.logger.info(">SelectTransfer Error..");
			return ret;	
		}	

		CommUtil.logger.info("> FillTransferList");
		TransferListAction translistbox = new TransferListAction(webdriver); 
		retstr = translistbox.FillTransferList(InputArr);
		if(retstr.equals("0")) {
			CommUtil.logger.info("FillTransferList - Success");
		} else {
			CommUtil.logger.info("FillTransferList - Failure. Code:"+retstr);
			return ret;
		}	
		
		CommUtil.logger.info("> TransferList.Save");		
		ret = translistbox.Save();
		if(ret.equals("0")) {
			CommUtil.logger.info("TransferList.Save - Success");
		} else {
			CommUtil.logger.info("TransferList.Save - Failure. Code:"+retstr);
			return ret;
		}	

		return ret;
	}
	
	public String CompleteTransferRequest_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		String TransNum = "";
		
		String inTransType = InputObj.get("TransType").toString();
		String inTransByType = InputObj.get("TransByType").toString();
		String inCustomer = InputObj.get("Customer").toString();
		String inFromProj = InputObj.get("FromProj").toString();
		String inToProj = InputObj.get("ToProj").toString();
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> RequestBoxIds = (ArrayList<HashMap<String, String>>) InputObj.get("RequestBoxIds");		
		
		String inSearchUsername = InputObj.get("SearchUsername").toString();
		String inUsername = InputObj.get("Username").toString();
		String inStatus = InputObj.get("Status").toString();
		String inSearchTransNum = InputObj.get("SearchTransNum").toString();
		String inBoxId = InputObj.get("BoxId").toString();
		
		String inSearchPalletID = InputObj.get("SearchPalletID").toString();
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> ScanBoxIds = (ArrayList<HashMap<String, String>>) InputObj.get("ScanBoxIds");
		
		CommUtil.logger.info("> RequestTransfer_CA");
		HashMap<String, Serializable> RequestTransfer_CAInputObject = new HashMap<String, Serializable>();
		RequestTransfer_CAInputObject.put("TransType", inTransType);
		RequestTransfer_CAInputObject.put("TransByType", inTransByType);
		RequestTransfer_CAInputObject.put("Customer", inCustomer);
		RequestTransfer_CAInputObject.put("FromProj", inFromProj);
		RequestTransfer_CAInputObject.put("ToProj", inToProj);
		RequestTransfer_CAInputObject.put("RequestBoxIds", RequestBoxIds);
		String retVal = RequestTransfer_CA(RequestTransfer_CAInputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("RequestTransfer_CA - Success");
		} else {
			CommUtil.logger.info("RequestTransfer_CA - Failure. Code:"+retVal);
			return ret;
		}	
		
		CommUtil.logger.info("> TransferAdminAssignUser_CA");
		HashMap<String, String> TransferAdminInputObject = new HashMap<String, String>();
		TransferAdminInputObject.put("TransType", inTransType);
		TransferAdminInputObject.put("TransByType", inTransByType);
		TransferAdminInputObject.put("SearchUsername", inSearchUsername);
		TransferAdminInputObject.put("Username", inUsername);
		TransferAdminInputObject.put("Status", inStatus);
		TransferAdminInputObject.put("TransNum", inSearchTransNum);
		TransferAdminInputObject.put("BoxId", inBoxId);
		retVal = TransferAdminAssignUser_CA(TransferAdminInputObject);
		if(!retVal.equals("-1")) {
			CommUtil.logger.info("TransferAdminAssignUser_CA - Success");
			TransNum = retVal;
		} else {
			CommUtil.logger.info("TransferAdminAssignUser_CA - Failure. Code:"+retVal);
			return ret;
		}	
		
		CommUtil.logger.info("> TransferAction_CA");
		HashMap<String, Serializable> TransferAction_CAInputObject = new HashMap<String, Serializable>();
		TransferAction_CAInputObject.put("PalletID", inSearchPalletID);
		TransferAction_CAInputObject.put("BoxID", inBoxId);
		TransferAction_CAInputObject.put("Status", inStatus);
		TransferAction_CAInputObject.put("TransNum", TransNum);
		TransferAction_CAInputObject.put("ScanBoxIds", ScanBoxIds);
		ret = TransferAction_CA(TransferAction_CAInputObject);
		if(ret.equals("0")) {
			CommUtil.logger.info("TransferAction_CA - Success");
		} else {
			CommUtil.logger.info("TransferAction_CA - Failure. Code:"+retVal);
			return ret;
		}	

		return ret;
	}
	
	public String ShipOutPart_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		
		//String inMode = InputObj.get("Mode").toString();
		String inMode = "0";
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();
		String inBoxID = InputObj.get("BoxID").toString();
		String inKittingStatus = InputObj.get("KittingStatus").toString();
		String inShippingStatus = InputObj.get("ShippingStatus").toString();
		ArrayList<?> InputArr = (ArrayList<?>) InputObj.get("TrackArr");		
		
		CommUtil.logger.info("> Kitting_CA");
		HashMap<String, String> Kitting_CAInputObj = new HashMap<String, String>();
		Kitting_CAInputObj.put("Mode", inMode);					
		Kitting_CAInputObj.put("ProjectCode", inProjectCode);					
		Kitting_CAInputObj.put("OrdNum", inOrdNum);					
		Kitting_CAInputObj.put("BoxID", inBoxID);					
		Kitting_CAInputObj.put("Status", inKittingStatus);
		String retVal = Kitting_CA(Kitting_CAInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("Kitting_CA - Success");
		} else {
			CommUtil.logger.info("Kitting_CA - Failure. Code:"+retVal);
			return ret;
		}	
		
		HashMap<String, Serializable> Shipping_CAInputObj = new HashMap<String, Serializable>();				
		Shipping_CAInputObj.put("ProjectCode", inProjectCode);					
		Shipping_CAInputObj.put("OrdNum", inOrdNum);					
		Shipping_CAInputObj.put("BoxID", inBoxID);					
		Shipping_CAInputObj.put("Status", inShippingStatus);		
		Shipping_CAInputObj.put("TrackArr", InputArr);		
		retVal = Shipping_CA(Shipping_CAInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("Shipping_CA - Success");
		} else {
			CommUtil.logger.info("Shipping_CA - Failure. Code:"+retVal);
			return ret;
		}
			

		ret = "0";
		
		return ret;

	}
	public String ReturnToStockPart_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		
		//String inMode = InputObj.get("Mode").toString();
		String inMode = "1";
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();
		String inBoxID = InputObj.get("BoxID").toString();
		String inKittingStatus = InputObj.get("KittingStatus").toString();

		String inSN = InputObj.get("SN").toString();
		String inLocation = InputObj.get("Location").toString();
		String inPartNum = InputObj.get("PartNum").toString();
		String inPONum = InputObj.get("PONum").toString();
		String inQty = InputObj.get("Qty").toString();

		
		CommUtil.logger.info("> Kitting_CA");
		HashMap<String, String> Kitting_CAInputObj = new HashMap<String, String>();
		Kitting_CAInputObj.put("Mode", inMode);					
		Kitting_CAInputObj.put("ProjectCode", inProjectCode);					
		Kitting_CAInputObj.put("OrdNum", inOrdNum);					
		Kitting_CAInputObj.put("BoxID", "");					
		Kitting_CAInputObj.put("Status", inKittingStatus);
		String retVal = Kitting_CA(Kitting_CAInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("Kitting_CA - Success");
		} else {
			CommUtil.logger.info("Kitting_CA - Failure. Code:"+retVal);
			return ret;
		}	


		
    	HashMap<String, String> InputPutAway=new HashMap<String, String>();
    	InputPutAway.put("SN",inSN);
    	InputPutAway.put("ProjectCode",inProjectCode);
    	InputPutAway.put("PalletID","");
    	InputPutAway.put("BoxID",inBoxID);
    	InputPutAway.put("Location",inLocation);
    	InputPutAway.put("PartNum",inPartNum);
    	InputPutAway.put("PONum",inPONum);
    	InputPutAway.put("Qty",inQty);
    	InputPutAway.put("StockGroup","");
    	
    	 CommUtil.logger.info(">PutawayToInv_CA  ..."+InputPutAway);
    	
    	HashMap<Object, Object> OutputInvputaway=this.PutawayToInv_CA(InputPutAway);
    	String outIsFound= OutputInvputaway.get("IsFound").toString();
    	@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, String>> rstArr= (ArrayList<HashMap<String, String>>) OutputInvputaway.get("RstArr");
    
    	if(outIsFound.equals("1")){
    		CommUtil.logger.info(">PutawayToInv.Success: " +inPartNum+" Result Array size: "+rstArr.size());
/*    		for (int i = 0; i < rstArr.size(); i++) {
    			HashMap<String, String> retObj = rstArr.get(i);
    			CommUtil.logger.info(">Result Obj : "+i);
    			for (Entry<String, String> entry : retObj.entrySet()) {
    				CommUtil.logger.info("> Key:" + entry.getKey()+" value: "+ entry.getValue());
    			}
    		}*/
    		
    		
    	}
    	else if (outIsFound.equals("0")) {
    		CommUtil.logger.info(">ReceiveToPutaway.Part not found. PartNum: " +inPartNum );
			return ret;
    	}
    	else
    	{
    		CommUtil.logger.info(">ReceiveToPutaway.Error. PartNum: " +inPartNum );
			return ret;
    	}
			
		ret = "0";	
		return ret;

	}	
	public String Shipping_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		String ret = "-1";
		
		CommUtil.logger.info("> MenuShipping");
		CST_MainAction cstMain= new CST_MainAction(webdriver);
		cstMain.MenuShipping();
		
		CommUtil.logger.info("> GoToShipping");
		ShipIndexAction ShipIndex = new ShipIndexAction(webdriver);
		ShipIndex.GoToShipping();

		ShippingListAction Shiplistpage = new ShippingListAction(webdriver);
			
		CommUtil.logger.info("> SearchShipping");				
		String retVal = Shiplistpage.SearchShipping(InputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchShipping - Success");
		} else {
			CommUtil.logger.info("SearchShipping - Failure. Code:"+retVal);
			return ret;
		}				

		CommUtil.logger.info("> ShipListGoToShipping");
		retVal = Shiplistpage.ShipListGoToShipping(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("ShipListGoToShipping - Success");
		} else {
			CommUtil.logger.info("ShipListGoToShipping - Failure. Code:"+retVal);
			return ret;
		}
		
		
		ShippingAction Shippage = new ShippingAction(webdriver);
		
		CommUtil.logger.info("> Ship");				
		retVal = Shippage.Ship(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("Ship - Success");
		} else {
			CommUtil.logger.info("Ship - Failure. Code:"+retVal);
			return ret;
		}				
			

		ret = "0";
		
		return ret;

	}
	public String Kitting_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//'Mode:0:send to ship, 1: return to stock
		String inMode = InputObj.get("Mode").toString();

		String ret = "-1";
		
		CommUtil.logger.info("> MenuShipping");
		CST_MainAction cstMain= new CST_MainAction(webdriver);
		cstMain.MenuShipping();
		
		CommUtil.logger.info("> GoToKitting");
		ShipIndexAction ShipIndex = new ShipIndexAction(webdriver);
		ShipIndex.GoToKitting();

		PreshipAction Preshippage = new PreshipAction(webdriver);
			
		CommUtil.logger.info("> SearchKitting");				
		String retVal = Preshippage.SearchKitting(InputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchKitting - Success");
		} else {
			CommUtil.logger.info("SearchKitting - Failure. Code:"+retVal);
			return ret;
		}				

		CommUtil.logger.info("> GoEditKitting");
		retVal = Preshippage.GoEditKitting(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("GoEditKitting - Success");
		} else {
			CommUtil.logger.info("GoEditKitting - Failure. Code:"+retVal);
			return ret;
		}
		
		
		if (inMode.equals("0")) {
			CommUtil.logger.info("> SendToShipping");				
			retVal = Preshippage.SendToShipping();
			if(retVal.equals("0")) {
				CommUtil.logger.info("SendToShipping - Success");
			} else {
				CommUtil.logger.info("SendToShipping - Failure. Code:"+retVal);
				return ret;
			}				
		} else if (inMode.equals("1")) {
			CommUtil.logger.info("> ReturnToStock");				
			retVal = Preshippage.ReturnToStock();
			if(retVal.equals("0")) {
				CommUtil.logger.info("ReturnToStock - Success");
			} else {
				CommUtil.logger.info("ReturnToStock - Failure. Code:"+retVal);
				return ret;
			}			
		} else {
			CommUtil.logger.info("Invalid mode:"+inMode);
			return ret;			
		}
		
		ret = "0";
		
		return ret;

	}
	public String AddHPPartToRepair_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		ArrayList<?> inOrdLines = (ArrayList<?>) InputObj.get("OrdLines");	

		String ret = "-1";
		
		CommUtil.logger.info("> MenuProduction");
		CST_MainAction cstMain= new CST_MainAction(webdriver);
		cstMain.MenuProduction();
		
		CommUtil.logger.info("> GoToRepairTechQueue");
		ProdIndexAction ProdIndex = new ProdIndexAction(webdriver);
		ProdIndex.GoToRepairTechQueue();;

		RepairTechQueueAction RepairTechQueue = new RepairTechQueueAction(webdriver);
		
		CommUtil.logger.info("> SearchRepairTechQueue");				
		String retVal = RepairTechQueue.SearchRepairTechQueue(InputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchRepairTechQueue - Success");
		} else {
			CommUtil.logger.info("SearchRepairTechQueue - Failure. Code:"+retVal);
			return ret;
		}	
		
		CommUtil.logger.info("> GoToEvalQuote");				
		retVal = RepairTechQueue.GoToEvalQuote(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("GoToEvalQuote - Success");
		} else {
			CommUtil.logger.info("GoToEvalQuote - Failure. Code:"+retVal);
			return ret;
		}	
		
		
		EvalQuoteAction EvalQuote = new EvalQuoteAction(webdriver);
		
		CommUtil.logger.info("> AddNewEvalQOrdLine");				
		retVal = EvalQuote.AddNewEvalQOrdLine(inOrdLines);
		if(retVal.equals("0")) {
			CommUtil.logger.info("AddNewEvalQOrdLine - Success");
		} else {
			CommUtil.logger.info("AddNewEvalQOrdLine - Failure. Code:"+retVal);
			return ret;
		}	
		
		CommUtil.logger.info("> GoToRepairDetail");				
		retVal = EvalQuote.GoToRepairDetail();
		if(retVal.equals("1")) {
			CommUtil.logger.info("GoToRepairDetail - Success");
		} else {
			CommUtil.logger.info("GoToRepairDetail - Failure. Code:"+retVal);
			return ret;
		}	
		
		ret = "0";
		
		return ret;

	}
	public String StandardRepairParts_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//Mode:0:ship to vendor, 1: repaircomplete
		String inMode = InputObj.get("Mode").toString();

		String ret = "-1";
		
		CommUtil.logger.info("> MenuProduction");
		CST_MainAction cstMain= new CST_MainAction(webdriver);
		cstMain.MenuProduction();
		
		CommUtil.logger.info("> GoToRepairTechQueue");
		ProdIndexAction ProdIndex = new ProdIndexAction(webdriver);
		ProdIndex.GoToRepairTechQueue();;

		RepairTechQueueAction RepairTechQueue = new RepairTechQueueAction(webdriver);
		
		CommUtil.logger.info("> SearchRepairTechQueue");				
		String retVal = RepairTechQueue.SearchRepairTechQueue(InputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchRepairTechQueue - Success");
		} else {
			CommUtil.logger.info("SearchRepairTechQueue - Failure. Code:"+retVal);
			return ret;
		}	
		
		CommUtil.logger.info("> GoToEvalQuote");				
		retVal = RepairTechQueue.GoToEvalQuote(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("GoToEvalQuote - Success");
		} else {
			CommUtil.logger.info("GoToEvalQuote - Failure. Code:"+retVal);
			return ret;
		}	
		
		
		EvalQuoteAction EvalQuote = new EvalQuoteAction(webdriver);
			
		CommUtil.logger.info("> GoToRepairDetail");				
		retVal = EvalQuote.GoToRepairDetail();
		if(retVal.equals("0")) {
			CommUtil.logger.info("GoToRepairDetail - Success");
		} else {
			CommUtil.logger.info("GoToRepairDetail - Failure. Code:"+retVal);
			return ret;
		}	
		
		RepairDetailAction repairdetail = new RepairDetailAction(webdriver);
		
		CommUtil.logger.info("> FillRepairDetailInfo");				
		retVal = repairdetail.FillRepairDetailInfo();
		if(retVal.equals("0")) {
			CommUtil.logger.info("FillRepairDetailInfo - Success");
		} else {
			CommUtil.logger.info("FillRepairDetailInfo - Failure. Code:"+retVal);
			return ret;
		}
	
		if (inMode.equals("1")) {
			CommUtil.logger.info("> RepairComplete");				
			retVal = repairdetail.RepairComplete(InputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("RepairComplete - Success");
			} else {
				CommUtil.logger.info("RepairComplete - Failure. Code:"+retVal);
				return ret;
			}
		} else if (inMode.equals("0")) {
			CommUtil.logger.info("> ShipToVendor");				
			retVal = repairdetail.ShipToVendor(InputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("ShipToVendor - Success");
			} else {
				CommUtil.logger.info("ShipToVendor - Failure. Code:"+retVal);
				return ret;
			}			
		}

		
		ret = "0";
		
		return ret;

	}
	
	// Multi thread issue: one pick at a time
	@SuppressWarnings("unchecked")
	public synchronized HashMap<Object, Object> PickParts_CA (WebDriver PickPartswebdriver, HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();

		String ret = "-1";
		List<String> Boxidlst = new ArrayList<String>();
		
		HashMap<Object, Object> RstObj = new HashMap<Object, Object>();
		RstObj.put("Ret", ret);
		RstObj.put("BoxIDs", Boxidlst);
		
		CommUtil.logger.info("> MenuInventory");
		CST_MainAction cstMain= new CST_MainAction(PickPartswebdriver);
		cstMain.MenuInventory();
		
		CommUtil.logger.info("> GotoPickAdmin");
		InvIndexAction InvIndex = new InvIndexAction(PickPartswebdriver);
		InvIndex.GotoPickAdmin();

		PickAdminAction PickAdminpage = new PickAdminAction(PickPartswebdriver);
			
		CommUtil.logger.info("> SearchPickAdmin");
		HashMap<String, String> SearchPickAdminInputObj = new HashMap<String, String>();
		SearchPickAdminInputObj.put("ProjectCode", inProjectCode);					
		SearchPickAdminInputObj.put("OrdNum", inOrdNum);					
		SearchPickAdminInputObj.put("UserName", "");					
		String retVal = PickAdminpage.SearchPickAdmin(SearchPickAdminInputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchPickAdmin - Success");
		} else {
			CommUtil.logger.info("SearchPickAdmin - Failure. Code:"+retVal);
			return RstObj;
		}				

		CommUtil.logger.info("> SavePickAdmin");
		retVal = PickAdminpage.SavePickAdmin(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("SavePickAdmin - Success");
		} else {
			CommUtil.logger.info("SavePickAdmin - Failure. Code:"+retVal);
			return RstObj;
		}
		
		CommUtil.logger.info("> MenuInventory");
		cstMain= new CST_MainAction(PickPartswebdriver);
		cstMain.MenuInventory();
		
		CommUtil.logger.info("> GotoPickQueue");
		InvIndex = new InvIndexAction(PickPartswebdriver);
		InvIndex.GotoPickQueue();;

		PickQueueAction PickQueuepage = new PickQueueAction(PickPartswebdriver);
		
		CommUtil.logger.info("> SearchPickQueue");
		retVal = PickQueuepage.SearchPickQueue(InputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchPickQueue - Success");
		} else {
			CommUtil.logger.info("SearchPickQueue - Failure. Code:"+retVal);
			return RstObj;
		}				

		CommUtil.logger.info("> PickQueueGoToPick");
		retVal = PickQueuepage.PickQueueGoToPick(inOrdNum);
		if(retVal.equals("0")) {
			CommUtil.logger.info("PickQueueGoToPick - Success");
		} else {
			CommUtil.logger.info("PickQueueGoToPick - Failure. Code:"+retVal);
			return RstObj;
		}
		
		PickEditAction PickEditpage = new PickEditAction(PickPartswebdriver);
		
		CommUtil.logger.info("> SavePick");
		HashMap<Object, Object> retObj = PickEditpage.SavePick(InputObj);
		
        retVal= (String) retObj.get("Ret");
		
		if(retVal.equals("0")) {
			CommUtil.logger.info("SavePick - Success");
			
			Boxidlst = (List<String>) retObj.get("BoxIDs");
		} else {
			CommUtil.logger.info("SavePick - Failure. Code:"+retVal);
			return RstObj;
		}				
		
		ret = "0";
		RstObj.put("Ret", ret);
		RstObj.put("BoxIDs", Boxidlst);  
		
		return RstObj;
		
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap<Object, Object> PickParts_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();

		String ret = "-1";
		List<String> Boxidlst = new ArrayList<String>();
		
		HashMap<Object, Object> RstObj = new HashMap<Object, Object>();
		RstObj.put("Ret", ret);
		RstObj.put("BoxIDs", Boxidlst);
		
		CommUtil.logger.info("> MenuInventory");
		CST_MainAction cstMain= new CST_MainAction(webdriver);
		cstMain.MenuInventory();
		
		CommUtil.logger.info("> GotoPickAdmin");
		InvIndexAction InvIndex = new InvIndexAction(webdriver);
		InvIndex.GotoPickAdmin();

		PickAdminAction PickAdminpage = new PickAdminAction(webdriver);
			
		CommUtil.logger.info("> SearchPickAdmin");
		HashMap<String, String> SearchPickAdminInputObj = new HashMap<String, String>();
		SearchPickAdminInputObj.put("ProjectCode", inProjectCode);					
		SearchPickAdminInputObj.put("OrdNum", inOrdNum);					
		SearchPickAdminInputObj.put("UserName", "");					
		String retVal = PickAdminpage.SearchPickAdmin(SearchPickAdminInputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchPickAdmin - Success");
		} else {
			CommUtil.logger.info("SearchPickAdmin - Failure. Code:"+retVal);
			return RstObj;
		}				

		CommUtil.logger.info("> SavePickAdmin");
		retVal = PickAdminpage.SavePickAdmin(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("SavePickAdmin - Success");
		} else {
			CommUtil.logger.info("SavePickAdmin - Failure. Code:"+retVal);
			return RstObj;
		}
		
		CommUtil.logger.info("> MenuInventory");
		cstMain= new CST_MainAction(webdriver);
		cstMain.MenuInventory();
		
		CommUtil.logger.info("> GotoPickQueue");
		InvIndex = new InvIndexAction(webdriver);
		InvIndex.GotoPickQueue();;

		PickQueueAction PickQueuepage = new PickQueueAction(webdriver);
		
		CommUtil.logger.info("> SearchPickQueue");
		retVal = PickQueuepage.SearchPickQueue(InputObj);
		if(retVal.equals("1")) {
			CommUtil.logger.info("SearchPickQueue - Success");
		} else {
			CommUtil.logger.info("SearchPickQueue - Failure. Code:"+retVal);
			return RstObj;
		}				

		CommUtil.logger.info("> PickQueueGoToPick");
		retVal = PickQueuepage.PickQueueGoToPick(inOrdNum);
		if(retVal.equals("0")) {
			CommUtil.logger.info("PickQueueGoToPick - Success");
		} else {
			CommUtil.logger.info("PickQueueGoToPick - Failure. Code:"+retVal);
			return RstObj;
		}
		
		PickEditAction PickEditpage = new PickEditAction(webdriver);
		
		CommUtil.logger.info("> SavePick");
		HashMap<Object, Object> retObj = PickEditpage.SavePick(InputObj);
		
        retVal= (String) retObj.get("Ret");
		
		if(retVal.equals("0")) {
			CommUtil.logger.info("SavePick - Success");
			
			Boxidlst = (List<String>) retObj.get("BoxIDs");
			
		} else {
			CommUtil.logger.info("SavePick - Failure. Code:"+retVal);
			return RstObj;
		}				
		
		ret = "0";
		RstObj.put("Ret", ret);
		RstObj.put("BoxIDs", Boxidlst);  
		
		return RstObj;
		
	}	

	public HashMap<String, String> CreateDisposalOrders_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		
		HashMap<String, String> retObj = null;
		
		String inCustomer = InputObj.get("Customer").toString();
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inSite = InputObj.get("Site").toString();
		String inAttribute = InputObj.get("Attribute").toString();
		String inTemplateName = InputObj.get("TemplateName").toString();
		String inReqEmail = InputObj.get("ReqEmail").toString();
		String inProdLine = InputObj.get("ProdLine").toString();
		ArrayList<?> inExpectedSendoutPart = (ArrayList<?>) InputObj.get("ExpectedSendoutPart");	
		
		CommUtil.logger.info("> MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();

		CommUtil.logger.info("> GotoOrdAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);	
		ordidxpage.GotoOrdAdmin();

		CommUtil.logger.info("> NewOrder");
		OrderAdminAction ordAdminpage = new OrderAdminAction(webdriver);	
		ordAdminpage.NewOrder();

		String vOrdType = TestSetting.OrdType_Disposal;
		String vMode = "0";
		String vPO = "";
		String vPOLink = "0";
		
		CommUtil.logger.info("> Creating Parent order.");
		OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
		HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", vMode);
		CreateOrderEntry1InputObject.put("Customer", inCustomer);
		CreateOrderEntry1InputObject.put("ProjectCode", inProjectCode);
		CreateOrderEntry1InputObject.put("Site", inSite);
		CreateOrderEntry1InputObject.put("OrdType", vOrdType);
		CreateOrderEntry1InputObject.put("Attribute", inAttribute);
		CreateOrderEntry1InputObject.put("TemplateName", inTemplateName);
		CreateOrderEntry1InputObject.put("ReqEmail", inReqEmail);
		CreateOrderEntry1InputObject.put("PO", vPO);
		CreateOrderEntry1InputObject.put("POLink", vPOLink);
		CreateOrderEntry1InputObject.put("ProdLine", inProdLine);
		
		CommUtil.logger.info("> CreateOrderEntry1");
		String retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
			return retObj;
		}		
		
		
		OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
		
		HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
		InputEntry2Obj.put("Mode", "0");
		InputEntry2Obj.put("PartArr", inExpectedSendoutPart);
		
		CommUtil.logger.info("> CreateOrderEntry2");
		retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
			return retObj;
		}				

		
		OrderEntry3Action OrderEntry3page = new OrderEntry3Action(webdriver);
		HashMap<String, String> CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
		
		CommUtil.logger.info("> CreateOrderEntry3");
		retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry3 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
			return retObj;
		}				

		OrderEntry4Action OrderEntry4page = new OrderEntry4Action(webdriver);
		HashMap<String, String> CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
		
		if (inTemplateName.equals("")) {
			vMode = "0";
		} else {
			vMode = "5";
		}
		
		CreateOrderEntry4InputObjInputObj.put("Mode", vMode);
		
		CommUtil.logger.info("> CreateOrderEntry4");
		retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry4 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
			return retObj;
		}			


		if (!inTemplateName.equals("")) {
			retObj = new HashMap<String, String>();
			retObj.put("TotalOrderCnt", "1");			
		} else {
		
			OrderSummaryAction OrderSummarypage = new OrderSummaryAction(webdriver);	
			retObj = OrderSummarypage.GetOrderSummary();
		}
		
		return retObj;
		
	}

	public HashMap<String, String> CreateEEXOrders_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		
		HashMap<String, String> retObj = null;
		
		String inCustomer = InputObj.get("Customer").toString();
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inSite = InputObj.get("Site").toString();
		String inAttribute = InputObj.get("Attribute").toString();
		String inTemplateName = InputObj.get("TemplateName").toString();
		String inReqEmail = InputObj.get("ReqEmail").toString();
		String inProdLine = InputObj.get("ProdLine").toString();
		ArrayList<?> inExpectedSendoutPart = (ArrayList<?>) InputObj.get("ExpectedSendoutPart");	
		ArrayList<?> inExpectedReceivePart = (ArrayList<?>) InputObj.get("ExpectedReceivePart");	
		
		CommUtil.logger.info("> MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();

		CommUtil.logger.info("> GotoOrdAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);	
		ordidxpage.GotoOrdAdmin();

		CommUtil.logger.info("> NewOrder");
		OrderAdminAction ordAdminpage = new OrderAdminAction(webdriver);	
		ordAdminpage.NewOrder();

		String vOrdType = TestSetting.OrdType_EEX;
		String vMode = "0";
		String vPO = "";
		String vPOLink = "0";
		
		CommUtil.logger.info("> Creating Parent order.");
		OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
		HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", vMode);
		CreateOrderEntry1InputObject.put("Customer", inCustomer);
		CreateOrderEntry1InputObject.put("ProjectCode", inProjectCode);
		CreateOrderEntry1InputObject.put("Site", inSite);
		CreateOrderEntry1InputObject.put("OrdType", vOrdType);
		CreateOrderEntry1InputObject.put("Attribute", inAttribute);
		CreateOrderEntry1InputObject.put("TemplateName", inTemplateName);
		CreateOrderEntry1InputObject.put("ReqEmail", inReqEmail);
		CreateOrderEntry1InputObject.put("PO", vPO);
		CreateOrderEntry1InputObject.put("POLink", vPOLink);
		CreateOrderEntry1InputObject.put("ProdLine", inProdLine);
		
		CommUtil.logger.info("> CreateOrderEntry1");
		String retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
			return retObj;
		}		
		
		
		OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
		
		HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
		InputEntry2Obj.put("Mode", "0");
		InputEntry2Obj.put("PartArr", inExpectedSendoutPart);
		
		CommUtil.logger.info("> CreateOrderEntry2");
		retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
			return retObj;
		}				

		
		OrderEntry3Action OrderEntry3page = new OrderEntry3Action(webdriver);
		HashMap<String, String> CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
		
		CommUtil.logger.info("> CreateOrderEntry3");
		retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry3 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
			return retObj;
		}				

		OrderEntry4Action OrderEntry4page = new OrderEntry4Action(webdriver);
		HashMap<String, String> CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry4InputObjInputObj.put("Mode", "0");
		
		CommUtil.logger.info("> CreateOrderEntry4");
		retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry4 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
			return retObj;
		}			

		CommUtil.logger.info("> Creating Child 1 order.");

		OrderEntry1page = new OrderEntry1Action(webdriver);
		CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", "1");
		CreateOrderEntry1InputObject.put("Customer", "");
		CreateOrderEntry1InputObject.put("ProjectCode", "");
		CreateOrderEntry1InputObject.put("Site", "");
		CreateOrderEntry1InputObject.put("OrdType", "");
		CreateOrderEntry1InputObject.put("Attribute", "");
		CreateOrderEntry1InputObject.put("TemplateName", "");
		CreateOrderEntry1InputObject.put("ReqEmail", "");
		CreateOrderEntry1InputObject.put("PO", "");
		CreateOrderEntry1InputObject.put("POLink", "1");
		CreateOrderEntry1InputObject.put("ProdLine", "");

		CommUtil.logger.info("> CreateOrderEntry1");
		retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
			return retObj;
		}
		
		
		OrderEntry2page = new OrderEntry2Action(webdriver);
		
		InputEntry2Obj = new HashMap<String, Serializable>();		
		InputEntry2Obj.put("Mode", "4");
		InputEntry2Obj.put("PartArr", inExpectedReceivePart);

		CommUtil.logger.info("> CreateOrderEntry2");
		retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
			return retObj;
		}			
		
		OrderEntry3page = new OrderEntry3Action(webdriver);
		CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
		
		CommUtil.logger.info("> CreateOrderEntry3");
		retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry3 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
			return retObj;
		}	
		
		OrderEntry4page = new OrderEntry4Action(webdriver);
		CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry4InputObjInputObj.put("Mode", "2");
		
		CommUtil.logger.info("> CreateOrderEntry4");
		retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry4 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
			return retObj;
		}
		
		if (inAttribute.equals(TestSetting.OrdAttr_ExtRepairNoHP) ||
				inAttribute.equals(TestSetting.OrdAttr_ExtRepairWithHP)) {
		
			CommUtil.logger.info("> Creating Child 2 order.");

			OrderEntry1page = new OrderEntry1Action(webdriver);
			CreateOrderEntry1InputObject = new HashMap<String, String>();
			CreateOrderEntry1InputObject.put("Mode", "1");
			CreateOrderEntry1InputObject.put("Customer", "");
			CreateOrderEntry1InputObject.put("ProjectCode", "");
			CreateOrderEntry1InputObject.put("Site", "");
			CreateOrderEntry1InputObject.put("OrdType", "");
			CreateOrderEntry1InputObject.put("Attribute", "");
			CreateOrderEntry1InputObject.put("TemplateName", "");
			CreateOrderEntry1InputObject.put("ReqEmail", "");
			CreateOrderEntry1InputObject.put("PO", "");
			CreateOrderEntry1InputObject.put("POLink", "1");
			CreateOrderEntry1InputObject.put("ProdLine", "");

			CommUtil.logger.info("> CreateOrderEntry1");
			retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry1 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
				return retObj;
			}
			
			
			OrderEntry2page = new OrderEntry2Action(webdriver);
			
			InputEntry2Obj = new HashMap<String, Serializable>();		
			InputEntry2Obj.put("Mode", "3");

			CommUtil.logger.info("> CreateOrderEntry2");
			retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry2 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
				return retObj;
			}			
			
			OrderEntry3page = new OrderEntry3Action(webdriver);
			CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
			CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
			
			CommUtil.logger.info("> CreateOrderEntry3");
			retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry3 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
				return retObj;
			}	
			
			OrderEntry4page = new OrderEntry4Action(webdriver);
			CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
			CreateOrderEntry4InputObjInputObj.put("Mode", "2");
			
			CommUtil.logger.info("> CreateOrderEntry4");
			retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry4 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
				return retObj;
			}	
		}
			
		OrderSummaryAction OrderSummarypage = new OrderSummaryAction(webdriver);

		retObj = OrderSummarypage.GetOrderSummary();

		//retVal = retObj.get("RetVal").toString();
/*		String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
		String ParentOrdNum = retObj.get("ParentOrdNum").toString();
		String Pstatus = retObj.get("Pstatus").toString();
		String ParentPO = retObj.get("ParentPO").toString();
		String C1Num = retObj.get("C1Num").toString();
		String C1Status = retObj.get("C1Status").toString();
		String C1PO = retObj.get("C1PO").toString();
		String C2Num = retObj.get("C2Num").toString();
		String C2Status = retObj.get("C2Status").toString();
		String C2PO = retObj.get("C2PO").toString();
		System.out.println("TotalOrderCnt:"+TotalOrderCnt);
		System.out.println("ParentOrdNum:"+ParentOrdNum);
		System.out.println("Pstatus:"+Pstatus);
		System.out.println("ParentPO:"+ParentPO);
		System.out.println("C1Num:"+C1Num);
		System.out.println("C1Status:"+C1Status);
		System.out.println("C1PO:"+C1PO);
		System.out.println("C2Num:"+C2Num);
		System.out.println("C2Status:"+C2Status);
		System.out.println("C2PO:"+C2PO);*/
		
		return retObj;
		
	}
	public HashMap<String, String> CreateFROrders_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		
		HashMap<String, String> retObj = null;
		
		String inCustomer = InputObj.get("Customer").toString();
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inSite = InputObj.get("Site").toString();
		String inAttribute = InputObj.get("Attribute").toString();
		String inTemplateName = InputObj.get("TemplateName").toString();
		String inReqEmail = InputObj.get("ReqEmail").toString();
		String inProdLine = InputObj.get("ProdLine").toString();
		ArrayList<?> inExpectedSendoutPart = (ArrayList<?>) InputObj.get("ExpectedSendoutPart");	
		ArrayList<?> inExpectedReceivePart = (ArrayList<?>) InputObj.get("ExpectedReceivePart");	
		
		CommUtil.logger.info("> MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();

		CommUtil.logger.info("> GotoOrdAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);	
		ordidxpage.GotoOrdAdmin();

		CommUtil.logger.info("> NewOrder");
		OrderAdminAction ordAdminpage = new OrderAdminAction(webdriver);	
		ordAdminpage.NewOrder();

		String vOrdType = TestSetting.OrdType_FR;
		String vMode = "0";
		String vPO = "";
		String vPOLink = "0";
		
		CommUtil.logger.info("> Creating Parent order.");
		OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
		HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", vMode);
		CreateOrderEntry1InputObject.put("Customer", inCustomer);
		CreateOrderEntry1InputObject.put("ProjectCode", inProjectCode);
		CreateOrderEntry1InputObject.put("Site", inSite);
		CreateOrderEntry1InputObject.put("OrdType", vOrdType);
		CreateOrderEntry1InputObject.put("Attribute", inAttribute);
		CreateOrderEntry1InputObject.put("TemplateName", inTemplateName);
		CreateOrderEntry1InputObject.put("ReqEmail", inReqEmail);
		CreateOrderEntry1InputObject.put("PO", vPO);
		CreateOrderEntry1InputObject.put("POLink", vPOLink);
		CreateOrderEntry1InputObject.put("ProdLine", inProdLine);
		
		CommUtil.logger.info("> CreateOrderEntry1");
		String retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
			return retObj;
		}		
		
		OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
		
		HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
		InputEntry2Obj.put("Mode", "0");
		InputEntry2Obj.put("PartArr", inExpectedSendoutPart);
		
		CommUtil.logger.info("> CreateOrderEntry2");
		retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else if (retVal.equals("2")) { 
			CommUtil.logger.info("Not enough avaiable qty, calling GenerateStandardPart_CA.");
			GenerateStandardPart_CA(TestSetting.DefaultGenQty); 
			
			CommUtil.logger.info("> MenuOrdMngt");
			mainpage = new CST_MainAction(webdriver);
			mainpage.MenuOrdMngt();

			CommUtil.logger.info("> GotoOrdAdmin");
			ordidxpage = new OrderIndexAction(webdriver);	
			ordidxpage.GotoOrdAdmin();

			CommUtil.logger.info("> NewOrder");
			ordAdminpage = new OrderAdminAction(webdriver);	
			ordAdminpage.NewOrder();			

			CommUtil.logger.info("> Recreating Parent order.");
			OrderEntry1page = new OrderEntry1Action(webdriver);
			CreateOrderEntry1InputObject = new HashMap<String, String>();
			CreateOrderEntry1InputObject.put("Mode", vMode);
			CreateOrderEntry1InputObject.put("Customer", inCustomer);
			CreateOrderEntry1InputObject.put("ProjectCode", inProjectCode);
			CreateOrderEntry1InputObject.put("Site", inSite);
			CreateOrderEntry1InputObject.put("OrdType", vOrdType);
			CreateOrderEntry1InputObject.put("Attribute", inAttribute);
			CreateOrderEntry1InputObject.put("TemplateName", inTemplateName);
			CreateOrderEntry1InputObject.put("ReqEmail", inReqEmail);
			CreateOrderEntry1InputObject.put("PO", vPO);
			CreateOrderEntry1InputObject.put("POLink", vPOLink);
			CreateOrderEntry1InputObject.put("ProdLine", inProdLine);
			
			CommUtil.logger.info("> CreateOrderEntry1");
			retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry1 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
				return retObj;
			}		

			OrderEntry2page = new OrderEntry2Action(webdriver);
			
			InputEntry2Obj = new HashMap<String, Serializable>();
			InputEntry2Obj.put("Mode", "0");
			InputEntry2Obj.put("PartArr", inExpectedSendoutPart);
			
			CommUtil.logger.info("> CreateOrderEntry2");
			retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry2 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
				return retObj;
			}			
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
			return retObj;
		}				

		
		OrderEntry3Action OrderEntry3page = new OrderEntry3Action(webdriver);
		HashMap<String, String> CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
		
		CommUtil.logger.info("> CreateOrderEntry3");
		retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry3 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
			return retObj;
		}				

		OrderEntry4Action OrderEntry4page = new OrderEntry4Action(webdriver);
		HashMap<String, String> CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry4InputObjInputObj.put("Mode", "0");
		
		CommUtil.logger.info("> CreateOrderEntry4");
		retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry4 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
			return retObj;
		}			

		CommUtil.logger.info("> Creating Child 1 order.");

		OrderEntry1page = new OrderEntry1Action(webdriver);
		CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", "1");
		CreateOrderEntry1InputObject.put("Customer", "");
		CreateOrderEntry1InputObject.put("ProjectCode", "");
		CreateOrderEntry1InputObject.put("Site", "");
		CreateOrderEntry1InputObject.put("OrdType", "");
		CreateOrderEntry1InputObject.put("Attribute", "");
		CreateOrderEntry1InputObject.put("TemplateName", "");
		CreateOrderEntry1InputObject.put("ReqEmail", "");
		CreateOrderEntry1InputObject.put("PO", "");
		CreateOrderEntry1InputObject.put("POLink", "1");
		CreateOrderEntry1InputObject.put("ProdLine", "");

		CommUtil.logger.info("> CreateOrderEntry1");
		retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
			return retObj;
		}
		
		
		OrderEntry2page = new OrderEntry2Action(webdriver);
		
		InputEntry2Obj = new HashMap<String, Serializable>();		
		InputEntry2Obj.put("Mode", "1");
		InputEntry2Obj.put("PartArr", inExpectedReceivePart);

		CommUtil.logger.info("> CreateOrderEntry2");
		retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
			return retObj;
		}			
		
		OrderEntry3page = new OrderEntry3Action(webdriver);
		CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
		
		CommUtil.logger.info("> CreateOrderEntry3");
		retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry3 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
			return retObj;
		}	
		
		OrderEntry4page = new OrderEntry4Action(webdriver);
		CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry4InputObjInputObj.put("Mode", "2");
		
		CommUtil.logger.info("> CreateOrderEntry4");
		retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry4 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
			return retObj;
		}
		
		if (inAttribute.equals(TestSetting.OrdAttr_ExtRepairNoHP) ||
				inAttribute.equals(TestSetting.OrdAttr_ExtRepairWithHP)) {
		
			CommUtil.logger.info("> Creating Child 2 order.");

			OrderEntry1page = new OrderEntry1Action(webdriver);
			CreateOrderEntry1InputObject = new HashMap<String, String>();
			CreateOrderEntry1InputObject.put("Mode", "1");
			CreateOrderEntry1InputObject.put("Customer", "");
			CreateOrderEntry1InputObject.put("ProjectCode", "");
			CreateOrderEntry1InputObject.put("Site", "");
			CreateOrderEntry1InputObject.put("OrdType", "");
			CreateOrderEntry1InputObject.put("Attribute", "");
			CreateOrderEntry1InputObject.put("TemplateName", "");
			CreateOrderEntry1InputObject.put("ReqEmail", "");
			CreateOrderEntry1InputObject.put("PO", "");
			CreateOrderEntry1InputObject.put("POLink", "1");
			CreateOrderEntry1InputObject.put("ProdLine", "");

			CommUtil.logger.info("> CreateOrderEntry1");
			retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry1 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
				return retObj;
			}
			
			
			OrderEntry2page = new OrderEntry2Action(webdriver);
			
			InputEntry2Obj = new HashMap<String, Serializable>();		
			InputEntry2Obj.put("Mode", "3");

			CommUtil.logger.info("> CreateOrderEntry2");
			retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry2 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
				return retObj;
			}			
			
			OrderEntry3page = new OrderEntry3Action(webdriver);
			CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
			CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
			
			CommUtil.logger.info("> CreateOrderEntry3");
			retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry3 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
				return retObj;
			}	
			
			OrderEntry4page = new OrderEntry4Action(webdriver);
			CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
			CreateOrderEntry4InputObjInputObj.put("Mode", "2");
			
			CommUtil.logger.info("> CreateOrderEntry4");
			retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry4 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
				return retObj;
			}	
		}
			
		OrderSummaryAction OrderSummarypage = new OrderSummaryAction(webdriver);

		retObj = OrderSummarypage.GetOrderSummary();

		//retVal = retObj.get("RetVal").toString();
/*		String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
		String ParentOrdNum = retObj.get("ParentOrdNum").toString();
		String Pstatus = retObj.get("Pstatus").toString();
		String ParentPO = retObj.get("ParentPO").toString();
		String C1Num = retObj.get("C1Num").toString();
		String C1Status = retObj.get("C1Status").toString();
		String C1PO = retObj.get("C1PO").toString();
		String C2Num = retObj.get("C2Num").toString();
		String C2Status = retObj.get("C2Status").toString();
		String C2PO = retObj.get("C2PO").toString();
		System.out.println("TotalOrderCnt:"+TotalOrderCnt);
		System.out.println("ParentOrdNum:"+ParentOrdNum);
		System.out.println("Pstatus:"+Pstatus);
		System.out.println("ParentPO:"+ParentPO);
		System.out.println("C1Num:"+C1Num);
		System.out.println("C1Status:"+C1Status);
		System.out.println("C1PO:"+C1PO);
		System.out.println("C2Num:"+C2Num);
		System.out.println("C2Status:"+C2Status);
		System.out.println("C2PO:"+C2PO);*/
		
		return retObj;
		
	}
	public HashMap<String, String> CreateRepairOrders_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		
		HashMap<String, String> retObj = null;
		
		String inCustomer = InputObj.get("Customer").toString();
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inSite = InputObj.get("Site").toString();
		String inAttribute = InputObj.get("Attribute").toString();
		String inTemplateName = InputObj.get("TemplateName").toString();
		String inReqEmail = InputObj.get("ReqEmail").toString();
		String inProdLine = InputObj.get("ProdLine").toString();
		ArrayList<?> inExpectedPart = (ArrayList<?>) InputObj.get("ExpectedPart");	
		
		CommUtil.logger.info("> MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();

		CommUtil.logger.info("> GotoOrdAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);	
		ordidxpage.GotoOrdAdmin();

		CommUtil.logger.info("> NewOrder");
		OrderAdminAction ordAdminpage = new OrderAdminAction(webdriver);	
		ordAdminpage.NewOrder();

		String vOrdType = TestSetting.OrdType_Repair;
		String vMode = "0";
		String vPO = "";
		String vPOLink = "1";
		if (inAttribute.equals(TestSetting.OrdAttr_IntRepairDFS) ||
				inAttribute.equals(TestSetting.OrdAttr_ExtRepair)) {
			vPOLink = "0";
		}
		
		CommUtil.logger.info("> Creating Parent order.");
		OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
		HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
		CreateOrderEntry1InputObject.put("Mode", vMode);
		CreateOrderEntry1InputObject.put("Customer", inCustomer);
		CreateOrderEntry1InputObject.put("ProjectCode", inProjectCode);
		CreateOrderEntry1InputObject.put("Site", inSite);
		CreateOrderEntry1InputObject.put("OrdType", vOrdType);
		CreateOrderEntry1InputObject.put("Attribute", inAttribute);
		CreateOrderEntry1InputObject.put("TemplateName", inTemplateName);
		CreateOrderEntry1InputObject.put("ReqEmail", inReqEmail);
		CreateOrderEntry1InputObject.put("PO", vPO);
		CreateOrderEntry1InputObject.put("POLink", vPOLink);
		CreateOrderEntry1InputObject.put("ProdLine", inProdLine);
		
		CommUtil.logger.info("> CreateOrderEntry1");
		String retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry1 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
			return retObj;
		}		
		
		
		OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
		
		HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
		InputEntry2Obj.put("Mode", "1");
		InputEntry2Obj.put("PartArr", inExpectedPart);
		
		CommUtil.logger.info("> CreateOrderEntry2");
		retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry2 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
			return retObj;
		}				

		
		OrderEntry3Action OrderEntry3page = new OrderEntry3Action(webdriver);
		HashMap<String, String> CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
		
		CommUtil.logger.info("> CreateOrderEntry3");
		retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry3 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
			return retObj;
		}				

		OrderEntry4Action OrderEntry4page = new OrderEntry4Action(webdriver);
		HashMap<String, String> CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
		CreateOrderEntry4InputObjInputObj.put("Mode", "1");
		
		CommUtil.logger.info("> CreateOrderEntry4");
		retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("CreateOrderEntry4 - Success");
		} else {
			CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
			return retObj;
		}			
		
		// Child1
		if (inAttribute.equals(TestSetting.OrdAttr_ExtRepair) ||
				inAttribute.equals(TestSetting.OrdAttr_ExtRepairNoHP) ||
				inAttribute.equals(TestSetting.OrdAttr_ExtRepairWithHP)) {
			
			CommUtil.logger.info("> Creating Child 1 order.");

			OrderEntry1page = new OrderEntry1Action(webdriver);
			CreateOrderEntry1InputObject = new HashMap<String, String>();
			CreateOrderEntry1InputObject.put("Mode", "1");
			CreateOrderEntry1InputObject.put("Customer", "");
			CreateOrderEntry1InputObject.put("ProjectCode", "");
			CreateOrderEntry1InputObject.put("Site", "");
			CreateOrderEntry1InputObject.put("OrdType", "");
			CreateOrderEntry1InputObject.put("Attribute", "");
			CreateOrderEntry1InputObject.put("TemplateName", "");
			CreateOrderEntry1InputObject.put("ReqEmail", "");
			CreateOrderEntry1InputObject.put("PO", "");
			CreateOrderEntry1InputObject.put("POLink", "1");
			CreateOrderEntry1InputObject.put("ProdLine", "");

			CommUtil.logger.info("> CreateOrderEntry1");
			retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry1 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
				return retObj;
			}
			
			
			OrderEntry2page = new OrderEntry2Action(webdriver);
			
			InputEntry2Obj = new HashMap<String, Serializable>();		
			InputEntry2Obj.put("Mode", "2");

			CommUtil.logger.info("> CreateOrderEntry2");
			retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry2 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
				return retObj;
			}			
			
			OrderEntry3page = new OrderEntry3Action(webdriver);
			CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
			CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
			
			CommUtil.logger.info("> CreateOrderEntry3");
			retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry3 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
				return retObj;
			}	
			
			OrderEntry4page = new OrderEntry4Action(webdriver);
			CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
			CreateOrderEntry4InputObjInputObj.put("Mode", "1");
			
			CommUtil.logger.info("> CreateOrderEntry4");
			retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
			if(retVal.equals("0")) {
				CommUtil.logger.info("CreateOrderEntry4 - Success");
			} else {
				CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
				return retObj;
			}		
			
		}
		
		OrderSummaryAction OrderSummarypage = new OrderSummaryAction(webdriver);

		retObj = OrderSummarypage.GetOrderSummary();

		//retVal = retObj.get("RetVal").toString();
/*		String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
		String ParentOrdNum = retObj.get("ParentOrdNum").toString();
		String Pstatus = retObj.get("Pstatus").toString();
		String ParentPO = retObj.get("ParentPO").toString();
		String C1Num = retObj.get("C1Num").toString();
		String C1Status = retObj.get("C1Status").toString();
		String C1PO = retObj.get("C1PO").toString();
		String C2Num = retObj.get("C2Num").toString();
		String C2Status = retObj.get("C2Status").toString();
		String C2PO = retObj.get("C2PO").toString();
		System.out.println("TotalOrderCnt:"+TotalOrderCnt);
		System.out.println("ParentOrdNum:"+ParentOrdNum);
		System.out.println("Pstatus:"+Pstatus);
		System.out.println("ParentPO:"+ParentPO);
		System.out.println("C1Num:"+C1Num);
		System.out.println("C1Status:"+C1Status);
		System.out.println("C1PO:"+C1PO);
		System.out.println("C2Num:"+C2Num);
		System.out.println("C2Status:"+C2Status);
		System.out.println("C2PO:"+C2PO);
		
*/		
		
		return retObj;
	}
	
	public HashMap<String, String> CreateStdPO_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoPOMngt");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoPOMngt();
			
		CommUtil.logger.info(" > NewPO");
		POMngtAction pomngtpage = new POMngtAction(webdriver);
		pomngtpage.NewPO();
		
		CommUtil.logger.info(" > CreateStandardPO");
		CreatePOAction createPOpage = new CreatePOAction(webdriver);
		HashMap<String, String> retObj = createPOpage.CreateStandardPO(InputObj);
		
		return retObj;
	}
	
	public String SearchPO_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoPOMngt");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoPOMngt();
			
		CommUtil.logger.info(" > SearchPO");
		POMngtAction pomngtpage = new POMngtAction(webdriver);
		isFound = pomngtpage.SearchPO(InputObj);
		
		return isFound;
	}
	public String UpdateOrder_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String ret = "-1";	
		
		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoOrdUpdate");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoOrdUpdate();
			
		CommUtil.logger.info(" > SearchOrder");
		OrderUpdateAction orderupdatepage = new OrderUpdateAction(webdriver);
		String isFound = orderupdatepage.SearchOrderUpdate(InputObj);
    	if(!isFound.equals("1")) {
    		CommUtil.logger.info(">SearchOrderUpdate Failure. Code:" +isFound);
    		return ret;
    	}
    	
		String retVal = orderupdatepage.EditOrderUpdate(InputObj);
		if(retVal.equals("0")) {
			CommUtil.logger.info("EditOrderUpdate - Success");
		} else {
			CommUtil.logger.info("EditOrderUpdate - Failure. Code:"+retVal);
			return ret;
		}	
    	
    	ret = "0";
		return ret;
	}
	public String SearchOrdAdmin_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoOrdAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoOrdAdmin();
			
		CommUtil.logger.info(" > SearchOrder");
		OrderAdminAction orderAdminpage = new OrderAdminAction(webdriver);
		isFound = orderAdminpage.SearchOrder(InputObj);
		
		return isFound;
	}
	public String SearchTemplate_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoTmpltAdmin");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoTmpltAdmin();
			
		CommUtil.logger.info(" > SearchOrder");
		TemplateAdminAction tmpltAdminpage = new TemplateAdminAction(webdriver);
		isFound = tmpltAdminpage.SearchTemplate(InputObj);
		
		return isFound;
	}
	public String SearchCust_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		CommUtil.logger.info(" > MenuSetting");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuSetting();
		
		CommUtil.logger.info(" > GotoCustAdmin");
		SettingIndexAction setidxpage = new SettingIndexAction(webdriver);
		setidxpage.GotoCustAdmin();
			
		CommUtil.logger.info(" > SearchCust");
		CustAdminAction custAdminpage = new CustAdminAction(webdriver);
		isFound = custAdminpage.SearchCust(InputObj);
		
		return isFound;
	}
	public String GotoOrderEntry1_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//Mode:0:parent entry1; 1:child1 ; 2: child2;3:From Parent to Child1;4: From Child1 to Child2
		String Mode = InputObj.get("Mode").toString();
		String ret = "-1";
		
		if (!Mode.equals("3") && !Mode.equals("4")) {
			
			CommUtil.logger.info("> SearchOrdAdmin_CA.");
			String isFound = this.SearchOrdAdmin_CA(InputObj);
        	if(!isFound.equals("1")) {
        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
        		return ret;
        	}
        	
        	CommUtil.logger.info("> EditOrder");
    		OrderAdminAction orderAdminpage = new OrderAdminAction(webdriver); 
    		String retstr = orderAdminpage.EditOrder(InputObj);
        	if(!retstr.equals("0")) {
        		CommUtil.logger.info(">EditOrder Failure. Code:" +isFound);
        		return ret;
        	}
		}
		
		if (!Mode.equals("0")) {
			if (!Mode.equals("4")) {
				CommUtil.logger.info("> Moving to Child 1 order entry1.");

				OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
				HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
				CreateOrderEntry1InputObject.put("Mode", "1");
				CreateOrderEntry1InputObject.put("Customer", "");
				CreateOrderEntry1InputObject.put("ProjectCode", "");
				CreateOrderEntry1InputObject.put("Site", "");
				CreateOrderEntry1InputObject.put("OrdType", "");
				CreateOrderEntry1InputObject.put("Attribute", "");
				CreateOrderEntry1InputObject.put("TemplateName", "");
				CreateOrderEntry1InputObject.put("ReqEmail", "");
				CreateOrderEntry1InputObject.put("PO", "");
				CreateOrderEntry1InputObject.put("POLink", "0");
				CreateOrderEntry1InputObject.put("ProdLine", "");

				String retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry1 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
					return ret;
				}	
				
				
				OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
				
				HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
				InputEntry2Obj.put("Mode", "3");
				InputEntry2Obj.put("PartArr", new ArrayList<HashMap<String, String>>());
				
				CommUtil.logger.info("> CreateOrderEntry2");
				retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry2 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
					return ret;
				}	
				
				OrderEntry3Action OrderEntry3page = new OrderEntry3Action(webdriver);
				HashMap<String, String> CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
				CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
				
				CommUtil.logger.info("> CreateOrderEntry3");
				retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry3 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
					return ret;
				}	
				
				OrderEntry4Action OrderEntry4page = new OrderEntry4Action(webdriver);
				HashMap<String, String> CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
				CreateOrderEntry4InputObjInputObj.put("Mode", "3");
				
				CommUtil.logger.info("> CreateOrderEntry4");
				retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry4 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
					return ret;
				}	
				
			}
			
			if (Mode.equals("2") || Mode.equals("4")) {
				CommUtil.logger.info("> Moving to Child 2 order entry1.");

				OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
				HashMap<String, String> CreateOrderEntry1InputObject = new HashMap<String, String>();
				CreateOrderEntry1InputObject.put("Mode", "1");
				CreateOrderEntry1InputObject.put("Customer", "");
				CreateOrderEntry1InputObject.put("ProjectCode", "");
				CreateOrderEntry1InputObject.put("Site", "");
				CreateOrderEntry1InputObject.put("OrdType", "");
				CreateOrderEntry1InputObject.put("Attribute", "");
				CreateOrderEntry1InputObject.put("TemplateName", "");
				CreateOrderEntry1InputObject.put("ReqEmail", "");
				CreateOrderEntry1InputObject.put("PO", "");
				CreateOrderEntry1InputObject.put("POLink", "0");
				CreateOrderEntry1InputObject.put("ProdLine", "");

				String retVal = OrderEntry1page.CreateOrderEntry1(CreateOrderEntry1InputObject);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry1 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry1 - Failure. Code:"+retVal);
					return ret;
				}	
				
				
				OrderEntry2Action OrderEntry2page = new OrderEntry2Action(webdriver);
				
				HashMap<String, Serializable> InputEntry2Obj = new HashMap<String, Serializable>();
				InputEntry2Obj.put("Mode", "3");
				InputEntry2Obj.put("PartArr", new ArrayList<HashMap<String, String>>());
				
				CommUtil.logger.info("> CreateOrderEntry2");
				retVal = OrderEntry2page.CreateOrderEntry2(InputEntry2Obj);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry2 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry2 - Failure. Code:"+retVal);
					return ret;
				}	
				
				OrderEntry3Action OrderEntry3page = new OrderEntry3Action(webdriver);
				HashMap<String, String> CreateOrderEntry3InputObjInputObj = new HashMap<String, String>();
				CreateOrderEntry3InputObjInputObj.put("ReturnToStock", "");
				
				CommUtil.logger.info("> CreateOrderEntry3");
				retVal = OrderEntry3page.CreateOrderEntry3(CreateOrderEntry3InputObjInputObj);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry3 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry3 - Failure. Code:"+retVal);
					return ret;
				}	
				
				OrderEntry4Action OrderEntry4page = new OrderEntry4Action(webdriver);
				HashMap<String, String> CreateOrderEntry4InputObjInputObj = new HashMap<String, String>();
				CreateOrderEntry4InputObjInputObj.put("Mode", "3");
				
				CommUtil.logger.info("> CreateOrderEntry4");
				retVal = OrderEntry4page.CreateOrderEntry4(CreateOrderEntry4InputObjInputObj);
				if(retVal.equals("0")) {
					CommUtil.logger.info("CreateOrderEntry4 - Success");
				} else {
					CommUtil.logger.info("CreateOrderEntry4 - Failure. Code:"+retVal);
					return ret;
				}					
			}

		}
		
		ret = "0";
		
		return ret;
		
	}	
	public String ReceiveToDetailReceiving_CA (HashMap<String, ?> InputObj) throws NoSuchElementException 
	{
		String PalletID = "0";	
		
		String inPONum = InputObj.get("PONum").toString();
		String inCarrier = InputObj.get("Carrier").toString();
	    String inProjectCode = InputObj.get("ProjectCode").toString();
	    String inBoxCnt = InputObj.get("BoxCnt").toString();
	    String inPalletCnt = InputObj.get("PalletCnt").toString();
	    String inTrackNum = InputObj.get("TrackNum").toString();
	    String inPartNum = InputObj.get("PartNum").toString();
	    String inQty = InputObj.get("Qty").toString();
	    String inSN = InputObj.get("SN").toString();
	    String inDisposition = InputObj.get("Disposition").toString();
	    
	    CommUtil.logger.info(" > MenuInventroy");
	    CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		CommUtil.logger.info(" > InvIndex");
		
		InvIndexAction invIndexpage = new InvIndexAction(webdriver);
		invIndexpage.GotoDockReceiving();
		
		CommUtil.logger.info(" > New Doc receiving..");
		DockReceivingAction docReceiving = new DockReceivingAction(webdriver);
		
		HashMap<String, String> DetailRecevingInputObject = new HashMap<String, String>();
		DetailRecevingInputObject.put("TrackNum",inTrackNum);
		DetailRecevingInputObject.put("PONum", inPONum);
		DetailRecevingInputObject.put("Carrier",inCarrier);
		DetailRecevingInputObject.put("ProjectCode", inProjectCode);
		DetailRecevingInputObject.put("BoxCnt",inBoxCnt);
		DetailRecevingInputObject.put("PalletCnt", inPalletCnt);
		
		String outResult = docReceiving.NewDocReceiving(DetailRecevingInputObject);
	    
		if(outResult.equals("1"))
		{
			CommUtil.logger.info(" > NewDocReceiving :Project not found in the project list. Project:"+ inProjectCode);
			PalletID = "-1";
			return PalletID;
		}
		
		CommUtil.logger.info(" > DocReceivingGoToDetail");
		String OutRetVal=docReceiving.DocReceivingGoToDetail(inTrackNum);
		
		if(OutRetVal.equals("0"))
		{
			CommUtil.logger.info("> DocReceivingGoToDetail: Success.");
		}
		else
		{
			CommUtil.logger.info("DocReceivingGoToDetail : Failed. Code:"+OutRetVal);
		}

		CommUtil.logger.info(" > DetailReceiving");
		HashMap<String, String> InputObject = new HashMap<String, String>();
		InputObject.put("PartNum",inPartNum);
		InputObject.put("Qty", inQty);
		InputObject.put("SN",inSN);
		InputObject.put("Disposition", inDisposition);
		HashMap<String, String> OutputObject= docReceiving.DetailReceiving(InputObj);
		
		String OutDetailReceivingRetVal= OutputObject.get("RetVal").toString();
		PalletID = OutputObject.get("PalletID").toString();
		
		if(OutDetailReceivingRetVal.equals("0"))
		{
			CommUtil.logger.info("> DetailReceiving. Pass.");
		}
		else
		{
			CommUtil.logger.info("> DetailReceiving. Failed. Code"+OutDetailReceivingRetVal);
			PalletID = "-1";
		}
		
	    return PalletID;
	}
	
	
    @SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> GetAvailablePart_CA(HashMap<String, ?> InputObj) throws NoSuchElementException
    {
    	CommUtil.logger.info(" > GetAvailablePart_CA");
    	String inQty = InputObj.get("Qty").toString();
    	String caseid = "";
		Object caseidobj = InputObj.get("caseid");
		if (caseidobj != null) {
			caseid = caseidobj.toString();
		}
    	//String inQty = "10";
    	String inProjectCode = TestSetting.Project;
    	String inSearchPartNum =TestSetting.ProjectPart1;   
    	String inSN = "";
    	String inPONum = "";
    	String inStatus = "PUTAWAY"; 
    	String inStockGroup = TestSetting.StockGroup; 
    	String DefaultGenQty = TestSetting.DefaultGenQty; 
/*    	String strBoxId="";
    	String strSN="";*/
    	ArrayList<HashMap<String, String>> rstArr = null;
    	
    	HashMap<String, String> RetObj = new HashMap<String, String>();
    	
    	RetObj.put("BoxID", "");
    	RetObj.put("SN", "");
    	
    	CommUtil.logger.info(" > MenuInventroy");
	    CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
    	CommUtil.logger.info(" > GotoInventoryLookup");
		InvIndexAction invIndexpage = new InvIndexAction(webdriver);
		invIndexpage.GotoInventoryLookup();
		
		InventoryLookupAction inventoryLookupAction = new InventoryLookupAction(webdriver);
		HashMap<String, String> InputInventoryObject = new HashMap<String, String>();
		InputInventoryObject.put("PartNum", inSearchPartNum);
		InputInventoryObject.put("SearchSN", inSN);
		InputInventoryObject.put("SearchPONum", inPONum);
		InputInventoryObject.put("ProjectCode", inProjectCode);
		InputInventoryObject.put("SearchStatus", inStatus);
		InputInventoryObject.put("StockGroup", inStockGroup);
		InputInventoryObject.put("Qty", inQty);
		InputInventoryObject.put("PalletID", "");
		InputInventoryObject.put("SearchBoxid", "");
		InputInventoryObject.put("caseid", caseid);
    	CommUtil.logger.info(" > SearchInventory");
		HashMap<Object, Object>OutputInventoryLookup= inventoryLookupAction.SearchInventory(InputInventoryObject);
    			
        String outIsFound= (String) OutputInventoryLookup.get("IsFound");
        
        if(!outIsFound.equals("1")) {
        	GenerateStandardPart_CA(DefaultGenQty); 
        	
        	mainpage= new CST_MainAction(webdriver);
        	mainpage.MenuInventory();
        	
        	invIndexpage = new InvIndexAction(webdriver); 
        	invIndexpage.GotoInventoryLookup();
			
        	OutputInventoryLookup= inventoryLookupAction.SearchInventory(InputInventoryObject);
        	outIsFound= OutputInventoryLookup.get("IsFound").toString();
        	
        	if(!outIsFound.equals("1")) {
        		CommUtil.logger.info(">SearchInventory Failure. Code:" +outIsFound);
        		return rstArr;
        	} else {
        		rstArr= (ArrayList<HashMap<String, String>>) OutputInventoryLookup.get("RstArr");       		
        	}
        } else {
        	rstArr= (ArrayList<HashMap<String, String>>) OutputInventoryLookup.get("RstArr"); 
/*        	if (rstArr.size() < Integer.parseInt(inQty)) {
            	GenerateStandardPart_CA(inQty); 
            	
            	mainpage= new CST_MainAction(webdriver);
            	mainpage.MenuInventory();
            	
            	invIndexpage = new InvIndexAction(webdriver); 
            	invIndexpage.GotoInventoryLookup();
    			
            	OutputInventoryLookup= inventoryLookupAction.SearchInventory(InputInventoryObject);
            	outIsFound= OutputInventoryLookup.get("IsFound").toString();
            	
            	if(!outIsFound.equals("1")) {
            		CommUtil.logger.info(">SearchInventory Failure. Code:" +outIsFound);
            		CST_MainAction cstMainaction = new CST_MainAction(webdriver);
            		CommUtil.logger.info(">Logging Out...");
        			cstMainaction.logout();
            	} else {
            		rstArr= (ArrayList<HashMap<String, String>>) OutputInventoryLookup.get("RstArr");       		
            	}        		
        	}  */ 	
   	
        }
        
    	return rstArr;
    }
    
    public void GenerateStandardPart_CA(String Qty) throws NoSuchElementException
    {	
    	        String inQty = Qty;
    			//String inPONum=TestSetting.StandardPONum; // Hard  coded string in UFT test cases
    			String inPONum=""; // new requirement that qty is limited when receiving. Need create a new PO.

    		    String inMode = "1"; // comment out the mode 
    			
    		    String inProjCode = TestSetting.Project;
    			
    		    String inPartNum = TestSetting.ProjectPart1;
    			String inOrdNum = "";
    			String inCarrier=TestSetting.ShipVia;
    			String inBoxCnt="1";
    			String inPalletCnt ="1";
    			
    			String inSN=CommUtil.genDateTimeStamp();
    			String inBoxID="";
    			
    			String inLocation=TestSetting.StockLocation;
    			String inDisposition = "Good";
    			String inStockGroup=TestSetting.StockGroup;
   			
    			HashMap<String, String> InputGeneratePart=new HashMap<String, String>();
      
    			InputGeneratePart.put("ProjectCode", inProjCode);
    			InputGeneratePart.put("PartNum", inPartNum);
    			InputGeneratePart.put("Qty", inQty);
    			InputGeneratePart.put("OrdNum", inOrdNum);
    			InputGeneratePart.put("PONum", inPONum);
    			InputGeneratePart.put("Carrier",inCarrier );
    			InputGeneratePart.put("BoxCnt", inBoxCnt);
    			InputGeneratePart.put("PalletCnt",inPalletCnt);
    			InputGeneratePart.put("SN", inSN);
    			InputGeneratePart.put("BoxID", inBoxID);
    			InputGeneratePart.put("Location", inLocation);
    			InputGeneratePart.put("Disposition", inDisposition);
    			InputGeneratePart.put("Mode", inMode);
    			InputGeneratePart.put("StockGroup", inStockGroup);
    			
    			CommUtil.logger.info("Calling..GeneratePart_CA");
    			this.GeneratePart_CA(InputGeneratePart);
    }
    
    public String GeneratePart_CA(HashMap<String, String> InputObj) throws NoSuchElementException{
    	
    	String ret = "-1";
    	
    	String inMode = InputObj.get("Mode").toString();
    	CommUtil.logger.info("Mode:"+inMode);
    	String inProjCode = InputObj.get("ProjectCode").toString();
    	//CommUtil.logger.info(inProjCode);
    	String inPartNum = InputObj.get("PartNum").toString();
    	//CommUtil.logger.info(inPartNum);
    	String inQty = InputObj.get("Qty").toString();
    	//CommUtil.logger.info(inQty);
    	String inOrdNum = InputObj.get("OrdNum").toString();
    	//CommUtil.logger.info(inOrdNum);
    	String inPONum = InputObj.get("PONum").toString(); 
    	//CommUtil.logger.info(inPONum);
    	String inStockGroup = InputObj.get("StockGroup").toString(); 
    	
      
        String vTrackNum=CommUtil.genDateTimeStamp(); 
        
        if( inMode.equals("1"))
         {
        	 HashMap<String, String> InputPoObject = new HashMap<String, String>();
        	 InputPoObject.put("ProjectCode", inProjCode);
        	 InputPoObject.put("PartNum", inPartNum);
        	 InputPoObject.put("Qty", inQty);
        	 InputPoObject.put("OrdNum", inOrdNum);
        	 InputPoObject.put("StockGroup", inStockGroup);
        	 HashMap<String, String> OutputPoObject = this.CreateStdPO_CA(InputPoObject);
     		String retVal = OutputPoObject.get("RetVal").toString();
     		if (retVal.equals("0")) {
     			inPONum=OutputPoObject.get("PONum").toString();
     		} else {
     			CommUtil.logger.info("CreateStdPO_CA is failed.");
     			return ret;
     		}
         }
         
        InputObj.put("TrackNum", vTrackNum);
        InputObj.put("PONum", inPONum);
       
        CommUtil.logger.info("Calling ReceiveToPutaway_CA");
        ArrayList<HashMap<String, String>> retArr = this.ReceiveToPutaway_CA(InputObj);
        if (retArr!=null) {
        	ret = inPONum;
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> ReceiveToPutaway_CA(HashMap<String, ?> InputObj) throws NoSuchElementException{
    	        
    	String inPONum = InputObj.get("PONum").toString();
    	//String inCarrier = InputObj.get("Carrier").toString();
    	String inProjectCode=InputObj.get("ProjectCode").toString();
    	//String inBoxCnt=InputObj.get("BoxCnt").toString();
    	//String inPalletCnt=InputObj.get("PalletCnt").toString();
    	//String inTrackNum=InputObj.get("TrackNum").toString();
    	String inPartNum=InputObj.get("PartNum").toString();
    	String inQty=InputObj.get("Qty").toString();
    	String inSN=InputObj.get("SN").toString();
    	String inBoxID=InputObj.get("BoxID").toString();
    	String inLocation=InputObj.get("Location").toString();
    	//String inDisposition = InputObj.get("Disposition").toString();
    	
    	String outIsFound = "-1";
    	ArrayList<HashMap<String, String>> rstArr;
    	
    	
    	CommUtil.logger.info("Calling ReceiveToDetailReceiving_CA");
    	 //CommUtil.logger.info("ReceiveToPutaway_CA ...read inputs");
    	
    	String OutPalletID= this.ReceiveToDetailReceiving_CA(InputObj);
    	if (OutPalletID.equals("-1")) {
    		CommUtil.logger.info(">ReceiveToDetailReceiving_CA failed. PalletID:" +OutPalletID );
    		return null;
    	}
    	
    	CommUtil.logger.info("Pallet ID" + OutPalletID);
    	
    	if(!inQty.equals("1")) {
    		inSN = "";
    	}
    	
    	HashMap<String, String> InputPutAway=new HashMap<String, String>();
    	InputPutAway.put("SN",inSN);
    	InputPutAway.put("ProjectCode",inProjectCode);
    	InputPutAway.put("PalletID",OutPalletID);
    	InputPutAway.put("BoxID",inBoxID);
    	InputPutAway.put("Location",inLocation);
    	InputPutAway.put("PartNum",inPartNum);
    	InputPutAway.put("PONum",inPONum);
    	InputPutAway.put("Qty",inQty);
    	InputPutAway.put("StockGroup","");
    	
    	 CommUtil.logger.info("Calling PutawayToInv_CA  ...");
    	
    	HashMap<Object, Object> OutputInvputaway=this.PutawayToInv_CA(InputPutAway);
    	outIsFound= OutputInvputaway.get("IsFound").toString();
    	rstArr= (ArrayList<HashMap<String, String>>) OutputInvputaway.get("RstArr");
    
    	if(outIsFound.equals("1")){
    		CommUtil.logger.info(">PutawayToInv.Success: " +inPartNum+" Result Array size: "+rstArr.size());
/*    		for (int i = 0; i < rstArr.size(); i++) {
    			HashMap<String, String> retObj = rstArr.get(i);
    			CommUtil.logger.info(">Result Obj : "+i);
    			for (Entry<String, String> entry : retObj.entrySet()) {
    				CommUtil.logger.info("> Key:" + entry.getKey()+" value: "+ entry.getValue());
    			}
    		}*/
    		
    		
    	}
    	else if (outIsFound.equals("0")) {
    		CommUtil.logger.info(">ReceiveToPutaway.Part not found. PartNum: " +inPartNum );
    		return null;
    	}
    	else
    	{
    		return null;
    	}
    	return rstArr;
    }
    
    public HashMap<Object, Object> PutawayToInv_CA(HashMap<String, ?> InputObj) throws NoSuchElementException
    {
       HashMap<Object, Object> OutPutObj = new HashMap<Object, Object>();
       OutPutObj.put("IsFound", "");
   
       String outIsFound = "-1";
       
       String inSN = InputObj.get("SN").toString();
       //CommUtil.logger.info(inSN);
       String inProjCode = InputObj.get("ProjectCode").toString();
       //CommUtil.logger.info(inProjCode);
       String inPalletID = InputObj.get("PalletID").toString();
       //CommUtil.logger.info(inPalletID);
       String inBoxID = InputObj.get("BoxID").toString();
       //CommUtil.logger.info(inBoxID);
       String inLocation = InputObj.get("Location").toString();
       //CommUtil.logger.info(inLocation);
       String inPartNum = InputObj.get("PartNum").toString();
       //CommUtil.logger.info(inPartNum);
       String inPONum = InputObj.get("PONum").toString();
       //CommUtil.logger.info(inPONum);
       String inQty=InputObj.get("Qty").toString();
       
       String inStockGroup=InputObj.get("StockGroup").toString();
     
       
       CST_MainAction cstMain= new CST_MainAction(webdriver);
       cstMain.MenuInventory();
       InvIndexAction InvIndex = new InvIndexAction(webdriver);
       InvIndex.GotoReceivingPutaway();
       
       ReceivingPutawayAction ReceivingPutaway= new ReceivingPutawayAction(webdriver);
      
       HashMap<String, String> ReceivingPutawayInPutObj = new HashMap<String, String>();
       ReceivingPutawayInPutObj.put("Status","PendPutaway");
       ReceivingPutawayInPutObj.put("SN","");
       ReceivingPutawayInPutObj.put("ProjectCode",inProjCode);
       ReceivingPutawayInPutObj.put("PalletID",inPalletID);
       ReceivingPutawayInPutObj.put("BoxID",inBoxID);
       CommUtil.logger.info("SearchPutaway");
       outIsFound= ReceivingPutaway.SearchPutaway(ReceivingPutawayInPutObj);
       
       //CommUtil.logger.info("SearchPutaway : " + outIsFound);
      // RunAction "SearchPutaway [ReceivingPutaway]", oneIteration, "PendPutaway",inSN,inProjCode,inPalletID,inBoxID,outIsFound
       if(outIsFound.equals("1"))
       {
    	   CommUtil.logger.info(">SearchPutaway-PendPutaway Item found.");
       }
       else if(outIsFound.equals("0"))
       {
    	   CommUtil.logger.info(">SearchPutaway-PendPutaway Item not found. SN: " + inSN+" Projectcode : "+inProjCode+" PalletID : "+inPalletID+" BoxID"+inBoxID);
    	   return OutPutObj;
       }
       else
       {
    	   CommUtil.logger.info(">SearchPutaway-PendPutaway Item found.Error..");
    	   return OutPutObj;
       }
       
      CommUtil.logger.info("SaveToPutawayready");
       
      String OutRetVal= ReceivingPutaway.SaveToPutawayready(inLocation);
      //CommUtil.logger.info(" SaveToPutawayready : Return val"+OutRetVal);
      
      if(OutRetVal.equals("0")) {
   	   CommUtil.logger.info("SaveToPutawayready - Success");
      }
      else
      {
   	     CommUtil.logger.info("SaveToPutawayready - Failure. Code:"+OutRetVal);
   	     return OutPutObj;
      }
      
       ReceivingPutaway= new ReceivingPutawayAction(webdriver);
       
       ReceivingPutawayInPutObj = new HashMap<String, String>();
       ReceivingPutawayInPutObj.put("Status","PutawayReady");
       ReceivingPutawayInPutObj.put("SN","");
       ReceivingPutawayInPutObj.put("ProjectCode",inProjCode);
       ReceivingPutawayInPutObj.put("PalletID",inPalletID);
       ReceivingPutawayInPutObj.put("BoxID",inBoxID);
       CommUtil.logger.info("Calling SearchPutaway");
       outIsFound= ReceivingPutaway.SearchPutaway(ReceivingPutawayInPutObj);

       //CommUtil.logger.info("Calling SearchPutaway return val"+outIsFound);
     
       if(outIsFound.equals("1")){
    	   CommUtil.logger.info(">SearchPutaway-PutawayReady Item found.");
       }
       else if(outIsFound.equals("0")){
    	   CommUtil.logger.info(">SearchPutaway-PutawayReady Item not found. SN: " + inSN+" Projectcode : "+inProjCode+" PalletID : "+inPalletID+" BoxID"+inBoxID);
    	   return OutPutObj;
       }
       else{
    	   CommUtil.logger.info(">SearchPutaway-PutawayReady Item found.Error..");
    	   return OutPutObj;
       }
       CommUtil.logger.info("Calling SaveToPutaway");
       String outRetVal= ReceivingPutaway.SaveToPutaway(inQty);
       //CommUtil.logger.info("Calling SaveToPutaway : return val"+outRetVal);
      
       if(outRetVal.equals("0")) {
    	   CommUtil.logger.info("SaveToPutaway - Success");
       }
       else{
    	    CommUtil.logger.info("SaveToPutaway - Failure. Code:"+outRetVal);
    	    return OutPutObj;
       }
       
       cstMain= new CST_MainAction(webdriver); // correct it - new object
       cstMain.MenuInventory();
       InvIndex = new InvIndexAction(webdriver);
       InvIndex.GotoInventoryLookup();
       
      CommUtil.logger.info("Calling Inventory lookup...");
      InventoryLookupAction InvLookup = new InventoryLookupAction(webdriver);
      HashMap<String, String> InventoryLookupObj = new HashMap<String, String>();
      InventoryLookupObj.put("PartNum",inPartNum);
      InventoryLookupObj.put("SearchSN",inSN);
      InventoryLookupObj.put("SearchPONum",inPONum);
      InventoryLookupObj.put("ProjectCode",inProjCode);
      InventoryLookupObj.put("SearchStatus","");
      InventoryLookupObj.put("PalletID",inPalletID);
      InventoryLookupObj.put("StockGroup",inStockGroup);
      InventoryLookupObj.put("Qty", "1");   
      InventoryLookupObj.put("SearchBoxid", "");
      HashMap<Object, Object> OutPutInventoryObj=InvLookup.SearchInventory(InventoryLookupObj);
      // RunAction "SearchInventory [InventoryLookup]", oneIteration, inPartNum, inSN, inPONum, inProjCode,"","", outIsFound, outPartStatus, outBoxid, outLocation, outSN, outPONum
      OutPutObj.put("IsFound", OutPutInventoryObj.get("IsFound").toString());
  	  OutPutObj.put("RstArr", OutPutInventoryObj.get("RstArr"));
      
      return OutPutObj;
    }
    
}
