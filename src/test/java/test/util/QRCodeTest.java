package test.util;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.tonto.common.util.qrcode.QRCodeConfig;
import com.tonto.common.util.qrcode.QRCodeException;
import com.tonto.common.util.qrcode.QRCodeUtil;

public class QRCodeTest {
	
	
	public void test() throws FileNotFoundException, QRCodeException{
		
		String contents = "射点法大师傅都是fads发生的噶啥风格似的发射点噶附属国萨芬";
		OutputStream output = new FileOutputStream("d:/a.png");
		InputStream input = new FileInputStream("d:/f.png");
		
		QRCodeConfig config = new QRCodeConfig();
//		config.setShowLogoBorder(true);
//		config.setLogoBorderSize(10);
//		//config.setShowLogoBackground(true);
//config.setWidth(600);
//config.setHeight(600);
//		config.setBackgroundPadding(5);
//		config.setLogeBackground(Color.RED);
		
		QRCodeUtil.createQRCode(contents, output, input, config);
		
		
	}
	
	@Test
	public void aa(){
		String token = "   ";
		System.out.println(token);
		if((token=token.trim()).equals(""))
			System.out.println("sss:"+token);
		
		//
	}
	
}
