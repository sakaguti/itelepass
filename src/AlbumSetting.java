import javax.swing.SwingUtilities;

import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class AlbumSetting extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel picasaWindow = null;
	private JLabel picasaLoginLabel = null;
	private JLabel picasaPasswdLabel = null;
	private JTextField picasaLoginFild = null;
	private JTextField picasaPasswdField = null;
	private JButton okButton = null;
	private JButton openPicasaButton = null;
	private JButton cancelButton = null;

	private static String setupFile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home");
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/PicasaSetup.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\PicasaSetup.txt");// Windows
		}
	}

	public void set_login(String lg) 
	{
		picasaLoginFild.setText(lg);
	}

	public void set_passwd(String pw) 
	{
		picasaPasswdField.setText(pw);
	}
	

	public static void save_ID(String arg) 
	{
		// Open settings file
		String[] IDPW=arg.split(System.getProperty("line.separator"));
		String ln=IDPW[0];
		if(ln.compareTo("")==0) return;
		
		// save text as login name
		String pww=IDPW[1];//
		
//		System.out.println("save_ID="+pww);
		
		if(pww.compareTo("")==0) return;
		
		String pw="";
		
    //暗号化(noで指定文字数プラスする)
		for(int i=0; i<pww.length(); i++){
			pw = pw + String.valueOf((char)(pww.charAt(i) + 3));
		}
		
		// save 
		//System.out.println("ID: "+ln);
		//System.out.println("PW: "+pw);

		String s= ln+System.getProperty("line.separator")+pw+System.getProperty("line.separator");
		
		//s+="Planter planter1";// ////
		// full path need.
		File f = new File(setupFile());// Windowsに未対応だった。
		if(f.exists()== false){
			// can not find setupFile
			JOptionPane.showMessageDialog(null,"ERROR_MESSAGE", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// for debug
		//JOptionPane.showMessageDialog(null, SETUPFILE);
		
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
		String ln=picasaLoginFild.getText();
		if(ln.compareTo("")==0) return;
		
		// save text as login name
		String pww=picasaPasswdField.getText();//
		
//		System.out.println("save_ID="+pww);
		
		if(pww.compareTo("")==0) return;
		
		String pw="";
		
    //暗号化(noで指定文字数プラスする)
		for(int i=0; i<pww.length(); i++){
			pw = pw + String.valueOf((char)(pww.charAt(i) + 3));
		}
		
		// save 
		//System.out.println("ID: "+ln);
		//System.out.println("PW: "+pw);

		String s= ln+System.getProperty("line.separator")+pw+System.getProperty("line.separator");
		
		//s+="Planter planter1";// ////
		// full path need.
		File f = new File(setupFile());// Windowsに未対応だった。
		if(f.exists()== false){
			// can not find setupFile
			JOptionPane.showMessageDialog(null,"ERROR_MESSAGE", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
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
			if(f.exists()== false){
				// can not find setupFile
				JOptionPane.showMessageDialog(null,"ERROR_MESSAGE", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			fi.read(b);	
			s = new String(b);	
//	System.out.println(s);
			fi.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Picasa設定ファイルが見つかりません。itpManagerをインストールしなおしてください。"+"|"+f);
			e.printStackTrace();
			//System.exit(0);		// アプリケーションを終了する
		}
		return s;
	}
	
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	static boolean flag=false;
	private JTextField getLoginName() {
		if (picasaLoginFild == null) {
			picasaLoginFild = new JTextField();
			picasaLoginFild.setPreferredSize(new Dimension(160, 28));
			picasaLoginFild.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			picasaLoginFild.setBounds(new Rectangle(110, 60, 170, 30));
			
			String arg=fread();
			String loginname="";
				
			if(arg.length()>0){
			String[] args=arg.split(System.getProperty("line.separator"));
			loginname=args[0];
			} else {
				if(flag==false){
					JOptionPane.showMessageDialog(null, "Picasa設定ファイルにログイン情報がありません。");
					flag=true;
				}
			}
			
		//	System.out.println("arg loginname="+loginname);
			picasaLoginFild.setText(loginname);
		}
		
		return picasaLoginFild;
	}
	
	
	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPasswd() {
		if (picasaPasswdField == null) {
			picasaPasswdField = new JPasswordField();
			picasaPasswdField.setPreferredSize(new Dimension(160, 28));
			picasaPasswdField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
			picasaPasswdField.setBounds(new Rectangle(110, 95, 170, 30));
			
			// モーダルの設定
			
			String arg=fread();
			
		//	System.out.println("arg passwd="+arg);
			
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
	        
			picasaPasswdField.setText(passwd);
		}
		return picasaPasswdField;
	}
	
	public static void openURL() {
		// Open Cloud-Garden Page
        Desktop desktop = Desktop.getDesktop();
        
        try {
        	// public site
			desktop.browse(new URI("https://picasaweb.google.com/home?hl=ja"));
 
		} catch (IOException e) {
			// 
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// 
			e.printStackTrace();
		}
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenPicasaButton() {
		if (openPicasaButton == null) {
			openPicasaButton = new JButton();
			openPicasaButton.setBounds(new Rectangle(110, 20, 170, 30));
			openPicasaButton.setText("Open Picasa");
			openPicasaButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					openURL();
				}
			});
			}
		return openPicasaButton;
	}


	private JButton oKbutton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
			okButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			okButton.setToolTipText("Save Data and Dispose.");
			okButton.setBounds(new Rectangle(110, 140, 70, 30));

			okButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						save_ID();
						dispose();
				}
			});
		}
		return okButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton cancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.setToolTipText("Cancel Login Setting.");
			cancelButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			cancelButton.setBounds(new Rectangle(200, 140, 70, 30));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//
					int ans=JOptionPane.showConfirmDialog(null, "終了しますか？");
					//System.out.println("ans="+ans);
					if(ans==0) System.exit(0);// はい
					else
					if(ans==2) return;//　キャンセル
					else dispose(); // いいえ
				}
			});
		}
		return cancelButton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AlbumSetting thisClass = new AlbumSetting();
	//			thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public AlbumSetting() {
		super();
		initialize();
	}
	
	public void AlbumSetting_run(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AlbumSetting thisClass = new AlbumSetting();
				thisClass.setVisible(false);
			}
		});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 230);
		this.setContentPane(getJContentPane());
		this.setTitle("Album Setting");
	}
	

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			picasaPasswdLabel = new JLabel();
			picasaPasswdLabel.setBounds(new Rectangle(13, 94, 92, 34));
			picasaPasswdLabel.setText("Passwd");
			picasaLoginLabel = new JLabel();
			picasaLoginLabel.setBounds(new Rectangle(13, 59, 91, 28));
			picasaLoginLabel.setText("User ID");
			picasaWindow = new JLabel();
			picasaWindow.setBounds(new Rectangle(13, 22, 50, 26));
			picasaWindow.setText("Picasa");
			
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(picasaWindow, null);
			jContentPane.add(picasaLoginLabel, null);
			jContentPane.add(picasaPasswdLabel, null);
			jContentPane.add(getOpenPicasaButton(), null);		
			
			jContentPane.add(getLoginName(), null);	// Picasa Login
			jContentPane.add(getPasswd(), null);	// Picasa Passwd
			
			jContentPane.add(oKbutton(), null);
			jContentPane.add(cancelButton(), null);
		}
		return jContentPane;
	}

}
