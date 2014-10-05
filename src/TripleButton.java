import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.BoxLayout;

public class TripleButton extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		TripleButton gjp=new TripleButton();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public TripleButton() {
		super();
		initialize();
	}

	private JButton doneButton=null;// �K�p����
	private JButton editButton=null;// �ҏW����
	private JButton delButton=null;// �폜����

	// �{�^���̃A�N�e�B�u�A��A�N�e�B�u��
	void setDoneEnable(boolean b)
	{
		doneButton.setEnabled(b);
	}
	
	void setEditEnable(boolean b)
	{
		editButton.setEnabled(b);
	}
	
	void setDelEnable(boolean b)
	{
		delButton.setEnabled(b);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		GridBagLayout gridbag = new GridBagLayout();
		JPanel jp=new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		// default size
		jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp.setPreferredSize(new Dimension(660,55));
		//
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		doneButton=new JButton("�K�p����");
		doneButton.setName(doneButton.getText());
		doneButton.setPreferredSize(new Dimension(100,40));
//		doneButton.setBackground(Color.red);
//		ImageIcon img=new ImageIcon("/Users/sakaguti/Documents/workspace2/GraphicsButton/src/image/button.jpg");
//		doneButton.setIcon(img);
//		doneButton.setForeground(Color.white);
//		doneButton.setOpaque(true);
		gridbag.setConstraints(doneButton, constraints1);
		doneButton.addActionListener(this);
		jp.add(doneButton);
		
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 1;
		constraints2.gridy = 0;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		editButton=new JButton("�ҏW����");
		editButton.setName(editButton.getText());
		editButton.setEnabled(true);
		editButton.setPreferredSize(new Dimension(100,40));
		gridbag.setConstraints(editButton, constraints2);
		editButton.addActionListener(this);
		jp.add(editButton);
		
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 2;
		constraints3.gridy = 0;
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		delButton=new JButton("�폜����");
		delButton.setName(delButton.getText());
		delButton.setEnabled(true);
		delButton.setPreferredSize(new Dimension(100,40));
		gridbag.setConstraints(delButton, constraints3);
		delButton.addActionListener(this);
		jp.add(delButton);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.setPreferredSize(new Dimension(320,65));
		this.add(jp);

		// Class�ԒʐM��ݒ肷��
		//(1)
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();
		observableMan.setMessage(message);

		//(2)
		// �ώ@�҂�ǉ�
		// observableMan.addObserver((java.util.Observer)new tagTest.ObserverA());
		// �����͌�ŁAaddObserver(Observer o)�ŏ㏑���ł���B
		addObserver(new PlanterSetting.ObserverA());
	}

	void addObserver(Observer o)
	{
		if(observableMan==null) return;
			observableMan.addObserver(o);
	}
	
	public void addActionListener(ActionListener a)
	{
		delButton.addActionListener(a);
		editButton.addActionListener(a);
		doneButton.addActionListener(a);
	}
	
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String im="����TripleButton class�ł��B ";
	      JButton btn = (JButton)e.getSource();
	      if(btn.equals(delButton)){
	    	  //System.out.println("delButton");
	    	  message = im+"delButton"+" ��������܂����B";
	    	  // Do DelButton processing
	      } else
		      if(btn.equals(editButton)){
		    	  //System.out.println("editButton");
		    	  message = im+"editButton"+" ��������܂����B";
		    	  // Do EditButton processing
		      } else
			      if(btn.equals(doneButton)){
			    	  //System.out.println("doneButton");
			    	  message = im+"doneButton"+" ��������܂����B";
			    	  // Do DoneButton processing
			      } 
	      
		    //  �ώ@�҂ɒʒm���郁�b�Z�[�W���Z�b�g
		    observableMan.setMessage(message);
			// �ώ@�ґS���ɒʒm
			observableMan.notifyObservers();
			//System.out.println("notifyObservers");
	}
}
