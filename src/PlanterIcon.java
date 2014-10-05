import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

public class PlanterIcon extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		PlanterIcon gjp=new PlanterIcon("ITPLANTER00");
		
		gjp.setPlanterIcon("CamOFF");
		gjp.setPlanterIcon("CamON");
		gjp.setPlanterIcon("NoCamOFF");
		gjp.setPlanterIcon("NoCamON");
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
	private JLabel label=null;
	private JPanel iconNoCamOFF=null;
	private JPanel iconNoCamON=null;
	private JPanel iconCamOFF=null;
	private JPanel iconCamON=null;
	private JPanel currentPanel=null;
	private String filename="";
	
	public void setText(String s){
		label.setText(s);
	}
	
	/**
	 * This is the default constructor
	 */
	public PlanterIcon(String s) {
		super();
		filename=s;
		initialize();
	}
	
	
	public void setPlanterIcon(String s)
	{
		if(label==null) return;
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		
		if(s.matches("CamON")) currentPanel=iconCamON;
		else if(s.matches("CamOFF")) currentPanel=iconCamOFF;
		else if(s.matches("NoCamON")) currentPanel=iconNoCamON;
		else if(s.matches("NoCamOFF")) currentPanel=iconNoCamOFF;
		
//
		int fontSize=label.getFont().getSize();
		label.setPreferredSize(new Dimension(iconNoCamOFF.getWidth(),iconNoCamOFF.getWidth()-fontSize));
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		currentPanel.add(label, null);
		
		base.add(currentPanel);
		
		this.removeAll();
		this.add(base);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
	    //base.setOpaque(false);
	    
		// 水位計のアイコン表示
		String s1 = Path.getPath()+"/images//icon_itplanter_0.png";
		if(IsMacorWin.isMacOrWin()==false) s1 = Path.getPath()+"images\\/icon_itplanter_0.png";
		iconNoCamOFF=new GPanel(s1);
		String s2 = Path.getPath()+"/images//icon_itplanter_1.png";
		if(IsMacorWin.isMacOrWin()==false) s2 = Path.getPath()+"images\\/icon_itplanter_1.png";
		iconNoCamON=new GPanel(s2);
		String s3 = Path.getPath()+"/images//icon_itplanter_camera_0.png";
		if(IsMacorWin.isMacOrWin()==false) s3 = Path.getPath()+"images\\/icon_itplanter_camera_0.png";
		iconCamOFF=new GPanel(s3);
		String s4 = Path.getPath()+"/images//icon_itplanter_camera_1.png";
		if(IsMacorWin.isMacOrWin()==false) s4 = Path.getPath()+"images\\/icon_itplanter_camera_1.png";
		iconCamON=new GPanel(s4);
		currentPanel=iconCamON;
		
		label=new JLabel(filename);
		label.setOpaque(false);
//		System.out.println(iconNoCamOFF.getWidth()+" "+iconNoCamOFF.getWidth());
		Font fontlabel=new Font("Dialog", Font.PLAIN,10);
		label.setFont(fontlabel);
		
		int fontSize=label.getFont().getSize();
		setSize(new Dimension(iconNoCamOFF.getWidth(),iconNoCamOFF.getWidth()-fontSize));
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		currentPanel.add(label, null);
		
		base.add(currentPanel);
		this.add(base);
		
	}
	
	public void setPlanterName(String s)
	{
		label.setText(s);
	}

	public String getIconText() {
		return label.getText();
	}

	public String getPlanterName() {
		return label.getText();
	}

}
    
