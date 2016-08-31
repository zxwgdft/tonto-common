package tonto.test.excel.read;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.tonto2.common.excel.base.DefaultSheet;
import com.tonto2.common.excel.read.ExcelReader;
import com.tonto2.common.excel.read.exception.ExcelReadException;


public class ExcelReaderTest {
	
	@Test
	public void testRead() throws IOException, ExcelReadException{
		XSSFWorkbook workbook = new XSSFWorkbook(ExcelReaderTest.class.getResourceAsStream("test2user.xlsx"));
		ExcelReader<User> reader = new ExcelReader<User>(User.class, new DefaultSheet(workbook.getSheetAt(0)),1);
		List<User> users = reader.readRows();
		
		
		for(User user: users)
			System.out.println(user);
	}
}
