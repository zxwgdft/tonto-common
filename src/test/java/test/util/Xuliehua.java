package test.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.tonto.common.im.request.message.Message;
import com.tonto.common.im.request.message.body.MessageBody;
import com.tonto.common.im.request.message.body.TextMessageBody;

public class Xuliehua {
	
	@Test
	public void aaa() throws IOException, ClassNotFoundException{
		MessageBody body=new TextMessageBody("sssss");
		
		Message a=new Message();
		a.setMsg(body);
		
		
		ByteArrayOutputStream out =new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);  
		os.writeObject(a); 
		
		
		ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
		Message b =(Message) is.readObject();
		
		
		System.out.println("sfsdf");
	}
	
}
