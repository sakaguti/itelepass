import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class PlanterSelecter extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		PlanterSelecter gjp=new PlanterSelecter();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public PlanterSelecter() {
		super();
		initialize();
	}
	
	ITPlanterClass itpClass=null;  //  @jve:decl-index=0:
	ArrayList<PlanterClass> planterList=null;  //  @jve:decl-index=0:
	JPanel base=null;
	static JPanel baseS=null;
	JButton selectButton=null;
	
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
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.equals(selectButton)==false) return;
	      JButton btn = (JButton)arg0.getSource();
	      
	      if(btn.equals(selectButton)){
//	    	  System.out.println("addTimeButton");  	  
	      } 
	      
	      message ="私はPlanterSelecter classです。"+btn.getText()+"が押されました。";
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			
	}
	
	private static ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private static String message="";
	private static Observer defaultO=null;  //  @jve:decl-index=0:
	
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	public void update(Observable o, Object arg) {
	String str = (String) arg;
	//
	
	System.out.println("私はPlanterSelecter classです。観察対象の通知を検知したよ。" + str);
	// CameraSelecter class からのCamNoの値を反映する
	
		if(str.contains("CameraNameList")==true){
			// カメラの名前のリストが届いたので、メニュにあるカメラのリストを作り直す。
			str=str.replace("CameraNameList ", "");// remove keyword
			String[] cameraList=str.split(",");
			
			int cameraListSize=cameraList.length;// "私はCameraSelecterClassです。観察対象の通知を検知したよ。 CameraNameList "
System.out.println("PlanterSelecterClass1:  camera no="+cameraListSize);
ITPlanterClass.getCurrentPlanterClass().getInformation().setCameraNumberMax(cameraListSize);
			// set camera number
//			ITPlanterClass.getCurrentPlanterClass().getInformation().setCurrentCameraNumber(0); // default cam no is 0
System.out.println("PlanterSelecterClass: pupUps.size="+pupUps.size());// プランターの数の２倍になっている
			
// DO to All Planters
			if(pupUps!=null && pupUps.size()>0){
				for(int planterNo=0;planterNo < pupUps.size();planterNo++){ // 
System.out.println("PlanterSelecterClass: j="+planterNo+" pupUps.size() "+pupUps.size());
				popup = pupUps.get(planterNo);// プランターの数だけ処理する
				//
//				JMenuItem menuChName = (JMenuItem) popup.getComponent(popup.getComponentCount()-1);
				popup.removeAll();				
				//
				for(int i=0;i<cameraListSize;i++){
System.out.println("PlanterSelecterClass: cameraList"+"["+i+"]="+cameraList[i]);
				JMenuItem menu = new JMenuItem("CAM-"+i);
				// すでにmenuに登録されていたら、二重に登録しない				
				//
					menu.setName("PopUp"+i);
					String s=checkMark+cameraList[i];			
					menu.setText(s);//
					menu.addActionListener(new MyAction());
					popup.add(menu);
					
					System.out.println("PlanterSelecterClass: i="+i+" cameraList.length "+cameraList.length+" planterNo "+planterNo);
					//if(ipn<=planterNo)
					//	ITPlanterClass.getPlanterList().add(new PlanterClass("ITPLANTER-"+planterNo));
					if(i>=cameraList.length) break;
					if(planterNo>=ITPlanterClass.getSystemPlanterNumber()) break;
					// 123
					ITPlanterClass.getPlanterList().get(planterNo).getInformation().setCameraName(i,cameraList[i]);
														
					//　このカメラを登録する
System.out.println("PlanterSelecterClass: "+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(i)+"が登録されました。"
		+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber());						
					}

				
				
				
//					popup.add(menuChName);
				}// next j
				
				cameraListSize=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber();
				System.out.println("PlanterSelecterClass2:  camera no="+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber());
				for(int i=0;i<cameraListSize;i++){
					System.out.println("["+i+"]"+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(i));	
				}
	
			}
		}// CameraNameList
	
	//System.out.println("pupUps.size()="+pupUps.size());
	if(pupUps.size()<=0) return;

	}
	
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		
		//base.setPreferredSize(new Dimension(640,400));
		base.setBackground(new Color(250,251,245));

		baseS=new JPanel();
		baseS.setPreferredSize(new Dimension(640,150));
		baseS.setBackground(new Color(250,251,245));
		baseS.setName("PlanterSelecter");
		baseS.addMouseListener(this);

		
		JScrollPane scrollPane = new JScrollPane(baseS);
		// 縦スクロールバーを表示しない。
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//scrollPane.setPreferredSize(new Dimension(200, 160));
		scrollPane.setBorder(null);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;	
		constraints.gridy = 0;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(scrollPane, constraints);
		
		// ここが全ての始まりになる。
		if(ITPlanterClass.getState()==false){
			new ITPlanterClass();// 一度だけコンストラクターを呼ぶこと。
		}
		
	    int cn=0;
	    String[] camn=Files.getCamNo().split(" ");
	    System.out.println("PlanterSellecrerClass: Files.getCamNo() "+Files.getCamNo());
	    if(camn.length>1){
	    System.out.println("PlanterSellecrerClass: Files.getCamNo() "+camn[1]);
			cn=Integer.parseInt(camn[1].replaceAll("[^0-9]",""));// 
	    } else cn=0;
	    ITPlanterClass.setSystemCameraSize(cn);
	    for(int j=0;j<ITPlanterClass.getPlanterList().size();j++){
	    for(int i=1;i<cn;i++){
	    	ITPlanterClass.getPlanterList().get(j).getInformation().addCameraName("CAM-"+i);
	    }
	    }
	    
	    	
		planterList=ITPlanterClass.getPlanterList();
		//
		if(planterList!=null)
			for(int i=0;i<planterList.size();i++){
					baseS.add( planterCase(i) );
			}
		base.add(scrollPane);
		
		this.add(base);
		this.setBackground(new Color(250,251,245));
		
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlanterSetting.ObserverA();
		observableMan.addObserver(defaultO);
		
		defaultO=new Selecters.ObserverA();
		observableMan.addObserver(defaultO);
		
