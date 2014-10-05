import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;

public class AutoPicture extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 自動撮影　：　一定時間ごと
		JFrame frame = new JFrame();
		AutoPicture gjp=new AutoPicture();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	//	constantTimePicture.setEnabled(false);
	//	choiceSetting.setSelected(false);
	//	nonAutoPicture.setEnabled(true);
	}

	/**
	 * This is the default constructor
	 */
	public AutoPicture() {
		super();
		initialize();
	}

	private static CheckComb constantTimePicture = new CheckComb();
	private static ChoiceSetting choiceSetting = new ChoiceSetting();
	private static CheckLabel 	nonAutoPicture = new CheckLabel();	
	private static JButton acceptBtn=new JButton("この設定を適用する");
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */

	private void initialize() {
		String s=Files.getAutoPicture();
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel jp=new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		// default size
		//jp.setPreferredSize(new Dimension(640,320));
		
		//System.out.println("s=|"+s+"|");
		// panel1
		// 一定時間ごと
		//constantTimePicture = new CheckComb();
		constantTimePicture.setSelected(false);
		//constantTimePicture.jComboBox.setEnabled(false);
				
		//
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(constantTimePicture, constraints1);
		jp.add(constantTimePicture);
		
		// separator
		JSeparator vspr=new JSeparator(SwingConstants.VERTICAL);
		vspr.setPreferredSize(new Dimension(30, 100));
		GridBagConstraints constraints1s = new GridBagConstraints();
		constraints1s.gridx = 1;
		constraints1s.gridy = 0;
		constraints1s.gridwidth= 1;
		constraints1s.gridheight = 1;
		constraints1s.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr, constraints1s);
		jp.add(vspr);

		// panel2
		// お好み設定
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 2;
		constraints2.gridy = 0;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(choiceSetting, constraints2);
		// periodic atTime non
		
// AutoPicture atTime 10:00,12:00,....
		choiceSetting.setSelected(false);
		
		
		jp.add(choiceSetting);
		
		// separator
		JSeparator vspr2=new JSeparator(SwingConstants.VERTICAL);
		vspr2.setPreferredSize(new Dimension(30, 100));
		GridBagConstraints constraints2s = new GridBagConstraints();
		constraints2s.gridx = 3;
		constraints2s.gridy = 0;
		constraints2s.gridwidth= 1;
		constraints2s.gridheight = 1;
		constraints2s.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr2, constraints2s);
		jp.add(vspr2);
		
		// panel3
		// 自動撮影を行わない
		nonAutoPicture.getJRadioButton().setSelected(true);
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 4;
		constraints3.gridy = 0;
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(nonAutoPicture, constraints3);
		jp.add(nonAutoPicture);
		// periodic atTime non
		nonAutoPicture.setSelected(false);
		
		//
		
		//	horizontal line
		GridBagConstraints constraints4s = new GridBagConstraints();
		constraints4s.gridx = 0;
		constraints4s.gridy = 1;
		constraints4s.gridwidth= 5;
		constraints4s.gridheight = 3;
		constraints4s.insets = new Insets(0, 0, 0, 0);
		
		JSeparator jspr=new JSeparator(SwingConstants.HORIZONTAL);
		//jspr.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jspr.setPreferredSize(new Dimension(600, 10));
		//jspr.setSize(200, 10);

		gridbag.setConstraints(jspr, constraints4s);
		jp.add(jspr);	// 水平線
		//
		
		// accept button
		
		acceptBtn.setPreferredSize(new Dimension(200,60));
		acceptBtn.setEnabled(true);
		// set ActionListner
		acceptBtn.addActionListener(new acceptAction());
		GridBagConstraints constraints5 = new GridBagConstraints();
		constraints5.gridx = 0;
		constraints5.gridy = 5;
		constraints5.gridwidth= 5;
		constraints5.gridheight = 1;
		constraints5.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(acceptBtn, constraints5);
		jp.add(acceptBtn);
		
		//
		this.add(jp);

		//
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// 観察者を追加
		observableMan.addObserver(new AutoPicture.ObserverA());
	}
public static void setNon(){
	String s=Files.getAutoPicture();
		nonAutoPicture.setSelected(true);
//System.out.println("non |"+s);
	}
	
