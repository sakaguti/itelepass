

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
			jTabbedPane.add("�Z���T�[",sensorPanel);
			
			//			
			plantAbsPanel=new PlantAbsPanel();// �͔|�v���O�����I��
			//
			plantPrgPanel = new PlantPrgEdit();// �͔|�v���O�����ڍו\��
//			plantPrgPanel.setEnabled(false);// �\�����[�h
			plantPrgPanel.setName("�ڍו\��");
			
			//
			plantPrgEdit  = new PlantPrgEdit();// �͔|�v���O�����C��
			plantPrgEdit.setName("�ҏW");
			selecters  = new Selecters();// �Z���N�^�[
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

		    cardPanel.add(plantAbsPanel,    "plantAbsProgram");// �͔|�v���O�����I��			cardPanel.getComponent(0)
		    cardPanel.add(plantPrgEdit, 	"plantPrgPanel");// �͔|�v���O�����ڍו\��			cardPanel.getComponent(1)
		    cardPanel.add(plantPrgEdit,  	"plantPrgEdit");// �͔|�v���O�����C��   		    cardPanel.getComponent(2)
		    plantPrgPanel=plantPrgEdit;
		    panelContiner=new PanelContiner(plantAbsPanel,plantPrgPanel,plantPrgEdit);

		    //
			jTabbedPane.add("�͔|�v���O����",cardPanel);
			// �L���ł����̋@�\
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
			jTabbedPane.add("�����点���[��",reportPanel);
				}
			}
			//
			// �L���ł����̋@�\
			if(Version.getFreeOrPaid().contains("Paid")==true){
			AutoPictureTab autoPicture=new AutoPictureTab();
//System.out.println("PlanterSettingClass: autoPicture.setState()");
			autoPicture.setState();
			//autoPicture.set
			autoPicture.setBackground(new Color(250,251,245));
			jTabbedPane.add("�����B�e",autoPicture);
			}
			
			//
			jTabbedPane.add("�Z���N�^�[",selecters);
			
			//
		
			option=new Settings();
			option.setBackground(new Color(250,251,245));
			jTabbedPane.add("�ݒ�",option);
			
		    //
		    layout.first(cardPanel);
		    //		    
		    jTabbedPane.setBackground(new Color(250,251,245));
		    
		}
		return jTabbedPane;
	}
	
	//
	// ���������삷��B
	//
	private static int preIndex=0;
	  @Override
	public void stateChanged(ChangeEvent e) {
		    int index = jTabbedPane.getSelectedIndex();
		    
//	    System.out.println("Selected Index �F " + index);
		    int n=ITPlanterClass.getCurrentPlanterNo();
			if(ITPlanterClass.getSystemPlanterSize()>0)
				sendCom.sendcom((n+1)+" -e p");// Auto Mode
			
		    //
		    if(thisClass==null) thisClass=this;
		    
		    //
		    if(index==4){// Selecter Tab
		    //	System.out.println("Panel="+index);
		    	selecters.reListPlanter();// �v�����^�[�𐔂����� 
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
				//jTabbedPane.add("�Z���T�[",bkgProgram);
		    	//bkgProgram.startSensorTimer(); 
		    //
		    }
//		    System.out.println("Selected prgNo �F " + prgNo +" index "+ index);
		    if( index == 0 ){
		    	//
		    	
//		    	System.out.println("Start Sensor Timer");
//	System.out.println("+++++++++++++CP="+(ITPlanterClass.getCurrentPlanterNo()+1));
		    	// ���O�C���ݒ肪�ł��Ă��Ȃ���΁ATimer���J�n���ׂ��ł͂Ȃ��B
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
			jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount()-2); // �Z���N�^�[�̃^�u����n�܂�
		else
			jTabbedPane.setSelectedIndex(0); // �Z���T�[�̃^�u����n�܂�
			
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
	* �ώ@�����l�B
	* 
	*/
	class ObservableMan extends Observable {

	// ���O�ɗ^����ꂽ����
	private String previousArg = "";

	/**
	* �ώ@�҂ɒʒm���܂��B
	* @param �I�u�W�F�N�g
	*/
	@Override
	public void notifyObservers() {
		String str="this";//String.valueOf(editBtn.isSelected());
		
			// ���O�ɗ^����ꂽ�����Ɠ����Ȃ炻�̂܂܃��^�[��
			if (str==previousArg) {
			//return;
			}
		
			previousArg = str;
		
			setChanged();
		
			// �ʒm
			super.notifyObservers(str);
		
			clearChanged();

			}
		}
	
	private static int prgNo=0;
	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	static String selectedFile="";
	static String selectedFileFullPath="";
	
	public static class ObserverA implements Observer {
	/* (�� Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
			String str = (String) arg;
			
			

			System.out.println("����PlanterSetting class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
			
			if(str.contains("doneButton")){
				// ���X�g�����Ȃ���
				PlantAbsPanel pap=(PlantAbsPanel)PanelContiner.plantAbsPanel;
				if(pap==null) return;
				//pap.reList();
				
				System.out.println("�K�p����!!!");
				//
				//	PlantProgram Class�̓��e���A�C�e�B�v�����^�[�ɑ��M����
				//
				
				// �v���O���X�o�[�̕\��
				// ���ۂ̏����̐i�s�Ƃ͓������Ă��Ȃ��B�B�B�B
				Progress progress=new Progress();
				progress.start();
							
				//if(ITPlanterClass.getState()==false) new ITPlanterClass();
				//ITPlanterClass.ITPlanterClassBegin();
				int ic=ITPlanterClass.getCurrentPlanterNo();
				Information pi=ITPlanterClass.getPlanterList().get(ic).getInformation();
				PlantProgram p=pi.getPlantProgram();
				
				p.sendToPlanter(ic);
				
				//
				// �͔|�v���O�������X�g���A���݂͔̍|�v���O�����Ƀt�H�[�J�X����B
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
				// �I������Ă���͔|�v���O������ݒ肷��
				//if(ITPlanterClass.getState()==false) new ITPlanterClass();		
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram(selectedFile+".xml");
//System.out.println(selectedFile+"���I�����ꂽ�B");
			}
			
				
			//
			if(str.contains("���̃v�����^�[��I������")){
/*			
				System.out.println("PlanterSetting: ���̃v�����^�[��I������");
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
			if(str.contains("���̐ݒ��K�p����")){
				//////
				System.out.println("����PlanterSetting class�ł��B ���̐ݒ��K�p����");
				//
				// ����ReportPanel class�ł��B���̐ݒ��K�p���� �{�^����������܂����Btemp true water true illum false
				// �����ŁA�����点���[����ݒ肷��
				
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
				// �͔|�v���O�������X�g���A���݂͔̍|�v���O�����Ƀt�H�[�J�X����B
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
				if(str.contains("�L�����Z��")){
					System.out.println("�L�����Z��");
					if(layout!=null){
					layout.first(cardPanel);
					prgNo=0;
					if(thisClass!=null)
						setReSizeM(thisClass);
					}
					return;
				} else
				if(str.contains("�͔|�v���O�����̏ڍׂ��J��")){
					System.out.println("�K�p�͔|�v���O�����̏ڍׂ��J��");
					if(selectedFile!=""){	
					//PlantPrgEdit p=(PlantPrgEdit) cardPanel.getComponent(1);
					//
					// �I������Ă���͔|�v���O������ǂݍ���
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram(selectedFile+".xml");

					PlantPrgEdit pp=(PlantPrgEdit)PanelContiner.plantPrgPanel;
					pp.setProgramTitle(pp.prgInfoEdit);// �͔|�v���O�����̃^�C�g���\��
					pp.authorPanel.setAutorInfo(selectedFile+".xml");// Author Panel���Z�b�g
					
					pp.setPlantProgramDataToPanel(pp);// �p�l���ɐݒ�l��ǂݍ���
					
					pp.setEnabled(false);
		
					layout.next(cardPanel);
					prgNo=1;
					if(thisClass!=null)
						setReSizeLL(thisClass);
					}
					return;
				} else 
			if(str.contains("�V�����͔|�v���O���������")){
//System.out.println("�V�����͔|�v���O���������");
						//
						//	�V�K�f�[�^��p�ӂ���
						//
						// �@�f�t�H���g�͔̍|�v���O������ǂݍ���
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram("current.xml");
					ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setPlantName("");
					ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setPlantMemo("");
					
					//
					PlantPrgEdit pe=(PlantPrgEdit)PanelContiner.plantPrgEdit;
					
//System.out.println("name "+pe.getName());

					// TODO ��������
					pe.setPlantProgramDataToPanel(pe);// �p�l���ɐݒ�l��ǂݍ���
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
					System.out.println("�K�p���܂�");

					layout.first(cardPanel);
					//layout.show(jTabbedPane, "plantAbsProgram");
					prgNo=0;
					if(thisClass!=null)
						setReSizeM(thisClass);//
					return;
			} else 
				if(str.contains("editButton")){
					System.out.println("�ҏW���܂�");
					//
					// �I������Ă���͔|�v���O������ǂݍ���
					ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram(selectedFile+".xml");
					//
					PlantPrgEdit pe=(PlantPrgEdit)PanelContiner.plantPrgEdit;// 
					pe.authorPanel.setAutorInfo(selectedFile+".xml");// Author Panel���Z�b�g
					pe.setProgramTitle(pe.prgInfoEdit);// �͔|�v���O�����̃^�C�g���\��
					pe.setPlantProgramDataToPanel(pe);// �p�l���ɐݒ�l��ǂݍ���
					pe.setEnabled(true);
					//
					layout.last(cardPanel);
					prgNo=2;
					if(thisClass!=null)
						setReSizeLL(thisClass);// ���������삵�Ȃ�
				} else 
					if(str.contains("delButton")){
					System.out.println("�폜���܂�");
					// �I������Ă���͔|�v���O������ǂݍ���
					File f=new File(Files.getDBPath()+selectedFile+".xml");
					if(f.exists()==true){
						int ans = JOptionPane.showConfirmDialog(thisClass, "�{���ɍ폜���܂���?");
						if(ans == JOptionPane.YES_OPTION) {
						f.delete();// �t�@�C���̍폜
						// ���X�g�����Ȃ���
						PlantAbsPanel pap=(PlantAbsPanel)PanelContiner.plantAbsPanel;
						pap.reList();
						
						// �v���O�������X�g�̐擪��XML�̏���\������
						//Component c=getPlantAbsPanel().getPrgList().getProgramComponents()[0];
						//String r=c.getName();
						//System.out.println("Component "+c);
						//
						}
					}
					
					//layout.last(cardPanel);
					if(thisClass!=null)	
						setReSizeM(thisClass);// ���������삵�Ȃ�
					// comb�Ń`�F�b�N����Ă��鍀�ڂ̍폜
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
		// ���X�g�����Ȃ���
		PlantAbsPanel pap=(PlantAbsPanel)PanelContiner.plantAbsPanel;
		if(pap!=null)
				pap.reList();
	}

}