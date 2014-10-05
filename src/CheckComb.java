import java.awt.Color;

import java.awt.GridBagLayout;
import javax.swing.WindowConstants;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class CheckComb extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
    private static  ObservableMan observableMan=null;  //  @jve:decl-index=0:

    
   /**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton jRadioButton = null;
	private static CheckComb parentClass=null;
	
	public void  setParentClass(CheckComb p)
	{
		parentClass=p;
	}
	public static CheckComb getParentClass()
	{
		return parentClass;
	}
	
	public  JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.addActionListener(new acceptAction());
		}
		return jRadioButton;
	}

	public void setEnabled(boolean b)
	{
		jRadioButton.setSelected(b);
		jRadioButton.setEnabled(b);
		jComboBox.setEnabled(b);
		
	}
	static public class acceptAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
		//	System.out.println("ˆê’èŠÔ‚²‚Æ "+jRadioButton.isSelected());
			if(getParentClass().jRadioButton==null) return;
		    String message="";  //  @jve:decl-index=0:
			message ="„‚ÍCheckComb class‚Å‚·B"+"ˆê’èŠÔ‚²‚Æ@"+getParentClass().jRadioButton.isSelected();
			if(getParentClass().jRadioButton.isSelected()==true){
				getParentClass();
				CheckComb.jComboBox.setEnabled(true);
			} else {
				getParentClass();
				CheckComb.jComboBox.setEnabled(false);
			}
			// ŠÏ@Ò‘Sˆõ‚É’Ê’m
			observableMan.setMessage(message);
			observableMan.notifyObservers();
		}
	}
	
	static public class acceptActionComb implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {

			JComboBox cb = (JComboBox)arg0.getSource();

	        // Get the new item
			//System.out.println(cb.getSelectedItem()+"ŠÔ‚²‚Æ");
			//
			String message="";
			message ="„‚ÍCheckComb class‚Å‚·B"+"B‰eŠÔŠÔŠu@"+cb.getSelectedItem()+"ŠÔ‚²‚Æ";
			
			System.out.println("Save");
			Files.setAutoPicture("periodic "+cb.getSelectedItem().toString());
			Files.savePlantersSettings();

			
			// ŠÏ@Ò‘Sˆõ‚É’Ê’m
			observableMan.setMessage(message);
			observableMan.notifyObservers();
		}
	}
	
	// CheckBox‚Ìó‘Ô‚ğ•Ô‚·
	public boolean isSelected()
	{
		return jRadioButton.isSelected();
	}
	
	// CheckBox‚Ìó‘Ô‚ğ•Ï‚¦‚é
	public void setSelected(boolean b)
	{
		jRadioButton.setSelected(b);
		jComboBox.setEnabled(b);
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public static JComboBox jComboBox = null;
	@SuppressWarnings({ })
	private static  JComboBox getJComboBox() {
		if (jComboBox == null) {
			// 24 hours
		    String[] combodata = null;
			combodata=new String[23];
			for(int i=1;i<24;i++){
				combodata[i-1]= String.valueOf(i);
			}
			jComboBox = new JComboBox(combodata);
			jComboBox.addActionListener(new acceptActionComb());
		}
		return jComboBox;
	}
	
	public void setSelectedIndex(int n)
	{
		jComboBox.setSelectedIndex(n);
	}

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		CheckComb cc=new CheckComb();
		// ©“®B‰e@F@ˆê’èŠÔ‚²‚Æ
		if(Files.getAutoPicture().contains("periodic")==true){
			cc.setSelected(true);
			//set periodic schedule
			String s1=Files.getAutoPicture();
System.out.println("s1="+s1);
			String[] s2=s1.split(" ");
			if(s2.length>2){
System.out.println("n1=|"+s2[2]+"|");
		//int p=s2[2].length();
			int n=Integer.parseInt(s2[2])-1;
System.out.println("n1="+n);
			cc.setSelectedIndex(n);
			}
		}
			
		JFrame frame = new JFrame();
		CheckComb p=new CheckComb();
		p.setSelected(true);
		//p.setSelectedIndex(2);
		//JPanel p=new JPanel();
		frame.getContentPane().add(p);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public CheckComb() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
	//
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 1;
		gridBagConstraints12.gridy = 0;
		jLabel1 = new JLabel();
		jLabel1.setText("ˆê’èŠÔ‚²‚Æ");
		
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints21.gridy = 1;
		gridBagConstraints21.weightx = 1.0;
		gridBagConstraints21.gridx = 1;
		
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 0;
		gridBagConstraints11.gridwidth= 1;
		gridBagConstraints11.gridheight = 1;
		
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.gridx = 2;
		gridBagConstraints22.gridy = 1;
		jLabel2 = new JLabel();
		jLabel2.setText("ŠÔ‚²‚Æ");
		
		setParentClass(this);
		
		//this.setSize(214, 109);
		this.setLayout(new GridBagLayout());
		//
		this.add(getJRadioButton(), gridBagConstraints11);
		//
		this.add(getJComboBox(), gridBagConstraints21);
		
		this.add(jLabel1, gridBagConstraints12); // ˆê’èŠÔ‚²‚Æ
		this.add(jLabel2, gridBagConstraints22); // ŠÔ–ˆ
		this.setBackground(new Color(250,251,245));
		
		//
		// ŠÏ@‚³‚ê‚él‚ğ¶¬
		observableMan = new ObservableMan();

		// ŠÏ@Ò‚ğ’Ç‰Á
		observableMan.addObserver(new AutoPicture.ObserverA());
		//
		observableMan.addObserver(new ChoiceSetting.ObserverA());
		observableMan.addObserver(new CheckLabel.ObserverA());
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
	}
	
	public String getSchedule() {
		return String.valueOf(getJComboBox().getSelectedItem());
	}


	/**
	* ŠÏ@Ò‚ğŠÏ@‚·‚élAB
	*
	*/
	static class ObserverA implements Observer {
		
	@Override
	public void update(Observable o, Object arg) {
		String message = (String) arg;
		
		System.out.println("„‚ÍCheckComb class‚Å‚·BŠÏ@‘ÎÛ‚Ì’Ê’m‚ğŒŸ’m‚µ‚½‚æB" + message);
		if(getParentClass()==null) return;
		if(message.contains("true")&& !message.contains("CheckComb")){
			//getParentClass().setEnabled(false);
			getParentClass().getJRadioButton().setSelected(false);	
			//getParentClass().jComboBox.setEditable(false);
			//||message.contains("‚ğ’Ç‰Á‚·‚é"));
			}
		}
	}



} 
