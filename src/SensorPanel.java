import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class SensorPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//SensorPanel sp=new SensorPanel();
		//sp.setSensorLongTime();
		
		SensorPanel spn=new SensorPanel();		
		JFrame frame = new JFrame();		
		//spn.startSensorLongTimer();
		 
		frame.getContentPane().add(spn);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

	/**
	 * This is the default constructor
	 */
	public SensorPanel() {
		super();
		initialize();
	}

	
	//private GraphicsButton gbCam =null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private tempDisplay tmpDisp=null;
	private WaterDisplay waterDisp=null;
	private IllumDisplay illumDisplay=null;
	private Timer shortTimer =new Timer(true);;//new Timer();// timer for short period
	private Timer longTimer  =new Timer(true);//new Timer();// timer for long period
	
	public void startSensorShortTimer()
	{	
		System.out.println("PlanterSettingClass: Start Short Sensor Timer");

		if( shortTimer != null){
			shortTimer.cancel();
			shortTimer.purge();
		}
		shortTimer=new Timer(true);
        shortTimer.schedule(new TaskSensorShort(), 0, 15000); // タスクの実行間隔は20秒
        //else 			  shortTimer.schedule(new TaskSensorShort(), 0, 5000); // タスクの実行間隔は5秒
	}
	
	public void stopSensorShortTimer()
	{
		System.out.println("PlanterSettingClass: Stop Short Sensor Timer");

		if( shortTimer != null){
			shortTimer.cancel();
			shortTimer.purge();
			//shortTimer = null;
		}
	}

	public void purgeSensorSHortTimer()
	{
		if( shortTimer != null){
			shortTimer.purge();
			//shortTimer=null;
		}
	}
	
	private long periodicTime=360000;// 6hour*60min*1000 msec
	public void setPeriodicTime(long t)
	{
		periodicTime=t;
	}
	
	public long getPeriodicTime()
	{
		return periodicTime;
	}
	
	public void setSensorLongTime()
	{
		// Macではうまくいかない。WinではOK.
		if(IsMacorWin.isMacOrWin()==false){
		String sr=Files.getRecordSensor();
		sr=sr.replace(System.getProperty("line.separator"), "");
		String[] sr2=sr.split(" ");
		if(sr2.length>1){
			String sr3=sr2[1].replace("\n", "");//.substring(0,sr2[1].length()-1);
System.out.println("SensorPanel: periodicTime sr3=|"+sr3+"|");			
			periodicTime=Long.valueOf(sr3);
System.out.println("SensorPanel: periodicTime = "+periodicTime);			
			}
		}
	}
	
	public void startSensorLongTimer()
	{
		System.out.println("PlanterSettingClass: Start Long Sensor Timer");

		if( longTimer != null){
			longTimer.cancel();
			longTimer.purge();
		}
		longTimer=new Timer(true);
		System.out.println("periodicTime="+periodicTime);
////		longTimer.schedule(new TaskSensorLong(), 600/* delay after 1min */, periodicTime); // タスクの実行間隔は6時間(periodicTime)に１度
		longTimer.schedule(new TaskSensorLong(), 60/* delay after 1min */, periodicTime*1000); // タスクの実行間隔は6時間(periodicTime)に１度
////       else 			  longTimer.schedule(new TaskSensorLong(), 0, 5000); // タスクの実行間隔は5秒
	}
	
	public void stopSensorLongTimer()
	{
		System.out.println("PlanterSettingClass: Stop Long Sensor Timer");

		if( longTimer != null){
			longTimer.cancel();
			longTimer.purge();
		//	longTimer = new Timer();
		}
	}

	public void purgeSensorLongTimer()
	{
		if( longTimer != null){
			longTimer.purge();
		//	longTimer=new Timer();
		}
	}
	
	private void initialize() {
		JPanel jp=new JPanel();
		jp.setBackground(Color.WHITE);
		// default size
		jp.setPreferredSize(new Dimension(640,320));
		
		// panel
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		
		// Temperature gauge
		tmpDisp=new tempDisplay();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 3;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(tmpDisp, constraints1);
		jp.add(tmpDisp);
		
		double temp=20.0;
		if(ITPlanterClass.getState() == false){
		ITPlanterClass.ITPlanterClassBegin();
		}
			temp=ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorTemprarure();
			tmpDisp.setTemp(temp);// for default temp display

		// Water gauge
		waterDisp=new WaterDisplay();
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.gridwidth= 3;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(waterDisp, constraints2);
		jp.add(waterDisp);
		
		double water=50.0;
			water=ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorWaterlevel();
			waterDisp.setWater(water);
			
		// Illum gauge
		illumDisplay=new IllumDisplay();
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 0;
		constraints3.gridy = 2;
		constraints3.gridwidth= 3;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumDisplay, constraints3);
		jp.add(illumDisplay);
		double illum=1000.0;
			illum=ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorIllumination();
			illumDisplay.setIllum(illum);
		
		// LED button
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.gridx = 0;
		constraints4.gridy = 3;
		constraints4.gridwidth= 1;
		constraints4.gridheight = 1;
		constraints4.insets = new Insets(0, 0, 0, 0);
		JButton gbLED = new JButton();
		String s = Path.getPath()+"/images/btn_led_on.jpg";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\btn_led_on.jpg";
