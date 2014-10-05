//
// capture from USB cam and upload image to Picasa
//	itplants.ltd.	2011
//	Yosiyuki SAKAGUCHI
//
//
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JOptionPane;


import com.google.gdata.client.photos.PicasawebService;



public class CameraCapture {
	private static boolean runState=false;
	static final String sCurrentDir = new File(".").getAbsoluteFile().getParent();  //  
	static final String CapturecommandMac=new String(sCurrentDir+"/com"+"/Mac"+"/capture");  // MacOSX
	static final String CapturecommandLinux=new String(sCurrentDir+"/com"+"/Linux"+"/capture");  // MacOSX
	static final String CapturecommandWin=new String("\""+sCurrentDir+"\\com\\Win\\capture.exe"+"\"");  //Win

	//�B�e�^�C���e�[�u���̐�
	static int timeTableNo=0;
	// �B�e�^�C���e�[�u��
	static String[] timeTable=null;

	public static ArrayList<TimerTask> cameraSchedule=new ArrayList<TimerTask>();
//	public static ArrayList<Timer> cameraTimer=new ArrayList<Timer>();
	
	// Picasa ligin info
	public static String PicasaLogin="";
	public static String PicasaPasswd="";
	
	private static boolean state=false;
	///
	private static String capture_return = null;
	//private static int nCam=0;// �J������
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String f=Files.getPhotoHolder();
		System.out.println(f);
		f=f.replace("PhotoHolder ", "");
		f=f+"test.bmp";
//		System.out.println("nCam="+CameraCapture.getNCam());
		
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		//monitorCam(0);
		//CameraCapture cc = new CameraCapture();
		System.out.print(f);
		//CameraCapture.capture_thread("-f "+f);
		
		
		String args0 = "-c 0 "+" -f "+f;
		itp_Logger.logger.info("autoCapture_processing: Args="+args0);
		String ret=CameraCapture.capturePhoto(args0, true);// Light ON/PFF
		System.out.print("return:"+ret);
		//monitorCam(1);
		//monitorCam(2);
		//monitorCam(3);
	}
	
	public static void monitorCam(int c)
	{
		
		// Take a picture
		//String mycommand = Capturecommand()+" "+"-c "+c;
//		String arg="-c "+c;
//		System.out.println( Capturecommand()+" "+arg);
        int w=ITPlanterClass.getCurrentPlanterClass().getInformation().getWidthCamera();
        int h=ITPlanterClass.getCurrentPlanterClass().getInformation().getHeightCamera();

	    ProcessBuilder pb = new ProcessBuilder(Capturecommand(),"-w",String.valueOf(w),"-h",String.valueOf(h), "-c", String.valueOf(c));
	    try {
	        Process p = pb.start();
	        p.waitFor();
//	        System.out.println("process exited with value : " + ret);
	    } catch (IOException e) {
	        // start()�ŗ�O������
		e.printStackTrace();
	    } catch (InterruptedException e) {
	        // waitFor()�ŗ�O������
		e.printStackTrace();
	    }

	}
	
	/*
	public static int get()
	{
		if(nCam!=0) return nCam;
		return 1;
	}
	*/
	private static final String picasaSetupFile()
	{
		// TO BE CHANGE
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home");
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/Mac/PicasaSetup.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\PicasaSetup.txt");// Windows
		}

	}

	public static  String picasa_fread(){
		//
		File f = new File(picasaSetupFile());
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
			JOptionPane.showMessageDialog(null,"Picasa�ݒ�t�@�C����������܂���BitpManager���C���X�g�[�����Ȃ����Ă��������B");
			e.printStackTrace();
			System.exit(0);		// �A�v���P�[�V�������I������
		}
		return s;
	}


	public static  String Capturecommand()
	{
		// TO BE CHANGE
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			if(IsOS.isOS().contains("Mac")==true)
				return CapturecommandMac;
			// Linux
			else if(IsOS.isOS().contains("Linux")==true)
				return CapturecommandLinux;
		} else {
			// Windows
			return CapturecommandWin;
		}
		return null;
	}
	
	// Constructor
	public CameraCapture()
	{
		//
		state=true;
	}
	
	// ���̃N���X���g���邩�ǂ�����Ԃ�Ԃ�
	public static boolean getState()
	{
		return state;
	}
	
	public static  String capturePhotoThread(String com, boolean sw)
	{
		if(runState==true) return "NG";// ��d�N���h�~
		runState=true;// ��d�N���h�~
    //String mycommand=null;
		String imageFileName=null;
		String[] filename=null;
       String uname = null; // Picasa Login Name
       String passwd = null;// Picasa Passwd
       
       int w=ITPlanterClass.getCurrentPlanterClass().getInformation().getWidthCamera();
       int h=ITPlanterClass.getCurrentPlanterClass().getInformation().getHeightCamera();
       String mycom = Capturecommand()+" -w "+w+" -h "+h+" "+com;
       String[] ccom=com.split(" ");
       String[] mycommand = new String[5+ccom.length];
       mycommand[0]=CameraCapture.Capturecommand();
       mycommand[1]="-w";
       mycommand[2]=String.valueOf(w);
       mycommand[3]="-h";
       mycommand[4]=String.valueOf(h);
       for(int x=0;x<ccom.length;x++){
      	 mycommand[5+x]=ccom[x];
       }

		Runtime runtime = Runtime.getRuntime();
		itp_Logger.logger.info(mycom);
		try {
			Process process = runtime.exec(mycommand);
			if(process==null){
				System.out.println("CameraCapture�����삵�܂���B");
				return "NG";
			}
			try {
				process.waitFor();
				} catch (InterruptedException e1) {
				e1.printStackTrace();
				}	
			InputStream in = process.getInputStream(); 
			byte[] b = new byte[256];
				in.read(b);
			String capture_return = new String(b,"UTF-8"); 
				itp_Logger.logger.info("CameraCapture:"+capture_return);
		  	} catch (IOException e1) {
		  		System.out.println("CameraCapture�����삵�܂���B");
		  		e1.printStackTrace();
		  		return "NG";
			}
		
		 // 	
        if(Files.IscheckSavePicasa()==true){
	        filename=com.split(" ");
	        for(int i=0;i<filename.length;i++){
	        	if(filename[i].equals("-f")){
	        		imageFileName=filename[i+1];// file name
	        		break;
	        	}
	        }
	        
		  	if(sw == true ){
		  	itp_Logger.logger.info("Try Picasa upload.");
		    try {
		        PicasawebService service = new PicasawebService("PicasaUploadImage");     		        
		        //
		        String[] s=picasa_fread().split(System.getProperty("line.separator"));
		        
		      // for upload image to Picasa        
		    //  itp_Logger.logger.info("picasa_fread1:"+s);
		        System.out.println("picasa_fread2:"+s[0]);
		        System.out.println("picasa_fread3:"+s[1]);
		        
		        PicasaLogin=s[0];
		        PicasaPasswd=s[1];/// decode
		        uname = PicasaLogin; // Picasa Login Name
		        passwd = PicasaPasswd;// Picasa Passwd
		        
//		        System.out.println("PicasaLogin:"+PicasaLogin);
//		        System.out.println("PicasaPasswd:"+PicasaPasswd);
		        
		        //������(no�Ŏw�蕶�����}�C�i�X����[���ɖ߂�])
		    	passwd="";
		        for(int i=0; i<PicasaPasswd.length(); i++){
		            passwd = passwd + String.valueOf((char)(PicasaPasswd.charAt(i) - 3));
		        	}
//		        System.out.println("passwd="+passwd);

		        //
		        //Thread.sleep(300);// Wait
		        itp_Logger.logger.info(service+" uname "+uname+" passwd "+passwd+" imageFileName "+imageFileName);
		        //
			    itp_Logger.logger.info("imageFileName="+imageFileName);
			    
		        PicasaUploadImage pwc=new PicasaUploadImage(service, uname, passwd, imageFileName );// �����Ŏ��s����B�����͉����H
		        pwc.wait(1000);//
		        pwc.uploadImage();
		       
		        //System.out.println("Picasa�ւ̃A�b�v���[�h�ɐ������܂����B�@"+imageFileName);
		        itp_Logger.logger.info("Picasa�ւ̃A�b�v���[�h�ɐ������܂����B�@"+imageFileName);
		        if(Files.getCheckSaveImage().contains("false")==true)  
		        									Files.delete(imageFileName);// delete image
		        } catch (Exception ee) {
		        System.out.println("[1] Picasa�ւ̃A�b�v���[�h�Ɏ��s���܂����B�@"+imageFileName);
		        itp_Logger.logger.info("[1] Picasa�ւ̃A�b�v���[�h�Ɏ��s���܂����B�@"+imageFileName);
			      return "NG";
		      }
		  } else {
			  // Open Image File
				// open image file
			       		Desktop desktop = Desktop.getDesktop();
			        	try 
			        	{	
			        	// ����őS�Ẳ摜�t�@�C�����J���̂��H
			        		File f=new File(imageFileName);
			        		desktop.open(f);
			        		//URI uri=new URI(imageFileName);
			        		//desktop.browse(uri);
			        	}
			        	catch(Exception e)
			        	{
			        		System.out.println("Can not open image file:"+imageFileName);
			        	}
			  //
		  	}
        }
        runState=false;// ����\�ɂ���
		return "save image";   
	}
	
	//thread capture
	 public static String capturePhoto(String com, boolean sw) {
	 	String s="";
		 // int st_min = Calendar.MINUTE;     //���Ԃ��擾
		  int st_sec = Calendar.getInstance().get(Calendar.SECOND);
		  
		  if(ITPlanterClass.getCurrentPlanterClass() == null) new ITPlanterClass();
		  int currentPlanterNo=ITPlanterClass.getCurrentPlanterNo();
		  if(ITPlanterClass.getSystemPlanterNumber()>0){//
		  // if Light OFF then turn ON light
		  String sc = sendCom.sendcom((currentPlanterNo+1)+" -e o");
		  String[]	arr=sc.split(System.getProperty("line.separator"),0);
			// SendCom���󂯂��R�}���h�����������ǂ����̌���
			if( arr[0].contains( "Command: o") == false){
				// �������Ȃ��I
				itp_Logger.logger.info("�A�C�e�B�v�����^�[���������܂���B"+arr[0]);
			} else
				itp_Logger.logger.info(sc);

				  String sl = sendCom.sendcom((currentPlanterNo+1)+" -e L1");
				  String[]	arl=sl.split(System.getProperty("line.separator"),0);
					// SendCom���󂯂��R�}���h�����������ǂ����̌���
					if( arl[0].contains("Command: L1") == false){
						// �������Ȃ��I
						itp_Logger.logger.info("�A�C�e�B�v�����^�[���������܂���B"+arl[0]);
						//return "NG";
					}  else 
					itp_Logger.logger.info("Light ON");
		  }//
		  	
		  // take picture
		  //	s=capturePhotoThread(com, sw);  
//System.out.println("CameraCaptureClass: com="+com);
		  	s=capture_thread(com);// �����̃o�O
			itp_Logger.logger.info(s);
			
		  while(true){
			  int nw_sec = Calendar.getInstance().get(Calendar.SECOND);

			  int passage=nw_sec-st_sec;
			  
			  if(s.compareTo("") != 0) break;// �A�C�e�B�v�����^�[���牞����������
			  
			  if(passage<0) passage+=60;
			  if(passage > 100){// time out = 100sec
				  System.out.println("�^�C���A�E�g���܂����B");
				  itp_Logger.logger.info("�^�C���A�E�g���܂����B");
				  s = "400 Error\r\n"; //
				  break;// Time out
			  }
			  try {
				Thread.sleep(100);// Sleep 1 sec	2011.11.23
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
		  
		  if(ITPlanterClass.getSystemPlanterNumber()>0){//
		  // Automode
		  String sp = sendCom.sendcom((currentPlanterNo+1)+" -e p");
		  String[]	arp=sp.split(System.getProperty("line.separator"),0);
			// SendCom���󂯂��R�}���h�����������ǂ����̌���
			if( arp[0].contains( "Command: p") == false){
				// �������Ȃ��I
				itp_Logger.logger.info("�A�C�e�B�v�����^�[���������܂���B"+arp[0]);
			} else
				itp_Logger.logger.info(sp);
		  }//
		 
		  return "save_image";
	 }
	 
	 public static  String capture_thread(String com) {
	     ExecutorService e = Executors.newSingleThreadExecutor();
	     
//	     CameraCapture cap=new CameraCapture();		// need check  error <<
	     Future<?> future = e.submit(new Capture(com)); // ERROR  <<
	     capture_return="save_image";

		  boolean isDone=future.isDone();

	  itp_Logger.logger.info("CaptureThread : RuunableTask is Called and done."); //
	  System.out.println("CaptureThread Future#isDone(): " + isDone); // FALSE <<
	     try {
	    	 try {
				future.get(150,TimeUnit.SECONDS);// 30sec�ł͕s���Ȃ̂��H  100sec
				} catch (TimeoutException e1) {
					e1.printStackTrace();
					itp_Logger.logger.info("future.get time out");
					future.cancel(true);// cancel sendcom processing
					capture_return="error";
				     e.shutdown();
				     return capture_return;
				}
	    	 isDone=future.isDone();
	    	 //String status=Capture.getStatue();
	         System.out.println("CaptureThread Future#isDone(): "+ isDone + " "+capture_return);
	     } catch (ExecutionException ex) {
	         ex.printStackTrace();
	     } catch (InterruptedException ex) {
	         ex.printStackTrace();
	     }
	     
	     e.shutdown();
	     return capture_return;
	 }
	 ///// END of Capture
	 

	//
	 public static void saveCameraTimeTable(String[] arr) 
	 {
	 	// �J�����B�e�̃^�C���e�[�u��
	 	//TPT	
	 	//Amount: 3	
	 	//Time: 07:00:00	
	 	//Time: 12:00:00	
	 	//Time: 17:00:00	
	 	//(��s)	
		 
		 	for(int i=0;i<arr.length;i++){
		 		System.out.println("saveCameraTimeTable "+arr[i]);
	 		}	 	
	 	//
	 	String parm="";
	 	// TPT���̕ۑ�
	 	for(int i=0;i<arr.length;i++)
	 		parm += (arr[i]+System.getProperty("line.separator"));	
	 	
	 	sampleReplace sr=new sampleReplace();
//	 	String filename=itplanter.getPlanterName(itplanter.currentPlanterNo-1);
	 	
	 	if(ITPlanterClass.getState()==false){
	 		new ITPlanterClass();
			// �J�����g�v�����^�[���ݒ肳��Ă��Ȃ���ΐݒ肷��
			ITPlanterClass.setCurrentPlanterClass(ITPlanterClass.getPlanterList().get(0));
	 	}
	 	
	 	Information pi=ITPlanterClass.getCurrentPlanterClass().getInformation();
	 	String filename=pi.getPlanterName();
	 	
	 	itp_Logger.logger.info("camera_time_table 1 "+filename);
	 	filename=sr.planterSchedulefile(filename);
	 	itp_Logger.logger.info("camera_time_table 2 "+filename);
	 	
	 	Files.writeFile(filename, parm);// �ۑ�����f�[�^
	 	
	 	/*
	 	File f = new File(filename);
	 	
	 	byte[] b = parm.getBytes();// �ۑ�����f�[�^
	 	try 
	 	{	
	 		FileOutputStream fo= new FileOutputStream(f);
	 		fo.write(b);	
	 		fo.close();
	 	}
	 	catch(Exception e)
	 	{
	 		e.printStackTrace();
	 	}
	 	*/
	 	
//	 	System.out.println("cameta_time_table "+filename+" has saved");
	 	itp_Logger.logger.info("cameta_time_table "+filename+" has saved");
	 	
	 	String[] amount=arr[1].split(" ");
	 	// �B�e�^�C���e�[�u���̐�
	 	timeTableNo=Integer.parseInt(amount[1].replaceAll("[^0-9]",""));
	 	// �B�e�^�C���e�[�u��
	 	timeTable=new String[timeTableNo];
	 	// 
	 	int state=0;
	 	
	 	// �J�����̗L���𒲂ׂ�B�@�Ȃ���΃G���[��Ԃ��B
	 	// 405 Error No Camera
	 	state = setTimeTable(arr, state);
/*	 	
	 	if(ListnerConnecter.itpCom1!=null){
		 	if(state==0) ListnerConnecter.itpCom1.say("300 OK\r\n\r\n"); 
		 	else 
		 		ListnerConnecter.itpCom1.say("400 Error\r\n\r\n");
	 	}
  */
	 	
	 }

	private static int setTimeTable(String[] arr, int state) {
		
		for(int i=0;i<timeTableNo;i++){
	 			String[] time=null;
	 			time=arr[2+i].split(":");
/*
	 			if(time == null){
	 			 	if(ListnerConnecter.itpCom1!=null)
	 			 		ListnerConnecter.itpCom1.say("407 Error Can not Set Time Table\r\n\r\n");
	 				state=1;
	 				break;
	 			}	
 */
	 			if( time[0].equals("Time")==true){
	 				time[1].replace("00", "0");
	 				time[1].replace("01", "1");
	 				time[1].replace("02", "2");
	 				time[1].replace("03", "3");
	 				time[1].replace("04", "4");
	 				time[1].replace("05", "5");
	 				time[1].replace("06", "6");
	 				time[1].replace("07", "7");
	 				time[1].replace("08", "8");
	 				time[1].replace("09", "9");
	 		  		time[1]=time[1].replace(" ", "");
	 		  		//
	 				time[2].replace("00", "0");
	 				time[2].replace("01", "1");
	 				time[2].replace("02", "2");
	 				time[2].replace("03", "3");
	 				time[2].replace("04", "4");
	 				time[2].replace("05", "5");
	 				time[2].replace("06", "6");
	 				time[2].replace("07", "7");
	 				time[2].replace("08", "8");
	 				time[2].replace("09", "9");
	 		  		time[2]=time[2].replace(" ", "");
	 		  		//
	 				time[3].replace("00", "0");
	 				time[3].replace("01", "1");
	 				time[3].replace("02", "2");
	 				time[3].replace("03", "3");
	 				time[3].replace("04", "4");
	 				time[3].replace("05", "5");
	 				time[3].replace("06", "6");
	 				time[3].replace("07", "7");
	 				time[3].replace("08", "8");
	 				time[3].replace("09", "9");
	 		  		time[3]=time[3].replace(" ", "");
	 		  		//
	 				timeTable[i]=time[1]+":"+time[2]+":"+time[3];
	 				itp_Logger.logger.info("timeTable: "+timeTable[i]);
	 				}
	 	}
		return state;
	}


	 public static void TPT_processing(String[] arr)
	 {
	
	       //lengthOfTask=tt
	 	Calendar retTime = Calendar.getInstance(); //�C���X�^���X��HOUR_OF_DAY);//�����擾
	 	// current time  chour:cmin
	 	int cyear = retTime.get(Calendar.YEAR);
	 	int cmonth= retTime.get(Calendar.MONTH);
	    int cday =  retTime.get(Calendar.DATE);
	    int chour = retTime.get(Calendar.HOUR_OF_DAY);
	    int cmin =  retTime.get(Calendar.MINUTE);    
	     
	    
//	    int cPlanterNo=ITPlanterClass.getCurrentPlanterNo();
	    
	    for(int pn=0;pn<ITPlanterClass.getPlanterList().size();pn++){// 130624
	    Information pi=ITPlanterClass.getPlanterList().get(pn).getInformation();// 130624
	 	String cPlanterName=pi.getPlanterName();

	     itp_Logger.logger.info("TPT_processing:Take Picture Time Table making 1:");
	 	
	 	//
	 	//	clear timer of list array
	 	//
	 	// ���݂̃v�����^�[�̎B�e�X�P�W���[���������N���A����B
	 	//
	 	if(cameraSchedule.isEmpty()==false){
	 		// cancel timer
	 		int[] clearNo=new int[cameraSchedule.size()];
	 		int clearSize=0;

	 		
	 		for(int i=0;i<cameraSchedule.size();i++){
	 		AutoCapture tt = (AutoCapture) cameraSchedule.get(i);
	 		
System.out.println("Pname:"+tt.Pname+" Current Pname:"+cPlanterName);

	 		//
	 		if( tt.Pname.contains(cPlanterName) ){
	 			clearNo[clearSize++]=i;
	 			break;
	 			}
	 		}

	 		for(int i=0;i<clearSize;i++){
	 		AutoCapture autoCapture = (AutoCapture) cameraSchedule.get(i);
	 		// cancel it
	 		cameraSchedule.get(i).cancel();//
	 		autoCapture.cancel();// cancel
	 		// remove it
	 		cameraSchedule.remove(i);
System.out.println("cameraSchedule["+cPlanterName+"] is cleard");
	 		}// next i
	 	}// end if
	 	
	 	// add timer to list array
	 	//
	 	  String pname="";
	 	  // �S�Ẵ^�C���e�[�u����o�^����

	 	  for(int j=0;j<timeTableNo;j++){
		 	  // �o�^����Ă���J�����S�Ă�Ώۂɂ���B
		 	  for(int iCamNo=0;iCamNo<ITPlanterClass.getSystemCameraSize();iCamNo++){// 130524
		 		 String camName=pi.getCameraNameList().get(iCamNo);// 130624
		 		 
		 	 	if(camName.contains("-removed-")==true) continue;// 130628 -removed- �v�����^�[�͑ΏۊO
		 	 	
	 		  String[] timetable=timeTable[j].split(":");
	 		  
System.out.println("TPT_processing GetSetting 2:   "+timeTable[j]+" iCamNo="+iCamNo);
	 		  
	 			int hour = Integer.parseInt(timetable[0]);
	 			int min  = Integer.parseInt(timetable[1]);
	 			
	 			Calendar cal=Calendar.getInstance();
	 			
//	 			itp_Logger.logger.info("getTime="+cal.getTime());
	 System.out.println("TPT_processing cal.set 3:"+" cameraNo "+iCamNo+" "+" hour "+hour+" min "+min);

	 // ���łɓo�^����Ă���B�e�X�P�W���[���𒲂ׂāA�������̎B�e�����Ȃ��悤�ɂ���B
	 			if(cameraSchedule.isEmpty()==false){
	 				// cancel timer
	 				for(int k=0;k<cameraSchedule.size();k++){
	 				AutoCapture autoCapture = (AutoCapture) cameraSchedule.get(k);
	 				if((autoCapture.hour*60+autoCapture.min)==(hour*60+min)){
	 					// �������Ȃ̂�2�����炷
	 					// TODO
	 					min+=2;//
	 					if(min>60){ 
	 						hour++;
	 						if(hour>24) hour-=24;
	 						min -= 60;
	 					}
	 					//cal.add(Calendar.MINUTE, 1);// 1�����炷�̂͂�����ł��邱�ƁB	
	 					// �ēx�A�ŏ�����`�F�b�N�����Ȃ���
	 					k=0;
	 					}
	 				}// next k
	 			}
	 			/////
	 			
	 			cal.set(cyear, cmonth, cday, hour, min, 0);// 0 sec at every time
 				cal.add(Calendar.MINUTE, iCamNo);// After 1 minutes		

 				//  ���łɎ��Ԃ��߂��Ă�����A��������J�n����B
	 			if(hour <= chour && min < cmin){
	 				// ���̎��Ԃ͂����߂��Ă���B�����ɂ���B
	 				cal.add(Calendar.DAY_OF_MONTH, 1);// ����		
	 			}
	 			
//	 			pname=cPlanterName+"_Cam"+iCamNo+"_DATE_.jpg";
//	 			pname=cPlanterName+"_Cam-"+iCamNo+"_DATE_.bmp";
	 			pname=cPlanterName+"_"+camName+"_DATE_.bmp";// 130624
	 			itp_Logger.logger.info("TPT_processing 4: setTime="+cal.getTime()+" "+pname);
	 					//itp_Logger.logger.info("TPT_processing 5: FileName="+pname);	
	 					// Save to Timer List
	 					itp_Logger.logger.info("TPT_processing 5: TimerTask new start "+pname);						
	 					TimerTask timerTask=new AutoCapture(pname, iCamNo);
	 					itp_Logger.logger.info("TPT_processing 6: TimerTask new end");	

	 					//
	 					cameraSchedule.add(timerTask);
//	 					itp_Logger.logger.info("TPT_processing: cameraSchedule.add");	
	 					
	 					// save schedule to timer
	 					timerSchedule.timerScheduleSet(timerTask, cal);
	 							  
	 					itp_Logger.logger.info("TPT_processing 7: TimerTask :"+hour+" "+min+" save schedule at : "+cal.getTime());	
	 					//itp_Logger.logger.info("TPT_processing 9: save schedule at : "+cal.getTime());	
	 			  
	 	  		}// next iCam
	 	  }// next j
	    }// 130524
	 }// End of TPP_Processing
	 //
}
