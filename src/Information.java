import java.util.ArrayList;
import java.util.Calendar;

public class Information {
	private  int currentCamera=0; // 現在、利用中のカメラの番号
	// カメラの解像度
	private  int widthCamera=640;
	private  int heightCamera=480;
	private  String planterName=""; // アイティプランターの名前
	private  String oldPlanterName="";
	private  ArrayList<String> cameraList=new ArrayList<String>(); // カメラの全リスト
	
	private  String memoFile="";// メモファイルのファイル名
	private  PlanterClass currentPlanterClass=null; // 現在のプランターのクラス（上位）
	private  String bIXppFileName="";
	private  PlantProgram plantProgram=null;
	
	private  String Reports="Water ";
	private  Calendar lastsendWarnMail=null;
	
	public static void main(String[] args) {
		new ITPlanterClass();
		Information inf=new Information(ITPlanterClass.getCurrentPlanterClass());
		inf.setLastSendWarnMailDate();
		Calendar d=inf.getLastSendWarnMailDate();
		System.out.println("Date:"+d.getTime());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar n=Calendar.getInstance();
		System.out.println("Date:"+n.getTime());
		int dif=n.compareTo(d);
		System.out.println("compareTo:"+dif);
		System.out.println("compareTo:"+(n.getTimeInMillis()-d.getTimeInMillis()));
		
		System.out.println("Diff Time:"+inf.getTimeFromLastWarnMail()/1000);
	}
	
	// 警告メールを送信したらコールする
	public void setLastSendWarnMailDate()
	{
		lastsendWarnMail=Calendar.getInstance();
	}
	//　最後に警告メールを送信してからの経過時間(msec)
	public long getTimeFromLastWarnMail()
	{
		//  millisec
		return (Calendar.getInstance().getTimeInMillis()-lastsendWarnMail.getTimeInMillis());
	}
	
	// 最後に警告メールを送信した日時
	public Calendar getLastSendWarnMailDate()
	{
		return lastsendWarnMail;
	}
	// Contractor
	public Information(PlanterClass planterClass) {
	System.out.println("InformationClassConstructor "+planterClass.getPlanterNo());	
		
		setCurrentPlanterClass(planterClass);
		setCameraName("CAM-0");
		setLastSendWarnMailDate();
		lastsendWarnMail.add(Calendar.DATE,-1); // 初日から機能させるために、1日戻す
	}

	// このクラスを含む planterClass
	public  void setCurrentPlanterClass(PlanterClass planterClass) {
		currentPlanterClass=planterClass;
			}

	public  PlanterClass getCurrentPlanterClass() {
		return currentPlanterClass;
	}
	
	// USBにつながっている全カメラの数
	public  int getTotalCamera()
	{	
		if(cameraList==null) return(0);
		else return(cameraList.size());
	}
	
	// カメラの数を取得する
	public  int getCameraNumber()
	{
		return cameraList.size();
	}
	
	// 現在、使っているカメラの番号を設定する
	/*
	public void setCurrentCameraNumber(int i)
	{
		currentCamera=i;
	}
	*/
	
	// 現在、使っているカメラの名前を取得する
	public  String getCameraName()
	{
		if(cameraList.size()<=0){
			cameraList.add("CAM-0");
			currentCamera=0;
		}
		
		System.out.println("getCameraName");
		printCameras();

		
		return cameraList.get(currentCamera);
	}
	
	// 現在、使っているカメラの名前を利用可能に設定する
	public  void setCameraName(String s)
	{
		System.out.println("setCameraName 0000");
		printCameras();
		
		if(cameraList.size()<=0){
			cameraList.add(s); // 新規登録
			return;
		}
		// 同じ名前は登録しない
		boolean sw=false;
		for(int i=0;i<cameraList.size();i++){
			if(cameraList.get(i).replace("--remove--", "").equals(s)==false) continue;
			cameraList.set(i,s);// 差し替え
			sw=true;
		}
		if(sw==false) cameraList.add(s); // 新規登録
		
		System.out.println("setCameraName 1111");
		printCameras();

	}
	
	// アイティプランターの名前を取得する
	public  String getPlanterName()
	{
		if(planterName==""){
			planterName="DUMMY_PLANTER";
		}
		return planterName;
	}

	// アイティプランターの名前を設定する
	public  void setPlanterName(String s)
	{
		this.setOldPlanterName(this.planterName);
		this.planterName=s;
	}

	// メモファイルの名前を取得する
	public String getMemoFile() {
		return memoFile;
	}

	// メモファイルの名前を設定する
	public void setMemoFile(String memoFile) {
		this.memoFile = memoFile;
	}
	
	// メモファイルに文字を追加する
	public void addMemo(String s)
	{
		// Memo fileに文字列を書き込む
	}

	// 現在のアイティプランターの番号を取得する
	public int getCurrentPlanterNo() {
		return this.currentPlanterClass.getPlanterNo();
	}

	public void setBIXppFileName(String bIXppFileName) {
		this.bIXppFileName = bIXppFileName;
	}