public static void setPeriodic(){
	String s=Files.getAutoPicture();
	//set periodic schedule
	if(constantTimePicture==null) return;
	constantTimePicture.setSelected(true);
//	String s1=Files.getAutoPicture();
//System.out.println("s1="+s);
	String[] s2=s.split(" ");
	String t="1";// defaul value
	if(s2.length>2){		
//System.out.println("s2=|"+s2[2]+"|");
	if(s2[2].length()>0) t=s2[2];// remove line.separator
	if(t.length()>0){
//System.out.println("n1=|"+t+"|");
		int n=Integer.parseInt(t.replaceAll("[^0-9]",""))-1;// 23時 → n=22
//	int n=Integer.valueOf(t)-1;
//System.out.println("n1="+n);
	constantTimePicture.setSelectedIndex(n);
	// set schedule
	setPeriodicSchedule(n);
			}// include periodic number
		}// keyword word value
	}// periodic keyword found


public static void	setConstTime(){
	//
	String s=Files.getAutoPicture();
	choiceSetting.setSelected(true);
	
//System.out.println("AutoPictureClass: Files.getAutoPicture() s=|"+s+"|");

	//
	String[] s2=s.split(" ");
//System.out.println("AutoPictureClass: s2.length= |"+s2.length+"|");
	if(s2.length>2){		
	if(s2[2].length()>0){
		String[] tt=s2[2].split(",");
	// Time Table set
//System.out.println("AutoPictureClass: constTime |"+tt.length+"|");
	choiceSetting.getTimeTable().removeAll(choiceSetting.getTimeTable());
//System.out.println("choiceSetting.getTimeTable().size()=|"+choiceSetting.getTimeTable().size()+"|");
	for(int i=0;i<tt.length;i++){
		choiceSetting.addTimeSet(i);
//System.out.println("AutoPictureClass: choiceSetting.getTimeTable().size()=|"+i+" "+choiceSetting.getTimeTable().size()+"|");
	}
			for(int i=0;i<tt.length;i++){
				String t=tt[i];
				if(t.length()>0){
				String h=t.split(":")[0];
				String m=t.split(":")[1];
				//choiceSetting
//System.out.println("AutoPictureClass: setTime="+i+" "+"|"+h+":"+m+"|");
				int ih=Integer.parseInt(h.replaceAll("[^0-9]",""));//
				int im=Integer.parseInt(m.replaceAll("[^0-9]",""));//
				choiceSetting.setTime(i,ih,im);
				}
			}// next i
	// set schedule
			setConstTimeSchedule();
			}
		}
	}//

