
package com.npb.gp;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.npb.gp.dao.mysql.GpJobDao;
import com.npb.gp.domain.core.GpJob;

public class GpBatchGen {

	private static GpJobDao gpjobs;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//GpServerJavaSpringGenService.flag = "green";
		//GpServerJavaSpringGenService.flag = args[0];
		
		ApplicationContext context = new
	    		 ClassPathXmlApplicationContext("classpath:spring_config.xml");
		gpjobs = (GpJobDao) context.getBean("GpJobDao");
		// TODO Auto-generated method stub
		System.out.println("Generator started...");
		while(true){
			try {
			     List<GpJob> jobs = gpjobs.find_jobs();
			     
			     for(int i = 0;i < jobs.size();i++){
			    	 Mythread mythread = new Mythread("Thread for project " + jobs.get(i).getProject_id(),jobs.get(i));
			    	 mythread.start();
			     }
			     
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
