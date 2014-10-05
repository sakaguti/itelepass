import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.* ;



public class IllumDisplay extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double illum=25000.0;// lux

	void setIllum(double t)
	{
		illum=t;
		String st=String.format("%1$5.0f", illum);
		illumPanel.setText(st+" lux"); // default temp value
		illumSlider.setIllumLevel(illum);
	}

	double getIllum()
	{
		return(illum);
	}
	
	private GJLabelPanel illumPanel=null;
	private IllumGJSlider illumSlider=null;
	// Constructor
	IllumDisplay() {
		//setBackground(ViewPanel.COLOR);
		// panel
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel basepanel = new JPanel(gridbag);
		//basepanel.setBackground(ViewPanel.COLOR);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;

		constraints.insets = new Insets(0, 0, 0, 0);
		
		// 水位計のアイコン表示
		String s = Path.getPath()+"/images/dt_illuminance.jpg";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\dt_illuminance.jpg";
//		JPanel iconPanel=new GPanel("/Users/sakaguti/Documents/workspace2/illumDisplay/src/images/dt_illuminance.jpg");
		JPanel iconPanel=new GPanel(s);
		gridbag.setConstraints(iconPanel, constraints);
		basepanel.add(iconPanel);
		iconPanel.addMouseListener(this);

		// 水位値の表示
		illumPanel=new GJLabelPanel();
		String st=String.format("%1$5.0f", illum);
		illumPanel.setText(st+" lux"); // default temp value
		constraints.gridx = 2;// 横位置が変わる
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.weightx = 100.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(illumPanel, constraints);
		basepanel.add(illumPanel);
		illumPanel.addMouseListener(this);

		// 水位値のslider表示		
		illumSlider=new IllumGJSlider();
		illumSlider.setIllumLevel(illum);
		constraints.gridx = 3;// 横位置が変わる
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0.0;
		gridbag.setConstraints(illumSlider, constraints);
		basepanel.add(illumSlider);
		illumSlider.addMouseListener(this);
		
		Container cont = this;
		//cont.setBackground(ViewPanel.COLOR);
		cont.setLayout(new BorderLayout());
		cont.add( basepanel, BorderLayout.CENTER );
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		IllumDisplay gjp=new IllumDisplay();
		frame.getContentPane().add(gjp);
		
		gjp.setIllum(25000.0);// for default water display

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			System.out.println("Draw illum Graph");
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
