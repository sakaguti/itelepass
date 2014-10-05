import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;



public class GJCheck extends JPanel {
//
//	�摜�̉��ɁA�`�F�b�N�{�b�N�X�ƃe�L�X�g��z�u����JPanel
//
	private static final long serialVersionUID = 1L;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel = null;

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.addActionListener(new acceptAction());
		}
		return jCheckBox;
	}

	public class acceptAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//	acceptBtn.setEnabled(true);
			//	���̐ݒ��K�p����B
			// �ώ@�ґS���ɒʒm
//			System.out.println("Check Button");
			JCheckBox jchk=(JCheckBox)arg0.getSource();
			
			message ="����GJCheck class�ł��B"+ jchk.getName() +" �{�^����������܂����B";
			// �ώ@�ґS���ɒʒm
			observableMan.setMessage(message);
			observableMan.notifyObservers();
		}
	}

	private ObservableMan observableMan=null;  
	private String message="";
	private Observer defaultO=null;  //  @jve:decl-index=0:


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame thisClass = new JFrame();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				String st = Path.getPath()+"/images/program_h6_bg_temperature.gif";
				if(IsMacorWin.isMacOrWin()==false) st = Path.getPath()+"images\\program_h6_bg_temperature.gif";
				GJCheck gjc=new GJCheck(st);
				
				gjc.setSelected(true);
				thisClass.add(gjc);
				//"/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_temperature.gif"));
				thisClass.pack();
				thisClass.setVisible(true);
				thisClass.setResizable(false);
			}
		});
	}
	/**
	 * This is the default constructor
	 */
	public GJCheck(String s) {
		super();
		initialize(s);
	}

	private GJPanel gjp=null;
	

	
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
	boolean str = (Boolean) arg;
	System.out.println("����GJCheck�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
		}
	}
	
	// CheckBox�̏�Ԃ�Ԃ�
	public boolean isSelected()
	{
		return jCheckBox.isSelected();
	}

	// CheckBox�̏�Ԃ�ς���
	public void setSelected(boolean b)
	{
		jCheckBox.setSelected(b);
	}

	// Icon�̉��̕���
	public void setIconText(String s)
	{
		gjp.setText(s);// Text
		getJCheckBox().setName(gjp.getText());//
	}
	
	// Icon�摜
	public void setImage(String iconName)
	{
		gjp.setImage(iconName);// Background Image
	}
	
	// �`�F�b�N�{�b�N�X���̕���
	public void setCehckText(String s)
	{
		jLabel.setText(s);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private static String iconName="/Users/sakaguti/Documents/workspace2/GJCheck/src/images/program_h6_bg_temperature.gif";  //  @jve:decl-index=0:
	private void initialize(String s) {
		iconName = s;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.gridwidth = 2;
		gridBagConstraints2.gridheight = 1;

//		iconName = Path.getPath()+"/images/program_h6_bg_temperature.gif";
//		if(IsMacorWin.isMacOrWin()==false) iconName = Path.getPath()+"images\\program_h6_bg_temperature.gif";
		iconName=s;
		// �w�i�摜�t����JLabel��������JPanel
		gjp= new GJPanel(iconName);
		
		gjp.setText("���x");// Text

		gjp.setImage(iconName);// Background Image

		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 1;
		// label for check box
		jLabel = new JLabel();
		jLabel.setText("���[���𑗐M����");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;

		this.setLayout(new GridBagLayout());
		this.add(gjp, gridBagConstraints2);

		this.add(getJCheckBox(), gridBagConstraints);
		getJCheckBox().setName(gjp.getText());//
		this.add(jLabel, gridBagConstraints1);
		this.setBackground(	new Color(250,251,245));
		this.setVisible(true);
		
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		defaultO=new ReportPanel.ObserverA();
		// �ώ@�҂�ǉ�
		observableMan.addObserver(defaultO);

		}
	

}
