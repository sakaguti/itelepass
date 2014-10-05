
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class TempPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		TempPanel gjp=new TempPanel();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public TempPanel() {
		super();
		initialize();
	}

	public GJPanel gJPanel=null;
	public InfoDisp ifD=null;
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		TempPanelEdit1 gjp=new TempPanelEdit1();
		gjp.setEnabled(false);
		this.add(gjp);

/*	
		GridBagLayout gridbag = new GridBagLayout();
		JPanel jp=new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		// default size
//		jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//		jp.setPreferredSize(new Dimension(200,320));
		
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);

		// separator
		JSeparator vspr=new JSeparator(JSeparator.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(200, 20));
		gridbag.setConstraints(vspr, constraints1);
		jp.add(vspr);

		
		// panel1
		gJPanel = new GJPanel("/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_temperature.gif");

		constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 1;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(gJPanel, constraints1);
		jp.add(gJPanel);
		
		
		// panel
		JPanel jp1=new JPanel();
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		jp1.setLayout(gridLayout);
		
//		jp1.setPreferredSize(new Dimension(gJPanel.getWidth(),gJPanel.getHeight()));
//		jp1.setPreferredSize(new Dimension(160,50));
		jp1.setBackground(Color.gray);
	
//
		InfoDisp ifD=new InfoDisp();

		//
		constraints1 = new GridBagConstraints();
		constraints1.gridy = 0;	
		constraints1.gridy = 2;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ifD, constraints1);
		jp.add(ifD);
		
		//
		this.add(jp);
//		this.setSize(200, 400);
//		this.setPreferredSize(new Dimension(200,400));
		this.setLayout(new BorderLayout());
		this.add( jp, BorderLayout.CENTER );
*/
	}

}
