package test.word;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		
		Configuration cfg=new Configuration();		
		cfg.setDirectoryForTemplateLoading(new File("src/test/resources"));
		
		
		WordWriter writer=new WordWriter();
		
		//writer.add(new WordFileTemplate(WordTest.class.getClassLoader().getResourceAsStream("word_1.xml")));		
		writer.add(new WordFreeMakerTemplate(cfg.getTemplate("word_2.xml"),data));
		
		writer.write(new FileOutputStream("d:/fxpg.xml"));
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
		
		HashMap<String,Object> q1=new HashMap<>();
		q1.put("name", x+"+"+y+"=?");
		
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
