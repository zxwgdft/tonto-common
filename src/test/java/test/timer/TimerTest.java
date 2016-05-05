package test.timer;

import java.util.Timer;

import org.junit.Test;

import com.tonto.common.timer.Task;
import com.tonto.common.timer.UniqueTaskContainer;

public class TimerTest {
	
	@Test
	public void test() throws InterruptedException
	{
		Timer timer=new Timer(false);
		
		UniqueTaskContainer container=new UniqueTaskContainer();
		timer.schedule(container.createTask(1, new Task(){
			@Override
			public void doTask() {
				System.out.println("执行任务1");
			}			
		}),3000);
		
		Thread.sleep(1000);
		
		timer.schedule(container.createTask(1, new Task(){
			@Override
			public void doTask() {
				System.out.println("重新执行任务1");
			}			
		}),3000);
		
		Thread.sleep(1000);
		
		timer.schedule(container.createTask(2, new Task(){
			@Override
			public void doTask() {
				System.out.println("执行任务2");
			}			
		}),3000);
		
		
		Thread.sleep(100000);
	}
}
