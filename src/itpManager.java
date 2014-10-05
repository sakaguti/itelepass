
import java.awt.AWTException;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import javax.swing.JOptionPane;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * �^�X�N�g���C�풓�A�v���T���v��
 */

public final class itpManager {
	private static LoginSetting itpg=null;
	private static AlbumSetting itppicasa=null;
//	private static itplanter itp=null;
	private static itpConsole itpconsole=null;
	
	// ��d�N���֎~�̂��߂̃t�@�C���X�g���[��
	private static FileOutputStream fos = null;
	private static FileChannel fchan = null;
	private static FileLock flock=null;
	
	// �{�Ԋ�
	public static final int port_no = 8080;
	public static final String webName = "http://www.cloud-garden.com";//175.41.238.34

	//�J����
	public static final int dev_port_no = 8081;
	public static final String dev_webName = "http://dev.cloud-garden.com";//175.41.238.34
	
	private static PlanterSetting planterSetting =null;
	private static itpAbout itpabout;
	  
	/** �R���X�g���N�^ */

	public itpManager()	 {
		//
		//
		//
		//
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				planterSetting = new PlanterSetting();
				planterSetting.setThisClass(planterSetting);
				planterSetting.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				planterSetting.setSize(new Dimension(640,400));
				planterSetting.setVisible(true);				
			}
		});
		
        // �^�X�N�g���C�A�C�R��
    	Image image=null;
    	InputStream ips =null;
    	
    		if(IsMacorWin.isMacOrWin()==true ){
    			// MacOSX
    			if(IsOS.isOS().contains("Mac")==true)
    	    	ips = getClass().getResourceAsStream("icon_Mac.png");
    			// Linux
    			else if(IsOS.isOS().contains("Linux")==true)
    	    	ips = getClass().getResourceAsStream("icon_Linux.png");
    		} else {
    			// Windows
    	    	ips = getClass().getResourceAsStream("icon_Win.png");
    		}
    	if (ips == null) {
    		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			if(IsOS.isOS().contains("Mac")==true)
		    	   image = new ImageIcon("icon_Mac.png").getImage();
			// Linux
			else if(IsOS.isOS().contains("Linux")==true)
		    	   image = new ImageIcon("icon_linux.png").getImage();
    		} else {
    			// Windows
    			image = new ImageIcon("icon_Win.png").getImage();
    		}
    	} else {
    	   try {
			image = ImageIO.read(ips);
		} catch (IOException e1) {
			//
			e1.printStackTrace();
		}
    	}
    	 final TrayIcon icon = new TrayIcon(image);

    	 
        // �|�b�v�A�b�v���j���[
        PopupMenu menu = new PopupMenu();
        // ���j���[�̗�
        if(Version.getRevision().contains("Education")==false){
        MenuItem aItem = new MenuItem("�N���E�h�E�K�[�f���̔����J��");
        aItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Open cloud-garden URL
                    openURL();
                    //
                   }
        	});
        menu.add(aItem);
        }
        
        // �Z���N�^�[�̃��j���[
        MenuItem updateItem = new MenuItem("�A�C�e�B�v�����^�[�̑���");
        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(planterSetting!=null)
            		planterSetting.setVisible(true);
            }
        });
        // ���j���[�Ƀ��j���[�A�C�e����ǉ�
        menu.add(updateItem);

             
        if(Version.getRevision().contains("Education")==false){
        // �R���\�[���E�B���h�E�̃��j���[
        MenuItem conSeleItem = new MenuItem("�R���\�[�����J��");
        itpconsole = new itpConsole("Console");
        
        conSeleItem.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if(itpconsole!=null)
        			itpconsole.setVisible(true);
            }
        });
        menu.add(conSeleItem);
        }

        // About�̃��j���[
        MenuItem aboutItem = new MenuItem("���̃A�v���ɂ���");
        itpabout = new itpAbout("About");
        aboutItem.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if(itpabout!=null)
        			itpabout.setVisible(true);
            }
        });
        menu.add(aboutItem);

        // �I�����j���[
        MenuItem exitItem = new MenuItem("�I��");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	/*
            	if(itp.isAlive()){
				// say CLS
				itp.stopRunning();
				itp_Logger.logger.info("itplanter thread stopping");
				// wait untill thread stopping
				while(itp.isAlive()){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
				// Save Console Log
				itpconsole.log_save();
            	}
				//
				 */
                System.exit(0);		// �A�v���P�[�V�������I������
            }
        });
    
        if(Version.getRevision().contains("Education")==false){
        // �ݒ胁�j���[       
        PopupMenu menu2 = new PopupMenu("�ݒ�");
        // �ݒ胁�j���[ �̃T�u���j���[
        MenuItem setupItem = new MenuItem("���O�C�����ݒ�");
        itpg=new LoginSetting(); // with Pipe I/O
        itpg.LoginSetting_run(); 

        setupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(itpg!=null)
            		itpg.setVisible(true);
            }
        });
        menu.add(menu2);

        // Picasa�ݒ胁�j���[ �̃T�u���j���[       
        MenuItem picasaItem= new MenuItem("Picasa���ݒ�");
        
    	itppicasa=new AlbumSetting();// with Pipe I/O
    	itppicasa.AlbumSetting_run();
    	
    	picasaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(itppicasa!=null)
            		itppicasa.setVisible(true);
            }
        });

        // �T�u���j���[�ɃA�C�e����ǉ�
        menu2.add(setupItem);
       // menu2.add(planterItem);
        menu2.add(picasaItem);
        }
        
        
        menu.add(exitItem);
        icon.setPopupMenu(menu);
        

        // �^�X�N�g���C�Ɋi�[
        try {
			SystemTray.getSystemTray().add(icon);
		} catch (AWTException e3) {
			// 
			e3.printStackTrace();
		}
              
     	
 /*    	
        // check setupfiles
        boolean s=true;
        while(s){
        	boolean[] sw={false,false,false};

    		// setup.txt
    		if(sw[0]==false){
    		File f0 = new File(Files.getSetupfile());
    		if(f0!=null){
    		byte[] b = new byte[(int) f0.length()];
            if(b.length==0 && sw[0]==false && itpg.isVisible()==false){
            	// empty setup.txt
            	itpg.setVisible(true);
            	sw[0]=true;
            	continue;
            	} else sw[0]=true;
    			}
    		}
    		// planters.txt
            if(sw[1]==false){
            	File f0 = new File(Files.getPlantersfile());
            	byte[] b = new byte[(int) f0.length()];
            	if(b.length==0 && sw[1]==false && itpp.isVisible()==false){
            	// empty planters.txt
            	itpp.setVisible(true);
            	sw[1]=true;
            	continue;
            } else sw[1]=true;
            }

            if(Version.getRevision().contains("Education")==false){
            // PicasaSetup.txt
            if(sw[2]==false){
            	if(Files.getCheckSavePicasa().contains("true")==true){
            	File f0 = new File(Files.getupAlbumFile());
            	byte[] b = new byte[(int) f0.length()];
            if(b.length==0 && sw[2]==false && itppicasa.isVisible()==false ){
            	// empty picasaSetup.txt
            	itppicasa.set_login(itpg.get_login());	// default
            	itppicasa.set_passwd(itpg.get_passwd());// default
            	itppicasa.setVisible(true);
            	sw[2]=true;
            	continue;
            			} else sw[2]=true;
            		}
            	}
            }

            //
            try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        } //
        
           
        // setup.txt ��timeStamp���ύX���ꂽ�ꍇ�ɂ́A���C���X���b�h���ċN������B
        String start=timeStamp.getSettingTimeStamp();
        String startTimeStamp=timeStamp.getPlanterTimeStamp();
		while(true){// Loop
			String nowSetting=timeStamp.getSettingTimeStamp();
			String nowPlanter=timeStamp.getPlanterTimeStamp();
							
			//JOptionPane.showMessageDialog(null,now+" "+start);
			if(nowSetting.equals(start)==false ){

	            	// itpManager���ċN������
	        	} else
			if( nowPlanter.equals(startTimeStamp)==false ){
				// REN COMMAND REQUEST
		      	// ReStart
	
			}
			else {
				// Sleep
	            try {
					Thread.sleep(1000);
						} catch (InterruptedException e2) {
					e2.printStackTrace();
						}
		        }
			}// Loop end   
*/
        
    }


    
	public void openPlanterSetting() {
		// Open Cloud-Garden Page
        Desktop desktop = Desktop.getDesktop();
        File f=new File("PlanterSetting.jar");
        try {
			desktop.open(f);
		} catch (IOException e) {
			e.printStackTrace();
		}       
	}

	public void openURL() {
		// Open Cloud-Garden Page
        Desktop desktop = Desktop.getDesktop();
        
        try {
        	
        	// full path need.
        	File f = new File("debug.txt");
        	try 
        	{	
        		FileInputStream fi = new FileInputStream(f);
        		fi.close();

    			// dev site
    			desktop.browse(new URI(dev_webName));// �{�Ԃł́A�Ȃ�������	
            	// command site
    			desktop.browse(new URI("http://api.cloud-garden.com/tasting"));
        	}
        	catch(Exception e)
        	{
        		// �Ȃ���΁A�ʏ탂�[�h�ŋN������B
            	// public site
    			desktop.browse(new URI(webName));
        	}
        		
        	

	//		desktop.browse(new URI(hostName));
			

 
		} catch (IOException e) {
			// 
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// 
			e.printStackTrace();
		}
	}
	
    /** ���C�����\�b�h */ 
    public static void main(String[] args) throws Exception {
	//
    	/*
        SplashSample splash = new SplashSample();     
        if(splash.getContext()!=null)
        			splash.setVisible(true);
		*/
    	// Mac�̏ꍇ�A���[�U�[�f�B���N�g���[�Ƀf�[�^���R�s�[����B
    	//Files.initialCopyFiles();
    	FileUtil.setupFiles();		// 
    	Thread.sleep(300);
    	//
    	//
		Files.setupData();
		//
		try {
//			fos = new FileOutputStream("./lockfile");
			String userProfile="";
			if(IsMacorWin.isMacOrWin()==true ){
				// MacOSX
				userProfile=System.getProperty("user.home");
				userProfile=userProfile+"/itpManager"+Version.getFolderName();// Mac
			} else {
				// Windows
				userProfile=System.getenv("APPDATA");
				userProfile=userProfile+"\\itpManager"+Version.getFolderName();
			}
		
			File newdir = new File(userProfile);
			newdir.mkdir();

			fos = new FileOutputStream(userProfile+"/lockfile");
			fchan = fos.getChannel();
			flock = fchan.tryLock();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "lockfile�����܂���BWindowsVista��Windows7�����g���Ȃ�΁A�Ǘ��Ҍ����Ŏ��s���Ă݂Ă��������B");
			throw e;
		}
		
	    // initialize Logger System
    	//itp_Logger.itp_LoggerSetup();
    	
	//	
		if (flock == null) {
			JOptionPane.showMessageDialog(null, "����itpManager���N�����Ă��܂��B");
			System.exit(0);
		}
		
		// ���b�N����������V���b�g�_�E���t�b�N�ɓo�^
		Runtime.getRuntime().addShutdownHook( new Thread() {
		    @Override
			public void run() {
				try {
					if(flock!=null)	flock.release();
					if(fchan!=null) fchan.close();
					if(fos!=null) fos.close();		
					new File("./lockfile").delete();
				}
				catch (IOException e) {	
					e.printStackTrace();
					}
		    }
		});
		
		//
		//ProgressBarPanel pbp=new ProgressBarPanel();
		//pbp.start();		
    	// main
    	new itpManager();
  
    	/*
    	if(splash!=null)
    		splash.setVisible(false);
    		*/
        // end of main
	}
  
}