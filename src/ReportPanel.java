import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class ReportPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		ReportPanel spn=new ReportPanel();
		
		frame.getContentPane().add(spn);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public ReportPanel() {
		super();
		initialize();
	}

	
	public JButton acceptBtn=null;
	public GDCheck tempDisp=null;
	public GDCheck waterDisp=null;
	public GDCheck illumDisp=null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		String status=Files.getReports();
		
		Container c = this;
		JPanel jp=new JPanel();
		jp.setBackground(new Color(250,251,245));
		// default size
		jp.setPreferredSize(new Dimension(640,320));
		
		// panel
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		
		/////
		String s0 = Path.getPath()+"/images/program_h6_bg_temperature.gif";
		if(IsMacorWin.isMacOrWin()==false) s0 = Path.getPath()+"images\\program_h6_bg_temperature.gif";
		///
		
		tempDisp=new GDCheck(s0);
		//"/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_temperature.gif");
//		tmpDisp.setAction(this.acceptEnableAction());// Action when checked
		
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 3;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(tempDisp, constraints1);
		jp.add(tempDisp);
		tempDisp.setLabel("�x�����x");
		if(status!=null){
		if(status.contains("Temp")==true) tempDisp.setSelected(true);
		}
		String st=Files.getTempratueWarningLevel();
		
		if(st!=null){ 
		int idt=(Integer.valueOf(st)-0)/1;
		tempDisp.setSelectedIndex(idt);
		} else
		tempDisp.setSelectedIndex(0);
		
		// separator
		JSeparator vspr=new JSeparator(SwingConstants.VERTICAL);
		vspr.setPreferredSize(new Dimension(30, 100));
		jp.add(vspr);
		
		String s1 = Path.getPath()+"/images/program_h6_bg_water.gif";
		if(IsMacorWin.isMacOrWin()==false) s1 = Path.getPath()+"images\\program_h6_bg_water.gif";

		waterDisp=new GDCheck(s1);
		//"/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_water.gif");
		String[] wlabel=new String[21];
		int first=35;
		for(int i=first;i<=100;i+=5) wlabel[(i-first)/5]=String.valueOf(i);
		waterDisp.setSelectionItems(wlabel);
		waterDisp.setIconText("����");
		waterDisp.setName("����");
		waterDisp.setLabel("�x������");
		String s=Files.getWaterWarningLevel();
		
		if(s!=null){
		int id=(Integer.valueOf(s)-35)/5;
		waterDisp.setSelectedIndex(id);
		} else 
		waterDisp.setSelectedIndex(0);
		
		waterDisp.setLevelUnit("%");
		
		if(status!=null){
		if(status.contains("Water")==true) waterDisp.setSelected(true);
		}
		constraints1.gridx = 1;
		constraints1.gridy = 0;
		constraints1.gridwidth= 3;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(waterDisp, constraints1);
		jp.add(waterDisp);
		
		// separator
		JSeparator vspr2=new JSeparator(SwingConstants.VERTICAL);
		vspr2.setPreferredSize(new Dimension(30, 100));
		jp.add(vspr2);
		
		String s2 = Path.getPath()+"/images/program_h6_bg_illuminance.gif";
		if(IsMacorWin.isMacOrWin()==false) s2 = Path.getPath()+"images\\program_h6_bg_illuminance.gif";

		illumDisp=new GDCheck(s2);
		//"/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_illuminance.gif");
		illumDisp.setIconText("�Ɠx");
		illumDisp.setName("�Ɠx");
		illumDisp.setLabel("�x���Ɠx");
		illumDisp.setLevelUnit("Lux");
		String[] label=new String[50];
		for(int i=100;i<5000;i+=100) label[i/100]=String.valueOf(i);
		illumDisp.setSelectionItems(label);
		String si=Files.getIlluminationWarningLevel();
		if(si!=null){
		int idi=(Integer.valueOf(si)-100)/100;
		illumDisp.setSelectedIndex(idi);
		} else 
		illumDisp.setSelectedIndex(0);
			
		if(status!=null){
		if(status.contains("Illum")==true) illumDisp.setSelected(true);
		}
		constraints1.gridx = 2;
		constraints1.gridy = 0;
		constraints1.gridwidth= 3;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumDisp, constraints1);
		jp.add(illumDisp);
		
		//	horizontal line
		constraints1.gridx = 0;
		constraints1.gridy = 1;
		constraints1.gridwidth= 1;
