import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class PlantPrg extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		PlantPrg gjp=new PlantPrg();
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public PlantPrg() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		Container c = this;

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		JPanel base=new JPanel();
		base.setBackground(new Color(250,251,245));

		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		
		// panel
		TempPanel tempPanel=new TempPanel();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 0;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(tempPanel, constraints1);
		tempPanel.setPreferredSize(new Dimension(200,600));
		base.add(tempPanel);
		//
		// separator
		JSeparator vspr=new JSeparator(SwingConstants.VERTICAL);
		vspr.setPreferredSize(new Dimension(10, 200));
		base.add(vspr);
		// panel
		PumpPanel pumpPanel=new PumpPanel();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 1;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(pumpPanel, constraints1);
		pumpPanel.setPreferredSize(new Dimension(200,600));
		base.add(pumpPanel);
		// separator
		JSeparator vspr2=new JSeparator(SwingConstants.VERTICAL);
		vspr2.setPreferredSize(new Dimension(10, 200));
		base.add(vspr2);

		
		// panel
		illumPanel illumPane=new illumPanel();
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 2;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumPane, constraints1);
		illumPane.setPreferredSize(new Dimension(200,600));
		base.add(illumPane);
		//
		c.add(base);
		c.setBackground(Color.white);
		c.setSize(660, 600);
		c.setPreferredSize(new Dimension(660,600));
	}

}
