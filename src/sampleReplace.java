import java.io.File;

public class sampleReplace {
	public   String planterSchedulefileTemplate()
	{
		String sCurrentDir = new File(".").getAbsoluteFile().getParent();
			if(IsMacorWin.isMacOrWin()==true){
				// MacOSX
				if(IsOS.isOS().contains("Mac")==true)
					return(sCurrentDir+"/data/Mac/_PN_camSch.txt");// MACOSX  
				else if(IsOS.isOS().contains("Linux")==true)
					return(sCurrentDir+"/data/Linux/_PN_camSch.txt");// MACOSX  
			} else {
				// Windows
				return(sCurrentDir+"\\data\\Win\\_PN_camSch.txt");// Windows
			}
			return null;
	}
		
	// 拡張子[.txt]の前に、プランタの名前を入れる。
	public   String planterSchedulefile(String planterName)
	{
		String tmp=planterSchedulefileTemplate();
	String t=tmp.replace("_PN_", planterName);
	return t;
	}
	
	public static void main(String[] args) {
		sampleReplace sr=new sampleReplace();
		String planterName="planter0";
		System.out.println(planterName);
		System.out.println(sr.planterSchedulefile(planterName));
	}

}