//		defaultO=new CameraSelecter.ObserverA();
//		observableMan.addObserver(defaultO);
		
	}
	
	private static ArrayList<JPopupMenu> pupUps=new ArrayList<JPopupMenu>();  //  @jve:decl-index=0:
	// menu
	private static JPopupMenu popup =null;
	
	
	public JPanel planterCase(int i)
	{
		PlanterIcon planterIcon=ITPlanterClass.getPlanterList().get(i).getActiveIcon();
		planterIcon.setOpaque(false);
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel planterCase=new JPanel(gridbag);
		planterCase.addMouseListener(this);
		planterCase.setBackground(new Color(250,251,245));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;	
		constraints.gridy = 0;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(planterCase, constraints);
		planterCase.add(planterIcon);
		planterCase.setName("planterCase"+String.valueOf(i));
		
		// PopUp Menue
		popup = new JPopupMenu();

		// 全てのカメラを登録する
		PlanterClass p=ITPlanterClass.getPlanterList().get(i);
		Information inf=p.getInformation();
		for(int c=0;c<inf.getCameraNameList().size();c++){
		JMenuItem menu = new JMenuItem(checkMark+inf.getCameraName(c));
		menu.setName("PopUp"+c);
		menu.addActionListener(new MyAction());
		popup.add(menu);
		}
		pupUps.add(popup);
		
		return planterCase;
	}

	private static String checkMark="✓";
	private static int pointPlanter=0;
	
	static class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {	//メニューが選択されると、このメソッドが呼ばれる
			JMenuItem mi=(JMenuItem)e.getSource();
			int camNo=0;
			String k=mi.getName().replaceAll("[^0-9]","");//PupUp0
			if(k.length()>0) camNo=Integer.parseInt(k);//   PupUp0 ->  0
			
			String s=mi.getText();// CAM-0
					
			PlanterClass p = ITPlanterClass.getPlanterList().get(pointPlanter);			

			if(s.contains(checkMark)){
//				System.out.println("PlanterSelecterClass pointPlanter　"+pointPlanter);
//				System.out.println("PlanterSelecterClass camno1="+p.getInformation().getCameraNumber());// ???

				s=s.replace(checkMark, "");
				mi.setText(s);
				//　このカメラを抹消する
				p.getInformation().changeCameraName(s, "--removed--"+s);
						
//		System.out.println("PlanterSelecterClass1: "+"Planter-"+pointPlanter+" "+p.getInformation().getPlanterName()+"の"+s+"が抹消されました。");
				p.getInformation().printCameras();
			}
			else{
				s=s.replace(checkMark, "");				
				p.getInformation().changeCameraName("--removed--"+s, s);
				s=checkMark+s;
				//　このカメラを登録する
				//s=p.getInformation().getCameraName(camNo);
//		System.out.println("PlanterSelecterClass1 pn=: "+ITPlanterClass.getCurrentPlanterNo());
//		System.out.println("PlanterSelecterClass1: "+p.getInformation().getPlanterName()+"の"+s.replace(checkMark, "")+"["+camNo+"]"+"が登録されました。");
				p.getInformation().printCameras();
				mi.setText(s);
			}

			//
			// カメラの選択結果を知らせる
		    String message="PlanterSelect "+"planter "+p.getInformation().getPlanterName()+" camera "+s;
//    System.out.println("PlanterSelecterClass message　"+message);
//    System.out.println("PlanterSelecterClass camno2="+p.getInformation().getCameraNumber());
		    // 観察者全員に通知
		    observableMan.setMessage(message);
		    observableMan.notifyObservers();
		    
		    //
			//mi.setText(s);
		}
	}
	
	public void reListPlanter()
	{
		baseS.removeAll();
		//ITPlanterClass.reMakePlanterList();
		planterList=ITPlanterClass.getPlanterList();
			for(int i=0;i<planterList.size();i++){
					baseS.add( planterCase(i) );
			}
	}
	
