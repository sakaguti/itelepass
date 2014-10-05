import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	
	public static String grobalString="";

	private Timer timer  =null;// timer for long period
	public static void main(String[] args) {
		TimerTest tt=new TimerTest();
		tt.startTimer();
		//
		//
		for(int i=0;i<100;i++){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tt.setNum(String.valueOf(i));
		}
	}
	public void setNum(String n)
	{
		grobalString=n;
	}
	
	public void startTimer()
	{
		if( timer != null){
			timer.cancel();
			timer.purge();
		}
		timer = new Timer();
        timer.schedule(new task(), 0, 500); // ƒ^ƒXƒN‚ÌŽÀsŠÔŠu‚Í5•b
	}
	
	class task extends TimerTask {
		@Override
		public void run() {	
			String s=grobalString;
	    	System.out.println("Sensor:"+s);
	    	}
	    }
}
