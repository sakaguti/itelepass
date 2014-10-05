
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PrgAbs extends JPanel {

	private static final long serialVersionUID = 1L;

	public  static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PrgAbs());
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

	}

	/**
	 * This is the default constructor
	 */
	public PrgAbs() {
		super();
		initialize();
	}

	public static PrgAbs parentClass=null;
	
	public void setParentClass(PrgAbs p)
	{
		parentClass=p;
	}
	
	public static PrgAbs getParentClass()
	{
		return parentClass;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel jp = new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		plantTitle=new PlantTitle();
		plantTitle.setBackground(new Color(250,251,245));
		gridbag.setConstraints(plantTitle, constraints);
		jp.add(plantTitle);
		plantTitle.setBIX("default.xml");
		
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 1;
		constraints2.gridy = 0;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		
		autherPanel=new AuthorPanel("default.xml");
		gridbag.setConstraints(autherPanel, constraints2);
		autherPanel.setBackground(new Color(250,251,245));
		jp.add(autherPanel);
		setParentClass(this);
		this.add(jp);

		this.setBackground(new Color(250,251,245));
	}
	
	public  PlantTitle plantTitle=null;
	public  AuthorPanel autherPanel=null;
	
	public  void setInformation(String f)
	{
		if(f==null) return;
		String ff=Files.getDBPath()+f+".xml";
//		System.out.println(ff);
		if(plantTitle != null )	plantTitle.getBIX(ff);
		if(autherPanel  != null )	autherPanel.getBIX(ff);
	}
	
	/**
	* ŠÏŽ@ŽÒ‚ðŠÏŽ@‚·‚élAB
	*
	*/
	static class ObserverA implements Observer {
	/* (”ñ Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
		if(getParentClass()==null)	return;

	String str = (String) arg;
System.out.println("Ž„‚ÍPrgAbs class‚Å‚·BŠÏŽ@‘ÎÛ‚Ì’Ê’m‚ðŒŸ’m‚µ‚½‚æB" + str);

//if(str.contains("Selected")==true){
	if(str.contains("Program")==true){
		//
		String[] f=str.split(" ");
		if(f.length>1){
			if(f[1].equals("null")== false)
				getParentClass().setInformation(f[1]);
				
				}
			}
		}
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

