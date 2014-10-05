import java.awt.CardLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;


public class CameraSelecter extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		CameraSelecter gjp=new CameraSelecter();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public CameraSelecter() {
		super();
		initialize();
	}
	
	JPanel base=null;
	JPanel baseS=null;
	JPanel inpNum=null;
	JComboBox setCamNo=null;
	JButton setButton=null;
	
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
	    //
		//
	      message ="私はCameraSelecter classです。"+"が押されました。";
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			
	}
	
	private ObservableMan observableMan=null;  
	private String message="";
	private Observer defaultO=null;
	
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

	System.out.println("私はCameraSelecter classです。観察対象の通知を検知したよ。" + str);
	
	if(str.contains("PlanterSelect")== true){
		
System.out.println("CameraSelecterClass: PlanterSelect  "+str);		

	// プランター選択からカメラが選択された。
	// Menuのプランターの名前のチェック印を変更する
		String[] para=str.split(" ");
		if(para.length<5) return;
		// 		    String message="PlanterSelect "+"planter "+pointPlanter+" camera "+s;
		String curPlanterName=para[2];
		String curCameraName =para[4];
		int thisPlanterNo=0;
		int thisCameraNo=0;
		PlanterClass p =null;
		//
		// search planter by name
		for(int i=0;i<ITPlanterClass.getPlanterList().size();i++){
		p = ITPlanterClass.getPlanterList().get(i);
		if(p.getInformation().getPlanterName().equals(curPlanterName.replace(checkMark, ""))==true){
			thisPlanterNo=i;
			break;
			}
		}
		
System.out.println("CameraSelecterClass: curPlanterName="+curPlanterName+" thisPlanterNo="+thisPlanterNo);	
		// search camera by name
		PlanterClass itp=ITPlanterClass.getPlanterList().get(thisPlanterNo); // PlanterList has bug	
//		PlanterClass itp=ITPlanterClass.getCurrentPlanterClass();
		int camn=itp.getInformation().getCameraNumber();
		
		for(int iCam=0;iCam<camn;iCam++){
System.out.println("!!!!!! CameraSelecterClass: "+itp.getInformation().getCameraName(iCam)+" camSize "+camn);//camn=0??
		String cn=itp.getInformation().getCameraName(iCam);
		String camname=cn.replace(checkMark, "");
		camname=camname.replace("--remove--", "");
		if(curCameraName.contains(camname)==true){
			thisCameraNo=iCam;
System.out.println("CameraSelecterClass: curCameraName="+curCameraName+" camname "+camname+" thisCameraNo "+thisCameraNo);	
//break;
			}
		}
		
		
System.out.println("CameraSelecterClass: curCameraName="+curCameraName+" thisCameraNo="+thisCameraNo);	
	//
	// curCameraName のメニューを得る
	// メニューの項目の curPlanterName を変更する
// PlanterListの中のoPlanterをnPlanterに入れ替える
		if(pupUps.size()<=0) return;
				popupCam = pupUps.get(thisCameraNo);
				for(int i=0;i<popupCam.getComponentCount();i++){
					JMenuItem menu = (JMenuItem) popupCam.getComponent(i);
					String s=menu.getText();
					
					if(curCameraName.contains(checkMark)==true) curPlanterName=checkMark+curPlanterName.replace(checkMark, "");
System.out.println("CameraSelecterClass: menuText="+s+" +curPlanterName "+curPlanterName+"の"+curCameraName);
					
					if(s.contains(curPlanterName.replace(checkMark, ""))==true){
							menu.setText(curPlanterName);
						if(curCameraName.contains(checkMark)==false){
							// このカメラを抹消する
							p.getInformation().removeCameraName(curCameraName);// add remove tag
							System.out.println("CameraSelecterClass:"+p.getInformation().getCameraName(thisCameraNo)+"の"+curCameraName+"が抹消されました。");
						}
						else{
							//　このカメラを登録する
				//			p.getInformation().setCameraName(thisCameraNo, curCameraName.replace(checkMark, ""));
							p.getInformation().setCameraName(curCameraName.replace(checkMark, ""));// delete remove tag
							System.out.println("CameraSelecterClass:"+curPlanterName.replace(checkMark, "")+"の"+curCameraName+"が登録されました。");
						}
					}
			}// next i
		
		
	//	
	} else
	if(str.contains("PlanterNameChange")== true){
		
		str=str.replace("PlanterNameChange ", "");// remove keyword
		//int mn=Integer.valueOf(str.split(",")[0]); // no
		int mn=Integer.parseInt(str.split(",")[0].replaceAll("[^0-9]",""));//

		String nPlanter=str.split(",")[1];
		if(pupUps.size()<=0) return;
		
		popupCam = pupUps.get(0);
		String ss=str.split(",")[mn];
//		JMenuItem menu = (JMenuItem) popupCam.getComponent(Integer.valueOf(str.split(",")[mn]));
		JMenuItem menu = (JMenuItem) popupCam.getComponent(Integer.parseInt(ss.replaceAll("[^0-9]","")));//

		String oPlanter=menu.getText();// 前のプランターの名前

		// プランターの名前が変更された
			System.out.println("CameraSelecter:"+"Planter name change from "+oPlanter+" to "+nPlanter);

			// PlanterListの中のoPlanterをnPlanterに入れ替える
			if(pupUps.size()<=0) return;
			for(int j=0;j<pupUps.size();j++){
			popupCam = pupUps.get(j);
			for(int i=0;i<popupCam.getComponentCount();i++){
				menu = (JMenuItem) popupCam.getComponent(i);
				String s=menu.getText();
				if(s.contains(oPlanter)==true){
						menu.setText(s.replace(oPlanter, nPlanter));
						s=menu.getText();
					//
					int no=menu.getComponentCount();
System.out.println("CameraSelecter:"+"getComponentCount no="+no+" "+s);
				
					PlanterClass p1 = ITPlanterClass.getPlanterList().get(no);					
					if(s.contains(checkMark)){
						s=s.replace(checkMark, "");
						//　このカメラを抹消する
					//	p1.getInformation().removeCameraName(s.replace(checkMark, ""));
						p1.getInformation().setCameraName(no, "--remove--"+s.replace(checkMark, ""));// add remove tag
						System.out.println("CameraSelecter:"+s.replace(checkMark, "")+"が抹消されました。");
					}
					else{
						s=s.replace(checkMark, "");
						s=checkMark+s;
						//　このカメラを登録する
						p1.getInformation().setCameraName(s.replace(checkMark, ""));
						System.out.println("CameraSelecter:"+s.replace(checkMark, "")+"が登録されました。");
						
					}
					menu.setText(s);
					break;
					}
				}
			}
			//
		}
		}
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private static JPanel 		cardPanel=null;
	private static CardLayout 	layout=null;

	private void initialize() {
		//
		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		//base.setPreferredSize(new Dimension(640,400));
		base.setBackground(new Color(250,251,245));

		
		baseS=new JPanel();
		baseS.setPreferredSize(new Dimension(640,100));
		baseS.setBackground(new Color(250,251,245));
		baseS.addMouseListener(this);// add 0119
		baseS.setName("CameraSelecterBase");
		
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
		
		if(ITPlanterClass.getState()==false ){
			new ITPlanterClass();
		    // if there are no camera entry then DUMMY_CAM should be entry.
			//	String oldCamName="CAM-0";
			//	ITPlanterClass.getPlanterList().get(ITPlanterClass.getCurrentPlanterNo()).getInformation().setCameraName(0, oldCamName);
		}
		
/*		カメラの数が取得できないので変更する。
 
		if(IsMacorWin.isMacOrWin()){
		// 全カメラを表示
			for(int i=0;i<ITPlanterClass.getCurrentPlanterClass().getInformation().getTotalCamera();i++){
					baseS.add( cameraCase( i ) );
			}
		}
 */	
		// 全カメラの数を登録
		ITPlanterClass.setSystemCameraSize(ITPlanterClass.getCurrentPlanterClass().getInformation().getTotalCamera());
//		System.out.println("System cam size="+ITPlanterClass.getSystemCameraSize());

		//
		base.add(scrollPane);
		
		// Card Panel
	    cardPanel = new JPanel();
	    layout = new CardLayout();
	    cardPanel.setLayout(layout);

	    cardPanel.add(base,     "base");// 

	    //
	    inpNum=new JPanel();
	    //
	    JLabel jLabel=new JLabel("このPCに接続されているカメラの台数を入力します。");
	    inpNum.add(jLabel);
	    inpNum.setName("CameraNumInput");
	    inpNum.addMouseListener(this);
	    
	    setCamNo= new JComboBox();
	    // Total Cam selectable no is 16
	    int camno=16;
	    if(Version.getFreeOrPaid().contains("Free")==true) camno=2;
	    for(int i=0;i<camno;i++){
	    	setCamNo.addItem(new Integer(i));
	    }
	    
	    int cn=0;
	    String gcm=Files.getCamNo();
	    /*
	    if(gcm.length()<=0){
	    	//
		    JOptionPane.showMessageDialog(null,"すみませんが、もう一度、起動してください。 "+gcm);
		    System.exit(0);
	    }
	    */
//System.out.println("CameraSellecrerClass: Files.getCamNo() "+gcm);
	    String[] camn=Files.getCamNo().split(" ");
//System.out.println("CameraSellecrerClass: Files.getCamNo() "+camn[1]);
	    
	    if(camn.length>1){
			cn=Integer.parseInt(camn[1].replaceAll("[^0-9]",""));// 
	    } else cn=0;
	    	ITPlanterClass.setSystemCameraSize(cn);

	    setCamNo.setSelectedIndex(cn);
	    setCamNo.addActionListener(new camNoSelectAction());
	    inpNum.add(setCamNo);

	    cardPanel.add(inpNum, 	"inpNum");
	    JButton setButton=new JButton("設定");
	    setButton.addActionListener(new setCamNum());
	    inpNum.add(setButton);

	    /*
	    if(IsMacorWin.isMacOrWin()){
	    	layout.first(cardPanel);
	    } else {
	    	layout.last(cardPanel);
	    }
	    */
	    
	    if(cn==0){
	    	layout.last(cardPanel); // カメラ数の入力
	    } else {
	    	ITPlanterClass.setSystemCameraSize(cn);
				// 全カメラを表示
					for(int i=0;i<cn;i++){
							baseS.add( cameraCase( i ) );
					}
	    	layout.first(cardPanel);
	    }
	    //
	    //
		this.add("栽培プログラム",cardPanel);
		//
		
	//	this.add(base);
		this.setBackground(new Color(250,251,245));
		
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlanterSetting.ObserverA();
		observableMan.addObserver(defaultO);
		
		// デフォルトの観察者を追加
		defaultO=new PlanterSelecter.ObserverA();
		observableMan.addObserver(defaultO);
		
		defaultO=new Selecters.ObserverA();
		observableMan.addObserver(defaultO);
		
	}
	
	private static ArrayList<JPopupMenu> pupUps=new ArrayList<JPopupMenu>();  //  popup menu list of cameras
	private static JPopupMenu popupCam =null; // popup menu of a camera
	
	//
	// Change Camera PopUp Menu Items
	// 
	public void cameraCaseUpdate(int i)
	{		
		// PopUp Menue
		popupCam=pupUps.get(i);
		popupCam.removeAll();
		
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
				
		for(int c=0;c<ITPlanterClass.getPlanterList().size();c++){
			JMenuItem menuitem = new JMenuItem(ITPlanterClass.getPlanterList().get(c).getInformation().getPlanterName());
			popupCam.add(menuitem);
			menuitem.setName("PopUp"+c);
			menuitem.addActionListener(new MyAction());
		}
//		System.out.println("------カメラの名称変更"+i);
		
		// カメラの名称変更タグ
//		JMenuItem menuitem = new JMenuItem("カメラの名称変更"+i);
//		popupCam.add(menuitem);
		
//		menu.setName("PopUpCamNameChange"+String.valueOf(ITPlanterClass.getPlanterList().size()));
		menu.setName("PopUpCamNameChange"+i);
		menu.addActionListener(new MyNameChangeAction());
	}
		
	
	JMenuItem menu =null;
	
	/**
	 * @param i
	 * @return
	 */
	public JPanel cameraCase(int i)
	{
		String s = Path.getPath()+"/images/USBcam.jpg";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\USBcam.jpg";
//		GPanel cameraIcon=new GPanel("/Users/sakaguti/Documents/workspace2/PlantPrg/images/USBcam.jpg");
		
		GPanel cameraIcon=new GPanel(s);
		cameraIcon.setOpaque(false);
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel cameraCase=new JPanel(gridbag);
		cameraCase.addMouseListener(this);
		cameraCase.setBackground(new Color(250,251,245));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;	
		constraints.gridy = 0;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(cameraCase, constraints);
		cameraCase.add(cameraIcon);
		cameraCase.setName("cameraCase"+String.valueOf(i));
		
		// PopUp Menue
		popupCam = new JPopupMenu();

System.out.println("CameraSelecterClass: Planter size="+ITPlanterClass.getPlanterList().size());
		
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		
		// System.out.println("Planter="+ITPlanterClass.get_nPlanter()); // 実際のプランターの数
		// 全てのプランターを登録する
		// 実際のプランター数が０でも、ダミープランターがリストに登録されている。
		/* 
		 * Camera Icon を右クリックするとPopUpメニューを出す。　操作が複雑なので廃止。 130511 y.sakaguti
		for(int c=0;c<ITPlanterClass.getPlanterList().size();c++){
		// デフォルトで選択されている印を付ける
		JMenuItem menu = new JMenuItem(checkMark+ITPlanterClass.getPlanterList().get(c).getInformation().getPlanterName());
		popupCam.add(menu);
		
				menu.setName("PopUp"+c);
		//System.out.println("menu.getName() "+menu.getName());
		menu.addActionListener(new MyAction());
		}
		*/
		
	
		//
		// カメラの名前を変更すると、いろいろ面倒なので、当面、デフォルトのカメラ名を使う
		// カメラの名称変更タグ
		JMenuItem menu = new JMenuItem("カメラの名称変更");
//		popupCam.add(menu);　//  menuに登録しない
		
//		menu.setName("PopUpCamNameChange"+String.valueOf(ITPlanterClass.getPlanterList().size()));
		menu.setName("PopUpCamNameChange"+i);
		menu.addActionListener(new MyNameChangeAction());
		pupUps.add(popupCam);
		
		return cameraCase;
	}

	class MyNameChangeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {	//プランターの名称変更が選択されると、このメソッドが呼ばれる
			//
			
System.out.println("カメラの名称変更が選択");
			JMenuItem mi=(JMenuItem)e.getSource();
			String s=mi.getName();
			
			System.out.println("メニュー　"+s+" "+mi.getName()+" s="+s);
			System.out.println("メニュー No. "+Integer.parseInt(s.replaceAll("[^0-9]","")));
			int icamNo = Integer.parseInt(s.replaceAll("[^0-9]",""));			
			System.out.println("カメラの番号　"+icamNo);

			// 1台のプランターに１つのカメラの名前しか登録されていない？

			
			String oldCamName=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(icamNo);
			
System.out.println("CameraSelecterClass 修正前のカメラの名前　["+icamNo+"] "+oldCamName);
		    String value = JOptionPane.showInputDialog("カメラの名前を入力", oldCamName);

			String newCamName=oldCamName;

		    if(value!=null){
System.out.println("new Cam Name "+icamNo+" "+value);
		    	newCamName=value;
		    	ITPlanterClass.getCurrentPlanterClass().getInformation().setCameraName(icamNo,newCamName);
		    	//
		    	ITPlanterClass.getCurrentPlanterClass().getInformation().changeCameraName(oldCamName, newCamName);// change cam name to new one.
		    	
System.out.println("CameraSelecterClass:All Cam List");
				for( int iCamNo=0;iCamNo<ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber();iCamNo++){
					// リストに登録されたカメラの番号を得る
					String currentCamName=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(iCamNo); // リストに登録されたカメラの名前を得る
System.out.println("CameraSelecterClass: ["+iCamNo+"]"+currentCamName);
				}
				
				
		    	// Save Camera Information
		    	// Planter currentPlanter
		    	//
		    	// Camera yes
		    	// CamNo
		    	// CamName
		    	//
		    	Files.SaveCameraInformation(icamNo,value);
		    }

System.out.println("CameraSelecterClass 修正後のカメラの名前　"+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(icamNo));
		    message="CameraNameChange "+icamNo+","+newCamName;
System.out.println("CameraSelecterClass message　"+message);

			// 観察者全員に通知
			observableMan.setMessage(message);
			observableMan.notifyObservers();
		}
	}
	
