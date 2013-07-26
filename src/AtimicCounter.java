import java.util.concurrent.atomic.AtomicInteger;


public class AtimicCounter {
	public static void main(String[] args){
//		threadNotSafeValueInNotSafeWay();
//		threadNotSafeValueInSafeWay();
		threadSafeValue();
	}
	
	private static void threadNotSafeValueInNotSafeWay(){
		Count count = new Count();
		
		NotSafeValueNotSafeWayCounter[] counters = new NotSafeValueNotSafeWayCounter[10];
		
		for(int i=0; i<10; ++i){
			counters[i] = new NotSafeValueNotSafeWayCounter(count); 
			 
		}
		for(int i=0; i<10; ++i){
//			System.out.println(i+":"+counters[i].getState().toString());
			counters[i].start(); 
//			System.out.println(i+":"+counters[i].getState().toString());
		}		
		
		for(int i=0; i<10; ++i){
			try {
				counters[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		
		for(int i=0; i<10; ++i){
//			System.out.println(i+":"+counters[i].getState().toString());
		}
		
		System.out.println(count.threadNotSafeValue);
	}
	
	private static void threadNotSafeValueInSafeWay(){
		Count count = new Count();
		
		NotSafeValueWithSafeWayCounter[] counters = new NotSafeValueWithSafeWayCounter[10];
		
		for(int i=0; i<10; ++i){
			counters[i] = new NotSafeValueWithSafeWayCounter(count); 
			counters[i].start();  
		}
		
		for(int i=0; i<10; ++i){
			try {
				counters[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<10; ++i){
			System.out.println(i+":"+counters[i].getState().toString());
		}
		
		System.out.println(count.threadNotSafeValue);
	}
	
	private static void threadSafeValue(){
		Count count = new Count();
		
		SafeValueCounter[] counters = new SafeValueCounter[10];
		
		for(int i=0; i<10; ++i){
			counters[i] = new SafeValueCounter(count); 
			counters[i].start();  
		}
		
		for(int i=0; i<10; ++i){
			try {
				counters[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<10; ++i){
			System.out.println(i+":"+counters[i].getState().toString());
		}
		
		System.out.println(count.threadSafeValue.get());
	}	
}

class Count{
	public int threadNotSafeValue = 0;
	public AtomicInteger threadSafeValue = new AtomicInteger(0);
	
	public void increaseThreadNotSafeValueInNotSafeWay(){
		++this.threadNotSafeValue;
	}
	
	public synchronized void increaseThreadNotSafeValueInSafeWay(){
		++this.threadNotSafeValue;
	}
	
	public void increaseThreadSafeValue(){
		this.threadSafeValue.incrementAndGet();
	}
}

class NotSafeValueNotSafeWayCounter extends Thread{
	public NotSafeValueNotSafeWayCounter(Count count){
		this.count = count;
	}
	
	private Count count;
	
	public void run(){
//		System.out.println(this.getState().toString());
		for(int i=0; i<20000; ++i){
			count.increaseThreadNotSafeValueInNotSafeWay();
		}
	}
}

class NotSafeValueWithSafeWayCounter extends Thread{
	public NotSafeValueWithSafeWayCounter(Count count){
		this.count = count;
	}
	
	private Count count;
	
	public void run(){
		for(int i=0; i<20000; ++i){
			count.increaseThreadNotSafeValueInSafeWay();
		}
	}
}

class SafeValueCounter extends Thread{
	public SafeValueCounter(Count count){
		this.count = count;
	}
	
	private Count count;
	
	public void run(){
		for(int i=0; i<20000; ++i){
			count.increaseThreadSafeValue();
		}
	}
}
