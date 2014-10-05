//
// load setting data from local file
//	itplants.ltd.	2011
//	Yosiyuki SAKAGUCHI
//
//
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;



public class Files {
	public static void main(String[] args) {
//		test();
		//initialCopyFiles();
		//setupData();
		System.out.println("start files");
		System.out.println(Files.getCamNo() );
		System.out.println(Files.getPhotoHolder());
		System.out.println(Files.getCheckSaveImage());
		System.out.println(Files.getCheckSaveHolder());
		
		System.out.println(Files.getCheckSavePicasa());
//		System.out.println(System.getenv("ProgramFiles"));
//		System.out.println("Normal End");
	}
	
	public static void test()
	{
	    Map mp = System.getenv();
	    Set set =mp.entrySet();
	    Iterator it = set.iterator();

	    while(it.hasNext()) {
	        Map.Entry me = (Map.Entry) it.next();
	        System.out.println(me.getKey() + "/" + me.getValue());
	    }
	}
	
		/**
		 * @param src 送信元
		 * @param dest　宛先
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
		 * @param srcPath 送信元
		 * @param destPath　宛先
		 * @throws IOException
		 */
		public static void copyTransfer( String srcPath, String destPath) 
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
		 * @param srcPath 送信元
		 * @param destPath　宛先
		 * @throws IOException
		 */
		public static void copyDirectory(File string ,File sCurrentDir) 
			throws IOException {
			
			if (string.isFile())
				copyFile(string,sCurrentDir);
			if (!sCurrentDir.exists())
				sCurrentDir.mkdir();
			for (File ff : string.listFiles()) {
				
				String name = ff.getName();
				if (name.equals(".") || name.equals("..")) continue;
				File d2 = new File(sCurrentDir.getPath()+"/"+ff.getName());
				
				if (ff.isDirectory())
					copyDirectory(ff,d2);
				else
					copyFile(ff,d2);
			}
		}
		
		/**
		 * @param f 削除するfile
		 */
		public static void delete(File f) {
			if (f.isDirectory())
				for (File ff : f.listFiles())
					delete(ff);
			f.delete();
		}
	
	public static File[] ls(String path) {
	    //String path = "C:\\filelist";
	    File dir = new File(path);
	    File[] files = dir.listFiles();	    
		return files;
	}
	
	// Macの場合、必須ファイルをコピーする　
	public static void initialCopyFiles()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
					String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
					String sAppDir="/Applications/itpManager"+Version.getFolderName();
					File fileRT=new File(sCurrentDir);
					if(fileRT.isDirectory()==false){
						if(fileRT.mkdir()==false){
							JOptionPane.showMessageDialog(null, "ディレクトリーが作成できません。"+sCurrentDir);
							System.exit(0);	
						}
					}
					File fileDT=new File(sCurrentDir+"/data/");
					if(fileDT.isDirectory()==false){
						if(fileDT.mkdir()==false){
							JOptionPane.showMessageDialog(null, "ディレクトリーが作成できません。"+sCurrentDir+"/data/");
							System.exit(0);	
						}
						File fileDM=new File(sAppDir+"/data/Mac");
						File fileCr=new File(sCurrentDir+"/data");
						try {
							copyDirectory(fileDM, fileCr);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    File Filedest=new File(sAppDir+"/DB");
					//	fileCr.renameTo(Filedest);
						File fileDB=new File(sCurrentDir+"/DB");
						try {
							copyDirectory(Filedest, fileDB);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
/*						
						Runtime r = Runtime.getRuntime();
						try {
							r.exec("/bin/cp -r /Applications/itpManager/data/Mac "+sCurrentDir);
							r.exec("/bin/mv "+sCurrentDir+"/data/Mac "+sCurrentDir+"/data");
							r.exec("/bin/rm -rf "+sCurrentDir+"/data/");
							r.exec("/bin/mv "+sCurrentDir+"/Mac "+sCurrentDir+"/data/");	
							r.exec("/bin/sync");	
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				File fileDB=new File(sCurrentDir+"/DB/");
				if(fileDB.isDirectory()==false){
					if(fileDB.mkdir()==false){
						JOptionPane.showMessageDialog(null, "ディレクトリーが作成できません。"+sCurrentDir+"/DB/");
						System.exit(0);	
					}
					try {
						r.exec("/bin/cp -r /Applications/itpManager/DB "+sCurrentDir);
						r.exec("/bin/sync");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
*/
				}
			} 
		if(IsMacorWin.isMacOrWin()==false ){
			// Win
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+"\\"+Version.getFolderName();
			File fileRT=new File(sCurrentDir);
			if(fileRT.isDirectory()==false){
				if(fileRT.mkdir()==false){
					JOptionPane.showMessageDialog(null, "ディレクトリーが作成できません。"+sCurrentDir);
					System.exit(0);	
				}
			}
			File fileDT=new File(sCurrentDir+"\\data");
			String appdir=System.getenv("ProgramFiles");
			if(fileDT.isDirectory()==false){
				if(fileDT.mkdir()==false){
					JOptionPane.showMessageDialog(null, "ディレクトリーが作成できません。"+sCurrentDir+"\\data");
					System.exit(0);	
				}
				// copyDirectory
				Runtime r = Runtime.getRuntime();
				try {
					String com="C:\\WINDOWS\\system32\\xcopy /E "+"\""+appdir+"\\itpManager"+Version.getFolderName()+"\\data"+"\""+" "+"\""+sCurrentDir+"\\data"+"\"";
					r.exec( com );
				} catch (IOException e) {
					e.printStackTrace();
				}
		File fileDB=new File(sCurrentDir+"\\DB\\");
		if(fileDB.isDirectory()==false){
			if(fileDB.mkdir()==false){
				JOptionPane.showMessageDialog(null, "ディレクトリーが作成できません。"+sCurrentDir+"\\DB");
				System.exit(0);	
			}
			
			// copyDirectory
			try {
				String com="C:\\WINDOWS\\system32\\xcopy /E "+"\""+appdir+"\\itpManager"+Version.getFolderName()+"\\DB"+"\""+" "+"\""+sCurrentDir+"\\DB"+"\"";
				r.exec( com );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
					}
			
			
				}
			}
		}
	}
	
