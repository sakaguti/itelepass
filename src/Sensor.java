import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang.math.NumberUtils;


public class Sensor {
	private static  double temperature=0.0;
	static  double waterlevel=0.0;
	static  double illumination=0.0;
	
	public static boolean temperatureReport=false;
	public static boolean waterlevelReport=false;
	public static boolean illuminationReport=false;
	
//
//	private int levelLimit=0;  
//
	private Clock clock; // ?
	private static int currentCamNo=0;
	private static int totalCamNo=0;
	private static int activeCamNo=0;
	private static double waterFull=500;// full default value
	private static double waterEmpty=200;// empty default value
	private static double waterWarningLevel=0.0;
	private static double temperatureWarningLevel=0.0;
	private static double illuminationWarningLevel=0.0;
	private static double frequency=0.0;
	private static double duty=0.0;
	private PlanterClass currentPlanterClass=null;

	private double tempCorrect=0.0;
	private static double illumCorrect=1.0;
	
	public void setTotalCamNo(int n)
	{
		totalCamNo=n;
	}
	public static boolean getTemperatureReport()
	{
		return temperatureReport;
	}
	
	public static boolean getWaterlevelReport()
	{
		return waterlevelReport;
	}
	
	public static boolean getIlluminationReport()
	{
		return illuminationReport;
	}
	
	public static double getWaterWarningLevel()
	{
		return waterWarningLevel;
	}

	
	public static double getIlluminationWarningLevel()
	{
		return illuminationWarningLevel;
	}

	public static double getTemperatureWarningLevel()
	{
		return temperatureWarningLevel;
	}

	public void setTempratureReport(boolean b)
	{
		temperatureReport=b;
	}
	
	public void setWaterlevelReport(boolean b)
	{
		waterlevelReport=b;
	}
	
	public void setIlluminationReport(boolean b)
	{
		illuminationReport=b;
	}

	// Constructor
	public Sensor(PlanterClass planterClass) {
		this.currentPlanterClass=planterClass;
	}
	
	public void setComp()
	{
		// 補正値を読み込む
		String s=fread();
		String[] t=s.split(System.getProperty("line.separator"));
		if(t.length>1){
			setTempCorrect(Double.valueOf(t[0]));
			setIllumCorrect(Double.valueOf(t[1]));
		}
	}
	
	public void setWaterLevelWarningLevel(double w)
	{
		String Command="x"+WaterLevelToAD(w);
		// ??????  //
//		setValue(Command);
//		itp_Logger.logger.info("setWaterLevelWarningLevel "+String.valueOf(getSensorTemperature()));
	}

	public void setSerialNumber(int i)
	{
		String Command="\\\\"+i;
		setValue(Command);
		itp_Logger.logger.info("setSerial "+String.valueOf(i));
	}
	
	private int WaterLevelToAD(double w) {
		return (int)((w-30.0)*(waterFull-waterEmpty)+waterEmpty);
	}

	public void setTempratureWarningLevel(double w)
	{
		temperatureWarningLevel=w;
//		itp_Logger.logger.info("setTempratureWarningLevel "+String.valueOf(w));
	}
	
	public void setIlluminationWarningLevel(double w)
	{
		illuminationWarningLevel=w;
//		itp_Logger.logger.info("setIlluminationWarningLevel "+String.valueOf(w));
	}

	private void setValue(String Command) {
		// 
		int n=ITPlanterClass.getCurrentPlanterNo();
		String wr = sendCom.sendcom((n+1)+" -e "+Command); // sendcom
//		itp_Logger.logger.info(wr);
		/*
		String[]  arr=wr.split(System.getProperty("line.separator"),0);
		// SendComが受けたコマンドが正しいかどうかの検証
		if( arr[0].compareTo( String.valueOf("Command: "+Command)) != 0){
			// 正しくない！
			itp_Logger.logger.info("アイティプランターが応答しません。");
		}
		*/
	}
	
