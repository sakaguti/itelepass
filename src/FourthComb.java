import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class FourthComb extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		// �����B�e�@�F�@��莞�Ԃ���
		JFrame frame = new JFrame();
		FourthComb gjp=new FourthComb();
		
		gjp.setEnabled(false);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public FourthComb() {
		super();
		initialize();
	}
	
	private DoubleComb dcmb1=null;
	private DoubleComb dcmb2=null;
	private JPanel jp1=null;// ON label panel
	private JPanel jp2=null;// OFF label panel
	
	@Override
	public void setEnabled(boolean b)
	{
		dcmb1.setEnabled(b);
		dcmb2.setEnabled(b);
	}
	
	public void setONBackground(Color c)
	{
		jp1.setBackground(c);
	}

	public void setOFFBackground(Color c)
	{
		jp2.setBackground(c);
	}

	   public void setFromHour(int i)
	    {
		   dcmb1.setHour(i);
	    }
	   public void setFromMin(int i)
	    {
		   dcmb1.setMin(i);
	    }
	   
	   public void setToHour(int i)
	    {
		   dcmb2.setHour(i);
	    }
	   
	   public void setToMin(int i)
	    {
		   dcmb2.setMin(i);
	    }
	   
	   
	   public int getFromHour()
	    {
		   return dcmb1.getHour();
	    }
	   
	   public int getFromMin()
	    {
		   return dcmb1.getMin();
	    }

	   public int getToHour()
	    {
		   return dcmb2.getHour();
	    }
	   
	   public int getToMin()
	    {
		   return dcmb2.getMin();
	    }
	   
	   
	   // Hour, Min�̔w�i�F��ύX����B
	   public void 	setBackgrounds(Color c)
	   {
		   dcmb1.setBackground(c);
		   dcmb2.setBackground(c);		   
	   }
	    
	private void initialize() {
		this.setSize(300, 100);
		this.setLayout(new GridBagLayout());
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		jp1=new JPanel();
		jp1.setBackground(new Color(255,255,255));
		JLabel jLabel = new JLabel("ON");
		jLabel.setBackground(new Color(255,255,255));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jLabel, constraints);
		jp1.add(jLabel);
		this.add(jp1, constraints);
	
		dcmb1=new DoubleComb();
		// �f�t�H���g�̊ώ@�҂�ǉ�
		defaultO=new PlantPrgEdit.ObserverA();
		dcmb1.setObserver(defaultO);
		
		dcmb1.setName("From");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(dcmb1, constraints);
		this.add(dcmb1, constraints);
		//
		JLabel jLabel1 = new JLabel("��");
		jLabel1.setBackground(new Color(255,255,255));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jLabel1, constraints);
		this.add(jLabel1, constraints);
		
		jp2=new JPanel();
		jp2.setBackground(new Color(120,120,120));
		JLabel jLabel2 = new JLabel("OFF");
		jLabel2.setBackground(new Color(255,255,255));// ?????
		jLabel2.setForeground(new Color(255,255,255));
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jLabel2, constraints);
		jp2.add(jLabel2);
		this.add(jp2, constraints);
	
		dcmb2=new DoubleComb();
		dcmb2.setName("To");
		// �f�t�H���g�̊ώ@�҂�ǉ�
		defaultO=new PlantPrgEdit.ObserverA();
		dcmb2.setObserver(defaultO);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(dcmb2, constraints);
		this.add(dcmb2, constraints);
		this.setBackground(new Color(240,240,240));
		
		//
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �f�t�H���g�̊ώ@�҂�ǉ�
		defaultO=new PlantPrgEdit.ObserverA();
		setObserver(defaultO);
	}
	
	// Observer��ǉ�����
	void addObserver(Observer o)
	{
		//observableMan.deleteObserver(defaultO);// �O�ɐݒ肳�ꂽObserver���폜����B
		//observableMan.deleteObservers();
		observableMan.addObserver(o);
		dcmb1.addObserver(o);
		dcmb2.addObserver(o);
	}
	
	// Observer��ݒ肷��
	// �ȑO�ɒǉ����ꂽObserver�͑S�Ĕj�������
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
		dcmb1.setObserver(o);
		dcmb2.setObserver(o);
	}
	
	// Observer���폜
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
		dcmb1.deleteObserver(o);
		dcmb2.deleteObserver(o);
	}
	
	
	private ObservableMan observableMan=null;  
	//private String message="";
	private Observer defaultO=null;
	
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
	System.out.println("����illumPaneEdit class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
		}
	}
}
