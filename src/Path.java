import java.io.File;


public class Path {
	final static String sCurrentDir = new File(".").getAbsoluteFile().getParent();  //  
	final static String pathMac=new String(sCurrentDir+"/");  // MacOSX
	final static String pathWin=new String(sCurrentDir+"\\");  //Win
	final static String sendcomMac=new String("/com"+"/Mac"+"/sendcom");  // MacOSX
	final static String sendcomWin=new String("\\com\\Win\\sendcom.exe"+"\"");  //Win

	public static String getPath()
	{
		if(IsMacorWin.isMacOrWin()) return pathMac;
		else return pathWin;
	}
}