	public String getBIXppFileName() {
		return bIXppFileName;
	}

	public void setPlantProgram(String plantprogram) {
		plantProgram = new PlantProgram(plantprogram);
		//
	}

	//
	// PlantPrgEdit Classの設定を読み込む
	public PlantProgram getPlantProgram() {
		if(plantProgram==null){
			setPlantProgram(null);
		}
		return plantProgram;
	}

	public String getCameraName(int i) {
		if(i>=cameraList.size()) return null;
		// get i th camera name
		return cameraList.get(i);
	}
	
	public void setCameraName(int i, String s) {
		// get i th camera name
		if(cameraList.size()>i){
	// replace >> set(i, string)
//	System.out.println("InformationClass: cameraList.replace "+i+" "+s);
		cameraList.set(i,s);// replace name
		} else {
		cameraList.add(s); // add new name
//	System.out.println("InformationClass: cameraList.add "+s);
		}
		
		System.out.println("setCameraName--");
		printCameras();
	}

	public void setPlantProgam(PlantProgram p) {
		this.plantProgram=p;	
	}

	public void setWidthCamera(int widthCamera) {
		this.widthCamera = widthCamera;
	}

	public int getWidthCamera() {
		return widthCamera;
	}

	public void setHeightCamera(int heightCamera) {
		this.heightCamera = heightCamera;
	}

	public int getHeightCamera() {
		return heightCamera;
	}
	
	public void setCameraResolution(int w, int h) {
		this.heightCamera =h;
		this.widthCamera = w;
	}

	public String getOldPlanterName() {
		return oldPlanterName;
	}

	public void setOldPlanterName(String oldPlanterName) {
		this.oldPlanterName = oldPlanterName;
	}

	// おしらせメール設定
	public String loadReports() {
		Files.getReports();
		return Reports;
	}

	public String getReports() {
		return Reports;
	}

	public void setReports(String reports) {
		Reports = reports;
	}
	
	public void setTempReport(boolean b) {
		if(b==true){
		if(Reports.contains("Temp")==false) Reports += "Temp";
		} else {
		if(Reports.contains("Temp")==true) Reports = Reports.replace("Temp ", "");	
		}
	}

	public void setWaterReport(boolean b) {
		if(b==true){
		if(Reports.contains("Water")==false) Reports += "Water";
		} else {
		if(Reports.contains("Water")==true) Reports = Reports.replace("Water ", "");	
		}
	}

	public void setIllumReport(boolean b) {
		if(b==true){
		if(Reports.contains("Illum")==false) Reports += "Illum";
		} else {
		if(Reports.contains("Illum")==true) Reports = Reports.replace("Illum ", "");	
		}
	}
	public boolean isTempReport() {
		boolean ans=false;
		if(Reports.contains("Temp")==true) ans=true;
		return ans;
	}

	public boolean isWaterReport() {
		boolean ans=false;
		if(Reports.contains("Water")==true) ans=true;
		return ans;
	}
	public boolean isIllumReport() {
		boolean ans=false;
		if(Reports.contains("Ilum")==true) ans=true;
		return ans;
	}

	public void replacePlanterName(String oPlanterName0, String nPlanterName0) {
		oldPlanterName=oPlanterName0;
		planterName=nPlanterName0;
	}

	
	public void removeCameraName(String name) {
//		このプランターのカメラ名を削除する
			for(int i=0;i<cameraList.size();i++){
			if(cameraList.get(i).equals(name)==true){
//				cameraList.remove(i);// 削除する
				cameraList.set(i,"--remove--"+name);// 削除する
				break;
			}
		}
			
		System.out.println("removeCameraName");
		printCameras();
	}


	public void changeCameraName(String oName, String nName) {
//		このプランターのカメラ名を変更する
			for(int i=0;i<cameraList.size();i++){
			if(cameraList.get(i).equals(oName)==true){
				cameraList.set(i,nName);// 新しい名前を書き込む
				break;
				}
			}
			
			System.out.println("changeCameraName");
			printCameras();
		
	}

	public int findCameraNoByName(String cCameraName) {
//		このプランターのカメラの名前からカメラの番号を返す
		int ir=0;
		for(int i=0;i<cameraList.size();i++){
			if(cameraList.get(i).replace("--remove--", "").equals(cCameraName)==true){
				ir=i;
				break;
			}
		}
		
		System.out.println("findCameraNoByName");
		printCameras();
		
		return ir;
	}

	public  int camMaxNo=0;
	public int getCameraNumberMax() {
		return camMaxNo;
	}

	public void setCameraNumberMax(int n) {
		 camMaxNo=n;
	}

	public  void printCameras() {
		for(int i=0;i<cameraList.size();i++){
			System.out.println("InformationClass ["+i+"]"+" "+cameraList.get(i));
		}
	}

	public ArrayList<String> getCameraNameList() {
		return cameraList;
	}

	public void removeAllCameraName() {
		cameraList.removeAll(cameraList);
	}

	public void addCameraName(String cname) {
		cameraList.add(cname);
	}
}
