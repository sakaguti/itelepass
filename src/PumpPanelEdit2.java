import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;


public class PumpPanelEdit2 extends JPanel  implements ActionListener{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		PumpPanelEdit2 gjp=new PumpPanelEdit2();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public PumpPanelEdit2() {
		super();
		initialize();
	}

	private final int MAXENTRY=6;

	private  JButton addTimeButton=null;
	public   ArrayList<DelDoubleComb> timeTable=null;
	private   GridBagLayout gridbagTimeTable =null;
	private   JPanel timePanel =null;
	private  JScrollPane scrollPane=null;
	private  JPanel base=null;
	private  boolean state=true;
	private static  PumpPanelEdit2 objectParent=null;
	
	public void setParentClass(PumpPanelEdit2 p)
	{
		objectParent=p;
	}
	
	public static PumpPanelEdit2 getParentClass()
	{
		return objectParent;
	}
	
	@Override
	public void setEnabled(boolean b)
	{
		for(int i=0;i<timeTable.size();i++){
			timeTable.get(i).setEnabled(b);
		}
		rearrangeTimeTable(objectParent);
		addTimeButton.setVisible(b);
		state=b;
	}
	
	public boolean getEnabled()
	{
		return state;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);

		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		// default size
		//base.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	//	base.setPreferredSize(new Dimension(220, 320));
		

		JPanel gJPanel=new JPanel();
		JLabel gJLabel=new JLabel("�|���v���쎞��");
		gJLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		gJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gJLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		//gJLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(gJPanel, constraints1);
		
		gJPanel.setLayout(gridLayout);

		gJPanel.setPreferredSize(new Dimension(200, 20));
		gJPanel.add(gJLabel, null);
		base.add(gJPanel);
			
		
		// panel
		gridbagTimeTable = new GridBagLayout();
		timePanel = new JPanel(gridbagTimeTable);
		timePanel.setBackground(Color.gray);
		timePanel.setName("TimeTablePanel");
		timePanel.setBackground(new Color(250,251,245));
		//
		//
		// �|���v�̓��쎞����\������
		timeTable=new ArrayList<DelDoubleComb>();
		/*
		int n=2;
		for(int i=0;i<n;i++){
		addTimeTable();
		}	
		*/
		setParentClass(this);
		//
		scrollPane = new JScrollPane(timePanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// ���X�N���[���o�[��\�����Ȃ��B
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setPreferredSize(new Dimension(220, 100));
		scrollPane.setBorder(null);
		
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(scrollPane, constraints2);
		base.add(scrollPane);

		
		//
		// button
		addTimeButton=new JButton("������ǉ�����");
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.anchor=GridBagConstraints.NORTH;
		constraints3.gridx = 0;
		constraints3.gridy = 2;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(addTimeButton, constraints3);
		addTimeButton.setPreferredSize(new Dimension(200,20));
		addTimeButton.addActionListener(this);
		base.add(addTimeButton);
		
		//
		//this.add(base);
		//this.setSize(200, 400);
		//this.setPreferredSize(new Dimension(200,400));
		this.setLayout(new BorderLayout());
		this.add( base, BorderLayout.CENTER );
		
		
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �ώ@�҂�ǉ�
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
	}
	
	// �ݒ肳�ꂽ�^�C���e�[�u����Ԃ�
	//  Hour1:Min1 Hour2:Min2 Hour3:Min3......   
	public String getTimeTable()
	{
		String s="";
		for(int i=0;i<timeTable.size();i++){
			s += timeTable.get(i).getHour()+":"+timeTable.get(i).getMin()+" ";
		}
//		System.out.println("pump shcedule = "+s+" size="+timeTable.size());
		return s;
	}
	
	public Schedule getTimeTable(int i)
	{
		if(i>=timeTable.size()) return null;
		Schedule ss=new Schedule();
		ss.setStartTime(timeTable.get(i).getHour()+":"+timeTable.get(i).getMin());
		ss.setContinueTime(timeTable.get(i).getHour()+":"+timeTable.get(i).getMin());
		return ss;
	}
	
