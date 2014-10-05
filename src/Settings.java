
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.text.JTextComponent;

public class Settings extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;



	/**
	 * This method initializes CloudGardenID	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JCheckBox getCheckSaveHolder() {
		if (checkSaveHolder == null) {
			checkSaveHolder = new JCheckBox();
			//checkSaveHolder.setFont(new Font("Dialog", Font.PLAIN, 10));
			checkSaveHolder.setPreferredSize(new Dimension(30, 30));
			//checkSaveHolder.setFocusTraversalKeysEnabled(false);
			checkSaveHolder.setName("checkSaveHolder");
			checkSaveHolder.addActionListener(this);
			if(Version.getRevision().equals("Education")==false && Files.getCheckSaveHolder().contains("true")==true){
				if(Version.getFreeOrPaid().contains("Pais")==true)	checkSaveHolder.setSelected(true);
				else 	checkSaveHolder.setSelected(false);
			} else 
			checkSaveHolder.setSelected(false);
			//checkSaveHolder.setText(fromAddressString);
		}
		return checkSaveHolder;
	}
	private JLabel getCheckSaveHolderLabel() {
		if (checkSaveHolderLabel == null) {
			checkSaveHolderLabel = new JLabel();
			checkSaveHolderLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
//			checkSaveHolderLabel.setPreferredSize(new Dimension(100, 30));
//			checkSaveHolderLabel.setFocusTraversalKeysEnabled(false);
//			sMTPServer.addActionListener(this);
			checkSaveHolderLabel.setName("checkSaveHolderLabel");
			checkSaveHolderLabel.setText("�w��z���_�[�ɕۑ�");
		}
		return checkSaveHolderLabel;
	}

	/**
	 * This method initializes SMTPServer	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSMTPServer() {
		if (sMTPServer == null) {
			sMTPServer = new JTextField();
			sMTPServer.setFont(new Font("Dialog", Font.PLAIN, 10));
			sMTPServer.setPreferredSize(new Dimension(100, 30));
			sMTPServer.setFocusTraversalKeysEnabled(false);
			sMTPServer.setName("SMTPServer");
			sMTPServer.addActionListener(this);
			sMTPServer.setText(sMTPServerString);
			sMTPServer.setEditable(false);
		}
		return sMTPServer;
	}

	private JTextField getSMTPServerPasswd() {
		if (sMTPServerPasswd == null) {
			sMTPServerPasswd = new JPasswordField();
			sMTPServerPasswd.setFont(new Font("Dialog", Font.PLAIN, 10));
			sMTPServerPasswd.setPreferredSize(new Dimension(100, 30));
			sMTPServerPasswd.setFocusTraversalKeysEnabled(false);
			sMTPServerPasswd.setName("SMTPServerPasswd");
			sMTPServerPasswd.addActionListener(this);
			sMTPServerPasswd.setText(sMTPPasswdString);
		}
		return sMTPServerPasswd;
	}

	/**
	 * This method initializes FromAddress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFromAddress() {
		if (fromAddress == null) {
			fromAddress = new JTextField();
			fromAddress.setFont(new Font("Dialog", Font.PLAIN, 10));
			fromAddress.setPreferredSize(new Dimension(100, 30));
			fromAddress.setFocusTraversalKeysEnabled(false);
			fromAddress.setName("FromAddress");
			fromAddress.addActionListener(this);
			fromAddress.setText(fromAddressString);
		}
		return fromAddress;
	}


	/**
	 * This method initializes ToAddress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getToAddress() {
		if (toAddress == null) {
			toAddress = new JTextField();
			toAddress.setFont(new Font("Dialog", Font.PLAIN, 10));
			toAddress.setPreferredSize(new Dimension(100, 30));
			toAddress.setFocusTraversalKeysEnabled(false);
			toAddress.setName("FromAddress");
			toAddress.addActionListener(this);
			toAddress.setText(toAddressString);
		}
		return toAddress;
	}


	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (getResolutionSelect() == null) {
			String[] combodata = {"320X240", "640X480", "800X600", "1024X768","1280X800"};
			
			setResolutionSelect(new JComboBox(combodata));
			getResolutionSelect().setFont(new Font("Dialog", Font.PLAIN, 10));
			getResolutionSelect().setName("Resolution");
			//
			String r=Files.LoadCameraResolution();
			//
			if(r!=null){
			for(int i=0;i<combodata.length;i++){
				if(r.contains(combodata[i])== true){
					if(getResolutionSelect()!=null){
						getResolutionSelect().setSelectedIndex(i);
					if(ITPlanterClass.getState()==false) new ITPlanterClass();
					System.out.println("SettingClass: "+r);
					String[] wh=r.split("X");
					if(wh.length>1){
					int w=  Integer.parseInt(wh[0].replaceAll("[^0-9]",""));
					int h=  Integer.parseInt(wh[1].replaceAll("[^0-9]",""));
					ITPlanterClass.getCurrentPlanterClass().getInformation().setCameraResolution(w, h);
							}
						}
					}
				}
			}
			getResolutionSelect().addActionListener(this);
			//jComboBox.setAction(new setResolution());
		}
		return getResolutionSelect();
	}


	/**
	 * This method initializes CultureProgramSelect	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCultureProgramSelect() {
		if (cultureProgramSelect == null) {
//			String[] combodata = {"�ʏ�͔|", "��ԍ͔|", "�ߓd�͔|", "�s�[�N�V�t�g"};
			String[] combodata = {"�ʏ�͔|", "��ԍ͔|"};
			
			String s=Files.LoadCultivationMode();

			cultureProgramSelect = new JComboBox(combodata);
			cultureProgramSelect.setFont(new Font("Dialog", Font.PLAIN, 10));
			
			if(s.contains("day")) 
				cultureProgramSelect.setSelectedIndex(0);
			if(s.contains("night")) 
				cultureProgramSelect.setSelectedIndex(1);
			
			cultureProgramSelect.setName("CultivationMode");
			cultureProgramSelect.addActionListener(this);
		}
		return cultureProgramSelect;
	}


	/**
	 * This method initializes ClockSet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getClockSet() {
		if (clockSet == null) {
			clockSet = new JButton();
			clockSet.setText("��������");
			clockSet.setFont(new Font("Dialog", Font.PLAIN, 10));
			clockSet.addActionListener(this);
		}
		return clockSet;
	}


	/**
	 * This method initializes ClockSet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMailTest() {
		if (mailTest == null) {
			mailTest = new JButton();
			mailTest.setText("���M�e�X�g");
			mailTest.setFont(new Font("Dialog", Font.PLAIN, 10));
			mailTest.addActionListener(this);
			
		}
		return mailTest;
	}
	
	// 130511 y.sakaguti
	private Component getSettingReadBtn() {
		if (readSettings == null) {
			readSettings = new JButton();
			readSettings.setText("�v�����^�[�ݒ���Ǐo");
			readSettings.setName("�v�����^�[�ݒ���Ǐo");
			readSettings.setFont(new Font("Dialog", Font.PLAIN, 10));
			readSettings.addActionListener(this);
		}
		return readSettings;
	}
	
	/**
	 * This method initializes TempSensorComp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTempSensorComp() {
		if (tempSensorComp == null) {
			tempSensorComp = new JTextField();
			tempSensorComp.setFont(new Font("Dialog", Font.PLAIN, 10));
			tempSensorComp.setSize(new Dimension(100, 30));
			tempSensorComp.setFocusTraversalKeysEnabled(false);
			tempSensorComp.addActionListener(this);
			
			if(ITPlanterClass.getState()==false) new ITPlanterClass();
			String tc=Files.LoadTempCorrection();
//			ITPlanterClass.getCurrentPlanterClass().getSensor().setTempCorrect(Double.valueOf(tc));	
			tempSensorComp.setText(tc);
		}
		return tempSensorComp;
	}


	/**
	 * This method initializes IllumSensorComp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getIllumSensorComp() {
		if (illumSensorComp == null) {
			illumSensorComp = new JTextField();
			illumSensorComp.setFont(new Font("Dialog", Font.PLAIN, 10));
			illumSensorComp.setSize(new Dimension(100, 30));
			illumSensorComp.setFocusTraversalKeysEnabled(false);
			illumSensorComp.addActionListener(this);
			String tc=Files.LoadIllumCorrection();
			//ITPlanterClass.getCurrentPlanterClass().getSensor().setIllumCorrect(Double.valueOf(tc));	
			illumSensorComp.setText(tc);
		}
		return illumSensorComp;
	}


	/**
	 * This method initializes MessageArea	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMessageArea() {
		if (messageArea == null) {
			messageArea = new JTextField();
			messageArea.setFont(new Font("Dialog", Font.PLAIN, 10));
			messageArea.setFocusTraversalKeysEnabled(false);
			messageArea.addActionListener(this);
			messageArea.setText(messageAreaString);
		}
		return messageArea;
	}


	/**
	 * This method initializes Reset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getReset() {
		if (reset == null) {
			reset = new JButton();
			reset.setFont(new Font("Dialog", Font.PLAIN, 10));
			reset.setText("�H��o�גl�ɖ߂�");
			reset.setName("�H��o�גl");
			reset.addActionListener(this);
		}
		return reset;
	}


	/**
	 * This method initializes WhiteLEDSpeed	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getWhiteLEDSpeed() {
		if (whiteLEDSpeed == null) {
			whiteLEDSpeed = new JTextField();
			whiteLEDSpeed.setFont(new Font("Dialog", Font.PLAIN, 10));
			whiteLEDSpeed.setPreferredSize(new Dimension(100, 30));
			whiteLEDSpeed.setFocusTraversalKeysEnabled(false);
			whiteLEDSpeed.addActionListener(this);
			
			//String tc=Files.LoadWhiteLEDTime();
			//whiteLEDSpeed.setText(tc);
			
			// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
			if(ITPlanterClass.getSystemPlanterSize()>0){
				String tc=Files.LoadWhiteLEDTime();
				whiteLEDSpeed.setText(tc);
			}
			
		}
		return whiteLEDSpeed;
	}


	/**
	 * This method initializes PumpBuustTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPumpBuustTime() {
		if (pumpBoostTime == null) {
			pumpBoostTime = new JTextField();
			pumpBoostTime.setFont(new Font("Dialog", Font.PLAIN, 10));
			pumpBoostTime.setPreferredSize(new Dimension(100, 30));
			pumpBoostTime.setFocusTraversalKeysEnabled(false);
			pumpBoostTime.addActionListener(this);
			
			// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
			if(ITPlanterClass.getSystemPlanterSize()>0){
				String tc=Files.LoadBoostTime();
				pumpBoostTime.setText(tc);
			}
		}
		return pumpBoostTime;
	}


	/**
	 * This method initializes PC2AutoTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPC2AutoTime() {
		if (pC2AutoTime == null) {
			pC2AutoTime = new JTextField();
			pC2AutoTime.setFont(new Font("Dialog", Font.PLAIN, 10));
			pC2AutoTime.setPreferredSize(new Dimension(100, 30));
			pC2AutoTime.setFocusTraversalKeysEnabled(false);
			pC2AutoTime.addActionListener(this);
			
			// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
			if(ITPlanterClass.getSystemPlanterSize()>0){
				String tc=Files.LoadPCAutoTime();
				pC2AutoTime.setText(tc);
			}
		}
		return pC2AutoTime;
	}


	/**
	 * This method initializes Manu2AutoTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getManu2AutoTime() {
		if (manu2AutoTime == null) {
			manu2AutoTime = new JTextField();
			manu2AutoTime.setFont(new Font("Dialog", Font.PLAIN, 10));
			manu2AutoTime.setPreferredSize(new Dimension(100, 30));
			manu2AutoTime.setFocusTraversalKeysEnabled(false);
			manu2AutoTime.addActionListener(this);
			// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
			if(ITPlanterClass.getSystemPlanterSize()>0){
			String tc=Files.LoadManualAutoTime();
				manu2AutoTime.setText(tc);
			}
		}
		return manu2AutoTime;
	}


	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextField getJTextArea() {
		if (commandArea == null) {
			commandArea = new JTextField();
			commandArea.setFont(new Font("Dialog", Font.PLAIN, 10));
			commandArea.addActionListener(this);
			commandArea.addMouseListener(this);
			commandArea.setFocusTraversalKeysEnabled(false);
			commandArea.setText("* -e ");
		}
		return commandArea;
	}

	private JTextField getPhotoHolderArea() {
		if (photoHolder == null) {
			photoHolder = new JTextField();
			photoHolder.setFont(new Font("Dialog", Font.PLAIN, 10));
			photoHolder.addActionListener(this);
			photoHolder.addMouseListener(this);
			photoHolder.setFocusTraversalKeysEnabled(false);
			photoHolder.setName("photoHolder");

			/*
			if(Version.getRevision().equals("Education")==false){
				photoHolder.setText(Files.getPhotoHolder().replace("PhotoHolder ", ""));
			}
			else
			*/
			{
				String s="";
				if(Files.getPhotoHolder().replace("PhotoHolder ", "").contains("C:\\TEMP")==true){
				// Education
					if(IsMacorWin.isMacOrWin()==false){
						s=Desktop.getDesktopPath();//+"\\" // Win
					}
					else
					{
						s=System.getProperty("user.home")+"/"+"Desktop"+"/"; // Mac
					}
				}
				else 
				{
					s=Files.getPhotoHolder().replace("PhotoHolder ", "");
				}
				photoHolder.setText(s);
				Files.setPhotoHolder(s);
			}
		}
		return photoHolder;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//
		JFrame frame = new JFrame();
		
		Settings gjp=new Settings();
		frame.getContentPane().add(gjp);
		
		
		gjp.setEnabled(false);

		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}


	/**
	 * This is the default constructor
	 */
	public Settings() {
		super();
		initialize();
	}

	/*
	 *   X �{�^���������ꂽ����Action��t�������邱��
	 */
	private JPanel jp=null;

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	static String albumIDPW="";
	static String loginIDPW="";
	static String mailIDPW="";


	
	private void initialize() {
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		//
		albumIDPW=AlbumSetting.fread();
		loginIDPW=LoginSetting.fread();
		
		readMailSetup();
		
		GridBagConstraints readSettingsConstraints =null;
		GridBagConstraints smtpLabelConstraints =null;
		GridBagConstraints smtpConstraints = null;
		GridBagConstraints smtpPassConstraints = null;
		GridBagConstraints fromLabelConstraints = null;
		GridBagConstraints getFromConstraints = null;
		GridBagConstraints toLabelConstraints = null;
		GridBagConstraints getToAddressConstraints = null;
		GridBagConstraints pcAutoLabelConstraints = null;
		GridBagConstraints testMailBtnConstraints = null;
		
		// �摜�ۑ������
		GridBagConstraints photoholderLabelConstraints = new GridBagConstraints();
		photoholderLabelConstraints.gridx = 0;
		photoholderLabelConstraints.gridy = 12;
		//
		GridBagConstraints photHolderConstraints = new GridBagConstraints();
//		photHolderConstraints.fill = GridBagConstraints.HORIZONTAL;
		photHolderConstraints.gridx = 6;
		photHolderConstraints.gridy = 12;
		photHolderConstraints.ipadx = 200;
//		photHolderConstraints.gridwidth = 150;
		
		
		
		// �R�}���h����
		GridBagConstraints commandLabelConstraints = new GridBagConstraints();
		commandLabelConstraints.gridx = 7;
		commandLabelConstraints.gridy = 12;
		//
		GridBagConstraints getJAreaConstraints = new GridBagConstraints();
//		getJAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		getJAreaConstraints.gridx = 8;
		getJAreaConstraints.gridy = 12;
		getJAreaConstraints.ipadx = 200;
//		getJAreaConstraints.gridwidth = 150;
				
		
		GridBagConstraints getPC2AutoTimeConstraints = new GridBagConstraints();
		getPC2AutoTimeConstraints.fill = GridBagConstraints.VERTICAL;
		getPC2AutoTimeConstraints.gridwidth = 1;
		getPC2AutoTimeConstraints.gridx = 6;
		getPC2AutoTimeConstraints.gridy = 11;
		getPC2AutoTimeConstraints.ipadx = 100;

		pcAutoLabelConstraints = new GridBagConstraints();
		//pcAutoLabelConstraints.insets = new Insets(4, 23, 7, 1);
		pcAutoLabelConstraints.gridy = 11;
		pcAutoLabelConstraints.ipadx = 0;
		pcAutoLabelConstraints.ipady = 0;
		pcAutoLabelConstraints.gridx = 0;
		
		
		// �Ɠx�Z���T�␳�l
		GridBagConstraints illumSensorLabelConstraints = new GridBagConstraints();
		illumSensorLabelConstraints.gridx = 7;
		illumSensorLabelConstraints.gridy = 9;
		// �Ɠx�Z���T�␳�l����
		GridBagConstraints getIllumSensorCompConstraints = new GridBagConstraints();
		getIllumSensorCompConstraints.gridx = 8;
		getIllumSensorCompConstraints.gridy = 9;
		getIllumSensorCompConstraints.ipadx = 100;		

		// �|���v�u�[�X�g����
		GridBagConstraints pumpBoostLabelConstraints = new GridBagConstraints();
		pumpBoostLabelConstraints.gridx = 7;
		pumpBoostLabelConstraints.gridy = 10;
		// �|���v�u�[�X�g���ԓ���
		GridBagConstraints getPumpBoostTimeConstraints = new GridBagConstraints();
		getPumpBoostTimeConstraints.gridx = 8;
		getPumpBoostTimeConstraints.gridy = 10;
		getPumpBoostTimeConstraints.ipadx = 100;

		// Manual->Auto
		GridBagConstraints manAutoLabelConstraints = new GridBagConstraints();
		manAutoLabelConstraints.gridx = 7;
		manAutoLabelConstraints.gridy = 11;
		// Manual->Auto ����
		GridBagConstraints getManu2AutoTimeConstraints = new GridBagConstraints();
		getManu2AutoTimeConstraints.gridx = 8;
		getManu2AutoTimeConstraints.gridy = 11;
		getManu2AutoTimeConstraints.ipadx = 100;

		// �������킹
		GridBagConstraints clockSetConstraints = new GridBagConstraints();
		//clockSetConstraints.insets = new Insets(7, 1, 3, 64);
		clockSetConstraints.ipadx = 0;
		clockSetConstraints.ipady = 0;
		clockSetConstraints.gridx = 7;
		clockSetConstraints.gridy = 7;

		GridBagConstraints getwLEDConstraints = new GridBagConstraints();
		getwLEDConstraints.fill = GridBagConstraints.VERTICAL;
		getwLEDConstraints.gridwidth = 1;
		getwLEDConstraints.gridx = 6;
		getwLEDConstraints.gridy = 10;
		getwLEDConstraints.ipadx = 100;
		//getwLEDConstraints.ipady = -8;
		//getwLEDConstraints.weightx = 1.0;
		//getwLEDConstraints.insets = new Insets(7, 1, 10, 8);
		GridBagConstraints wLEDLabelConstraints = new GridBagConstraints();
		wLEDLabelConstraints.gridy = 10;
		wLEDLabelConstraints.ipadx = 0;
		wLEDLabelConstraints.ipady = 0;
		wLEDLabelConstraints.gridx = 0;
		GridBagConstraints getResetConstraints = new GridBagConstraints();
		getResetConstraints.gridx = 8;
		getResetConstraints.gridy = 7;

		// 
		GridBagConstraints messageLabelConstraints = new GridBagConstraints();
		messageLabelConstraints.gridx = 0;
		messageLabelConstraints.gridy = 7;

		GridBagConstraints getMessageAreaConstraints = new GridBagConstraints();
		getMessageAreaConstraints.gridx = 6;
		getMessageAreaConstraints.gridy = 7;
		getMessageAreaConstraints.ipadx = 150;


		GridBagConstraints tempSensorCompConstraints = new GridBagConstraints();
		tempSensorCompConstraints.fill = GridBagConstraints.VERTICAL;
		tempSensorCompConstraints.gridx = 6;
		tempSensorCompConstraints.gridy = 9;
		tempSensorCompConstraints.ipadx = 100;
//		tempSensorCompConstraints.ipady = -8;
//		tempSensorCompConstraints.weightx = 1.0;
		//tempSensorCompConstraints.insets = new Insets(8, 1, 7, 3);
		GridBagConstraints tempSensorLabelConstraints = new GridBagConstraints();
		//tempSensorLabelConstraints.insets = new Insets(6, 19, 5, 1);
		tempSensorLabelConstraints.gridy = 9;
		tempSensorLabelConstraints.ipadx = 0;
		tempSensorLabelConstraints.ipady = 0;
		tempSensorLabelConstraints.gridx = 0;
		// clock display
		GridBagConstraints clockDispConstraints = new GridBagConstraints();
		//clockSetConstraints.insets = new Insets(7, 1, 3, 64);
		clockDispConstraints.gridy = 5;
		clockDispConstraints.ipadx = 0;
		clockDispConstraints.ipady = 0;
		clockDispConstraints.gridx = 10;

		
		GridBagConstraints culturePrgSelectConstraints = new GridBagConstraints();
		culturePrgSelectConstraints.fill = GridBagConstraints.VERTICAL;
		culturePrgSelectConstraints.gridwidth = 1;
		culturePrgSelectConstraints.gridx = 8;
		culturePrgSelectConstraints.gridy = 8;
//		culturePrgSelectConstraints.ipadx = 50;
		culturePrgSelectConstraints.ipady = 0;
		culturePrgSelectConstraints.weightx = 1.0;
		//culturePrgSelectConstraints.insets = new Insets(6, 0, 4, 10);
		
		// �͔|���[�h
		GridBagConstraints cultPrgLabelConstraints = new GridBagConstraints();
		//cultPrgLabelConstraints.insets = new Insets(12, 7, 8, 1);
//		cultPrgLabelConstraints.ipadx = 0;
//		cultPrgLabelConstraints.ipady = 0;
		cultPrgLabelConstraints.gridx = 7;
		cultPrgLabelConstraints.gridy = 8;
		GridBagConstraints cameraResoCombConstraints = new GridBagConstraints();
		cameraResoCombConstraints.fill = GridBagConstraints.VERTICAL;
		cameraResoCombConstraints.gridwidth = 1;
		cameraResoCombConstraints.gridx = 6;
		cameraResoCombConstraints.gridy = 8;
//		cameraResoCombConstraints.ipadx = 50;
		//cameraResoCombConstraints.ipady = 3;
		//cameraResoCombConstraints.weightx = 1.0;
		//cameraResoCombConstraints.insets = new Insets(5, 2, 5, 7);
		GridBagConstraints cameraLabelConstraints = new GridBagConstraints();
		//cameraLabelConstraints.insets = new Insets(11, 22, 9, 26);
		cameraLabelConstraints.gridy = 8;
		cameraLabelConstraints.ipadx = 0;
		cameraLabelConstraints.ipady = 0;
		cameraLabelConstraints.gridx = 0;
		
		if(Version.getRevision().contains("Education")==false && Version.getFreeOrPaid().contains("Paid")==true){
		getToAddressConstraints = new GridBagConstraints();
		getToAddressConstraints.fill = GridBagConstraints.VERTICAL;
		getToAddressConstraints.gridwidth = 1;
		getToAddressConstraints.gridx = 8;
		getToAddressConstraints.gridy = 6;
		getToAddressConstraints.ipadx = 150;
		//getToAddressConstraints.ipady = -8;
		//getToAddressConstraints.weightx = 1.0;
		//getToAddressConstraints.insets = new Insets(3, 9, 8, 59);
		toLabelConstraints = new GridBagConstraints();
		//toLabelConstraints.insets = new Insets(4, 4, 7, 3);
		toLabelConstraints.gridx = 7;
		toLabelConstraints.gridy = 6;
		//toLabelConstraints.ipadx = 19;
		//toLabelConstraints.ipady = 0;
		//toLabelConstraints.gridwidth = 1;
		
		getFromConstraints = new GridBagConstraints();
		getFromConstraints.fill = GridBagConstraints.VERTICAL;
		getFromConstraints.gridx = 6;
		getFromConstraints.gridy = 6;
		getFromConstraints.ipadx = 150;
		getFromConstraints.ipady = 0;
//		getFromConstraints.gridwidth = 4;
//		getFromConstraints.weightx = 1.0;
		//getFromConstraints.insets = new Insets(4, 2, 7, 55);
		fromLabelConstraints = new GridBagConstraints();
		//fromLabelConstraints.insets = new Insets(9, 71, 2, 27);
		fromLabelConstraints.ipadx = 0;
		fromLabelConstraints.ipady = 0;
		fromLabelConstraints.gridx = 0;
		fromLabelConstraints.gridy = 6;
		
		// readSettingsConstraints
		readSettingsConstraints= new GridBagConstraints();
		readSettingsConstraints.ipadx = 0;
		readSettingsConstraints.ipady = 0;
		readSettingsConstraints.gridx = 8;
		readSettingsConstraints.gridy = 0;		
		
		// SMTP�T�[�o�[
		smtpLabelConstraints = new GridBagConstraints();
		smtpLabelConstraints.ipadx = 0;
		smtpLabelConstraints.ipady = 0;
		smtpLabelConstraints.gridx = 0;
		smtpLabelConstraints.gridy = 5;
		//�@SMTP�T�[�o�[����
		smtpConstraints = new GridBagConstraints();
		smtpConstraints.fill = GridBagConstraints.VERTICAL;
		smtpConstraints.gridx = 6;
		smtpConstraints.gridy = 5;
		smtpConstraints.ipadx = 150;
		smtpConstraints.ipady = 0;
		//�@PASSWD
		smtpPassConstraints = new GridBagConstraints();
		smtpPassConstraints.fill = GridBagConstraints.VERTICAL;
		smtpPassConstraints.gridx = 7;
		smtpPassConstraints.gridy = 5;
		smtpPassConstraints.ipadx = 60;
		smtpPassConstraints.ipady = 0;		
		//
		// TEST BUTTON
		testMailBtnConstraints = new GridBagConstraints();
		testMailBtnConstraints.fill = GridBagConstraints.VERTICAL;
		testMailBtnConstraints.gridx = 8;
		testMailBtnConstraints.gridy = 5;
			
		
		
		GridBagConstraints picasaLabelConstraints = new GridBagConstraints();
		//picasaLabelConstraints.insets = new Insets(2, 27, 7, 21);
		picasaLabelConstraints.gridy = 2;
		picasaLabelConstraints.ipadx = 0;
		picasaLabelConstraints.ipady = 0;
		picasaLabelConstraints.gridx = 0;
		GridBagConstraints picasaIDConstraints = new GridBagConstraints();
		picasaIDConstraints.fill = GridBagConstraints.VERTICAL;
		picasaIDConstraints.gridx = 6;
		picasaIDConstraints.gridy = 2;
		picasaIDConstraints.ipadx = 150;
		picasaIDConstraints.ipady = 0;
//		picasaIDConstraints.gridwidth = 4;
//		picasaIDConstraints.weightx = 1.0;
		//picasaIDConstraints.insets = new Insets(4, 2, 5, 55);
		GridBagConstraints picasaPasswdConstraints = new GridBagConstraints();
		picasaPasswdConstraints.fill = GridBagConstraints.VERTICAL;
		picasaPasswdConstraints.gridwidth = 1;
		picasaPasswdConstraints.gridx = 16;
		picasaPasswdConstraints.gridy = 2;
		picasaPasswdConstraints.ipadx = 150;
		picasaPasswdConstraints.ipady = 0;
		//picasaPasswdConstraints.weightx = 1.0;
		//picasaPasswdConstraints.insets = new Insets(3, 5, 6, 63);
		GridBagConstraints passswdLabelConstraints = new GridBagConstraints();
		//passswdLabelConstraints.insets = new Insets(10, 14, 3, 100);
		passswdLabelConstraints.gridx = 16;
		passswdLabelConstraints.gridy = 0;
		passswdLabelConstraints.ipadx = 0;
		passswdLabelConstraints.ipady = 0;
		passswdLabelConstraints.gridwidth = 3;
		GridBagConstraints loginLabelConstraints = new GridBagConstraints();
		//loginLabelConstraints.insets = new Insets(8, 24, 9, 77);
		loginLabelConstraints.gridx = 6;
		loginLabelConstraints.gridy = 0;
		loginLabelConstraints.ipadx = 0;
		loginLabelConstraints.ipady = 0;
		loginLabelConstraints.gridwidth = 3;
		GridBagConstraints cloudGardenPasswsConstraints = new GridBagConstraints();
		cloudGardenPasswsConstraints.fill = GridBagConstraints.VERTICAL;
		cloudGardenPasswsConstraints.gridwidth = 1;
		cloudGardenPasswsConstraints.gridx = 16;
		cloudGardenPasswsConstraints.gridy = 1;
		cloudGardenPasswsConstraints.ipadx = 150;
		cloudGardenPasswsConstraints.ipady = 0;
		cloudGardenPasswsConstraints.weightx = 1.0;
		//cloudGardenPasswsConstraints.insets = new Insets(4, 8, 3, 60);
		GridBagConstraints cloudGardenIDConstraints = new GridBagConstraints();
		cloudGardenIDConstraints.fill = GridBagConstraints.VERTICAL;
		cloudGardenIDConstraints.gridwidth = 4;
		cloudGardenIDConstraints.gridx = 6;
		cloudGardenIDConstraints.gridy = 1;
		cloudGardenIDConstraints.ipadx = 150;
		cloudGardenIDConstraints.ipady = 0;
		cloudGardenIDConstraints.weightx = 1.0;
		//cloudGardenIDConstraints.insets = new Insets(6, 2, 1, 55);
		GridBagConstraints cloudGardenConstraints = new GridBagConstraints();
		//cloudGardenConstraints.insets = new Insets(5, 27, 2, 21);
		cloudGardenConstraints.gridy = 1;
		cloudGardenConstraints.ipadx = 0;
		cloudGardenConstraints.ipady = 0;
		cloudGardenConstraints.gridx = 0;
		GridBagConstraints lobinInfoConstraints = new GridBagConstraints();
		//lobinInfoConstraints.insets = new Insets(12, 36, 11, 34);
		lobinInfoConstraints.gridy = 0;
		lobinInfoConstraints.gridx = 0;
		}// skip when education mode
		
		commandLabel = new JLabel();
		commandLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		commandLabel.setText("�R�}���h����");
		commandLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		
		
		//getPhotoholderConstraints
		photoolderLabel = new JLabel();
		photoolderLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		photoolderLabel.setText("�摜�ۑ��ꏊ");
		photoolderLabel.setName("photoolderLabel");
		photoolderLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		
		manAutoLabel = new JLabel();
		manAutoLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		manAutoLabel.setText("Manual->Auto���A����[sec]");
		pcAutoLabel = new JLabel();
		pcAutoLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		pcAutoLabel.setText("PC->Auto���A����[sec]");
		pumpBoostLabel = new JLabel();
		pumpBoostLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		pumpBoostLabel.setText("�|���v�u�[�X�g����[msec]");
		wLEDLabel = new JLabel();
		wLEDLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		wLEDLabel.setText("��LED�_�ő��x[%]");
		messageLabel = new JLabel();
		messageLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		messageLabel.setText("���b�Z�[�W");
		illmSensorLabel = new JLabel();
		illmSensorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		illmSensorLabel.setText("�Ɠx�Z���T�␳�l[�~a]");
		tempSensorLabel = new JLabel();
		tempSensorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		tempSensorLabel.setText("���x�Z���T�␳�l[+b]");
		cultPrgLabel = new JLabel();
		cultPrgLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		cultPrgLabel.setText("�͔|���[�h");
		cultPrgLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		cameraLabel = new JLabel();
		cameraLabel.setText("�J�����𑜓x");
		cameraLabel.setFont(new Font("Dialog", Font.PLAIN, 10));

		if(Version.getRevision().contains("Education")==false && Version.getFreeOrPaid().contains("Paid")==true){
		toLabel = new JLabel();
		toLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		toLabel.setText("To");
		fromLabel = new JLabel();
		fromLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		fromLabel.setText("From");
		smtpLabel = new JLabel();
		smtpLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		smtpLabel.setText("SMTP�T�[�o�[");
		mailLabel = new JLabel();
		mailLabel.setText("Mail");
		picasaLabel = new JLabel();
		picasaLabel.setText("Picasa");
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		
		passwdLabel = new JLabel();
		passwdLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		passwdLabel.setText("�p�X���[�h");
		loginLabel = new JLabel();
		loginLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		loginLabel.setText("���O�C��ID");
		cloudGardenLabel = new JLabel();
		cloudGardenLabel.setText("Cloud-Garden");
		loginInfoLabel = new JLabel();
		loginInfoLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		loginInfoLabel.setText("���O�C�����");
		loginInfoLabel.setBackground(new Color(250, 251, 245));
		loginInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		loginInfoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		loginInfoLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		}// skip when education mode
		
		jp=new JPanel();
		jp.setLayout(new GridBagLayout());
		jp.setBackground(new Color(250,251,245));
		jp.setPreferredSize(new Dimension(800, 480));
		
		/*
		jp.add(loginInfoLabel, lobinInfoConstraints);
		jp.add(cloudGardenLabel, cloudGardenConstraints);
		jp.add(getCloudGardenID(), cloudGardenIDConstraints);
		jp.add(getCloudGardenPasswd(), cloudGardenPasswsConstraints);
		jp.add(loginLabel, loginLabelConstraints);
		jp.add(passwdLabel, passswdLabelConstraints);
		jp.add(getPicasaPasswd(), picasaPasswdConstraints);
		jp.add(getPicasaID(), picasaIDConstraints);
		jp.add(picasaLabel, picasaLabelConstraints);
		*/
		//jp.add(mailLabel, mailLabelConstraints);
		//jp.add(getMailID(), mailIDConstraints);
	//	jp.add(getMailPasswd(), mailPasswdConstraints);
		if(Version.getRevision().contains("Education")==false && Version.getFreeOrPaid().contains("Paid")==true){
			
		jp.add(getSettingReadBtn(), readSettingsConstraints);
			
		jp.add(smtpLabel, smtpLabelConstraints);
		//
		jp.add(getSMTPServer(), smtpConstraints);
		jp.add(getSMTPServerPasswd(), smtpPassConstraints);
		//
		jp.add(fromLabel, fromLabelConstraints);
		jp.add(getFromAddress(), getFromConstraints);
		jp.add(toLabel, toLabelConstraints);
		jp.add(getToAddress(), getToAddressConstraints);
		}
		
		jp.add(cameraLabel, cameraLabelConstraints);
		jp.add(getJComboBox(), cameraResoCombConstraints);
		jp.add(cultPrgLabel, cultPrgLabelConstraints);
		jp.add(getCultureProgramSelect(), culturePrgSelectConstraints);
		
		if(Version.getRevision().contains("Education")==false && Version.getFreeOrPaid().contains("Paid")==true){
		jp.add(getMailTest(), testMailBtnConstraints);
		}
//
		//jp.add(getClockDisp(), clockDispConstraints);
		//
		jp.add(getClockSet(), clockSetConstraints);
		
		//
		jp.add(tempSensorLabel, tempSensorLabelConstraints);
		jp.add(getTempSensorComp(), tempSensorCompConstraints);
		
		//
		jp.add(illmSensorLabel, illumSensorLabelConstraints);
		jp.add(getIllumSensorComp(), getIllumSensorCompConstraints);
		
		if(Version.getRevision().contains("Education")==false && Version.getFreeOrPaid().contains("Paid")==true){
		jp.add(messageLabel, messageLabelConstraints);
		jp.add(getMessageArea(), getMessageAreaConstraints);
		}
		
		jp.add(getReset(), getResetConstraints);
		jp.add(wLEDLabel, wLEDLabelConstraints);
		jp.add(getWhiteLEDSpeed(), getwLEDConstraints);
		jp.add(pumpBoostLabel, pumpBoostLabelConstraints);
		jp.add(getPumpBuustTime(), getPumpBoostTimeConstraints);
		jp.add(pcAutoLabel, pcAutoLabelConstraints);
		jp.add(getPC2AutoTime(), getPC2AutoTimeConstraints);
		jp.add(manAutoLabel, manAutoLabelConstraints);
		jp.add(getManu2AutoTime(), getManu2AutoTimeConstraints);
		jp.add(getJTextArea(), getJAreaConstraints);
		jp.add(commandLabel, commandLabelConstraints);
		
		jp.add(photoolderLabel, photoholderLabelConstraints);
		jp.add(getPhotoHolderArea(), photHolderConstraints);
	//
		if(Version.getRevision().contains("Education")==false && Version.getFreeOrPaid().contains("Paid")==true){
//	label
		GridBagConstraints checkSaveHolderLabelConstrains = new GridBagConstraints();
		checkSaveHolderLabelConstrains.gridx = 0;
		checkSaveHolderLabelConstrains.gridy = 13;
		jp.add(getCheckSaveHolderLabel(), checkSaveHolderLabelConstrains);
// check box		
		GridBagConstraints checkSaveHolderConstrains = new GridBagConstraints();
		checkSaveHolderConstrains.gridx = 6;
		checkSaveHolderConstrains.gridy = 13;
		jp.add(getCheckSaveHolder(), checkSaveHolderConstrains);
		
//		label
			GridBagConstraints checkSavePicasaLabelConstrains = new GridBagConstraints();
			checkSavePicasaLabelConstrains.gridx = 7;
			checkSavePicasaLabelConstrains.gridy = 13;
			jp.add(getCheckSavePicasaLabel(), checkSavePicasaLabelConstrains);
	// check box		
			GridBagConstraints checkSavePicasaConstrains = new GridBagConstraints();
			checkSavePicasaConstrains.gridx = 8;
			checkSavePicasaConstrains.gridy = 13;
			jp.add(getCheckSavePicasa(), checkSavePicasaConstrains);
		}
		
		this.setLayout(new GridBagLayout());
	//
		this.add(jp);
//		this.add(jp, GridBagConstraints);
		observableMan = new ObservableMan();

		// �f�t�H���g�̊ώ@�҂�ǉ�
		
		
		defaultO=new PlanterSetting.ObserverA();
		addObserver(defaultO);
}


	private Component getCheckSavePicasa() {
		if (checkSavePicasa == null) {
			checkSavePicasa = new JCheckBox();
			checkSavePicasa.setFont(new Font("Dialog", Font.PLAIN, 10));
//			checkSaveHolderLabel.setPreferredSize(new Dimension(100, 30));
//			checkSaveHolderLabel.setFocusTraversalKeysEnabled(false);
			checkSavePicasa.addActionListener(this);

			if(Files.getCheckSavePicasa().contains("true")==true){
				checkSavePicasa.setSelected(true);
			} else 
				checkSavePicasa.setSelected(false);
				checkSavePicasa.setName("checkSavePicasa");
//			checkSavePicasa.setText("�w��z���_�[�ɕۑ�");
		}
		return checkSavePicasa;
	}

	private Component getCheckSavePicasaLabel() {
		if (checkSavePicasaLabel == null) {
			checkSavePicasaLabel = new JLabel();
			checkSavePicasaLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
//			checkSaveHolderLabel.setPreferredSize(new Dimension(100, 30));
//			checkSaveHolderLabel.setFocusTraversalKeysEnabled(false);
//			sMTPServer.addActionListener(this);
			checkSavePicasaLabel.setName("checkSavePicasa");
			checkSavePicasaLabel.setText("Picasa�ɕۑ�");
		}
		return checkSavePicasaLabel;
	}
	public static void readMailSetup() {
		//sendMail.read_ID();
		String setup=Files.getSetupMailFileName();
		mailIDPW=Files.readFile(setup);
		String[] p=mailIDPW.split(System.getProperty("line.separator"));
		for(int i=0;i<p.length;i++) p[i]=p[i].replace("\r", "");
		for(int i=0;i<p.length;i++) p[i]=p[i].replace("\n", "");
		/*
		0  sMTPServerString+System.getProperty("line.separator");
		1  sMTPPasswdString+System.getProperty("line.separator");
		2   toAddressString+System.getProperty("line.separator");
		3 fromAddressString+System.getProperty("line.separator");
		4       titleString+System.getProperty("line.separator");
		5 messageAreaString+System.getProperty("line.separator");
		 */
		sMTPServerString=p[0];
	//�@�@�@������
		sMTPPasswdString=Fukugouka(p[1]);// Decode
		toAddressString=p[2];
		fromAddressString=p[3];
		titleString=p[4];
		messageAreaString=p[5];
		
		//SMTPServer
  		//sendMail sm=new sendMail();
  		sendMail.setServerAdress(sMTPServerString);

		//ToAddress
  		sendMail.setToAdress(toAddressString);

		//FromAddress
  		sendMail.setFromAdress(fromAddressString);

		//Title
  		sendMail.setTitleText(titleString);
  		// TEST DUMY DATA
  		sendMail.addMessageText(messageAreaString);  // << 130704 DEBUG
  		sendMail.addMessageText("Temp 24.0 deg");
  		sendMail.addMessageText("illum 5000 lux");
  		sendMail.addMessageText("water 100 %%");
  		//MessageArea
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

private JCheckBox checkSaveHolder = null;
private JLabel checkSaveHolderLabel=null;

private ObservableMan observableMan=null;  
private String message="";
private Observer defaultO=null;
private JLabel loginInfoLabel = null;
private JLabel cloudGardenLabel = null;
private JTextField cloudGardenID = null;
private JPasswordField cloudGardenPasswd = null;
private JLabel loginLabel = null;
private JLabel passwdLabel = null;
private JPasswordField picasaPasswd = null;
private JTextField picasaID = null;
private JLabel picasaLabel = null;
private JLabel mailLabel = null;
private JLabel smtpLabel = null;
public JTextField sMTPServer = null;
public JPasswordField sMTPServerPasswd = null;
private JLabel fromLabel = null;
public JTextField fromAddress = null;
private JLabel toLabel = null;
public JTextField toAddress = null;
private JLabel cameraLabel = null;
private JComboBox resolutionSelect = null;
private JLabel cultPrgLabel = null;
public JComboBox cultureProgramSelect = null;
//
private JButton clockSet = null;
private JButton mailTest = null;
private JButton readSettings=null;

private JLabel tempSensorLabel = null;
public JTextField tempSensorComp = null;
private JLabel illmSensorLabel = null;
public JTextField illumSensorComp = null;
private JLabel messageLabel = null;
public JTextField messageArea = null;
private JButton reset = null;
private JLabel wLEDLabel = null;
public JTextField whiteLEDSpeed = null;
private JLabel pumpBoostLabel = null;
public JTextField pumpBoostTime = null;
private JLabel pcAutoLabel = null;
public JTextField pC2AutoTime = null;
private JLabel manAutoLabel = null;
public JTextField manu2AutoTime = null;
private JTextField commandArea = null;
private JLabel commandLabel = null;
private JLabel photoolderLabel=null;
private JTextField photoHolder=null;

private JLabel checkSavePicasaLabel=null;
private JCheckBox checkSavePicasa = null;

private static 	String cloudGardenStringID="";
private static 	String messageAreaString="";
private static 	String cloudGardenStringPasswd="";
private static 	String picasaIDString="";
private static	String sMTPServerString="";
private static	String sMTPPasswdString="";
private static	String toAddressString="";
private static	String titleString="";
private static	String fromAddressString="";
private static	String picasaPsswdString="";
private static	String tempComp="";
private static	String illumComp="";


//������(no�Ŏw�蕶�����}�C�i�X����[���ɖ߂�])
public static String Fukugouka( String sMTPPasswd1)
{
	String passwd="";
	for(int i=0; i<sMTPPasswd1.length(); i++){
	    passwd = passwd + String.valueOf((char)(sMTPPasswd1.charAt(i) - 3));
		}
	return passwd;
}

//������(no�Ŏw�蕶�����}�C�i�X����[���ɖ߂�])
public static String Angouka( String sMTPPasswd1)
{
	String passwd="";
	for(int i=0; i<sMTPPasswd1.length(); i++){
	    passwd = passwd + String.valueOf((char)(sMTPPasswd1.charAt(i) + 3));
		}
	return passwd;
}


@Override
public void actionPerformed(ActionEvent arg0) {
	//JButton b=(JButton)arg0.getSource();
	String s="";
	int n=ITPlanterClass.getCurrentPlanterNo();
//	if(n==0){
//		System.out.println("ERROR");
//	}
	
	// 130511 y.sakaguti
	if(arg0.getSource().equals(getSettingReadBtn())){
		JButton jcb=(JButton)arg0.getSource();
		message="����Settings class�ł��B"+jcb.getName()+"��������܂����B";
		// read setting infor from current planter
		// ���x�Z���T�[�␳�l T
		int pNo=ITPlanterClass.getCurrentPlanterNo()+1;
		String result=sendCom.sendcom(pNo+" -e T");
		String[] s1=result.split(System.getProperty("line.separator"));
		if(s1!=null&&s1.length>1){
		String[] t=s1[1].split(" ");
		if(t[1].equals(":")==false){
			tempSensorComp.setText(t[2]);
		} else {
			String tc=Files.LoadTempCorrection();
			tempSensorComp.setText(tc);
		}
		} else {
			String tc=Files.LoadTempCorrection();
			tempSensorComp.setText(tc);
		}		
		// �Ɠx�Z���T�␳�l �@S
		{
		String result1=sendCom.sendcom(pNo+" -e S");
		String[] s2=result1.split(System.getProperty("line.separator"));
		if(s2.length>1){
		String[] t=s2[1].split(" ");
		if(t[1].equals(":")==false){
			illumSensorComp.setText(t[2]);
		} else {
			String tc=Files.LoadIllumCorrection();
			illumSensorComp.setText(tc);
		}
		} else {
			String tc=Files.LoadIllumCorrection();
			illumSensorComp.setText(tc);
		}
		}
		// ��LED�_�ő��x     c
		{
			String result1=sendCom.sendcom(pNo+" -e c");
			String[] s3=result1.split(System.getProperty("line.separator"));
			if(s3.length>1){
			String[] t=s3[1].split(" ");
			if(t[1].equals(":")==false){
				whiteLEDSpeed.setText(t[1]);
			} else {
				String tc=Files.LoadWhiteLEDTime();
				whiteLEDSpeed.setText(tc);
			}
			} else {
				String tc=Files.LoadWhiteLEDTime();
				whiteLEDSpeed.setText(tc);
			}
			}
		// �|���v�u�[�X�g����  j
		{
			String result1=sendCom.sendcom(pNo+" -e j");
			String[] s4=result1.split(System.getProperty("line.separator"));
			if(s4.length>1){
			String[] t=s4[1].split(" ");
			if(t[1].equals(":")==false){
				pumpBoostTime.setText(t[1]);
			} else {
				String tc=Files.LoadBoostTime();
				pumpBoostTime.setText(tc);
			}
			} else {
				String tc=Files.LoadBoostTime();
				pumpBoostTime.setText(tc);
			}
			}
		// uto���A����  N
		{
			String result1=sendCom.sendcom(pNo+" -e N");
			String[] s5=result1.split(System.getProperty("line.separator"));
			if(s5.length>1){
			String[] t=s5[1].split(" ");
			if(t[1].equals(":")==false){
				if(ITPlanterClass.getCurrentPlanterClass().getVersion().getMiner()==9){
					String ts=String.valueOf(Integer.valueOf(t[3])*60);// 60�{�ɂ���
					this.pC2AutoTime.setText(ts);					
				} else
				this.pC2AutoTime.setText(t[3]);
			} else {
				String tc=Files.LoadPCAutoTime();
				pC2AutoTime.setText(tc);
			}
			} else {
				String tc=Files.LoadPCAutoTime();
				pC2AutoTime.setText(tc);
			}
			}
		// Manual->Auto���A���� M
		{
			String result1=sendCom.sendcom(pNo+" -e M");
			String[] s2=result1.split(System.getProperty("line.separator"));
			if(s2.length>1){
			String[] t=s2[1].split(" ");
			if(t[1].equals(":")==false){
				if(ITPlanterClass.getCurrentPlanterClass().getVersion().getMiner()==9){
					String st=String.valueOf(Integer.valueOf(t[1]));
					this.manu2AutoTime.setText(st);
				} else
				this.manu2AutoTime.setText(t[1]);
			} else {
				String tc=Files.LoadManualAutoTime();
				manu2AutoTime.setText(tc);
			}
			} else {
				String tc=Files.LoadManualAutoTime();
				manu2AutoTime.setText(tc);
			}
			}

	}
	//

	if(arg0.getSource().equals(getCheckSaveHolder())){
		JCheckBox jcb=(JCheckBox)arg0.getSource();
		message="����Settings class�ł��B"+jcb.getName()+"��������܂����B";
			Files.setCheckSaveHolder(jcb.isSelected());
			Files.savePlantersSettings();
	}
	
	// savePicasa box
		if(arg0.getSource().equals(getCheckSavePicasa())){
			System.out.println("CheckPicasa Save");
			JCheckBox jcb=(JCheckBox)arg0.getSource();
			message="����Settings class�ł��B"+jcb.getName()+"��������܂����B";
			Files.setCheckSavePicasa(jcb.isSelected());
			Files.savePlantersSettings();
		}
		
	
	// �J�����𑜓x�̑I��
	if(arg0.getSource().equals(getResolutionSelect())){
	JComboBox jcb=(JComboBox)arg0.getSource();
	message="����Settings class�ł��B"+jcb.getName()+"��������܂����B";
	s=(String) jcb.getSelectedItem();
	message="�J�����𑜓x�̑I�� "+s;
	String[] r=s.split("X");
	 int w=Integer.parseInt(r[0].replaceAll("[^0-9]",""));//
	 int h=Integer.parseInt(r[1].replaceAll("[^0-9]",""));//
//	int w=Integer.valueOf(r[0]);
//	int h=Integer.valueOf(r[1]);
	ITPlanterClass.getCurrentPlanterClass().getInformation().setCameraResolution(w, h);
	//
	
	// �J�����̉𑜓x�̐ݒ��ۑ�����
	Files.SaveCameraResolution(w,h);
	//	
	}
	
	if(arg0.getSource().equals(cultureProgramSelect)){
	// �͔|���[�h�̑I��
	JComboBox jcb=(JComboBox)arg0.getSource();
	message="����Settings class�ł��B"+jcb.getName()+"��������܂����B";
	s=(String) jcb.getSelectedItem();
	message="�͔|���[�h�̑I�� "+s;
	if(s.contains("��ԍ͔|")){
		// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
		if(ITPlanterClass.getSystemPlanterSize()>0){
		String result=sendCom.sendcom((n+1)+" -e y");
		itp_Logger.logger.info(result);
		// �͔|���[�h��ۑ�����
		Files.SaveCultivationMode("CultivationMode night");
		}
	} else
	if(s.contains("�ʏ�͔|")){
		// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
		if(ITPlanterClass.getSystemPlanterSize()>0){
		String result=sendCom.sendcom((n+1)+" -e s");
		itp_Logger.logger.info(result);
		Files.SaveCultivationMode("CultivationMode day");
		}
	} 

	}
	
	/// 
	if(arg0.getSource().equals(mailTest)){
		JButton jcb=(JButton)arg0.getSource();
		message="����Settings class�ł��B"+jcb.getName()+"��������܂����B";
		// SEND TEST MAIL
		System.out.println("Mail�e�X�g");
	
//	�Ȃ����ł܂��ĕ\������Ȃ�
//		Progress p=new Progress();
//		p.run();		
		sendMail.setFromAdress(fromAddressString);
		sendMail.setToAdress(toAddressString);
		sendMail.setServerAdress(sMTPServerString);
		sendMail.setPasswdText(Angouka(sMTPPasswdString));// ENCODE
		sendMail.setTitleText(titleString+" "+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName());
		sendMail.setMessageText(messageAreaString);
		sendMail.mailSend();

//
		sendMail.setMessageText(messageAreaString);
		// ���m�点���[�����o���B
		sendMail.addMessageText(System.getProperty("line.separator"));
		sendMail.addMessageText("Temp 24.0");
		sendMail.addMessageText("Illum 5000");
		sendMail.addMessageText("Water 30");
			//
		Settings.readMailSetup();// read from filezzz
			//PlanterSetting.setOptions() ;// read from panel
		sendMail.mailSend();// �����点���[��
//
		
//		new SimpleSendMail(toAddressString,fromAddressString,sMTPServerString,Fukugouka(sMTPPasswdString),titleString,messageAreaString);
        System.out.println("���[�����M�I��");
		//
	}
	
	if(arg0.getSource().equals(clockSet)){
	//��������	
//		���݂̎�����\�����邩�H
		
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
		String before=sendCom.sendcom((n+1)+" -e G").split(System.getProperty("line.separator"))[1];
	message="�������� "+"^";
	String result="";
	if(IsMacorWin.isMacOrWin()==true){
		result=sendCom.sendcom((n+1)+" -e ^");// Mac ^
	} else {
		result=sendCom.sendcom((n+1)+" -e ^");// win ^^		
	}
	String now=sendCom.sendcom((n+1)+" -e G").split(System.getProperty("line.separator"))[1];
	JOptionPane.showMessageDialog(null,before+" "+now);
	}
	}
	
	if(arg0.getSource().equals(tempSensorComp)){
	//���x�␳�l����
	JTextField jta=(JTextField)arg0.getSource();
	message="���x�␳�l "+jta.getText();
	Sensor sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
	tempComp=jta.getText();
	sensor.setTempCorrect(Double.valueOf(tempComp));
	message="TempCorrect "+jta.getText();
	Files.SaveTempCorrection(message);
	}
	
	if(arg0.getSource().equals(illumSensorComp)){
	//�Ɠx�␳�l����
	JTextField jta=(JTextField)arg0.getSource();
	message="�Ɠx�␳�l "+jta.getText();
	Sensor sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
	illumComp=jta.getText();
	sensor.setIllumCorrect(Double.valueOf(illumComp));
	message="IllumCorrect "+jta.getText();
	Files.SaveIllumCorrection(message);
	}
	
	if(tempComp.length()>0 && illumComp.length()>0){
		Sensor sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
// �Z���T�[�␳�l��ۑ�����B�@�@planters.txt�ɕۑ����������K�؁B�@���̂܂܂ł͕����v�����^�[�ɑΉ��ł��Ȃ��B
		sensor.save(tempComp+System.getProperty("line.separator")+illumComp+System.getProperty("line.separator"));
//		System.out.println("CompData Saved");
	}
	
	////
	if(arg0.getSource().equals(manu2AutoTime)){
	//Manual-��Auto���A����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	JTextField jta=(JTextField)arg0.getSource();
	message="Manual->Auto���A���� "+jta.getText();
	String result=sendCom.sendcom((n+1)+" -e M"+jta.getText());
	itp_Logger.logger.info(result);
	message="ManualAutoTime "+jta.getText();
	Files.SaveManualAutoTime(message);
		}
	}
	
	if(arg0.getSource().equals(pC2AutoTime)){
	//PC-��Auto���A����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	JTextField jta=(JTextField)arg0.getSource();
	message="uto���A���� "+jta.getText();
	String tval=jta.getText();
	String result="";
	pVersion pv=ITPlanterClass.getCurrentPlanterClass().getVersion();
	if(pv.getMiner()==9){
	String ts=String.valueOf(Integer.valueOf(tval)/60);	// send 1/60
	result=sendCom.sendcom((n+1)+" -e N"+ts);
	} else
	result=sendCom.sendcom((n+1)+" -e N"+tval);
	
	itp_Logger.logger.info(result);
	message="PCAutoTime "+jta.getText();
	Files.SavePCAutoTime(message);
		}
	}
	
	if(arg0.getSource().equals(pumpBoostTime)){
	//PumpBoost����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	JTextField jta=(JTextField)arg0.getSource();
	message="PumpBoost���� "+jta.getText();
	String result=sendCom.sendcom((n+1)+" -e j"+jta.getText());
	itp_Logger.logger.info(result);
	message="PumpBoostTime "+jta.getText();
	Files.SaveBoostTime(message);
		}
	}
	
	//WhiteLEDSpeed����
	if(arg0.getSource().equals(whiteLEDSpeed)){
		// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
		if(ITPlanterClass.getSystemPlanterSize()>0){
		JTextField jta=(JTextField)arg0.getSource();
		message="WhiteLEDSpeed "+jta.getText();
		String result=sendCom.sendcom((n+1)+" -e c"+jta.getText());
		itp_Logger.logger.info(result);
		message="WhiteLEDTime "+jta.getText();
		Files.SaveWhiteLEDTime(message);
		}
	}

	/////// CLOUD GARDEN ////////
	if(arg0.getSource().equals(cloudGardenID)){
		//CloudGardenID
		JTextField jta=(JTextField)arg0.getSource();
		message="CloudGardenID "+jta.getText();
		cloudGardenStringID=jta.getText();
		}

	if(arg0.getSource().equals(cloudGardenPasswd)){
		//CloudGardenPasswd
		JTextField jta=(JTextField)arg0.getSource();
		message="CloudGardenPasswd "+jta.getText();
		cloudGardenStringPasswd=jta.getText();
		}
	
	if(arg0.getSource().equals(picasaID)){
		//PicasaID
		JTextField jta=(JTextField)arg0.getSource();
		message="PicasaID "+jta.getText();
		picasaIDString=jta.getText();
		}

	if(arg0.getSource().equals(picasaPasswd)){
		//PicasaPasswd
		JTextField jta=(JTextField)arg0.getSource();
		message="PicasaPasswd "+jta.getText();
		picasaPsswdString=jta.getText();
		}
	
	// Login�ݒ�t�@�C���ɏ�����������
	if(cloudGardenStringPasswd.length()>0 && cloudGardenStringID.length()>0){
	LoginSetting.save_ID(cloudGardenStringID+System.getProperty("line.separator")+cloudGardenPasswd+System.getProperty("line.separator"));
//	System.out.println("LoginSetting Data Saved");
	}

	// PICASA�ݒ�t�@�C���ɏ�����������
	if(picasaPsswdString.length()>0 && picasaIDString.length()>0){
	AlbumSetting.save_ID(picasaIDString+System.getProperty("line.separator")+picasaPsswdString+System.getProperty("line.separator"));
//	System.out.println("AlbumSetting Data Saved");
	}
	

	if(arg0.getSource().equals(sMTPServer)){
		//SMTPServer
		JTextField jta=(JTextField)arg0.getSource();
		sMTPServerString=jta.getText();
		message="SMTPServer "+jta.getText();
		sendMail.setServerAdress(sMTPServerString);
		saveMailSetup();;
		}
	
	if(arg0.getSource().equals(sMTPServerPasswd)){
		//SMTPServer
		JTextField jta=(JTextField)arg0.getSource();
		// 
		sMTPPasswdString=jta.getText();
		
		
		
		message="SMTPServerPasswd "+sMTPPasswdString;
		sendMail.setPasswdText(sMTPPasswdString);
		//
		saveMailSetup();;
		}

	if(arg0.getSource().equals(toAddress)){
		//ToAddress
		JTextField jta=(JTextField)arg0.getSource();
		toAddressString=jta.getText();
		message="ToAddress "+ toAddressString;
		sendMail.setToAdress(toAddressString);
		saveMailSetup();;
		}

	if(arg0.getSource().equals(fromAddress)){
		//FromAddress
		JTextField jta=(JTextField)arg0.getSource();
		fromAddressString=jta.getText();
		message="FromAddress "+fromAddressString;
		sendMail.setFromAdress(fromAddressString);
		saveMailSetup();;
		}
	
	if(arg0.getSource().equals(titleString)){
		//TitleArea
		JTextField jta=(JTextField)arg0.getSource();
		 titleString=jta.getText();
		message="TitleArea "+titleString;
		sendMail.setMessageText(titleString);
		saveMailSetup();;
		}


	if(arg0.getSource().equals(messageArea)){
		//MessageArea
		JTextField jta=(JTextField)arg0.getSource();
		 messageAreaString=jta.getText();
		message="MessageArea "+messageAreaString;
		sendMail.setMessageText(messageAreaString);
		saveMailSetup();;
		}
	
	// Save Mail Settings
	if(sMTPServerString.length()>0 && toAddressString.length()>0 && fromAddressString.length()>0){
		/*
		0  sMTPServerString+System.getProperty("line.separator");
		1  sMTPPasswdString+System.getProperty("line.separator");
		2   toAddressString+System.getProperty("line.separator");
		3 fromAddressString+System.getProperty("line.separator");
		4       titleString+System.getProperty("line.separator");
		5 messageAreaString+System.getProperty("line.separator");
		 */
		saveMailSetup();
//		System.out.println("Mail Data Saved");
	}

	if(arg0.getSource().equals(commandArea)){
		//commandArea
		// -e A
		// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
		if(ITPlanterClass.getSystemPlanterSize()>0){
		JTextField jta=(JTextField)arg0.getSource();
		message="CommandArea "+jta.getText();
		String result=sendCom.sendcom((n+1)+" "+jta.getText());
		itp_Logger.logger.info(result);
		jta.setText(result);
		}
		}
	
	if(arg0.getSource().equals(reset)){
		//Reset
		// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
		if(ITPlanterClass.getSystemPlanterSize()>0){
		JButton jta=(JButton)arg0.getSource();
		message=jta.getText();
		String result=sendCom.sendcom((n+1)+" -e s");
		itp_Logger.logger.info(result);
		}
		JOptionPane.showMessageDialog(null,"�H��o�גl�ɖ߂��܂����B");
		}
	
	
