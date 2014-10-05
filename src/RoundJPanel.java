import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class RoundJPanel extends JPanel {
	//
	//	äpÇÃä€Ç¢JPanel
	//
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			// é©ìÆéBâeÅ@ÅFÅ@àÍíËéûä‘Ç≤Ç∆
			JFrame frame = new JFrame();
			RoundJPanel gjp=new RoundJPanel();
			JLabel jlb=new JLabel("Hello");
			gjp.add(jlb);
			
			frame.getContentPane().add(gjp);

			
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			//frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public RoundJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setBackground(new Color(250,251,245));
		this.setLayout(new GridBagLayout());
	}
	
	private double r1=40.0;
	private double r2=40.0;
	
	public void setR1(double r)
	{
		r1=r;
	}
	public void setR3(double r)
	{
		r2=r;
	}
	public void setR(double r)
	{
		r1=r;
		r2=r;
	}
	
	public double getR1()
	{
		return r1;
	}

	public double getR3()
	{
		return r2;
	}
	
	@Override
	  public void paintComponent(Graphics g){
		    Graphics2D g2 = (Graphics2D)g;

		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
		                        RenderingHints.VALUE_ANTIALIAS_ON);

		    BasicStroke normalStroke = new BasicStroke(1.0f);
		    g2.setStroke(normalStroke);

		    g2.setPaint(this.getBackground());
		    RoundRectangle2D.Double rec2 = 
		      new RoundRectangle2D.Double(this.getAlignmentX(), this.getAlignmentY(), this.getWidth(), this.getHeight(), r1, r2);
		    g2.fill(rec2);
		  }

}
