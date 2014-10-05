import info.bix.tokai.bixpp.binding.BIXpp;
import info.bix.tokai.bixpp.binding.Cultivation;
import info.bix.tokai.bixpp.binding.Plant;
import info.bix.tokai.bixpp.io.BIXppIO;
import info.bix.tokai.bixpp.xml.ValidationException;
import info.bix.tokai.bixpp.xml.XMLException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class PlantPrgEdit extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		PlantPrgEdit gjp=new PlantPrgEdit();
		
		//gjp.setEnabled(false);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public PlantPrgEdit() {
		super();
		initialize();
	}

	public void setProgramTitle(PrgInfoEdit prgInfoEdit)
	{
		BIXpp bixpp=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().getBIXpp();
		if(bixpp!=null){
		if(bixpp.getCultivationCount()>0){
		Cultivation c=bixpp.getCultivation(0);
		if(c!=null){
		Plant p=c.getPlant();
		String plantName=p.getName();//
		ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().getBIXppFileName();
		setProgramTitles(plantName);// Display Program file name [.xml]
		prgInfoEdit.setText(c.getComment());// �R�����g�̕\��
		}
		}
		}
	}
	public static boolean state=true;
	
	@Override
	public void setEnabled(boolean b)
	{
		prgInfoEdit.setEnabled(b);
		tempPanel1.setEnabled(b);
		pumpPanel1.setEnabled(b);
		pumpPanel2.setEnabled(b);
		illumPane1.setEnabled(b);
		illumPane2.setEnabled(b);
		illumPane3.setEnabled(b);
		if(b==false){ 
			doneBtn.setText("�߂�"); 
			cancelBtn.setVisible(false);
			cancelBtn.setEnabled(false);
		} else {
			doneBtn.setText("���̐ݒ��K�p����");
			cancelBtn.setVisible(true);
			cancelBtn.setEnabled(true);
		}
		doneBtn.setBackground(new Color(250,251,245));
//		doneBtn.setBorder(null);
		state=b;
	}
	
	public  PrgInfoEdit prgInfoEdit=null;
	//public  static PrgInfoEdit prgInfoEdits=null;
	public  TempPanelEdit1 tempPanel1=null;
	public  AuthorPanel	   authorPanel=null;
	public  PumpPanelEdit2 pumpPanel2=null;
	//public static PumpPanelEdit2 pumpSchedulePanel=null;
	public PumpPanelEdit1 pumpPanel1=null;
	public  illumPanelEdit1 illumPane1=null;
	public illumPanelEdit2 illumPane2=null;
	//private static illumPanelEdit2 dutySchedulePanel=null;
	public  illumPanelEdit3 illumPane3=null;
	//private static illumPanelEdit3 lampSchedulePanel=null;
	private static JButton doneBtn=null;
	private static JPanel base=null;
	private static boolean status=false;
	
	private static PlantPrgEdit parentClass=null;
	
	private void setParentClass(PlantPrgEdit p)
	{
		parentClass=p;
	}
	public static PlantPrgEdit getParentClass()
	{
		return parentClass;
	}

	public boolean getStatus()
	{
		return status;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  RoundLabelTextPane roundLabelText=null;
	private RoundLabelTextPane roundLabelTexts=null;
	private JButton cancelBtn;
	
	public void setProgramTitles(String s)
	{
		roundLabelText.setText(s);
	}
	
	public void saveAuthorPanel()
	{	
        //
		/*
		authorPanel.getAuthor();
		authorPanel.getDate();
		authorPanel.getUpDate();
		*/
		//
	}
	
	public void setAuthorPanel()
	{
		String bixppFilename=ITPlanterClass.getCurrentPlanterClass().getInformation().getBIXppFileName();
        File file=new File(bixppFilename);
        if(file.exists()==false) return;
        
        BIXpp bixpp=null;
        
		try {
			bixpp = BIXppIO.read(new File(bixppFilename));
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //
		authorPanel.setAuthor(bixpp.getProducer().getName());
		authorPanel.setDate(String.valueOf(bixpp.getStart()));
		authorPanel.setUpDate(String.valueOf(bixpp.getUpdate()));
		//
	}
	
	private void initialize() {
		int vsepheight=500;
		int vsepwidth=600;
		
		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		
		base.setBackground(new Color(250,251,245));
		
		GridBagConstraints constraints1 = new GridBagConstraints();
		
		//
		roundLabelText=new RoundLabelTextPane("Program");
		roundLabelTexts=roundLabelText;
		//roundLabelText.setPreferredSize(new Dimension(600,20));
//		roundLabelText.setSize(new Dimension(600,20));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 5;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(roundLabelText, constraints);
		base.add(roundLabelText);
		
		//
		prgInfoEdit=new PrgInfoEdit();
		//prgInfoEdit.setPreferredSize(new Dimension(600,80));
//		prgInfoEdit.setSize(new Dimension(600,80));
		//prgInfoEdits=prgInfoEdit;// static
		
		constraints = new GridBagConstraints();
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth= 6;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(prgInfoEdit, constraints);
		
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		BIXpp bixpp=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().getBIXpp();
		if(bixpp!=null){
		if(bixpp.getCultivationCount()>0){
		Cultivation c=bixpp.getCultivation(0);
		if(c!=null){
		Plant p=c.getPlant();
		String t=p.getName();//
		prgInfoEdit.setText(t);
		}
		}
		}
		
		prgInfoEdit.setEnabled(true);
		
		base.add(prgInfoEdit);
		//
		//
		// separator
		GridBagConstraints constraintsSP = new GridBagConstraints();
		constraintsSP.gridx = 0;
		constraintsSP.gridy = 2;
		constraintsSP.gridwidth= 5;
		constraintsSP.gridheight = 1;
		constraintsSP.insets = new Insets(0, 0, 0, 0);

		JSeparator vspr=new JSeparator(SwingConstants.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(vsepwidth, 20));
		gridbag.setConstraints(vspr, constraintsSP);
		base.add(vspr);
		
	
		// ���x�@panel
		tempPanel1=new TempPanelEdit1();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 0;
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 2;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(tempPanel1, constraints1);
		//tempPanel.setPreferredSize(new Dimension(200,700));
		base.add(tempPanel1);
		
		// Author�@panel
		authorPanel=new AuthorPanel("current.xml");
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 0;
		constraints1.gridy = 5;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(authorPanel, constraints1);
		//tempPanel.setPreferredSize(new Dimension(200,700));
		base.add(authorPanel);

		//
		/*
		JButton doneBtn=new JButton("���̐ݒ��K�p����");
		doneBtn.setBackground(new Color(250,251,245));
		doneBtn.setBorder(null);
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 0;
		constraints1.gridy = 6;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(doneBtn, constraints1);
	//	doneBtn.setPreferredSize(new Dimension(200,12));
		doneBtn.addActionListener(this);
	//	base.setSize(640, 600);
	//	base.setPreferredSize(new Dimension(600,600));
		base.add(doneBtn);
		*/
		
		
		// separator
		JSeparator vsprVR=new JSeparator(SwingConstants.VERTICAL);
		vsprVR.setPreferredSize(new Dimension(10, vsepheight));
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 1;
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 5;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vsprVR, constraints1);
		base.add(vsprVR);

		
		// �|���v�@panel
		pumpPanel1=new PumpPanelEdit1();
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 2;
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 2;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(pumpPanel1, constraints1);
		//pumpPanel.setPreferredSize(new Dimension(200,700));
		base.add(pumpPanel1);
		
		// Spacer
		JPanel spacer1=new JPanel();
		spacer1.setBackground(new Color(250,251,245));
		spacer1.setBorder(null);
		spacer1.setSize(50,30);
		GridBagConstraints constraintsS1 = new GridBagConstraints();
		constraintsS1.anchor=GridBagConstraints.NORTH;
		constraintsS1.gridx = 2;
		constraintsS1.gridy = 4;	
		constraintsS1.gridwidth= 1;
		constraintsS1.gridheight = 2;
		constraintsS1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(spacer1, constraintsS1);
		base.add(spacer1);
		
		// �|���v�@panel
		pumpPanel2=new PumpPanelEdit2();
		//pumpSchedulePanel=pumpPanel2;
		GridBagConstraints constraints21 = new GridBagConstraints();
		constraints21.anchor=GridBagConstraints.NORTH;
		constraints21.gridx = 2;
		constraints21.gridy = 7;	
		constraints21.gridwidth= 1;
		constraints21.gridheight = 1;
		constraints21.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(pumpPanel2, constraints21);
		//pumpPanel2.setPreferredSize(new Dimension(200,700));
		base.add(pumpPanel2);
		

		// separator
		JSeparator vspr2=new JSeparator(SwingConstants.VERTICAL);
		vspr2.setPreferredSize(new Dimension(10, vsepheight));
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 3;
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 5;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr2, constraints1);
		base.add(vspr2);


		/////
		// �Ɠx�@panel
		illumPane1=new illumPanelEdit1();
		// Observer�̐ݒ�
		illumPane1.setObserver(new PlantPrgEdit.ObserverA());
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 4;
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumPane1, constraints1);
//		illumPane1.setPreferredSize(new Dimension(200,470));
		base.add(illumPane1);
		
		// Spacer
		JPanel spacer2=new JPanel();
		spacer2.setBackground(new Color(250,251,245));
		spacer2.setBorder(null);
		spacer2.setSize(50,30);
		GridBagConstraints constraintsS2 = new GridBagConstraints();
		constraintsS2.anchor=GridBagConstraints.NORTH;
		constraintsS2.gridx = 4;
		constraintsS2.gridy = 4;	
		constraintsS2.gridwidth= 1;
		constraintsS2.gridheight = 1;
		constraintsS2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(spacer1, constraintsS2);
		base.add(spacer2);
		
		// �Ɠx�@panel
		illumPane2=new illumPanelEdit2();
		//dutySchedulePanel=illumPane2;
		// Observer�̐ݒ�
		illumPane2.setObserver(new PlantPrgEdit.ObserverA());
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 4;
		constraints1.gridy = 5;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumPane2, constraints1);
		//illumPane2.setPreferredSize(new Dimension(200,800));
		base.add(illumPane2);
		
		// Spacer
		JPanel spacer3=new JPanel();
		spacer3.setBackground(new Color(250,251,245));
		spacer3.setBorder(null);
		//spacer3.setSize(50,200);
		GridBagConstraints constraintsS3 = new GridBagConstraints();
		constraintsS3.anchor=GridBagConstraints.NORTH;
		constraintsS3.gridx = 4;
		constraintsS3.gridy = 6;	
		constraintsS3.gridwidth= 1;
		constraintsS3.gridheight = 1;
		constraintsS3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(spacer3, constraintsS3);
		base.add(spacer3);
		
		// �Ɠx�@panel
		illumPane3=new illumPanelEdit3();
		//lampSchedulePanel=illumPane3;
		// Observer�̐ݒ�
		illumPane3.setObserver(new PlantPrgEdit.ObserverA());
		constraints1 = new GridBagConstraints();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 4;
		constraints1.gridy = 7;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumPane3, constraints1);
		//illumPane2.setPreferredSize(new Dimension(200,800));
		base.add(illumPane3);
	
		// separator
		GridBagConstraints constraintsSP3 = new GridBagConstraints();
		constraintsSP3.gridx = 0;
		constraintsSP3.gridy = 8;
		constraintsSP3.gridwidth= 5;
		constraintsSP3.gridheight = 1;
		constraintsSP3.insets = new Insets(0, 0, 0, 0);

		JSeparator vspr3=new JSeparator(SwingConstants.HORIZONTAL);
		vspr3.setPreferredSize(new Dimension(vsepwidth, 20));
		gridbag.setConstraints(vspr3, constraintsSP3);
		base.add(vspr3);
		
		//		
		doneBtn=new JButton("���̐ݒ��K�p����");
		doneBtn.setBackground(new Color(250,251,245));
	//	doneBtn.setBorder(null);
		GridBagConstraints constraints10 = new GridBagConstraints();
		constraints10.anchor=GridBagConstraints.NORTH;
		constraints10.gridx = 0;
		constraints10.gridy = 6;	
		constraints10.gridwidth= 1;
		constraints10.gridheight = 1;
		constraints10.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(doneBtn, constraints10);
	//	doneBtn.setPreferredSize(new Dimension(200,12));
		doneBtn.addActionListener(this);
		//	base.setPreferredSize(new Dimension(600,600));
		base.add(doneBtn);
		
		//		
		cancelBtn=new JButton("�L�����Z��");
		cancelBtn.setBackground(new Color(250,251,245));
		GridBagConstraints constraints12 = new GridBagConstraints();
		constraints12.anchor=GridBagConstraints.NORTH;
		constraints12.gridx = 0;
		constraints12.gridy = 7;	
		constraints12.gridwidth= 1;
		constraints12.gridheight = 1;
		constraints12.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(cancelBtn, constraints12);
	//	doneBtn.setPreferredSize(new Dimension(200,12));
		cancelBtn.addActionListener(this);
		//	base.setPreferredSize(new Dimension(600,600));
		base.add(cancelBtn);
		
		base.setSize(640, 600);

		
		setParentClass(this);
		
		this.add(base);
		this.setBackground(new Color(250,251,245));
		this.setSize(640, 600);
