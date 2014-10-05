
import java.io.File;
import java.text.DateFormat;
import java.util.Date;


public class timeStamp {
    public timeStamp()
    {
    	
    }
    
    private static String setupFile()
	{
    	String SETUPFILE=null;
		String sCurrentDir = new File(".").getAbsoluteFile().getParent();
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			if(IsOS.isOS().contains("Mac")==true)
				SETUPFILE=sCurrentDir+"/data/Mac/setup.txt";// MACOSX  
			else if(IsOS.isOS().contains("Linux")==true)
				SETUPFILE=sCurrentDir+"/data/linux/setup.txt";// MACOSX  
		} else {
			// Windows
			SETUPFILE=sCurrentDir+"\\data\\Win\\setup.txt";// WIndows
		}
		return SETUPFILE;
	}
	
    private static String setupPlanterFile()
	{
    	String SETUPFILE=null;
		String sCurrentDir = new File(".").getAbsoluteFile().getParent();
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			if(IsOS.isOS().contains("Mac")==true)
				SETUPFILE=sCurrentDir+"/data/Mac/planters.txt";// MACOSX  
			else if(IsOS.isOS().contains("Linux")==true)
				SETUPFILE=sCurrentDir+"/data/linux/planters.txt";// MACOSX  
		} else {
			// Windows
			SETUPFILE=sCurrentDir+"\\data\\Win\\planters.txt";// WIndows
		}
		return SETUPFILE;
	}
    
	// setup.txtが更新されたか調べる。
	 public static String getSettingTimeStamp()
	 {
		  File file = new File(setupFile());
		 // System.out.println(setupFile());
		  long lastModifytime = file.lastModified();
		  Date date = new Date(lastModifytime);
		  DateFormat df = DateFormat.getDateInstance();
		  df = DateFormat.getDateTimeInstance();
		  return df.format(date);
	 }
   
		// setup.txtが更新されたか調べる。
	 public static String getPlanterTimeStamp()
	 {
		  File file = new File(setupPlanterFile());
		 // System.out.println(setupFile());
		  long lastModifytime = file.lastModified();
		  Date date = new Date(lastModifytime);
		  DateFormat df = DateFormat.getDateInstance();
		  df = DateFormat.getDateTimeInstance();
		  return df.format(date);
	 }

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			
			String start=getSettingTimeStamp();
			 System.out.println(start);
			while(true){
				String now=null;
				now=getSettingTimeStamp();
				System.out.println(now+" "+start);
				if(now.equals(start)==false) break; 
			}
		}
}
