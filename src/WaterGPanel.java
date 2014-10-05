
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import java.awt.CardLayout;

public class WaterGPanel extends javax.swing.JPanel {
	/**
	 * 	GPanel	: 	背景画像付きのJPanel
	 */
	private static final long serialVersionUID = 1L;

	private static String iconName="/Users/sakaguti/Documents/workspace2/waterDisplay/src/images/dt_water.jpg";  //  @jve:decl-index=0:
	/**
	 * This method initializes this
	 * 
	 */
	private BufferedImage backIcon = null;  
	private void initialize(String iconName) {
		try {
			this.setLayout(new CardLayout());
			this.setPreferredSize(new Dimension(73, 73));
			backIcon = ImageIO.read(new File(iconName));
			} catch (Exception e) {
			  e.printStackTrace();
			}	
	}
	
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		// 表示する画像ファイル名を引数にしてインスタンスを生成する
		WaterGPanel gjp=new WaterGPanel(iconName);
		
		frame.getContentPane().add(gjp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public WaterGPanel(String iconname) {
		super();
		initialize(iconname);
		initGUI();
	}
	
	@Override
	public void paintComponent(Graphics g) {	
		Graphics2D g2 = (Graphics2D)g;		
		g2.drawImage(backIcon, (this.getWidth()-backIcon.getWidth())/2, (this.getHeight()-backIcon.getHeight())/2, this);
	  }

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(backIcon.getWidth(), backIcon.getHeight()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}//
