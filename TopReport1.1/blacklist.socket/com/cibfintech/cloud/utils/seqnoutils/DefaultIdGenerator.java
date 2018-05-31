package com.cibfintech.cloud.utils.seqnoutils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 默认的ID生成器, 采用前缀+时间+原子数的形式实现 
 * 建议相同的配置采用同一个实例 
 * @author administrator
 *
 */
public class DefaultIdGenerator implements IdGenerator, Runnable {
	private String time;
	
	private AtomicInteger value; 
	
	//private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {  

      @Override  
       protected DateFormat initialValue() {  
    	  return new SimpleDateFormat("yyyyMMddHHmmss");  
       }  
	};

	private IdGeneratorConfig config; 
	
	private Thread thread;  
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); 
	
	public DefaultIdGenerator(){  
		 config = new DefaultIdGeneratorConfig();
		 //time = LocalDateTime.now().format(FORMATTER);
		 time = threadLocal.get().format(new Date());
		 value = new AtomicInteger(config.getInitial());  
		 thread = new Thread(this);  
		 thread.setDaemon(true);  
		 thread.start();  
	} 
	public DefaultIdGenerator(IdGeneratorConfig config){  
		this.config = config;  
		//time = LocalDateTime.now().format(FORMATTER);
		time = threadLocal.get().format(new Date());
		value = new AtomicInteger(config.getInitial());
		thread = new Thread(this); 
		thread.setDaemon(true); 
		thread.start(); 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){  
			try {  
					Thread.sleep(1000 * config.getRollingInterval());  
				} catch (InterruptedException e) {  
					e.printStackTrace();  
				} 
			 //String now = LocalDateTime.now().format(FORMATTER);  
			 String now = time = threadLocal.get().format(new Date());;  
			 if (!now.equals(time)){  
				 	lock.writeLock().lock();  
				 	time = now;  
				 	value.set(config.getInitial());  
				 	lock.writeLock().unlock();  
				 }

		}
	}

	@Override
	public String next() {
		lock.readLock().lock(); 
		StringBuffer sb = new StringBuffer(config.getPrefix()).append(config.getSplitString()).append(time).append(config.getSplitString()).append(value.getAndIncrement()); 
		lock.readLock().unlock();
		return sb.toString();
	}

}
