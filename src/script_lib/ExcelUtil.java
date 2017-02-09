package script_lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import autoMain.AutoTest;
import config.TestSetting;




public class ExcelUtil {
	static XSSFWorkbook workbook = null;
	static Logger logger = Logger.getLogger(AutoTest.class.getName()) ; 
	
	public static void loadExcelFile () throws Exception {
		
		String fullfilename = System.getProperty("user.dir")+"\\"+TestSetting.ExcelFileName;
		
		logger.info("Loading Excel :" + fullfilename);
		workbook = new XSSFWorkbook(new FileInputStream(new File(fullfilename)));
		
	}
	
	public static ArrayList<HashMap<String, String>> getAllExecuteCases () throws Exception {
		
		ArrayList<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
		
		XSSFSheet sht = workbook.getSheet(TestSetting.ExcelConsoleSheet);
		
		for (int i = 0; i < sht.getPhysicalNumberOfRows(); i++) {
			XSSFRow row = sht.getRow(i);
			XSSFCell extcell = row.getCell(TestSetting.TestExcuteCol);	
		
			if (extcell != null) {
				if ("Y".equals(extcell.toString().toUpperCase())) {
					HashMap<String, String> retMap = new HashMap<String, String>();
					XSSFCell testcasecell = row.getCell(TestSetting.TestCaseCol);
					
					retMap.put("rowNum", Integer.toString(i));
					retMap.put("casename", testcasecell.toString());
					retMap.put("result", "");
					retlist.add(retMap);
				}				
			}

		}
			
		return retlist;
	}
	
	public static void updateExcelResult () throws Exception {
		
		loadExcelFile();

        for (int i = 0; i < TestSetting.caselist.size(); i++) {
        	HashMap<?, ?> map = (HashMap<?, ?>) TestSetting.caselist.get(i);
        	int currentRow = Integer.parseInt((String) map.get("rowNum"));
        	String result = (String) map.get("result");
        	String exeDate = (String) map.get("exeDate");
        	setResult(currentRow,result,exeDate);
        }
		ExcelUtil.closeWorkbook();
	}
	public static void setResult (int rowNum, String result, String exeDate) throws Exception {
			
		XSSFSheet sht = workbook.getSheet(TestSetting.ExcelConsoleSheet);
		XSSFRow row = sht.getRow(rowNum);
	
		XSSFCell rstcell = row.createCell(TestSetting.TestResultCol);
		rstcell.setCellValue(result);
		
/*
 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String extDate = df.format(new Date());
		*/
		XSSFCell datecell = row.createCell(TestSetting.TestRunDateCol);
		datecell.setCellValue(exeDate);
		
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"\\"+TestSetting.ExcelFileName); 
        workbook.write(fileOut);  
        fileOut.close();

	}
	public static void clearResult () throws Exception {
		
		XSSFSheet sht = workbook.getSheet(TestSetting.ExcelConsoleSheet);
		
		for (int i = TestSetting.TestStartRow; i < sht.getPhysicalNumberOfRows(); i++) {

    		XSSFRow row = sht.getRow(i);
  		
    		XSSFCell rstcell = row.createCell(TestSetting.TestResultCol);
    		rstcell.setCellValue("");
    		
    		XSSFCell datecell = row.createCell(TestSetting.TestRunDateCol);
    		datecell.setCellValue("");
    		
		}
		
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"\\"+TestSetting.ExcelFileName); 
        workbook.write(fileOut);  
        fileOut.close();

	}

	public static void closeWorkbook() throws Exception {
		logger.info("Closing Excel.");		
		workbook.close();
		workbook = null;
	}
}
