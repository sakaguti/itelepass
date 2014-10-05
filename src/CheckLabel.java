import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

public class CheckLabel extends JPanel {
	/**
	 * 	自動撮影を行わない　パネル
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 自動撮影　を　行わない
		JFrame frame = new JFrame();
		CheckLabel gjp=new CheckLabel();
		gjp.setEnabled(true);
		//
		//gjp.setSelected(false);
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * This is the default constructor
	 */
	public CheckLabel() {
		super();
		initialize();
	}
	
	public void setSelected(boolean b)
	{
		jRadioButton.setSelected(b);
	}
	
	@Override
	public void setEnabled(boolean b)
	{
		jRadioButton.setEnabled(b);
		jRadioButton.setSelected(b);
		//jRadioButton.setVisible(b);
	}
	
	public JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.addActionListener(new acceptAction());
		}
		return jRadioButton;
	}

	public class acceptAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//
//			System.out.println( getText()+jRadioButton.isSelected() );
			
			message = "CheckLabelからのメッセージだよ。"+getText()+" "+jRadioButton.isSelected();
			
			// (4)
			// 観察者全員に通知するメッセージをセットする
			observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();// 引数はとらない
		}
	}
	
	public void setText(String s)
	{
		jLabel1.setText(s);
	}
	
	public String getText()
	{
		return jLabel1.getText();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private static JRadioButton jRadioButton =null;
	private JLabel jLabel1=null;
	
	private void initialize() {
//
		GridBagLayout gridgag = new GridBagLayout();
		JPanel base=new JPanel(gridgag);
		base.setBackground(new Color(250,251,245));
		
		jRadioButton =getJRadioButton();
		//
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridgag.setConstraints(jRadioButton, constraints);
		base.add(jRadioButton);

		jLabel1 = new JLabel();
		jLabel1.setText("自動撮影を行わない");
		//
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 1;
		constraints1.gridy = 0;
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.fill = GridBagConstraints.VERTICAL;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridgag.setConstraints(jLabel1, constraints1);
		base.add(jLabel1);

		//
		this.add(base);
		this.setBackground(new Color(250,251,245));
		
		// Class間通信を設定する
		//(1)
		// 観察される人を生成
		observableMan = new ObservableMan();
		observableMan.setMessage(message);

		//(2)
		// 観察者を追加
		// observableMan.addObserver((java.util.Observer)new tagTest.ObserverA());
		// ここは後で、addObserver(Observer o)で上書きできる。
		addObserver(new AutoPicture.ObserverA());
		addObserver(new PlantPrgEdit.ObserverA());
		addObserver(new CheckComb.ObserverA());
		addObserver(new ChoiceSetting.ObserverA());
	}

	// (3)
	// 観察者のアドレス
	private ObservableMan observableMan=null;  
	// 観察者全員に通知するメッセージ
	private String message="";
	// 観察者を追加
	void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}

	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
		
	@Override
	public void update(Observable o, Object arg) {
		String message = (String) arg;
		
		System.out.println("私はCheckLabel classです。観察対象の通知を検知したよ。" + message);
		
		if(message.contains("true") && !message.contains("CheckLabel")){
			if(jRadioButton != null){
				jRadioButton.setSelected(false);
			//	jComboBox.setEnabled(false);
			}
			}
		}
	}

	public boolean getCheckBox() {
		return jRadioButton.isSelected();
	}
	public boolean getSelected() {
		return jRadioButton.isSelected();
	}
}
