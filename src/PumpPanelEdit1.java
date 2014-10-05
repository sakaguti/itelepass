
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class PumpPanelEdit1 extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		PumpPanelEdit1 gjp=new PumpPanelEdit1();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public PumpPanelEdit1() {
		super();
		initialize();
	}

	private GJPanel gJPanel=null;
	private InfoDispEdit idisp1=null;
	private InfoDispEdit idisp2=null;
//	private JButton addTimeButton=null;
	
	
	@Override
	public void setEnabled(boolean b)
	{
		idisp1.setEnabled(b);
		idisp2.setEnabled(b);
	}
	
	public Double getWaterWarn()
	{
		return idisp1.getValue();// Water Warnning
	}
	
	public void setWaterWarn(double pw)
	{
		int i;
		i=(int)((pw-0.0)/5.0+0.5);
		if(i<0) i=0;
		if(i>50) i=50;
		idisp1.setSelectedIndex(i);//  Water Warnning
	}

	
	public Double getPumpWorkTime()
	{
		return idisp2.getValue();// Pump Work Time
		//return 1.0; // 一先ず１分を返す。
	}
	
	public void setPumpWorkTime(double pw)
	{
		int i;
		i=(int)((pw-60.0)/5.0+0.5);
		if(i<0) i=0;
		if(i>60) i=60;
		idisp2.setSelectedIndex(i);// Pump Work Time
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
		JPanel base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		this.setBackground(new Color(250,251,245));
		// default size
		//base.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
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
		String st = Path.getPath()+"/images//program_h6_bg_water.gif";
		if(IsMacorWin.isMacOrWin()==false) st = Path.getPath()+"images\\/program_h6_bg_water.gif";

		gJPanel = new GJPanel(st);
		//"/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_water.gif");
		gJPanel.setText("ポンプ");
	
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(gJPanel, constraints2);
		base.add(gJPanel);
		
		
		// panel
		JPanel jp1=new JPanel();
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp1.setLayout(gridLayout);
		jp1.setPreferredSize(new Dimension(160,50));
		jp1.setBackground(Color.gray);
		

		idisp1=new InfoDispEdit();
		String[] s=new String[21];
		for(int i=0;i<=100;i+=5){
			s[i/5]=String.valueOf(i);
		}
		idisp1.setSelectionItems(s);
		idisp1.setText("警告水位");
		idisp1.setSelectedIndex(7);
		idisp1.setUnit("％");
		
		//
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridy = 0;	
		constraints3.gridy = 2;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(idisp1, constraints3);
		
		if(Version.getRevision().equals("Education")!=true)
			base.add(idisp1);
		
	
		// ポンプの動作時間を表示する
		
		idisp2=new InfoDispEdit();

		String[] t=new String[40];
		for(int i=60;i<=255;i+=5){
			t[(i-60)/5]=String.valueOf(i);
		}
		idisp2.setSelectionItems(t);
		idisp2.setText("ポンプ動作時間");
		idisp2.setSelectedIndex(0);
		idisp2.setUnit("秒");
		//idisp2.setBorder(getBorder());
		
		//
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.gridy = 0;	
		constraints4.gridy = 3;	
		constraints4.gridwidth= 1;
		constraints4.gridheight = 1;
		constraints4.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(idisp2, constraints4);
		base.add(idisp2);
		

		//
		this.add(base);

		
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// 観察者を追加
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
	}
	
	public void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}
	

	
	private ObservableMan observableMan=null;  
//	private String message="";
	
	/**
	* 観察者を観察する人A。
	*
	*/
	public static class ObserverA implements Observer {
		String name="";
		public void setName(String s){
			name=s;
		}
		
		// override して変更するメソッド
		@Override
		public void update(Observable o, Object arg) {	
			String str = (String) arg;
			System.out.println("私はPumpPanelEdit1 classです。観察対象の通知を検知したよ。" + str);
				}// update
		}
}