//		ImageIcon iconLED=new ImageIcon("/Users/sakaguti/Documents/workspace2/GraphicsButton/src/image/btn_led_on.jpg");
		ImageIcon iconLED=new ImageIcon(s);
		gbLED.setPreferredSize(new Dimension(iconLED.getIconWidth(),iconLED.getIconHeight()));
		gbLED.setIcon(iconLED);
		gbLED.setName("LED Button");
		gbLED.addActionListener(this);
		gridbag.setConstraints(gbLED, constraints4);
		jp.add(gbLED);
		
		// pump button
		GridBagConstraints constraints5 = new GridBagConstraints();
		constraints5.gridx = 1;
		constraints5.gridy = 3;
		constraints5.gridwidth= 1;
		constraints5.gridheight = 1;
		constraints5.insets = new Insets(0, 0, 0, 0);
		JButton gbPump = new JButton();
		String t = Path.getPath()+"/images/btn_pump_on.jpg";
		if(IsMacorWin.isMacOrWin()==false) t = Path.getPath()+"images\\btn_pump_on.jpg";
//		ImageIcon iconPump=new ImageIcon("/Users/sakaguti/Documents/workspace2/GraphicsButton/src/image/btn_pump_on.jpg");
		ImageIcon iconPump=new ImageIcon(t);
		gbPump.setPreferredSize(new Dimension(iconPump.getIconWidth(),iconPump.getIconHeight()));
		gbPump.setIcon(iconPump);
		gbPump.setName("Pump Button");
		gbPump.addActionListener(this);
		gridbag.setConstraints(gbPump, constraints5);
		jp.add(gbPump);
		
		// Camera button
		GridBagConstraints constraints6 = new GridBagConstraints();
		constraints6.gridx = 2;
		constraints6.gridy = 3;
		constraints6.gridwidth= 1;
		constraints6.gridheight = 1;
		constraints6.insets = new Insets(0, 0, 0, 0);
		JButton gbCam = new JButton();
		String u = Path.getPath()+"/images/btn_camera_on.jpg";
		if(IsMacorWin.isMacOrWin()==false) u = Path.getPath()+"images\\btn_camera_on.jpg";
