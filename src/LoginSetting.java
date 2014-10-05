
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.ComponentOrientation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.Font;
import java.awt.Point;

public class LoginSetting extends JDialog {	
	/**
	 * SETUPFILE="../data/setup.txt";
	 */

	private static final long serialVersionUID = 1L;
	private JTextField loginName = null;
	private JLabel loginNameLabel = null;
	private JTextField passwdWindow = null;
	private JLabel passwdLabel = null;
	private JButton CancelButton = null;
	private JButton OKbutton = null;
	private JPanel JPanel = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoginSetting thisClass = new LoginSetting();
	//			thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}
	private static String setupFile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home");
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/setup.txt");// Mac
		} else {
			// Windows
			String sCurrentDir0=System.getProperty("user.home");
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			
			System.out.println(sCurrentDir0);
			System.out.println(sCurrentDir);
			return(sCurrentDir+"\\data\\setup.txt");// Windows
		}
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */


	/**
	 * This method initializes loginName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	static boolean flag=false;
	private JTextField getLoginName() {
		if (loginName == null) {
			loginName = new JTextField();
			loginName.setPreferredSize(new Dimension(160, 28));
			loginName.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			String arg=fread();
			String loginname="";
			
			if(arg.length()>=1){
			String[] args=arg.split(System.getProperty("line.separator"));
			loginname=args[0];
			} else {
				if(flag==false){
//				JOptionPane.showMessageDialog(null, "設定ファイルにログイン情報がありません。");
				System.out.println("設定ファイルにログイン情報がありません。");
				flag=true;
				}
			}
			loginName.setText(loginname);
		}
		return loginName;
	}
	
	public String get_login()
	{
	// Get login ID
		String ln=loginName.getText();
		if(ln.compareTo("")==0) return null;
		return ln;
	}

	public String get_passwd()
	{
	//  Get Passwd
	String pww=passwdWindow.getText();
	if(pww.compareTo("")==0) return null;
	return pww;
	}
	
	public static void save_ID(String arg) 
	{
		// Open settings file
		String[] IDPW=arg.split(System.getProperty("line.separator"));
		String ln=IDPW[0];
		
		// save text as login name
		String pww=IDPW[1];
		
		//JOptionPane.showMessage(null,pww);
		String pw="";
		
		//暗号化(noで指定文字数プラスする)
		for(int i=0; i<pww.length(); i++){
    	pw = pw + String.valueOf((char)(pww.charAt(i) + 3));
    	}
		
		// save 
		//System.out.println("ID: "+ln);
		//System.out.println("PW: "+pw);

		String s= ln+System.getProperty("line.separator")+pw+System.getProperty("line.separator");
		
		File f = new File(setupFile());// Windowsに未対応だった。
		
		byte[] b = new byte[(int) f.length()];
		b = s.getBytes();

		try 
		{	
			FileOutputStream fo= new FileOutputStream(f);
			fo.write(b);	
			fo.close();
		}
		catch(Exception e)
		{
			//JOptionPane.showMessageDialog(null,"設定ファイルに書き込めませんでした。");
			e.printStackTrace();
		}
		
		}
	
	private void save_ID() 
	{
		// Open settings file
		String ln=loginName.getText();
		if(ln.compareTo("")==0) return;
		
		// save text as login name
		String pww=passwdWindow.getText();
		if(pww.compareTo("")==0) return;
		
		//JOptionPane.showMessage(null,pww);
		String pw="";
		
		//暗号化(noで指定文字数プラスする)
		for(int i=0; i<pww.length(); i++){
    	pw = pw + String.valueOf((char)(pww.charAt(i) + 3));
    	}
		
		// save 
		//System.out.println("ID: "+ln);
		//System.out.println("PW: "+pw);

		String s= ln+System.getProperty("line.separator")+pw+System.getProperty("line.separator");
		
		File f = new File(setupFile());// Windowsに未対応だった。
		
		byte[] b = new byte[(int) f.length()];
		b = s.getBytes();

		try 
		{	
			FileOutputStream fo= new FileOutputStream(f);
			fo.write(b);	
			fo.close();
		}
		catch(Exception e)
		{
			//JOptionPane.showMessageDialog(null,"設定ファイルに書き込めませんでした。");
			e.printStackTrace();
		}
		
		}
	
	public static  String fread(){
		//
		File f = new File(setupFile());
		byte[] b = new byte[(int) f.length()];
		String	s = "";
		try 
		{	
			FileInputStream fi = new FileInputStream(f);
			fi.read(b);	
			s = new String(b);	
			//System.out.println(s);
			fi.close();
		}
		catch(Exception e)
		{
//			JOptionPane.showMessageDialog(null,"設定ファイルが見つかりません。itpManagerをインストールしなおしてください。!"+s);
			System.out.println("設定ファイルが見つかりません。itpManagerをインストールしなおしてください。!"+s);
			e.printStackTrace();
			System.exit(0);		// アプリケーションを終了する
		}
		return s;
	}
	
	/**
	 * This method initializes passwdWindow	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPasswdWindow() {
		if (passwdWindow == null) {
			passwdWindow = new JPasswordField();
			passwdWindow.setPreferredSize(new Dimension(160, 28));
			passwdWindow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			// モーダルの設定
			
			String arg=fread();
			String pwd="";
			if(arg.length()>0){
			String[] args=arg.split(System.getProperty("line.separator"));
			pwd=args[1];
			} else {
				//JOptionPane.showMessageDialog(null,"パスワードを設定してください。");
			}
			
	        //復号化(noで指定文字数マイナスする[元に戻す])
	    	String passwd="";
	        for(int i=0; i<pwd.length(); i++){
	            passwd = passwd + String.valueOf((char)(pwd.charAt(i) - 3));
	        	}
	        
			passwdWindow.setText(passwd);
		}
		return passwdWindow;
	}

	/**
	 * This method initializes CancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (CancelButton == null) {
			CancelButton = new JButton();
			CancelButton.setText("Cancel");
			CancelButton.setToolTipText("Cancel Login Setting.");
			CancelButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			CancelButton.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//
/*
					int ans=JOptionPane.showConfirmDialog(null, "終了しますか？");
					//System.out.println("ans="+ans);
					if(ans==0) System.exit(0);// はい
					else
					if(ans==2) return;//　キャンセル
					else dispose(); // いいえ
*/
					System.exit(0);// はい
					}
			});
		}
		return CancelButton;
	}

	/**
	 * This method initializes OKbutton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOKbutton() {
		if (OKbutton == null) {
			OKbutton = new JButton();
			OKbutton.setText("OK");
			OKbutton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			OKbutton.setToolTipText("Save Data and Dispose.");
			OKbutton.setMnemonic(KeyEvent.VK_UNDEFINED);
			OKbutton.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
						save_ID();
						dispose();
				}
			});
		}
		return OKbutton;
	}

	public void LoginSetting_run(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoginSetting thisClass = new LoginSetting();
				thisClass.setVisible(false);
			}
		});
	}
	

	/**
	 * This is the default constructor
	 */
	public LoginSetting() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(301, 131);
		this.setLocation(new Point(22, 22));
		
		this.setContentPane(getJPanel());

		this.setResizable(false);  // Generated
		//this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);  // Generated
		this.setAlwaysOnTop(true);
		this.setLocationByPlatform(true);
		this.setTitle("LoginSetting");
		this.setAlwaysOnTop(true);
		this.setFocusable(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		//
		//this.setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		
	}
	
	public String check()
	{
		return "Yes";
	}
	/**
	 * This method initializes JPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (JPanel == null) {
				JPanel = new JPanel();
				JPanel.setLayout(new FlowLayout());
				passwdLabel = new JLabel();
				passwdLabel.setText("Password         ");
				loginNameLabel = new JLabel();
				loginNameLabel.setText("E-Mail Address");
				JPanel.setSize(new Dimension(356, 146));
				JPanel.add(loginNameLabel, null);
				JPanel.add(getLoginName(), null);// Login Name
				JPanel.add(passwdLabel, null);
				JPanel.add(getPasswdWindow(), null);// PassWd
				JPanel.add(getOKbutton(), null);
				JPanel.add(getCancelButton(), null);
		}
		return JPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="163,79"
