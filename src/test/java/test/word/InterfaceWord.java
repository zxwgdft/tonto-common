package test.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tonto.common.util.ducument.interfaces.Attribution;
import com.tonto.common.util.ducument.interfaces.InterfaceDocumentUtil;
import com.tonto.common.util.ducument.interfaces.DefaultAttribution;
import com.tonto.common.word.WordFileTemplate;
import com.tonto.common.word.WordFreeMakerTemplate;
import com.tonto.common.word.WordWriter;

import freemarker.template.Configuration;

public class InterfaceWord {
	
	@Test
	public void AA() throws IOException{
		
		Attribution attribution = DefaultAttribution.createAttribution(FundInvestigationInfo.class);
		
		List<Attribution> attributions = new ArrayList<>();		
		
		InterfaceDocumentUtil.toList(attribution, attributions);
	
		
		Configuration cfg=new Configuration();		
		cfg.setDirectoryForTemplateLoading(new File("src/test/resources"));
		
		Map<String,Object> data=new HashMap<>();
		data.put("objects", attributions);
		
		WordWriter writer=new WordWriter();
		
		writer.add(new WordFreeMakerTemplate(cfg.getTemplate("interface.xml"),data));
		
		writer.write(new FileOutputStream("d:/interface.doc"));
		
	}
	
}
