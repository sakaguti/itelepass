import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.WindowConstants;


import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TempPanelEdit2 extends JPanel  implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		TempPanelEdit2 gjp=new TempPanelEdit2();
		
		gjp.setEnabled(false);
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public TempPanelEdit2() {
		super();
		initialize();
	}


	private JButton addTimeButton=null;
	private  ArrayList<DelTripleComb> timeTable=null;
	private JPanel base=null;
	private  JPanel timePanel=null;
	private  GridBagLayout gridbagTimeTable=null;
	private static TempPanelEdit2 parentClass=null;
	public void setParentClass(TempPanelEdit2 p)
	{
		parentClass=p;
	}
	public static TempPanelEdit2 getParentClass()
	{
	return parentClass;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		// default size
		base.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//base.setPreferredSize(new Dimension(220, 320));
		

		AuthorPanel ap=new AuthorPanel("stevia.xml");
		base.add(ap);
		//
		this.add(base);
		//this.setSize(200, 700);
		//this.setPreferredSize(new Dimension(200,300));
		this.setLayout(new BorderLayout());
		this.add( base, BorderLayout.CENTER );
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �f�t�H���g�̊ώ@�҂�ǉ�
		defaultO=new PlantPrgEdit.ObserverA();
		observableMan.addObserver(defaultO);
	}

	public void addTimeTable() {
		//
		// ON�ɂ��鎞���AOFF�ɂ��鎞���̃e�[�u����ǉ�����B
		DelTripleComb delTripleComb=new DelTripleComb();
		//
		delTripleComb.setBackground(new Color(250,251,245));
		//
		// �f�t�H���g�̊ώ@�҂�ǉ�
		delTripleComb.setObserver(new TempPanelEdit2.ObserverA());
		delTripleComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTable�ɓ��ꂽ����
		//delTripleComb.setPreferredSize(new Dimension(180,180));
		// add Object to timeTable array
		int i=timeTable.size();
		//
		// �����Ł@ON�̎��Ԃ�OFF�̎��Ԃ�ݒ肷��B
		//
		// default value
		delTripleComb.setHour(i);
		delTripleComb.setMin(0);
		
		// Add to ArrayList
		timeTable.add(delTripleComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = i;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		gridbagTimeTable.setConstraints(delTripleComb, constraints);

		timePanel.add(delTripleComb);//
		
		timePanel.revalidate();
		//scrollPane.revalidate();
		//timePanel.repaint();
	}
	
	//
	public  void rearrangeTimeTable() {
		//System.out.println("rearrangeTimeTable !!");	
		if(timeTable==null) return;
		
		//System.out.println(timeTable.size());
		for(int i=0;i<timeTable.size();i++){
		//System.out.println("No."+i);	
		
		// Add to ArrayList
		DelTripleComb	delTripleComb= timeTable.get(i);		
		// delete mark in it
//		System.out.println("Name "+delTripleComb.getName());
		// Name�ɁA" X"���t���Ă�����A�폜�ΏۂɂȂ��Ă���B
		if(delTripleComb.getName().contains(" X")){
			//System.out.print(timeTable.size()+"->");	
			timeTable.remove(i);
			//System.out.println(timeTable.size());	
			}
		}
		//
		timePanel.removeAll();
		timePanel.repaint();
		
		for(int i=0;i<timeTable.size();i++){
			//
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = i;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		DelTripleComb delTripleComb= timeTable.get(i);	
		gridbagTimeTable.setConstraints(delTripleComb, constraints);
		timePanel.add(delTripleComb);
		}
//		timePanel.repaint();
		timePanel.revalidate();
	}
	
	
	// Observer��ǉ�����
	void addObserver(Observer o)
	{
		//observableMan.deleteObserver(defaultO);// �O�ɐݒ肳�ꂽObserver���폜����B
		//observableMan.deleteObservers();
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	      JButton btn = (JButton)arg0.getSource();
	      
	      if(btn.equals(addTimeButton)){
	    	//  System.out.println("addTimeButton");  	  
	  			addTimeTable();
	      } 
	      
	      message ="����TempPanelEdit2 class�ł��B"+btn.getText()+"��������܂����B";
	      	observableMan.setMessage(message);
			// �ώ@�ґS���ɒʒm
			observableMan.notifyObservers();
			
	}
	
	private ObservableMan observableMan=null;  
	private String message="";
	private Observer defaultO=null;
	
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

	System.out.println("����TempPaneEdit2 class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
	  //System.out.println("rearrangeTimeTable");  	 
	  // X button
	  //System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
	  
	getParentClass().rearrangeTimeTable();
	 // System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
		}
	}
	

}
