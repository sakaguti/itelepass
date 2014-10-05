//
//  itplanter class, this is main class
//	itplants.ltd.	2011
//	Yosiyuki SAKAGUCHI
//
//
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Calendar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Timer;
import java.util.TimerTask;


public final class CloudGarden extends Thread  {
//
//public static int version_Majer = 2;//互換性があることを保証する。
//public static int version_Miner = 0;//バージョンアップ
public static int version_Majer = 1;//互換性があることを保証する。
public static int version_Miner = 0;//バージョンアップ

static boolean running = true; // for thread control

static String sensorInfo="";

// Planter Name
public static ArrayList<Integer> planterSerial= new ArrayList<Integer>();
public static ArrayList<Integer> planterVersion= new ArrayList<Integer>();
public static int currentPlanterNo=1;


public static itpCom itpCom1=null;
//
public static void main(String[] args) {
	new ITPlanterClass();
	CloudGarden cg=new CloudGarden();
	cg.initial_step();
	cg.itplanter_run();
}


public CloudGarden()
{
//	System.out.println("SetupStream");
	sensorInfo="S_LAMP 1 S_PUMP 0:PTime 0\r\n";
	sensorInfo += "Temp 0\r\n";
	sensorInfo += "Water 0\r\n";
	sensorInfo += "illum 0\r\n";
}

// get time
public static String getTimeDate()
{
	String day=String.valueOf(Calendar.getInstance().getTime());
	String[] timedate=null;
	timedate=day.split(" ");
	String[] time=null;
	time=timedate[3].split(":");
	return time[0]+" "+time[1]+" "+time[2];
}

    
private static 	int nPlanter=-1;//
// PCに接続されているアイティプランターの数を調べる。
public static int get_nPlanter()
{
	nPlanter=ITPlanterClass.getSystemPlanterNumber();
	return nPlanter;
}


//一定時間ごとにアイティプランターの状態をListenerに知らせる。
public static void planter_status() 
{// run
//	while( itpCom1.messageUse==false ); // wait
	
	itp_Logger.logger.info("update planterStatus");
	

	/// PRT
	String rmessage = "RPT\r\n";
	rmessage += "Name: "+getPlanterName(ITPlanterClass.getCurrentPlanterNo()-1);
	ITPlanterClass.getCurrentPlanterClass().getSensor();
	rmessage += "Temperature: " +ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorTemprarure()+" \\% " +Sensor.getTemperatureWarningLevel()+"\r\n";// get_TempWarnFlag	
	ITPlanterClass.getCurrentPlanterClass().getSensor();
	rmessage += "WaterLevel: "  +ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorWaterlevel()+" \\% "+Sensor.getWaterWarningLevel()+"\r\n";// get_WaterWarn	
	ITPlanterClass.getCurrentPlanterClass().getSensor();
	rmessage += "Illumination: "+ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorIllumination()+" "  +Sensor.getIlluminationWarningLevel()+"\r\n"; // get_illumWarn	
	rmessage += "\r\n";

	System.out.println("Message "+rmessage);
	itpCom1.say(rmessage);
	itpCom1.listen();
	}


// ログインの手続きを行う
public static void login() 
{
		CloudGarden itp=new CloudGarden();
		
		itp.Login_processing();
		
		itp.VER_processing();
		
//		itp.SPL_processing();

		itp.PLT_processing();
}

// server(Listener)に登録されているプランターの名前のリスト
public static ArrayList<String> server_list = new ArrayList<String>();
//設定ファイル（planters.txt）に登録されているプランターの名前のリスト
public static ArrayList<String> setting_list = new ArrayList<String>();


// serverに登録されているプランターのリストを調べる
/*
private  void SPL_processing() {
	String message;
	String[] er=null;

	message = "SPL\r\n\r\n";
	itpCom1.say(message);
	String[] ms=itpCom1.listen().split("\r\n");
			er=ms[0].split(" ");
			String[] nOK=er[0].split(" ");
			if(nOK[0].compareTo("100")!=0){
				// Message: 100 OK が帰ってきていないのでエラー
				return;
			}
			String[] amount=ms[1].split(" ");
			// Amount: 2
			
			int server_pn=Integer.parseInt(amount[1].replaceAll("[^0-9]",""));
			if(server_pn == 0 ){
				//プランターが未登録のとき、新規登録する。
				REN_processing();
			} else {
				// プランターが登録されていたとき
				String[] serverPlanterName=new String[server_pn];
				String[] sPNbuff=null;
				for(int i=2,j=0;i<server_pn*2;i+=2){
				// Name: planterA
				sPNbuff = ms[i].split(" ");// spaceで分割する
				if(sPNbuff[0].compareTo("Name:")==0){// Name:
						serverPlanterName[j++]=sPNbuff[1];// planterA
						server_list.add(sPNbuff[1]);
				}
				// ここでVersion / Serial を読み込む
				// Todo
				}
				// 設定ファイルを読込む
				//Planter Name reader
				String pname="";
				String pdata=Files.getPlantersfile();
				// 設定ファイルからプランターの名前を読む。
					if(pdata.compareTo("")!=0){
						String[] pd=pdata.split(System.getProperty("line.separator"));
						String[] pp=null;
					// Name set
					for(int i=0;i<pd.length;i++){
					pp = pd[i].split("Planter ");
					if(pp.length>=2){
						pname += (pp[1]+" ");
						setting_list.add(pp[1]);
							}
						}
					} else {
						// プランター名設定ファイルが空。設定してもらわないといけない。
					}
					
					String[] planterName=null;
					//
					planterName=pname.split(" ");
					
					
					//
					if(Arrays.equals(planterName, serverPlanterName)){
						// サーバーに登録されているプランター名と設定ファイル内のプランター名は完全一致している。
						System.out.println("Match!!");
						return;
					}
					// サーバーの登録リストから、設定ファイルにあるプランター名を削除する。
					for(int i=setting_list.size()-1;i>=0;i--){
					while(server_list.remove(setting_list.get(i))){}
					}

					if(get_nPlanter()>0){
					// サーバーに登録されているプランターの数と設定ファイルに登録されているプランターの数が同じ場合には、
					// サーバーに登録されているプランターの名前を設定ファイルにある名前に変更する。
					if(server_list.size() == setting_list.size()){
					for(int i=0;i<server_list.size();i++){
						//	Rename			
						message = "REN\r\n";
						message += "OldName: "+server_list.get(i)+"\r\nNewName: "+setting_list.get(i)+"\r\n";
						message += "\r\n";
						itpCom1.say(message);
						er=itpCom1.listen().split(" ");
						if(er[0].compareTo("100")==0 ){
							// 新規に登録されました。
							itp_Logger.logger.info( setting_list.get(i)+"が新規に登録されました。");
							} else {
							// Error
								// エラーは無視する
							}
					}
					} else if(server_list.size() != setting_list.size()){{
					// サーバーに登録されているプランターの数と設定ファイルに登録されているプランターの数が異なる場合には、
					// とにかく新規登録する
					for(int i=0;i<setting_list.size();i++){
							//	Rename			
							message = "REN\r\n";
							message += "OldName:\r\nNewName: "+setting_list.get(i)+"\r\n";
							message += "\r\n";
							itpCom1.say(message);
							er=itpCom1.listen().split(" ");
							if(er[0].compareTo("100")==0 ){
								// 新規に登録されました。
								itp_Logger.logger.info( setting_list.get(i)+"が新規に登録されました。");
								} 
							//else {
							// Error
							// エラーは無視する
							//	}
						}//
					}
				}
			} else {
				// planter = 0
				message = "REN\r\n";
				message += "OldName:\r\nNewName: dummyPlanter\r\n";
				message += "\r\n";
				itpCom1.say(message);
				er=itpCom1.listen().split(" ");
				if(er[0].compareTo("100")==0 ){
					// 新規に登録されました。
					itp_Logger.logger.info("dummyPlanterが新規に登録されました。");
					} else {
					// Error
						// エラーは無視する
					}
			}
		}
}
*/

// IDとPasswdを送信して、クラウド・ガーデンにログインする。
public   void Login_processing() 
{
	String username;
	String passwd;
	String message = null;
	char[]	cline=null;
	
    {// Login_processing
	String data = Files.readFile(Files.getSetupfile());
	
	String[] arg=null;
	arg=data.split(System.getProperty("line.separator"));
	
	if(arg.length<2){
		//
		// Error
		//JOptionPane.showMessageDialog(null, "ログイン情報を設定してください。");
		System.out.println("Set Login Information");
		return;
	}
	username = arg[0].replace("\r", "");

    //複合化(noで指定文字数マイナスする[元に戻す])
	passwd="";
    for(int i=0; i<arg[1].length(); i++){
        passwd = passwd + String.valueOf((char)(arg[1].charAt(i) - 3));
    	}
    passwd = passwd.replace("\n", "");
	// Login
	//メッセージの送信処理
		message="HELO\r\n\r\n";
		itpCom1.say(message);
		
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		cline=itpCom1.listen().toCharArray();
		itp_Logger.logger.info(String.valueOf(cline));

		String[] arr=String.valueOf(cline).split(" ",0);
		if(arr.length!=3){
			// Error
			itp_Logger.logger.info("Cloud-Garden Serverが応答しないので、終了します。");
			System.exit(1);
			return;
		}
		String[] arr2=arr[2].split("\r\n",0);
		if (arr2.length < 1) {
			return;
		}
	    passwd = sha256(passwd+"cgdn");
	    passwd = sha256(passwd+arr2[0]);
	    

		message = "AUTH\r\n";
		message += "User: "+username+"\r\n";
		message += "Pass: "+passwd+"\r\n\r\n";
		itpCom1.say(message);
		String[] er=itpCom1.listen().split(" ");
		
	//	itp_Logger.logger.info(message);
		//Message: 200 Error (please, Input User and Pass)
		
		if(er[1].compareTo("Error")==0){
//			JOptionPane.showMessageDialog(null, "ログインできません。");
			System.out.println("ログインできません。");
			// 
			LoginSetting itpg=new LoginSetting();
            itpg.setFocusableWindowState(true);
            itpg.LoginSetting_run();
            itpg.setVisible(true);
			return;
			}
		}
}
// アイティプランターのVERSIONを送る。
public void VER_processing() 
{
	String message;
	{// VER_processing
	String[] er=null;
	message = "VER\r\n";
	// 10.15 y.s  2011/10現在のアイティプランターのVERは０のみ。
	
	
	String result = sendCom.sendcom("1 -e v");
	String[] rp=result.split(System.getProperty("line.separator"));
	String[] vn=rp[1].split(" ");
	String[] vr=vn[1].split("\\.");
	//
	if(vr.length>1){
	vr[0]=vr[0].replace("\r", "");
	vr[0]=vr[0].replace("\n", "");
	vr[1]=vr[1].replace("\r", "");
	vr[1]=vr[1].replace("\n", "");
	//
	version_Majer = Integer.valueOf(vr[0]);
	version_Miner = Integer.valueOf(vr[1]);
	//
	}
	
	
	message += "Manager: "+version_Majer+"."+version_Miner+"\r\n\r\n";

	itpCom1.say(message);
	er=itpCom1.listen().split(" ");
	if(er[0].compareTo("111")==0 ){
//		JOptionPane.showMessageDialog(null, "新しいitpManagerがあります。"+System.getProperty("line.separator")+"アップデートしてください。");
		System.out.print("新しいitpManagerがあります。"+System.getProperty("line.separator")+"アップデートしてください。");
		}
	}
}

// Cameraが付いているかどうかを調べる。
public static ArrayList<Boolean> hasCamera=new ArrayList<Boolean>();
public static void setCamera(boolean p)
{
	hasCamera.add(p);
}

public boolean getCamera(int p)
{
	return hasCamera.get(p);
}

//serialを調べる。
public static ArrayList<Integer> isSerial=new ArrayList<Integer>();
public static void setSerial(int p)
{
	isSerial.add(p);
}

public Integer getSerial(int p)
{
	return isSerial.get(p);
}

//versionを調べる。
public static ArrayList<String> isVersion=new ArrayList<String>();
public static void setVersion(String p)
{
	isVersion.add(p);
}

public String getVersion(int p)
{
	return isVersion.get(p);
}

//Planter Nameを調べる。
public static ArrayList<String> isPlanterName=new ArrayList<String>();
public static void setPlanterName(String p)
{
	isPlanterName.add(p);
}

public static String getPlanterName(int p)
{

 		if(isPlanterName.size()-1 >= p){	
 			if(p>=0){
 				itp_Logger.logger.info(isPlanterName.get(p)+" "+p);
 				return isPlanterName.get(p);
 			}
		}
 		if(isPlanterName.size()==0){
		itp_Logger.logger.info(isPlanterName.size()+" "+p);
 		}
		return("dummyPlanter");
}

//Planter Numberを調べる。
public static ArrayList<Integer> isPlanterNumber=new ArrayList<Integer>();
public static void setPlanterNumber(int p)
{
	isPlanterNumber.add(p);
}

public int getPlanterNumber(int p)
{
	return Integer.valueOf(isPlanterNumber.get(p));
}

static ArrayList<Integer> camNoList = new ArrayList<Integer>(); 
private static void setCameNo(String v) {
	itp_Logger.logger.info("setCameNo:"+v);
	camNoList.add(Integer.parseInt(v.replaceAll("[^0-9]", "")));
}

private static Integer getCameNo(int pn) {
//	itp_Logger.logger.info("getCameNo:"+pn);
		return camNoList.get(pn);
}

public void sendREN() {
	// プランター設定ファイルが変更されたのでRENを実行する。
	{// sendREN_processing
//			Planter Name reader
		int iren=0;
		String[] pd=null;
		String[] pp=null;
		String pdata=Files.getPlantersfile();
		// 
		if(pdata.compareTo("")!=0){
			pd=pdata.split(System.getProperty("line.separator"));

		// Name set
		for(int i=0;i<pd.length;i++){
			if(pd[i].contains("Planter") == false) continue; // not Planter tag
			
			pp = pd[i].split("Planter ");			
			if(pp.length<2) continue;
				
			if(setting_list.size() < i) continue; // これは追加だ！！
			
			if(setting_list.contains(pp[1])) continue;// 名前の変更はない
			
			//　名前の変更があった
			//	Rename			
			String message = "REN\r\n";		
			// serverに登録されていない
			message += "OldName: "+getPlanterName(i)+"\r\nNewName: "+pp[1]+"\r\n\r\n";
			itp_Logger.logger.info("sendREN \r\n|"+message+"|");
			itpCom1.say(message);
			String er=itpCom1.listen();
			//itp_Logger.logger.info(er);
			if(er.contains("100 OK")){
				// 新規に登録されました。 100 OK
				//JOptionPane.showMessageDialog(null, planterName[i]+"が新規に登録されました。");
				itp_Logger.logger.info(getPlanterName(i)+"が"+pp[1]+"に変更されました。");
				iren++;
				if(iren > get_nPlanter()) break; // no more REN
				} else if(er.contains("203 Error Unknown Planter Name")){
				// 203 Error Unknown Planter Name
								itp_Logger.logger.info("sendREN ERROR: |"+er+"|");
								return;
							}else if(er.contains("203 Error Unknown Planter Name")){
				// 204 Error Bad Name
								itp_Logger.logger.info("sendREN ERROR: |"+er+"|");
								return;
								}
							} // next i
			}// 
		
		// re-read planter file

		// 設定ファイルからプランターの名前を読む。
				setting_list.clear();
				pd=pdata.split(System.getProperty("line.separator"));
			// Name set
			for(int i=0;i<pd.length;i++){
				pp = pd[i].split("Planter ");
			if(pp.length>=2){
				setting_list.add(pp[1]);
					}
				}
		}
}

// プランターの名前を変更する。現在は、新規登録するだけ。
public static void REN_processing() 
{

	{// REN_processing
		int nPlanter=get_nPlanter();//
//			Planter Name reader
		String pname="";
		String pdata=Files.getPlantersfile();
		// 
		if(pdata.compareTo("")!=0){
			String[] pd=pdata.split(System.getProperty("line.separator"));
			String[] pp=null;
		// Name set
		for(int i=0;i<pd.length;i++){
			pp = pd[i].split("Planter ");
		if(pp.length>=2) pname += (pp[1]+" ");
		}
		
		String[] planterName=null;
		itp_Logger.logger.info(pname);
		
		planterName=pname.split(" ");
	
	// plantern_memo.txt の中で定義されているプランターの名前を使う。
	// Name set
	for(int i=0;i<nPlanter;i++){
	String[] er=null;
	//	Rename			
	if( server_list.contains(planterName[i])==false){
	String message = "REN\r\n";
	
	// serverに登録されていない
	message += "OldName:\r\nNewName: "+planterName[i]+"\r\n";	
	
	message += "\r\n";
	//itp_Logger.logger.info(message);
	itpCom1.say(message);
	er=itpCom1.listen().split(" ");
	//itp_Logger.logger.info(er);
	if(er[0].compareTo("100")==0 ){
		// 新規に登録されました。
		//JOptionPane.showMessageDialog(null, planterName[i]+"が新規に登録されました。");
		itp_Logger.logger.info(planterName[i]+"が新規に登録されました。");
					} else {
		itp_Logger.logger.info(planterName[i]+"が新規に登録されませんでした。");				
					}
				}
			}// next i
		} else {
		//プランター名が登録されていない
			itp_Logger.logger.info("Planter情報がありません。");
			// PlanterSetting() を表示するようにTaskTrayに伝える。
		}
	}// End of REN processing
}

public String initial_step()
{
	String[] parms=null;
	String ret=null;
	// 設定ファイルを読み込む
	String s=Files.getSetupfile();
	if( s == null ){
		// ERROR
		System.out.println("設定ファイルが見つかりません。^"+s);
		// LoginSettingWindowを表示すること
		 return null;
	}
	// ID, Passwd, Planter Name を読み込むこと。
	String[] arrg=s.split(System.getProperty("line.separator"),0);
	if( arrg.length < 2){
		//JOptionPane.showMessageDialog(null, "ログイン情報が設定されていません。");
		// LoginSettingWindowを表示すること
        return null;
	} 
		//　戻り値作成
		ret=arrg[0];
		ret += " ";
		String pw="";
	      //複合化(noで指定文字数マイナスする[元に戻す])
	      for(int i=0; i<arrg[1].length(); i++){
	          pw = pw + String.valueOf((char)(arrg[1].charAt(i) - 3));
	      }
		//itp_Logger.logger.info(pw);
		ret+=pw;
		ret += " ";		

		for(int i=2;i<arrg.length;i++){
			// Name
			// Planter planter1
			parms=arrg[i].split(" ");
			if(parms.length == 1 )	ret+= (parms[0]+" ");
			else	ret+= (parms[1]+" ");
		}
	return ret;
	
}


//
// main loop
public void itplanter_run()
{
	String fromListener=null;
		
	// for upload image to Picasa
	// String[] s=CameraCapture.picasa_fread().split(System.getProperty("line.separator"));
	
	// set planter name
	serial_set();
	
	String[] arr=null;//
	
	// open com port
	itpCom1=new itpCom();
	// Open Stream
	// start com
	itpCom1.start();
	
	// Login
	login();
	
	// 撮影スケジュールを設定する。
	setCamSchedule();

	// 時間が来たら実行する処理
	planter_status_processing();// update planterStatus by 60 min
	
	// 生きていることを知らせる。WiFiが切断されないようにしている。
	ALV_processing();
	
	// 無限Loop
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(running){// loop
			String command = "";
			
			command="";
			try {
				if(br.ready()==true){
				   try {
						String str = br.readLine();
						System.out.println(str);
						command = str;
						itpCom1.say("CMD\r\n"+"Type:"+command+"\r\n\r\n");
						System.out.println("Result"+itpCom1.listen());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			
			//	
			// Listenerからの要求に対応する部分の開始
			// Listen command
			
			fromListener=itpCom1.listen();
				if(fromListener==null){
					try {
						sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
					continue;
				}
				
//			System.out.println("Listner:"+fromListener);
			
			if(fromListener.length() > 0){//
				// Listenerからの改行コードはいつも、"\r\n\r\n"になっている。
				// System.getProperty("line.separator")は使ってはいけない。
			arr=fromListener.split("\r\n",0);
			//itp_Logger.logger.info(arr[0]);
			// カメラで撮影する
			if( arr[0].compareTo( String.valueOf("SHT")) == 0){
				// Camera Command Processing
				camera_processing(arr);//
				//
			} 
			// カメラ撮影のタイムテーブル
			else if( arr[0].compareTo( String.valueOf("TPT")) == 0){
				// Camera Command Processing
				//capture.cameta_time_table(arr);//
				TPT_processing(arr);
				//
			}
			// センサーの一括取得
			else if( arr[0].compareTo( String.valueOf("RSI")) == 0){
				// senser Processing
				RSI_processing(arr);//
				//
			} 
			// プランター名の変更要求
			else if( arr[0].compareTo( String.valueOf("CHN")) == 0){
				// change planter name
				CHN_processing(arr);//
				//
			} 
			// EEPROM内容の読み出し
			else if( arr[0].compareTo( String.valueOf("GPG")) == 0){
				// EEPROM Read Processing
				GPG_processing(arr);//
				//
			} 
			// EEPROM内容の設定
			else if( arr[0].compareTo( String.valueOf("SPG")) == 0){
				// EEPROM Save Processing
				SPG_processing(arr);//
				//
			} 
			// コマンドの発行
			else if( arr[0].compareTo( String.valueOf("CMD")) == 0){
				CMD_processing(arr);
				//
			}  
			// 栽培プログラムののセット
			else if( arr[0].compareTo( String.valueOf("SCP")) == 0){
			// Pump schedule setting
				SCP_processing(arr);
				
			}  else if( arr[0].compareTo( String.valueOf("SCL")) == 0){
			// Lamp schedule setting
				SCL_processing(arr);
			} 
			// ここまで栽培プログラムのセット
			//
			// 終了処理
			else if( arr[0].compareTo( String.valueOf("BYE")) == 0){
				try {
					bye_processing();
				} catch (IOException e) {
					e.printStackTrace();
				}
				running=false;//
				}			
			}// Listenerからの要求に対応する部分の終わり
			
			
			// プロセスを空ける
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
				
			//	itp_Logger.logger.info("CLS");
			}// while loop
		
			itp_Logger.logger.info("CLS");
			
			try {
				sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			// Say Bye to Server
			itpCom1.say("CLS\r\n\r\n");
			
			try {
				sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		//終了処理
			itpCom1.stop_thread();
			
			for(int i=0;i<10000;i++){
				if(itpCom1.get_RunEndFlag()==true) break;// wait for thread finish 
				try {
					sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
				
			try {
				itpCom1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	

		itp_Logger.logger.info("itpManager is terminated.");

		return;
		//running=true;
		
	//JOptionPane.showMessageDialog(null, "itplanter is terminated.");
}


//PCにつながっているプランターのリストを送る。
private  void PLT_processing() 
{
	String message="";
	int pn=0;
	{// PLT_processing
		// プランターの設定ファイルを読み込む
	String pdata=Files.readFile(Files.getPlantersfile());
	// 行毎に分ける
	String[] pd=pdata.split(System.getProperty("line.separator"));

	// CamNo　を読む
	for(int i=0;i<pd.length;i++){
	//	itp_Logger.logger.info("pd["+i+"]="+pd[i]);
		String[] pp=pd[i].split(" ");
			if(pp[0].compareTo("CamNo")==0){
				if(pp.length>=2){
						setCameNo(pp[1]);
					}
					else{
						// error
					}
			}

		if(pp[0].contains("Planter")==true && pp[1].contains("DUMMY_PLANTER")==true) pn=0;
		if(pp[0].contains("Planter")==true && pp[1].contains("DUMMY_PLANTER")==false) pn++;
		if(get_nPlanter()==0) setPlanterName("DUMMY_PLANTER");
		if(pn==0) continue;
		// Name set

		int k=1;// 新しいプランターの番号
			if(pp[0].contains("Planter")==true ){
			if(pp.length>=2){
			setPlanterName(pp[1]); // set Planter Name
			setPlanterNumber(k-1);
			} 
		}
	}
	
	
	// REN
	message = "REN\r\n";
	message += "OldName:\r\n";
	message += "NewName: "+getPlanterName(0)+"\r\n\r\n";
	
	itpCom1.say(message);
	String mss=itpCom1.listen();//
	System.out.println(mss);
	
	// PLT
	/*
	message = "PLT\r\n";
	message += "Amount: 1\r\n";
	message += "Name: itplanter-0\r\n";
	message += "Number: 1 / Version: 1.0 / Serial: 0 / Camera: 1\r\n";
	message += "\r\n";
    message += "Amount: "+get_nPlanter()+"\r\n";//　つながっているプランター
	*/
	// 設定ファイルにあるプランターと実際につながっているプランターとの比較をすること。
	// 実際につながっているプランターだけサーバーに送るようにすること。

	if(get_nPlanter()==0){
		message = "PLT\r\n";
		message += "Amount: 1\r\n";//　つながっているプランター
		message += ("Name: DUMMY_PLANTER\r\n");
		message += ("Number: 0 / Version: 2.10 / Serial: 1234567890 / Camera: 1\r\n");// Planter Number	
		message += "\r\n\r\n";
	//	itp_Logger.logger.info("dummy planter "+message);	
		itpCom1.say(message);
		itpCom1.listen();
//		itp_Logger.logger.info("dummy planter "+c);//
		return;
	} else {

	// プランターの総数は、設定ファイルに書かれているものと異なる場合があるので、実際につながっているプランターの数にする。

	/*----*/
		message="";
		message = "PLT\r\n";
		message += "Amount: "+get_nPlanter()+"\r\n";


			message += ("Name: "+getPlanterName(0)+"\r\n");
			message += ("Number: "+(getPlanterNumber(0)+1));// Planter Number
			//
			// devcontrol.cloud-garden.comはVersion 2.Xに対応している。
			//
			if(itpCom.hostname.contains("devcontrol")==false)
			message += (" / Version: 1.1 ");// Planter Number
			else
			message += (" / Version: 2.10 ");// Planter Number
			}
	

				String result = sendCom.sendcom("1 -e Z");// pn ??
				String[] serial=result.split(System.getProperty("line.separator"));
				serial[1]=serial[1].replace("\r", "");
				serial[1]=serial[1].replace("\n", "");
					
					message += (" / Serial: "+serial[1]+" ");
					System.out.println("Serial[1]="+serial[1]);
					Integer ii=Integer.parseInt(serial[1].replaceAll("[^0-9]",""));
					setSerial(ii);

					message += ("/ Camera: "+(getCameNo(0)+1)+"\r\n");
		}

/*-----*/
	
	message += "\r\n\r\n";
	
	itp_Logger.logger.info(message);
	
	itpCom1.say(message);
	String mss=itpCom1.listen();//
	//itp_Logger.logger.info("ms="+ms);
	
	String[] er=mss.split(" ");
	if(er[0].compareTo("205")==0){
		String[] c;
		c=mss.split("\r\n");
		// 期待したエラーメッセージではない
		if(c[0].compareTo("Amount:")!=0){
			System.out.println("Server Error：期待したエラーメッセージではない");
			itp_Logger.logger.info("cloud garden error");
			return;
		}
			// 知らない名前のプランターがあるとき
			//JOptionPane.showMessageDialog(null, c);
			itp_Logger.logger.info("unknown planter name "+c);
			}
}

private void CHN_processing(String[] arr) {
	
	// CHN	:	arr[0]="CHN"
	// OldName: planterA
	// NewName: planterB
	String oname=(arr[1].split("OldName: "))[1];
	String nname=(arr[2].split("NewName: "))[1];
	//
	itp_Logger.logger.info(arr[0]+" "+oname+" "+nname);
	if(isPlanterName.contains(oname)){
		isPlanterName.remove(isPlanterName.indexOf(oname));
		isPlanterName.add(nname);
	}
	
	// 
	// planter.txt のプランター名を変更する。
	
	// full path need.
	// 設定ファイルを読込む
	//Planter Name reader

	String pdata=Files.getPlantersfile();
	// 設定ファイルからプランターの名前を読む。
		if(pdata.compareTo("")!=0){
			// プランターの名前の書き換え
			itp_Logger.logger.info("1 "+pdata);
			pdata=pdata.replace(oname, nname);//
			itp_Logger.logger.info("2 "+pdata);
		} else {
			// プランター名設定ファイルが空。設定してもらわないといけない。
			itpCom1.say("400 Error\r\n\r\n");
			return;
		}
		
		byte[] b = pdata.getBytes();// 保存するデータ
		File f = new File(Files.getPlantersfile());
		try 
		{	
			FileOutputStream fo= new FileOutputStream(f);
			fo.write(b);	
			fo.close();
			itpCom1.say("300 OK\r\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			itpCom1.say("400 Error\r\n\r\n");
		}
	//
}


private void setCamSchedule() {
	String[] arr=null;
	int cp=currentPlanterNo;// pop
	
	for(int i=1;i<get_nPlanter()+1;i++){
		// find camShedule.txt file
		// full path need.
		sampleReplace sr=new sampleReplace();
		String filename=getPlanterName(i-1);// planterName start from 0
//		itp_Logger.logger.info("setCamSchedule 1 "+filename);
		filename=sr.planterSchedulefile(filename);
//		itp_Logger.logger.info("setCamSchedule 2 "+filename);
		
	try 
	{	
		File f = new File(filename);
		itp_Logger.logger.info("setCamSchedule "+filename +" has loaded");
		byte[] b = new byte[(int) f.length()];
		String	s = "";
		FileInputStream fi = new FileInputStream(f);
		fi.read(b);	
		s = new String(b);	
		fi.close();
		
		arr=s.split(System.getProperty("line.separator"));
		
		currentPlanterNo=i;
		// Camera Command Processing
		//capture.cameta_time_table(arr);//
		TPT_processing(arr);	
	}
	catch(Exception e)
	{
		//e.printStackTrace();
		itp_Logger.logger.info("no schedule file found "+filename);
		}
	}
	currentPlanterNo=cp;// pop
	//
}

private void SPG_processing(String[] arr) {
	// EEPROMに値を格納する。
	// ToDo sakaguti 11.27
	//arr[0] SPG
	//arr[1] Temperature: 25
	//arr[2]  WaterLevel: 50%
	//arr[3]  Illumination: 1200
	//arr[4]  Frequency: 128
	//arr[5]  Pwm: 200
	//arr[6]  LumpAmount: 2
	//arr[7]  Time: 420-5
	//arr[8]  Time: 720-10
	//arr[9]  PumpAmount: 1
	//arr[10]  Time: 420
	itpCom1.say("300 OK\r\n\r\n");
}

private void GPG_processing(String[] arr) {
	// ToDo sakaguti 11.27
	// READ EEPROM DATA and Pars it
	// 警告温度(℃)
	// 警告水位(%)
	// 警告照度(lux)
	// ランプ周期 (1～255)
	// デューティ値 (0～1023)
	// ランプスケジュール行数
	// 開始時刻(分)-継続時間(分)
	// (1日を 0～1439分で表す)
	// ポンプスケジュール行数
	// 開始時刻(分)
	// (1日を 0～1439分で表す)
	
	itpCom1.say("300 OK\r\n\r\n");
}


private void RSI_processing(String[] arr) {
	// z
	// A
	// B
	// F
	itpCom1.say(sensorInfo);
	itp_Logger.logger.info("RSI:"+System.getProperty("line.separator")+sensorInfo);
	
	if(get_nPlanter()==0){
		// 現在のプランター番号は　何番か？
	
		sensorInfo="S_LAMP 1 S_PUMP 0:PTime 0\r\n";
		sensorInfo += "Temp 0\r\n";
		sensorInfo += "Water 0\r\n";
		sensorInfo += "illum 0\r\n";

	} else {
	//s  =  	 	   planterStatus.get(currentPlanterNo-1).get_planterStatus()+"\r\n";
		String result = sendCom.sendcom((1)+" -e z");
		String[] m=result.split(System.getProperty("line.separator"));
		sensorInfo = m[1];	
		sensorInfo += "\r\n";
		
	if( itpCom.portnum == 8080 ){
		// 旧サイト
			sensorInfo += "Temp " + 1024.0*(19.5*(ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorTemprarure()+2.9)+400.0)/(4.9*1000.0)+"\r\n";
		//	sensorInfo += "Temp 200\r\n"; 
			// 26C　T=(AD値/1024.0*4.9*1000.0-400.0) /19.5-2.9
			// AD値=1024.0*(19.5*(T+2.9)+400.0)/(4.9*1000.0)
			// W=((float)AD値/(float)(500-200))*100.0
			// AD値=(500-200)*W/100
			// sensorInfo += "Water 400\r\n"; // 67%
			sensorInfo += "Water "+ 3.0*ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorWaterlevel()+"\r\n";
		//	sensorInfo += "illum "+ ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorIllumination()+"\r\n";
			sensorInfo += "illum "+ ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorIllumination()/1100.0+"\r\n";
	} else {
		// 新サイト
		sensorInfo += "Temp " + ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorTemprarure()+"\r\n";
		sensorInfo += "Water "+ ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorWaterlevel()+"\r\n";
		sensorInfo += "illum "+ ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorIllumination()+"\r\n";
		}
	}
	sensorInfo="300 OK\r\n"+sensorInfo+"\r\n";

	
	//
}

public String getPlanterName()
{
// 設定ファイルを読込む
//Planter Name reader
String pname="";
String pdata=Files.getPlantersfile();
// 設定ファイルからプランターの名前を読む。
	if(pdata.compareTo("")!=0){
		String[] pd=pdata.split(System.getProperty("line.separator"));
		String[] pp=null;
	// Name set
	for(int i=0;i<pd.length;i++){
	pp = pd[i].split("Planter ");
	if(pp.length>=2){
		pname += (pp[1]+" ");
			}
		}
	} else {
		// プランター名設定ファイルが空。設定してもらわないといけない。
	}
	return pname;
}

// 2011.11.12 y.s
private void camera_processing(String[] arr) 
{
	String args=null;
	String plantername="";
	// Camera Control
// すべてのカメラの写真を撮る
	
//	for(int i=1;i<=get_nPlanter();i++){
		int i = currentPlanterNo;
		if(get_nPlanter()==0){
			// dummyplanter
			plantername="dummyPlanter";
		} else {
			plantername=getPlanterName(i-1);
		}
		itp_Logger.logger.info("PlanterName "+plantername);
		
		String pname="";
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;   //時間を取得
		int date = Calendar.getInstance().get(Calendar.DATE);   //時間を取得
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);		//時間を取得
		int min  = Calendar.getInstance().get(Calendar.MINUTE);     	//時間を取得
		
		pname=plantername+"_"+month+"_"+date+"_"+hour+"_"+min+".jpg";


			// MacOSX
			// Windows
			if(IsOS.isOS().contains("Linux")==false)
			args = "-c "+ getCameNo(currentPlanterNo-1)+" -f "+pname;
			else
				// Linuxでは0番から始まる。何故か、getCameNo(currentPlanterNo-1)に１が入っている。
			args = "-c 0 -f "+pname;
			
			
			///
			itp_Logger.logger.info(args);
			System.out.println("capture !! "+args);
			

			String ret=CameraCapture.capturePhoto(args, true);

			System.out.println("capture.capturePhoto: "+ret);
			itp_Logger.logger.info(ret);// 11.23. y.s
			
			if(ret.contains("save_image"))
				itpCom1.say("300 OK\r\n\r\n"); // OK state
			else
				itpCom1.say("406 Error Can not Take a Photo\r\n\r\n");// NG state

//		}// next i
	//
			
		
}

public void timerSchedule(TimerTask tt, Calendar cal)
{	
	itp_Logger.logger.info("---timerSchedule()---");
	Timer timer = new Timer();

	itp_Logger.logger.info("calendar="+cal.getTime());

	//
	// List list = new ArrayList(); 
	// list.add(timer);
	//
	//Calendar calender = Calendar.getInstance();
	long period=24*60*60*1000;// 24h
	//long period=5*60*1000;// 5min
	
	itp_Logger.logger.info(""+cal.getTime());
	
	//
	timer.schedule(tt, cal.getTime(), period);// Add timer schedule
// TODO
//	capture.cameraTimer.add(timer);
}

class autoPlanterStatus extends TimerTask {
// Constructor	
	public  autoPlanterStatus()
	{
	}

	@Override
	public void run() {
		planter_status(); // 毎時　00分にステータスリポートを行う。
	}
//
}

// Planter status report
public void planter_status_processing()
{
//
	Calendar retTime = Calendar.getInstance(); //インスタンス化HOUR_OF_DAY);//時を取得
	// current time  chour:cmin
	int cyear = retTime.get(Calendar.YEAR);
	int cmonth= retTime.get(Calendar.MONTH);
    int cday =  retTime.get(Calendar.DATE);
    int chour = retTime.get(Calendar.HOUR_OF_DAY);
    int cmin =  0;    
// save schedule
    autoPlanterStatus tt = new autoPlanterStatus();// read sensor values
	Calendar cal=Calendar.getInstance();
	cal.set(cyear, cmonth, cday, chour, cmin);
	cal.add(Calendar.HOUR_OF_DAY, 1);//	do from After 1 hour
	itp_Logger.logger.info("---timerSchedule()---");
	Timer timer = new Timer();
	long period=60*60*1000;// 60 min
	timer.schedule(tt, cal.getTime(), period);// Add timer schedule
}


class autoALV_processing extends TimerTask {
	// Constructor	
	public  autoALV_processing()
	{
	}
	@Override
	public void run() {
		  // ２０分毎にALVを送信する。
//		while(	itpCom1.messageUse==false ); // wait
		  itpCom1.say("ALV\r\n\r\n");
		  String s = itpCom1.listen();
		  if(s.contains("100 OK")==false ){
			  itp_Logger.logger.info("クラウドガーデンが応答しません。"+s);
			  
			  // update setup file for restart itpManager
	    		File f0 = new File(Files.getSetupfile());
	    		Calendar calender = Calendar.getInstance();
				f0.setLastModified(calender.get(Calendar.HOUR_OF_DAY));
		  } 
	}
}

public void ALV_processing()
{
	//
	Calendar retTime = Calendar.getInstance(); //インスタンス化HOUR_OF_DAY);//時を取得
	// current time  chour:cmin
	int cyear = retTime.get(Calendar.YEAR);
	int cmonth= retTime.get(Calendar.MONTH);
    int cday =  retTime.get(Calendar.DATE);
    int chour = retTime.get(Calendar.HOUR_OF_DAY);
    int cmin =  retTime.get(Calendar.MINUTE);    
// save schedule
    autoALV_processing tt = new autoALV_processing();
	Calendar cal=Calendar.getInstance();
	cal.set(cyear, cmonth, cday, chour, cmin);			
	cal.add(Calendar.HOUR_OF_DAY, 1);//	do from After 1 hour
	itp_Logger.logger.info("---timerSchedule()---");
	Timer timer = new Timer();
	long period=20*60*1000;// 20 min
	timer.schedule(tt, cal.getTime(), period);// Add timer schedule
}


public void TPT_processing(String[] arr)
{
	//
	//Calendar retTime = Calendar.getInstance(); //インスタンス化HOUR_OF_DAY);//時を取得
	// current time  chour:cmin
	/*
	int cyear = retTime.get(Calendar.YEAR);
	int cmonth= retTime.get(Calendar.MONTH);
    int cday =  retTime.get(Calendar.DATE);
    int chour = retTime.get(Calendar.HOUR_OF_DAY);
    int cmin =  retTime.get(Calendar.MINUTE);    
	*/
	
    itp_Logger.logger.info("TPT_processing:Take Picture Time Table making");
	
	//
	//	clear timer of list array
	//
	// 現在のプランターの撮影スケジュールだけをクリアする。
	//
    /*
	if(capture.cameraSchedule.isEmpty()==false){
		// calcel timer
		int[] clearNo=new int[64];
		int clearSize=0;
		
		for(int i=0;i<capture.cameraSchedule.size();i++){
		autoCapture tt = (autoCapture) capture.cameraSchedule.get(i);
		
		itp_Logger.logger.info("Pname:"+tt.Pname);
		itp_Logger.logger.info("Current Pname:"+getPlanterName(currentPlanterNo-1));

		if(tt.Pname.contains(getPlanterName(currentPlanterNo-1))){
			clearNo[clearSize++]=i;
			break;
			}
		}

		for(int i=0;i<clearSize;i++){
		autoCapture tt = (autoCapture) capture.cameraSchedule.get(i);
		// cancel it
		capture.cameraSchedule.get(i).cancel();//
		capture.cameraTimer.get(i).cancel();//
		tt.cancel();// cancel
		// remove it
		capture.cameraSchedule.remove(i);
		capture.cameraTimer.remove(i);
//		capture.cameraSchedule.clear();
//		capture.cameraTimer.clear();
		itp_Logger.logger.info("cameraSchedule["+getPlanterName(currentPlanterNo-1)+"] is cleard");
		}
	}
	// add timer to list array
	//
	  String pname="";
	  for(int j=0;j<capture.timeTableNo;j++){
		  String[] timetable=capture.timeTable[j].split(":");
		  
		  itp_Logger.logger.info("TPT_processing GetSetting:"+capture.timeTable[j]);
		  
			int hour = Integer.parseInt(timetable[0]);
			int min  = Integer.parseInt(timetable[1]);
			
			Calendar cal=Calendar.getInstance();
			
//			itp_Logger.logger.info("getTime="+cal.getTime());
			itp_Logger.logger.info("TPT_processing cal.set:"+" "+" "+hour+" "+min);

// すでに登録されている撮影スケジュールを調べて、同時刻の撮影をしないようにする。
			if(capture.cameraSchedule.isEmpty()==false){
				// calcel timer
				for(int i=0;i<capture.cameraSchedule.size();i++){
				autoCapture tt = (autoCapture) capture.cameraSchedule.get(i);
				if((tt.hour*60+tt.min)==(hour*60+min)){
					// 同時刻なので１分ずらす
					min++;
					// 再度、最初からチェックをしなおす
					i=0;
					}
				}
			}
			/////
			
			cal.set(cyear, cmonth, cday, hour, min);
//  すでに時間が過ぎていたら、翌日から開始する。
			if(hour <= chour && min < cmin){
				// この時間はもう過ぎている。明日にする。
				cal.add(Calendar.DAY_OF_MONTH, 1);// 翌日		
			}
			
			itp_Logger.logger.info("setTime="+cal.getTime());
										
					pname=getPlanterName(currentPlanterNo-1)+"_DATE_"+".jpg";
					
					// for BUG of OpenCV 2.3
					if(IsMacorWin.isMacOrWin()==true ){
						// MacOSX
						pname.replace(".jpg", ".tif");
					}
					
					itp_Logger.logger.info("FileName="+pname);	
					//JOptionPane.showMessageDialog(null,pname);
					// Save to Timer List
					itp_Logger.logger.info("TimerTask new start");						
					TimerTask tt=new autoCapture(pname, getCameNo(currentPlanterNo-1));
					itp_Logger.logger.info("TimerTask new end");	

					//
					capture.cameraSchedule.add(tt);
//					itp_Logger.logger.info("TPT_processing: cameraSchedule.add");	
					// save schedule
					timerSchedule(tt, cal);
					
					itp_Logger.logger.info("TimerTask :"+hour+" "+min);	
					itp_Logger.logger.info("save schedule at : "+cal.getTime());	
			  
	  		}// next j
	  		*/
}

public void bye_processing() throws IOException 
{
// disconnection web
	try {
		bye();
	}
	catch(Exception e) {
	}	
	running = true;	// itplanter threadは終了。
}

public String[] SCL_processing(String[] arr) 
{
	String result;
	String Scom;
	String[] nL=null;
	// nL[0]="SCL"
	// nL[1]="Amount: 3"
	nL = arr[1].split(" ");
	// nL[0]="Amount: "
	// nL[1]="1"
	//Scom += (nL[1]+" ");//ライトのスケジュールの数
	//
	int amount=0;
	for(int i=2;i<arr.length;i++){
		// arr[i]="Time: 420-425"
		nL = arr[i].split(" ");
		// nL[1]="420-425"
		String[] tt = nL[1].split("-");
		int starttime0=0,conttime0=0;
		starttime0=Integer.valueOf(tt[0]);
		conttime0=Integer.valueOf(tt[1]);
		
		// conttimeが240以上であった場合、分割する。
		if(conttime0 > 240){
			itp_Logger.logger.info("starttime0:"+starttime0);
			itp_Logger.logger.info("conttime0:"+conttime0);
			
			int n=(int)(conttime0/240)+1;
			Integer[] starttime=new Integer[n];
			Integer[] conttime=new Integer[n];
			for(i=0;i<n;i++){
				starttime[i]=starttime0+i*240;

				if( conttime0 > 240 ){
					conttime[i]= 240;
					conttime0 -= 240;// 240min差し引く					
				} else conttime[i]=conttime0;
				
				amount+=3;// 3byte
				//
				itp_Logger.logger.info("starttime["+i+"]:"+starttime[i]);
				itp_Logger.logger.info("conttime["+i+"]:"+conttime[i]);
				}
			} else amount+=3;// 3byte
		}

	result = sendCom.sendcom(" "+currentPlanterNo+" -e f"+amount); ;//ライトのスケジュールの数
	Scom = " "+currentPlanterNo+" -e e 43 ";// ライトのスケジュールの開始番地43 0x2B
	Scom += (amount+" ");// 開始時間2Byteと継続時間1Byteに分けるので３倍になる。
	
	//Time: 420-425
	for(int i=2;i<arr.length;i++){
		int up=0,dn=0;
		// arr[i]="Time: 420-425"
		nL = arr[i].split(" ");
		// nL[1]="420-425"
		String[] tt = nL[1].split("-");
		int starttime0=0,conttime0=0;
		starttime0=Integer.valueOf(tt[0]);
		conttime0=Integer.valueOf(tt[1]);
		
		// conttimeが240以上であった場合、分割する。
		if(conttime0 > 240){
			int n=(int)(conttime0/240)+1;
			Integer[] starttime=new Integer[n];
			Integer[] conttime=new Integer[n];
			
			for(int j=0;j<n;j++){
				starttime[j]=starttime0+j*240;
				
				if( conttime0 > 240 ){
					conttime[j]= 240;
					conttime0 -= 240;// 240min差し引く					
				} else conttime[j]=conttime0;

				//
			//　１バイト単位なので、上位バイトと下位バイトに分ける。
			up = (int)(starttime[j]/256);// 256
			Scom += (String.valueOf(up)+" ");
			dn = starttime[j]-256*up;// 256
			
			Scom += (String.valueOf(dn)+" ");
			// conttime < 256
			Scom += (String.valueOf(conttime[j])+" ");
					}// next j
			} else {
			// conttime < 240
			//　１バイト単位なので、上位バイトと下位バイトに分ける。
			up = (int)(starttime0/256);// 256
			Scom += (String.valueOf(up)+" ");
			dn = starttime0-256*up;// 256
			
			Scom += (String.valueOf(dn)+" ");
			// conttime < 256
			Scom += (String.valueOf(conttime0)+" ");
		} //
		///
	}
	
	itp_Logger.logger.info("Command SCL: "+Scom);// Debug

//					result="Command: e";
	result = sendCom.sendcom(Scom); // sendcom
	
//	itp_Logger.logger.info(result);

	arr=result.split(System.getProperty("line.separator"),0);

	// SendComが受けたコマンドが正しいかどうかの検証
	if( arr[0].compareTo( String.valueOf("Command: e")) == 0){
		itpCom1.say("300 OK\r\n\r\n");
	} else {
		// 正しくない！
		itpCom1.say("409 Error Can not Set Schedule\r\n\r\n");
	}
	return arr;
}

public String[] SCP_processing(String[] arr) 
{
	String result;
	String Scom;
	// Pump Schedule set	
		String[] nL = arr[1].split(" ");
		// nL[0]="Amount: "
		// nL[1]="1"
		//Scom += (nL[1]+" ");// ポンプのスケジュールの数

		result = sendCom.sendcom(" "+currentPlanterNo+" -e g"+nL[1]); ;// ポンプのスケジュールの数設定
		
		Scom = " "+currentPlanterNo+" -e e 18 ";// ポンプのスケジュールの開始番地18 	0x12
		Scom += (String.valueOf(Integer.valueOf(nL[1])*2)+" ");// 上位バイトと下位バイトに分けるので２倍になる。
		//Time: 420
		for(int i=2;i<arr.length;i++){
			nL = arr[i].split(" ");
			// nL[0] "Time:"
			// nL[1] 420
			int up=0,dn=0;
			//　１バイト単位なので、上位バイトと下位バイトに分ける。
			up = Integer.valueOf(nL[1])/256;
			Scom += (String.valueOf(up)+" ");
			dn = Integer.valueOf(nL[1])-256*up;
			Scom += (String.valueOf(dn)+" ");
		}
		
		itp_Logger.logger.info("Command SPC: "+Scom);// Debug
//					result="Command: e";
		result = sendCom.sendcom(Scom); // sendcom
		itp_Logger.logger.info(result);
		
		arr=result.split(System.getProperty("line.separator"),0);
		
		// SendComが受けたコマンドが正しいかどうかの検証
		if( arr[0].compareTo( String.valueOf("Command: e")) == 0){
			// 正しい
			itpCom1.say("300 OK\r\n\r\n");
		} else {
			// 正しくない！
			itpCom1.say("409 Error Can not Set Schedule\r\n\r\n");
		}
	return arr;
}


public void CMD_processing(String[] arr) 
{
	String result="";
	String Scom=null;
	String Scombuf=null;
	
	String[] commandline=null;
	itp_Logger.logger.info("currentPlanterNo "+currentPlanterNo);
	
	// CMD

		if( arr.length >= 2 ){
			commandline=arr[1].split(" ",0);
			if(commandline.length>1){
			//	commandline[1]=commandline[1].replace("undefined", "");
				Scombuf = commandline[1];
			}
		}
//		System.out.println("Scombuf="+Scombuf);
		if(commandline[1].compareTo("SHT")==0){
			camera_processing(arr);//
			return;
		}
		if(commandline[1].compareTo("A")==0){
			itpCom1.say("300 OK\r\n");
			String rmessage="Temp "+ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorTemprarure();
			itpCom1.say(rmessage+"\r\n\r\n");// send result
			itp_Logger.logger.info("A "+rmessage);
			return;
		}
		
		if(commandline[1].compareTo("B")==0){
			itpCom1.say("300 OK\r\n");
			String rmessage="Water "+ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorWaterlevel();
			itpCom1.say(rmessage+"\r\n\r\n");// send result
			itp_Logger.logger.info("B "+rmessage);
			return;
		}
		
		if(commandline[1].compareTo("F")==0){
			itpCom1.say("300 OK\r\n");
//			String rmessage="illum "+planterStatus.get(currentPlanterNo-1).get_ADillum();
			String rmessage="illum "+ITPlanterClass.getCurrentPlanterClass().getSensor().getSensorIllumination();
			itpCom1.say(rmessage+"\r\n\r\n");// send result
			return;
		}
		
		if(commandline[1].compareTo("z")==0){
			itpCom1.say("300 OK\r\n");
			String rmessage=ITPlanterClass.getCurrentPlanterClass().getStatue();
			itpCom1.say(rmessage+"\r\n\r\n");// send result
			itp_Logger.logger.info("z "+rmessage);
			return;
		}
	
		// %
		if(commandline[1].equals("%")==true){
			result = sendCom.sendcom("-e %");
			System.out.println("result="+result);
			arr=result.split("\n",0);
			System.out.println("arr[1]="+arr[1]);
			String[] nn=arr[1].split("  ");
			System.out.println("nn="+nn[0]);
			System.out.println("nn="+nn[1]);
			nn[1].replace("\r", "");
			nn[1].replace("\n", "");
			
			currentPlanterNo=Integer.parseInt(nn[1].replaceAll("[^0-9]",""));
			itp_Logger.logger.info("Currenr Planter No "+currentPlanterNo);
			itpCom1.say("300 OK\r\n");
			itpCom1.say(currentPlanterNo+"\r\n\r\n");// send result
			itp_Logger.logger.info("% "+currentPlanterNo);
			return;
			//JOptionPane.showMessageDialog(null, "currentPlanterNo"+currentPlanterNo);
		} else { // CMD
		// else CMD
		Scom = currentPlanterNo+" -e "+Scombuf;// 指定したプランターを制御する。
//		System.out.println("Command|"+Scom+"|");// Debug
		
		
		// L command 以外のコマンド処理
		if(commandline[1].compareTo("L")!=0){
			
		System.out.println("Command_to_sendcom "+Scom);
//		itp_Logger.logger.info("Connect to the itplanter :"+Scom);
		
		result = sendCom.sendcom(Scom);
		
		arr=result.split("\n",0);
		
		for(int c=0;c<arr.length;c++){
		// Error
		if(arr[c].contains("error")==true){
			if(get_nPlanter()>0)
				itpCom1.say("400 Error\r\n\r\n");
			else {
				// response for dummy system
				itpCom1.say("300 OK\r\n");
				String rmessage="";
				if(commandline[1].compareTo("A")==0) rmessage="Temp 0";
				if(commandline[1].compareTo("B")==0) rmessage="Water 0";
				if(commandline[1].compareTo("F")==0) rmessage="illum 0";
				if(commandline[1].compareTo("z")==0) rmessage="S_LAMP 0 S_PUMP 0:PTime 0";
				itpCom1.say(rmessage+"\r\n\r\n");// send result
			}
				
			return;
			}
		}
		//
		// SendComが受けたコマンドが正しいかどうかの検証
		if( arr[0].contains( String.valueOf("Command: "+Scombuf))){	
		// T command
		/*
					if( commandline[1].compareTo("T")==0 ){//
					// T command
					String ret=null;
					ret=toASCII(result);
					if(ret != null ) itpCom1.say(ret);	
					//
					itpCom1.say("300 OK\r\n");
					return;
					}// T command
		*/
		//   check return value of sendcom
				if(arr.length>=2 && arr[0].contains(commandline[1])){
					// return result
					//itp_Logger.logger.info("CMD_processing OUTPUT");
					itpCom1.say("300 OK\r\n");
					itpCom1.say(arr[1]+"\r\n\r\n");// send result
								} 
					else 
								{
						itpCom1.say("400 Error\r\n\r\n");						
								}
					} 
		///
			}// CMD L
			else {
				// L command
				if( commandline[1].compareTo("L")==0 ){//
				// CMD L

				String Lcom=currentPlanterNo+" -e o";// PC mode	
				result = sendCom.sendcom(Lcom);	// PC mode		
				
				Lcom=currentPlanterNo+" -e L";// L
				result = sendCom.sendcom(Scom);	// L	
				arr=result.split(System.getProperty("line.separator"),0);
				// SendComが受けたコマンドが正しいかどうかの検証
				if( arr[0].contains( String.valueOf("Command: "+Scombuf)) == true){
					itpCom1.say("300 OK\r\n");					
					itpCom1.say(arr[1]+"\r\n\r\n"); // status report
				} else {
					itpCom1.say("400 Error\r\n");
				}

				try {
					sleep(3000);// sleep 3 sec
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		
				Lcom=currentPlanterNo+" -e p";// AUTO mode	
				result = sendCom.sendcom(Lcom);	 // AUTO mode	
				}
			}
		}// CMD
}

//
public void serial_set() 
{
	//planterName=new String[nPlanter];
	int np=get_nPlanter();
	// get serial of All Planters
	for(int i=0;i<np;i++){
		String ret=null;
		//
		ret=sendCom.sendcom(i+1+" -e v");
		String[] arrv=ret.split(System.getProperty("line.separator"),0);
		String mmver= arrv[1].replace("\r", "");
		mmver= mmver.replace("\n", "");
		String[] sver= mmver.split(" ");
		String[] mver= sver[1].split("\\.",0);
		Integer ver_major=Integer.valueOf(mver[0]);
		planterVersion.add(ver_major);
	}
	//
	
	for(int i=0;i<np;i++){
	//int	i=currentPlanterNo;
	String[] arr=null;
	String s="";
	
		if(planterVersion.get(i)==1){
		//
		// set planter name
		//
		s=sendCom.sendcom(i+1+" -e c");
		// 'c' command で　white LED Blink を調べる。　default=250
		arr=s.split(System.getProperty("line.separator"),0);
		// SendComが受けたコマンドが正しいかどうかの検証
		if( arr[0].contains("Command: c") == false){
			// Error
			itp_Logger.logger.info("アイティプランターが正しく応答していません。"+arr[0].compareTo("Command: c"));
		//	return;
		}
		// itplanter ERROR
		if(arr[1].contains("usb_claim_interface error")){
			itp_Logger.logger.info("アイティプランターの電源を入れなおして下さい。"+currentPlanterNo);
			System.exit(get_nPlanter());
		//return;
		}
		// Command: c
		// NormalBlin 250
		// BUG?
		String[] ps=arr[1].split(" ",0);
		if(ps.length<1) return;
			planterSerial.add(250-i-1);// 1,2,3,.... 249
			//planterStatus.get(i).set_serial(250-i-1);// set serial to planter status			
			// 'c' command で　white LED Blink をセットする。
			arr=sendCom.sendcom(i+1+" -e c "+planterSerial.get(i)).split(System.getProperty("line.separator"),0);
			// SendComが受けたコマンドが正しいかどうかの検証
			if( arr[0].contains("Command: c") == false){
				// Error
				itp_Logger.logger.info("アイティプランターが正しく応答していません。"+arr[0]+" "+arr[0].compareTo("Command: c "));
				}
			} else 
			if(planterVersion.get(i)==2){// itpOS ver.2
				//
				s=sendCom.sendcom(i+1+" -e Z");
				arr=s.split(System.getProperty("line.separator"),0);
				// SendComが受けたコマンドが正しいかどうかの検証
				if( arr[0].contains("Command: Z") == false){
					// Error
					itp_Logger.logger.info("アイティプランターが正しく応答していません。"+arr[0].compareTo("Command: Z"));
				//	return;
				}
				// itplanter ERROR
				if(arr[1].contains("usb_claim_interface error")){
					itp_Logger.logger.info("アイティプランターの電源を入れなおして下さい。"+currentPlanterNo);
					System.exit(get_nPlanter());
				//return;
				}
				String[] ps=arr[1].split(" ",0);
				if(ps.length<1) return;
				ps[0]=ps[0].replace("\r", "");
				ps[0]=ps[0].replace("\n", "");
				//
					planterSerial.add(Integer.parseInt(ps[0].replaceAll("[^0-9]","")));
			}// itpOS ver.2
			

			// '^' command で　時計を合わせる
			s=sendCom.sendcom(i+1+" -e ^");
			itp_Logger.logger.info(s);
			//
	}// next i
}

public void bye() throws IOException 
{
	// Listenerから切断を申し入れるとき
	// BYE
	//終了処理
	itpCom1.close();
}

//
public String sha256(String input)
{
	MessageDigest md;

	try {
		md = MessageDigest.getInstance("SHA-256");
	}
	catch(NoSuchAlgorithmException e) {
		return null;
	}

	md.reset();
	md.update(input.getBytes());
	byte[] hash = md.digest();
	StringBuffer sb = new StringBuffer();
	int count = hash.length;
	for (int i = 0; i < count; i++) {
		sb.append(Integer.toHexString((hash[i] >> 4) & 0x0F));
		sb.append(Integer.toHexString(hash[i] & 0x0F));
	}
	return sb.toString();
}

public  byte[] Pump_schedule(Integer[] data)
{
	byte[] b=new byte[64];// EEPROM BUFFER
	b[0]= data[0].byteValue(); // Pump Schedule No.
	for(int i=1;i<25;i+=2){
	b[i]  =(byte) (( data[1].byteValue() )>> 8);	// First Byte
	b[i+1]=(byte) (( data[1].byteValue() ) & 0xff);	// Last Byte
	}
	return b;
}

public  byte[] Lamp_schedule(Integer[] data)
{
	byte[] b=new byte[64];// EEPROM BUFFER
	b[0]= data[0].byteValue(); // Lamp Schedule No.
	for(int i=1;i<19;i+=3){
	b[i]  =(byte) (( data[1].byteValue() )>> 8);	// First Byte
	b[i+1]=(byte) (( data[1].byteValue() ) & 0xff);	// Last Byte
	b[i+2]=data[2].byteValue();	// Lamp Cont Time
	}
	return b;
}

// stop thread
public void stopRunning()
 {
	running = false;// Loop break
}

//
@Override
public void run() 
{
	// First of All, Get setup file informations.
	while(true){
		if( (initial_step()) != null ) break;
		try {
			sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// Planter設定ファイルからプランターの名前を読む
//	Planter Name reader
	String pname="";
	String pdata=Files.getPlantersfile();
	String[] pd=pdata.split(System.getProperty("line.separator"));
	String[] pp=null;
	// Name set
	for(int i=1;i<get_nPlanter();i++){
//		itp_Logger.logger.info("pd:"+pd[i-1]+" i="+i+" nP="+get_nPlanter());
	pp = pd[i-1].split("Planter ");
	if(pp.length>=2) pname += (pp[1]+" ");
	}
	itp_Logger.logger.info(pname);
	
	///
	itplanter_run();
}



}