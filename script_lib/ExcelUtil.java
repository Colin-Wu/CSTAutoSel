package script_lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import autotMain.AutoTest;
import config.TestSetting;



public class ExcelUtil {
	static XSSFWorkbook workbook = null;
	static Logger logger = Logger.getLogger(AutoTest.class.getName()) ; 
	
	public static void loadExcelFile () throws Exception {
		
		String fullfilename = System.getProperty("user.dir")+"\\"+TestSetting.ExcelFileName;
		
		logger.info("Loading Excel :" + fullfilename);
		workbook = new XSSFWorkbook(new FileInputStream(new File(fullfilename)));
		
	}
	
	public static ArrayList<HashMap<String, Comparable>> getAllExecuteCases () throws Exception {
		
		ArrayList<HashMap<String, Comparable>> retlist = new ArrayList<HashMap<String, Comparable>>();
		
		XSSFSheet sht = workbook.getSheet(TestSetting.ExcelConsoleSheet);
		
		for (int i = 0; i < sht.getPhysicalNumberOfRows(); i++) {
			XSSFRow row = sht.getRow(i);
			XSSFCell extcell = row.getCell(TestSetting.TestExcuteCol);	
		
			if (extcell != null) {
				if ("Y".equals(extcell.toString().toUpperCase())) {
					HashMap<String, Comparable> retMap = new HashMap<String, Comparable>();
					XSSFCell testcasecell = row.getCell(TestSetting.TestCaseCol);
					
					retMap.put("rowNum", i);
					retMap.put("casename", testcasecell.toString());
					retlist.add(retMap);
				}				
			}

		}
			
		return retlist;
	}
	public static void setResult (int rowNum, String result) throws Exception {
			
		XSSFSheet sht = workbook.getSheet(TestSetting.ExcelConsoleSheet);
		XSSFRow row = sht.getRow(rowNum);
	
		XSSFCell rstcell = row.createCell(TestSetting.TestResultCol);
		rstcell.setCellValue(result);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String extDate = df.format(new Date());
		XSSFCell datecell = row.createCell(TestSetting.TestRunDateCol);
		datecell.setCellValue(extDate);
		
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"\\"+TestSetting.ExcelFileName); 
        workbook.write(fileOut);  
        fileOut.close();

	}
	public static void clearResult () throws Exception {
		
		XSSFSheet sht = workbook.getSheet(TestSetting.ExcelConsoleSheet);
		
		for (int i = 0; i < sht.getPhysicalNumberOfRows(); i++) {

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
