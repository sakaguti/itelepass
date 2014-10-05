import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DisplayComb extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DisplayComb gjp=new DisplayComb();
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private JLabel doubleColoum;

	/**
	 * This is the default constructor
	 */
	public DisplayComb() {
		super();
		initialize();
	}
	
	private JLabel hour=null;
	private JLabel min=null;
	
	public void setHour(int i)
	{
		hour.setText(String.valueOf(i));
	}
	
	public void setMin(int i)
	{
		min.setText(String.valueOf(i));
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		this.setLayout(new GridBagLayout());
		hour = new JLabel("10");
		this.add(hour, gridBagConstraints1);
		//
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.gridy = 0;
		doubleColoum = new JLabel();
		doubleColoum.setText(":");
		//
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints2.gridx = 2;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.weightx = 1.0;
		min = new JLabel("10");
		this.add(min, gridBagConstraints2);
		//
		this.add(doubleColoum, gridBagConstraints3);	
		this.setBackground(new Color(216,241,254));
		this.setSize(200, 50);
		this.setPreferredSize(new Dimension(200,50));
	}

}
