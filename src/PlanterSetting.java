

import javax.swing.SwingUtilities;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.Observable;
import java.util.Observer;


public class PlanterSetting extends JFrame implements ChangeListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private static JTabbedPane 	jTabbedPane = null;
	private static JPanel 		cardPanel=null;
	private static CardLayout 	layout=null;
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	public  static ReportPanel 	 reportPanel   =null;
	public  static SensorPanel   sensorPanel   =null;
	public  static PlantAbsPanel plantAbsPanel =null;
	public  static PlantPrgEdit  plantPrgPanel =null;
	public  static PlantPrgEdit  plantPrgEdit  =null;
	private static Settings option=null;
	
	public static PanelContiner panelContiner=null;
	
	private  static PlanterSetting thisClass =null;
	private Selecters  selecters=null;
	
	public  Component getThisClass()
	{
		return thisClass;
	}
	
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			//
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBackground(new Color(250,251,245));

		    jTabbedPane.addChangeListener(this);
			jTabbedPane.setBackground(Color.white);
			
			//
			this.setLayout(new GridBagLayout());
//
			//if(ITPlanterClass.getState()==false ) new ITPlanterClass();
			sensorPanel=new SensorPanel();
//			
			jTabbedPane.add("センサー",sensorPanel);
			
			//			
			plantAbsPanel=new PlantAbsPanel();// 栽培プログラム選択
			//
			plantPrgPanel = new PlantPrgEdit();// 栽培プログラム詳細表示
//			plantPrgPanel.setEnabled(false);// 表示モード
			plantPrgPanel.setName("詳細表示");
			
			//
			plantPrgEdit  = new PlantPrgEdit();// 栽培プログラム修正
			plantPrgEdit.setName("編集");
			selecters  = new Selecters();// セレクター
			selecters.setBackground(new Color(250,251,245));
			
			plantAbsPanel.addObserver(new PlanterSetting.ObserverA());
//			plantPrgPanel.addObserver((java.util.Observer)new PlanterSetting.ObserverA());
			plantPrgEdit.addObserver(new PlanterSetting.ObserverA());
			selecters.addObserver(new PlanterSetting.ObserverA());
