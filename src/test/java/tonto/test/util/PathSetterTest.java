package tonto.test.util;

import org.junit.Test;

import tonto.test.base.Car;

import com.tonto2.common.utils.reflect.PathSetter;

public class PathSetterTest {
	
	@Test
	public void test()
	{
		Car car = new Car();
		
		PathSetter.set(car, "wheels.1.size", 100);
		
		System.out.println(car.getWheels().get(1).getSize());
		
		PathSetter.set(car, "datas.address.district", "浦东");
		
		System.out.println(car.getDatas());
		
		
	}
	
}
