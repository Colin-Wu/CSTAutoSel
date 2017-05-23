package test_case.test380;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import action_lib.common_action.CommonAction;
import action_lib.odr_mngt.OrderAdminAction;
import action_lib.odr_mngt.OrderEntry1Action;
import action_lib.odr_mngt.OrderSummaryAction;
import config.TestSetting;
import script_lib.CommUtil;

public class Case92_9 {
	public static String run (WebDriver webdriver) throws Exception {
		String retVal = "-1";
		String vCustomer = TestSetting.Customer;
		String vUserName = TestSetting.UserName;
		String vProject = TestSetting.Project;
		String vSite = TestSetting.Site;
		String vOrdAttr = TestSetting.OrdAttr_ExtRepairWithHP;
		String vProjectPart1 = TestSetting.ProjectPart1;
		String vStockGroup = TestSetting.StockGroup;
		String vShipVia = TestSetting.ShipVia;
		String inExpectedReceiveSN = CommUtil.genDateTimeStamp();
		
		CommUtil.logger.info(" > GetAvailablePart for FR order.");	
		HashMap<String, String> InputObj = new HashMap<String, String>();
		InputObj.put("Qty", "1");
		CommonAction CA = new CommonAction(webdriver);
		ArrayList<HashMap<String, String>> retArr = CA.GetAvailablePart_CA(InputObj);
		if (retArr == null) {
			return retVal;
		}			
		if (retArr.size() > 0) {
			//HashMap<String, String> Retobj = retArr.get(0);
			//String inExpectedSendoutSN = Retobj.get("SN");
			
			//CommUtil.logger.info(" > inExpectedSendoutSN:" + inExpectedSendoutSN);	
			
			HashMap<String, String> retObj = null;
			HashMap<String, Serializable> CreateFROrders_CAInputObj = new HashMap<String, Serializable>();
			CreateFROrders_CAInputObj.put("Customer", vCustomer);
			CreateFROrders_CAInputObj.put("ProjectCode", vProject);
			CreateFROrders_CAInputObj.put("Site", vSite);
			CreateFROrders_CAInputObj.put("Attribute", vOrdAttr);
			CreateFROrders_CAInputObj.put("TemplateName", "");
			CreateFROrders_CAInputObj.put("ReqEmail", "11@22.com");
			CreateFROrders_CAInputObj.put("ProdLine", "");
			
			HashMap<String, String> PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", "");
			PartAddMap.put("StockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "0");
			PartAddMap.put("ReqQty", "");
			
			ArrayList<HashMap<String, String>> InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateFROrders_CAInputObj.put("ExpectedSendoutPart", InputArr);

			PartAddMap = new HashMap<String, String>();
			PartAddMap.put("SearchPartNum", vProjectPart1);
			PartAddMap.put("SearchBOM", "");
			PartAddMap.put("SearchSN", "");
			PartAddMap.put("ExpectedSN", inExpectedReceiveSN);
			PartAddMap.put("ReceiveStockGroup", vStockGroup);
			PartAddMap.put("IsConfig", "");
			
			InputArr = new ArrayList<HashMap<String, String>>();
			InputArr.add(PartAddMap);
			
			CreateFROrders_CAInputObj.put("ExpectedReceivePart", InputArr);

			CommUtil.logger.info(" > Create FR order.");
			retObj = CA.CreateFROrders_CA(CreateFROrders_CAInputObj);

			if (retObj != null) {
				
				String TotalOrderCnt = retObj.get("TotalOrderCnt").toString();
				
				if (TotalOrderCnt.equals("3")) {

					String ParentOrdNum = retObj.get("ParentOrdNum").toString();
					String C1Num = retObj.get("C1Num").toString();
					String C1PO = retObj.get("C1PO").toString();
					String C2Num = retObj.get("C2Num").toString();
					CommUtil.logger.info(" > Order created. Order count:" + TotalOrderCnt + ",Parent order:"+ParentOrdNum+",Child1 order:"+C1Num+",Child2 order:"+C2Num);
					
					HashMap<String, String> GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "2");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");
					CommUtil.logger.info(" > Cancel Child2 order.");					
					CommUtil.logger.info(" > Go to Child2 entry1.");
			    	String retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}					

					OrderEntry1Action OrderEntry1page = new OrderEntry1Action(webdriver);
					HashMap<String, String> CancelOrderEntry1InputObject = new HashMap<String, String>();
					CancelOrderEntry1InputObject.put("Mode", "1");
					//CancelOrderEntry1InputObject.put("HasCommitedPart", "");
					
					CommUtil.logger.info("> CancelOrderEntry1");
					retstr = OrderEntry1page.CancelOrderEntry1(CancelOrderEntry1InputObject);
					if(retstr.equals("0")) {
						CommUtil.logger.info("CancelOrderEntry1 - Success: Child2 order canceled.");
					} else {
						CommUtil.logger.info("CancelOrderEntry1 - Failure. Code:"+retstr);
						return retVal;	
					}
					
					CommUtil.logger.info("Check parent order and child1 not cancelled and child2 cancelled.");
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					HashMap<String, String> SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", ParentOrdNum);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					String isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
					
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		OrderAdminAction orderAdminpage = new OrderAdminAction(webdriver);
					HashMap<String, String> GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", ParentOrdNum);
					HashMap<String, String> RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					String OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (!OrdStatus.equals("Cancel")) {
							CommUtil.logger.info("Parent Order not be canceled.");
						} else {
							CommUtil.logger.info("Parent Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", C1Num);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					orderAdminpage = new OrderAdminAction(webdriver);
					isFound = orderAdminpage.SearchOrder(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", C1Num);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (!OrdStatus.equals("Cancel")) {
							CommUtil.logger.info("Child1 Order not be canceled.");
						} else {
							CommUtil.logger.info("Child1 Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", C2Num);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					orderAdminpage = new OrderAdminAction(webdriver);
					isFound = orderAdminpage.SearchOrder(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", C2Num);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("Cancel")) {
							CommUtil.logger.info("Child2 Order cancel successfully.");
						} else {
							CommUtil.logger.info("Child2 Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
					
					CommUtil.logger.info("> Activate other 2 orders.");
					GotoOrderEntry1InputObj = new HashMap<String, String>();
					GotoOrderEntry1InputObj.put("Mode", "2");
					GotoOrderEntry1InputObj.put("OrdNum", ParentOrdNum);
					GotoOrderEntry1InputObj.put("ProjectCode", vProject);
					GotoOrderEntry1InputObj.put("PONum", "");
					GotoOrderEntry1InputObj.put("Status", "");				
			    	retstr= CA.GotoOrderEntry1_CA(GotoOrderEntry1InputObj);
					if (retstr.equals("-1")) {
						CommUtil.logger.info(">GotoOrderEntry1_CA.Error..");
						return retVal;	
					}						
					
					OrderSummaryAction OrderSummarypage = new OrderSummaryAction(webdriver);

					OrderSummarypage.GetOrderSummary();
	
					
					HashMap<String, Serializable> InputObject = new HashMap<String, Serializable>();
					InputObject.put("ProjectCode", vProject);					
					InputObject.put("OrdNum", ParentOrdNum);					
					InputObject.put("UserName", vUserName);	
					InputObject.put("QAMode", "");		
					InputObject.put("caseid", "92_9");	
							
					ArrayList<HashMap<String, String>> boxArr = new ArrayList<HashMap<String, String>>();				
					InputObject.put("Boxids", boxArr);

					HashMap<String, String> trackMap = new HashMap<String, String>();
					trackMap.put("isFedex", "0");
					trackMap.put("TrackingNum", ParentOrdNum);
					
					ArrayList<HashMap<String, String>> TrackInputArr = new ArrayList<HashMap<String, String>>();
					TrackInputArr.add(trackMap);
					InputObject.put("TrackArr", TrackInputArr);
					CommUtil.logger.info(" > Complete parent order.");
					retVal = CA.CompleteDeploymentOrder_CA(InputObject);
					if(retVal.equals("0")) {
						CommUtil.logger.info("CompleteDeploymentOrder_CA - Success.");
					} else {
						CommUtil.logger.info("CompleteDeploymentOrder_CA - Failure. Code:"+retVal);
						return retVal;	
					}
					
					
					InputObject = new HashMap<String, Serializable>();
					InputObject.put("PickMode", "1");					
					InputObject.put("ShipMode", "0");								
					
					InputObject.put("ProjectCode", vProject);					
					InputObject.put("OrdNum", C1Num);									
					InputObject.put("PONum", C1PO);					
					InputObject.put("PartNum", vProjectPart1);					
					InputObject.put("Carrier", vShipVia);					
					InputObject.put("SN", inExpectedReceiveSN);	
					InputObject.put("TrackNum", C1PO);

					trackMap = new HashMap<String, String>();
					trackMap.put("isFedex", "0");
					trackMap.put("TrackingNum", C1PO);
					
					TrackInputArr = new ArrayList<HashMap<String, String>>();
					TrackInputArr.add(trackMap);
					InputObject.put("TrackArr", TrackInputArr);
					CommUtil.logger.info(" > Complete Child1 order.");
					retVal = CA.CompleteStandardRepairOrder_CA(InputObject);
					if(retVal.equals("0")) {
						CommUtil.logger.info("CompleteStandardRepairOrder_CA - Success.");
					} else {
						CommUtil.logger.info("CompleteStandardRepairOrder_CA - Failure. Code:"+retVal);
						return retVal;	
					}					
					
					
					CommUtil.logger.info("Check Child1 is complete.");				
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", C1Num);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					isFound = CA.SearchOrdAdmin_CA(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", C1Num);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("Complete")) {
							CommUtil.logger.info("Child1 Order is Complete.");
							
						} else {
							CommUtil.logger.info("Child1 Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
 					CommUtil.logger.info("> Check parent is completed.");
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", ParentOrdNum);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					orderAdminpage = new OrderAdminAction(webdriver);
					isFound = orderAdminpage.SearchOrder(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
					
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", ParentOrdNum);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("Complete")) {
							CommUtil.logger.info("Parent Order is Completed.");
						} else {
							CommUtil.logger.info("Parent Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}	
					
					CommUtil.logger.info("> SearchOrdAdmin_CA.");
					SearchOrdAdmin_CAInputObj = new HashMap<String, String>();
					SearchOrdAdmin_CAInputObj.put("OrdNum", C2Num);
					SearchOrdAdmin_CAInputObj.put("ProjectCode", vProject);
					SearchOrdAdmin_CAInputObj.put("PONum", "");
					SearchOrdAdmin_CAInputObj.put("Status", "");
					isFound = orderAdminpage.SearchOrder(SearchOrdAdmin_CAInputObj);
		        	if(!isFound.equals("1")) {
		        		CommUtil.logger.info(">SearchOrdAdmin_CA Failure. Code:" +isFound);
		        		return retVal;
		        	}
		        	
		    		CommUtil.logger.info(" > GetOrdInfoFromResult");
		    		orderAdminpage = new OrderAdminAction(webdriver);
					GetOrdInfoFromResultInputObj = new HashMap<String, String>();
					GetOrdInfoFromResultInputObj.put("OrdNum", C2Num);
					RetObj = orderAdminpage.GetOrdInfoFromResult(GetOrdInfoFromResultInputObj);
					retstr = RetObj.get("RetVal");
					OrdStatus = RetObj.get("OrdStatus");
					if(retstr.equals("0")) {
						CommUtil.logger.info("GetOrdInfoFromResult - Success");
						
						if (OrdStatus.equals("Cancel")) {
							retVal = "0";
							CommUtil.logger.info("Child2 is still cancelled.");
						} else {
							CommUtil.logger.info("Child2 Order status incorrect. status:"+OrdStatus);
							return retVal;	
						}
						
					} else {
						CommUtil.logger.info("GetOrdInfoFromResult - Failure. Code:"+retstr);
						return retVal;	
					}						

				}
			}
			
		}		
		
		return retVal;
	}

}
