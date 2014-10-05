
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class DelTripleComb extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DelTripleComb gjp=new DelTripleComb();
		JPanel p=new JPanel();
		
		gjp.setEnabled(true);
		gjp.setEnabled(false);
		gjp.setEnabled(true);
		p.add(gjp);
		
		frame.getContentPane().add(p);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public DelTripleComb() {
		super();
		initialize();
	}

	private JButton jb=null;
	private DoubleComb dc=null;
	private JPanel base=null;
	private JLabel jl=null;
	
	@Override
	public void setEnabled(boolean b)
	{
		base.removeAll();
		if(b==false){
		JLabel jlv=new JLabel(String.valueOf(jComboBox.getSelectedItem()));
			jComboBox.setVisible(b);
			jl.setVisible(b);
			jb.setVisible(b);
			jb.setEnabled(b);
			dc.setEnabled(b);
			base.add(jlv,gridBagConstraints0);
			base.add(dc,gridBagConstraints1);
			base.add(jl,gridBagConstraints2);
			base.add(jb,gridBagConstraints3);
		} else {
			jComboBox.setVisible(b);
			jl.setVisible(b);
			jb.setVisible(b);
			jb.setEnabled(b);
			dc.setEnabled(b);
			base.add(jComboBox,gridBagConstraints0);
			base.add(dc,gridBagConstraints1);
			base.add(jl,gridBagConstraints2);
			base.add(jb,gridBagConstraints3);
		}
	}

	   @Override
	public void 	setBackground(Color c)
	   {
		   if(base !=null)
			   base.setBackground(c);	   
	   }
	   

	   /**
		 * This method initializes jComboBox	
		 * 	
		 * @return javax.swing.JComboBox	
		 */
		private  JComboBox jComboBox = null;
		
		private   JComboBox getJComboBox() {
			if (jComboBox == null) {
				// 24 hours
			    String[] combodata = null;
				combodata=new String[21];
				for(int i=0;i<=100;i+=5){
					combodata[i/5]= String.valueOf(i);
				}
				jComboBox = new JComboBox(combodata);
				jComboBox.setSelectedIndex(12);
				jComboBox.addActionListener(new acceptActionComb());
			}
			return jComboBox;
		}
		
		public String getDuty()
		{
			return (String) getJComboBox().getSelectedItem();
		}
		
		public void setDuty(String s)
		{
			int is=Integer.parseInt(s.replaceAll("[^0-9]",""));//
			getJComboBox().setSelectedIndex(Integer.valueOf(is)/5);
		}
		
		public void setHour(int i)
		{
			dc.setHour(i);
		}
		
		public void setMin(int i)
		{
			dc.setMin(i);
		}
		public int getHour()
		{
			return dc.getHour();
		}
		
		public int getMin()
		{
			return dc.getMin();
		}
		
		private GridBagConstraints gridBagConstraints0 =null;
		private GridBagConstraints gridBagConstraints1 = null;
		private GridBagConstraints gridBagConstraints2 = null;
		private GridBagConstraints gridBagConstraints3 = null;
		private GridBagLayout gridbag=null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		
		gridbag=new GridBagLayout();
		base=new JPanel(gridbag);
		//jp.setBackground(Color.white);
		
		gridBagConstraints0 = new GridBagConstraints();
		gridBagConstraints0.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints0.gridx = 0;
		gridBagConstraints0.gridy = 0;
		gridBagConstraints0.gridwidth= 1;
		gridBagConstraints0.gridheight = 1;
		gridBagConstraints0.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints0.weightx = 1.0;
		gridbag.setConstraints(getJComboBox(), gridBagConstraints0);
		base.add(getJComboBox());
		
		// Time
		gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.gridwidth= 3;
		gridBagConstraints1.gridheight = 1;
		gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints1.weightx = 1.0;
		dc=new DoubleComb();
		//
		dc.setObserver(new PlantPrgEdit.ObserverA());//
		gridbag.setConstraints(dc, gridBagConstraints1);
		base.add(dc);
		
		// [%]
		gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.gridwidth= 1;
		gridBagConstraints2.gridheight = 1;
		gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints2.weightx = 1.0;
		jl=new JLabel();
		//
		jl.setText("%");
		jl.setBorder(null);
		jl.setBackground(Color.white);
		gridbag.setConstraints(jl, gridBagConstraints2);
		base.add(jl);
		
		// [X]
		gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints3.gridx = 2;
		gridBagConstraints3.gridy = 0;
		gridBagConstraints3.gridwidth= 1;
		gridBagConstraints3.gridheight = 1;
		gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints3.weightx = 1.0;
		jb=new JButton();
		//
		jb.setText("X");
		jb.setBorder(null);
		//jb.setBackground(Color.white);
		//jb.setSize(new Dimension(20,20));
		jb.setPreferredSize(new Dimension(20,30));
		jb.addActionListener(this);
		gridbag.setConstraints(jb, gridBagConstraints3);
		base.add(jb);
		
		this.add(base);
		this.setName("DelTripleComb");
		//this.revalidate();
		//
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �f�t�H���g�̊ώ@�҂�ǉ�
		defaultO=new PumpPanelEdit1.ObserverA();
		addObserver(defaultO);
		
		defaultO=new PlantPrgEdit.ObserverA();// ??
		addObserver(defaultO);
	}
	
	// Observer��ǉ�����
	void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}
	
	// Observer��ݒ肷��
	// �ȑO�ɒǉ����ꂽObserver�͑S�Ĕj�������
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observer���폜
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}
	
	
	private static ObservableMan observableMan=null;  
	private String message="";
	private Observer defaultO=null;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		message="����DelTripleComb class�ł��B"+"Delete Button��������܂����B";
		deleteProcessing();
		//	this.setVisible(false);
		
//			System.out.println("Delete Button");
		observableMan.setMessage(message);
		// �ώ@�ґS���ɒʒm
		observableMan.notifyObservers();
		

	}
	
	public static class acceptActionComb implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {

			JComboBox cb = (JComboBox)arg0.getSource();

	        // Get the new item
			//System.out.println(cb.getSelectedItem()+"���Ԃ���");
			//
			String message="";
			message ="����DelTripleComb class�ł��B"+cb.getSelectedItem();
			
			// �ώ@�ґS���ɒʒm
			observableMan.setMessage(message);
			observableMan.notifyObservers();
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
	System.out.println("����DelTripleComb class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
		}
	}
	
	// Should be Override
	void deleteProcessing()
	{
//		System.out.println("deleteProcessing: " +this.getName());
		// finish processing
		this.setName(this.getName()+" X");// add delete mark
		
//		System.out.println("deleteProcessing: " +this.getName());
		//this.remove(base);
	}
	
	


}
