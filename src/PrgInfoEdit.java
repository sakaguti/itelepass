
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import javax.swing.WindowConstants;

public class PrgInfoEdit extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		PrgInfoEdit gjp=new PrgInfoEdit();
		frame.getContentPane().add(gjp);
		//
		gjp.setEnabled(true);
		//
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);

	}

	/**
	 * This is the default constructor
	 */
	public PrgInfoEdit() {
		super();
		initialize();
	}
	
	//
	private JTextArea  textArea=null;
	private JComboBox  jPlants=null;
	private JComboBox  jProgram=null;
//	private CheckLabel chkLabel=null;
	//
	
	@Override
	public void setEnabled(boolean b)
	{
		textArea.setEditable(b);
		jPlants.setEnabled(b);
		jProgram.setEnabled(b);
	}
	
	public void setText(String t)
	{
		textArea.setText(t);
	}
	
	public String getText()
	{
		return textArea.getText();
	}
	
	public String getSelectedItem()
	{
		return (String)jPlants.getSelectedItem();
	}
	
	public String getCheckStatus()
	{
		//return chkLabel.getCheckBox();
		return (String)jProgram.getSelectedItem();
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

		//
		// �c�̎�� panel
		JLabel naeTitleLabel=new JLabel("�c�̎��");
		naeTitleLabel.setForeground(new Color(15,155,15));
		//
		GridBagConstraints constraints00 = new GridBagConstraints();
//		constraints0.anchor=GridBagConstraints.WEST;
		constraints00.gridx = 0;
		constraints00.gridy = 0;
		constraints00.gridwidth= 1;
		constraints00.gridheight = 1;
		constraints00.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(naeTitleLabel, constraints00);
		base.add(naeTitleLabel);

		// �c�̎�� panel
		GridBagConstraints constraints01 = new GridBagConstraints();
//		constraints2.anchor=GridBagConstraints.WEST;
		constraints01.gridx = 0;
		constraints01.gridy = 1;	
		constraints01.gridwidth= 1;
		constraints01.gridheight = 1;
		constraints01.insets = new Insets(0, 0, 0, 0);
		jPlants=getJComboBox();
		gridbag.setConstraints(jPlants, constraints01);
		jPlants.addActionListener(new acceptActionComb());
		base.add(jPlants);
		//
		//
		// �c�̎�� panel
		JLabel categoryTitleLabel=new JLabel("�J�e�S��");
		categoryTitleLabel.setForeground(new Color(15,155,15));
		//
		GridBagConstraints constraints02 = new GridBagConstraints();
//		constraints01.anchor=GridBagConstraints.WEST;
		constraints02.gridx = 1;
		constraints02.gridy = 0;
		constraints02.gridwidth= 1;
		constraints02.gridheight = 1;
		constraints02.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(categoryTitleLabel, constraints02);
		base.add(categoryTitleLabel);
		

		GridBagConstraints constraints03 = new GridBagConstraints();
//		constraints4.anchor=GridBagConstraints.WEST;
		constraints03.gridx = 1;
		constraints03.gridy = 1;	
		constraints03.gridwidth= 1;
		constraints03.gridheight = 1;
		constraints03.insets = new Insets(0, 0, 0, 0);
		String[] data={"���e","����","����"};
	    jProgram = new JComboBox(data);
		gridbag.setConstraints(jProgram, constraints03);
		jProgram.addActionListener(new acceptActionComb());
		base.add(jProgram);//, BorderLayout.CENTER);
		
		// Text Area
		GridBagLayout gridbag3 = new GridBagLayout();
		JPanel titlePanel3=new JPanel(gridbag3);
		textArea=new JTextArea("");
		textArea.setLineWrap(true);
		textArea.addKeyListener(this);
		textArea.setPreferredSize(new Dimension(400,50));
		textArea.setSize(new Dimension(400,50));
		textArea.setLineWrap(true);// �܂�Ԃ�
		JScrollPane scrollpane = new JScrollPane(textArea, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		//
		GridBagConstraints constraints04 = new GridBagConstraints();
		constraints04.anchor=GridBagConstraints.NORTH;
		constraints04.gridx = 2;
		constraints04.gridy = 0;	
		constraints04.gridwidth= 1;
		constraints04.gridheight = 1;
		constraints04.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(scrollpane, constraints04);
		titlePanel3.add(scrollpane);//, BorderLayout.CENTER);
		//
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.anchor=GridBagConstraints.NORTH;
		constraints3.gridx = 2;
		constraints3.gridy = 0;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 3;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(titlePanel3, constraints3);
		base.add(titlePanel3);//, BorderLayout.CENTER);
		//
		
		this.setBackground(new Color(250,251,245));
		this.add(base);
		
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();	

		observableMan.addObserver(new PlanterSetting.ObserverA());
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
	}
	
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private static JComboBox jComboBox = null;
	private static  JComboBox getJComboBox() {
		if (jComboBox == null) {
//	
		    String[] combodata = {"�n�[�u","���","��","���̑�"};
			jComboBox = new JComboBox(combodata);
			jComboBox.addActionListener(new acceptActionComb());
		}
		return jComboBox;
	}
	
	static public class acceptActionComb implements ActionListener{
		String cs="";
		@Override
		public void actionPerformed(ActionEvent arg0) {

			JComboBox cb = (JComboBox)arg0.getSource();
	
	        // Get the new item
			//System.out.println(cb.getSelectedItem()+"���Ԃ���");
			//
			if(cs.equals(cb.getSelectedItem())==false){
			cs =(String)cb.getSelectedItem();
			message ="����PrgInfoEdit class�ł��B"+"�c�̎�ށ@"+cs+"�@��ʒm������B";
//			System.out.println(message);
			// �ώ@�ґS���ɒʒm
			observableMan.setMessage(message);
			observableMan.notifyObservers();
			}
		}
	}
	
	
	private static ObservableMan observableMan=null;  
	private static String message=""; 
	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	public static class ObserverA implements Observer {
		String name="";
		public void setName(String s){
			name=s;
		}
		
		// override ���ĕύX���郁�\�b�h
		@Override
		public void update(Observable o, Object arg) {	
			String str = (String) arg;
			System.out.println("����PrgInfoEdit class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
				}// update
		}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextArea jt=(JTextArea)arg0.getSource();
		String s=jt.getText();
		
		message ="����PrgInfoEdit class�ł��B"+"Memo�@"+s;
		// �ώ@�ґS���ɒʒm
		observableMan.setMessage(message);
		observableMan.notifyObservers();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		//System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode()==10){// Return
			
			JTextArea jt=(JTextArea)arg0.getSource();
			String s=jt.getText();
			
			message ="����PrgInfoEdit class�ł��B"+"Memo�@"+System.getProperty("line.separator")+s;
			if(observableMan!=null){
			// �ώ@�ґS���ɒʒm
			observableMan.setMessage(message);
			observableMan.notifyObservers();
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}


}  //  @jve:decl-index=0:visual-constraint="145,9"

