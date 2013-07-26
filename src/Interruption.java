
public class Interruption {
	public static void main(String[] args){


		InterruptingThread interruptingThread = new InterruptingThread(Thread.currentThread());
		interruptingThread.start();

        try {
        	System.out.println("Main: Go sleep now!");
            Thread.sleep(20000);
            System.out.println("Main: Come back!");
        } catch (InterruptedException e) {
            System.out.println("Main: I was interrupted!");
        }
	}
}

class InterruptingThread extends Thread{
	private Thread mainThread = null;
	public InterruptingThread(Thread mainThread){
		this.mainThread = mainThread;
	}
    @Override public void run() {
        // Let the main thread start to sleep
        try {
            Thread.sleep(2000);
            if(mainThread != null){
            	mainThread.interrupt();
            }
            
            Thread.sleep(2000);
            System.out.println("Sub: I'm still alive");
//            mainThread.;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }	
}