	private double ADToTemp(int AD) {
		// AD値と温度との換算
		// 室温 = (AD/1024*3.3*1000-400)/19.5
		return (double)((double)AD/1024*5.0*1000-400)/19.5;
//		return (double)(AD/1024*3.3*1000-400)/19.5;
	}

	private int TempToAD(double t) {
		// 温度とAD値との換算
		// 1024*(19.5*室温+400)/3.3/1000 = AD
		return (int)(1024.0*(19.5*t+400.0)/3300);
	}

	// 全センサーの値を返す
	public String getSensors()
	{
//		System.out.println("SensorClass: getSensors");

		String s="";
		String t="";
		String u="";
		s=String.valueOf(getSensorTemprarure());
		t=s+" ";
		u=s+System.getProperty("line.separator");

		s=String.valueOf(getSensorWaterlevel());
		t+=(s+" ");
		u=(u+s+System.getProperty("line.separator"));
		
		s=String.valueOf(getSensorIllumination());
		t=(t+s+System.getProperty("line.separator"));
		u=(u+s+System.getProperty("line.separator"));

// DUMMY-PLANTER
	if(ITPlanterClass.getCurrentPlanterClass().getInformation().getPlanterName().equals("DUMMY_PLANTER")==true){
		u = "0"+System.getProperty("line.separator")+"0"+System.getProperty("line.separator")+"0"+System.getProperty("line.separator");
		return u;
		}  
	//System.out.println("SensorClass: Rest");
		//sendCom.sendcom(ITPlanterClass.getCurrentPlanterNo()+" -e w");// Reset
		return u;
	}
	
	public double getSensorTemprarure()
	{
			String Command="A";
			setSensorTemperature(getDoubleValue(Command));
//			System.out.println("version "+ITPlanterClass.getCurrentPlanterClass().getVersion().getMejar());
			
			//ITPlanterClass.getCurrentPlanterClass().getVersion();
			if(Version.getMejar()<2){
				// ITPLANTER-01の値　AD値
				setSensorTemperature(ADToTemp((int)getSensorTemperature()));
			}
			
//			itp_Logger.logger.info("getTemprarure temperature "+String.valueOf(temperature));
//			itp_Logger.logger.info("getTemprarure tempCorrect "+String.valueOf(tempCorrect));
//			
			if(getSensorTemperature() > getTemperatureWarningLevel()){
				temperatureReport=true;
				sendMail.addToText(String.valueOf(getSensorTemperature()));
			}
//
			return getSensorTemperature()+tempCorrect;
	}
	
	// センサーは読まずに温度値を返す。
	public double getTemprarure()
	{
			return getSensorTemperature()+tempCorrect;
	}

	// 温度値をセットする。
	public void setTemprarure(Double v)
	{
			setSensorTemperature(v-tempCorrect);
	}

