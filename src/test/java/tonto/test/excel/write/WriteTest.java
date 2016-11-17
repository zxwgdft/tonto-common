package tonto.test.excel.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.tonto2.common.excel.write.DefaultExcelWriter;
import com.tonto2.common.excel.write.ExcelWriter;
import com.tonto2.common.excel.write.exception.ExcelWriteException;
import com.tonto2.common.utils.random.RandomObject;

public class WriteTest {
	
	@Test
	public void test() throws ExcelWriteException, FileNotFoundException, IOException
	{
		RandomObject randomObject= new RandomObject();
		
		List<Teacher> ts = new ArrayList<Teacher>();
		
		for(int i=0;i<10;i++)
			ts.add( randomObject.createRandomObject(Teacher.class));
		
		Workbook workbook = new HSSFWorkbook();
		
		ExcelWriter<Teacher> writer= new DefaultExcelWriter<Teacher>(workbook,Teacher.class);
		writer.writeTitle();
		writer.write(ts);
		writer.output(new FileOutputStream("g:/test/a.xls"));
		
	}
	
}