//	カメラの総数を決定する
	class setCamNum implements ActionListener {
		public setCamNum() {
			//
		}

	//	@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {	//設定ボタンが押されると、このメソッドが呼ばれる		
			// remake 
			if(ITPlanterClass.getState()==false) new ITPlanterClass();
			message="CameraNameList ";
			// カメラの台数が分かったので、カメラの名前を入れなおす
			Files.setCamNo(ITPlanterClass.getSystemPlanterSize());
			
			// set camera shedule
			String s=Files.getAutoPicture();
			if(s.contains("periodic")==true){
				AutoPicture.setPeriodic(); 
			}
			if(s.contains("const")==true){
				AutoPicture.setConstTime();	
				}
			if(s.contains("non")==true){
				AutoPicture.setNon();
			}

			
			for(int j=0;j<ITPlanterClass.getSystemPlanterSize();j++){// All planters
				PlanterClass p=ITPlanterClass.getPlanterList().get(j);
				Information inf=p.getInformation();
				inf.removeAllCameraName();
				System.out.println("CameraSelecter: getSystemCameraSize="+ITPlanterClass.getSystemCameraSize());
				baseS.removeAll();// 追加登録されるので、登録を消してから作り直す。
			for(int i=0;i<ITPlanterClass.getSystemCameraSize();i++){
				baseS.add( cameraCase( i ) );// Add Camera Icon Button
				// save camera name
				String cname="CAM-"+i;// default camera name
//System.out.println("CameraSelecter: setCameraName1　　i="+i+" "+cname);
				inf.addCameraName(cname);
				cname = inf.getCameraName(i);
//System.out.println("CameraSelecter: "+p.getInformation().getPlanterName()+" "+"setCameraName　　i="+i+" "+cname);
				if(j==0){
				message += cname;
			    if(i<ITPlanterClass.getSystemCameraSize()-1) message +=",";
					}
				}// next i
			}// next j
//System.out.println("CameraSelecter: setCameraNumber　　i="+ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraNumber());
			
//System.out.println("CameraSelecter: pupUps.size()="+pupUps.size());
			// Search Cammera
			for(int j=0;j<pupUps.size()/2-1;j++) {
					popupCam=pupUps.get(j);
//System.out.println("CameraSelecter: popupCam.getComponentCount()="+popupCam.getComponentCount());
			if(popupCam.getComponentCount()<1) break;
				}// next j
			//
//			System.out.println("!!cam="+ITPlanterClass.getPlanterList().get(0).getCameraSize());
			


System.out.println("message="+message);
			// カメラの台数分のカメラの名前をプランターメニューに登録してもらう
			// 観察者全員に通知
			observableMan.setMessage(message);
			observableMan.notifyObservers();
			
				//
				message="私はCameraSelecter classです。　CamNo "+ITPlanterClass.getSystemCameraSize();
				// 観察者全員に通知
				observableMan.setMessage(message);
				observableMan.notifyObservers();
		    layout.first(cardPanel);
		}
	}
	
	private static String checkMark="✓";
	private static int pointCamera=-1;

	class MyAction implements ActionListener {
		public MyAction() {
		}

		@Override
		public void actionPerformed(ActionEvent e) {	//メニューが選択されると、このメソッドが呼ばれる
			JMenuItem mi=(JMenuItem)e.getSource();
						
			// Search Cammera
			for(int i=0;i<pupUps.size();i++) {
				if(pupUps.get(i)==mi.getParent()){
					pointCamera=i;
				}
			}
			
			String s=mi.getText();// Planter Name
		
			//pointCamera=Integer.parseInt(mi.getName().replaceAll("[^0-9]",""));
			//
/*
System.out.println("CameraSelecterClass: pointCamera="+pointCamera);
System.out.println("CameraSelecterClass: Text="+s);
System.out.println("CameraSelecterClass: Name="+mi.getName());
*/
			int thisplanterNo=0;	
			if(ITPlanterClass.getPlanterList()==null) new ITPlanterClass();
			//
			// search Camera by name
			PlanterClass p =null;
			for(int i=0;i<ITPlanterClass.getPlanterList().size();i++){
			p = ITPlanterClass.getPlanterList().get(i);
			if(p.getInformation().getPlanterName().equals(s)==true) thisplanterNo=i;
System.out.println("CameraSelecterClass: "+i+" PlanterNo="+thisplanterNo+" planterName="+p.getInformation().getPlanterName());		
			}
			
			String camName=p.getInformation().getCameraName(pointCamera);
System.out.println("CameraSelecterClass: camName="+camName);
		
			if(s.contains(checkMark)){
				s=s.replace(checkMark, "");
				//camName=s; // 
				//　このカメラをプランターから抹消する
				//camName=s.replace(checkMark, "");
//				camName=p.getInformation().getCameraName(pointCamera);				
				p.getInformation().removeCameraName(camName);
//				p.getInformation().setCameraName(camName);// add remove tag
System.out.println("CameraSelecterClass: MenuText="+s);

System.out.println("CameraSelecterClass: プランター"+p.getInformation().getPlanterName()+"のカメラ|"+camName+"|が抹消されました。");
			}
			else{
				s=checkMark+s;// PlanterName with check label
				//camName=s.replace(checkMark, "");
//				camName=p.getInformation().getCameraName(pointCamera);
				camName=camName.replace("--remove--", ""); // del remove tag
//System.out.println("プランター "+s.replace(checkMark, "")+"にカメラ|"+camName+"|を登録します。");
				//　このカメラをプランターに登録する
				p.getInformation().setCameraName(camName);
System.out.println("CameraSelecterClass: プランター "+p.getInformation().getPlanterName()+"にカメラ|"+camName+"|が登録されました。");
			}
			
			// カメラの選択結果を知らせる
		    message="CameraSelect planter "+s+" camera "+camName;
System.out.println("CameraSelecterClass message　"+message);
		    // 観察者全員に通知
		    observableMan.setMessage(message);
		    observableMan.notifyObservers();
		    
			mi.setText(s);


		}
	}


	public class camNoSelectAction implements ActionListener{// set cam no by popup menu
		public camNoSelectAction() {
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
//			System.out.println(cb.getSelectedItem());
			int n=(Integer)cb.getSelectedItem();
			Files.setCamNo(n);
			Files.savePlantersSettings();
//System.out.println("setCameraNumber="+n);
			ITPlanterClass.setSystemCameraSize(n);
			}
		}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		   if(arg0.getButton()==MouseEvent.BUTTON3) return;// 右ボタンはポップアップの処理に使うので。
			JPanel p=(JPanel)arg0.getSource();
		
			if(p.getName().contains("CameraSelecterBase")){
				//System.out.println("Base clicked");
				layout.last(cardPanel);
			}
			
			if(p.getName().contains("CameraNumInput")){
				//System.out.println("Base clicked");
				layout.first(cardPanel);
			}
			
			
			if(p.getName().contains("cameraCase")){
			//p.setBackground(Color.gray);

			message ="私はCameraSelecter classです。"+"このカメラを選択する"+"　が押されました。";
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			//
			//
			// Macの時にモニター画面がでない
			//
			/*
			if(IsMacorWin.isMacOrWin()==true){
			int c=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
			System.out.println("cam="+c);
			CameraCapture.monitorCam(c);// このカメラの画像を表示する
				}
				*/
			}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JPanel p=(JPanel)arg0.getSource();
		if(p.getName().contains("cameraCase")){
		p.setBorder(new BevelBorder(BevelBorder.RAISED));
		// 現在、マウスがあるプランター番号
		pointCamera=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JPanel p=(JPanel)arg0.getSource();
		if(p.getName().contains("cameraCase")){
		p.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Lucida Grande", Font.PLAIN, 13), Color.black));
		}
		}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// WIndows Only
		JPanel p=(JPanel)arg0.getSource();
		if(p.getName().contains("cameraCase")==false) return;
		