	public double getSensorWaterlevel()
	{
			//String Command="m";
			//Integer[] ivalue=getTripleIntValue(Command);
			
			// C command
			// Full=1 Empty=0		90%
			// Full=0 Empty=0		65%
			// Full=0 Empty=1		30%
			
			if(ITPlanterClass.getSystemPlanterSize()==0) return 0.0;
			// 
			int n=ITPlanterClass.getCurrentPlanterNo();
			System.out.println("getSensorWaterlevel:"+"sendcom "+(n+1)+" -e "+" C");

			String wr = sendCom.sendcom((n+1)+" -e C"); // sendcom
//			System.out.println("retrun:|"+wr+"|");
			String[] arg=null;
			if(wr!=null)
				arg=wr.split(System.getProperty("line.separator"));
			if(arg!=null){
			waterlevel=0.0;
			//
			// 130704
//			ITPlanterClass.getCurrentPlanterClass().getVersion();
			if(Version.getMejar()<2){
			// ITPLANTER-01の値　AD値
			// 水位とAD値の関係は、どんな計算式だったかな？
			// TODO
				waterlevel=60.0;
			} else {
			// ITPLANTER-02の値
			if(arg.length>1){
			String[] parA=arg[1].split(" ");
			String[] full =parA[0].split("=");
			String[] empty=parA[1].split("=");
			
			if(full.length>1 && empty.length>1){
			if(full[1].contains("0") && empty[1].contains("1")) waterlevel=25.0;
			if(full[1].contains("0") && empty[1].contains("0")) waterlevel=60.0;
			if(full[1].contains("1") && empty[1].contains("0")) waterlevel=90.0;
					}
				}
			}
			/* 静電センサーの値は、信用できない。
			waterEmpty=ivalue[0]; waterFull=ivalue[2];
			if((Double.valueOf(ivalue[2])-Double.valueOf(ivalue[0]))!=0.0){
			waterlevel=(Double.valueOf(ivalue[1])-Double.valueOf(ivalue[0]))/(Double.valueOf(ivalue[2])-Double.valueOf(ivalue[0]))*70.0+30.0;
			} else waterlevel=0.0;
			*/
		
			if(waterlevel<0.0) waterlevel=0.0;
			if(waterlevel>100) waterlevel=100.0;
//			itp_Logger.logger.info("getWaterlevel "+String.valueOf(waterlevel));
			if(waterlevel > getWaterWarningLevel()){
				waterlevelReport=true;
				sendMail.addToText(String.valueOf(waterlevel));
			}
			}
			return waterlevel;
	}
	
	//　センサーは読まずに水位を返す
	public static double getWaterlevel()
	{
		return waterlevel;
	}
	
	//　水位をセットする
	public void setWaterlevel(Double v)
	{
		waterlevel=v;
	}

	public double getSensorIllumination()
	{
			String Command="F";
			// 130704
//			ITPlanterClass.getCurrentPlanterClass().getVersion();
			if(Version.getMejar()==1){
				illumination=getDoubleValue(Command)*11.0;// AD値と照度の換算係数				
			} else {
				illumination=Math.abs(getDoubleValue(Command));//*11.0;// AD値と照度の換算係数
			}
//			itp_Logger.logger.info("getIllumination "+String.valueOf(illumination));
			if(illumination > getIlluminationWarningLevel()){
				illuminationReport=true;
				sendMail.addToText(String.valueOf(illumination));
			}
			return illumination*illumCorrect;
	}

	// センサーは読まずに照度値を返す
	public static double getIllumination()
	{
			return illumination*illumCorrect;
	}

	// 照度値をセットする
	public void setIllumination(Double v)
	{
		illumination= v/illumCorrect;
	}

	public void setTempCorrect(Double tc)
	{
			tempCorrect=tc;//
			// 130704
	//		ITPlanterClass.getCurrentPlanterClass().getVersion();
			if(Version.getMejar()==2){
			String Command="T"+String.valueOf(tempCorrect);
			setIntegerValue(Command);// 温度補正値の設定
			tempCorrect=0.0;//
			}
	}

	public void setIllumCorrect(Double tc)
	{
			illumCorrect=tc;
			// 130704
//			ITPlanterClass.getCurrentPlanterClass().getVersion();
			if(Version.getMejar()==2){
			String Command="S"+String.valueOf(illumCorrect);
			setIntegerValue(Command);// 照度補正値の設定
			illumCorrect=1.0;
			}
	}

	private String setIntegerValue(String Command) {
		int n=ITPlanterClass.getCurrentPlanterNo();

System.out.println("getDoubleValue:"+"sendcom "+(n+1)+" -e "+Command);

		String wr = sendCom.sendcom((n+1)+" -e "+Command); // sendcom
		return wr;
	}
	
