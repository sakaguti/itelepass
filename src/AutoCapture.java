
import java.io.File;
import java.util.Calendar;
import java.util.TimerTask;

import javax.swing.JOptionPane;


public class AutoCapture extends TimerTask {
		public Integer camNo=0;
		public String Pname=null;
		public int month=0;// 	
		public int day=0;// 	
		public int hour=0;// 
		public int min=0; //
		public Calendar calendar=null;
		private boolean sw=true;
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			if(CameraCapture.getState()==false){
				new CameraCapture();
			}	
		TimerTask timerTask=new AutoCapture("planter0_DATE_.bmp",0);
		timerTask.run();
		}
		
	// Constructor	
		public  AutoCapture(String Pname0, Integer camNo0)
		{
			this.camNo=camNo0;// get from camNo setting.
			this.Pname=Pname0;		
//			this.sw=sw;
		}
		
		public void takePictureProcessing() {
			
			// pname= Planter0_DATE_hour_min.jpg
			//
			// replace to today's date
			// qname: planter1_DATE_12(hour)_36(min).jpg/tif[MacOSX]
		    calendar = Calendar.getInstance();
			
			month = calendar.get(Calendar.MONTH)+1; // １月は０から始まる
			day = calendar.get(Calendar.DATE);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			min = calendar.get(Calendar.MINUTE);
			// 
			String qname=Pname.replace("_DATE_", "_"+month+"_"+day+"_"+hour+"_"+min+"_");
		
			// Only Mac
			// OpenCVでjpgが使えない場合、tifを使って変換する。
			//	if(IsMacorWin.isMacOrWin()==true) qname+=".tif";
			
			// 保存先ホルダーを付加する。
			String holder=Files.getPhotoHolder();
			if(IsMacorWin.isMacOrWin()==true){
				if(holder.substring(holder.length()-1).equals("/")==false){
				holder = holder+"/";
				}
			} else {
				if(holder.substring(holder.length()-1).equals("\\")==false){
				holder = holder+"\\";
				}
			}
			qname = holder+qname;
				
			
				itp_Logger.logger.info("takePictureProcessing: FileName="+qname);	
				//	Time Table of Capture
				String args = "-c "+camNo+" -f "+qname;
				///
				itp_Logger.logger.info("takePictureProcessing: Args="+args);

				
				
				if(ITPlanterClass.getState()==false) new ITPlanterClass();
				String f=Files.getPhotoHolder();
//System.out.println(f);
				f=f.replace("PhotoHolder ", "");				
//
//
				File fd=new File(f);
			    if (fd.exists()){
//			    	System.out.println("1"+fd.isDirectory());
//			    	System.out.println("2"+fd.canWrite());
				      if ((fd.isDirectory() && fd.canWrite())==false){
				    	// Message
				    	  // Message
				    	  String message=f+" can not write";
				    	  JOptionPane.showMessageDialog(null,message);
				        return;
				      }
				    }			    
				//
				f=f+"test.bmp";
				/*
				File fi=new File(f);
				if(Files.checkBeforeWritefile(fi)==false){
			    	  // Message
			    	  String message=f+" can not write";
			    	  JOptionPane.showMessageDialog(null,message);
						return;
				}
				*/
				CameraCapture.capture_thread("-f "+f);
				
				
				
				String ret="save_image";
				// Take a picture
				// String ret=CameraCapture.capturePhoto(args,false);// Light ON/PFF
				try {
					Thread.sleep(1000);// wait 1sec
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					
				if(ret.contains("save_image")==true){
					itp_Logger.logger.info("takePictureProcessing: OK :"+qname);
					// upload to Picasa
					//
					//	ここで画像ファイルを削除する
					//
					//Files.delete(qname);
					//
					//
				} else {
					itp_Logger.logger.info("takePictureProcessing: NG :"+qname);
				}
				//
				//	ここで画像ファイルを削除する
				/*
				System.out.println("takePictureProcessing: file delete:"+qname);
				itp_Logger.logger.info("takePictureProcessing: file delete:"+qname);
				Files.delete(qname);
				*/
			}
		
		
		public void autoCaptureProcessing() {
			
			// pname= Planter0_DATE_hour_min.jpg
			//
			// replace to today's date
			// qname: planter1_DATE_12(hour)_36(min).jpg/tif[MacOSX]
		    calendar = Calendar.getInstance();
			
			month = calendar.get(Calendar.MONTH)+1; // １月は０から始まる
			day = calendar.get(Calendar.DATE);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			min = calendar.get(Calendar.MINUTE);
			// 
			String qname=Pname.replace("_DATE_", "_"+month+"_"+day+"_"+hour+"_"+min+"_");
			String photoholder=Files.getPhotoHolder().replace("PhotoHolder ","");
			//
			File fd=new File(photoholder);
		    if (fd.exists()){
			      if ((fd.isDirectory() && fd.canWrite())==false){
			    	  // Message
			    	  String message=photoholder+" can not write";
			    	  JOptionPane.showMessageDialog(null,message);
			        return;
			      }
			    }			
			qname =photoholder+qname;
			/*
			File ff=new File(qname);
		    if (ff.exists()){
			      if ((fd.isFile() && fd.canWrite())==false){
			    	  // Message
			    	  String message=qname+" can not write";
			    	  JOptionPane.showMessageDialog(null,message);
			        return;
			      }
			    }
			*/
			
				itp_Logger.logger.info("Auto Capture: FileName="+qname);	
				//	Time Table of Capture
				String args = "-c "+camNo+" -f "+qname;
				///
				itp_Logger.logger.info("autoCapture_processing: Args="+args);

				if(ITPlanterClass.getState()==false) new ITPlanterClass();
				//
				/*
				CameraCapture cc = new CameraCapture();
				String f=Files.getPhotoHolder();
				System.out.println(f);
				f=f.replace("PhotoHolder ", "");
				f=f+qname;
				System.out.print("f="+f);
				String ret=cc.capture_thread("-f "+f);
				*/
				
				// Take a picture
				String ret=CameraCapture.capturePhoto(args, sw);// Light ON/PFF
				
				try {
					Thread.sleep(10000);// wait 10sec
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				//	String ret="save_image";	
				if(ret.contains("save_image")==true){
					itp_Logger.logger.info("autoCapture_processing: OK :"+qname);
					// upload to Picasa
					//
					//	ここで画像ファイルを削除する
					//
					//
				} else {
					itp_Logger.logger.info("autoCapture_processing: NG :"+qname);
				}
				
				//
			}

		@Override
		public void run() {
			itp_Logger.logger.info("autoCapture_processing run() 1:"+Pname+" "+Calendar.getInstance().getTime());
			autoCaptureProcessing();	
//			takePictureProcessing();
			}
	//

}