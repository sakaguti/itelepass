
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class sendMail extends TimerTask {
	
	private static String toString="sakaguti3@me.com";		// sakaguti@me.com
	private static String fromString="sakaguti3@gmail.com";	// sakaguti@itplants.com
	private static String serverString="smtp.gmail.com"; //	smtp.me.com
	private static String passString = "|rvl|xnl6";
	private static String titleString="�A�C�e�B�v�����^�[���|�[�g";	// �A�C�e�B�v�����^�[���|�[�g
	private static String messageString="I'm ITPLANTER...";
//	private static Calendar sendCal=Calendar.getInstance();
	
	
	public static void addToText(String s)
	{
		toString+= (s+System.getProperty("line.separator"));
	}

	public static void setToAdress(String s)
	{
		toString=s;
	}
	
	public static void setFromAdress(String s)
	{
		fromString=s;
	}
	
	public static void setServerAdress(String s)
	{
		serverString=s;
	}
	
	public static void setTitleText(String s)
	{
		titleString=s;
	}
	
	public static void setMessageText(String s)
	{
		messageString=s;
	}
	
	public static void addMessageText(String s)
	{
		messageString += (s + System.getProperty("line.separator"));
	}
	
	public static void setPasswdText(String s)
	{
		passString=s;
	}
	
	
	public static void send()
	{
		System.out.println("sendMail : send()");	
		new SimpleSendMail(toString,fromString,serverString,Settings.Fukugouka(passString),titleString,messageString);
	}
	
	public static void setTemperatureMessage(String t)
	{
		String s="�ُ퉷�x�����o���܂����B���݁A"+t+"�x�ɂȂ��Ă��܂��B";
		addToText(s);
	}

	public static void setWaterLevelMessage(String t)
	{
		String s="�ُ퐅�ʂ����o���܂����B���݁A"+t+"���ɂȂ��Ă��܂��B";
		addToText(s);
	}
	
	public static void setIlluminationLevellMessage(String t)
	{
		String s="�ُ�Ɠx�����o���܂����B���݁A"+t+"lux�ɂȂ��Ă��܂��B";
		addToText(s);
	}
	
	public static void mailSend()
	{
		new SimpleSendMail(toString,fromString,serverString,Settings.Fukugouka(passString),titleString,messageString);
	}
	
	// h:m:0 �Ƀ��[������^�C�}�[�^�X�N
	public static void timerSet(int h, int m)
	{
		System.out.println("timerSet "+h+":"+m);
		
		Calendar cal=Calendar.getInstance();
		
		//
		System.out.println("---timerSchedule()---");
		Timer timer = new Timer();
		TimerTask tt=new sendMail();
		Date date = new Date();
		date = cal.getTime();
		long delay=0;// msec
		long period=24*60*60*1000;// 24h
		date.setTime(date.getTime()+delay);
		timer.schedule(tt, date, period);
	}
		
	public static void main(String[] args) 
	{
		System.out.println("START MAIL");
		
		mailSend();
		//new sendMail();
		
		/*
		Calendar cal=Calendar.getInstance();
		String s=cal.getTime().toString();
		String[] t=s.split(" ");
		String[] hm=t[3].split(":");
		int h=Integer.valueOf(hm[0]);
		int m=Integer.valueOf(hm[1])+1;
		
		
		System.out.println("TimerSet "+h+":"+m);
		// send Mail test
		sendMail.timerSet(h,m);
		// hour, min
		 */
	}
//
/*
  		m=m+ sMTPServerString+System.getProperty("line.separator");
		m=m+ sMTPPasswdString+System.getProperty("line.separator");
		m=m+  toAddressString+System.getProperty("line.separator");
		m=m+fromAddressString+System.getProperty("line.separator");
		m=m+titleString+System.getProperty("line.separator");
		m=m+messageAreaString+System.getProperty("line.separator");

 */
	public static void readMailSetup()
	{
		String s=Files.readFile(Files.getSetupMailFileName());
		String[] p=s.split(System.getProperty("line.separator"));
		if(p.length==6){
			serverString=p[0];
			passString=p[1];
			toString=p[2];
			fromString=p[3];
			titleString=p[4];
			messageString=p[5];
		} else {
//			System.out.println(p.length);
//			System.out.println(s);
		}
	}
	


	@Override
	public void run() {
		send();
	}

	public static String getSMTPserver() {
		return serverString;
	}

	public static String getPasswd() {
		return passString;
	}

	public static String getFrom() {
		return fromString;
	}

	public static String getTo() {
		return toString;
	}

	public static String getMessage() {
		return messageString;
	}

	public void setServerPasswd(String passString2) {
		passString=passString2;
	}
	
	
}