//	System.out.println(message);

	observableMan.setMessage(message);
	// �ώ@�ґS���ɒʒm
	observableMan.notifyObservers();
	

}
private void saveMailSetup() {
	String m="";
	m=m+ sMTPServerString+System.getProperty("line.separator");
	// TODO
	m=m+ Angouka(sMTPPasswdString)+System.getProperty("line.separator");// ENCODE
	m=m+  toAddressString+System.getProperty("line.separator");
	m=m+fromAddressString+System.getProperty("line.separator");
	m=m+titleString+System.getProperty("line.separator");// title
	m=m+messageAreaString+System.getProperty("line.separator");
	Files.saveMailSetup(m);
}

//
// �ݒ�p�l���̏����v�����^�[�ɑ���B
//
void setAllInformations()
{
	int n=ITPlanterClass.getCurrentPlanterNo();
	
	
	//���x�␳�l����
	JTextField jta=tempSensorComp;
	Sensor sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
	tempComp=jta.getText();
	sensor.setTempCorrect(Double.valueOf(tempComp));


	//�Ɠx�␳�l����
	jta=illumSensorComp;
	sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
	illumComp=jta.getText();
	sensor.setIllumCorrect(Double.valueOf(illumComp));
	
	
	
	// �␳�l�̕ۑ�
	if(tempComp.length()>0 && illumComp.length()>0){
		sensor=ITPlanterClass.getCurrentPlanterClass().getSensor();
		sensor.save(tempComp+System.getProperty("line.separator")+illumComp+System.getProperty("line.separator"));
		Files.SaveIllumCorrection("TempCorrect "+tempComp);
		Files.SaveTempCorrection("IllumCorrect "+illumComp);
	}

	
	
// 2013/5/11 y.sakaguti	
	// ���x�Z���T�␳�l�̐ݒ�
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	jta=tempSensorComp;
	// set Manual->Auto time to planter
	String result=sendCom.sendcom((n+1)+" -e T"+jta.getText());
	itp_Logger.logger.info(result);
	Files.SaveTempCorrection("TempCorrect "+jta.getText());
	}
	// �Ɠx�Z���T�␳�l�̐ݒ�
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	jta=illumSensorComp;
	// set Manual->Auto time to planter
	String result=sendCom.sendcom((n+1)+" -e S"+jta.getText());
	itp_Logger.logger.info(result);
	Files.SaveIllumCorrection("IllumCorrect "+jta.getText());
	}
	// 2013/5/11 y.sakaguti	
	
	

	//Manual-��Auto���A����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	jta=manu2AutoTime;
	// set Manual->Auto time to planter
	String result=sendCom.sendcom((n+1)+" -e M"+jta.getText());
	itp_Logger.logger.info(result);
	Files.SaveManualAutoTime("ManualAutoTime "+jta.getText());
	}
	
	//PC-��Auto���A����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	jta=pC2AutoTime;
	String result="";
	String val=jta.getText();
	// set uto time to planter
	if(ITPlanterClass.getCurrentPlanterClass().getVersion().getMejar()==9){
		String ts=String.valueOf(Integer.valueOf(val)/60);// 1/60
	result=sendCom.sendcom((n+1)+" -e N"+ts);
	} else
	result=sendCom.sendcom((n+1)+" -e N"+val);
		
	itp_Logger.logger.info(result);
	Files.SavePCAutoTime("PCAutoTime "+jta.getText());
	}
	
	//PumpBoost����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	jta=pumpBoostTime;
	// set boost time to planter
	String result=sendCom.sendcom((n+1)+" -e j"+jta.getText());
	itp_Logger.logger.info(result);
	Files.SaveBoostTime("PumpBoostTime "+jta.getText());
	}
	
	//WhiteLEDSpeed����
	// �v�����^�[���Ȃ��ƃG���[�ɂȂ�̂�
	if(ITPlanterClass.getSystemPlanterSize()>0){
	jta=whiteLEDSpeed;
	// set LED speed to planter
	String result=sendCom.sendcom((n+1)+" -e c"+jta.getText());
	itp_Logger.logger.info(result);
	Files.SaveWhiteLEDTime("WhiteLEDTime "+jta.getText());
	}

		//CloudGardenID
		jta=cloudGardenID;
		if(jta!=null){
		cloudGardenStringID=jta.getText();
		}

		//CloudGardenPasswd
		jta=cloudGardenPasswd;
		if(jta!=null){
		cloudGardenStringPasswd=jta.getText();
		}
	
		//PicasaID
		jta=picasaID;
		if(jta!=null){
		picasaIDString=jta.getText();
		}
		
		//PicasaPasswd
		if(jta!=null){
		jta=picasaPasswd;
		picasaPsswdString=jta.getText();
		}
	
	// Login�ݒ�t�@�C���ɏ�����������
	if(cloudGardenStringPasswd.length()>0 && cloudGardenStringID.length()>0){
	LoginSetting.save_ID(cloudGardenStringID+System.getProperty("line.separator")+cloudGardenPasswd+System.getProperty("line.separator"));
//	System.out.println("LoginSetting Data Saved");
	}

	// PICASA�ݒ�t�@�C���ɏ�����������
	if(picasaPsswdString.length()>0 && picasaIDString.length()>0){
	AlbumSetting.save_ID(picasaIDString+System.getProperty("line.separator")+picasaPsswdString+System.getProperty("line.separator"));
//	System.out.println("AlbumSetting Data Saved");
	}
	
		//SMTPServer
		jta=sMTPServer;
		sMTPServerString=jta.getText();
		sendMail.setServerAdress(jta.getText());
		
		//
		jta=sMTPServerPasswd;
		sMTPPasswdString=jta.getText();
		sendMail.setPasswdText(Angouka(sMTPPasswdString));
		

		//ToAddress
		jta=toAddress;
		toAddressString=jta.getText();
		sendMail.setToAdress(jta.getText());

		//FromAddress
		jta=fromAddress;
		fromAddressString=jta.getText();
		sendMail.setFromAdress(jta.getText());
		

		//MessageArea
		jta=messageArea;
		 messageAreaString=jta.getText();
		 sendMail.setMessageText(messageAreaString);
		
	
	// Save Mail Settings
	if(sMTPServerString.length()>0 && toAddressString.length()>0 && fromAddressString.length()>0){
		String m="";
		m=m+ sMTPServerString+System.getProperty("line.separator");
		m=m+ Angouka(sMTPPasswdString)+System.getProperty("line.separator");
		m=m+  toAddressString+System.getProperty("line.separator");
		m=m+fromAddressString+System.getProperty("line.separator");
		m=m+      titleString+System.getProperty("line.separator");
		m=m+messageAreaString+System.getProperty("line.separator");
		Files.saveMailSetup(m);
//		System.out.println("Mail Data Saved");
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
public void update(Observable o, Object arg) {
String str = (String) arg;
System.out.println("����Settings class�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
	}
}

@Override
public void mouseClicked(MouseEvent arg0) {
	System.out.println("MouseClicked");
	System.out.println(((JTextComponent) arg0.getSource()).getName());
	if(((JTextComponent) arg0.getSource()).getName()!=null){
	if(((JTextComponent) arg0.getSource()).getName().equals("photoHolder")==true){
		JFrame frame=new JFrame();
		JFileChooser file = new JFileChooser(new File(".").getAbsoluteFile().getParent());
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (file.showDialog(frame, null) == JFileChooser.CANCEL_OPTION){
				return;
			}
			String s=file.getSelectedFile().getPath();
			if(IsMacorWin.isMacOrWin()==true){
				s = s+"/";
			} else {
				s = s + "\\";
			}
			((JTextComponent)arg0.getSource()).setText(s);
			Files.setPhotoHolder(s);
			
			if(Version.getRevision().equals("Education")!=true && checkSaveHolder.isSelected()==true){
				if(Version.getFreeOrPaid().contains("Pais")==true) Files.setCheckSaveImage("true");
				else
					Files.setCheckSaveImage("false");
			} else {
				Files.setCheckSaveImage("false");
			}
			Files.savePlantersSettings();
	}
	} else {
	if(ITPlanterClass.getSystemPlanterSize()>0){
		commandArea.setText("* -e ");
		}
	}
}


@Override
public void mouseEntered(MouseEvent arg0) {
	System.out.println("mouseEntered");

}


@Override
public void mouseExited(MouseEvent arg0) {
	
}


@Override
public void mousePressed(MouseEvent arg0) {
	System.out.println("mousePressed");
	
}


@Override
public void mouseReleased(MouseEvent arg0) {
	
}


public JComboBox getResolutionSelect() {
	return resolutionSelect;
}


public void setResolutionSelect(JComboBox resolutionSelect) {
	this.resolutionSelect = resolutionSelect;
}


}