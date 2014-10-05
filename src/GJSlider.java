
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


public class GJSlider extends javax.swing.JPanel {
	/**
	 * 	GJSlider	: 	”wŒi‰æ‘œ•t‚«‚ÌJSlider‚ğ‚Á‚½JPanel
	 */
	private static final long serialVersionUID = 1L;

	private Icon iconGuage=null;
	private BufferedImage readImage = null;
	private int x=200;
	// Slider‚ÌGauge‰æ‘œ
	private String guageImage="/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/gauge_line.gif";
	// Slider‚Ì”wŒi‰æ‘œ
	private String sliderImage="/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/gauge_bg_temperature.jpg";
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		guageImage = Path.getPath()+"images/gauge_line.gif";
	
		iconGuage = new ImageIcon(guageImage);
		try {
			sliderImage = Path.getPath()+"images/gauge_bg_temperature.jpg";
		  readImage = ImageIO.read(new File(sliderImage));
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
		GJSlider gjp=new GJSlider("/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/gauge_bg_temperature.jpg");
		frame.getContentPane().add(gjp);
		
		gjp.setTemp(24.0);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public GJSlider(String s) {
		super();
		sliderImage=s;
		initialize();
		initGUI();
	}
	
	private double scale=10.0*19.0/20.0;
	private double temp=0.0;
	
	public void setScale(double s)
	{
		scale = s;
	}
	
	public void setTemp(double t)
	{
		temp=t;
		int y = (int)(scale*temp+0.5);
		setValue(y);
		repaint();
	}

	// 
	public void setValue(double v)
	{	
		x=(int)(v+0.5);
		if( x<8  ) x=8;
		if( x>369) x=369;
	}
	
	//
	public int getValue()
	{
		return x;
	}

	// Œ»İ‚Ì‰·“x‚ğ“¾‚é
	public double getTemp()
	{
		return temp;
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
