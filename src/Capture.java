
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import com.google.gdata.client.photos.PicasawebService;

	// 
	//外部アプリのCapture をThreadで動かす。
	public class Capture implements Runnable {
		private static boolean runState=false;
	 	private static String com="";
	 	private static String status="";

		public static void main(String[] args) {
			String f=Files.getPhotoHolder().substring(0,Files.getPhotoHolder().length()).replace("PhotoHolder ", "");
			System.out.println("|"+f+"XXXXX.jpg"+"|");	
			String r=removeHolderName(f+"XXXXX.jpg"); 
			System.out.println("|"+r+"|");
		}
		
	 	public static String getStatue()
	 	{
	 		return status;
	 	}
	 	
	 	// Constructor
	 	public Capture(String c){
	 		com=c;
	 	}
			@Override
			public void run() {
				// com : -f directory/filename
				// check file and directory
				String df0=com.replace("\n", "");
				String[] pcom=df0.split(" ");
				// -c n -f dir/file
				if(pcom.length>4){
				String[] dfs=pcom[4].split("/");
				int dl=dfs.length-1;
				String f0=dfs[dl];
				String fd1=pcom[4].replace(f0,"");
				
				File fd=new File(fd1);
				
			    if (fd.exists()){
//			    	System.out.println("1"+fd.isDirectory());
//			    	System.out.println("2"+fd.canWrite());
				      if ((fd.isDirectory() && fd.canWrite())==false){
				    	  // Message
				    	  String message=fd1+" can not write";
				    	  JOptionPane.showMessageDialog(null,message);				    	  
				        return;
				      }
				    }
			    /*
				File fi=new File(pcom[3]);
				if(Files.checkBeforeWritefile(fi)==false){
			    	  // Message
			    	  String message=pcom[3]+" can not write";
			    	  JOptionPane.showMessageDialog(null,message);
						return;
				}
				*/
				}
				//
				// 抹消されたカメラは撮影しない。
				//
				if(runState==true) return; // 使用中なので動作しない
				runState=true;
				int pointCamera=0;
		        String[] camNo=com.split(" ");
		        for(int i=0;i<camNo.length;i++){
		        	if(camNo[i].equals("-c")){
		        		int camno=Integer.parseInt(camNo[i+1].split(",")[0].replaceAll("[^0-9]",""));//
//		        		pointCamera=Integer.valueOf(camNo[i+1]);// cemara No
		        		pointCamera=Integer.valueOf(camno);// cemara No
		        		break;
		        	}
		        }
				String camName=ITPlanterClass.getCurrentPlanterClass().getInformation().getCameraName(pointCamera);
				if(camName.contains("-remove-")==true) return;// 抹消されたカメラの印
				//
				//
				//
				
				sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e o");// remote mode
				sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e L1");// light ON
	
				// カメラがサチュレーションするので、光量を下げる
			boolean saturationSW=false; // falseなら何もしない
				String underPower="50";// サチュレーションしない光量
				//
				String lp="90";
				if(saturationSW==true){
				String power=sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e H");// light ON
//System.out.println(power);
				// COMMAND: H
				// PWM 90
				String[] tmp=power.split(System.getProperty("line.separator"));
				if(tmp.length>1){
				lp=tmp[1].split(" ")[1];// 現在の光量を保存
				}
				sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e H"+underPower);// Duty 50%
				}
				//
				//
				
		        int w=ITPlanterClass.getCurrentPlanterClass().getInformation().getWidthCamera();
		        int h=ITPlanterClass.getCurrentPlanterClass().getInformation().getHeightCamera();
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
//		         CameraCapture.Capturecommand()+" -w "+w+" -h "+h+" "+com;
		         String mycom = CameraCapture.Capturecommand()+" -w "+w+" -h "+h+" "+com;

		         String imageFileName=null;
			     String[] filename=null;

				Runtime runtime = Runtime.getRuntime();
				
				itp_Logger.logger.info("Capture:"+mycom);
				
				try {
					Process process = runtime.exec(mycommand);
					if(process==null){
						itp_Logger.logger.info("Captureが動作しません。");
						return ;
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
					itp_Logger.logger.info("Capture:"+capture_return);
					if(capture_return.contains("failed")==true){
						itp_Logger.logger.info("Capture: Captureが動作しません。"+capture_return);	
						status=capture_return;
						return ;
						}
				  	} catch (IOException e1) {
				  		System.out.println("Captureが動作しません。");
				  		e1.printStackTrace();
				  		return ;
					}
				

				//System.out.println("com="+com);
			        filename=com.split(" ");
			        for(int i=0;i<filename.length;i++){
			        	if(filename[i].equals("-f")){
			        		imageFileName=filename[i+1];// file name
			        		break;
			        	}
			        }
			        
			        itp_Logger.logger.info("imageFileName :"+imageFileName);
			        
			        //
					//
				  	//  capture_thread("capture 0 -f "+imageFileName);
				  	//
					if(saturationSW==true){
			        int duty=Integer.parseInt(lp.replaceAll("[^0-9]",""));// 元のDutyの値を得る
					sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e H"+duty);// Dutyをもとに戻す
					}
					
					sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e p");// auto mode
			        
			  if(Files.IscheckSavePicasa()==true){    
 		     	 String uname = null; // Picasa Login Name
			     String passwd = null;// Picasa Passwd
				  	itp_Logger.logger.info("Try Picasa upload.");
				    try {

				        String[] s=CameraCapture.picasa_fread().split(System.getProperty("line.separator"));
				        
				        CameraCapture.PicasaLogin=s[0];
				        CameraCapture.PicasaPasswd=s[1];/// decode
				        uname = CameraCapture.PicasaLogin; // Picasa Login Name
				        passwd = CameraCapture.PicasaPasswd;// Picasa Passwd
				        
		//		        System.out.println("PicasaLogin:"+PicasaLogin);
		//		        System.out.println("PicasaPasswd:"+PicasaPasswd);
				        
				        //復号化(noで指定文字数マイナスする[元に戻す])
				    	passwd="";
				        for(int i=0; i<CameraCapture.PicasaPasswd.length(); i++){
				            passwd = passwd + String.valueOf((char)(CameraCapture.PicasaPasswd.charAt(i) - 3));
				        	}
				        //System.out.println("passwd="+passwd);
				        
				        PicasawebService service = new PicasawebService("PicasaUploadImage");
			//	        System.out.println(service+"22 uname:"+uname+" passwd:"+passwd+" imageFileName:"+imageFileName);
				        // imageFileName  /***/***/xxxxx.bmp  -> xxxxx.bmp
				        // imageFileName=removeHolderName(imageFileName);
				        PicasaUploadImage pwc=new PicasaUploadImage(service, uname, passwd, imageFileName );// ここでエラーになる。
				        pwc.uploadImage();
				        
				        System.out.println("[CaptureClass run]Picasaへのアップロードに成功しました。　"+uname+" "+imageFileName);
				        itp_Logger.logger.info("[CaptueClass run]Picasaへのアップロードに成功しました。　"+uname+" "+imageFileName);
				        // upload image　のメッセージを確認したい。
				      } catch (Exception ee) {
				        System.out.println("[CaptureClass run]Picasaへのアップロードに失敗しました。　"+uname+" "+imageFileName);
				        itp_Logger.logger.info("[CaptureClass run]Picasaへのアップロードに失敗しました。　"+uname+" "+imageFileName);
				      }	      
			  	}
				runState=false;//　動作完了
			}

			private static String removeHolderName(String imageFileName) {
//System.out.println("Capture: imageFileName1:"+imageFileName);
		String f=Files.getPhotoHolder().substring(0,Files.getPhotoHolder().length()).replace("PhotoHolder ", "");	
//System.out.println("Capture: imageFileName2:|"+f+"|");
		String filename=imageFileName.replace(f, "");
//System.out.println("Capture: imageFileName3:"+filename);
				return filename;
			}
	 }// Class Capture