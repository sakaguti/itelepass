import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

//
// お好み設定パネル
//
public class ChoiceSetting extends JPanel {
	/**
	 * 	お好み設定パネル
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 自動撮影　：　一定時間ごと
		JFrame frame = new JFrame();
		ChoiceSetting gjp=new ChoiceSetting();
		
		gjp.setSelected(true);
		int h=13;
		int m=10;
		gjp.addTimeSet(0);
		gjp.setTime(0, h, m);
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

/*
	private void setTime(int h, int m) {
			// time set
			int ih=h;
			int im=m;
			if(delDoubleComb==null) delDoubleComb=new DelDoubleComb();
	        delDoubleComb.setName("TimeSet "+String.valueOf(no));
			delDoubleComb.setHour(ih);
			delDoubleComb.setMin(im);
			// Observerを設定する
			delDoubleComb.addObserver(new AutoPicture.ObserverA());
			//
			GridBagConstraints constraints0=new GridBagConstraints();
			constraints0.gridy = 0;	
			constraints0.gridy = no;	
			constraints0.gridwidth= 1;
			constraints0.gridheight = 1;
			constraints0.insets = new Insets(0, 0, 0, 0);
			//
			gridbagTimeTable.setConstraints(delDoubleComb, constraints0);

			if(timeTable !=null){
				timePanel.add(delDoubleComb);
				timePanel.revalidate();
			}
			//
			no++;
	}
	*/
	/**
	 * This is the default constructor
	 */
	public ChoiceSetting() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private static JRadioButton jRadioButton =null;
	private static JLabel jLabel1=null;
	private static JLabel jLabel2=null;
	private static DelDoubleComb delDoubleComb = null;
	private static JButton jButton=null;  //  @jve:decl-index=0:visual-constraint="10,60"
	private static  ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:

	
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.addActionListener(new acceptAction());
		}
		return jRadioButton;
	}

	public void setSelected(boolean b)
	{
		jRadioButton.setSelected(b);
	}
	
	public static void setEnabledAll(boolean b)
	{

		timePanel.setEnabled(b);
		jButton.setEnabled(b);
		jRadioButton.setSelected(b);
		if(timeTable==null) return;
		for(int i=0;i<timeTable.size();i++){
			timeTable.get(i).setEnabled(b);
		}
		
	}
	
	public class acceptAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println("お好み設定　"+jRadioButton.isSelected());
			//
			setSelected(jRadioButton.isSelected());
			if(timeTable==null) return;
			for(int i=0;i<timeTable.size();i++){
				timeTable.get(i).setEnabled(jRadioButton.isSelected());
			}
			jButton.setEnabled(jRadioButton.isSelected());
			//jRadioButton.setEnabled(b);
			
			message ="私はChoiceSetting classです。"+"お好み設定　"+jRadioButton.isSelected();
			//
			if(observableMan!=null){
			observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			}
		}
	}

	private static GridBagLayout gridbagTimeTable=null;
	private static JPanel base=null;
	private static JPanel timePanel=null;
	private static ArrayList<DelDoubleComb>	 timeTable=new ArrayList<DelDoubleComb>();
	private static JScrollPane scrollPane=null;
	private static GridBagLayout gridbag=null;
	private GridBagConstraints constraints0 =null;
	private GridBagConstraints constraints1 =null;
	private GridBagConstraints constraints2 =null;
	private GridBagConstraints constraints3 =null;
	private GridBagConstraints constraints4 =null;
	private GridBagConstraints constraints5 =null;
	
	public DelDoubleComb getTimeTableElement(int n)
	{
		return timeTable.get(n);
	}
	
	
	private void initialize() {
		//
		gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));

		jLabel1 = new JLabel();		
		jRadioButton = getJRadioButton();
		jRadioButton.setName("お好み設定");
		constraints0 = new GridBagConstraints();
		constraints0.gridx = 0;
		constraints0.gridy = 0;
		constraints0.gridwidth= 1;
		constraints0.gridheight = 1;
		constraints0.fill = GridBagConstraints.VERTICAL;
		constraints0.insets = new Insets(0, 0, 0, 0);
		base.add(jRadioButton, constraints0);

		jLabel1.setText("お好み設定");
		constraints1 = new GridBagConstraints();
		constraints1.gridx = 1;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.fill = GridBagConstraints.HORIZONTAL;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jLabel1, constraints1);
		base.add(jLabel1, constraints1);

		jLabel2 = new JLabel();
		jLabel2.setText("・３０件まで登録できます。");
		constraints2 = new GridBagConstraints();
		constraints2.gridx = 1;
		constraints2.gridy = 1;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.fill = GridBagConstraints.VERTICAL;
		constraints2.insets = new Insets(0, 0, 0, 0);
		base.add(jLabel2, constraints2);
		
		constraints3 = new GridBagConstraints();
		constraints3.gridx = 0;
		constraints3.gridy = 2;
		constraints3.insets = new Insets(0, 0, 0, 0);
		constraints3.fill = GridBagConstraints.VERTICAL;
		constraints3.gridwidth = 2;
		constraints3.gridheight = 1;
		
		
		//
		// ここで　ONの時間とOFFの時間を設定されている数だけ表示する。
		//
		// panel
		gridbagTimeTable = new GridBagLayout();
		timePanel=new JPanel(gridbagTimeTable);
		//
		timePanel.setBackground(new Color(250,251,245));
		timePanel.setName("TimeTablePanel");
		timePanel.setBorder(null);
		
		scrollPane = new JScrollPane(timePanel);
		scrollPane.setBackground(new Color(250,251,245));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(215, 50));
		scrollPane.setBorder(null);
		//		
		constraints4 = new GridBagConstraints();
		constraints4.gridy = 0;	
		constraints4.gridy = 2;	
		constraints4.gridwidth= 2;
		constraints4.gridheight = 1;
		constraints4.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(timePanel, constraints1);
		base.add(scrollPane, constraints4);
		//
		constraints5 = new GridBagConstraints();
		constraints5.gridx = 0;
		constraints5.gridy = 3;
		constraints5.insets = new Insets(0, 0, 0, 0);
		constraints5.fill = GridBagConstraints.VERTICAL;
		constraints5.gridwidth = 2;
		constraints5.gridheight = 1;
		//
		jButton=getTimeSetButton();
		base.add(jButton, constraints5);
				
		this.add(base);
		//this.setSize(200, 100);
		this.setBackground(new Color(250,251,245));
	
		//
		// 観察される人を生成
		observableMan = new ObservableMan();
		// 観察者を追加
	
		observableMan.addObserver(new AutoPicture.ObserverA());
		observableMan.addObserver(new CheckComb.ObserverA());
		observableMan.addObserver(new CheckLabel.ObserverA());
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
		
		//setEnabledAll(true);
	}
	
	
	//
	public static void rearrangeTimeTable() {
//System.out.println("rearrangeTimeTable !!");	
//setSelected(true);
		for(int i=0;i<timeTable.size();i++){
		// Add to ArrayList
		DelDoubleComb dispComb= timeTable.get(i);		
		// delete mark in it
//System.out.println("Name ["+i+"]="+dispComb.getName());
		// Nameに、" X"が付いていたら、削除対象になっている。
		if(dispComb.getName().contains("X")){
//System.out.println("timeTable.size() A "+timeTable.size());
			timeTable.remove(i);
//			System.out.println(timeTable.size());	
			}
		}
		//
		timePanel.removeAll();
		timePanel.repaint();// 残像が残るので書き換えること。
//System.out.println("timeTable.size() B "+timeTable.size());
		int ts=timeTable.size();
		for(int i=0;i<ts;i++){
			DelDoubleComb delDoubleComb= timeTable.get(i);
			GridBagConstraints constraints=new GridBagConstraints();
			constraints.gridy = 0;	
			constraints.gridy = i;
			constraints.gridwidth= 1;
			constraints.gridheight = 1;
			constraints.insets = new Insets(0, 0, 0, 0);
			gridbagTimeTable.setConstraints(delDoubleComb, constraints);

			timePanel.add(delDoubleComb);
//System.out.println("timePanel.size() C "+timePanel.getComponentCount());
		}
/*
		timePanel.revalidate();
		timePanel.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		base.revalidate();
		base.repaint();		
*/
	}
	
	//
	public void setTime(int n, int h, int m)
	{
		// time set
		if(n<timeTable.size()){
		DelDoubleComb ddc=getTimeTableElement(n);
		if(ddc == null) ddc.add(new DelDoubleComb());
		ddc.setHour(h);
		ddc.setMin(m);
		}
	}

	private static int no=0;

	public ArrayList<DelDoubleComb> getTimeTable() {
		return timeTable;
	}

	public String[] getSchedule() {
		String[] s=new String[timeTable.size()];
		for(int i=0;i<timeTable.size();i++){
			DelDoubleComb ddc=timeTable.get(i);
			s[i]=String.valueOf(ddc.getHour())+":"+String.valueOf(ddc.getMin());
		}
		return s;
	}
	
	//
	public int addTimeSet(int i) {
		if(i<0) return i;
		if(i>=30) return i;
		// 時刻設定
		delDoubleComb=new DelDoubleComb();
		delDoubleComb.setBackground(new Color(250,251,245));
		// set name of number
		//delDoubleComb.setName(String.valueOf(i));
		//
		// default time set
		TimeDate td=new TimeDate();
        int h=td.getHour();
        int m=td.getMin();

		// 時刻設定
	        delDoubleComb=new DelDoubleComb();
	        delDoubleComb.setBackground(new Color(250,251,245));
			// set name of number
	        delDoubleComb.setName("TimeSet "+String.valueOf(no));
			//
			// default time set
			delDoubleComb.setHour(h);
			delDoubleComb.setMin(m);
			
			// Observerを設定する
			delDoubleComb.addObserver(new ChoiceSetting.ObserverA());
			
			//
			GridBagConstraints constraints0=new GridBagConstraints();
			constraints0.gridy = 0;	
			constraints0.gridy = no;
			gridbagTimeTable.setConstraints(delDoubleComb, constraints0);
			// Add to ArrayList
			timeTable.add(delDoubleComb);		
			activateTimePanel();

			no++;
			return no-1;
	}
	
	// TimePanelにTimeTableを付け加えて表示する
	private void activateTimePanel() {
		timePanel.removeAll();
		for(int i=0;i<timeTable.size();i++){
		timePanel.add(timeTable.get(i));
		}
		timePanel.revalidate();
	}

	public JButton getTimeSetButton() {
		if(jButton==null){
		jButton=new JButton();
		jButton.setText("時刻を追加する");
		jButton.setBorder(null);
		jButton.setName(jButton.getText());
		
		// set ActionListner
		jButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	//
                //System.out.println("時刻を追加する");
                message ="私はChoiceSetting classです。"+"時刻を追加する"+"　ボタンが押されました。 "+"true";
        		//
    			observableMan.setMessage(message);
    			// 観察者全員に通知
    			observableMan.notifyObservers();
    			
                jRadioButton.setSelected(true);//
                jRadioButton.setEnabled(true);//
                
//        		TimeSetを追加
            	addTimeSet(no);
            	rearrangeTimeTable();
                //
                
                message ="私はChoiceSetting classです。"+"お好み設定"+"　"+"true";
        		//
    			observableMan.setMessage(message);
    			// 観察者全員に通知
    			observableMan.notifyObservers();
                

            }});

		} 
		return jButton;
	}
	
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
		
	@Override
	public void update(Observable o, Object arg) {
		String message = (String) arg;
		
System.out.println("私はChoiceSetting classです。観察対象の通知を検知したよ。" + message);
		//
		//	num X
		// 	delete timeTable element
		//	timeTableからnumを削除
		//  

		if(message.contains(" X")){
			if(message.equals("deleteProcessing: DelBtn X ")==true) return;
			String[] no=message.split(" ");
			if(no.length<2) return;
			String ss=no[2];
			if(ss.equals("X")==true) return;
			int delNo=Integer.parseInt( no[2].replaceAll("[^0-9]","" ));
			if(delNo>=0 ){
//System.out.println("timeTable.size()="+timeTable.size());
			rearrangeTimeTable();
			
			if(scrollPane != null){
			scrollPane.repaint();
			scrollPane.revalidate();
			}
			//
			if(base != null){
			base.repaint();
			base.revalidate();
			}
			}
		}

		//	
		if(message.contains("true")==true && message.contains("ChoiceSetting")==false){
			if(jRadioButton != null){
				jRadioButton.setSelected(false);
				for(int i=0;i<timeTable.size();i++){
				timeTable.get(i).setEnabled(false);
				}
			}
		}
		
		if(observableMan!=null){
			// 観察者全員に通知
    		//
			observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
		  }
		}
	}

	public boolean getSelected() {
		return jRadioButton.isSelected();
	}

	public boolean isSelected() {
		return jRadioButton.isSelected();
	}

}
