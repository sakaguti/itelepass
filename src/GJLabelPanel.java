
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.CardLayout;

public class GJLabelPanel extends javax.swing.JPanel {
	/**
	 * 	GJPanel	: 	îwåiâÊëúïtÇ´ÇÃJLabelÇéùÇ¡ÇΩJPanel
	 */
	private static final long serialVersionUID = 1L;

	private JLabel jLabel = null;

	/**
	 * This method initializes this
	 * 
	 */
	private BufferedImage backIcon = null;  
	private void initialize() {
		try {
			this.setLayout(new CardLayout());
			//this.setPreferredSize(new Dimension(73, 73));
			String s = Path.getPath()+"/images/dd_bg_temperature.jpg";
			if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\dd_bg_temperature.jpg";
//			backIcon = ImageIO.read(new File("/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/dd_bg_temperature.jpg"));
			backIcon = ImageIO.read(new File(s));
			} catch (Exception e) {
			  e.printStackTrace();
			}	

        jLabel = new JLabel();
        jLabel.setText("24Åé");
        jLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        jLabel.setForeground(new Color(255, 255, 255));
        jLabel.setBorder(null);
        jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
        jLabel.setEnabled(false);
        jLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setName("GJLabel");
		//jLabel.setPreferredSize(new Dimension(73, 73));
		jLabel.setOpaque(false);
		this.add(jLabel, jLabel.getName());
	}
	
	public void setText(String t)
	{
		jLabel.setText(t);
	}

	public String getText()
	{
		return jLabel.getText();
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GJLabelPanel gjp=new GJLabelPanel();
		frame.getContentPane().add(gjp);
		gjp.setText("30Åé");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public GJLabelPanel() {
		super();
		initialize();
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