//		if(arg0.getButton()==MouseEvent.BUTTON1 && IsMacorWin.isMacOrWin()==false){
			//p.setBackground(Color.gray);

			message ="私はCameraSelecter classです。"+"このカメラを選択する"+"　が押されました。";
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			//
			int c=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
//			System.out.println("cam="+c);
			CameraCapture.monitorCam(c);// このカメラの画像を表示する
		// 
		//	ITPlanterClass.setSystemCameraSize(c);// システムにあるカメラの個数を登録
		//	PlanterClass itpc=ITPlanterClass.getPlanterList().get(ITPlanterClass.getCurrentPlanterNo());
/*			
		} else {
			// Mac
			message ="私はCameraSelecter classです。"+"このカメラを選択する"+"　が押されました。";
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			//
			int c=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
			System.out.println("CameraCapture.monitorCam cam="+c);
			CameraCapture.monitorCam(c);// このカメラの画像を表示する
		}
*/
		boolean trigger=arg0.isPopupTrigger();
		
		if(arg0.getButton()!=MouseEvent.BUTTON1 && IsMacorWin.isMacOrWin()==false){
			trigger=!trigger;
		}
		
		if (trigger) {
			int i=Integer.parseInt(p.getName().replaceAll("[^0-9]",""));
			JPopupMenu pop=pupUps.get(i);
			pop.show(arg0.getComponent(), arg0.getX(), arg0.getY());
		}
		
		if(arg0.getButton()==MouseEvent.BUTTON3) return;// 右ボタンはポップアップの処理に使うので。
		p.setBorder(new BevelBorder(BevelBorder.LOWERED));
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON3) return;// 右ボタンはポップアップの処理に使うので。
		JPanel p=(JPanel)arg0.getSource();
		p.setBorder(new BevelBorder(BevelBorder.RAISED));
		}

	public int getCamNo() {
		
		return 0;
	}
}