	public double getDoubleValue(String Command) {
		if(ITPlanterClass.getSystemPlanterSize()==0) return 0.0;
		// 
		int n=ITPlanterClass.getCurrentPlanterNo();
		
System.out.println("getDoubleValue:"+"sendcom "+(n+1)+" -e "+Command);
		
		String wr = sendCom.sendcom((n+1)+" -e "+Command); // sendcom
//		itp_Logger.logger.info(wr);
		String[]  arr=null;
		if(wr != null)
			arr=wr.split(System.getProperty("line.separator"),0);
		//
		if(arr==null) return 0;
		// SendComが受けたコマンドが正しいかどうかの検証
		/*
		String comCom="Command: "+Command;
		if( arr[0].contains(comCom)==false){
			// 正しくない！
			itp_Logger.logger.info("アイティプランターが応答しません。"+ arr[0]+"|"+comCom);
			return 0;
		}
*/
		if(arr.length<2) return 0;
		arr[1]=arr[1].replace("\r", "");
		String[] ps=arr[1].split(" ",0);
		if(ps[0].contains("error")) return 0;
		
		if(ps.length>1){
			double value=0;
			//
//			System.out.println("Sensor0="+ps[0]);
//			System.out.println("Sensor1="+ps[1]);
			// LAMP duty 90  WLED frequency 50
			if(ps!=null){
			if(ps.length>2){
			if(NumberUtils.isNumber(ps[2])==true)
				value=Double.valueOf(ps[2]);
			} else {
				// ERROR対策が必要
			if(NumberUtils.isNumber(ps[1])==true)
					value=Double.valueOf(ps[1]);
				}
			}
		return value;
		} else return 0;
	}
	
	public int getIntValue(String Command) {
		if(ITPlanterClass.getSystemPlanterSize()==0) return 0;
		//
		int n=ITPlanterClass.getCurrentPlanterNo();
System.out.println("getIntValue:"+"sendcom "+(n+1)+" -e "+Command);
		String wr = sendCom.sendcom((n+1)+" -e "+Command); // sendcom
//		itp_Logger.logger.info(wr);
		String[]  arr=wr.split(System.getProperty("line.separator"),0);
		// 
/*
		String comCom="Command: "+Command;
		if( arr[0].contains(comCom)==false){
			// 正しくない！
			itp_Logger.logger.info("アイティプランターが応答しません。");
			return 0;
		}
*/
		String[] ps=arr[1].split(" ",0);
		int value=Integer.parseInt(ps[1]);
		return value;
	}

	public String getStringValue(String Command) {
		if(ITPlanterClass.getSystemPlanterSize()==0) return "";
		//
		int n=ITPlanterClass.getCurrentPlanterNo();
		
System.out.println("getStringValue:"+"sendcom "+(n+1)+" -e "+Command);

		String wr = sendCom.sendcom((n+1)+" -e "+Command); // sendcom
//		itp_Logger.logger.info(wr);
		String[]  arr=wr.split(System.getProperty("line.separator"),0);
		// 
/*
		String comCom="Command: "+Command;
		if( arr[0].contains(comCom)==false){
			// 正しくない！
			itp_Logger.logger.info("アイティプランターが応答しません。");
			return null;
		}
*/
		return arr[1];
	}
	
