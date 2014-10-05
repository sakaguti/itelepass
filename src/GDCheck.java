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



public class GDCheck extends JPanel {
//
//	画像の下に、チェックボックスとテキストを配置したJPanel
//
	private static final long serialVersionUID = 1L;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel = null;
	private InfoDispEdit idisp=null;
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
			//	この設定を適用する。
			// 観察者全員に通知
//			System.out.println("Check Button");
			JCheckBox jchk=(JCheckBox)arg0.getSource();
			
			message ="私はGJCheck classです。"+ jchk.getName() +" ボタンが押されました。";
			// 観察者全員に通知
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
				GDCheck gdc=new GDCheck(st);
				
				gdc.setSelected(true);
				gdc.setValue("30");
				thisClass.add(gdc);
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
	public GDCheck(String s) {
		super();
		initialize(s);
	}

	private GJPanel gjp=null;
	

	
	/**
	* 観察者を観察する人A。
	*
	*/
	class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
	boolean str = (Boolean) arg;
	System.out.println("私はGJCheckです。観察対象の通知を検知したよ。" + str);
		}
	}
	
	// CheckBoxの状態を返す
	public boolean isSelected()
	{
		return jCheckBox.isSelected();
	}

	// CheckBoxの状態を変える
	public void setSelected(boolean b)
	{
		jCheckBox.setSelected(b);
	}

	// Iconの横の文字
	public void setIconText(String s)
	{
		gjp.setText(s);// Text
		getJCheckBox().setName(gjp.getText());//
	}
	
	// Icon画像
	public void setImage(String iconName)
	{
		gjp.setImage(iconName);// Background Image
	}
	
	// チェックボックス横の文字
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
		GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
		gridBagConstraints01.gridx = 0;
		gridBagConstraints01.gridy = 0;
		gridBagConstraints01.gridwidth = 2;
		gridBagConstraints01.gridheight = 1;
		iconName=s;
		// 背景画像付きのJLabelを持ったJPanel
		gjp= new GJPanel(iconName);
		gjp.setText("温度");// Text
		gjp.setImage(iconName);// Background Image
		this.setLayout(new GridBagLayout());
		this.add(gjp, gridBagConstraints01);

		//
		GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
		gridBagConstraints02.gridx = 0;
		gridBagConstraints02.gridy = 1;		
		gridBagConstraints02.gridwidth = 2;
		gridBagConstraints02.gridheight = 1;
		idisp=new InfoDispEdit();
		idisp.setText("警告温度");
		idisp.setSelectedIndex(35);
		idisp.setUnit("℃");
		this.add(idisp, gridBagConstraints02);
		
		// check box
		GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
		gridBagConstraints03.gridx = 0;
		gridBagConstraints03.gridy = 3;
		gridBagConstraints03.gridwidth = 1;
		gridBagConstraints03.gridheight = 1;
		this.add(getJCheckBox(), gridBagConstraints03);
		getJCheckBox().setName(gjp.getText());//
		
		// label
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 3;
		// label for check box
		jLabel = new JLabel();
		jLabel.setText("メールを送信する");
		//this.setLayout(new GridBagLayout());
		this.add(jLabel, gridBagConstraints1);

		this.setBackground(	new Color(250,251,245));
		this.setVisible(true);
		//
		// 観察される人を生成

		observableMan = new ObservableMan();

		defaultO=new ReportPanel.ObserverA();
		// 観察者を追加
		observableMan.addObserver(defaultO);
		idisp.addObser(defaultO);

		}
	public void setLabel(String string) {
		idisp.setText(string);
	}
	public void setSelectedIndex(int i) {
		idisp.setSelectedIndex(i);
	}
	public void setLevelUnit(String string) {
		idisp.setUnit(string);
	}
	public void setSelectionItems(String[] t) {
		idisp.setSelectionItems(t);
	}
	public void setValue(String tw) {
		idisp.setValue(tw);		
	}
	

}