import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.* ;


public class tempDisplay extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double temp=24.0;

	void setTemp(double t)
	{
		temp=t;
		String st=String.format("%1$4.1f", temp);
		tempValuePanel.setText(st+"℃"); // default temp value
		tempSlider.setTemp(temp);
	}

	double getTemp()
	{
		return(temp);
	}

	GJLabelPanel tempValuePanel=null;
	GJSlider tempSlider=null;
	
	// Constructor
	tempDisplay() {

		// panel
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel basepanel = new JPanel(gridbag);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;

		constraints.insets = new Insets(0, 0, 0, 0);
		
		// 温度計のアイコン表示
		String s = Path.getPath()+"images/dt_temperature.jpg";
		if(IsMacorWin.isMacOrWin() == false) s = Path.getPath()+"images\\dt_temperature.jpg";
		//System.out.println(IsMacorWin.isMacOrWin());
		
//		JPanel iconPanal=new GPanel("/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/dt_temperature.jpg");
//		System.out.println(s);
		JPanel iconPanel=new GPanel(s);
		gridbag.setConstraints(iconPanel, constraints);
		basepanel.add(iconPanel);
		iconPanel.addMouseListener(this);

		// 温度値の表示
		tempValuePanel=new GJLabelPanel();
		String st=String.format("%1$4.1f", temp);
		tempValuePanel.setText(st+"℃"); // default temp value
		constraints.gridx = 2;// 横位置が変わる
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.weightx = 100.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(tempValuePanel, constraints);
		tempValuePanel.addMouseListener(this);
		basepanel.add(tempValuePanel);

		// 温度値のslider表示		
		tempSlider=new GJSlider("/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/gauge_bg_temperature.jpg");
		tempSlider.setTemp(temp);
		constraints.gridx = 3;// 横位置が変わる
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0.0;
		gridbag.setConstraints(tempSlider, constraints);
		tempSlider.addMouseListener(this);
		basepanel.add(tempSlider);
		
		// Layoutにconstraintを付け加える
		Container cont = this;
		cont.setLayout(new BorderLayout());
		cont.add( basepanel, BorderLayout.CENTER );
		
		//
		
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		tempDisplay tmpDisp=new tempDisplay();
		frame.getContentPane().add(tmpDisp);
		
		tmpDisp.setTemp(20.0);// for default temp display
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	System.out.println("Draw temp Graph");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//System.out.println("mousePressed Draw Graph");
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