////			selecters.addObserver((java.util.Observer)new PlanterSettingWindow.ObserverA());
			
			// Card Panel
		    cardPanel = new JPanel();
		    layout = new CardLayout();
		    cardPanel.setLayout(layout);

		    cardPanel.add(plantAbsPanel,    "plantAbsProgram");// 栽培プログラム選択			cardPanel.getComponent(0)
		    cardPanel.add(plantPrgEdit, 	"plantPrgPanel");// 栽培プログラム詳細表示			cardPanel.getComponent(1)
		    cardPanel.add(plantPrgEdit,  	"plantPrgEdit");// 栽培プログラム修正   		    cardPanel.getComponent(2)
		    plantPrgPanel=plantPrgEdit;
		    panelContiner=new PanelContiner(plantAbsPanel,plantPrgPanel,plantPrgEdit);

		    //
			jTabbedPane.add("栽培プログラム",cardPanel);
			// 有償版だけの機能
			if(Version.getFreeOrPaid().contains("Paid")==true){			
			reportPanel=new ReportPanel();
			reportPanel.addObserver(new PlanterSetting.ObserverA());
			// load setting from planters.txt
			String status=Files.getReports();
//System.out.println("PlanterSettingClass: "+status);
			if(status.contains("Temp")==true) reportPanel.tempDisp.setSelected(true); else reportPanel.tempDisp.setSelected(false);
			if(status.contains("Water")==true) reportPanel.waterDisp.setSelected(true); else reportPanel.waterDisp.setSelected(false);
			if(status.contains("Illum")==true) reportPanel.illumDisp.setSelected(true); else reportPanel.illumDisp.setSelected(false);
			if(status.contains(":")==true){
				String[] elm=status.split(" ");
				String[] hm=elm[elm.length-1].split(":");
				//reportPanel.rHour=Integer.valueOf(hm[0]); 
				reportPanel.rHour=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
				//reportPanel.rMin=Integer.valueOf(hm[1]);
				ReportPanel.rMin=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
				reportPanel.stopReportTimer();
				reportPanel.startReportTimer();
			}
			//			
			if(Version.getRevision().contains("Education")==false){
			jTabbedPane.add("おしらせメール",reportPanel);
				}
			}
			//
			// 有償版だけの機能
			if(Version.getFreeOrPaid().contains("Paid")==true){
			AutoPictureTab autoPicture=new AutoPictureTab();
//System.out.println("PlanterSettingClass: autoPicture.setState()");
			autoPicture.setState();
			//autoPicture.set
			autoPicture.setBackground(new Color(250,251,245));
			jTabbedPane.add("自動撮影",autoPicture);
			}
			
			//
			jTabbedPane.add("セレクター",selecters);
			
			//
		
			option=new Settings();
			option.setBackground(new Color(250,251,245));
			jTabbedPane.add("設定",option);
			
		    //
		    layout.first(cardPanel);
		    //		    
		    jTabbedPane.setBackground(new Color(250,251,245));
		    
		}
		return jTabbedPane;
	}
	
	//
	// 正しく動作する。
	//
	private static int preIndex=0;
	  @Override
	public void stateChanged(ChangeEvent e) {
		    int index = jTabbedPane.getSelectedIndex();
		    
//	    System.out.println("Selected Index ： " + index);
		    int n=ITPlanterClass.getCurrentPlanterNo();
			if(ITPlanterClass.getSystemPlanterSize()>0)
				sendCom.sendcom((n+1)+" -e p");// Auto Mode
			
		    //
		    if(thisClass==null) thisClass=this;
		    
		    //
		    if(index==4){// Selecter Tab
		    //	System.out.println("Panel="+index);
		    	selecters.reListPlanter();// プランターを数え直す 
		    }
		    
		    if(index==1){
		    	// Resize Panel
		    if( prgNo != 0 ) setReSizeLL(thisClass); else setReSizeM(thisClass);
		    } else {
		    setReSizeS(thisClass);
		    }
		    
		    if((preIndex==0 && index==0)||preIndex==5){
		    // Save option data
		    	if(option!=null){
		    	option.setAllInformations();
		    	}
		    	setOptions();
				//jTabbedPane.add("センサー",bkgProgram);
		    	//bkgProgram.startSensorTimer(); 
		    //
		    }
//		    System.out.println("Selected prgNo ： " + prgNo +" index "+ index);
		    if( index == 0 ){
		    	//
		    	
//		    	System.out.println("Start Sensor Timer");
//	System.out.println("+++++++++++++CP="+(ITPlanterClass.getCurrentPlanterNo()+1));
		    	// ログイン設定ができていなければ、Timerを開始すべきではない。
		    	sensorPanel.startSensorShortTimer(); 
		    	sensorPanel.setSensorLongTime();// Add By SAKAGUTI 2013.1.10
		    	sensorPanel.startSensorLongTimer(); 
		    	} else {
		    	sensorPanel.stopSensorShortTimer();
// for test
	//	    	bkgProgram.stopSensorLongTimer(); 
		    	}
		    //			
		    
			//	jTabbedPane.set(0,bkgProgram.get(ITPlanterClass.getCurrentPlanterNo()));
		    preIndex=index;
	}
	  
	  
	  public static void setOptions() {
		  
		  		if(option==null) return;
				//SMTPServer
		  		//sendMail sm=new sendMail();
		  		sendMail.setServerAdress(option.sMTPServer.getText());
				//ToAddress
		  		sendMail.setToAdress(option.toAddress.getText());
				//FromAddress
		  		sendMail.setFromAdress(option.fromAddress.getText());
				//Title
		  		//sendMail.setTiteleText(option.getText());
		  		//MessageArea
		  		sendMail.setMessageText(option.messageArea.getText()+System.getProperty("line.separotor"));
	

	}

	//
	  public static  void setReSizeLL(PlanterSetting ps)
	  {
		  if(jTabbedPane != null){
	    	jTabbedPane.setSize(new Dimension(670,730));
			//
		  }
		 ps.setSize(new Dimension(670,730));

	  }
	  
	  
	  public  void setReSizeL(PlanterSetting ps)
	  {
		  if(jTabbedPane != null){
	    	jTabbedPane.setSize(new Dimension(640,700));
		  }
			  ps.setSize(new Dimension(640,700));
	  }
	  
	  //
	  public static  void setReSizeM(PlanterSetting ps)
	  {
		  if(jTabbedPane != null){
	    	jTabbedPane.setSize(new Dimension(640,400));
		  }
	    	ps.setSize(new Dimension(640,400));

	  }
	  
	  //
	  public static  void setReSizeS(PlanterSetting ps)
	  {
		  if(jTabbedPane != null){
	    	jTabbedPane.setSize(new Dimension(640,400));
		  }
		  ps.setSize(new Dimension(640,400));

	  }
	  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				thisClass = new PlanterSetting();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setResizable(false);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public PlanterSetting() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		this.setSize(640, 420);
		this.setContentPane(getJContentPane());
		Image icon=getToolkit().getImage("icon_Mac.png");
		this.setIconImage(icon);
		
		if(ITPlanterClass.getSystemCameraSize()==0)
			jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount()-2); // セレクターのタブから始まる
		else
			jTabbedPane.setSelectedIndex(0); // センサーのタブから始まる
			
		this.setTitle("itpManager v2");

	}