//		ImageIcon icon=new ImageIcon("/Users/sakaguti/Documents/workspace2/GraphicsButton/src/btn_camera_on.jpg");
		ImageIcon camIcon=new ImageIcon(u);
		gbCam.setPreferredSize(new Dimension(camIcon.getIconWidth(),camIcon.getIconHeight()));
		gbCam.setIcon(camIcon);
		gbCam.setName("Camera Button");
		gbCam.addActionListener(this);
		gridbag.setConstraints(gbCam, constraints6);
		jp.add(gbCam);
		
		//
		Container cont = this;
		cont.setLayout(new BorderLayout());
		cont.add( jp, BorderLayout.CENTER );
		
		this.add(jp);

		//
		// 追加
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlanterSetting.ObserverA();
		setObserver(defaultO);
		
       // System.out.println("---Sensor Update schedule()---");
		// 実プランターがない場合、タイマーはセットしない。
		
		// プランターがないとエラーになるので
		if(ITPlanterClass.getSystemPlanterSize()>0){
			startSensorShortTimer();
//        timer1 = new Timer();
//        timer1.schedule(new TaskSensor(), 0, 10000); // タスクの実行間隔は10秒
		} else {
	    		tmpDisp.setTemp(0);// for default temp display
	    		waterDisp.setWater(0);
	    		illumDisplay.setIllum(0);
	    		revalidate();
		}
        
        
     //   Timer timer2 = new Timer();
     //   timer2.schedule(new TaskAutoMode(), 0, 600000); // タスクの実行間隔は60秒
	}


//必要なメッソドを追加
// Observerを追加する
void addObserver(Observer o)
{
	//observableMan.deleteObserver(defaultO);// 前に設定されたObserverを削除する。
	//observableMan.deleteObservers();
	observableMan.addObserver(o);
}

// Observerを設定する
// 以前に追加されたObserverは全て破棄される
void setObserver(Observer o)
{
	observableMan.deleteObservers();
	observableMan.addObserver(o);
}

// Observerを削除
void deleteObserver(Observer o)
{
	observableMan.deleteObserver(o);
}

//
private ObservableMan observableMan=null;  //  @jve:decl-index=0:
private String message="";  //  @jve:decl-index=0:
private Observer defaultO=null;

