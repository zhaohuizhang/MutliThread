
public class WaitNotify {
	public static void main(String[] args){
//		singleWaitSingleNofityCall();
//		multipleWaitSingleNofity();
		multipleWaitNofityAll();
		
//		waitNotify();
	}
	
	private static void waitNotify(){
		SomeClass someObj = new SomeClass();
		synchronized(someObj){
			try {
				someObj.wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	private static void singleWaitSingleNofityCall(){
		Door door = new Door();
		
		StrictDoorKeeper keeper = new StrictDoorKeeper(door);
		keeper.setName("keeper");
		Guest guest = new Guest(door);
		guest.setName("guest");
		
		guest.start();
		keeper.start();
	}
	
	private static void multipleWaitSingleNofity(){
		Door door = new Door();
		
		StrictDoorKeeper keeper = new StrictDoorKeeper(door);
		keeper.setName("keeper");
		Guest guest1 = new Guest(door);
		guest1.setName("guest1");
		Guest guest2 = new Guest(door);
		guest2.setName("guest2");		
		
		guest1.start();
		guest2.start();
		keeper.start();
	}	
	
	private static void multipleWaitNofityAll(){
		Door door = new Door();
		
		NiceDoorKeeper keeper = new NiceDoorKeeper(door);
		keeper.setName("keeper");
		Guest guest1 = new Guest(door);
		guest1.setName("guest1");
		Guest guest2 = new Guest(door);
		guest2.setName("guest2");		
		
		guest1.start();
		guest2.start();
		keeper.start();
	}	
}

class Door{
	public synchronized void openDoorForAtMostOneGuest(){
		System.out.println(Thread.currentThread().getName()+": open door for at most one guest");
		notify();			
	}
	
	public synchronized void openDoorForAllGuest(){
		System.out.println(Thread.currentThread().getName()+": open door for all guest");
		notifyAll();			
	}	
	
	public synchronized void tryEnterDoor(){
		System.out.println(Thread.currentThread().getName()+": nock door and wait for door open");
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+": enter door");		
	}
}

class StrictDoorKeeper extends Thread{
	public StrictDoorKeeper(Door door){
		this.door = door;
	}
	
	@Override
	public void run(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.door.openDoorForAtMostOneGuest();
	}
	
	private Door door;
}

class NiceDoorKeeper extends Thread{
	public NiceDoorKeeper(Door door){
		this.door = door;
	}
	
	@Override
	public void run(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.door.openDoorForAllGuest();
	}
	
	private Door door;
}

class Guest extends Thread{
	public Guest(Door door){
		this.door = door;
	}
	
	@Override
	public void run(){
		this.door.tryEnterDoor();
	}
	
	private Door door;
}

class SomeClass{
	
}
