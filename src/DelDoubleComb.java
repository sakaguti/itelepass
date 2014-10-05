
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DelDoubleComb extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//
		JFrame frame = new JFrame();
		
		DelDoubleComb gjp=new DelDoubleComb();
		frame.getContentPane().add(gjp);
		//
		gjp.setEnabled(true);
		//
		//gjp.setDelButton(false);
		//gjp.setEnabled(false);

		int h,m;
		Calendar c1=Calendar.getInstance();
		h=c1.get(Calendar.HOUR_OF_DAY);
		m=c1.get(Calendar.MINUTE);
		gjp.setHour(h);
		gjp.setMin(m);

		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void setDelButton(boolean b)
	{
		jb.setEnabled(b);
		jb.setVisible(b);
	}

	/**
	 * This is the default constructor
	 */
	public DelDoubleComb() {
		super();
		initialize();
	}
//
	private JButton jb=null;
	private DoubleComb dc=null;
	
	@Override
	public void setEnabled(boolean b)
	{
		dc.setEnabled(b);
		jb.setVisible(b);
		jb.setEnabled(b);
	}
	public DoubleComb getCOmb()
	{
		return dc;
	}
	
	public void setHour(int i)
	{
		dc.setHour(i);
	}
	
	public void setButtonEnabled(boolean b)
	{
		jb.setVisible(b);
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
	/*
	 *   X �{�^���������ꂽ����Action��t��������
	 */
	private JPanel jp=null;
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		jp=new JPanel();
		jp.setBackground(new Color(250,251,245));
		dc=new DoubleComb();
		jb=new JButton();
		jb.setName("DelBtn");
		jb.setText("X");
		jb.setBorder(null);
		jb.addActionListener(this);
		jb.setPreferredSize(new Dimension(20,20));
		
		jp.add(dc);
		jp.add(jb);
		
		this.setName(jb.getName());
		this.add(jp);
		//this.setSize(250, 50);
		this.setLayout(new GridBagLayout());
	//
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �f�t�H���g�̊ώ@�҂�ǉ�
		
		Observer default00=new AutoPicture.ObserverA();
		setObserver(default00);
		
		Observer default01=new ChoiceSetting.ObserverA();
		addObserver(default01);

		Observer default02=new PumpPanelEdit2.ObserverA();
		addObserver(default02);
		
		defaultO=new PlantPrgEdit.ObserverA();
		addObserver(defaultO);
		
		//setEnabled(false);
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


private ObservableMan observableMan=null;  
private String message="";
private Observer defaultO=null;

@Override
public void actionPerformed(ActionEvent arg0) {
	//JButton b=(JButton)arg0.getSource();
	message="����DelDoubleComb class�ł��B"+this.getName()+"��������܂����B";
	deleteProcessing();
	//	this.setVisible(false);
	
//		System.out.println("Delete Button");
	observableMan.setMessage(message);
	// �ώ@�ґS���ɒʒm
	observableMan.notifyObservers();
	

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
System.out.println("����DelDoubleComb class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
	}
}

// Should be Override
void deleteProcessing()
{
//	System.out.println("deleteProcessing: " +this.getName());
	// finish processing
	if(this.getName().contains(" X ")==false)
				this.setName(this.getName()+" X ");// add delete mark
	
//	System.out.println("deleteProcessing: " +this.getName());
	message="deleteProcessing: " +this.getName();
}


}