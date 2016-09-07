package action_lib.odr_mngt;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.OrderSummaryObj;
import script_lib.SeleniumUtil;

public class OrderSummaryAction {
	WebDriver webdriver;
	
	public OrderSummaryAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public HashMap<String, String> GetOrderSummary () throws NoSuchElementException  {
	
		String vTotalOrderCnt = "";
		String vParentOrdNum = "";
		String vPstatus = "";
		String vParentPO = "";
		String vC1Num = "";
		String vC1Status = "";
		String vC1PO = "";
		String vC2Num = "";
		String vC2Status = "";
		String vC2PO = "";
		
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("TotalOrderCnt", vTotalOrderCnt);
		RetObj.put("ParentOrdNum", vParentOrdNum);
		RetObj.put("Pstatus", vPstatus);
		RetObj.put("ParentPO", vParentPO);
		RetObj.put("C1Num", vC1Num);
		RetObj.put("C1Status", vC1Status);
		RetObj.put("C1PO", vC1PO);
		RetObj.put("C2Num", vC2Num);
		RetObj.put("C2Status", vC2Status);
		RetObj.put("C2PO", vC2PO);
		

		OrderSummaryObj Obj = new OrderSummaryObj(webdriver);

		
		WebElement TblSummary = Obj.getTblSummary();

		int tblRow = SeleniumUtil.getTableRows(TblSummary);
		

		
		vTotalOrderCnt = Integer.toString(tblRow);
		
		List<WebElement> rows = TblSummary.findElements(By.xpath("./tr")); 

		
		if (tblRow >= 1) {


			WebElement tblrow = rows.get(0);
			List<WebElement> cells = tblrow.findElements(By.tagName("td")); 
			WebElement tdParentOrdNum = cells.get(Obj.getvOrdNumSeq());
			WebElement tdPstatus = cells.get(Obj.getvStatusSeq());
			WebElement tdParentPO = cells.get(Obj.getvPOSeq());
			
			By ParentCellLocator = Obj.getParentCellLocator();			
			
			WebElement CellParentOrdNum = tdParentOrdNum.findElement(ParentCellLocator);
			WebElement CellPstatus = tdPstatus.findElement(ParentCellLocator);
			WebElement CellParentPO = tdParentPO.findElement(ParentCellLocator);		
			
			vParentOrdNum = CellParentOrdNum.getText();
			vPstatus = CellPstatus.getText();
			vParentPO = CellParentPO.getText();

			
			if (tblRow >= 2) {

				tblrow = rows.get(1);
				cells = tblrow.findElements(By.tagName("td")); 

				WebElement tdC1Num = cells.get(Obj.getvOrdNumSeq());
				WebElement tdC1Status = cells.get(Obj.getvStatusSeq());
				WebElement tdC1PO = cells.get(Obj.getvPOSeq());

				By ChildCellLocator = Obj.getChildCellLocator();					
				By ChildCellLegendLocator = Obj.getChildCellLegendLocator();			
				
				WebElement CellC1Num = tdC1Num.findElement(ChildCellLocator);
				WebElement CellLegendC1Num = tdC1Num.findElement(ChildCellLegendLocator);
				WebElement CellC1Status = tdC1Status.findElement(ChildCellLocator);
				WebElement CellLegendC1Status = tdC1Status.findElement(ChildCellLegendLocator);
				WebElement CellC1PO = tdC1PO.findElement(ChildCellLocator);		
				WebElement CellLegendC1PO = tdC1PO.findElement(ChildCellLegendLocator);		
				
				String CellTxtC1Num = CellC1Num.getText();
				String CellLegendTxtC1Num = CellLegendC1Num.getText();				
				vC1Num = CellTxtC1Num.replaceAll(CellLegendTxtC1Num, "").trim();
				
				String CellTxtC1Status = CellC1Status.getText();
				String CellLegendTxtC1Status = CellLegendC1Status.getText();				
				vC1Status = CellTxtC1Status.replaceAll(CellLegendTxtC1Status, "").trim();
				
				String CellTxtC1PO = CellC1PO.getText();
				String CellLegendTxtC1PO = CellLegendC1PO.getText();				
				vC1PO =  CellTxtC1PO.replaceAll(CellLegendTxtC1PO, "").trim();
			
				
				if (tblRow == 3) {
					tblrow = rows.get(2);
					cells = tblrow.findElements(By.tagName("td")); 

					WebElement tdC2Num = cells.get(Obj.getvOrdNumSeq());
					WebElement tdC2Status = cells.get(Obj.getvStatusSeq());
					WebElement tdC2PO = cells.get(Obj.getvPOSeq());			
					
					WebElement CellC2Num = tdC2Num.findElement(ChildCellLocator);
					WebElement CellLegendC2Num = tdC2Num.findElement(ChildCellLegendLocator);
					WebElement CellC2Status = tdC2Status.findElement(ChildCellLocator);
					WebElement CellLegendC2Status = tdC2Status.findElement(ChildCellLegendLocator);
					WebElement CellC2PO = tdC2PO.findElement(ChildCellLocator);		
					WebElement CellLegendC2PO = tdC2PO.findElement(ChildCellLegendLocator);		
					
					String CellTxtC2Num = CellC2Num.getText();
					String CellLegendTxtC2Num = CellLegendC2Num.getText();				
					vC2Num = CellTxtC2Num.replaceAll(CellLegendTxtC2Num, "").trim();
					
					String CellTxtC2Status = CellC2Status.getText();
					String CellLegendTxtC2Status = CellLegendC2Status.getText();				
					vC2Status = CellTxtC2Status.replaceAll(CellLegendTxtC2Status, "").trim();
					
					String CellTxtC2PO = CellC2PO.getText();
					String CellLegendTxtC2PO = CellLegendC2PO.getText();				
					vC2PO =  CellTxtC2PO.replaceAll(CellLegendTxtC2PO, "").trim();					
				}
				
				
				
			}
			
		}		
		
		
		RetObj.put("TotalOrderCnt", vTotalOrderCnt);
		RetObj.put("ParentOrdNum", vParentOrdNum);
		RetObj.put("Pstatus", vPstatus);
		RetObj.put("ParentPO", vParentPO);
		RetObj.put("C1Num", vC1Num);
		RetObj.put("C1Status", vC1Status);
		RetObj.put("C1PO", vC1PO);
		RetObj.put("C2Num", vC2Num);
		RetObj.put("C2Status", vC2Status);
		RetObj.put("C2PO", vC2PO);
		

		return RetObj;
	}
}