	public void addTimeTable(Schedule s) {
		//
		// ON�ɂ��鎞���AOFF�ɂ��鎞���̃e�[�u����ǉ�����B
		DelDoubleComb delDoubleComb=new DelDoubleComb();
		delDoubleComb.setBackground(new Color(250,251,245));
		//
		// �f�t�H���g�̊ώ@�҂�ǉ�
		delDoubleComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTable�ɓ��ꂽ����
		delDoubleComb.addObserver(new PumpPanelEdit2.ObserverA());// X�@�ŏ���
		//
		// �����Ł@ON�̎��Ԃ�OFF�̎��Ԃ�ݒ肷��B
		//
		// default value
		String[] hm=s.getStartTime().split(":");
		int ih=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
		int im=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//

		delDoubleComb.setHour(ih);
		delDoubleComb.setMin(im);
//		delDoubleComb.setHour(Integer.valueOf(s.getStartTime().split(":")[0]));
//		delDoubleComb.setMin(Integer.valueOf(s.getStartTime().split(":")[1]));
		
		// Add to ArrayList
		timeTable.add(delDoubleComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = timeTable.size()-1;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		gridbagTimeTable.setConstraints(delDoubleComb, constraints);
		timePanel.add(delDoubleComb);
		timePanel.revalidate();
	}
	
	private void addTimeTable() {
		//
		// ON�ɂ��鎞���AOFF�ɂ��鎞���̃e�[�u����ǉ�����B
		DelDoubleComb delDoubleComb=new DelDoubleComb();
		delDoubleComb.setBackground(new Color(250,251,245));
		//
		// �f�t�H���g�̊ώ@�҂�ǉ�
		//delDoubleComb.setObserver(new illumPanelEdit.ObserverA());
		delDoubleComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTable�ɓ��ꂽ����
		//delDoubleComb.setPreferredSize(new Dimension(220,80));
		// add Object to timeTable array
		delDoubleComb.addObserver(new PumpPanelEdit2.ObserverA());// X�@�ŏ���
		int i=timeTable.size();
		//
		// �����Ł@ON�̎��Ԃ�OFF�̎��Ԃ�ݒ肷��B
		//
		TimeDate td=new TimeDate();
		// default value
		delDoubleComb.setHour(td.getHour());
		delDoubleComb.setMin(0);
		
		// Add to ArrayList
		timeTable.add(delDoubleComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = i;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		gridbagTimeTable.setConstraints(delDoubleComb, constraints);
		timePanel.add(delDoubleComb);
		timePanel.revalidate();
	}
	

	public void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	      JButton btn = (JButton)arg0.getSource();
	      if(btn.equals(addTimeButton)){	  
		  			addTimeTable();
		  			//
		  			// �Z�b�g�ł���ő吔�ȉ��ɂȂ����̂Ń{�^�����f�A�N�e�B�u������
		  			if(timeTable.size() > (MAXENTRY-1)){
		  				addTimeButton.setEnabled(false);
		  			} 
		  			//
		  			message = "����PumpPanelEdit2 class�ł��B"+btn.getText()+" ��������܂����B";
//		  		System.out.println("PumpPanelEdit2: "+getTimeTable());
		  		observableMan.setMessage(message);
				// �ώ@�ґS���ɒʒm
				observableMan.notifyObservers();// �����͂Ƃ�Ȃ�
	    	  // Do addTimeButton processing
	      } 
	}
	
	//
	public   void rearrangeTimeTable(PumpPanelEdit2 p) {
		//System.out.println("rearrangeTimeTable !!");	
		if(p.timeTable==null) return;
		
//		System.out.println(p.timeTable.size());
		for(int i=0;i<p.timeTable.size();i++){
		
		// Add to ArrayList
		DelDoubleComb dispComb= p.timeTable.get(i);		
		// delete mark in it
//		System.out.println("Name "+dispComb.getName());
		// Name�ɁA" X"���t���Ă�����A�폜�ΏۂɂȂ��Ă���B
		if(dispComb.getName().contains(" X")){
			int b=timeTable.size();
			System.out.print(b+"->");	
			p.timeTable.remove(i);
			}
		}
		
		//
		p.timePanel.removeAll();
		p.timePanel.repaint();// �c�����c��̂ŏ��������邱�ƁB
		
		for(int i=0;i<p.timeTable.size();i++){
			GridBagConstraints constraints=new GridBagConstraints();
			constraints.gridy = 0;	
			constraints.gridy = i;	
			constraints.gridwidth= 1;
			constraints.gridheight = 1;
			constraints.insets = new Insets(0, 0, 0, 0);
			
			DelDoubleComb delDoubleComb= p.timeTable.get(i);	
			p.gridbagTimeTable.setConstraints(delDoubleComb, constraints);
			p.timePanel.add(delDoubleComb);
		}
		// �Z�b�g�ł���ő吔�ȉ��ɂȂ����̂Ń{�^�����A�N�e�B�u������
		if(timeTable.size()<MAXENTRY){
				addTimeButton.setEnabled(true);
			}
		p.timePanel.revalidate();
		//scrollPane.revalidate();

	}
	
	private ObservableMan observableMan=null;  
	private String message="";
	
	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	 static class ObserverA implements Observer {
	/* (�� Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/

	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;
	System.out.println("����PumpPanelEdit2 class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
	if(getParentClass()==null) return;
		getParentClass().rearrangeTimeTable(getParentClass());
  		//System.out.println("PumpPanelEdit2: "+getTimeTable());
		}
	}

	int timeTableNo=0;
	
	public void deleteAllSchedule() {
		timePanel.removeAll();// �p�l���̃R���|�[�l���g��S�ď���
		for(int i=0;i<timePanel.getComponentCount();i++) timePanel.remove(i);
		
		// �|���v�̓��쎞����\������
		timeTable=new ArrayList<DelDoubleComb>();
		/*
		 * ���̂��P�����c���Ă��܂��B
		for(int i=0;i<timeTable.size();i++){
			timeTable.remove(i);// ���X�g���ꂽ�X�P�W���[����S�ď���
		}
		*/
		timeTableNo=0;
	}
	
	public void addSchedule(Schedule s) {
		//
		// ON�ɂ��鎞���AOFF�ɂ��鎞���̃e�[�u����ǉ�����B
		DelDoubleComb delDoubleComb=new DelDoubleComb();
		delDoubleComb.setBackground(new Color(250,251,245));
		//
		// �f�t�H���g�̊ώ@�҂�ǉ�
		//delDoubleComb.setObserver(new illumPanelEdit.ObserverA());
		delDoubleComb.setName(String.valueOf(objectParent.timeTable.size()));// gridbagTimeTable�ɓ��ꂽ����
		delDoubleComb.setEnabled(getEnabled());
		//delDoubleComb.setPreferredSize(new Dimension(220,80));
		// add Object to timeTable array
		//
		if(s!=null){
//		System.out.println(s.getStartTime());
		String[] hm=s.getStartTime().split(":");
		if(hm.length==2){
		delDoubleComb.setHour(Integer.valueOf(hm[0]));
		delDoubleComb.setMin(Integer.valueOf(hm[1]));
		}
		}
		
		// Add to ArrayList
		objectParent.timeTable.add(delDoubleComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = objectParent.timeTableNo++;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		gridbagTimeTable.setConstraints(delDoubleComb, constraints);
		objectParent.timePanel.add(delDoubleComb);
		objectParent.timePanel.revalidate();
	}
}
