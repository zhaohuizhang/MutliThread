
public class Synchronized {
	public static void main(String[] args){
//		testSameFunctionCall();
//		testSameFunctionCall2();
//		testDifferentFunctionCall();
//		testStaticFunctionCall1();
//		testStaticFunctionCall2();
		testObjectLock();
	}

	private static void testSameFunctionCall() {
		SynchronizedObject syncObj = new SynchronizedObject();
		
		Thread aCaller1 = new Thread(new AFunctionCallerThread(syncObj));
		aCaller1.setName("aCaller1");
		Thread aCaller2 = new Thread(new AFunctionCallerThread(syncObj));
		aCaller2.setName("aCaller2");
		
		aCaller1.start();
		aCaller2.start();
	}
	
	private static void testSameFunctionCall2() {
		SynchronizedObject syncObj1 = new SynchronizedObject();
		SynchronizedObject syncObj2 = new SynchronizedObject();	
		
		Thread aCaller1 = new Thread(new AFunctionCallerThread(syncObj1));
		aCaller1.setName("aCaller1");
		Thread aCaller2 = new Thread(new AFunctionCallerThread(syncObj2));
		aCaller2.setName("aCaller2");
		
		aCaller1.start();
		aCaller2.start();
	}	
	
	private static void testDifferentFunctionCall(){
		SynchronizedObject syncObj = new SynchronizedObject();
		
		Thread aCaller = new Thread(new AFunctionCallerThread(syncObj));
		aCaller.setName("aCaller");
		Thread bCaller = new Thread(new BFunctionCallerThread(syncObj));
		bCaller.setName("bCaller");
		
		aCaller.start();
		bCaller.start();		
	}
	
	private static void testStaticFunctionCall1(){
		SynchronizedObject syncObj = new SynchronizedObject();
		
		Thread aCaller = new Thread(new AFunctionCallerThread(syncObj));
		aCaller.setName("aCaller");
		Thread sCaller = new Thread(new SFunctionCallerThread());
		sCaller.setName("sCaller");
		
		aCaller.start();
		sCaller.start();		
	}
	
	private static void testStaticFunctionCall2(){
		SynchronizedObject syncObj = new SynchronizedObject();
		
		Thread sCaller1 = new Thread(new SFunctionCallerThread());
		sCaller1.setName("sCaller1");
		Thread sCaller2 = new Thread(new SFunctionCallerThread());
		sCaller2.setName("sCaller2");
		
		sCaller1.start();
		sCaller2.start();		
	}	
	
	private static void testObjectLock() {
		SynchronizedObject syncObj = new SynchronizedObject();
		
		Thread cCaller1 = new Thread(new CFunctionCallerThread(syncObj));
		cCaller1.setName("cCaller1");
		Thread cCaller2 = new Thread(new AFunctionCallerThread(syncObj));
		cCaller2.setName("cCaller2");
		
		cCaller1.start();
		cCaller2.start();
	}	
}


class AFunctionCallerThread implements Runnable{
	public AFunctionCallerThread(SynchronizedObject syncObj){
		this.syncObj = syncObj;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(this.syncObj != null){
			this.syncObj.functionA();
		}
	}
	
	private SynchronizedObject syncObj;
}

class BFunctionCallerThread implements Runnable{
	public BFunctionCallerThread(SynchronizedObject syncObj){
		this.syncObj = syncObj;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(this.syncObj != null){
			this.syncObj.functionB();
		}
	}
	
	private SynchronizedObject syncObj;
}

class CFunctionCallerThread implements Runnable{
	public CFunctionCallerThread(SynchronizedObject syncObj){
		this.syncObj = syncObj;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(this.syncObj != null){
			this.syncObj.functionC();
		}
	}
	
	private SynchronizedObject syncObj;
}

class SFunctionCallerThread implements Runnable{
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SynchronizedObject.functionS();
	}
}

class SynchronizedObject {
//	public void functionA(){
	public synchronized void functionA(){
		System.out.println(Thread.currentThread().getName()+": enter function A");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+": leave function A");
	}

//	public void functionB(){	
	public synchronized void functionB(){
		System.out.println(Thread.currentThread().getName()+": enter function B");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+": leave function B");		
	}
	
	public static synchronized void functionS(){
		System.out.println(Thread.currentThread().getName()+": enter function S");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+": leave function S");			
	}
	
	public void functionC(){
		synchronized(lock){
			System.out.println(Thread.currentThread().getName()+": enter locked section");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			System.out.println(Thread.currentThread().getName()+": leave locked section");
		}		
	}
	
	private Object lock = new Object();
	
}