	// ファイルを読む
	public static  String readFile(String fname)
	{
		// full path
		File f = new File(fname);
		byte[] b = new byte[(int) f.length()];
		String	s = "";
		FileInputStream fi = null;
		try 
		{	
			fi = new FileInputStream(f);
			fi.read(b);	
			s = new String(b);	
			//System.out.println(s);
			fi.close();
		}
		catch(Exception e)
		{
//			JOptionPane.showMessageDialog(null, fname+" 設定ファイルが読めません。");
			e.printStackTrace();
			String def=
			"WarnTemp 38"+System.getProperty("line.separator")+
			"WarnWater 35"+System.getProperty("line.separator")+
			"WarnIllum 2000"+System.getProperty("line.separator");
			FileOutputStream fo = null;
			try {
				fo = new FileOutputStream(f);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fo.write(def.getBytes("UTF-8"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return(def);
		}
		return s;
	}
	
	
	// ファイルを上書きする
	public static  void writeFile(String file, String txt)
	{
		// full path
		File f = new File(file);
		
		if (checkBeforeWritefile(f)){
	        try {
				FileWriter filewriter = new FileWriter(file, false);
				filewriter.write(txt);
				filewriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ファイルに追記する
	public static  void addFile(String file, String txt)
	{
		// full path
		File f = new File(file);
		
		if (checkBeforeWritefile(f)){
	        try {
				FileWriter filewriter = new FileWriter(file, true);
				filewriter.write(txt);
				filewriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	  static boolean checkBeforeWritefile(File file){
		    if (file.exists()){
		      if (file.isFile() && file.canWrite()){
		        return true;
		      }
		    } else {
		    	try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    return false;
		  }
	
		// DB のパスを返す
		public static String getDBPath()
		{
			if(IsMacorWin.isMacOrWin()==true ){
				// MacOSX
				String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
				return(sCurrentDir+"/DB/");// Mac
			} else {
				// Windows
				String sCurrentDir=System.getenv("APPDATA");
				sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
				return(sCurrentDir+"\\DB\\");// Windows
			}
		}
	
	// 設定ファイルの名前を返す
	public static String getSetupfile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/setup.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\setup.txt");// Windows
		}
	}

	// 設定ファイルの名前を返す
	public static String getCGSetupfile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/cloudgarden.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\setup.txt");// Windows
		}
	}
	
	// 設定ファイルの名前を返す
	public static String getCGsite()
	{
		String f=getCGSetupfile();
		String n=Files.readFile(f);
		return n;// control.cloud-garden.com:8080
	}

	// FIle save & load
	public static String compFile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home");
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/"+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName()+"_Comp.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data"+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName()+"_Comp.txt");// Windows
		}		

	}

	
	// 設定ファイルの名前を返す
	public static String getRecordfile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home");
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/record.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\record.txt");// Windows
		}		
	}
	
	// プランターの設定ファイルの名前を返す
	public static String getPlantersfile()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home");
			return(sCurrentDir+"/itpManager"+Version.getFolderName()+"/data/planters.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\planters.txt");// Windows
		}		
	}
	
