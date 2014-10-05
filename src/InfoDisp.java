
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class InfoDisp extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		InfoDisp gjp=new InfoDisp();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public InfoDisp() {
		super();
		initialize();
	}

	private JLabel jl1=null;
	private JLabel jl2=null;
	private JLabel jl3=null;
	
	void setText1(String s)
	{
		jl1.setText(s);
	}
	void setText2(String s)
	{
		jl2.setText(s);
	}
	void setText3(String s)
	{
		jl3.setText(s);
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
		JPanel jp=new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		
		// panel
		GridBagLayout gridbag1 = new GridBagLayout();
		JPanel jp1=new JPanel(gridbag1);
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp1.setLayout(gridLayout);
//		jp1.setPreferredSize(new Dimension(gJPanel.getWidth(),gJPanel.getHeight()));
		jp1.setPreferredSize(new Dimension(160,40));
		jp1.setBackground(Color.gray);
		
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag1.setConstraints(jp1, constraints1);
		
		jl1=new JLabel("åxçêè„å¿â∑ìx");
		jl1.setHorizontalTextPosition(SwingConstants.CENTER);
		jl1.setVerticalTextPosition(SwingConstants.CENTER);
		jl1.setHorizontalAlignment(SwingConstants.CENTER);
		jl1.setForeground(null);
		gridbag1.setConstraints(jl1, constraints1);
		jp1.add(jl1);
		

		constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 1;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jp1, constraints1);
		jp.add(jp1);
		
		//
		GridBagLayout gridbag2 = new GridBagLayout();
		JPanel jp2=new JPanel(gridbag2);
		jp2.setBackground(new Color(250,251,245));
		jl2=new JLabel("38");
		jl2.setBackground(Color.gray);
		jl2.setPreferredSize(new Dimension(80,20));
		jl2.setHorizontalAlignment(SwingConstants.RIGHT);
		jl2.setHorizontalTextPosition(SwingConstants.RIGHT);
		constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag2.setConstraints(jl2, constraints1);
		jp2.add(jl2);
		
		jl3=new JLabel("Åé");
		jl3.setBackground(new Color(250,251,245));
		jl3.setHorizontalAlignment(SwingConstants.RIGHT);
		jl3.setHorizontalTextPosition(SwingConstants.RIGHT);
		jl3.setPreferredSize(new Dimension(40,20));
		
		constraints1 = new GridBagConstraints();
		constraints1.gridx = 1;	
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag2.setConstraints(jl3, constraints1);
		jp2.add(jl3);
		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 2;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jp2, constraints1);
		jp.add(jp2);
		//
		this.add(jp);
		//this.setSize(200, 100);
		//this.setPreferredSize(new Dimension(200,100));
		//this.setLayout(new BorderLayout());
		//this.add( jp, BorderLayout.CENTER );
	}

}
