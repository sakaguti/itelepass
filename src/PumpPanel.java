import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.WindowConstants;

public class PumpPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		PumpPanel gjp=new PumpPanel();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public PumpPanel() {
		super();
		initialize();
	}

	public GJPanel gJPanel=null;
	public InfoDisp ifD=null;
	public CardDisplay cdisp=null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		Container c = this;

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		JPanel jp=new JPanel();
		jp.setBackground(new Color(250,251,245));
		// default size
		jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp.setPreferredSize(new Dimension(200,320));
		
		// separator
		/*
		JSeparator vspr=new JSeparator(JSeparator.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(200, 20));
		jp.add(vspr);
		*/
		
		// panel1
		String s = Path.getPath()+"/images/program_h6_bg_water.gif";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\program_h6_bg_water.gif";
		gJPanel = new GJPanel(s);
//		"/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_water.gif");
		gJPanel.setText("ポンプ");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 1;
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
		jp1.setPreferredSize(new Dimension(160,50));
		jp1.setBackground(Color.gray);
		
		/*
		JLabel jl1=new JLabel("警告水位");
		jl1.setHorizontalTextPosition(SwingConstants.CENTER);
		jl1.setVerticalTextPosition(SwingConstants.CENTER);
		jl1.setHorizontalAlignment(SwingConstants.CENTER);
		jl1.setForeground(null);
		jp1.add(jl1, null);
		constraints1.gridx = 0;
		constraints1.gridy = 2;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jp1, constraints1);
		jp.add(jp1);
		
		//
		JPanel jp2=new JPanel();
		jp2.setBackground(new Color(250,251,245));
		JLabel lb2=new JLabel("10");
		lb2.setBackground(Color.gray);
		lb2.setPreferredSize(new Dimension(80,20));
		lb2.setHorizontalAlignment(SwingConstants.RIGHT);
		lb2.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		JLabel lb3=new JLabel("％");
		lb3.setBackground(new Color(250,251,245));
		lb3.setHorizontalAlignment(SwingConstants.RIGHT);
		lb3.setHorizontalTextPosition(SwingConstants.RIGHT);
		lb3.setPreferredSize(new Dimension(40,20));
		
		constraints1.gridy = 0;	
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(lb2, constraints1);
		jp2.add(lb2);
		
		constraints1.gridy = 1;	
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(lb3, constraints1);
		jp2.add(lb3);
		*/
		ifD=new InfoDisp();
		ifD.setText1("警告水位");
		ifD.setText2("10");
		ifD.setText3("％");
		//
		constraints1.gridy = 0;	
		constraints1.gridy = 2;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD, constraints1);
		jp.add(ifD);
		
		//
		// ポンプの動作時刻を表示する
		cdisp=new CardDisplay();
		constraints1.gridy = 0;	
		constraints1.gridy = 3;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(cdisp, constraints1);
		jp.add(cdisp);
		
		//
		this.add(jp);
		this.setSize(200, 400);
		this.setPreferredSize(new Dimension(200,400));
		this.setLayout(new BorderLayout());
		c.add( jp, BorderLayout.CENTER );
	}

}