private static void setConstTimeSchedule() {
	String[] s=choiceSetting.getSchedule();
	
	String ss="const ";
	for(int i=0;i<s.length;i++){
		ss +=s[i].replace(" ", "");
		if(i<s.length-1) ss+=",";
	}
	
//System.out.println("AutoPicutreClass setConstTImeSchedule ss="+ss+"	choiceSetting.getTimeTable().size()"+	choiceSetting.getTimeTable().size());

	Files.setAutoPicture(ss);
	//
	//ArrayList<String> ps=Files.getPlantersSettings();
	// keyword  AutoPicture
	
	// AutoPicture atTime
	
	// AutoPicture atTime h:m,h:m,h:m,....
	
	//
	//Files.savePlantersSettings();
	
	//String[] s =choiceSetting.getSchedule();
 	// カメラ撮影のタイムテーブル
 	//TPT	
 	//Amount: 3	
 	//Time: 07:00:00	
 	//Time: 12:00:00	
 	//Time: 17:00:00	
 	//(空行)	
	String timeScheduleCom="";
	timeScheduleCom+="TPT"+System.getProperty("line.separator");
	timeScheduleCom+="Amount: "+s.length+System.getProperty("line.separator");
	//
	//lengthOfTask=s.length;
	//
	for(int i=0;i<s.length;i++){
		timeScheduleCom+="Time: "+s[i]+":00"+System.getProperty("line.separator");
		}
		timeScheduleCom+=System.getProperty("line.separator");
		
//System.out.println(timeScheduleCom);

		String[] scheduleCom=timeScheduleCom.split(System.getProperty("line.separator"));
		
		if(CameraCapture.getState()==false){
			new CameraCapture();
		}
		//
		// すべてのアクティブなカメラを対象に撮影する。

		CameraCapture.saveCameraTimeTable(scheduleCom);// タイムスケジュールを作成
		CameraCapture.TPT_processing(scheduleCom);// タイマーに登録する

		for(int i=0;i<s.length;i++) System.out.println("AutoPictureClass: 自動撮影時間 "+s[i]+":00");
	}

	//
    private static void setPeriodicSchedule(int n) {
		// カメラ撮影のタイムテーブル
		//TPT	
		//Amount: 3	
		//Time: 07:00:00	
		//Time: 12:00:00	
		//Time: 17:00:00	
		//(空行)	
 //System.out.println("AutoPictureClass: setPeriodicSchedule= "+n);
    	if(n<=0) return;
		String timeScheduleCom="";
		timeScheduleCom+="TPT"+System.getProperty("line.separator");
		timeScheduleCom+="Amount: "+24/n+System.getProperty("line.separator");
		//
		//
		for(int i=0;i<24;i+=n){
			if(i<10)
				timeScheduleCom+="Time: 0"+i+":00:00"+System.getProperty("line.separator");
			else
				timeScheduleCom+="Time: "+i+":00:00"+System.getProperty("line.separator");
			}
			timeScheduleCom+=System.getProperty("line.separator");
							
//System.out.println("AutoPictureClass: "+timeScheduleCom);
			String[] scheduleCom=timeScheduleCom.split(System.getProperty("line.separator"));
			
			if(CameraCapture.getState()==false){
				new CameraCapture();
			}
			CameraCapture.saveCameraTimeTable(scheduleCom);
			CameraCapture.TPT_processing(scheduleCom);
	}

	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:
	
	//
	static private boolean pressed=false;
	static private boolean constTime=true;
	static private boolean atTime=false;
	static private boolean noAutoPic=false;
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {

		
	@Override
	public void update(Observable o, Object arg) {
		String message = (String) arg;
		// どれか一つしか選べない
//		System.out.println("AutoPictureClass MESSAGE "+message);
	
/*		
		if(message.contains("一定時間")==true){
			if(message.contains("true")==true){
			constTime=true;
			atTime=false; 
			noAutoPic=false; 
			pressed=false;
			if(constantTimePicture!=null) constantTimePicture.setSelected(true);
			if(choiceSetting!=null) choiceSetting.setSelected(false);
			String s="periodic ";
			if(constantTimePicture !=null){
				s=s+constantTimePicture.getSchedule();
			}
			Files.setAutoPicture(s);	
			System.out.println("!!******!!");

			} else {
			System.out.println("!!!!!");
			constTime=false;
			if(constantTimePicture!=null) constantTimePicture.setSelected(false);
			}
		}
		if(message.contains("お好み設定")==true){
			if(message.contains("true")==true){
			atTime=true; 
			constTime=false; 
			noAutoPic=false; 
			pressed=false; 
			if(choiceSetting==null) return;
			String[] s=choiceSetting.getSchedule();
			String ss="const ";
			for(int i=0;i<s.length;i++){
				ss +=s[i].replace(" ", "");
				if(i<s.length-1) ss+=",";
			}
System.out.println("AutoPictureClass: "+ss);
			if(constantTimePicture!=null) constantTimePicture.setSelected(false);
			if(choiceSetting!=null) choiceSetting.setSelected(true);
			Files.setAutoPicture(ss);
			} else {
			atTime=false;
			if(choiceSetting!=null) choiceSetting.setSelected(false);
			}
		}
		if(message.contains("自動撮影を行わない")==true){
			if(message.contains("true")==true){
			noAutoPic=true;  
			atTime=false; 
			constTime=false; 
			pressed=false; 
			String s="non";
//System.out.println(s);
			Files.setAutoPicture(s);
			if(nonAutoPicture!=null) nonAutoPicture.setSelected(true);
			if(constantTimePicture!=null) constantTimePicture.setSelected(false);
			if(choiceSetting!=null) choiceSetting.setSelected(false);
			} else {
				if(nonAutoPicture!=null) nonAutoPicture.setSelected(false);
			noAutoPic=false; 
			}
		}
		*/
		
		
//		System.out.println("私はAutoPicture classです。観察対象の通知を検知したよ。" + message);
		
		if(acceptBtn != null ){
			System.out.println("私はAutoPicture classです。観察対象の通知を検知したよ。" + constTime + atTime + noAutoPic + pressed );
				acceptBtn.setEnabled( (constTime|atTime|noAutoPic) & !pressed) ;
			}
		}
	}

  
	public class acceptAction implements ActionListener{
        int lengthOfTask = 0; //list.size();
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//	acceptBtn.setEnabled(true);
			//	この設定を適用する。
			//
			JButton jb=(JButton)arg0.getSource();
			// ボタンの状態を確認すること。
			if(	pressed != true){
			message ="私はAutoPicture classです。"+jb.getText()+" ボタンが押されました。";
			
			// 観察者全員に通知
			observableMan.setMessage(message);
			observableMan.notifyObservers();
			//System.out.println("Accept Button");
			
			// checkButtonが、お好み設定ならば、
			// TimerSetting !!　　ここでスケジュールを設定する。	
			
			//ここで checkBoxの状態を検知すること。
			
			atTime=choiceSetting.getSelected();
			constTime=constantTimePicture.isSelected();
			noAutoPic=nonAutoPicture.getSelected();
			
			if(atTime){
			setAtTimeSchedule();
			}
			else if(constTime){
			setPeriodicSchedule();
				}
			else if(noAutoPic){
			setCancelSchedule();
			}
			
			//pressed = true;// ボタン二度押し禁止
			acceptBtn.setEnabled(!pressed);
			}
		}
		
		private void setCancelSchedule() {
			// 設定されているスケジュールを消去する。
//System.out.println("AutoPictureClass: 自動撮影を行わない");

			String s="non";
//System.out.println(s);
			Files.setAutoPicture(s);
			Files.savePlantersSettings();
			
			//TPT	
			//Amount: 0
			//(空行)	
			String timeScheduleCom="";
			timeScheduleCom+="TPT"+System.getProperty("line.separator");
			timeScheduleCom+="Amount: 0"+System.getProperty("line.separator");
			timeScheduleCom+=System.getProperty("line.separator");
					
//		System.out.println(timeScheduleCom);
				String[] scheduleCom=timeScheduleCom.split(System.getProperty("line.separator"));
				
				// 多分、不要
				if(CameraCapture.getState()==false) new CameraCapture();
				
				CameraCapture.saveCameraTimeTable(scheduleCom);
				CameraCapture.TPT_processing(scheduleCom);
		}
		
		private void setPeriodicSchedule() {
			//
			String s0="periodic ";
			if(constantTimePicture !=null){
			s0=s0+constantTimePicture.getSchedule();
			}
//System.out.println(s);
			Files.setAutoPicture(s0);
			Files.savePlantersSettings();
			
			String s =constantTimePicture.getSchedule();
//
			// カメラ撮影のタイムテーブル
			//TPT	
			//Amount: 3	
			//Time: 07:00:00	
			//Time: 12:00:00	
			//Time: 17:00:00	
			//(空行)	
			String timeScheduleCom="";
			timeScheduleCom+="TPT"+System.getProperty("line.separator");
			int is=Integer.parseInt(s.replaceAll("[^0-9]",""));//
			timeScheduleCom+="Amount: "+24/is+System.getProperty("line.separator");
			//
			//lengthOfTask=24;
			//
			for(int i=0;i<24;i+=is){
				if(i<10)
					timeScheduleCom+="Time: 0"+i+":00:00"+System.getProperty("line.separator");
				else
					timeScheduleCom+="Time: "+i+":00:00"+System.getProperty("line.separator");
				}
				timeScheduleCom+=System.getProperty("line.separator");
								
//System.out.println(timeScheduleCom);
				String[] scheduleCom=timeScheduleCom.split(System.getProperty("line.separator"));
				
				if(CameraCapture.getState()==false){
					new CameraCapture();
				}
				CameraCapture.saveCameraTimeTable(scheduleCom);
				CameraCapture.TPT_processing(scheduleCom);
		}
	
		private void setAtTimeSchedule() {
			String[] s=choiceSetting.getSchedule();
			String ss="const ";
			for(int i=0;i<s.length;i++){
				ss += s[i].replace(" ", "");
				if(i<s.length-1) ss+=",";
			}
	
//System.out.println(ss);
			Files.setAutoPicture(ss);
			Files.savePlantersSettings();
			
			//String[] s =choiceSetting.getSchedule();
		 	// カメラ撮影のタイムテーブル
		 	//TPT	
		 	//Amount: 3	
		 	//Time: 07:00:00	
		 	//Time: 12:00:00	
		 	//Time: 17:00:00	
		 	//(空行)	
			String timeScheduleCom="";
			timeScheduleCom+="TPT"+System.getProperty("line.separator");
			timeScheduleCom+="Amount: "+s.length+System.getProperty("line.separator");
			//
			//lengthOfTask=s.length;
			//
			for(int i=0;i<s.length;i++){
				timeScheduleCom+="Time: "+s[i]+":00"+System.getProperty("line.separator");
				}
				timeScheduleCom+=System.getProperty("line.separator");
				
//System.out.println(timeScheduleCom);
	
				String[] scheduleCom=timeScheduleCom.split(System.getProperty("line.separator"));
				
				if(CameraCapture.getState()==false){
					new CameraCapture();
				}
				//
				// すべてのアクティブなカメラを対象に撮影する。
				CameraCapture.saveCameraTimeTable(scheduleCom);// タイムスケジュールを作成
				CameraCapture.TPT_processing(scheduleCom);// タイマーに登録する

//for(int i=0;i<s.length;i++) System.out.println("自動撮影時間 "+s[i]+":00");
		}
	}


	public String getSettingTime() {
		String s =constantTimePicture.getSchedule();
		return s;
	}
	
	

}