	public Integer[] getTripleIntValue(String Command) {
		Integer[] ivalue= new Integer[3];
		ivalue[0]=ivalue[1]=ivalue[2]=0;
		if(ITPlanterClass.getSystemPlanterSize()==0) return ivalue;
		// 
		int n=ITPlanterClass.getCurrentPlanterNo();
		
System.out.println("getTripleIntValue:"+"sendcom "+(n+1)+" -e "+Command);
		String wr = sendCom.sendcom((n+1)+" -e "+Command); // sendcom
//		itp_Logger.logger.info(wr);
		String[]  arr=wr.split(System.getProperty("line.separator"),0);
		//
		if(arr==null) return ivalue;
		// 
/*
		String comCom="Command: "+Command;
		if( arr[0].contains(comCom)==false){
			// 正しくない！
			itp_Logger.logger.info("アイティプランターが応答しません。");
			return ivalue;
		}
*/
		if(arr.length<2) return ivalue;
		String[] ps=arr[1].split(" ",0);
		if(ps[0].contains("error")) return ivalue;
		

		if(ps.length>1){
			/*
			Command: m
			Water Empty 100 Water level 123 Water Full 123
			*/
			if(ps.length>2){
				 int iv=Integer.parseInt(ps[2].replaceAll("[^0-9]",""));//
					ivalue[0]=iv;
//			ivalue[0]=Integer.valueOf(ps[2]);
			} 
			if(ps.length>5){
				 int iv=Integer.parseInt(ps[5].replaceAll("[^0-9]",""));//
					ivalue[1]=iv;
//			ivalue[1]=Integer.valueOf(ps[5]);
			}
			if(ps.length>8){
			//ps[8].replace(System.getProperty("line.separator"), "");
			//System.out.println("ps[8]="+ps[8]);
			// 最後に改行コードが入っているので Integer.valueOfはエラーになる
			ivalue[2]=Integer.parseInt(ps[8].replaceAll("[^0-9]",""));
			}
			return ivalue;
		} else return ivalue;
	}
	public Clock getClock()
	{
		// 内部時計を読み込む
		String Command="G";
		String result=getStringValue(Command);
		String[] p=result.split(" ",0);
		 int h=Integer.parseInt(p[1].replaceAll("[^0-9]",""));//
		 int m=Integer.parseInt(p[3].replaceAll("[^0-9]",""));//
		 int s=Integer.parseInt(p[5].replaceAll("[^0-9]",""));//
//		int h=Integer.valueOf(p[1]);
//		int m=Integer.valueOf(p[3]);
//		int s=Integer.valueOf(p[5]);
		
		clock=new Clock(h,m,s);
		return clock;	
	}
	
	public Clock setClock()
	{
		// 内部時計をPCの内部時計に同期する
		String Command="^";
		getStringValue(Command);
		return clock=getClock();	
	}
	
	// USBにつながっているカメラの総数
	public int getCamNo()
	{
		return totalCamNo;
	}
	
	// 使っているカメラの総数
	public int getActiveCamNo()
	{
		return activeCamNo;
	}

	public void setActiveCamNo(int n)
	{
		activeCamNo=n;
	}

	public void setCurrentCamNo(int n)
	{
		currentCamNo=n;
	}

	public int getCurrentCamNo()
	{
		return(currentCamNo);
	}
	
	public void setWaterFull(int f)
	{
		waterFull=f;
	}
	
	public void setWaterEmpty(int e)
	{
		waterEmpty=e;
	}

	public double getWaterFull()
	{
		return waterFull;
	}
	
	public double getWaterEmpty()
	{
		return waterEmpty;
	}
	
	public void setWaterWarningLevel(double w)
	{
 		waterWarningLevel=w;
	}

	
	public Image getImage()
	{
		Image image = null;
		return image;
	}

	public PlanterClass getCurrentPlanterClass() {
		return currentPlanterClass;
	}

	public void setCurrentPlanterClass(PlanterClass currentPlanterClass) {
		this.currentPlanterClass = currentPlanterClass;
	}
	public void getReset() {
		setValue("s");
	}
	
	
	//

		public  void save(String s) 
		{
			
			File f = new File(Files.compFile());// Windowsに未対応だった。
			
			byte[] b = new byte[(int) f.length()];
			b = s.getBytes();

			try 
			{	
				FileOutputStream fo= new FileOutputStream(f);
				fo.write(b);	
				fo.close();
			}
			catch(Exception e)
			{
				//JOptionPane.showMessageDialog(null,"設定ファイルに書き込めませんでした。");
				e.printStackTrace();
			}
			
			}
		
		public   String fread(){
			//
			File f = new File(Files.compFile());// Windowsに未対応だった。
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
				s="0.0"+System.getProperty("line.separator")+"1.0"+System.getProperty("line.separator");
			}
			return s;
		}
		
		// PWM
		public Double getDuty() {
			// return current Duty
			return duty;
		}
		
		// Frequency
		public void setFrequency(Double v) {
			frequency=v;
		}
		
		// Frequency
		public Double getFrequency() {
			return frequency;
		}
		public static double getSensorTemperature() {
			return temperature;
		}
		public static void setSensorTemperature(double temperature) {
			Sensor.temperature = temperature;
		}
	
}