//		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		JSeparator jspr=new JSeparator();
		jspr.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jspr.setPreferredSize(new Dimension(480, 10));
		jspr.setSize(200, 10);
		gridbag.setConstraints(jspr, constraints1);
		jp.add(jspr);	// ������
		

		// accept button
		if(acceptBtn == null)
			acceptBtn=new JButton("���̐ݒ��K�p����");
		
		acceptBtn.setName("���̐ݒ��K�p����");	
		acceptBtn.setPreferredSize(new Dimension(180,60));
		acceptBtn.setEnabled(true);// �����A�N�e�B�u
		// set ActionListner
		acceptBtn.addActionListener(this);
		
		constraints1.gridx = 0;
		constraints1.gridy = 5;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(acceptBtn, constraints1);
		jp.add(acceptBtn);
	
	
 // ToDo
		JLabel glabel=new JLabel();
		glabel.setText("������M����");
		GridBagConstraints constraintsTLabel = new GridBagConstraints();
		constraintsTLabel.gridx = 1;
		constraintsTLabel.gridy = 4;
		gridbag.setConstraints(glabel, constraintsTLabel);
		jp.add(glabel);
		
		
		DelDoubleComb gjp=new DelDoubleComb();
		gjp.setEnabled(true);
		gjp.setButtonEnabled(false);
		System.out.println("ReportPanelClass |"+Files.getReportsPeriodicTime()+"|");
		//
		String t=Files.getReportsPeriodicTime(); // <<  ????
		if(t==null || t.length()==0) t="0:0";
		
		String[] hm=t.split(":");
		System.out.println("ReportPanelClass h|"+hm[0]+"|");
		System.out.println("ReportPanelClass m|"+hm[1]+"|");
		//
		//
		hm[0]=hm[0].replace("00", "0");
		hm[0]=hm[0].replace("01", "1");
		hm[0]=hm[0].replace("02", "2");
		hm[0]=hm[0].replace("03", "3");
		hm[0]=hm[0].replace("04", "4");
		hm[0]=hm[0].replace("05", "5");
		hm[0]=hm[0].replace("06", "6");
		hm[0]=hm[0].replace("07", "7");
		hm[0]=hm[0].replace("08", "8");
		hm[0]=hm[0].replace("09", "9");
		int h=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//

		hm[1]=hm[1].replace("00", "0");
		hm[1]=hm[1].replace("01", "1");
		hm[1]=hm[1].replace("02", "2");
		hm[1]=hm[1].replace("03", "3");
		hm[1]=hm[1].replace("04", "4");
		hm[1]=hm[1].replace("05", "5");
		hm[1]=hm[1].replace("06", "6");
		hm[1]=hm[1].replace("07", "7");
		hm[1]=hm[1].replace("08", "8");
		hm[1]=hm[1].replace("09", "9");
		int m=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
		
		gjp.setHour(h);
		gjp.setMin(m);
		gjp.addObserver((java.util.Observer)new ReportPanel.ObserverA());
		
		GridBagConstraints constraintsT = new GridBagConstraints();
		constraintsT.gridx = 2;
		constraintsT.gridy = 4;
		gridbag.setConstraints(gjp, constraintsT);
		jp.add(gjp);
	
		
		//
		c.setLayout(new BorderLayout());
		c.add( jp, BorderLayout.CENTER );
		
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �f�t�H���g�̊ώ@�҂�ǉ�
		defaultO=new ObserverA();
		
		acceptBtnX=acceptBtn;
		tempDispX=tempDisp;
		waterDispX=waterDisp;
		illumDispX=illumDisp;
		
		setObserver(defaultO);
		
	}
	
	public void setTempWarn(String it)
	{
	tempDisp.setValue(it);
	}
	
	public void setWaterWarn(String tw) {
		waterDisp.setValue(tw);
	}

	public void setIllumWarn(String iw) {
		illumDisp.setValue(iw);
	}
	
	//�K�v�ȃ��b�\�h��ǉ�
	// Observer��ǉ�����
	void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}

	// Observer��ݒ肷��
	// �ȑO�ɒǉ����ꂽObserver�͑S�Ĕj�������
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
	}

	// Observer���폜
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}

	//
	private static ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:
	private Observer defaultO=null;  //  @jve:decl-index=0:
	public Object acceptBtnX=null;
	public static Object tempDispX=null;
	public static Object waterDispX=null;
	public static Object illumDispX=null;

	static public boolean temp=false;
	static public boolean water=false;
	static public boolean illum=false;
	
	public static int rHour=0;
	public static int rMin=0;
	
	//�e�N���X�ŗL

	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	static class ObserverA implements Observer {
		//
		

		
	@Override
	public void update(Observable arg0, Object arg1) {
		
		String message=(String)arg1;
//		if(message.contains("����InfoDispEdit class�ł��B")) return;
		if(tempDispX==null||waterDispX==null||illumDispX==null) return;
		if(message.contains("���x ")) temp  = ((GDCheck) tempDispX).isSelected();
		if(message.contains("���� ")) water = ((GDCheck) waterDispX).isSelected();
		if(message.contains("�Ɠx ")) illum = ((GDCheck) illumDispX).isSelected();
		//acceptBtn.setEnabled(temp|water|illum);

		String str = (String) arg1;
		System.out.println("����ReportPanel class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);

		// 
		Double x=0.0;
		if(message.contains("�x�����x")){
			String[] t=message.split(" ");
			if(t[2].equals("null")==false){
			x=Double.valueOf(t[2]);
			ITPlanterClass.getCurrentPlanterClass().getSensor().setTempratureWarningLevel(x);
			String msg="WarnTemp "+t[2]+System.getProperty("line.separator");
			Files.setTempratureWarningLevel(msg);
			System.out.println(msg);
			}
		} else
		if(message.contains("�x������ ")){
			String[] t=message.split(" ");
			if(t[2].equals("null")==false){
			x=Double.valueOf(t[2]);
			ITPlanterClass.getCurrentPlanterClass().getSensor().setWaterWarningLevel(x);
			String msg="WarnWater "+t[2]+System.getProperty("line.separator");
			Files.setWaterWarningLevel(msg);
			System.out.println(msg);
			}
		} else
		if(message.contains("�x���Ɠx")){
			String[] t=message.split(" ");
			if(t[2].equals("null")==false){
			x=Double.valueOf(t[2]);
			ITPlanterClass.getCurrentPlanterClass().getSensor().setIlluminationWarningLevel(x);
			String msg="WarnIllum "+t[2]+System.getProperty("line.separator");
			Files.setIlluminationWarningLevel(msg);
			System.out.println(msg);
			}
		}
		//
//		if(message.contains("�x������")) water = waterDisp.isSelected();
//		if(message.contains("�x���Ɠx")) illum = illumDisp.isSelected();
    	// set Warning level
    	// �͔|�v���O�����̐ݒ�l����ݒ肷�ׂ� ->  �����点���[���@�^�u�Őݒ肵�������g���₷�� 130704
    	/*
		����PlantPrgEdit class�ł��B�ώ@�Ώۂ̒ʒm�����m������B����InfoDispEdit class�ł��B�x�����x�㏸ 35 ��
		����PlantPrgEdit class�ł��B�ώ@�Ώۂ̒ʒm�����m������B����InfoDispEdit class�ł��B�x������ 35 ��
		����PlantPrgEdit class�ł��B�ώ@�Ώۂ̒ʒm�����m������B����InfoDispEdit class�ł��B�x�������Ɠx 0 lux
    	 */
    	
  //  	ITPlanterClass.getPlanterList().get(i).getSensor().setWaterWarningLevel(35.0);
  //  	ITPlanterClass.getPlanterList().get(i).getSensor().setIlluminationWarningLevel(2000);
//
		
		if(str.contains("HourComb")==true){
			String[] s=str.split(" ");
			rHour=Integer.parseInt(s[s.length-1]);
		}
		if(str.contains("minComb")==true){
			String[] s=str.split(" ");
			rMin=Integer.parseInt(s[s.length-1]);
		}
			
	}
	}

	
	public  void  acceptEnable(boolean b)
	{
		if(acceptBtnX!=null)
			((Component) acceptBtnX).setEnabled(b);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//
		JButton cb=(JButton)arg0.getSource();
		temp = tempDisp.isSelected();
		water = waterDisp.isSelected();
		illum = illumDisp.isSelected();
		// check box�̓��e���L�^����
    	ITPlanterClass.getCurrentPlanterClass().getSensor().setTempratureReport(temp);
    	ITPlanterClass.getCurrentPlanterClass().getSensor().setWaterlevelReport(water);
    	ITPlanterClass.getCurrentPlanterClass().getSensor().setIlluminationReport(illum);

		message = "����ReportPanel class�ł��B"+cb.getName()+" �{�^����������܂����Btemp "+temp+" water "+water+" illum "+illum +" time "+rHour+":"+rMin;
		String s="";
		if(temp==true ) s+="Temp ";
		if(water==true) s+="Water ";
		if(illum==true) s+="Illum ";
		//
		s+=rHour+":"+rMin;//+System.getProperty("line.separator");// report mail send time at one day
		Files.setReports(s);
		Files.savePlantersSettings();
		//
		stopReportTimer();
		startReportTimer();
		//
		// Do processing using temperature value
		observableMan.setMessage(message);
		// �ώ@�ґS���ɒʒm
		observableMan.notifyObservers();
		//
	}

	public void setTemp(boolean b) {
		tempDisp.setSelected(b);
	}
	public void setWater(boolean b) {
		waterDisp.setSelected(b);
	}
	public void setIllum(boolean b) {
		illumDisp.setSelected(b);
	}

	public void setTime(String s) {
		String[] hm=s.split(":");
		int ih=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
		int im=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
		rHour=Integer.valueOf(ih);
		rMin=Integer.valueOf(im);
//		rHour=Integer.valueOf(s.split(":")[0]);
//		rMin=Integer.valueOf(s.split(":")[1]);
	}
	public Timer longTimer =null;
	public void startReportTimer(){
		longTimer = new Timer(true);
		Calendar calendar=Calendar.getInstance();
		int h=calendar.get(Calendar.HOUR_OF_DAY);
		int m=calendar.get(Calendar.MINUTE);
		
		if( longTimer != null){
			longTimer.cancel();
			longTimer.purge();
		}
		longTimer=new Timer(true);
		//
		long startTime=60*(60*(rHour-h)+(rMin-m))*1000;// msec
		//
		if(startTime<0) startTime += 24*60*60*1000;
		//
System.out.println("ReportPanelClass: rHour "+rHour+" h "+h+" rMin "+rMin+" m "+m);
System.out.println("ReportPanelClass: startTime="+startTime);
		//
		longTimer.schedule(new TaskReportLong(), startTime, (24*60*60*1000)); // �^�X�N�̎��s�Ԋu��24���ԂɂP�x

	}
	
	public void stopReportTimer()
	{
		System.out.println("PlanterSettingClass: Stop Long Sensor Timer");

		if( longTimer != null){
			longTimer.cancel();
			longTimer.purge();
		//	longTimer = new Timer();
		}
	}

	public void purgeReportTimer()
	{
		if( longTimer != null){
			longTimer.purge();
		//	longTimer=new Timer();
		}
	}
	
	class TaskReportLong extends TimerTask {
	    @Override
		public void run() {
	    	
	    	for(int i=0;i<ITPlanterClass.getPlanterList().size();i++){
	    	String s=ITPlanterClass.getPlanterList().get(i).getSensor().getSensors();
	    	
	    	System.out.println("SensorPanelClass: TaskSensorLong "+s+" "+i);

	    	Settings.readMailSetup();// read from file

	    	TimeDate td=new TimeDate();
	    		String pn="Report from "+ITPlanterClass.getPlanterList().get(i).getInformation().getPlanterName()+System.getProperty("line.separator");
	    		String recordData=pn+"SensorRecordTime "+td.getYear()+":"+td.getMonth()+":"+td.getDay()+" "+td.getHour()+":"+td.getMin()+System.getProperty("line.separator");//+" Temp "+temp+" Water "+water+" Illum "+illum;
	 			
//				if(Sensor.temperatureReport==true||Sensor.illuminationReport==true||Sensor.waterlevelReport==true){
	    		// �ݒ肳�ꂽ���ڂ�ǂݏo��
				sendMail.setMessageText(recordData);
	    		// ���m�点���[�����o���B
				String subjectString="Report From itplanter";
				//Title
				sendMail.setTitleText(subjectString);
				//
				sendMail.addMessageText(System.getProperty("line.separator"));
				if(Sensor.getSensorTemperature() > Sensor.getTemperatureWarningLevel()){
					Sensor.temperatureReport=true;
					String msg="Temp Warnig over "+String.valueOf(Sensor.getTemperatureWarningLevel());
					sendMail.addMessageText(msg);
					sendMail.setTitleText(msg);
				} 
				if(Sensor.illumination < Sensor.getIlluminationWarningLevel()){// and check Light statue
					Sensor.illuminationReport=true;
					String msg="Illum Warnig under "+String.valueOf(Sensor.getIlluminationWarningLevel());
					sendMail.addMessageText(msg);
					sendMail.setTitleText(msg);
				} 
				if(Sensor.waterlevel < Sensor.getWaterWarningLevel()){// and check pump status
					Sensor.waterlevelReport=true;
					String msg="Water Warning under�@"+String.valueOf(Sensor.getWaterWarningLevel())+" %%";
					sendMail.setTitleText(msg);
					sendMail.addMessageText(msg);// Sensor.getWaterLevel();
				} 
				
				sendMail.addMessageText("Temp "+String.valueOf(Sensor.getSensorTemperature()));
				sendMail.addMessageText("Illum "+String.valueOf(Sensor.illumination));
				sendMail.addMessageText("Water "+String.valueOf(Sensor.waterlevel));// Sensor.getWaterLevel();
					//
					//PlanterSetting.setOptions() ;// read from panel
				sendMail.mailSend();// �����点���[�� /// <<< 130704
				System.out.println("SensorPanelClass: recordData2="+recordData);
//				}		
	    	}
	    }
	}




}

