
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class RoundLabelTextPane extends RoundJPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * �͔|�v���O������
	 * @param args
	 */
	public static void main(String[] args) {
		// �����B�e�@�F�@��莞�Ԃ���
		JFrame frame = new JFrame();
		RoundLabelTextPane gjp=new RoundLabelTextPane("Program");
		
		frame.getContentPane().add(gjp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setSize(200, 100);
		//frame.setResizable(false);

	}

	/**
	 * This is the default constructor
	 * @param string 
	 */
	public RoundLabelTextPane(String string) {
		super();
		initialize(string);
	}
	
	private JTextField jtextArea=null;
	private JLabel jlabel=null;
	
	public String getText()
	{
		return jtextArea.getText();
	}
	
	public void setText(String t)
	{
		jtextArea.setText(t);
		revalidate();
	}

	public void setLabelText(String t)
	{
		jlabel.setText(t);
		revalidate();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(String string) {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		GridBagLayout grigbug=new GridBagLayout();
		//this.setSize(600, 100);
		this.setLayout(grigbug);
		
		// ���x��
		jlabel=new JLabel(string+":  ");
		jlabel.setHorizontalTextPosition(SwingConstants.LEFT);
		Font font = new Font("Arial", Font.BOLD, 16);
		jlabel.setFont(font);
		jlabel.setForeground(new Color(10,150,10));
		// ����
		// Font�̑傫����傫�����邱�ƁI�I
		// To Do
		jtextArea=new JTextField();
		jtextArea.setFont(font);
		jtextArea.setLocation(0, 0);
		jtextArea.addActionListener(this);
		//jta.setSize(300, 10);	
		jtextArea.setPreferredSize(new Dimension(520,20));
		//jta.setLineWrap(true);
		jtextArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		// add text area to panel
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1;
		gridBagConstraints1.weighty = 1;
		
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.weightx = 1;
		gridBagConstraints2.weighty = 1;
		

		// add panel to base panel
		this.add(jlabel,  gridBagConstraints1);
		this.add(jtextArea,  gridBagConstraints2);
		
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �ώ@�҂�ǉ�
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
	}

	private static  ObservableMan observableMan=null;  //  @jve:decl-index=0:
	
	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	static class ObserverA implements Observer {
		
	@Override
	public void update(Observable o, Object arg) {
		String message = (String) arg;
		
		System.out.println("����RoundLabelTextPane class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + message);
	
			// �ώ@�ґS���ɒʒm
    		//
			observableMan.setMessage(message);
			// �ώ@�ґS���ɒʒm
			observableMan.notifyObservers();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextField jt=(JTextField)arg0.getSource();
		String message="����RoundLabelTextPane Class�ł��B���b�Z�[�W�����͂��ꂽ��B"+System.getProperty("line.separator")+jt.getText();
		if(observableMan!=null){
			observableMan.setMessage(message);
			// �ώ@�ґS���ɒʒm
			observableMan.notifyObservers();
			}
	}
}
