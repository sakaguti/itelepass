import info.bix.tokai.bixpp.binding.BIXpp;
import info.bix.tokai.bixpp.binding.Cultivation;
import info.bix.tokai.bixpp.binding.DefinitionItem;
import info.bix.tokai.bixpp.binding.DescriptionItem;
import info.bix.tokai.bixpp.binding.Farm;
import info.bix.tokai.bixpp.binding.Plant;
import info.bix.tokai.bixpp.binding.RecordingDefinition;
import info.bix.tokai.bixpp.io.BIXppIO;
import info.bix.tokai.bixpp.xml.ValidationException;
import info.bix.tokai.bixpp.xml.XMLException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//
//
//	BIXppファイルに栽培プログラムを書き込んだり、読みだしたりするクラス
//
//
public class PlantProgram {
private double tempWarn=0.0;// 警告温度
private double waterWarn=0.0;// 警告水位
private double illumWarn=0.0;// 警告下限照度
private double pumpWrokTime=1.0;// 60sec is default
private String serialNo="0";// シリアル番号
private int mejarVersion=2;// メジャーバージョン番号
private int minerVersion=0;// マイナーバージョン番号

//
private ArrayList<Schedule> pumpSchedule=new ArrayList<Schedule>();
private ArrayList<Schedule> lampSchedule=new ArrayList<Schedule>();
private ArrayList<Schedule> dutySchedule=new ArrayList<Schedule>();
//
    private BIXpp bixpp = null;
    private String bIXppFileName="";
    
private String programCategory="自作";
private String plantCategory="";
private String programName="";
private String programMemo="";
private String plantName="";
private String plantMemo="";

    
DescriptionItem descriptionItemTempWarn = new DescriptionItem();
DescriptionItem descriptionItemWaterWarn = new DescriptionItem();
DescriptionItem descriptionItemIllumWarn = new DescriptionItem();
DescriptionItem descriptionItemPumpWrokTime = new DescriptionItem();
DescriptionItem descriptionItemSerialNo = new DescriptionItem();
DescriptionItem descriptionItemVersion = new DescriptionItem();
DescriptionItem descriptionItemPumpSchedule = new DescriptionItem();
DescriptionItem descriptionItemLampSchedule = new DescriptionItem();
DescriptionItem descriptionItemDutySchedule = new DescriptionItem();

public BIXpp getBIXpp()
{
return bixpp;
}
//
//
//	private PlanterControl	planterControl=new PlanterControl(); // Planter control parameters	制御パラメータ
//
public PlantProgram(String f)
{
	// Constructor
	initialize(f);// 
}

private void initialize(String f) {
	// 栽培プログラムを開く
	if(f==null){
	bixpp=new BIXpp();
	return;
	}
	readFile(f);
	bIXppFileName=f;
}
public void writeDataToFile(String f)
{
// PlantProgramをBIXppファイルに書き出す
setBIXpp();
//
writeFile(f);
}
final int PLANTER_SERIAL=0;
final int MEJAR_VERSION=1;
final int MINER_VERSION=2;
final int TEMP_WARN=3;
final int WATER_WARN=4;
final int ILLUM_WARN=5;
final int PUMP_WORKTIME=6;

final int PLANTER_NAME=7;
final int PUMP_SCHEDULE=8;
final int LAMP_SCHEDULE=9;
final int DUTY_SCHEDULE=10;

final int CATEGORY=11;


public void setBIXpp()
{
if( bixpp== null ){
System.out.println("Error bixpp can not handle");
return;
}
PlanterClass planterClass=ITPlanterClass.getCurrentPlanterClass();
PlantProgram plantProgram=planterClass.getInformation().getPlantProgram();
if(plantProgram==null){
System.out.println("Error plantProgram can not handle");
return;
}
//
if( bixpp.getRecordingDefinition() == null ){
System.out.println("Error etRecordingDefinition can not handle");
return;
}
int ic=bixpp.getRecordingDefinition().getDefinitionItemCount();
for(int i=ic-1;i>=0;i--){
DefinitionItem item=bixpp.getRecordingDefinition().getDefinitionItem(i);
bixpp.getRecordingDefinition().removeDefinitionItem(item);
}
//
// 登録されていた定義を消す
bixpp.getRecordingDefinition().clearDefinitionItem();
// 新たな定義を追加していく
DefinitionItem[] definitionItems=null;
definitionItems=new DefinitionItem[12];
for(int i=0;i<12;i++){
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]=new DescriptionItem());
definitionItems[i].setIndex(i);
//
//	 System.out.println(definitionItems.length);
//
if( definitionItems[i].getIndex()==PLANTER_SERIAL){
   definitionItems[i].setName("SerialNo");
definitionItems[i].setComment(String.valueOf(planterClass.getSerial()));
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
if(definitionItems[i].getIndex()==MEJAR_VERSION){
definitionItems[i].setComment(String.valueOf(String.valueOf(Version.getMejar())));
   definitionItems[1].setName("mejarVersion");
   bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
if(definitionItems[i].getIndex()==MINER_VERSION){
definitionItems[i].setComment(String.valueOf(String.valueOf(Version.getMiner())));
   definitionItems[i].setName("minerVersion");
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
if(definitionItems[i].getIndex()==TEMP_WARN){
definitionItems[i].setComment(String.valueOf(String.valueOf(plantProgram.getTempWarn())));
   definitionItems[i].setName("tempWarn");
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
if(definitionItems[i].getIndex()==WATER_WARN){
definitionItems[i].setComment(String.valueOf(String.valueOf(plantProgram.getWaterWarn())));
   definitionItems[i].setName("waterWarn");
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
   
if(definitionItems[i].getIndex()==ILLUM_WARN){
definitionItems[i].setComment(String.valueOf(plantProgram.getIllumWarn()));
   definitionItems[i].setName("illumWarn");
   bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}

if(definitionItems[i].getIndex()==PUMP_WORKTIME){
definitionItems[i].setComment(String.valueOf(plantProgram.getPumpWrokTime()));
   definitionItems[i].setName("pumpWrokTime");
   bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
if(definitionItems[i].getIndex()==CATEGORY){
definitionItems[i].setComment(String.valueOf(plantProgram.getProgramCategory()));
   definitionItems[7].setName("category");
   bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
   	//
   // Lamp Schedule number
   // Lamp Schedule
if(definitionItems[i].getIndex()==PUMP_SCHEDULE){
   definitionItems[i].setName("pumpSchedule");
String t2="";
for(int i1=0;i1<plantProgram.getPumpScheduleSize();i1++){
   Schedule s=plantProgram.getPumpSchedule(i1);
  // System.out.print(s.getStartTime());
   t2=t2+"StartTime "+s.getStartTime()+System.getProperty("line.separator");
   int ipwt=(int)plantProgram.getPumpWrokTime();//
   String h=String.valueOf(ipwt/60);
   String m=String.valueOf(ipwt%60);
//	    String h=String.valueOf(Integer.valueOf((int)plantProgram.getPumpWrokTime())/60);
//	    String m=String.valueOf(Integer.valueOf((int)plantProgram.getPumpWrokTime())%60);
   String pw=h+":"+m;
   t2=t2+"ContinueTime "+pw+System.getProperty("line.separator");
   }
definitionItems[i].setComment(t2);
//	 System.out.println(t2);
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
   // Lamp Schedule number

   // Lamp Schedule
if(definitionItems[i].getIndex()==LAMP_SCHEDULE){
   definitionItems[i].setName("lampSchedule");
   String  t3="";
   for(int i1=0;i1<plantProgram.getLampScheduleSize();i1++){
   Schedule s=plantProgram.getLampSchedule(i1);
   t3=t3+"StartTime "+s.getStartTime()+System.getProperty("line.separator");
   t3=t3+"EndTime "+s.getEndTime()+System.getProperty("line.separator");
}
definitionItems[i].setComment(t3);
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
// Duty Schedule number
// Duty Schedule
if(definitionItems[i].getIndex()==DUTY_SCHEDULE){
   definitionItems[i].setName("dutySchedule");
   String t4="";
   for(int i1=0;i1<plantProgram.getDutyScheduleSize();i1++){
   Schedule s=plantProgram.getDutySchedule(i1);
   t4=t4+"Duty "+s.getValue()+" ";
   t4=t4+"StartTime "+s.getStartTime()+System.getProperty("line.separator");
   }
definitionItems[i].setComment(t4);
bixpp.getRecordingDefinition().addDefinitionItem(definitionItems[i]);
}
}// next i
   //
Plant plant=new Plant();
String plantName=plantProgram.getPlantName();
plant.setName(plantName);//　植物の名前
plant.setComment(plantProgram.getPlantMemo());// 植物の情報　コメント
if(bixpp.getCultivationCount()>0){
Cultivation c=bixpp.getCultivation(0);
c.setPlant(plant);
// メモの保存
String memo=plantProgram.getPlantMemo();
c.setComment(memo);
bixpp.setCultivation(0, c);
}

   //
   Farm farm=new Farm();
   // Farmの名前は、アイティプランターの名前
   farm.setName(planterClass.getInformation().getPlanterName());
   farm.setID(String.valueOf(planterClass.getSerial()));// IDは、プランターのシリアル番号
   farm.setArea(0.3*0.3);// プランターの栽培面積
  // <<< 130704
  // farm.setComment("itplanter "+String.valueOf(planterClass.getVersion().getMejar()));
   bixpp.setFarm(farm);
   
   bixpp.getRecordingDefinition().setDefinitionItem(definitionItems);
}

public void readFile(String f)
{
// 
	if(f.equals(".xml")==true) return;

	f=Files.getDBPath()+f;
        try {
		bixpp = BIXppIO.read(new File(f));
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
//
        // Author
        bixpp.getProducer().getName();
        // Date 
        bixpp.getStart();
        // UpDate
        bixpp.getUpdate();
        //
        //
        
        
// BIXppのデスクリプションに記録されている栽培プログラムを読み出す。
RecordingDefinition rd=bixpp.getRecordingDefinition();
//	 System.out.println("RecordingDefinition count="+rd.getDescriptionItemCount());
for(int i=0;i<rd.getDescriptionItemCount();i++){
DefinitionItem di=rd.getDefinitionItemByIndex(i);
if(di!=null){
//System.out.println("Name="+di.getName());
String name=di.getName();
if(name.contains("Category")){
programCategory=di.getComment();
} else
if(name.contains("SerialNo")){
/// error
//System.out.println("di.getComment()"+di.getComment());
//	 serialNo=Integer.parseInt(di.getComment().replaceAll("[^0-9]",""));
//serialNo=di.getComment();
} else
if(name.contains("mejarVersion")){
mejarVersion=Integer.parseInt(di.getComment().replaceAll("[^0-9]",""));
} else
if(name.contains("minerVersion")){
minerVersion=Integer.parseInt(di.getComment().replaceAll("[^0-9]",""));
} else
if(name.contains("tempWarn")){
//tempWarn=Double.parseDouble(di.getComment().replaceAll("[^0-9]",""));
tempWarn=Double.parseDouble(di.getComment());
} else
if(name.contains("waterWarn")){
//waterWarn=Double.parseDouble(di.getComment().replaceAll("[^0-9]",""));
waterWarn=Double.parseDouble(di.getComment());
} else
if(name.contains("illumWarn")){
//illumWarn=Double.parseDouble(di.getComment().replaceAll("[^0-9]",""));
illumWarn=Double.parseDouble(di.getComment());
} else
if(name.contains("pumpWrokTime")){
//pumpWrokTime=Double.parseDouble(di.getComment().replaceAll("[^0-9]",""));;
pumpWrokTime=Double.parseDouble(di.getComment());
System.out.println("PumpWorkTime "+pumpWrokTime);
} else
// Pump Schedule
if(name.contains("pumpSchedule")){
// clear schedule
removeAllPumpSchedule();
Schedule s=null;
String ps=di.getComment();
String[] pa=ps.split(System.getProperty("line.separator"));
for(int l=0;l<pa.length;l++){//
String[] item=pa[l].split(" ");
for(int j=0;j<item.length;j+=2){
// StartTime  hour:min
if(item[j].contains("StartTime")){
// pump start time 
if(pumpSchedule.size()-1 < j/2){
pumpSchedule.add(s=new Schedule());
}
s.setStartTime(item[j+1]);
} else
// ContinueTime min
if(item[j].contains("ContinueTime")){
s.setContinueTime(item[j+1]);
}
}// next j
} // next l
} else
// Lamp Schedule
if(name.contains("lampSchedule")){
// clear schedule
removeAllLampSchedule();
//	 for(int m=lampSchedule.size()-1;m<=0;m--) lampSchedule.remove(m);

Schedule s=null;
String ps=di.getComment();
String[] pa=ps.split(System.getProperty("line.separator"));
for(int l=0;l<pa.length;l++){//
String[] item=pa[l].split(" ");
for(int j=0;j<item.length;j+=4){
// StartTime  hour:min
if(item[j].contains("StartTime")){
// lamp start time 
lampSchedule.add(s=new Schedule());
s.setStartTime(item[j+1]);
s.setEndTime(item[j+3]);
}
}// next j
}// next l
//
} else
// Duty Schedule
if(name.contains("dutySchedule")){

// clear schedule
removeAllDutySchedule();
//	 for(int m=dutySchedule.size()-1;m<=0;m--) dutySchedule.remove(m);
Schedule s=null;
String ps=di.getComment();
String[] pa=ps.split(System.getProperty("line.separator"));
for(int l=0;l<pa.length;l++){//
String[] item=pa[l].split(" ");
// Duty XX StartTime XX:XX
for(int j=0;j<item.length;j+=4){
if(item[j].contains("Duty")){
dutySchedule.add(s=new Schedule());
s.setValue(item[j+1]);
s.setStartTime(item[j+3]);
}// 
}// next j
}// next l
} // duty schedule
     }// di != null
}// next i
}
public void writeFile(String f)
{
// 
try {
BIXppIO.write(bixpp,new File(f));
} catch (IOException e) {
e.printStackTrace();
}
}
public void removeAllPumpSchedule()
{
pumpSchedule=new ArrayList<Schedule>();
//	 for(int i=0;i<pumpSchedule.size();i++)
//	 pumpSchedule.remove(i);
}
public int getPumpScheduleSize()
{
return pumpSchedule.size();
}
public void removeAllLampSchedule()
{
lampSchedule=new ArrayList<Schedule>();
//	 for(int i=0;i<lampSchedule.size();i++)
//	 lampSchedule.remove(i);
}
public int getLampScheduleSize()
{
return lampSchedule.size();
}
public void removeAllDutySchedule()
{
dutySchedule=new ArrayList<Schedule>();
//	 for(int i=0;i<dutySchedule.size();i++)
//	 dutySchedule.remove(i);
}
public int getDutyScheduleSize()
{
return dutySchedule.size();
}

public void addPumpSchedule(Schedule s)
{
pumpSchedule.add(s);
}
public Schedule getPumpSchedule(int i)
{
return (Schedule) pumpSchedule.get(i);
}
public void setPumpSchedule(int i, Schedule s)
{
if(pumpSchedule.size()<i+1) pumpSchedule.add(s);
else pumpSchedule.set(i,s);
}
public void addLampSchedule(Schedule s)
{
lampSchedule.add(s);
}
public Schedule getLampSchedule(int i)
{
return (Schedule) lampSchedule.get(i);
}
public void setLampSchedule(int i, Schedule s)
{
if(lampSchedule.size()<i+1) lampSchedule.add(s);
else lampSchedule.set(i,s);
}
public void addDutySchedule(Schedule s)
{
dutySchedule.add(s);
}
public Schedule getDutySchedule(int i)
{
return (Schedule) dutySchedule.get(i);
}
public void setDutySchedule(int i, Schedule s)
{
if(dutySchedule.size()<i+1)	dutySchedule.add(s);
else dutySchedule.set(i,s);
}
public void setTempWarn(double tempwarn) {
tempWarn = tempwarn;
descriptionItemTempWarn.setComment("TempWarn"+" "+String.valueOf(tempWarn));
}

public double getTempWarn() {
return tempWarn;
}

public void setWaterWarn(double waterwarn) {
waterWarn = waterwarn;
descriptionItemWaterWarn.setComment("WaterWarn"+" "+String.valueOf(waterWarn));
}

public double getWaterWarn() {
return waterWarn;
}

public void setIllumWarn(double illumwarn) {
illumWarn = illumwarn;
descriptionItemIllumWarn.setComment("IllumWarn"+" "+String.valueOf(illumWarn));
}

public double getIllumWarn() {
return illumWarn;
}

public void setPumpWrokTime(double pumpwroktime) {
pumpWrokTime = pumpwroktime;
descriptionItemPumpWrokTime.setComment("PumpWrokTime"+" "+String.valueOf(pumpWrokTime));
}

public double getPumpWrokTime() {
return pumpWrokTime;
}

public void setSerialNo(String serialno) {
serialNo = serialno;
descriptionItemSerialNo.setComment("SerialNo"+" "+serialNo);
}

public String getSerialNo() {
return serialNo;
}

public void setMejarVersion(int mejarversion) {
mejarVersion = mejarversion;
descriptionItemVersion.setComment("MejarVersion"+" "+String.valueOf(mejarVersion));
}

public int getMejarVersion() {
return mejarVersion;
}

public void setMinerVersion(int minerversion) {
minerVersion = minerversion;
descriptionItemVersion.setComment("minerVersion"+" "+String.valueOf(minerVersion));
}

public int getMinerVersion() {
return minerVersion;
}

// TODO  check!!
//
// PlantProgram Classが保持している情報をNo. nのアイティプランターに送信する。
//
public void sendToPlanter(int n) {
// send PlantProgram to No n Planter
int p=1;
//if(n>0){
// index nのプランターのシリアル番号sを得る
////p=findPlanter();
// シリアル番号sのアイティプランターを探す
//}
p=n+1;

PlantProgram pp=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram();
if(pp.getMejarVersion()>=2){
// itplanter ver 2.0 の場合
//  W	n,aabbcc,ABCD	n番目のランプ点灯開始時間（分）,ランプ点灯時間（分）スケジュールの設定	W3,420,300 : 3番目のスケジュールは７時にランプONして５時間点灯する
//	X	n,aabbcc	 n番目のポンプ動作開始時間（分）スケジュールの設定	X0,125 : 0番のスケジュールは125分（２時５分）でポンプ動作時間1分
//	Y	n,aabbcc,ABCD	n番目のデュティ変更開始時間（分）,設定するデュティ（％：０～１００）スケジュールの設定
ArrayList<Integer> sendSchedule=new ArrayList<Integer>();
// 日をまたぐ設定がある場合の処理を追加すること
if(pp.getLampScheduleSize()>0){
int continueTime1,continueTime2;
int continueTime;
	// Lamp Schedule数のチェック
	Integer cn=pp.getLampScheduleSize();
	//	 System.out.println("ランプスケジュール数ORG "+cn);
	// Lamp Schedule チェック
	for(int i=0;i<pp.getLampScheduleSize();i++){
	// No. StartTime ContTime
	String[] hourMin=pp.getLampSchedule(i).getStartTime().split(":");
	int ih=Integer.parseInt(hourMin[0].replaceAll("[^0-9]",""));//
	int im=Integer.parseInt(hourMin[1].replaceAll("[^0-9]",""));//
	//	 int startTime=Integer.valueOf(hourMin[0])*60+Integer.valueOf(hourMin[1]);
	int startTime=ih*60+im;
	hourMin=pp.getLampSchedule(i).getEndTime().split(":");
	ih=Integer.parseInt(hourMin[0].replaceAll("[^0-9]",""));//
	im=Integer.parseInt(hourMin[1].replaceAll("[^0-9]",""));//
	int stopTime=ih*60+im;
	continueTime=stopTime-startTime;
	// 日をまたいだ設定になっている
	if(continueTime<0){
	continueTime1=24*60+im-startTime;
	continueTime2=stopTime;
	//
	sendSchedule.add(startTime);// start to 24h
	sendSchedule.add(continueTime1); // 
	//
	sendSchedule.add(0);// 0 to stoptime
	sendSchedule.add(continueTime2);
	cn++;
	} else {
		sendSchedule.add(startTime);// start to 24h
		sendSchedule.add(continueTime); // 
	}
	}
	//
	// Lamp Schedule数の書込み
	sendCommand(p,"f"+cn);
	//	 System.out.println("ランプスケジュール数NEW "+cn);
	// Lamp Schedule 書込み
	for(int i=0;i<cn*2;i+=2){
	String c="W"+i/2+","+sendSchedule.get(i)+","+sendSchedule.get(i+1);
	//	    System.out.println("ランプスケジュール"+i/2+" "+c);
	sendCommand(p,c);
	}
	}


/*
if(pp.getLampScheduleSize()>0){
// Lamp Schedule数の書込み
String cn="f"+pp.getLampScheduleSize();
sendCommand(p,cn);
//	System.out.println("ランプスケジュール数 "+cn);
// Lamp Schedule 書込み
for(int i=0;i<pp.getLampScheduleSize();i++){
// No. StartTime ContTime
String[] hourMin=pp.getLampSchedule(i).getStartTime().split(":");
int ih=Integer.parseInt(hourMin[0].replaceAll("[^0-9]",""));//
int im=Integer.parseInt(hourMin[1].replaceAll("[^0-9]",""));//
//	 int startTime=Integer.valueOf(hourMin[0])*60+Integer.valueOf(hourMin[1]);
int startTime=ih*60+im;
hourMin=pp.getLampSchedule(i).getEndTime().split(":");
ih=Integer.parseInt(hourMin[0].replaceAll("[^0-9]",""));//
im=Integer.parseInt(hourMin[1].replaceAll("[^0-9]",""));//
int continueTime=ih*60+im-startTime;
//	 int continueTime=Integer.valueOf(hourMin[0])*60+Integer.valueOf(hourMin[1])-startTime;
String c="W"+i+","+startTime+","+continueTime;
sendCommand(p,c);
}
}
*/
	if(pp.getPumpScheduleSize()>0){
	// Pump Schedule数の書込み
	String cp="g"+pp.getPumpScheduleSize();
	sendCommand(p,cp);
	// Pump Schedule　書込み
	int psn=pp.getPumpScheduleSize();
	for(int i=0;i<psn;i++){
	// No. StartTime WorkTime
	// Pupm Schedule > EndTime = StartTime + PumpWorkTime
	String[] hourMin=pp.getPumpSchedule(i).getStartTime().split(":");
	int ih=Integer.parseInt(hourMin[0].replaceAll("[^0-9]",""));//
	int im=Integer.parseInt(hourMin[1].replaceAll("[^0-9]",""));//
	int startTime=ih*60+im;
	//	 int startTime=Integer.valueOf(hourMin[0])*60+Integer.valueOf(hourMin[1]);
	String c="X"+i+","+startTime;
	sendCommand(p,c);
	}
	//
	// (int)pp.getPumpWrokTime()
	if( pp.getPumpWrokTime()<60) pp.setPumpWrokTime(60);// min 60sec
	String cu="U"+(int)(pp.getPumpWrokTime());
	sendCommand(p,cu);
	}
	if(getDutyScheduleSize()>0){
	// Duty Schedule数の書込み
	String cd="n"+pp.getDutyScheduleSize();
	sendCommand(p,cd);
	// Duty Schedule 書込み
	int dsn=pp.getDutyScheduleSize();
	for(int i=0;i<dsn;i++){
	// no duty StartTime
	String[] hourMin=pp.getDutySchedule(i).getStartTime().split(":");
	int ih=Integer.parseInt(hourMin[0].replaceAll("[^0-9]",""));//
	int im=Integer.parseInt(hourMin[1].replaceAll("[^0-9]",""));//
	int startTime=ih*60+im;
	//	 int startTime=Integer.valueOf(hourMin[0])*60+Integer.valueOf(hourMin[1]);
	int in=Integer.parseInt(pp.getDutySchedule(i).getValue().replaceAll("[^0-9]",""));//
	String c1="Y"+i+","+startTime+","+in;
	//	 String c1="Y"+i+","+startTime+","+Integer.valueOf(pp.getDutySchedule(i).getValue());
	sendCommand(p,c1);
	}
	}
	//
	} else {
	// itplanter Ver 1.X
	//　d EEPROM一括書込み
	//  e EEPROMアドレス指定書込み
	// TempWarn Level	'w'
	String w="w "+String.valueOf((int)pp.getTempWarn());
	sendCommand(p,w);
	// Water Warn Level 'x'
	String x="x"+String.valueOf((int)pp.getWaterWarn());
	sendCommand(p,x);
	}
		// ポンプの動作時間の設定
		//	String c="U"+String.valueOf((int)pp.getPumpWrokTime());
		//	sendCommand(p,c);
		//  コントロール値を書き込む
		// White LED Blink 
		// Return time from Manual Mode 'M'
		// Return time from PC Mode 'N'
		//
		// 順にプランター設定情報を送信する
		// （ROM内容を一度に送信する？）
		// 時計の設定  -e ^
		sendCommand(p,"^");
		// write to ROM
		sendCommand(p,"\\RAM");
}
// sendCommand( p: no of USB, s: command string)
private String sendCommand(int p, String s)
{
int n=p;
String m=n+" -e "+s;
System.out.println("sendcom "+m);
String wr = sendCom.sendcom(m); // sendcom
//itp_Logger.logger.info(wr);
String[]  arr=wr.split(System.getProperty("line.separator"),0);
// SendComが受けたコマンドが正しいかどうかの検証
if( arr[0].contains( String.valueOf("Command: "+s)) == false){
// 正しくない！
itp_Logger.logger.info("アイティプランターが応答しません。|"+arr[0]+"|"+s+"|");
}
if(arr.length>1) return arr[1]; else return null;
}

private int findPlanter() {
// シリアル番号が同じプランターを探す
String isereal="0";
int usbNo=1;
for(int i=1;i<=ITPlanterClass.getSystemPlanterNumber();i++){
		String wr = sendCom.sendcom(i+" -e Z"); // sendcom
		itp_Logger.logger.info(wr);
		String[]  arr=wr.split(System.getProperty("line.separator"),0);
		// SendComが受けたコマンドが正しいかどうかの検証
		if( arr[0].contains("Command: Z") == false){
			// 正しくない！
			itp_Logger.logger.info("アイティプランターが応答しません。");
		}
	isereal=arr[1];// serial number
	System.out.println("1 serial="+ITPlanterClass.getPlanterList().get(ITPlanterClass.getCurrentPlanterNo()).getSerial()+"iserial="+isereal+" i="+i);
	if(isereal.compareTo(ITPlanterClass.getPlanterList().get(ITPlanterClass.getCurrentPlanterNo()).getSerial())==0){
		usbNo=i;
		System.out.println("2 serial="+ITPlanterClass.getPlanterList().get(ITPlanterClass.getCurrentPlanterNo()).getSerial()+"iserial="+isereal+" i="+i);
		break;
		}
	}

	return usbNo;
}
public String getBIXppFileName() {
return bIXppFileName;
}
// 苗の種類
public void setPlantCategory(String plantCategory) {
this.plantCategory=plantCategory;
}

public String getPlantCategory() {
return this.plantCategory;
}

// 苗の種類
public void setProgramCategory(String plantCategory) {
this.programCategory=plantCategory;
}

// 入力された栽培プログラムの名前
public String getProgramCategory() {
return this.programCategory;
}
public void setProgramName(String prgName) {
this.programName=prgName;
}
public String getProgramName() {
return programName;
}
//　メモ情報
public void setProgramMemo(String prgMemo) {
this.programMemo=prgMemo;
}
public String getProgramMemo() {
return programMemo;
}
public void setPlantName(String pn) {
plantName=pn;
}
public String getPlantName() {
return plantName;
}
public String getPlantMemo() {
return plantMemo;
}
public void setPlantMemo(String pm) {
plantMemo=pm;
}
}
