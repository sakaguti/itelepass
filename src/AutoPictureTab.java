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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AutoPictureTab extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		AutoPictureTab gjp=new AutoPictureTab();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public AutoPictureTab() {
		super();
		initialize();
	}

	public AutoPicture autoPicture=null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		JPanel base=new JPanel(gridbag);
		//base.setBackground(new Color(250,251,245));
		// default size
		base.setBackground(Color.white);
		
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.gridwidth= 3;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);		
		JPanel spacePanel1 = new JPanel();
		spacePanel1.setPreferredSize(new Dimension(480,30));
		spacePanel1.setBackground(Color.white);
		gridbag.setConstraints(spacePanel1, constraints1);
		base.add(spacePanel1);
		
		
		JLabel label1 = new JLabel("自動撮影");
		label1.setForeground(Color.green);
		GridBagConstraints  constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(label1, constraints2);
		base.add(label1);
		
		
		JLabel label2 = new JLabel("毎日自動撮影を行う時間・間隔を設定することができます。");
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 1;
		constraints3.gridy = 1;
		constraints3.gridwidth= 2;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(label2, constraints3);
		base.add(label2);
		
		//
		
		JPanel spacePanel2 = new JPanel();
		constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 2;
		constraints1.gridwidth= 3;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		spacePanel2.setPreferredSize(new Dimension(480,30));
		spacePanel2.setBackground(Color.white);
		gridbag.setConstraints(spacePanel2, constraints1);
		base.add(spacePanel2);
		
		
		
		// panel2
		autoPicture = new AutoPicture();
		GridBagConstraints constraints5 = new GridBagConstraints();
		constraints5.gridx = 0;
		constraints5.gridy = 3;
		constraints5.gridwidth= 3;
		constraints5.gridheight = 1;
		constraints5.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(autoPicture, constraints5);
		autoPicture.setBackground(new Color(250,251,245));
		base.add(autoPicture);
		//
		//
		//
		this.add(base);
		//this.setSize(600, 400);
		//this.setPreferredSize(new Dimension(600,400));
		
		//
		// 追加
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlantPrgEdit.ObserverA();
		setObserver(defaultO);
	}
	
	void setState()
	{		
		// set camera shedule
		String s=Files.getAutoPicture();
		System.out.println("AutoPictureTabClass: Files.getAutoPicture() "+s);
		if(s.contains("periodic")==true){
			AutoPicture.setPeriodic(); 
		}
		if(s.contains("const")==true){
			System.out.println("AutoPictureTabClass: AutoPicture.setConstTime()");
			AutoPicture.setConstTime();	
			}
		if(s.contains("non")==true){
			AutoPicture.setNon();
		}
	}
	
	// 必要なメッソドを追加
	// Observerを追加する
	void addObserver(Observer o)
	{
		//observableMan.deleteObserver(defaultO);// 前に設定されたObserverを削除する。
		//observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observerを設定する
	// 以前に追加されたObserverは全て破棄される
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observerを削除
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}
	
// 通知

//
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";
	private Observer defaultO=null;

// 各クラス固有
	
	/**
	* 観察者を観察する人A。
	*
	*/
	class ObserverA implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		String str = arg0.toString();
		System.out.println("私はAutoPictureTab classです。観察対象の通知を検知したよ。" + str);
		
	}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton cb = (JButton)arg0.getSource();

        // Get the new item
		//  System.out.println(cb.getSelectedItem());
		message = "私はAutoPictureTab classです。"+cb.getName()+"ボタンが押されました。";
		//
		// Do processing using temperature value
		observableMan.setMessage(message);
		// 観察者全員に通知
		observableMan.notifyObservers();
		
	}
}
