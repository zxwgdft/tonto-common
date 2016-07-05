package test.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import test.excel.ExcelReaaderTest;

import com.tonto.common.word.WordFileTemplate;
import com.tonto.common.word.WordFreeMakerTemplate;
import com.tonto.common.word.WordWriter;

import freemarker.template.Configuration;

public class WordTest {
	
	@Test
	public void test() throws Exception
	{
		List<Map<String,Object>> datas=new ArrayList<>();
		
		List<Map<String,String>> info=new ArrayList<>();
		
		Map<String,String> info1=new HashMap<>();
		info1.put("infoFile", encodeFile("d:/dd.jpeg"));
		info1.put("infoName", "正面照片");
		
		Map<String,String> info2=new HashMap<>();
		info2.put("infoFile", encodeFile("d:/d.jpeg"));
		info2.put("infoName", "反面照片");
		
		Map<String,String> info3=new HashMap<>();
		info3.put("infoFile", encodeFile("d:/d.jpeg"));
		info3.put("infoName", "反面照片");
		
		Map<String,String> info4=new HashMap<>();
		info4.put("infoFile", encodeFile("d:/d.jpeg"));
		info4.put("infoName", "反面照片");
		
		Map<String,String> info5=new HashMap<>();
		info5.put("infoFile", encodeFile("d:/d.jpeg"));
		info5.put("infoName", "反面照片");
		
		Map<String,String> info6=new HashMap<>();
		info6.put("infoFile", encodeFile("d:/d.jpeg"));
		info6.put("infoName", "反面照片");
		
		info.add(info1);
		info.add(info2);
		info.add(info3);
		info.add(info4);
		info.add(info5);
		info.add(info6);
		
		Map<String,Object> data1=new HashMap<>();
		data1.put("orderNumber", "S00001");
		data1.put("progressName", "Tonto");
		data1.put("salesman", "周旭武");
		data1.put("telephone", "13584950680");
		data1.put("dateOfCreate", "2015-3-2 12:00:00");
		data1.put("productName", "");
		data1.put("totalAmount", "132352.22元");
		data1.put("customerName", "季佩");
		data1.put("gender", "男");
		data1.put("nationality", "中国");
		data1.put("cellphone", "13584950680");
		data1.put("idType", "身份证");
		data1.put("idNumber", "320583198806119617");
		//data1.put("paymentInformation", "");
		data1.put("info", info);
		
		Map<String,Object> data2=new HashMap<>();		
		data2.put("orderNumber", "S00002");
		data2.put("progressName", "Tonto");
		data2.put("salesman", "熊峰");
		data2.put("telephone", "13584950680");
		data2.put("dateOfCreate", "2015-3-2 12:00:00");
		data2.put("productName", "");
		data2.put("totalAmount", "366352.22元");
		data2.put("customerName", "陈敏");
		data2.put("gender", "女");
		data2.put("nationality", "中国");
		data2.put("cellphone", "13584950680");
		data2.put("idType", "身份证");
		data2.put("idNumber", "320583198806119617");
		data2.put("paymentInformation", encodeFile("d:/beij.jpeg"));
		
		datas.add(data1);
		datas.add(data2);
		
		Configuration cfg=new Configuration();		
		cfg.setDirectoryForTemplateLoading(new File("src/test/resources"));
		
		Map<String,Object> data=new HashMap<>();
		data.put("orders", datas);
		
		WordWriter writer=new WordWriter();
		
		writer.add(new WordFileTemplate(WordTest.class.getClassLoader().getResourceAsStream("word_1.xml")));		
		writer.add(new WordFreeMakerTemplate(cfg.getTemplate("word_2.xml"),data));
		
		writer.write(new FileOutputStream("d:/word.doc"));
	}
	
	
	private String encodeFile(String filePath) throws Exception
	{
		byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[2048];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
         }
        
        return Base64.encodeBase64String(data);
	}
	
	
}