@Override
public void actionPerformed(ActionEvent arg0) {
	JButton cb=(JButton)arg0.getSource();
	message = "私はSensorPanel classです。"+cb.getName()+" ボタンが押されました。";

System.out.println("SensorPanel  message="+message);
	int n=ITPlanterClass.getCurrentPlanterNo();
	String result="";

	//
	if( cb.getName().contains("LED Button")){
		result=sendCom.sendcom(" "+(n+1)+" -e o");// リモートモードにしないと動作しない
		result=sendCom.sendcom(" "+(n+1)+" -e L");// ON
		//
		setReturnAutoSchedule();
		//
	} else 
		//
	if( cb.getName().contains("Pump Button")){
			result=sendCom.sendcom(" "+(n+1)+" -e o");// リモートモードにしないと動作しない
			result=sendCom.sendcom(" "+(n+1)+" -e P");
			//
			setReturnAutoSchedule();
			//
		} else
	if( cb.getName().contains("Camera Button")){

//System.out.println("SensorPanel  Button"+ cb.getName());

				// カメラの撮影テストを行う
				//
//				int iCameraOfPlanter=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumberMax();	
				int iCameraOfPlanter=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNameList().size();
				
ITPlanterClass.getCurrentPlanterClass().getInformation().printCameras();

System.out.println("SensorPanel: PlanterName=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName());
System.out.println("SensorPanel: CameraNo=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber());
System.out.println("SensorPanel: CameraNoMax=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumberMax());
System.out.println("SensorPanel: CameraName=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName());
System.out.println("SensorPanel: EntryCammeraNo1=" +iCameraOfPlanter);// 0?
for(int i=0;i<iCameraOfPlanter;i++){
	System.out.println("SensorPanel:  EntryCammera ["+i+"]"+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(i));
}

				
				for( int iCamNo=0;iCamNo<iCameraOfPlanter;iCamNo++){
				String currentCamName=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(iCamNo); // リストに登録されたカメラの名前を得る
				if(currentCamName==null) continue;
System.out.println("SensorPanelClass: currentCamName="+currentCamName);
				if(currentCamName.contains("--removed--")== true){
					continue;// skip removed camera
				}

				String dateCal=Calendar.getInstance().getTime().toString();
				String[] date=dateCal.split(" ");
				String[] hm=date[3].split(":");
				String Date=date[1]+"_"+date[2]+"_"+hm[0]+"_"+hm[1];
				//Date = Date.replace(":", "_"); // remove ":"
				// ここで撮影してPicasaにアップす
				/*
				CameraCapture cc = new CameraCapture();
				String f=Files.getPhotoHolder();
				f=f.replace("PhotoHolder ", "");
				f=f+itpc.getInformation().getCameraName(currentCamNo)+"_"+Date+"_.bmp";
				f=f.replace(":","_");
//				f=f+itpc.getInformation().getPlanterName()+"_Cam"+String.valueOf(currentCamNo)+"_DATE_.jpg";
				System.out.println("file:"+f);
				*/				
				//CameraCapture cc0 = new CameraCapture();
				
				String f0=Files.getPhotoHolder();
//				System.out.println(f0);
				f0=f0.replace("PhotoHolder ", "");
				
				File fd=new File(f0);
			    if (fd.exists()){
//			    	System.out.println("1"+fd.isDirectory());
//			    	System.out.println("2"+fd.canWrite());
				      if ((fd.isDirectory() && fd.canWrite())==false){
				    	  // Message
				    	  String message=f0+" can not write";
				    	  JOptionPane.showMessageDialog(null,message);
				        return;
				      }
				 }
			    
//				f0=f0+"test.bmp";
				f0=f0+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName()+currentCamName+"_"+Date+"_.bmp";
//System.out.print("SensorPanelClass: "+f0);		    
				//
				/*
				File fi=new File(f0);
				if(Files.checkBeforeWritefile(fi)==false){
			    	  // Message
			    	  String message=fi+" can not write";
			    	  JOptionPane.showMessageDialog(null,message);
						return;
				}
				 */
				String captureReturn="";
				int retryNo=0;
				while(true){
					System.out.println("getCheckSaveHolder="+Files.getCheckSaveHolder());
					if(Files.getCheckSaveHolder().contains("true")==true){
						//
						//						
					captureReturn=CameraCapture.capture_thread("-c "+iCamNo+" -f "+f0);
					} else {
					captureReturn=CameraCapture.capture_thread("-c "+iCamNo+" -f ./tmp.jpg");
					}
					if(captureReturn.contains("failed")==false) break;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	//System.out.println("ReTry to Capture:"+retryNo);
					retryNo++;
					if(retryNo>10) break;// Too Many retry
				}

				
// ここで撮影してPicasaにアップする。
// 撮影するだけにしたい。
//				AutoCapture ac=new AutoCapture(itpc.getInformation().getPlanterName()+"_Cam"+String.valueOf(currentCamNo)+"_DATE_.jpg",currentCamNo);
//				ac.takePictureProcessing();
				}
				
			//	AlbumSetting.openURL() ;
			        	
				//
				
				// repaint
				repaint();
				this.repaint(getX(), getX(), getWidth(), getHeight());
				revalidate();
			}
	//System.out.println("result:"+result);
	itp_Logger.logger.info(result);
	// Do processing using temperature value
	observableMan.setMessage(message);
	// 観察者全員に通知
	observableMan.notifyObservers();
}


private void setReturnAutoSchedule() {
					TimerTask timerTask=new  TaskAutoMode();
					Timer timer = new Timer("復帰タイマー");
					System.out.println("復帰タイマー start：" + new Date());
					timer.schedule(timerTask, TimeUnit.SECONDS.toMillis(100)); //100秒後に実行
//					timer.cancel();  // cancelしなくてもガーベージコレクタで処理されるはず
}


//各クラス固有

/**
* 観察者を観察する人A。
*
*/
class ObserverA implements Observer {
@Override
public void update(Observable arg0, Object arg1) {
	String str = (String) arg1;
	System.out.println("私はSensorPanel classです。観察対象の通知を検知したよ。" + str);
}
}



// record.txtに記録する
class TaskSensorLong extends TimerTask {
    @Override
	public void run() {
    	
    	for(int i=0;i<ITPlanterClass.getPlanterList().size();i++){
    	String s=ITPlanterClass.getPlanterList().get(i).getSensor().getSensors();
    	System.out.println("SensorPanelClass: TaskSensorLong "+s+" "+i);
  	
    	String[] t=s.split(System.getProperty("line.separator"));
    		double temp=20.0;
    		temp=Double.valueOf(t[0]);
    		tmpDisp.setTemp(temp);// for default temp display
    		
    		double water=50.0;
    		water=Double.valueOf(t[1]);
    		waterDisp.setWater(water);
    		
    		double illum=1000.0;
    		illum=Double.valueOf(t[2]);
    		illumDisplay.setIllum(illum);

    		TimeDate td=new TimeDate();
    		String pn="Report from "+ITPlanterClass.getPlanterList().get(i).getInformation().getPlanterName()+",";
    		String recordData=pn+" SensorRecord: "+td.getYear()+":"+td.getMonth()+":"+td.getDay()+" "+td.getHour()+":"+td.getMin()+" Temp "+temp+" Water "+water+" Illum "+illum;
 System.out.println(recordData);
 			recordData += System.getProperty("line.separator");
    		Files.addFile(Files.getRecordfile(),recordData);    	
    		}
    }
}


public static Calendar before = Calendar.getInstance();
public static Integer periodMillis=1000*60*10;// 10min

class TaskSensorShort extends TimerTask {
    @Override
	public void run() {

		String s=ITPlanterClass.getCurrentPlanterClass().getSensor().getSensors();
    	System.out.println("SensorPanelClass: TaskSensorShort "+s);
    	
    	String[] t=s.split(System.getProperty("line.separator"));
    		double temp=20.0;
    		temp=Double.valueOf(t[0]);
    		tmpDisp.setTemp(temp);// for default temp display
  		
    		double water=50.0;
    		water=Double.valueOf(t[1]);
    		waterDisp.setWater(water);
    		
    		double illum=1000.0;
    		illum=Double.valueOf(t[2]);
    		illumDisplay.setIllum(illum);
    		
    		//
    		// 所定の間隔がすぎたら、センサー情報をファイルに書き込む
    		// 全てのプランターのセンサー情報を書き込む？
    		//
    		Calendar now = Calendar.getInstance();
    			String[] p=Files.getRecordSensor().split(" ");
    			if(p!=null && p.length>1){
    			String pd=p[1].replace("\n", "");
    			pd=p[1].replace(System.getProperty("line.separator"), "");
    			if(pd!=null){
    				periodMillis=Integer.valueOf(pd);
    				}
    			}
    			//
    			// 警告水準を読込む
    			Sensor sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
    			if(sensor!=null){
    				// set default value of warning lebel
    				sensor.setTempratureWarningLevel(Double.valueOf(Files.getTempratueWarningLevel()));
    				sensor.setWaterLevelWarningLevel(Double.valueOf(Files.getWaterWarningLevel()));
    				sensor.setIlluminationWarningLevel(Double.valueOf(Files.getIlluminationWarningLevel()));
    				}
    			//
    			//
    			// 警告メールを出す
    			for(int i=0;i<ITPlanterClass.getPlanterList().size();i++){
    				String s1=ITPlanterClass.getPlanterList().get(i).getSensor().getSensors();
    				//  最後の警告メールから日付が変わってから送信する
    					if(ITPlanterClass.getPlanterList().get(i).getInformation().getLastSendWarnMailDate().get(Calendar.DATE) == Calendar.getInstance().get(Calendar.DATE)) continue;
    					
    			    	System.out.println("SensorPanelClass: TaskSensorLong "+s1+" "+i);

    			    	Settings.readMailSetup();// read from file

    			    	TimeDate td=new TimeDate();
    			    		String pn="Report from "+ITPlanterClass.getPlanterList().get(i).getInformation().getPlanterName()+System.getProperty("line.separator");
    			    		String recordData=pn+"SensorRecordTime "+td.getYear()+":"+td.getMonth()+":"+td.getDay()+" "+td.getHour()+":"+td.getMin()+System.getProperty("line.separator");//+" Temp "+temp+" Water "+water+" Illum "+illum;
    			 			
    			    		// 設定された項目を読み出す
    						sendMail.setMessageText(recordData);
    			    		// お知らせメールを出す。
    						String subjectString="Report From itplanter";
    						//Title
    						sendMail.setTitleText(subjectString);
    						//
    						sendMail.addMessageText(System.getProperty("line.separator"));
    						if(Sensor.getTemperatureReport() && (Sensor.getSensorTemperature() > Sensor.getTemperatureWarningLevel())){
    							String msg="Temp Warnig over "+String.valueOf(Sensor.getTemperatureWarningLevel());
    							sendMail.addMessageText(msg);
    							sendMail.setTitleText(msg);
    						} 
    						if(Sensor.getIlluminationReport() && (Sensor.illumination < Sensor.getIlluminationWarningLevel())){// and check Light statue
    							String msg="Illum Warnig under "+String.valueOf(Sensor.getIlluminationWarningLevel());
    							sendMail.addMessageText(msg);
    							sendMail.setTitleText(msg);
    						} 
    						if(Sensor.getWaterlevelReport() && (Sensor.waterlevel < Sensor.getWaterWarningLevel())){// and check pump status
    							String msg="Water Warning under　"+String.valueOf(Sensor.getWaterWarningLevel())+" %";
    							sendMail.setTitleText(msg);
    							sendMail.addMessageText(msg);// Sensor.getWaterLevel();
    						} 
    						
    						sendMail.addMessageText("Temp "+String.valueOf( Sensor.getSensorTemperature()));
    						sendMail.addMessageText("Illum "+String.valueOf(Sensor.illumination));
    						sendMail.addMessageText("Water "+String.valueOf(Sensor.waterlevel));// Sensor.getWaterLevel();
    							//
    						sendMail.mailSend();// おしらせメール /// <<< 130704
    						ITPlanterClass.getPlanterList().get(i).getInformation().setLastSendWarnMailDate();// 警告メール送信日時を記録する
    						
    						System.out.println("SensorPanelClass: recordData2="+recordData);
    						}
    			
    		if(now.getTimeInMillis()-before.getTimeInMillis()<periodMillis) return;
        	for(int i=0;i<ITPlanterClass.getPlanterList().size();i++){
            	String s1=ITPlanterClass.getPlanterList().get(i).getSensor().getSensors();
            	System.out.println("SensorPanelClass: TaskSensorLong "+s1+" "+i);
          	
            	String[] t1=s1.split(System.getProperty("line.separator"));
            		double temp1=20.0;
            		temp1=Double.valueOf(t1[0]);
            		
            		double water1=50.0;
            		water1=Double.valueOf(t1[1]);
            		
            		double illum1=1000.0;
            		illum1=Double.valueOf(t1[2]);

            		TimeDate td=new TimeDate();
            		String pn="Report from "+ITPlanterClass.getPlanterList().get(i).getInformation().getPlanterName()+",";
            		String recordData=pn+" SensorRecord: "+td.getYear()+":"+td.getMonth()+":"+td.getDay()+" "+td.getHour()+":"+td.getMin()+" Temp "+temp1+" Water "+water1+" Illum "+illum1;
        // System.out.println(recordData);
         			recordData += System.getProperty("line.separator");
            		Files.addFile(Files.getRecordfile(),recordData); 	
            	}
        		before=now;// copy current time to before
    		
    		//
    		//
//    		System.out.println("Sensor update "+temp+" "+water+" "+illum);
    		//ITPlanterClass.getCurrentPlanterClass().getSensor().getReset();
    		
    		revalidate();
    }
}

class TaskAutoMode extends TimerTask {
    @Override
	public void run() {
    	int n=ITPlanterClass.getCurrentPlanterNo();
    //	System.out.println("状態1　"+sendCom.sendcom(" "+(n+1)+" -e t"));
    	sendCom.sendcom((n+1)+" -e p");// ここでAUTOにならない。何故？
    //	System.out.println("復帰　Auto Mode");
    //	System.out.println("状態2　"+sendCom.sendcom(" "+(n+1)+" -e t"));
    }
}

}
