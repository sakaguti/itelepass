
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;

public class GJPanel extends javax.swing.JPanel {
	/**
	 * 	GJPanel	: 	背景画像付きのJLabelを持ったJPanel
	 */
	private static final long serialVersionUID = 1L;

	private JLabel jLabel = null;
	/**
	 * This method initializes this
	 * 
	 */
	private BufferedImage backIcon = null; 
	private void initialize(String s) {
		if( s == null ){
		try {
			//this.setLayout(new CardLayout());
			//this.setPreferredSize(new Dimension(73, 73));
			String st = Path.getPath()+"/images/program_h6_bg_temperature.gif";
			if(IsMacorWin.isMacOrWin()==false) st = Path.getPath()+"images\\program_h6_bg_temperature.gif";
//			backIcon = ImageIO.read(new File("/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_temperature.gif"));
			backIcon = ImageIO.read(new File(st));
			} catch (Exception e) {
			  e.printStackTrace();
			}	
		} else {
			try {
				//this.setLayout(new CardLayout());
				//this.setPreferredSize(new Dimension(73, 73));
				backIcon = ImageIO.read(new File(s));
				} catch (Exception e) {
				  e.printStackTrace();
				}	

		}
			
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 0;
			gridBagConstraints2.gridheight = 0;
			
        jLabel = new JLabel();
        jLabel.setText("温度");
        jLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        // 文字が黒くならないのは何故か？
        jLabel.setForeground(new Color(0, 0, 0));
        jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
        jLabel.setEnabled(false);
        // Text Position
        jLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel.setVerticalTextPosition(0);
        //
        jLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.setLayout(new GridBagLayout());
		this.add(jLabel, gridBagConstraints2);
	}
	
	private void initialize() {
			
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.gridwidth = 0;
		gridBagConstraints2.gridheight = 0;
			
        jLabel = new JLabel();
        jLabel.setText("温度");
        jLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        // 文字が黒くならないのは何故か？
        jLabel.setForeground(new Color(0, 0, 0));
        jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
        jLabel.setEnabled(false);
        // Text Position
        jLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel.setVerticalTextPosition(0);
        //
        jLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.setLayout(new GridBagLayout());
		this.add(jLabel, gridBagConstraints2);
	}
	
	
	public void setText(String t)
	{
		jLabel.setText(t);
	}

	public String getText()
	{
		return jLabel.getText();
	}
	
	public void setImage(String s)
	{
		try {
			backIcon = ImageIO.read(new File(s));
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
		GJPanel gjp=new GJPanel(null);
		frame.getContentPane().add(gjp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public GJPanel(String s) {
		super();
		initialize(s);
		initGUI();
	}
	
	public GJPanel() {
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
