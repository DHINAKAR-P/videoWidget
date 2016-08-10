package com.npb.gp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.npb.gp.domain.core.GpJob;
import com.npb.gp.gen.managers.GpDefaultGenerationManager;

public class Mythread implements Runnable {
	private Thread t;
	private String threadName;
	private ApplicationContext context;
	private GpDefaultGenerationManager default_manager;
	private GpJob the_job;
	Mythread(String name, GpJob the_job) {
		threadName = name;
		this.the_job = the_job;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		context = new
	    		 ClassPathXmlApplicationContext("classpath:spring_config.xml");
		default_manager = (GpDefaultGenerationManager)
								context.getBean("GpDefaultGenerationManager");
		try {
			default_manager.accept_control(the_job);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	public GpDefaultGenerationManager getDefault_manager() {
		return default_manager;
	}
	
}
