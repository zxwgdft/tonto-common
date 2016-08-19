package test.excel;

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
		Address a1=new Address();
		Address a2=new Address();
		List<District> ds1=new ArrayList<District>();
		List<District> ds2=new ArrayList<District>();
		
		List<Address> as1=new ArrayList<Address>();
		List<Address> as2=new ArrayList<Address>();
		
		District d1=new District();
		d1.district="城里";
		District d2=new District();
		d2.district="乡下";
		District d3=new District();
		d3.district="郊区";
		
		ds1.add(d1);
		ds1.add(d2);
		
		ds2.add(d1);
		ds2.add(d3);
		
		a1.districts=ds1;
		a1.address="华容道1号";
		a2.districts=ds2;
		a2.address="鲁夫解";
		
		as1.add(a1);
		as2.add(a1);
		as2.add(a2);
		
		List<Data> datas=new ArrayList<Data>();
		Data da1=new Data();
		da1.birthday=new Date();
		da1.sex=1;
		da1.name="Jack";
		da1.addresses=as1;
		
		
		Data da2=new Data();
		da2.birthday=new Date();
		da2.sex=0;
		da2.name="Mary";
		da2.addresses=as2;
		
		//datas.add(da1);
		datas.add(da2);
		
		writer.writeAll(datas);
		writer.output(output);
		
	}

}
