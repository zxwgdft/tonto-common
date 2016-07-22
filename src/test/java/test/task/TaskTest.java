package test.task;

import org.junit.Test;

import com.tonto.common.timer.Task;
import com.tonto.common.timer.TaskContainer;

public class TaskTest {
	
	
	@Test
	public void a() throws InterruptedException{
		
		
		final TaskContainer tc = new TaskContainer();
		
		tc.start();
		
		
		Thread t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				
				final A a = new A();
				
				while(true)
				{
					tc.addTask(new Task(){
	
						@Override
						public void doTask() {
							
							System.out.println("执行线程一："+a.toString());
							a.add();
						}
						
					});
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}

			}
			
		});
		
		Thread t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				
				final A a = new A();
				
				while(true)
				{
					tc.addTask(new Task(){
	
						@Override
						public void doTask() {
							
							System.out.println("执行线程二："+a.toString());
							a.add();
						}
						
					});
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
			
		});
		
		Thread t3 = new Thread(new Runnable(){

			@Override
			public void run() {
				
				final A a = new A();
				
				while(true)
				{
					tc.addTask(new Task(){
	
						@Override
						public void doTask() {
							
							System.out.println("执行线程三："+a.toString());
							a.add();
						}
						
					});
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
			
		});
		
		
		t1.start();
		t2.start();
		t3.start();
		
		
		Thread.sleep(1000000);
		
	}
	
	
	private class A{
		
		private int i = 1;
		
		public void add(){
			
			i++;
		}
		
		public String toString(){
			return i+"";
		}
		
	}
	
}
