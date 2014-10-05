import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class FileUtil {
	public static void main(String[] args) {
		setupFiles();
	}

	public static void setupFiles(){
		
		if(IsMacorWin.isMacOrWin()==true){
			// Mac or Linux
		String home=System.getProperty("user.home");
		File destholder=new File(home+"/itpManager"+Version.getFolderName());	
		if (!destholder.exists()){ 
			File src=null;
			if(IsOS.isOS().contains("Mac")==true) src=new File("/Applications/itpManager"+Version.getFolderName()+"/data");
			else if(IsOS.isOS().contains("Linux")==true) src=new File("/usr/local/itpManager"+Version.getFolderName()+"/data");
			File dest=new File(home+"/itpManager"+Version.getFolderName()+"/data");
			destholder.mkdir();
		// copy data holder
		try {
			copyFile(src,dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// copy DB holder
		File srcDB=null;
		if(IsOS.isOS().contains("Mac")==true) srcDB=new File("/Applications/itpManager"+Version.getFolderName()+"/DB");
		else if(IsOS.isOS().contains("Linux")==true) srcDB=new File("/usr/local/itpManager"+Version.getFolderName()+"/DB");

		File destDB=new File(home+"/itpManager"+Version.getFolderName()+"/DB");
		destDB.mkdir();
		// copy data holder
		try {
			copyFile(srcDB,destDB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				}
			}
		} else {
			// Win
			String home=System.getenv("APPDATA");
			File destholder=new File(home+"\\itpManager"+Version.getFolderName());	
			if (!destholder.exists()){ 
				String appdir=System.getenv("ProgramFiles");				
				File src=new File(appdir+"\\data");
				File dest=new File(home+"\\itpManager"+Version.getFolderName()+"\\data");
				destholder.mkdir();
			// copy data holder
			try {
				copyFile(src,dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// copy DB holder
			File srcDB=new File(appdir+"\\DB");
			File destDB=new File(home+"\\itpManager"+Version.getFolderName()+"\\DB");
			destDB.mkdir();
			// copy data holder
			try {
				copyFile(srcDB,destDB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
					}
				} 
		}
	}
	/**
	 * @author ikki
	 *
	 */
		/**
		 * @param src source
		 * @param destination
		 * @throws IOException
		 */
		public static void copyFile(File src,File dest) 
			throws IOException {
			
			if (src.isDirectory()) 
				copyDirectory(src,dest);
			else
				copyTransfer(src.getPath(),dest.getPath());
		}
		/**
		 * @param srcPath 
		 * @param destPath
		 * @throws IOException
		 */
		public static void copyTransfer(final String srcPath,final String destPath) 
	    	throws IOException {
	    
		    FileChannel srcChannel = new
		        FileInputStream(srcPath).getChannel();
		    FileChannel destChannel = new
		        FileOutputStream(destPath).getChannel();
		    try {
		        srcChannel.transferTo(0, srcChannel.size(), destChannel);
		    } finally {
		        srcChannel.close();
		        destChannel.close();
		    }
		}
		/**
		 * @param srcPath 
		 * @param destPath
		 * @throws IOException
		 */
		public static void copyDirectory(File src ,File dest) 
			throws IOException {
			
			if (src.isFile())
				copyFile(src,dest);
			if (!dest.exists())
				dest.mkdir();
			for (File ff : src.listFiles()) {
				
				String name = ff.getName();
				if (name.equals(".") || name.equals("..")) continue;
				File d2 = new File(dest.getPath()+"/"+ff.getName());
				
				if (ff.isDirectory())
					copyDirectory(ff,d2);
				else
					copyFile(ff,d2);
			}
		}
		
		/**
		 * @param f 
		 */
		public static void delete(File f) {
			if (f.isDirectory())
				for (File ff : f.listFiles())
					delete(ff);
			f.delete();
		}

}
