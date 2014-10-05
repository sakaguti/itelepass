
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.JFrame;


public class WaterGJSlider extends javax.swing.JPanel {
	/**
	 * 	GJSlider	: 	îwåiâÊëúïtÇ´ÇÃJSliderÇéùÇ¡ÇΩJPanel
	 */
	private static final long serialVersionUID = 1L;

	private Icon iconGuage=null;
	private BufferedImage readImage = null;
	private int x=100;
	
	/**
	 * This method initializes this
	 * 
	 */

	private void initialize() {
		String s = Path.getPath()+"/images/gauge_line.gif";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\gauge_line.gif";
//		iconGuage = new ImageIcon("/Users/sakaguti/Documents/workspace2/waterDisplay/src/images/gauge_line.gif");
		iconGuage = new ImageIcon(s);
		try {
			String t = Path.getPath()+"/images/gauge_bg_water.jpg";
			if(IsMacorWin.isMacOrWin()==false) t = Path.getPath()+"images\\gauge_bg_water.jpg";
//		  readImage = ImageIO.read(new File("/Users/sakaguti/Documents/workspace2/waterDisplay/src/images/gauge_bg_water.jpg"));
		  readImage = ImageIO.read(new File(t));
		} catch (Exception e) {
		  e.printStackTrace();
		  readImage = null;
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		WaterGJSlider gjp=new WaterGJSlider();
		frame.getContentPane().add(gjp);
		
		gjp.setWaterLevel(85.0);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public WaterGJSlider() {
		super();
		initialize();
		initGUI();
	}
	public void setWaterLevel(double t)
	{
		int y = (int)(t*3.9+0.5);
		setValue(y);
		repaint();
	}

	public void setValue(double v)
	{	
		x=(int)(v+0.5);
		if( x<8  ) x=8;
		if( x>369) x=369;
	}
	
	public int getValue()
	{
		return x;
	}

	public double getWaterLevel()
	{
		double y=getValue();
		y=y;
		return y;
	}

	@Override
	public void paintComponent(Graphics g) {	
		Graphics2D g2 = (Graphics2D)g;		
		g2.drawImage(readImage, 0, 0, this);

		this.setValue(x);
		iconGuage.paintIcon(this, g , x , 0);
		}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(readImage.getWidth(), readImage.getHeight()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}//
