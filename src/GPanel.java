
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.WindowConstants;
import javax.swing.JFrame;


public class GPanel extends javax.swing.JPanel {
	/**
	 * 	GPanel	: 	背景画像付きのJPanel
	 */
	private static final long serialVersionUID = 1L;

	private static String iconName="/Users/sakaguti/Documents/workspace2/ImageGuage/src/images/dt_temperature.jpg";  //  @jve:decl-index=0:
	/**
	 * This method initializes this
	 * 
	 */
	private BufferedImage backIcon = null;  //  @jve:decl-index=0:
	private void initialize(String iconName0) {
		try {
			//this.setLayout(new CardLayout());
			//this.setPreferredSize(new Dimension(73, 73));
			backIcon = ImageIO.read(new File(iconName0));
			} catch (Exception e) {
			  e.printStackTrace();
			}	
	}
	
	@Override
	public int getWidth()
	{
		return backIcon.getWidth();
	}

	@Override
	public int getHeight()
	{
		return backIcon.getHeight();
	}

	public void setImage(String s)
	{
		try {
			//this.setLayout(new CardLayout());
			//this.setPreferredSize(new Dimension(73, 73));
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
		
		// 表示する画像ファイル名を引数にしてインスタンスを生成する
		iconName = Path.getPath()+"images/dt_temperature.jpg";
		if(IsMacorWin.isMacOrWin()==false) iconName = Path.getPath()+"images\\dt_temperature.jpg";

		GPanel gjp=new GPanel(iconName, "Title-------");
		
		
		frame.getContentPane().add(gjp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public GPanel(String iconname, String t) {
		super();
		initialize(iconname);
		setTitleText(t);
		initGUI();
	}
	
	public GPanel(String iconname) {
		super();
		initialize(iconname);
		initGUI();
	}	
	
	private boolean stringsw=false;
	private String title="";
	public void setTitleText(String t)
	{
		stringsw=true;
		title=t;
	}
	
	@Override
	public void paintComponent(Graphics g) {	
		Graphics2D g2 = (Graphics2D)g;		
		g2.drawImage(backIcon, (this.getWidth()-backIcon.getWidth())/2, (this.getHeight()-backIcon.getHeight())/2, this);
		if(stringsw==true){
		g.drawString(title, 50, 300);
		}
	  }

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(backIcon.getWidth(), backIcon.getHeight()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}//
