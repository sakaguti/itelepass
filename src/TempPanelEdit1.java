import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import java.util.Observable;
import java.util.Observer;

public class TempPanelEdit1 extends JPanel  {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		TempPanelEdit1 gjp=new TempPanelEdit1();
		
		//gjp.setEnabled(false); //  �ҏW�s�\
		gjp.setValue(35);
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public TempPanelEdit1() {
		super();
		initialize();
	}

	public GJPanel gJPanel=null;
	private JComboBox tempCombBox = null;
	
	@Override
	public void setEnabled(boolean b)
	{
		idisp.setEnabled(b);
	}
	
	//
	public void setValue(int i)
	{
		idisp.setSelectedIndex(i);
	}

	public double getValue()
	{
		return idisp.getValue();
	}
	
    InfoDispEdit idisp=null;
    
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		
		GridBagLayout gridbag = new GridBagLayout();
		
		JPanel base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		// default size
		base.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//base.setPreferredSize(new Dimension(200,320));
		
		/*
		// separator
		JSeparator vspr=new JSeparator(JSeparator.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(200, 20));
		base.add(vspr);
		*/
		
		// panel1
		String s = Path.getPath()+"/images/program_h6_bg_temperature.gif";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\program_h6_bg_temperature.gif";
//		gJPanel = new GJPanel("/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_temperature.gif");
		gJPanel = new GJPanel(s);

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 1;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(gJPanel, constraints1);
		base.add(gJPanel);
		
		//
		idisp=new InfoDispEdit();
		idisp.setText("�x�����x�㏸");
		idisp.setSelectedIndex(35);
		idisp.setUnit("��");
//		idisp.setActionMap(am);
		
		GridBagConstraints  constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 2;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(idisp, constraints2);
		
		if(Version.getRevision().equals("Education")!=true)
			base.add(idisp);
//

		//
//		this.add(base);
//		this.setSize(200, 400);
//		this.setPreferredSize(new Dimension(200,400));
		
		this.setLayout(new BorderLayout());
		this.add( base, BorderLayout.CENTER );
		this.setBackground(new Color(250,251,245));

		
		//
		//
		// �ώ@�����l�𐶐�
		// TODO
	//	observableMan = new ObservableMan();

		// �ώ@�҂�ǉ�
	//	observableMan.addObserver((java.util.Observer)new PlantPrgEdit.ObserverA());
	}


	
    public ObservableMan observableMan=null;  
	/**
	* �ώ@�����l�B
	* 
	*/
	class ObservableMan extends Observable {

	// ���O�ɗ^����ꂽ����
	private String previousArg = "";

	/**
	* �ώ@�҂ɒʒm���܂��B
	* @param �I�u�W�F�N�g
	*/
	@Override
	public void notifyObservers() {
	//String str= String.valueOf(jRadioButton.isSelected());
	String str="ZZ";
	// ���O�ɗ^����ꂽ�����Ɠ����Ȃ炻�̂܂܃��^�[��
	if (str==previousArg) {
	//return;
	}

	previousArg = str;

	setChanged();

	// �ʒm
	super.notifyObservers(str);

	clearChanged();

	}
	}
	
	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	class ObserverA implements Observer {
	/* (�� Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;
	System.out.println("����TempPanelEdit1 class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
		}
	}

}