private int selectedNo=-1;//  現在、選択されているプランター番号
private JPanel selectedPanel=null;//  現在、選択されているプランターのパネル

	@Override
	public void mouseClicked(MouseEvent arg0) {
//		System.out.println("Clicked");
		
		if(arg0.getButton()==MouseEvent.BUTTON3) return;// 右ボタンはポップアップの処理に使うので。
		JPanel p=(JPanel)arg0.getSource();
		
		if(p.getName().contains("PlanterSelecter")){
			System.out.println("Re-Search ITPLANTER...");
			
			ITPlanterClass.reSearchPlanter();
			ITPlanterClass.getSystemPlanterNumber();
			
		//	ITPlanterClass.getSystemPlanterSize();
		}

		
			if(selectedPanel!=null) selectedPanel.setBackground(Color.WHITE);

			selectedPanel=(JPanel)arg0.getSource();
			//System.out.println("!!!!!!"+selectedPanel.getName());
			if(selectedPanel.getName().contains("planterCase")){
			//p.setBackground(Color.gray);
			
			
			String g=selectedPanel.getName().replaceAll("[^0-9]","");
			if(g.length()>0){
			selectedNo=Integer.parseInt(g);
			} else selectedNo=0;
			
			message ="私はPlanterSelecter classです。"+"このプランターを選択する"+"　が押されました。 CurrentPlanterNo "+selectedNo;
			
			
			selectedPanel.setBackground(Color.GRAY);
			//
			// カレントプランターを設定
			
			ITPlanterClass.setCurrentPlanterNo(selectedNo);
			ITPlanterClass.setCurrentPlanterClass(ITPlanterClass.getPlanterList().get(selectedNo));

			if(PlanterSetting.sensorPanel!=null){
	    	PlanterSetting.sensorPanel.stopSensorShortTimer(); 
	    	PlanterSetting.sensorPanel.stopSensorLongTimer(); 
	    	
	    	PlanterSetting.sensorPanel.startSensorShortTimer(); 

	    	PlanterSetting.sensorPanel.setSensorLongTime();// Add By SAKAGUTI 2013.1.10
	    	PlanterSetting.sensorPanel.startSensorLongTimer(); 
			}
			// センサーの補正値読込
			ITPlanterClass.getCurrentPlanterClass().getSensor().setComp();
			
			// カレントプランターのシリアル番号の設定
			if(selectedNo>=0) selectedNo += 1;// USBは１から始まるので１を加える 2013/4/18
//				String wr = sendCom.sendcom(selectedNo+" -e Z"); // sendcom			
/*
 * y.sakaguti 20140419
			String wr0 = sendCom.sendcom("1 -e A"); // sendcom
			System.out.println("sendcom("+selectedNo+" -e A):"+wr0);
 */			
			// 何故か、sendcomが動作していない？
				String wr = sendCom.sendcom("1 -e Z"); // sendcom
				System.out.println("sendcom("+selectedNo+" -e Z):"+wr);
				
				String[]  arr=wr.split(System.getProperty("line.separator"));
				// SendComが受けたコマンドが正しいかどうかの検証
				
				if( arr[0].contains("Command: Z") == true){
					System.out.println("selectedNo="+selectedNo+" Sereal="+arr[1]);
					ITPlanterClass.getCurrentPlanterClass().setSerial(arr[1]);// serial number
				} else {
					// 正しくない！
					itp_Logger.logger.info("アイティプランターが応答しません。"+arr[0]+" "+selectedNo+" -e Z");
				}
				
			//
			//
		
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			}
	}
	int CurrentPlanterNoMemo=0;
	@Override
	public void mouseEntered(MouseEvent arg0) {
		JPanel p=(JPanel)arg0.getSource();
		p.setBorder(new BevelBorder(BevelBorder.RAISED));
		// 現在、マウスがあるプランター番号
		
		String ss=p.getName().replaceAll("[^0-9]","");
		if(ss.equals("")==true) return;
		pointPlanter=Integer.valueOf(ss.replaceAll("[^0-9]",""));		
		
		//System.out.println("pointPlanter "+pointPlanter);

		for(int i=0;i<ITPlanterClass.getPlanterList().get(pointPlanter).getInformation().getCameraNameList().size();i++){
		System.out.println(pointPlanter+"["+i+"]"+" "+ITPlanterClass.getPlanterList().get(pointPlanter).getInformation().getCameraNameList().get(i));
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JPanel p=(JPanel)arg0.getSource();
		p.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Lucida Grande", Font.PLAIN, 13), Color.black));
		// recoverd

//		for(int i=0;i<ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNameList().size();i++){
//			System.out.println(i+" "+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNameList().get(i));
//			}

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Windows Only
		
		JPanel pn=(JPanel)arg0.getSource();
		pn.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		
		// 現在、マウスがあるプランター番号
		String ss=pn.getName().replaceAll("[^0-9]","");
		if(ss.equals("")==true) return;
		pointPlanter=Integer.valueOf(ss.replaceAll("[^0-9]",""));		
//		System.out.println("pointPlanter "+pointPlanter);
	
		if(arg0.getButton()==MouseEvent.BUTTON1 && IsMacorWin.isMacOrWin()==false){
			JPanel p=(JPanel)arg0.getSource();			
			//
			if(p.getName().contains("PlanterSelecter")){
				System.out.println("Re-Search ITPLANTER...!!");
				ITPlanterClass.reSearchPlanter();
				ITPlanterClass.getSystemPlanterNumber();
				// remake planterList
				
				// ここでプランター表示を書き換えること
				
				// redraw planterlistpanel
				p.setBackground(new Color(250,251,245));
				return;
			}
			
			if(p.getName().contains("planterCase")){
			//p.setBackground(Color.gray);
			message ="私はPlanterSelecter classです。"+"このプランターを選択する"+"　が押されました。";
			int i=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
			//
			// カレントプランターを設定
			ITPlanterClass.setCurrentPlanterNo(i);
			ITPlanterClass.setCurrentPlanterClass(ITPlanterClass.getPlanterList().get(i));
			// センサーの補正値読込
			// TODO
			// 各プランター毎に補正値を持たなければならない
			ITPlanterClass.getCurrentPlanterClass().getSensor().setComp();
			//
		
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			}
			return;// Clicked
		}
		//  Windows Only End
		
		boolean trigger=arg0.isPopupTrigger();

		if(IsMacorWin.isMacOrWin()==false){
			trigger=!trigger;
		}		
		
		if (trigger) {
			JPanel p=(JPanel)arg0.getSource();
			//System.out.println(p.getName());
			if(p.getName().equals("PlanterSelecter")==false){
			int i=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
			JPopupMenu pop=pupUps.get(i);
			pop.show(arg0.getComponent(), arg0.getX(), arg0.getY());
			}
		}
		if(arg0.getButton()==MouseEvent.BUTTON3) return;// 右ボタンはポップアップの処理に使うので。
		JPanel p=(JPanel)arg0.getSource();
		p.setBorder(new BevelBorder(BevelBorder.LOWERED));
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON3) return;// 右ボタンはポップアップの処理に使うので。
		JPanel p=(JPanel)arg0.getSource();
		p.setBorder(new BevelBorder(BevelBorder.RAISED));
		}
}
