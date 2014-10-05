
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.WindowConstants;


import java.awt.GridLayout;
import java.awt.ComponentOrientation;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class illumPanelEdit1 extends JPanel  {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		illumPanelEdit1 gjp=new illumPanelEdit1();
		
		gjp.setValue(2200);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public illumPanelEdit1() {
		super();
		initialize();
	}

	private GJPanel gJPanel=null;
	private InfoDispEdit ifD1=null;
//	private InfoDispEdit ifD2=null;
//	private InfoDispEdit ifD3=null;
	private  ArrayList<DisplayFromToEdit> timeTable=null;
	private JPanel base=null;
	private static JPanel timePanel=null;
	private static GridBagLayout gridbagTimeTable=null;
//	public DisplayFromTo dispComb=null;
	private static illumPanelEdit1 parentClass=null;
	
	public void setParentClass(illumPanelEdit1 p)
	{
		parentClass=p;
	}

	public static illumPanelEdit1 getParentClass()
	{
		return parentClass;
	}

	@Override
	public void setEnabled(boolean b)
	{
		ifD1.setEnabled(b);
		
	}
	
	public void setValue(int b)
	{
		if(b>5000) b=4900;// limitter
		ifD1.setSelectedIndex(b/100);// X100 lux
	}
	
	public double getValue()
	{
//		return ifD1.getValue()*100.0;// X100 lux
		return ifD1.getValue();// X100 lux
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
		base.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//base.setPreferredSize(new Dimension(200,320));
		
		/*
		// separator
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);

		JSeparator vspr=new JSeparator(JSeparator.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(200, 20));
		gridbag.setConstraints(vspr, constraints1);
		base.add(vspr);
		*/
		
		// panel1
		String st = Path.getPath()+"/images//program_h6_bg_illuminance.gif";
		if(IsMacorWin.isMacOrWin()==false) st = Path.getPath()+"images\\/program_h6_bg_illuminance.gif";

		gJPanel = new GJPanel(st);
		gJPanel.setText("LEDライト");

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 1;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(gJPanel, constraints1);
		base.add(gJPanel);
		
		
		// panel
		JPanel jp1=new JPanel();
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp1.setLayout(gridLayout);
//		jp1.setPreferredSize(new Dimension(gJPanel.getWidth(),gJPanel.getHeight()));
		jp1.setPreferredSize(new Dimension(160,50));
		jp1.setBackground(Color.gray);
	
//
		ifD1=new InfoDispEdit();
		ifD1.setText("警告下限照度");
		// lux
		String[] illumdata=new String[50];
		for(int i=0;i<50;i++){
			illumdata[i]= String.valueOf(i*100);
		}
		ifD1.setSelectionItems(illumdata);
		ifD1.setSelectedIndex(22);//  2500 lux
		ifD1.setUnit("lux");
		//
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridy = 0;	
		constraints2.gridy = 2;	
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD1, constraints2);
		
		if(Version.getRevision().equals("Education")!=true)
			base.add(ifD1);

		setParentClass(this);
		this.setLayout(new BorderLayout());
		this.add( base, BorderLayout.CENTER );

		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlantPrgEdit.ObserverA();
		observableMan.addObserver(defaultO);
	}

	public void addTimeTable() {
		//
		// ONにする時刻、OFFにする時刻のテーブルを追加する。
		DisplayFromToEdit dispComb=new DisplayFromToEdit();
		dispComb.setBackground(new Color(250,251,245));
		//
		// デフォルトの観察者を追加
		dispComb.setObserver(new illumPanelEdit1.ObserverA());
		dispComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTableに入れた順序
		dispComb.setPreferredSize(new Dimension(180,180));
		// add Object to timeTable array
		int i=timeTable.size();
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
		// default value
		dispComb.setFromHour(i);
		dispComb.setFromMin(i);
		dispComb.setToHour(i+1);
		dispComb.setToMin(i+30);
		
		// Add to ArrayList
		timeTable.add(dispComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = i;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		gridbagTimeTable.setConstraints(dispComb, constraints);
		timePanel.add(dispComb);
	}
	
	//
	public  void rearrangeTimeTable() {
		//System.out.println("rearrangeTimeTable !!");	
		if(timeTable==null) return;
		
//		System.out.println(timeTable.size());
		for(int i=0;i<timeTable.size();i++){
		//System.out.println("No."+i);	
		
		// Add to ArrayList
		DisplayFromToEdit dispComb= timeTable.get(i);		
		// delete mark in it
		System.out.println("Name "+dispComb.getName());
		// Nameに、" X"が付いていたら、削除対象になっている。
		if(dispComb.getName().contains(" X")){
			//System.out.print(timeTable.size()+"->");	
			timeTable.remove(i);
			//System.out.println(timeTable.size());	
			}
		}
		//
		timePanel.removeAll();
		
		for(int i=0;i<timeTable.size();i++){
			//
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = i;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		DisplayFromToEdit dispComb= timeTable.get(i);	
		gridbagTimeTable.setConstraints(dispComb, constraints);
		timePanel.add(dispComb);
		}
//		timePanel.repaint();
		timePanel.revalidate();
	}
	
	
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
	
	
	private ObservableMan observableMan=null;  
//	private String message="";
	private Observer defaultO=null;
	
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;

	System.out.println("私はillumPaneEdit classです。観察対象の通知を検知したよ。" + str);
	  //System.out.println("rearrangeTimeTable");  	 
	  // X button
	  //System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
	  
	getParentClass().rearrangeTimeTable();
	 // System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
		}
	}
	

}
