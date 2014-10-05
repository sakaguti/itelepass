import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.* ;

public class WaterDisplay extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double water=50.0;// %

	void setWater(double t)
	{
		water=t;
		String st=String.format("%1$4.1f", water);
		waterLevelPanel.setText(st+"%"); // default temp value
//		waterLevelPanel.setText(String.valueOf(water)+"%"); // default temp value
		waterSlider.setWaterLevel(water);
	}

	double getWater()
	{
		return(water);
	}

	private GJLabelPanel waterLevelPanel=null;
	private WaterGJSlider waterSlider=null;
	// Constructor
	WaterDisplay() {


		// panel
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel basepanel = new JPanel(gridbag);


		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;

		constraints.insets = new Insets(0, 0, 0, 0);
		
		// 水位計のアイコン表示
		String s = Path.getPath()+"/images/dt_water.jpg";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\dt_water.jpg";
//		JPanel iconPanal=new WaterGPanel("/Users/sakaguti/Documents/workspace2/waterDisplay/src/images/dt_water.jpg");
		JPanel iconPanel=new WaterGPanel(s);
		gridbag.setConstraints(iconPanel, constraints);
		basepanel.add(iconPanel);
		iconPanel.addMouseListener(this);

		// 水位値の表示
		waterLevelPanel=new GJLabelPanel();
		String st=String.format("%1$4.1f", water);
		waterLevelPanel.setText(st+"%"); // default temp value
		constraints.gridx = 2;// 横位置が変わる
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.weightx = 100.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(waterLevelPanel, constraints);
		basepanel.add(waterLevelPanel);
		waterLevelPanel.addMouseListener(this);

		// 水位値のslider表示		
		waterSlider=new WaterGJSlider();
		waterSlider.setWaterLevel(water);
		constraints.gridx = 3;// 横位置が変わる
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0.0;
		gridbag.setConstraints(waterSlider, constraints);
		basepanel.add(waterSlider);
		waterSlider.addMouseListener(this);
		
		Container cont = this;
		cont.setLayout(new BorderLayout());
		cont.add( basepanel, BorderLayout.CENTER );
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		WaterDisplay gjp=new WaterDisplay();
		frame.getContentPane().add(gjp);
		
		gjp.setWater(0.0);// for default water display
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Draw water Graph");
		
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
