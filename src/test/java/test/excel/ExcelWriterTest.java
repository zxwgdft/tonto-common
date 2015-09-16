package test.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.tonto.common.excel.ExcelWriteException;
import com.tonto.common.excel.ExcelWriter;

public class ExcelWriterTest {
	
	@Test
	public void writeTest() throws ExcelWriteException, IOException
	{
		FileOutputStream output=new FileOutputStream("d:/aa.xlsx");
		ExcelWriter<Data> writer=new ExcelWriter<Data>(Data.class,new XSSFWorkbook());
		
		List<Data> datas=new ArrayList<Data>();
		Data d1=new Data();
		d1.birthday=new Date();
		d1.sex=1;
		d1.name="Jack";
		
		Data d2=new Data();
		d2.birthday=new Date();
		d2.sex=0;
		d2.name="Mary";
		
		datas.add(d1);
		datas.add(d2);
		
		writer.writeAll(datas);
		writer.output(output);
		
	}
}
