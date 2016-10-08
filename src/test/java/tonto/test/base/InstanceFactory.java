package tonto.test.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceFactory {
	
	public static Car getCar(){
				
		List<Wheel> wheels = new ArrayList<>();
		
		for(int i = 0; i < 4; i++)
		{
			Wheel w1 = new Wheel();
			w1.setColor("红");
			w1.setSize(15);
			w1.setType(1);
			
			wheels.add(w1);
			
		}
		
		Engin engin = new Engin();
		engin.setPower(500);
		
		
		Car car = new Car();
		car.setEngin(engin);
		car.setName("阿什顿马丁");
		car.setSeatNum(4);
		car.setWheels(wheels);
		
		
		Map<String,Object> datas = new HashMap<>();
		datas.put("door", "OPEN AIR");
		datas.put("speed", 1000);
		datas.put("weight", 1.5);
		
		car.setDatas(datas);
		
		
		return car;
		
	}
	
}
