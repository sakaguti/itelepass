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

public class illumPanelEdit2 extends JPanel  implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		illumPanelEdit2 gjp=new illumPanelEdit2();
		int n=10;
		for(int i=0;i<n;i++){
			gjp.addTimeTable();
		}
		gjp.setEnabled(false);
		gjp.setEnabled(true);
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public illumPanelEdit2() {
		super();
		initialize();
	}


	private JButton addTimeButton=null;
	ArrayList<DelTripleComb> timeTable=null;
	private JPanel base=null;
	private JPanel timePanel=null;
	public GridBagLayout gridbagTimeTable=null;
	private JScrollPane scrollPane =null;
	private boolean state=true;
	private static illumPanelEdit2 objectParent=null;
	
	public void setParentClass(illumPanelEdit2 p)
	{
		objectParent=p;
	}
	
	public illumPanelEdit2 getParentClass()
	{
		return objectParent;
	}
	
	@Override
	public void setEnabled(boolean b)
	{
		rearrangeTimeTable(objectParent);
		timePanel.repaint();
		addTimeButton.setVisible(b);
		for(int i=0;i<timeTable.size();i++){
			timeTable.get(i).setEnabled(b);
		}
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
		

		//デュティ変更時刻
		JPanel gJPanel=new JPanel();
		JLabel gJLabel=new JLabel("デュティ変更時刻");
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
		
//		gJPanel.setPreferredSize(new Dimension(200, 20));
		
		gJPanel.add(gJLabel, null);
		base.add(gJPanel);
		
		//
		// ここで　ONの時間とOFFの時間を設定されている数だけ表示する。
		//
		// panel
		gridbagTimeTable = new GridBagLayout();
		timePanel=new JPanel(gridbagTimeTable);
		timePanel.setBackground(Color.gray);
		timePanel.setName("DutyTablePanel");
		timePanel.setBackground(new Color(250,251,245));
		
		scrollPane = new JScrollPane(timePanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// 横スクロールバーを表示しない。
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setPreferredSize(new Dimension(200, 100));
		scrollPane.setBorder(null);
		
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 1;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(timePanel, constraints1);

		timeTable=new ArrayList<DelTripleComb>();
		
		setParentClass(this);
		
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
	//  Duty1,Hour1:Min1  Duty2,Hour2:Min2  Duty3,Hour3:Min3......   
	public String getTimeTable()
	{
		String s="";
		for(int i=0;i<timeTable.size();i++){
			DelTripleComb dtc=timeTable.get(i);
			s += dtc.getDuty()+" "+dtc.getHour()+":"+dtc.getMin()+" ";
		}
		return s;
	}
	
	
	public Schedule getTimeTable(int i)
	{
		DelTripleComb dtc=timeTable.get(i);
		Schedule ss=new Schedule();
		ss.setValue(dtc.getDuty());
		ss.setStartTime(dtc.getHour()+":"+dtc.getMin());
		return ss;
	}
	
	public void addTimeTable() {
		//
		int i=timeTable.size();
		if(i>5) return;// Time Table under 6
		//
		// ONにする時刻、OFFにする時刻のテーブルを追加する。
		DelTripleComb delTripleComb=new DelTripleComb();
		//
		delTripleComb.setBackground(new Color(250,251,245));
		//
		// デフォルトの観察者を追加
		delTripleComb.setObserver(new illumPanelEdit2.ObserverA());
		delTripleComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTableに入れた順序
		//delTripleComb.setPreferredSize(new Dimension(180,180));
		// add Object to timeTable array
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
		TimeDate td=new TimeDate();
		// default value
		delTripleComb.setHour(td.getHour());
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

	private int timeTableNo=0;// TimeTableの項目番号

	public void addTimeTable(Schedule s) {
		//
		// ONにする時刻、OFFにする時刻のテーブルを追加する。
		DelTripleComb delTripleComb=new DelTripleComb();
		delTripleComb.setEnabled(getEnabled());
		//
		delTripleComb.setBackground(new Color(250,251,245));
		//
		// デフォルトの観察者を追加
		delTripleComb.setObserver(new illumPanelEdit2.ObserverA());
		
		delTripleComb.setName(String.valueOf(timeTable.size()));// gridbagTimeTableに入れた順序
		//delTripleComb.setPreferredSize(new Dimension(180,180));
		// add Object to timeTable array
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
		if(s!=null){
		if(s.getStartTime().split(":").length==2){
			String[] hm=s.getStartTime().split(":");
			int ih=Integer.parseInt(hm[0].replaceAll("[^0-9]",""));//
			int im=Integer.parseInt(hm[1].replaceAll("[^0-9]",""));//
			delTripleComb.setHour(ih);
			delTripleComb.setMin(im);
//			delTripleComb.setHour(Integer.valueOf(s.getStartTime().split(":")[0]));
//			delTripleComb.setMin(Integer.valueOf(s.getStartTime().split(":")[1]));
			delTripleComb.setDuty(s.getValue());
		}	
		}
		
		// Add to ArrayList
		timeTable.add(delTripleComb);
		
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy = 0;	
		constraints.gridy = timeTableNo++;	
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
	public  void rearrangeTimeTable(illumPanelEdit2 p) {
		//System.out.println("rearrangeTimeTable !!");	
		if(p.timeTable==null) return;
		
//		System.out.println(timeTables.size());
		for(int i=0;i<timeTable.size();i++){
		//System.out.println("No."+i);	
		
		// Add to ArrayList
		DelTripleComb	delTripleComb= timeTable.get(i);		
		// delete mark in it
//		System.out.println("Name "+delTripleComb.getName());
		// Nameに、" X"が付いていたら、削除対象になっている。
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
	      
	      message ="私はillumPanelEdit2 classです。"+btn.getText()+"が押されました。";
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
	class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	
	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;

	System.out.println("私はillumPaneEdit2 classです。観察対象の通知を検知したよ。" + str);
	  //System.out.println("rearrangeTimeTable");  	 
	  // X button
	  //System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
	  
		getParentClass().rearrangeTimeTable(getParentClass());
	 // System.out.println("illumPanelEdit.timeTable."+illumPanelEdit.timeTable.size());  
		}
	}

	public void deleteAllSchedule() {
		timePanel.removeAll();
		for(int i=0;i<timePanel.getComponentCount();i++) timePanel.remove(i);
		// ポンプの動作時刻を表示する
		timeTable=new ArrayList<DelTripleComb>();
//		 * 何故か１つだけ残ってしまう。
		//for(int i=0;i<timeTable.size();i++) timeTable.remove(i);// リストされたスケジュールを全て消す
		timeTableNo=0;// 項目番号のクリア
	}

	public void addSchedule(Schedule s) {
		addTimeTable(s);
	}



}
