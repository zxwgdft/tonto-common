package test.validate;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.tonto.common.util.RandomObject;
import com.tonto.common.validate.FieldValidator;
import com.tonto.common.validate.Validate;
import com.tonto.common.validate.annotation.Digit;
import com.tonto.common.validate.annotation.IntegerEnum;
import com.tonto.common.validate.annotation.Length;

public class ValidateTest {
	
	@Test
	public void validate()
	{
		System.out.println("13584950680".matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|6|7|8]|18[0|1|2|3|5|6|7|8|9])\\d{8}$"));
		
		
		FieldValidator v=new FieldValidator();
		A a=new RandomObject().createRandomObject(A.class);
		
		v.addValidate("bs.ii", new Validate(){

			@Override
			public String validate(Object value,Object currentObj, Object validateObj) {
				return "错误啊";
			}
			
		});
		int count=1;
		Date now=new Date();
		for(int i=count;i>0;i--)
		{
			System.out.println(v.validate(a));
		}
		System.out.println(new Date().getTime()-now.getTime());
				
	}
	
	
	public static class B{
		@Length(minLength=1,maxLength=5)
		String aa;
		
		@IntegerEnum({1,2})
		Integer ii;
		public String getAa() {
			return aa;
		}
		public void setAa(String aa) {
			this.aa = aa;
		}
		public Integer getIi() {
			return ii;
		}
		public void setIi(Integer ii) {
			this.ii = ii;
		}
	}
	
	public static class A{
		@Length(minLength=1,maxLength=5)
		String a;
		@Digit(min="1",max="4")
		Integer i;
		
		List<B> bs;
		B[] bs2;
		public String getA() {
			return a;
		}
		public void setA(String a) {
			this.a = a;
		}
		public Integer getI() {
			return i;
		}
		public void setI(Integer i) {
			this.i = i;
		}
		public List<B> getBs() {
			return bs;
		}
		public void setBs(List<B> bs) {
			this.bs = bs;
		}
		public B[] getBs2() {
			return bs2;
		}
		public void setBs2(B[] bs2) {
			this.bs2 = bs2;
		}
		
		
		
	}
}
