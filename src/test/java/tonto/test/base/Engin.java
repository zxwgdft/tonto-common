package tonto.test.base;

/**
 * 发动机
 * @author TontoZhou
 *
 */
public class Engin {
	
	private Integer power;

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String toString(){
		
		return "发动机动力："+power+"匹";
		
	}
	
}
