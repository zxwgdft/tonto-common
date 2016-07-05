package test.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.tonto.common.util.RandomUtil;
import com.tonto.common.word.WordFileTemplate;
import com.tonto.common.word.WordFreeMakerTemplate;
import com.tonto.common.word.WordWriter;

import freemarker.template.Configuration;

public class QuestionWordTest {
	@Test
	public void test() throws Exception
	{
		List<Map<String,Object>> questions=new ArrayList<>();
		
		for(int i=0;i<2;i++)
			questions.add(createQuestion());
		
		
		List<List<String[]>> tables=new ArrayList<>();
		
		tables.add(createTable());
		tables.add(createTable());
		
		HashMap<String,Object> data=new HashMap<>();
		data.put("questionList", questions);
		data.put("questionScoreTable", tables);
		data.put("questionScoreType", createTypeTable());
		
		
		String[] strs=new String[5];
		strs[0]="分值范围";
		strs[1]="保守型";
		strs[2]="稳健型";
		strs[3]="平衡型";
		strs[4]="成长型";
		
		
		
		data.put("questionScoreTypes", strs);
		data.put("caImage",encodeFile("D:/ca.png"));
		
		
		FundInvestigationInfo info=new FundInvestigationInfo();
		info.setName("周旭武");
		info.setContactAddress("东城大道112号");
		info.setContactWay("1353020303");
		info.setCredentialsNumber("3201555344444");
		info.setCredentialsType("身份证");
		info.setDateOfCreate(new Date());
		
		data.put("customer",info);
		Configuration cfg=new Configuration();		
		cfg.setDirectoryForTemplateLoading(new File("src/test/resources"));
		
		
		WordWriter writer=new WordWriter();
		
		//writer.add(new WordFileTemplate(WordTest.class.getClassLoader().getResourceAsStream("word_1.xml")));		
		writer.add(new WordFreeMakerTemplate(cfg.getTemplate("fund_word_template_b2.xml"),data));
		
		writer.write(new FileOutputStream("d:/aadd.doc"));
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
	
	public static String[] indexs={"A", "B", "C", "D", "E", "F", "G",
		"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z"};
	
	public static Map<String,Object> createAnswer(int index)
	{

		HashMap<String,Object> a=new HashMap<>();
		a.put("title", indexs[index]);
		a.put("content", RandomUtil.getRandomInt(100));
		a.put("mark", RandomUtil.getRandomInt(10));
		
		return a;
	}
	
	public static Map<String,Object> createQuestion()
	{
		int x= RandomUtil.getRandomInt(100);
		int y= RandomUtil.getRandomInt(100);
		int z= RandomUtil.getRandomInt(4);
		
		HashMap<String,Object> q1=new HashMap<>();
		q1.put("name", x+"+"+y+"=?");
		q1.put("select", indexs[z]);
		
		List<Map<String,Object>> answers=new ArrayList<>();
		answers.add(createAnswer(0));
		answers.add(createAnswer(1));
		answers.add(createAnswer(2));
		answers.add(createAnswer(3));
		
		q1.put("answer", answers);
		
		return q1;
	}
	
	public static List<String[]> createTypeTable(){
		int num=5;
		
		String[] strs=new String[num];
		strs[0]="分值范围";
		strs[1]="保守型";
		strs[2]="稳健型";
		strs[3]="平衡型";
		strs[4]="成长型";
		
		String[] str1=new String[num];
		str1[0]="分值上限";
		str1[1]="32";
		str1[2]="32";
		str1[3]="32";
		str1[4]="43";
		
		String[] str2=new String[num];
		str2[0]="分值上限";
		str2[1]="32";
		str2[2]="32";
		str2[3]="32";
		str2[4]="43";
		
		
		List<String[]> list=new ArrayList<>();
		list.add(strs);
		list.add(str1);
		list.add(str2);
		
		return list;
	}
	
	public static List<String[]> createTable(){
		int num=11;
		
		String[] strs=new String[num];
		strs[0]="序号";
		
		for(int i=1;i<num;i++)
		{
			strs[i]=i+"";		
		}
		
		List<String[]> list=new ArrayList<>();
		
		list.add(strs);
		for(int j=0;j<6;j++)
			list.add(createRow(indexs[j],num));
		
		return list;
	}
	
	public static String[] createRow(String title,int rnum)
	{
		String[] strs=new String[rnum];
		strs[0]=title;
		
		for(int i=1;i<rnum;i++)
		{
			strs[i]=RandomUtil.getRandomInt(9)+"分";			
		}
		
		return strs;
	}
	
}
