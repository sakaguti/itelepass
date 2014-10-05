import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class illumPanelEdit3 extends JPanel  implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		illumPanelEdit3 gjp=new illumPanelEdit3();
		
		//gjp.setEnabled(false);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public illumPanelEdit3() {
		super();
		initialize();
	}


	private  JButton addTimeButton=null;
	ArrayList<DisplayFromToEdit> timeTable=null;
	private  JPanel base=null;
	private  JPanel timePanel=null;
	private  GridBagLayout gridbagTimeTable=null;
	private boolean state=true;
	private static illumPanelEdit3 objectParent=null;
	
	public void setParentClass(illumPanelEdit3 p)
	{
		objectParent=p;
	}
	
	public static illumPanelEdit3 getParentClass()
	{
		return objectParent;
	}
	
	@Override
	public void setEnabled(boolean b)
	{
		addTimeButton.setVisible(b);
		for(int i=0;i<timeTable.size();i++){
			timeTable.get(i).setEnabled(b);
		}
		rearrangeTimeTable(objectParent);
		timePanel.repaint();
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

		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		// default size
		base.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//base.setPreferredSize(new Dimension(220, 320));
		

		//LEDライト点灯時刻
		JPanel gJPanel=new JPanel();
		JLabel gJLabel=new JLabel("LEDライト点灯時刻");
		gJLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		gJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gJLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.setRows(1);
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gJPanel.setLayout(gridLayout2);
		gridbag.setConstraints(gJPanel, constraints1);
		gJPanel.setPreferredSize(new Dimension(200, 20));
		gJPanel.add(gJLabel, null);
		base.add(gJPanel);
		
		//
		// ここで　ONの時間とOFFの時間を設定されている数だけ表示する。
		//
		// panel
		gridbagTimeTable = new GridBagLayout();
		timePanel=new JPanel(gridbagTimeTable);
		timePanel.setBackground(Color.gray);
		timePanel.setName("TimeTablePanel");
		timePanel.setBackground(new Color(250,251,245));
		
		JScrollPane scrollPane = new JScrollPane(timePanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// 横スクロールバーを表示しない。
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	//	scrollPane.setPreferredSize(new Dimension(200, 160));
	//	scrollPane.setBorder(null);
		
		scrollPane.setPreferredSize(new Dimension(200, 160));
		scrollPane.setBorder(null);
		
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 1;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(timePanel, constraints1);

		timeTable=new ArrayList<DisplayFromToEdit>();
		/*
		int n=2;
		for(int i=0;i<n;i++){
		addTimeTable();
		}	
		*/
		gridbag.setConstraints(scrollPane, constraints1);
		base.add(scrollPane);
		
		// button
		addTimeButton=new JButton("時刻を追加する");
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 0;
		constraints1.gridy = 2;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(addTimeButton, constraints1);
		addTimeButton.setPreferredSize(new Dimension(200,20));
		addTimeButton.addActionListener(this);
		base.add(addTimeButton);
		//
		//
		setParentClass(this);
		//
		this.add(base);
		//this.setSize(200, 700);
		//this.setPreferredSize(new Dimension(200,300));
		this.setLayout(new BorderLayout());
		this.add( base, BorderLayout.CENTER );
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlantPrgEdit.ObserverA();
		observableMan.addObserver(defaultO);
	}

	// 設定されたタイムテーブルを返す
	//  From_Hour1:Min1  To_Hour1:Min1  From_Hour2:Min2 To_Hour2:Min2......   
	public String getTimeTable()
	{
		String s="";
		for(int i=0;i<timeTable.size();i++){
			DisplayFromToEdit dtc=timeTable.get(i);
			s += dtc.getFromHour()+":"+dtc.getFromMin()+" "+dtc.getToHour()+":"+dtc.getToMin()+" ";
		}
		return s;
	}
	
	public Schedule getTimeTable(int i)
	{
		Schedule ss=new Schedule();
		ss.setStartTime(timeTable.get(i).getFromHour()+":"+timeTable.get(i).getFromMin());
		ss.setEndTime(timeTable.get(i).getToHour()+":"+timeTable.get(i).getToMin());
		return ss;
	}
	public void addTimeTable() {
		//
		int i=timeTable.size();
		if(i>5) return;
		//
		// ONにする時刻、OFFにする時刻のテーブルを追加する。
		DisplayFromToEdit dispComb=new DisplayFromToEdit();
		dispComb.setBackground(new Color(250,251,245));
		//
		// デフォルトの観察者を追加
		dispComb.setObserver(new illumPanelEdit3.ObserverA());
		dispComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTableに入れた順序
		//dispComb.setPreferredSize(new Dimension(180,180));
		// add Object to timeTable array
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
		TimeDate td=new TimeDate();
		// default value
		dispComb.setFromHour(td.getHour());
		dispComb.setFromMin(0);
		dispComb.setToHour(td.getHour()+1);
		dispComb.setToMin(0);
		
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
		rearrangeTimeTable(objectParent);//
	}
	
	//
	public void rearrangeTimeTable(illumPanelEdit3 p) 
	{
//		System.out.println("rearrangeTimeTable !!");	
		if(p.timeTable==null) return;
		
//		System.out.println("timeTable.size"+p.timeTable.size());
		for(int i=0;i<p.timeTable.size();i++){
		//System.out.println("No."+i);	
		
		// Add to ArrayList
		DisplayFromToEdit dispComb= p.timeTable.get(i);		
		// delete mark in it
//		System.out.println("Name "+dispComb.getName());
		// Nameに、" X"が付いていたら、削除対象になっている。
		if(dispComb.getName().contains(" X")){
			//System.out.print(timeTable.size()+"->");	
			p.timeTable.remove(i);
			//System.out.println(timeTable.size());	
			}
		}
		//
		p.timePanel.removeAll();
		
		for(int i=0;i<p.timeTable.size();i++){
			//
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = i;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		DisplayFromToEdit dispComb= p.timeTable.get(i);	
		p.gridbagTimeTable.setConstraints(dispComb, constraints);
		p.timePanel.add(dispComb);
		}
		p.timePanel.repaint();
		p.timePanel.revalidate();
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	      JButton btn = (JButton)arg0.getSource();
	      
	      if(btn.equals(addTimeButton)){
//	    	  System.out.println("addTimeButton");  	  
	  			addTimeTable();
	      } 
	      
	      message ="私はillumPanelEdit3 classです。"+btn.getText()+"が押されました。";

	      rearrangeTimeTable(objectParent);
	      
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
	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;

	System.out.println("私はillumPaneEdit3 classです。観察対象の通知を検知したよ。" + str);
	  //System.out.println("rearrangeTimeTable");  	 
	  // X button
	  //System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  

	//TODO
	 	getParentClass().rearrangeTimeTable(objectParent);
	  
	 // System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
		}
	}

	public void deleteAllSchedule() {
		timePanel.removeAll();
		for(int i=0;i<timePanel.getComponentCount();i++) timePanel.remove(i);
		timeTable=new ArrayList<DisplayFromToEdit>();
// 		 * 何故か１つだけ残ってしまう。
//		for(int i=0;i<timeTable.size();i++)		timeTable.remove(i);// リストされたスケジュールを全て消す
		timeTableNo=0;
	}

	static int timeTableNo=0;
	public void addSchedule(Schedule s) {
		//
		// ONにする時刻、OFFにする時刻のテーブルを追加する。
		DisplayFromToEdit dispComb=new DisplayFromToEdit();
		dispComb.setBackground(new Color(250,251,245));
		dispComb.setEnabled(getEnabled());
		
		//
		// デフォルトの観察者を追加
		dispComb.setObserver(new illumPanelEdit3.ObserverA());
		dispComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTableに入れた順序
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
//		System.out.println("start "+s.getStartTime()+"end "+s.getEndTime()+"cont "+s.getContinueTime()+"val "+s.getValue());
		if(s.getStartTime().split(":").length==2){
			String[] hm=s.getStartTime().split(":");
			int ih=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
			int im=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
			dispComb.setFromHour(ih);
			dispComb.setFromMin(im);
//		dispComb.setFromHour(Integer.valueOf(s.getStartTime().split(":")[0]));
//		dispComb.setFromMin(Integer.valueOf(s.getStartTime().split(":")[1]));
		}
		if(s.getEndTime().split(":").length==2){
			String[] hm=s.getEndTime().split(":");
			int ih=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
			int im=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
			dispComb.setToHour(ih);
			dispComb.setToMin(im);

//		dispComb.setToHour(Integer.valueOf(s.getEndTime().split(":")[0]));
//		dispComb.setToMin(Integer.valueOf(s.getEndTime().split(":")[1]));
		}
		
		// Add to ArrayList
		timeTable.add(dispComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = timeTableNo++;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		gridbagTimeTable.setConstraints(dispComb, constraints);
		timePanel.add(dispComb);
	//	rearrangeTimeTable(objectParent);//	
	}

	public void addTimeTable(Schedule s) {
		//
		// ONにする時刻、OFFにする時刻のテーブルを追加する。
		DisplayFromToEdit dispComb=new DisplayFromToEdit();
		dispComb.setBackground(new Color(250,251,245));
		//
		// デフォルトの観察者を追加
		dispComb.setObserver(new illumPanelEdit3.ObserverA());
		dispComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTableに入れた順序
		//dispComb.setPreferredSize(new Dimension(180,180));
		// add Object to timeTable array
		int i=timeTable.size();
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
		// default value

		String[] hm0=s.getEndTime().split(":");
		int ih0=Integer.parseInt(hm0[0].replaceAll("[^0-9]",""));//
		int im0=Integer.parseInt(hm0[1].replaceAll("[^0-9]",""));//
		dispComb.setToHour(ih0);
		dispComb.setToMin(im0);
		dispComb.setFromHour(Integer.valueOf(s.getStartTime().split(":")[0]));
		dispComb.setFromMin(Integer.valueOf(s.getStartTime().split(":")[1]));
		String[] hm1=s.getEndTime().split(":");
		int ih1=Integer.parseInt(hm1[0].replaceAll("[^0-9]",""));//
		int im1=Integer.parseInt(hm1[1].replaceAll("[^0-9]",""));//
		dispComb.setToHour(ih1);
		dispComb.setToMin(im1);
//		dispComb.setToHour(Integer.valueOf(s.getEndTime().split(":")[0]));
//		dispComb.setToMin(Integer.valueOf(s.getEndTime().split(":")[1]));
		
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
		rearrangeTimeTable(objectParent);//
	}
	

}
