import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DisplayFromTo extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DisplayFromTo gjp=new DisplayFromTo();
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public DisplayFromTo() {
		super();
		initialize();
	}
	
	private DisplayComb from=null;
	private DisplayComb to=null;

	public void setFromHour(int i)
	{
		from.setHour(i);
	}
	
	public void setFromMin(int i)
	{
		from.setMin(i);
	}
	
	public void setToHour(int i)
	{
		to.setHour(i);
	}
	
	public void setToMin(int i)
	{
		to.setMin(i);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		int width=150;
		//
		JPanel base=new JPanel();
		base.setBackground(Color.white);
		base.setLayout(new GridBagLayout());
		//
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;

		// ON label
		JPanel onPanel=new JPanel();
		onPanel.setBackground(Color.white);
		onPanel.setForeground(Color.gray);
		JLabel onLabel=new JLabel("ON");
		onLabel.setPreferredSize(new Dimension(width,10));
		onPanel.add(onLabel, gridBagConstraints1);
		onPanel.setLocation(0, 0);

		base.add(onPanel, gridBagConstraints1);
		//
		gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.weightx = 1.0;

		from = new DisplayComb();
		from.setPreferredSize(new Dimension(width,30));
		from.setBackground(Color.yellow);
		base.add(from, gridBagConstraints1);
		//
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 2;
		JLabel arrow= new JLabel("Å´");
		base.add(arrow, gridBagConstraints2);	
	
		// OFF label
		JPanel offPanel=new JPanel();
		offPanel.setBackground(Color.gray);
		offPanel.setForeground(Color.white);
		JLabel offLabel=new JLabel("OFF");
		offLabel.setPreferredSize(new Dimension(width,10));
		offPanel.add(offLabel);
		gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 3;
		gridBagConstraints1.weightx = 1.0;
		base.add(offPanel, gridBagConstraints1);
		
		//
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.gridy = 4;
		gridBagConstraints3.weightx = 1.0;
		to = new DisplayComb();
		to.setBackground(Color.yellow);
		to.setPreferredSize(new Dimension(width,30));
		base.add(to, gridBagConstraints3);
		//
		this.add(base);
		this.setBackground(Color.white);
		this.setSize(200, 150);
		this.setPreferredSize(new Dimension(width,150));
	}

}
