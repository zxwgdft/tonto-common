

package test.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import com.tonto.common.excel.ExcelReadException;
import com.tonto.common.excel.ExcelReader;
import com.tonto.common.excel.ValueFormatException;
import com.tonto.common.excel.base.DefaultSheet;

public class ExcelReaaderTest{
	
	
	public void readExcelTest() 
	{
			
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(ExcelReaaderTest.class.getClassLoader().getResourceAsStream("test.xls"));
			ExcelReader<User> reader=new ExcelReader<User>(User.class,new DefaultSheet(workbook.getSheetAt(0)),2);
			
			List<User> users=reader.readRows();
			
			Assert.assertEquals(users.size(), 3);
			Assert.assertEquals(users.get(0).age, new Integer(6666));
		} catch (ExcelReadException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValueFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void read07ExcelTest() 
	{
			
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(ExcelReaaderTest.class.getClassLoader().getResourceAsStream("导入模板.xlsx"));
			ExcelReader<Customer2excel> reader=new ExcelReader<Customer2excel>(Customer2excel.class,new DefaultSheet(workbook.getSheetAt(0)),2);
			
			List<Customer2excel> users=reader.readRows();
			
			Assert.assertEquals(users.size(), 1);
		} catch (ExcelReadException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValueFormatException e) {
			e.printStackTrace();
		}
	}
}