//
	public JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(getJTabbedPane(), null);
		}
		return jContentPane;
	}
	

	//
	class TriangleSliderUI extends javax.swing.plaf.metal.MetalSliderUI {
		@Override 
		  public void paintThumb(Graphics g) {
		    Graphics2D g2 = (Graphics2D)g;
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.fillOval(thumbRect.x,thumbRect.y,thumbRect.width,thumbRect.height);
		  }
	}

	public ObservableMan observableMan=null;  //  @jve:decl-index=0:
	/**
	* 観察される人。
	* 
	*/
	class ObservableMan extends Observable {

	// 直前に与えられた引数
	private String previousArg = "";

	/**
	* 観察者に通知します。
	* @param オブジェクト
	*/
	@Override
	public void notifyObservers() {
		String str="this";//String.valueOf(editBtn.isSelected());
		
			// 直前に与えられた引数と同じならそのままリターン
			if (str==previousArg) {
			//return;
			}
		
			previousArg = str;
		
			setChanged();
		
			// 通知
			super.notifyObservers(str);
		
			clearChanged();

			}
		}
	
	private static int prgNo=0;
	/**
	* 観察者を観察する人A。
	*
	*/
	static String selectedFile="";
	static String selectedFileFullPath="";
	
	public static class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
			String str = (String) arg;
			
			

			System.out.println("私はPlanterSetting classです。観察対象の通知を検知したよ。" + str);
			
			if(str.contains("doneButton")){
				// リストを作りなおす
				PlantAbsPanel pap=(PlantAbsPanel)PanelContiner.plantAbsPanel;
				if(pap==null) return;
				//pap.reList();
				
				System.out.println("適用する!!!");
				//
				//	PlantProgram Classの内容をアイティプランターに送信する
				//
				
				// プログレスバーの表示
				// 実際の処理の進行とは同期していない。。。。
				Progress progress=new Progress();
				progress.start();
							
				//if(ITPlanterClass.getState()==false) new ITPlanterClass();
				//ITPlanterClass.ITPlanterClassBegin();
				int ic=ITPlanterClass.getCurrentPlanterNo();
				Information pi=ITPlanterClass.getPlanterList().get(ic).getInformation();
				PlantProgram p=pi.getPlantProgram();
				
				p.sendToPlanter(ic);
				
				//
				// 栽培プログラムリストを、現在の栽培プログラムにフォーカスする。
				System.out.println("forcus program="+p.getProgramName());
				System.out.println("categoly="+p.getProgramCategory());
				System.out.println("plant="+p.getPlantName());
	
				// set forcus
				PlantAbsPanel.prgList.setProgram(p.getProgramName());
				PlantAbsPanel.prgList.setCategory(p.getPlantCategory());
				PlantAbsPanel.prgList.setPlantName(p.getPlantName());
				
				//
				return;
			}


			if(str.contains("Selected")){
				String[] s=str.split(" ");
				selectedFile=s[1];
				selectedFileFullPath=Files.getDBPath()+s[1]+".xml";
				// 選択されている栽培プログラムを設定する
				//if(ITPlanterClass.getState()==false) new ITPlanterClass();		
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram(selectedFile+".xml");
//System.out.println(selectedFile+"が選択された。");
			}
			
				
			//
			if(str.contains("このプランターを選択する")){
/*			
				System.out.println("PlanterSetting: このプランターを選択する");
				System.out.println("PlanterSetting: PlanterName=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName());
				System.out.println("PlanterSetting: CameraNo=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber());
				System.out.println("PlanterSetting: CameraName=" +ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName());
				for(int i=0;i<ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber();i++){
					System.out.println("PlanterSetting:  EntryCammera ["+i+"]"+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(i));
				}
*/
				if(jTabbedPane!=null){

				layout.first(cardPanel);
				prgNo=0;
				
				jTabbedPane.setSelectedIndex(0);
				if(thisClass!=null)
					setReSizeS(thisClass);
				}
			}
			
			//
			if(str.contains("この設定を適用する")){
				//////
				System.out.println("私はPlanterSetting classです。 この設定を適用する");
				//
				// 私はReportPanel classです。この設定を適用する ボタンが押されました。temp true water true illum false
				// ここで、おしらせメールを設定する
				
				//if(ITPlanterClass.getState()==false) new ITPlanterClass();
				
				Progress progress=new Progress();
				progress.start();
				
				int ic=ITPlanterClass.getCurrentPlanterNo();
				Information pi=ITPlanterClass.getPlanterList().get(ic).getInformation();
				PlantProgram p=pi.getPlantProgram();
				
				p.sendToPlanter(ic);//
				
				// send warn info to reportpanel class
				/* Bug */
				/*
				reportPanel.setTempWarn(String.valueOf(p.getTempWarn()));
				reportPanel.setWaterWarn(String.valueOf(p.getWaterWarn()));
				reportPanel.setIllumWarn(String.valueOf(p.getIllumWarn()));
				*/
				
				//
				
				//
				// 栽培プログラムリストを、現在の栽培プログラムにフォーカスする。
				System.out.println("forcus program="+p.getProgramName());
				System.out.println("categoly="+p.getProgramCategory());
				System.out.println("plant="+p.getPlantName());
	
				// set forcus
				PlantAbsPanel.prgList.setProgram(p.getProgramName());
				PlantAbsPanel.prgList.setCategory(p.getPlantCategory());
				PlantAbsPanel.prgList.setPlantName(p.getPlantName());
				
				
				//startSensorLongTimer();
				if(str.contains("ReportPanel class")==true){
				// setting temp, water, illum, time
					Sensor.temperatureReport=false;
					if(str.contains("temp true")==true)
								Sensor.temperatureReport=true;
					Sensor.waterlevelReport=false;
					if(str.contains("water true")==true)
								Sensor.waterlevelReport=true;
					Sensor.illuminationReport=false;
					if(str.contains("illum true")==true)
								Sensor.illuminationReport=true;
					// set periodic time here
					//String[] sp=Files.getReports().split(" ");
					String[] hm=Files.getReportsPeriodicTime().split(":");
					int ih=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
					int im=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
					long t=(ih*60+im)*60*1000;// msec
//					long t=(Integer.valueOf(hm[0])*60+Integer.valueOf(hm[1]))*60*1000;// msec
					sensorPanel.setPeriodicTime(t);
					System.out.println("PlanterSettingClass: PeriodicTime="+t);
					//
					sensorPanel.startSensorLongTimer();
				}
				//
				
				if(layout!=null){
				layout.first(cardPanel);
				prgNo=0;
				if(thisClass!=null)
					setReSizeM(thisClass);
				}
				return;
			} else 
				if(str.contains("キャンセル")){
					System.out.println("キャンセル");
					if(layout!=null){
					layout.first(cardPanel);
					prgNo=0;
					if(thisClass!=null)
						setReSizeM(thisClass);
					}
					return;
				} else
				if(str.contains("栽培プログラムの詳細を開く")){
					System.out.println("適用栽培プログラムの詳細を開く");
					if(selectedFile!=""){	
					//PlantPrgEdit p=(PlantPrgEdit) cardPanel.getComponent(1);
					//
					// 選択されている栽培プログラムを読み込む
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram(selectedFile+".xml");

					PlantPrgEdit pp=(PlantPrgEdit)PanelContiner.plantPrgPanel;
					pp.setProgramTitle(pp.prgInfoEdit);// 栽培プログラムのタイトル表示
					pp.authorPanel.setAutorInfo(selectedFile+".xml");// Author Panel情報セット
					
					pp.setPlantProgramDataToPanel(pp);// パネルに設定値を読み込む
					
					pp.setEnabled(false);
		
					layout.next(cardPanel);
					prgNo=1;
					if(thisClass!=null)
						setReSizeLL(thisClass);
					}
					return;
				} else 
			if(str.contains("新しく栽培プログラムを作る")){
//System.out.println("新しく栽培プログラムを作る");
						//
						//	新規データを用意する
						//
						// 　デフォルトの栽培プログラムを読み込む
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram("current.xml");
					ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setPlantName("");
					ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setPlantMemo("");
					
					//
					PlantPrgEdit pe=(PlantPrgEdit)PanelContiner.plantPrgEdit;
					
//System.out.println("name "+pe.getName());

					// TODO ここここ
					pe.setPlantProgramDataToPanel(pe);// パネルに設定値を読み込む
					pe.setEnabled(true);

						//
						//
					layout.last(cardPanel);
					//layout.show(jTabbedPane, "plantPrgEdit");
					prgNo=2;
					if(thisClass!=null)
						setReSizeLL(thisClass);// 
					return;
					} else
			//
			if(str.contains("doneButton")){
					System.out.println("適用します");

					layout.first(cardPanel);
					//layout.show(jTabbedPane, "plantAbsProgram");
					prgNo=0;
					if(thisClass!=null)
						setReSizeM(thisClass);//
					return;
			} else 
				if(str.contains("editButton")){
					System.out.println("編集します");
					//
					// 選択されている栽培プログラムを読み込む
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram(selectedFile+".xml");
					//
					PlantPrgEdit pe=(PlantPrgEdit)PanelContiner.plantPrgEdit;// 
					pe.authorPanel.setAutorInfo(selectedFile+".xml");// Author Panel情報セット
					pe.setProgramTitle(pe.prgInfoEdit);// 栽培プログラムのタイトル表示
					pe.setPlantProgramDataToPanel(pe);// パネルに設定値を読み込む
					pe.setEnabled(true);
					//
					layout.last(cardPanel);
					prgNo=2;
					if(thisClass!=null)
						setReSizeLL(thisClass);// 正しく動作しない
				} else 
					if(str.contains("delButton")){
					System.out.println("削除します");
					// 選択されている栽培プログラムを読み込む
					File f=new File(Files.getDBPath()+selectedFile+".xml");
					if(f.exists()==true){
						int ans = JOptionPane.showConfirmDialog(thisClass, "本当に削除しますか?");
						if(ans == JOptionPane.YES_OPTION) {
						f.delete();// ファイルの削除
						// リストを作りなおす
						PlantAbsPanel pap=(PlantAbsPanel)PanelContiner.plantAbsPanel;
						pap.reList();
						
						// プログラムリストの先頭のXMLの情報を表示する
						//Component c=getPlantAbsPanel().getPrgList().getProgramComponents()[0];
						//String r=c.getName();
						//System.out.println("Component "+c);
						//
						}
					}
					
					//layout.last(cardPanel);
					if(thisClass!=null)	
						setReSizeM(thisClass);// 正しく動作しない
					// combでチェックされている項目の削除
					} 
			return;
			}
	}
	
	void setThisClass(PlanterSetting t)
	{
		thisClass=t;
	}



	public static void reList()
	{
		// リストを作りなおす
		PlantAbsPanel pap=(PlantAbsPanel)PanelContiner.plantAbsPanel;
		if(pap!=null)
				pap.reList();
	}

}