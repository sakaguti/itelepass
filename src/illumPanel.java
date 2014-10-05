import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;

public class illumPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		illumPanel gjp=new illumPanel();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public illumPanel() {
		super();
		initialize();
	}

	public GJPanel gJPanel=null;
	public InfoDisp ifD1=null;
	public InfoDisp ifD2=null;
	public InfoDisp ifD3=null;
//	public DisplayFromTo dispComb=null;
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel jp=new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		// default size
//		jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//		jp.setPreferredSize(new Dimension(200,320));
		
		// separator
		/*
		JSeparator vspr=new JSeparator(JSeparator.HORIZONTAL);
//		vspr.setPreferredSize(new Dimension(200, 20));
		jp.add(vspr);
*/
		
		// panel1
		String st = Path.getPath()+"/images//program_h6_bg_illuminance.gif";
		if(IsMacorWin.isMacOrWin()==false) st = Path.getPath()+"images\\/program_h6_bg_illuminance.gif";

		gJPanel = new GJPanel(st);
		gJPanel.setText("LEDライト");

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(gJPanel, constraints1);
		jp.add(gJPanel);
		
		
		// panel
		JPanel jp1=new JPanel();
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp1.setLayout(gridLayout);
//		jp1.setPreferredSize(new Dimension(gJPanel.getWidth(),gJPanel.getHeight()));
//		jp1.setPreferredSize(new Dimension(160,50));
		jp1.setBackground(Color.gray);
	
//
		InfoDisp ifD1=new InfoDisp();
		ifD1.setText1("警告下限照度");
		ifD1.setText2("780");
		ifD1.setText3("lux");
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 1;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD1, constraints1);
		jp.add(ifD1);
		
	//
		InfoDisp ifD2=new InfoDisp();
		ifD2.setText1("点灯周期");
		ifD2.setText2("1");
		ifD2.setText3("kHz");
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 2;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD2, constraints1);
		jp.add(ifD2);
		
		//
		InfoDisp ifD3=new InfoDisp();
		ifD3.setText1("点灯デュティー比");
		ifD3.setText2("63");
		ifD3.setText3("％");
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD3, constraints1);
		jp.add(ifD3);
		
		//
		InfoDisp ifD5=new InfoDisp();
		ifD5.setText1("警告上限温度");
		ifD5.setText2("30");
		ifD5.setText3("℃");
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 4;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD5, constraints1);
		jp.add(ifD5);

		//
		InfoDisp ifD4=new InfoDisp();
		ifD4.setText1("LEDライト点灯時刻");
		ifD4.setText2("");
		ifD4.setText3("");
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 5;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD4, constraints1);
		jp.add(ifD4);
		
		//
		// ここで　ONの時間とOFFの時間を設定されている数だけ表示する。
		//
		int n=1;
		for(int i=0;i<n;i++){
		DisplayFromTo dispComb=new DisplayFromTo();
		//
		dispComb.setFromHour(i);
		dispComb.setFromMin(i);
		dispComb.setToHour(i);
		dispComb.setToMin(i);
		//
		// ここで　ONの時間とOFFの時間を設定する。
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 6+i;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(dispComb, constraints1);
		jp.add(dispComb);
		}
		
		//
//		this.add(jp);
//		this.setSize(200, 600);
//		this.setPreferredSize(new Dimension(200,600));
		this.setLayout(new BorderLayout());
		this.add( jp, BorderLayout.CENTER );
	}

}
