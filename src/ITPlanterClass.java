import java.util.ArrayList;

public class ITPlanterClass {
	private static 	ArrayList<PlanterClass> planterList=null;
	private static int 		systemPlanterNo=0;// システムに接続されているプランターの数
	private static int 		systemCameraNo=0;//　システムに接続されているカメラの数
	static int 		    	currentPlanterNo=0;// 現在のプランターの番号
	static PlanterClass 	currentPlanterClass=null;
	private static String 	CloudGardenID="";
	private static String 	CloudGardenPassWD="";
	private static String 	PicasaID="";
	private static String 	PicasaPassWD="";
	private static boolean state=false;
	private static Version  version=null;
	
	public static boolean getState()
	{
		return state;
	}
	
	// 現在、カレントになっているプランター番号を得る
	public static int getCurrentPlanterNo()
	{
		return currentPlanterNo;
	}

	// 現在、カレントになっているプランター番号を得る
	public static void setCurrentPlanterNo(int i)
	{
		currentPlanterNo=i;
	}
	
	public static void setCurrentPlanterClass(PlanterClass planterClass) {
		currentPlanterClass=planterClass;
	}

	// 現在、システムに繋がっているカメラの数を設定する
	public static void setSystemCameraSize(int i)
	{
		systemCameraNo=i;
	}


	// 現在、システムに繋がっているプランターの数を設定する
	public static void setSystemPlanterSize(int i)
	{
		//systemPlanterNo=i;
	}
	
	// 現在、システムに繋がっているプランターの数を得る
	public static int getSystemPlanterSize()
	{
		// 
		// 新たにプランターが接続されていないか確かめる。
		if(systemPlanterNo <= 0) systemPlanterNo=getNplanter();
		//
		return systemPlanterNo;
	}
	
	
	public  static PlanterClass getCurrentPlanterClass()
	{
			return currentPlanterClass;
	}
	
	// クラウド・ガーデンの情報を入力する
	public static void setCloudGardenInfo(String ID, String PassWD)
	{
		CloudGardenID=ID;
		CloudGardenPassWD=PassWD;
	}
	
	public static String getCloudGardenID()
	{
		return CloudGardenID;
	}
	
	public static String getCloudGardenPassWD()
	{
		return CloudGardenPassWD;
	}
	
	public static void setPicasaInfo(String ID, String PassWD)
	{
		PicasaID=ID;
		PicasaPassWD=PassWD;
	}
	
	public String getPicasaID()
	{
		return PicasaID;
	}
	
	public static String getPicasaPassWD()
	{
		return PicasaPassWD;
	}
	
	// Constructor
	public ITPlanterClass()
	{
		ITPlanterClassBegin();
	}
	
	
	public static void ITPlanterClassBegin()
	{
		
		// 二重起動しない
		if(state==false){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(state==true) return;
		state=true;
		
		version=new Version();
//System.out.println("Constractir planter");		
		// 格納メモリを確保
		planterList=new ArrayList<PlanterClass>();
		//	USBに接続されたアイティプランターの数を調べる
		
		//systemPlanterNo=getNplanter();
		systemPlanterNo=getSystemPlanterNumber();
System.out.println("planter="+systemPlanterNo);

		int spn=systemPlanterNo;
		
		if(spn==0){
			planterList.add(new PlanterClass("DUMMY_PLANTER"));
System.out.println("ダミープランターをプランターリストに登録 DUMMY_PLANTER");
		} else {
		// 無償版では１台のアイティプランターしか認識しない
		if(Version.getFreeOrPaid().contains("Free")==true) spn=1;
		
		//  プランターリストに登録
		for(int i=0;i<spn;i++){
			planterList.add(new PlanterClass("ITPLANTER-"+i));
System.out.println("プランターリストに登録 ITPLANTER-"+i);
			}
		}
// BUG : 別プロセスが systemPlanerNoを０にしてしまう?
//	System.out.println("systemPlanterNo 1= "+systemPlanterNo);
		systemPlanterNo=spn;
//	System.out.println("systemPlanterNo 2= "+systemPlanterNo);
	
		//　USBに接続されたカメラの数を調べる
		systemCameraNo=1;// default
	    // if there are no camera entry then DUMMY_CAM should be entry.
		String oldCamName="CAM-0";
		PlanterClass p=getPlanterList().get(ITPlanterClass.getCurrentPlanterNo());
		Information inf=p.getInformation();
		inf.setCameraName(0, oldCamName);

		
		// デフォルトのカレントを設定する
		currentPlanterNo=0;
		currentPlanterClass=planterList.get(0);
		
//		System.out.println("systemCamNo="+systemCameraNo);
		//
//		state=true;// ITPlanterClass can be used.
	}
	
	// システムに繋がっているカメラの数を返す
	public static int getSystemCameraSize()
	{
		return systemCameraNo;
	}

	

	// PlanterListを取得する
	public static ArrayList<PlanterClass> getPlanterList()
	{
		
		return planterList;
	}
	
	private static boolean status=false;
	// PCに接続されているアイティプランターの数を調べる。
	// USBの状態が変化したら調べ直すようにすること
	
	public static void reSearchPlanter()
	{
		status=false; // reSearch Planter No
	}
	
	public static int getSystemPlanterNumber()
	{
		String result="";
		
		if( status==false ){// statusがfalseの時だけ、プランターの応答を調べる
			
		result="";
		result = sendCom.sendcom("-e %"); // sendcom
		System.out.println("getSystemPlanterNumber result : "+result);
		status=true;
		if(result==null){
			planterList.removeAll(planterList);
			systemPlanterNo=0;
System.out.println("getSystemPlanterNumber nPlanter "+systemPlanterNo);
			return 0;
			} else {
//				System.out.println(result);
				if(result.contains("error")==false){
			systemPlanterNo= Integer.parseInt(result.replaceAll("[^0-9]",""));
System.out.println("getSystemPlanterNumber nPlanter "+systemPlanterNo+" "+result);
				return systemPlanterNo;
				}
		String[] arrn=result.split(System.getProperty("line.separator"),0);
		if(arrn[0]==""){
			System.out.println("プランターが見つからないか応答がありません。\nプランターの電源をいれ直してみてください。");
			itp_Logger.logger.info("can not find planter");
			return 0;
				}
			}	
		} 
//		systemPlanterNo=2;
		return systemPlanterNo;
	}
	
	// DUMMYPLANTERも含めて、いくつのプランターがあるかを返す。実際に接続されているプランターの数ではない。
	public static int getNplanter()
	{
		return planterList.size();
	}
	
	public static void removePlanter(int i)
	{
		planterList.remove(i);
	}
	
	//public static  void reMakePlanterList() {
		/*
		//PlanterList remaking
		//systemPlanterNo=getNplanter();
		//System.out.println("Planter No="+systemPlanterNo);
		//systemPlanterNo=getSystemPlanterNumber();
		int spn=systemPlanterNo;
//		if(spn>planterList.size()) planterList.removeAll(planterList);
		//System.out.println("size="+planterList.size());
		
		//
System.out.println("reMakePlanterList spn="+spn);
		//  もしUSBに接続されたアイティプランターが１台もなければダミープランターを１台、登録する
		if(spn==0){
			if(planterList.size()>0)
			planterList.set(0,new PlanterClass("DUMMY_PLANTER"));
			else
			planterList.add(new PlanterClass("DUMMY_PLANTER"));
		} else {
		//  プランターリストに登録
				for(int i=0;i<spn;i++){
					if(spn > planterList.size()){
					planterList.set(i,new PlanterClass("ITPLANTER-"+i));
					} 
			}
		}
		*/
		
	//}



	
}
