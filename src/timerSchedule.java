import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


class Task1 extends TimerTask {
    @Override
	public void run() {
// capture & upload
          try {
                Thread.sleep(100); 
            } catch (InterruptedException ignore) {
        }
          System.out.println("TimerTask: run");
    }
}


public class timerSchedule {
	public timerSchedule()
	{	
		//
	}

	// Timerにスケジュールを登録していく。
	public static void timerScheduleSet(TimerTask tt, Calendar cal)
	{	

	//
//	System.out.println("---timerSchedule()---");
	Timer timer = new Timer();
//	Calendar calender = Calendar.getInstance();
	Date date = new Date();
	date = cal.getTime();
	long delay=0;// msec
	long period=24*60*60*1000;// 24h
//	long period=1000;// 1sec for TEST
	date.setTime(date.getTime()+delay);

	timer.schedule(tt, date, period);
	
		try {
		    Thread.sleep(500);
		} catch (InterruptedException ignore) {
		}
    
	}
	
	public static void main(String[] args) {
//		timerSchedule ts=new timerSchedule(new Task1(),Calendar.getInstance());
    	
       System.out.println("---schedule()---");
        Timer timer1 = new Timer();
        timer1.schedule(new Task1(), 0, 1000); // タスクの実行間隔は1000ミリ秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore) {
        }
        timer1.cancel();

        System.out.println("---scheduleAtFixedRate()---");
        Timer timer2 = new Timer();
        Calendar calender = Calendar.getInstance();
        Date date = new Date();
        date = calender.getTime();
        
        long delay=1000;// msec
        long period=24*60*60*1000;// 24h
        period=1000;
        date.setTime(date.getTime()+delay);
        
        timer2.schedule(new Task1(), date, period);
        
        try {
            Thread.sleep(1000+delay);
        } catch (InterruptedException ignore) {
        }
       timer2.cancel();
      System.out.println("---schedule test end---");

    }
}