//		this.setPreferredSize(new Dimension(640,600));
		
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();	
		
//		observableMan.addObserver(new PlanterSetting.ObserverA());
		
		status=true;
	}
	 
	//�@
	// �ݒ肳�ꂽ�p�����[�^��PlantProgram class�ɕۑ�����
	public void getPlantProgramDataFromPanel(PlantPrgEdit prgEdit)
	{
		//
		//	�ݒ肳�ꂽ�p�����[�^��PlantProgram class�ɕۑ�����
		//
		PlantProgram plantProgram=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram();
		
		if(plantProgram==null){
			ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram("current.xml");
			plantProgram=ITPlanterClass.getPlanterList().get(ITPlanterClass.getCurrentPlanterNo()).getInformation().getPlantProgram();
		}
		
		//�@����ɕۑ����ׂ�����
		// ToDo
		saveAuthorPanel();
		
		// �c�̎��
		String plantCategory=prgInfoEdit.getSelectedItem();
		plantProgram.setPlantCategory(plantCategory);
		// ���e�v���O����
		String programCategory=prgInfoEdit.getCheckStatus();
		plantProgram.setProgramCategory(programCategory);
		// Program��
		String prgName=roundLabelTexts.getText();
		plantProgram.setProgramName(prgName);
		// �R�����g���̓��e
		String prgMemo=prgInfoEdit.getText();
		plantProgram.setProgramMemo(prgMemo);
		
		
		// 2013/04/12 Add by SAKAGUCHI
		// �x�����x�̕ۑ�
		double wt=prgEdit.tempPanel1.getValue();
		System.out.println("TempWarn="+wt);
		plantProgram.setTempWarn(wt);
		
		// �x�����ʂ̕ۑ�
		double ww=prgEdit.pumpPanel1.getWaterWarn();
		plantProgram.setWaterWarn(ww);
		
		// �x�������Ɠx�̕ۑ�
		double wi=prgEdit.illumPane1.getValue();
		System.out.println("wi="+wi);
		plantProgram.setIllumWarn(wi);
		
		// �|���v���쎞�Ԃ̕ۑ�
		double pw=prgEdit.pumpPanel1.getPumpWorkTime();// pumpWorkTime
		plantProgram.setPumpWrokTime(pw);
		// --- 
		
		
		// Pump
		plantProgram.removeAllPumpSchedule();
		String s1=pumpPanel2.getTimeTable();
		String[] t1=s1.split(" ");// start end
		if(t1==null) return;

		if(t1.length>0 && t1[0]!=""){
		for(int i=0;i<t1.length;i++){
			Schedule sc1=new Schedule();
			sc1.setStartTime(t1[i]);
			// t= hh:mm
			String[] hm=t1[i].split(":");// hh:mm
//			int h=Integer.valueOf(hm[0]);// hour
			int h=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
//			int m=(int) (Integer.valueOf(hm[1])+(prgEdit.pumpPanel1.getPumpWorkTime()));// min
			int m=(int) (Integer.parseInt(hm[1].replaceAll("[^0-9]",""))+(prgEdit.pumpPanel1.getPumpWorkTime()));// min
			if(m>=60){
				h++;
				if(h>=24) h-=24;
				m-=60;
			}		
			sc1.setEndTime(h+":"+m);
			sc1.setContinueTime(String.valueOf(plantProgram.getPumpWrokTime()));// 
			plantProgram.addPumpSchedule(sc1);
			}
		}
		
		// Duty
		plantProgram.removeAllDutySchedule();
		String s2=illumPane2.getTimeTable();
		String[] t2=s2.split(" ");
		if(t2==null) return;
		if(t2.length>0 && t2[0]!=""){
		for(int i=0;i<t2.length;i+=2){
			Schedule sc2=new Schedule();
			sc2.setValue(t2[i]);
			sc2.setStartTime(t2[i+1]);
			plantProgram.addDutySchedule(sc2);
			}
		}
		
		// LED lamp
		plantProgram.removeAllLampSchedule();
		String s3=illumPane3.getTimeTable();
		String[] t3=s3.split(" ");
		if(t3==null) return;
		if(t3.length>0 && t2[0]!=""){
		for(int i=0;i<t3.length;i+=2){
			Schedule sc3=new Schedule();
			sc3.setStartTime(t3[i]);
			sc3.setEndTime(t3[i+1]);
			plantProgram.addLampSchedule(sc3);
			}
		}
	
	}
	
	// TODO
	// PlantProgram�̓��e��PlantPrgEdit class�ɐݒ肷��
	//
	public void setPlantProgramDataToPanel(PlantPrgEdit prgEdit)
	{
		//
		//	�ݒ肳�ꂽ�p�����[�^��ۑ�����
		//
		PlantProgram plantProgram=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram();
		
		if(plantProgram==null){
			ITPlanterClass.getCurrentPlanterClass().getInformation().setPlantProgram("current.xml");
			plantProgram=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram();
		}
		
		//�@����ɕۑ����ׂ�����
		
		// �c�̎��
		String plantCategory=prgEdit.prgInfoEdit.getSelectedItem();
		plantProgram.setPlantCategory(plantCategory);
		// ���e�v���O����
		String programCategory=prgEdit.prgInfoEdit.getCheckStatus();
		plantProgram.setProgramCategory(programCategory);
		// Program��
		String prgName=prgEdit.roundLabelTexts.getText();
		plantProgram.setProgramName(prgName);
		// �R�����g���̓��e
		String prgMemo=prgInfoEdit.getText();
		plantProgram.setProgramMemo(prgMemo);
		
		prgEdit.pumpPanel2.setParentClass(prgEdit.pumpPanel2);
		prgEdit.pumpPanel2.deleteAllSchedule();
//		ts1=prgEdit.pumpPanel2.timeTable.size();
//		if(ts1!=0)		
//			System.out.println("pumpPanel2 timeTable.size() "+ts1);

		// 2013/04/11 Add by SAKAGUCHI
		// �x�����x�̕\��
		double wt=plantProgram.getTempWarn();
		int iwt=(int)(wt);
		if(iwt<0) iwt=0;
		
		System.out.println("TempWarn="+iwt);
		//iwt=(iwt-1);
		System.out.println("iwt="+iwt);
		//prgEdit.pumpPanel1.setParentClass(prgEdit.pumpPanel1);
		prgEdit.tempPanel1.setValue(iwt);

		// �x�����ʂ̕\��
		double ww=plantProgram.getWaterWarn();
		prgEdit.pumpPanel1.setWaterWarn(ww);
		
		// �x�������Ɠx�̕\��
		double wi=plantProgram.getIllumWarn();
		int iwi=(int)wi;
		System.out.println("iwi="+iwi);
		prgEdit.illumPane1.setValue(iwi);
		
		// �|���v���쎞�Ԃ̕\��
		double pw=plantProgram.getPumpWrokTime();
		prgEdit.pumpPanel1.setPumpWorkTime(pw);// pumpWorkTime
		// --- 
		
		// �|���v�X�P�W���[��
		int ts3=plantProgram.getPumpScheduleSize();		
		for(int i=0;i<ts3;i++){
			Schedule s=plantProgram.getPumpSchedule(i);
			double pwt=prgEdit.pumpPanel1.getPumpWorkTime();
			String continueTime=String.valueOf((int)(pwt/60))+":"+String.valueOf((int)(pwt%60));
			s.setContinueTime(continueTime);
			// Pumpchedule�̃X�P�W���[�����p�l���ɒǉ�����B
			prgEdit.pumpPanel2.addSchedule(s);
		}
		

		// Duty		
		prgEdit.illumPane2.setParentClass(prgEdit.illumPane2);
		prgEdit.illumPane2.deleteAllSchedule();// �p�l���̑S�ẴX�P�W���[�����N���A����
		ts3=plantProgram.getDutyScheduleSize();
		for(int i=0;i<ts3;i++){
			Schedule s=plantProgram.getDutySchedule(i);
			// Dutychedule�̃X�P�W���[�����p�l���ɒǉ�����B
			prgEdit.illumPane2.addSchedule(s);
		}

		
		// LED lamp
		prgEdit.illumPane3.setParentClass(prgEdit.illumPane3);
		prgEdit.illumPane3.deleteAllSchedule();// �p�l���̑S�ẴX�P�W���[�����N���A����
		
		ts3=plantProgram.getLampScheduleSize();
		for(int i=0;i<ts3;i++){
			Schedule s=plantProgram.getLampSchedule(i);
			// Lampchedule�̃X�P�W���[�����p�l���ɒǉ�����B
			prgEdit.illumPane3.addSchedule(s);
		}	
	}
	
	void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}
	
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";
	
	public class selectAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//	acceptBtn.setEnabled(true);
			//	���̐ݒ��K�p����B
			JComboBox cb = (JComboBox)arg0.getSource();

			// Get the new item
			// System.out.println(cb.getSelectedItem());
			//
			message = "����PlantPrgEdit class�ł��B"+cb.getName()+" "+cb.getSelectedItem();
			observableMan.setMessage(message);
			// �ώ@�ґS���ɒʒm
			observableMan.notifyObservers();
		}
	}
	

	public class acceptAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			message = "����PlantEdit class�ł��B";
			observableMan.setMessage(message);
			// �ώ@�ґS���ɒʒm
			observableMan.notifyObservers();// �����͂Ƃ�Ȃ�
		}
	}

	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	public static class ObserverA implements Observer {
		String name="";
		public void setName(String s){
			name=s;
		}
		
		// override ���ĕύX���郁�\�b�h
		@Override
		public void update(Observable o, Object arg) {	
			String str = (String) arg;
			System.out.println("����PlantPrgEdit class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
					if(base != null ){
					base.revalidate();
					base.repaint();
					}
					// RoundLabelText ����	
					// �A���̖��O�̓���
					if(str.contains("RoundLabelTextPane")==true){
					String[] s=str.split(System.getProperty("line.separator"));
					if(s.length>1)
						ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setPlantName(s[1]);
					}
					// PrgInfoEdit class�ł��BMemo
					if(str.contains("PrgInfoEdit")==true && str.contains("Memo")==true){
						String[] s=str.split(System.getProperty("line.separator"));
						if(s.length>1)
							ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setPlantMemo(s[1]);
						
					//	System.out.println(ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram());
					}
					
				}// update
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		JButton jb=(JButton)arg0.getSource();
		

		if(jb.getText().equals("�L�����Z��")==true){
			//
			message = "����PlantPrgEdit class�ł��B"+"�L�����Z�� �{�^���������ꂽ�B";
			
		} else {
		message = "����PlantPrgEdit class�ł��B"+"���̐ݒ��K�p���� �{�^���������ꂽ�B";
		
		if(jb.getText().equals("�߂�")==false){
		int ans = JOptionPane.showConfirmDialog(null, "�t�@�C���ɕۑ����܂����H");
		if(ans == JOptionPane.YES_OPTION) {
		FileChooser fc=new FileChooser(new JFrame());		
		File f=fc.getSelectedFile();
		
		if(f!=null){
		// �ۑ�����
//		System.out.println(f);
		// �g���q .xml���Ȃ���Βǉ�����
		if(f.getName().contains(".xml")==true) 
			ITPlanterClass.getCurrentPlanterClass().getInformation().setBIXppFileName(f.getAbsolutePath());
		else
			ITPlanterClass.getCurrentPlanterClass().getInformation().setBIXppFileName(f.getAbsolutePath()+".xml");
		////		
			String s=roundLabelText.getText();
			Information info=ITPlanterClass.getCurrentPlanterClass().getInformation();
			info.getPlantProgram().setPlantName(s);
		// PrgInfoEdit class�ł��BMemo
			String t=prgInfoEdit.getText();
			info.getPlantProgram().setPlantMemo(t);
			
			getPlantProgramDataFromPanel(getParentClass());// ������Panel�̓��e��PlantProgram Class�ɃZ�b�g����		
			PlantProgram pp=info.getPlantProgram();		
			
			pp.setBIXpp();// PlantProgram�̓��e��BIXpp�ɔ��f����
			
			pp.writeFile(info.getBIXppFileName());// BIX�t�@�C���ɏ����o��
			//
			PlanterSetting.reList();
		//
		}// f!=null
		} else {
			if(ans == JOptionPane.CANCEL_OPTION) {
				// �������Ȃ�
				return;
				} else {
					if(ans == JOptionPane.NO_OPTION) {
						// �t�@�C���ɕۑ�����ŁANo���N���b�N����ƁA�͔|�v���O������������Ȃ������̂��C��
						String s=roundLabelText.getText();
						Information info=ITPlanterClass.getCurrentPlanterClass().getInformation();
						info.getPlantProgram().setPlantName(s);
						String t=prgInfoEdit.getText();
						info.getPlantProgram().setPlantMemo(t);
						getPlantProgramDataFromPanel(getParentClass());// ������Panel�̓��e��PlantProgram Class�ɃZ�b�g����		
						PlantProgram pp=info.getPlantProgram();		
						pp.setBIXpp();// PlantProgram�̓��e��BIXpp�ɔ��f����
					}
				}
			}
		
		// Save Data to Planter
		// 2012/12/01 sakaguti
		/*
			Progress progress=new Progress();
			progress.start();
			Information pi=ITPlanterClass.getCurrentPlanterClass().getInformation();
			PlantProgram p=pi.getPlantProgram();
			p.sendToPlanter(ITPlanterClass.getCurrentPlanterNo());
			*/
			}
		}
		//
		//
//		System.out.println(message);
		
		if(observableMan!=null){
		observableMan.setMessage(message);
		// �ώ@�ґS���ɒʒm
		observableMan.notifyObservers();// �����͂Ƃ�Ȃ�
		}
		
	}

	public void setInformation(String s) {
		//
		roundLabelText.setText(s);// ���̂��\������Ȃ� -> static�ɂ��Ă�������B
		
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		ITPlanterClass.getCurrentPlanterClass().getInformation().setBIXppFileName(s);
		//
		//
		BIXpp bixpp=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().getBIXpp();
		
		if(bixpp!=null){
		if(bixpp.getCultivationCount()>0){
		Cultivation c=bixpp.getCultivation(0);
		if(c!=null){
		Plant p=c.getPlant();
		String t=p.getName();//
		prgInfoEdit.setText(t);
		}
		}
		}
	}
	


}