	// 設定ファイルの名前を返す
	public static String getImagefile(String f)
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+f);// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+f);// Windows
		}
	}

// 画像ファイルの削除
	public static void delete(String qname) {
		String imageFile=getImagefile(qname);
		File file=new File(imageFile);
		//System.out.println("imageFile="+imageFile);
		boolean flag=false;
		for(int i=0;i<10;i++){
		//System.out.println("file.exists "+file.exists());
		if(file.exists()) flag=file.delete(); 
		if(flag) break;
		}
	}

	public static String getupAlbumFile() {
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
			sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/PicasaSetup.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\PicasaSetup.txt");// Windows
		}
	}
	
	public static void SaveCameraResolution(int w, int h) {
		String message="Resolution "+w+"X"+h;
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);
	}
	
	public static void SavePlanterName(String old_planterName) {
		// プランターの名前を保存する。　すでにある名前は書き換える
		// 現在のプランターの名前は、すでに　ITPlanterClass.getCurrentPlanterClass().getInformation().setPlanterName("newPlanterName"); で登録されている。
		String s1 = Files.readFile(getPlantersfile());
		String[] s2=s1.split(System.getProperty("line.separator"));
		// 現在のプランターの情報までスキップする
		String current_planterName=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName();
		if( old_planterName.equals(current_planterName)) return;

		String add="";
		String res="";
		boolean sw=false;
		for(int i=0;i<s2.length;i++){
			if(s2[i].contains(old_planterName)==false ) continue;// skip
			if(s2[i].contains("Planter")==true){
				res="Planter "+current_planterName;
				s2[i]=res;
				sw=true;
				break;
			}
		}
		//
		if(sw==false){
		// New entry
			add="Planter "+current_planterName+System.getProperty("line.separator");
			System.out.println("add planter file"+add);
		}
		// output strings
		String s3="";
		for(int i=0;i<s2.length;i++){
			//System.out.println("s2:"+s2[i]);
			s3 += s2[i]+System.getProperty("line.separator");
		}
		if(add.equals("")==false) s3+=add;
		//System.out.println("planter file"+getPlantersfile());
		//System.out.println(s3);
		writeFile(getPlantersfile(), s3);
	}
	
	
	public static String LoadCameraResolution() {
		return loadKeywordData("Resolution");
	}
	
	public static String loadKeywordData(String keyword){
		// Planters.txtからキーワードの値を読み出す
		return Files.readKeywordDatafromFile(getPlantersfile(),keyword);
		/*
		String s="";
		String s1 = Files.readFile(getPlantersfile());
		String[] s2=s1.split(System.getProperty("line.separator"));
		
		for(int i=0;i<s2.length;i++){
			if(s2[i].contains(keyword)==true){
				String[] r=s2[i].split(" ");
				if(r.length>1)
							s=r[1];
			}
		}
		return s;
		*/
	}

	public static void SaveCameraInformation(int cn, String camname) {
		// プランターの名前を保存する。　すでにある名前は書き換える
		// 現在のプランターの名前は、すでに　ITPlanterClass.getCurrentPlanterClass().getInformation().setPlanterName("newPlanterName"); で登録されている。
		String s1 = Files.readFile(getPlantersfile());
		String[] s2=s1.split(System.getProperty("line.separator"));
		// 現在のプランターの情報までスキップする
		String old_planterName=ITPlanterClass.getCurrentPlanterClass().getInformation().getOldPlanterName();
		String current_planterName=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName();
	//	if( old_planterName.equals(current_planterName)) return;
		String add="";
		String res="";
		boolean sw=false;
		for(int i=0;i<s2.length;i++){
			if(s2[i].contains(old_planterName)==false ) continue;// skip
			if(s2[i].contains("CamNo")==true){
				res="CamNo "+cn;
				s2[i]=res;
				sw=true;
			}
			if(s2[i].contains("CamName")==true){
				res="CamName "+camname;
				s2[i]=res;
				sw=true;
				break;
			}
		}
		//
		if(sw==false){
		// New entry
			add ="Planter "+current_planterName+System.getProperty("line.separator");// new planter name
			add+="Camera yes "+System.getProperty("line.separator");
			add+="CamNo "+cn+System.getProperty("line.separator");
			add += "CamName "+camname+System.getProperty("line.separator");
			add += "Resolution 640X480"+System.getProperty("line.separator");// default resolution
			System.out.println("add Camera"+add);
		}
		// output strings
		String s3="";
		for(int i=0;i<s2.length;i++){
			//System.out.println("s2:"+s2[i]);
			s3 += s2[i]+System.getProperty("line.separator");
		}
		if(add.equals("")==false) s3+=add;
		//System.out.println("planter file"+getPlantersfile());
		//System.out.println(s3);
		writeFile(getPlantersfile(), s3);
	}

	public static String getFileContents(String message )
	{
		// 現在のプランター設定業まで読み飛ばす
		// 現在のプランターの名前は、すでに　ITPlanterClass.getCurrentPlanterClass().getInformation().setPlanterName("newPlanterName"); で登録されている。
		String data="";
		String s1 = Files.readFile(getPlantersfile());
		String[] s2=s1.split(System.getProperty("line.separator"));
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		boolean sw1=false;
		boolean sw2=false;
		
		String[] keydata=message.split(" ");// keyword and data
		
		for(int i=0;i<s2.length;i++){
			//System.out.println("0  "+s2[i]+" " +keydata[0] + " "+current_planterName);
			
			// 現在のプランターの情報までスキップする
			if(sw1==false && s2[i].contains("Planter")==true 
					//&& s2[i].contains(current_planterName)==true
					){
				sw1=true;// 見つかった！
			//System.out.println("1 "+s2[i]+" " +keydata[0]);
				if(s2[i].length()>0){
				data += s2[i]+System.getProperty("line.separator");
				}
				continue;
			}
			
			//
			if(sw1==true){
			// キーワードを探す
			//System.out.println("1"+s2[i]+" " +keydata[0]);
				
			if(s2[i].contains(keydata[0])==true){
			// キーワードがあれば書き換える
			//System.out.println("333"+s2[i]+" " +keydata[0]);
				s2[i]=message;
				sw2=true;
			} else
			
			// キーワードがなければ追加する
			if(s2[i].contains("Planter")==true && sw2==false){
				// 別のプランター情報になってしまったので追加する。
			//System.out.println("444"+s2[i]+" " +keydata[0]);
					if(message.length()>0){
					data += message+System.getProperty("line.separator");
					}
				}
			}
				if(s2[i].length()>0){
				data += s2[i]+System.getProperty("line.separator");
				}
		}		
		return data;
	}
	

	 static ArrayList<String> planterSettings=null;
	 static String[] currentPlanterSettings=null;
	// キーワードの値を読み出す
	private static String readKeywordDatafromFile(String plantersfile, String keyword) {
		// get Target Planter Name
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		
		String pn=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName();
	//	System.out.println("pn="+pn);
		
		// reload planter setting data when currentPlanter is changed. 
		if(currentPlanterSettings!=null){
		if(currentPlanterSettings[0].contains(pn)==false){
			planterSettings=null;
			currentPlanterSettings=null;
			}
		}
		
		if(planterSettings==null){
		String s=readFile(plantersfile);
		String[] p=s.split(System.getProperty("line.separator"));
		// search Planter Keyword
		ArrayList<Integer> planterPosition=new ArrayList<Integer>();
		for(int i=0;i<p.length;i++){
			if(p[i].contains("Planter")==true) planterPosition.add(i);
		}
		planterPosition.add(p.length-1);
		// set planterSettings array
		planterSettings=new ArrayList<String>();
		for(int j=0;j<planterPosition.size()-1;j++){
				String t="";
				
				//System.out.println("j="+j+" maxj"+planterPosition.size());
				//System.out.println("start"+planterPosition.get(j)+" end"+planterPosition.get(j+1));
				
				for(int i=planterPosition.get(j);i<planterPosition.get(j+1);i++){
					t += (p[i]+System.getProperty("line.separator"));
				}
				planterSettings.add(t);
		}
		//
		for(int i=0;i<planterSettings.size();i++){
			if(planterSettings.get(i).contains(pn)==true){
				currentPlanterSettings=planterSettings.get(i).split(System.getProperty("line.separator"));
				break;
				}
			}
		// Current Planter Setting is not found.
			if(currentPlanterSettings==null) currentPlanterSettings=planterSettings.get(0).split(System.getProperty("line.separator"));
		}
		//
	
		// search keyword of current planter
		//
		for(int i=0;i<currentPlanterSettings.length;i++){
			if(currentPlanterSettings[i].contains(keyword)== true){
//				String[] q=currentPlanterSettings[i].split(" ");
//				return q[1];
				return currentPlanterSettings[i];
				}
			}
		return null;
	}

	// 栽培モードを保存する。
	public static void SaveCultivationMode(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);
	}
	// 栽培モードを読み出す。
	public static String LoadCultivationMode() {
		String data="CultivationMode";
		return readKeywordDatafromFile(getPlantersfile(), data);
	}
	
	// 温度補正値を保存する。
	public static void SaveTempCorrection(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);
	}

	// 温度補正値を読み出す。
	public static String LoadTempCorrection() {
		String data="TempCorrect";
		String r=readKeywordDatafromFile(getPlantersfile(), data);
		if(r==null) return "0";
		String[] rr=r.split(" ");
		if(rr.length>1) r=rr[1]; else r="0";
		return r;
	}

	// 照度補正値を保存する。
	public static void SaveIllumCorrection(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);
	}
	// 照度補正値を読み出す。
	public static String LoadIllumCorrection() {
		String data="IllumCorrect";
		String r=readKeywordDatafromFile(getPlantersfile(), data);
		if(r==null) return "0";
		String[] rr=r.split(" ");
		if(rr.length>1) r=rr[1]; else r="0";
		return r;
	}

	// Manual->Auto復帰時間を保存する。
	public static void SaveManualAutoTime(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);
	}

	// Manual->Auto復帰時間を読み出す。
	public static String LoadManualAutoTime() {
		String data="ManualAutoTime";
		String r=readKeywordDatafromFile(getPlantersfile(), data);
		if(r==null) return "0";
		String[] rr=r.split(" ");
		if(rr.length>1) r=rr[1]; else r="0";
		return r;	}

	// PC->Auto復帰時間を保存する。
	public static void SavePCAutoTime(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);	
	}
	// PC->Auto復帰時間を読み出す。
	public static String LoadPCAutoTime() {
		String data="PCAutoTime";
		String r=readKeywordDatafromFile(getPlantersfile(), data);
		if(r==null) return "0";
		String[] rr=r.split(" ");
		if(rr.length>1) r=rr[1]; else r="0";
		return r;
		}
	// ポンプブースト時間を保存する。
	public static void SaveBoostTime(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);
	}
	// ポンプブースト時間を読み出す。
	public static String LoadBoostTime() {
		String data="PumpBoost";
		String r=readKeywordDatafromFile(getPlantersfile(), data);
		if(r==null) return "0";
		String[] rr=r.split(" ");
		if(rr.length>1) r=rr[1]; else r="0";
		return r;
		}
	// 白色LED点滅時間を保存する。
	public static void SaveWhiteLEDTime(String message) {
		String data=getFileContents( message );
		writeFile(getPlantersfile(), data);	
	}
	// 白色LED点滅時間を読み出す。
	public static String LoadWhiteLEDTime() {
		String data="WhiteLEDTime";
		String r=readKeywordDatafromFile(getPlantersfile(), data);
		if(r==null) return "0";
		String[] rr=r.split(" ");
		if(rr.length>1) r=rr[1]; else r="0";
		return r;
		}

	//
	
	
	// keyword	dataString
	//111
	public static String getKeywordData(String key) {
		String rs=readKeywordDatafromFile( getPlantersfile(), key);
		
		/*
		String s="";
		if(plantersSettings==null){
			String d=readFile(getPlantersfile());
	//	System.out.println("get "+d+"|");
			plantersSettings=getPlantersSettings(d);
		}

		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		PlanterClass pclass=ITPlanterClass.getCurrentPlanterClass();
		Information info=pclass.getInformation();
		
		String pname0=Files.getPlanterName();
		
		String[] pname1=pname0.split(" ");
		String pname=pname1[1];
		info.setPlanterName(pname);
		
		for(int i=0;i<plantersSettings.size();i++){
			if(plantersSettings.get(i).contains("Planter")==true 
					&& plantersSettings.get(i).contains(pname)==true){
				String[] p=plantersSettings.get(i).split(System.getProperty("line.separator"));
				//for(int t=0;t<p.length;t++)
				//  System.out.println(t+" "+plantersSettings.get(i).split(System.getProperty("line.separator"))[t]);
				
				for(int j=0;j<p.length;j++){// Debug 2013/06/21
					if(p[j].contains(key)==true){
//System.out.println(i+" "+j+" "+pname+" "+j+" "+key+" |"+p[j]+"|");
						s=p[j];
						i++;
						break;
							}
						}// next j
					}
			}// next i
//System.out.println("getKeywordData001 |"+s+"|");
			s=s.replace("\t", " ");
//System.out.println("getKeywordData002 |"+s+"|");
			
			String rs = Files.removeKaigyo(s);// ??????
			
			//System.out.println("getKeywordData1 |"+s+"|");
			 */
			return rs;
		}//
	
	
	
	public static String removeKaigyo(String s)
	{
		return( s.replaceAll(System.getProperty("line.separator"),""));
//		return( s.replaceAll("\r\n",""));
	}
	// save all plantersSetting infor
	public static void savePlantersSettings(){
		if(planterSettings!=null){
			// ToDo
			//  copy currentPlanterSettings to planterSettings
			writeFile(getPlantersfile(), ArrayListtoString(planterSettings));	
		}
	}

	private static String ArrayListtoString(ArrayList<String> ps) {
		String r="";
		for(int i=0;i<ps.size();i++){
			r += (ps.get(i)+System.getProperty("line.separator"));
		}
	//	System.out.println("save "+r+"|");
		return r;
	}

	private static void plantersSettingsReplace(String t) {
		String key="";
		t=t.replace("\t", " ");// tab to space
		String[] u=t.split(" ");// separate bye space
		key=u[0];
		//System.out.println("name="+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName());
		if(planterSettings==null) return;
		for(int i=0;i<planterSettings.size();i++){
			String[] v=planterSettings.get(i).split(System.getProperty("line.separator"));
			for(int j=0;j<v.length;j++){

				if(v[j].contains("Planter")==true){
					String[] n=v[j].split(" ");
					//System.out.println("n0="+n[0]+" "+"n1="+n[1]);
					if(n[0].equals("Planter")&&n[1].contains(ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName())==true){

					for(int l=j;l<v.length;l++){
					if(v[l].contains(key)==true){
//System.out.println("from i="+i+" l="+l+" "+v[l]+" keyword "+key);
						v[l]=t;
//System.out.println("to i="+i+" l="+l+" "+v[l]+" keyword "+key);
							planterSettings.remove(i);
							String w="";
							for(int k=0;k<v.length;k++) w+=(v[k]+System.getProperty("line.separator"));
							planterSettings.add(w);
							i++;
							}
						}
					}
				}
			}
		}
		
	}
	
	// getSeries
	// Reports	Water Temp Illum
	public static String getReports() {
		String t="Reports";
		String s=getKeywordData(t);
		if(s==null) return "false";
		else return s;		
		}//


	public static String getPhotoHolder() {
		String t="PhotoHolder";
		String r=getKeywordData(t);
		if(r==null) return "false";
//System.out.println("getPhotoHolder0|"+r+"|");
		if(r.length()==0) return("");
//System.out.println("getPhotoHolder1|"+r+"|");	
	r=r.replaceAll(System.getProperty("line.separator"),"");
	r=r.replaceAll("\n","");// << 改行を取り去る
		return(r);
	}//
	
	// CheckSaveImage	status
		public static String getCheckSaveImage() {
			String t="CheckSaveImage";
			String s=getKeywordData(t);
			if(s==null) return "false";
			else return s;
			}//

	// AutoPicture	ON or OFF
	public static String getAutoPicture() {
		String t="AutoPicture";
		String s=getKeywordData(t);
		if(s==null) return "false";
		else return s;		}//
	
	// RecordSensor recordPeriodicTime[sec]
	public static String getRecordSensor() {
		String t="RecordSensor";
		String s=getKeywordData(t);
		if(s==null) return "false";
		else return s;		
		}//
	
	
	// Serial serialNo
	public static String getSerial() {
		String t="Serial";
		String s=getKeywordData(t);
		if(s==null) return "false";
		else return s;
	}//
	//
	
	// setSeries
	// Reports	Water Temp Illum
	public static void setReports(String s) {
		String t="Reports"+" "+s;
		plantersSettingsReplace(t);
		}//
	
	// PhotoHolder	adress
	public static void setPhotoHolder(String s) {
		String t="PhotoHolder"+" "+s;
		plantersSettingsReplace(t);
		}//
	
	// CheckSaveImage
	public static void setCheckSaveImage(String s) {
		String t="CheckSaveImage"+" "+s;
		plantersSettingsReplace(t);
		}//

	// AutoPicture	ON or OFF
	public static void setAutoPicture(String s) {
		String t="AutoPicture"+" "+s;
		plantersSettingsReplace(t);
		}//
	
	// RecordSensor recordPeriodicTime[sec]
	public static void setRecordSensor(String s) {
		String t="RecordSensor"+" "+s;
		plantersSettingsReplace(t);
		}//
	
	// Serial serialNo
	public static void setSerial(String s) {
		String t="Serial"+" "+s;
		plantersSettingsReplace(t);
		}//

	
	private static ArrayList<String> getPlantersSettings(String s1) {
		ArrayList<String> result=new ArrayList<String>();
		String[] s2=s1.split(System.getProperty("line.separator"));
		for(int i=0;i<s2.length;i++){
			s2[i]=s2[i].replace("\t", " ");// tab to space
			if(s2[i].contains("Planter")==true){
				String s=new String();
				for(int j=i;j<s2.length;j++){
					if(s2[j].length()>0){
						s+=(s2[j]+System.getProperty("line.separator"));
					}
					if(j<s2.length-1){
					if(s2[j+1].contains("Planter")==true){
						i=j;
						break;
						}
					}
				}// next j
				result.add(s);
				//plantersSettings.add(s);
			}// Planter keyword
		}// next i
		return result;
	}

	/*
	// MailSetup.txt
	public static void saveSMTPPasswd(String sMTPPasswdString) {
		// TODO Auto-generated method stub
		
	}

	public static void saveToAddress(String toAddressString) {
		// TODO Auto-generated method stub
		
	}

	public static void saveMessage(String titleString) {
		// TODO Auto-generated method stub
		
	}

	public static void saveSMTPServer(String sMTPServerString) {
		// TODO Auto-generated method stub
		
	}
	
	public static void saveCheckSaveHolder(boolean selected) {
		// TODO Auto-generated method stub
		
	}
	*/


	// File save & load
	public static String getSetupMailFileName()
	{
		if(IsMacorWin.isMacOrWin()==true ){
			// MacOSX
			String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
			return(sCurrentDir+"/data/MailSetup.txt");// Mac
		} else {
			// Windows
			String sCurrentDir=System.getenv("APPDATA");
			sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
			return(sCurrentDir+"\\data\\MailSetup.txt");// Windows
		}
	}
	
	public static void saveMailSetup(String s) 
	{
		/*
		0  sMTPServerString+System.getProperty("line.separator");
		1  sMTPPasswdString+System.getProperty("line.separator");
		2   toAddressString+System.getProperty("line.separator");
		3 fromAddressString+System.getProperty("line.separator");
		4       titleString+System.getProperty("line.separator");
		5 messageAreaString+System.getProperty("line.separator");
		 */
		writeFile(getSetupMailFileName(), s);
	}

	public static void setPlanterName(String nPlanterName) {
		String t="Planter"+" "+nPlanterName;
		plantersSettingsReplace(t);
	}

	public static void setPlanterName(String nPlanterName, String serial) {
		// Planter planterName
		// Serial serial
		
		String t="Planter"+" "+nPlanterName;
		plantersSettingsReplace(t);
		
		String s="Serial"+" "+serial;
		plantersSettingsReplace(s);
	}

	public static void setCheckSaveHolder(boolean selected) {
		String t="CheckSaveHolder"+" "+selected;
		plantersSettingsReplace(t);
System.out.println("FilesClass: setCheckSaveHolder :"+t);
	}
	// CheckSavePicasa	status
	public static String getCheckSaveHolder() {
		String t="CheckSaveHolder";
		String s=getKeywordData(t);
		if(s!=null)
			return s;
		else
			return "false";
		}//

	public static void setCheckSavePicasa(boolean selected) {
		String t="CheckSavePicasa"+" "+selected;
		plantersSettingsReplace(t);
System.out.println("FilesClass: setCheckSavePicasa :"+t);
	}
	
	// CheckSavePicasa	status
	public static String getCheckSavePicasa() {
		String t="CheckSavePicasa";
		String s=getKeywordData(t);
		if(s==null) return "false";
		else return s;
		}//
	
	// Files.IscheckSavePicasa()
	public static boolean IscheckSavePicasa()
	{
		if(getCheckSavePicasa().contains("true")) return true; else return false;
	}

	public static String getCamNo() {
		String t="CamNo";
		return getKeywordData(t);
	}

	public static void setCamNo(int camno) {
		String t="CamNo"+" "+camno;
		plantersSettingsReplace(t);
	}

	public static String getReportsPeriodicTime() {
		String[] s=getReports().split(" ");
		for(int i=1;i<s.length;i++){
			if(s[i].contains(":")==true){
				return s[i];
				}
			}
		return null;
		}
	
	
	// 設定ファイルの名前を返す
	public static void setupData()
	{
		//System.exit(0);
		//
			if(IsMacorWin.isMacOrWin()==true ){
				String sCurrentDir=System.getProperty("user.home");
				// MacOSX
				sCurrentDir=sCurrentDir+"/itpManager"+Version.getFolderName();
				File d=new File(sCurrentDir);
				if(d==null || d.exists()==false){
					// do install
					JOptionPane.showMessageDialog(null, "必要なデータがインストールされていません。インストールをやり直してください。");
					System.exit(0);
				}

			} else {
				// Windows
				String appdir=System.getenv("APPDATA");
				appdir=appdir+"\\itpManager"+Version.getFolderName();
				System.out.println("APPDATA="+appdir);
				File d=new File(appdir);
				if(d==null || d.exists()==false){
					// do install
					JOptionPane.showMessageDialog(null, "必要なデータがインストールされていません。インストールをやり直してください。");
					System.exit(0);
				}
			}
	}
	
	// ??
	public static String getWarningLevel(String k) {
		String s=readFile(getPlanterSettingfile());
		String[] t=s.split(System.getProperty("line.separator"));
		for(int i=0;i<t.length-1;i++){
			if(t[i].contains(k)==true) return t[i+1];
		}
		return null;
	}

	
	public static void doWrite(String x)
	{
	String y="";
	boolean b=false;
	String[] data=new String[5];
	String s=readFile(getPlanterSettingfile());
	if(s!=null){
	s=s.replace(System.getProperty("line.separator"), " ");
	data=s.split(" ");
	String[] key=x.split(" ");
	for(int i=0;i<data.length-1;i++){
		if(data[i].equals(key[0])==true){
			data[i+1]= key[1];
			b=true;
			break;
		}
	}
	for(int i=0;i<data.length-1;i++) y+=(data[i]+" ");
	}
	y+=data[data.length-1];
	//
	if(b==false) y+=x;// なければここで追加
	
	writeFile(getPlanterSettingfile(), y);
	}
	//
	public static void setTempratureWarningLevel(String x) {
		doWrite(x);
	}
	public static void setWaterWarningLevel(String x) {
		doWrite(x);
	}

	public static void setIlluminationWarningLevel(String x) {
		doWrite(x);
	}

	private static String getPlanterSettingfile() {
			if(IsMacorWin.isMacOrWin()==true ){
				// MacOSX
				String sCurrentDir=System.getProperty("user.home")+"/itpManager"+Version.getFolderName();
				if(ITPlanterClass.getState()==false) new ITPlanterClass();
				return(sCurrentDir+"/data/"+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName()+"_setting.txt");// Mac
			} else {
				// Windows
				String sCurrentDir=System.getenv("APPDATA");
				sCurrentDir=sCurrentDir+"\\itpManager"+Version.getFolderName();
				if(ITPlanterClass.getState()==false) new ITPlanterClass();
				return(sCurrentDir+"\\data\\"+ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName()+"_setting.txt");// Mac
			}
	}
	public static String getWaterWarningLevel() {
		/*
		String s=getKeywordData("WarnWater");
		String[] t=s.split(" ");
		t[1]=t[1].replace(System.getProperty("line.separator"), "");
		t[1]=t[1].replace("\n", "");
		return t[1];
		*/
		String s=readFile(getPlanterSettingfile());
		if(s.equals("")) return null;
		s=s.replace(System.getProperty("line.separator"), " ");
		String[] t=s.split(" ");
		for(int i=0;i<t.length-1;i++){
			if(t[i].contains("WarnWater")==true){
				t[i+1]=t[i+1].replace(System.getProperty("line.separator"), "");
				t[i+1]=t[i+1].replace("\n", "");
				return t[i+1];
			}
		}
		return null;
		
	}
	
	public static String getIlluminationWarningLevel() {
		/*
		String s=getKeywordData("WarnIllum");
		String[] t=s.split(" ");
		t[1]=t[1].replace(System.getProperty("line.separator"), "");
		t[1]=t[1].replace("\n", "");
		return t[1];
		*/

		String s=readFile(getPlanterSettingfile());
		if(s.equals("")) return null;
		s=s.replace(System.getProperty("line.separator"), " ");
		String[] t=s.split(" ");
		for(int i=0;i<t.length-1;i++){
			if(t[i].contains("WarnIllum")==true){
				t[i+1]=t[i+1].replace(System.getProperty("line.separator"), "");
				t[i+1]=t[i+1].replace("\n", "");
				return t[i+1];
			}
		}
		return null;
		
	}
	public static String getTempratueWarningLevel() {
		/*
		String s=getKeywordData("WarnTemp");
		String[] t=s.split(" ");
		t[1]=t[1].replace(System.getProperty("line.separator"), "");
		t[1]=t[1].replace("\n", "");
		return t[1];
		*/
		
		String s=readFile(getPlanterSettingfile());
		if(s.equals("")) return null;
		s=s.replace(System.getProperty("line.separator"), " ");
		String[] t=s.split(" ");
		for(int i=0;i<t.length-1;i++){
			if(t[i].contains("WarnTemp")==true){
				t[i+1]=t[i+1].replace(System.getProperty("line.separator"), "");
				t[i+1]=t[i+1].replace("\n", "");
				return t[i+1];
			}
		}
		return null;
		
	}

	public static boolean isNet() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static String getPlanterName() {
		String pn=getKeywordData("Planter");
		return pn;
	}
	
}