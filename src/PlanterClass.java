public class PlanterClass {
private Information info=null;//  information
private Sensor 	sensor=null;// sensor information
private Control control=null;// control information
private PlantProgram   plantProgram=null;// plant program
private String serial=""; 		// serial of itplanter
private pVersion pversion=null;// version of itplanter OS
private int usbNo=0; 		// USB No.
private PlanterIcon inactiveGPanel=null;// Inactive
private PlanterIcon activeGPanel=null;// Active

//
	public PlanterIcon getActiveIcon()
	{
		if(activeGPanel==null) activeGPanel=new PlanterIcon(info.getPlanterName());
		if(sensor.getActiveCamNo()==0)
			activeGPanel.setPlanterIcon("NoCamON");
		else
			activeGPanel.setPlanterIcon("CamON");
		return activeGPanel;
	}
	
//
	public PlanterIcon getInActiveIcon()
	{
		if(inactiveGPanel==null) inactiveGPanel=new PlanterIcon(info.getPlanterName());
		if(sensor.getActiveCamNo()==0)
			inactiveGPanel.setPlanterIcon("NoCamOFF");
		else
			inactiveGPanel.setPlanterIcon("NoCamOFF");
		return inactiveGPanel;
	}
	
	public PlanterClass(String s)
	{
		//this.setSystemCameraSize(ITPlanterClass.getSystemCameraSize());
		
		setInfo(new Information(this));
		info.setPlanterName(s);
		
		setSerial(serial);
		
		setSensor(new Sensor(this));
		

		
		
		setControl(new Control(this));
		
		//
		setPlantProgram(new PlantProgram("default.xml"));
		
		setVersion(new pVersion());
		// Add cam
		/*
		this.setCameraSize(3);
		this.addCameraName("CAM-0");
		this.addCameraNumber(0);
		this.addCameraName("CAM-1");
		this.addCameraNumber(1);
		this.addCameraName("CAM-2");
		this.addCameraNumber(2);
		*/
	}
	
	public pVersion getVersion()
	{
		if(pversion==null){
			setVersion(new pVersion());// Version
		}
		return pversion;
	}

	private void setVersion(pVersion version2) {
		// 
		pversion=version2;
		// 
		if(ITPlanterClass.getSystemPlanterNumber()==0) return;// USB
		String result="";
		int n=ITPlanterClass.getCurrentPlanterNo();
		if(n<=0){
			n=0;
			ITPlanterClass.setCurrentPlanterNo(n);
		}
		result = sendCom.sendcom(" "+(n+1)+" -e v"); // sendcom
		if(result=="")		return ;
		// Command: v		arrn[0]
		// Version 2.9		arrn[1]
		String[] arrn=result.split(System.getProperty("line.separator"),0);
		//
		if(arrn[0]==""){
			System.out.println("can not find planter");
			itp_Logger.logger.info("can not find planter");
			return ;
			}
		
		if(arrn[0].equals("Version")==true){
		// Version 2.2
		if(arrn.length>1){
		String[] s=arrn[1].split(" ");
		//System.out.println("s:"+s[1]);
		String[] mm=s[1].split("\\.");
		// ??
		//System.out.println(mm[0]);

		//
		pVersion pversion=new pVersion();
		pversion.setMejar(Integer.parseInt(mm[0].replaceAll("[^0-9]","")));
		pversion.setMiner(Integer.parseInt(mm[1].replaceAll("[^0-9]","")));
		itp_Logger.logger.info("Version "+Version.getMejar()+"."+Version.getMiner());
		} else {
			System.out.println("no version");
			itp_Logger.logger.info("no version");		
			}
		}
	}
		
	
	public Information getInformation() {
		return this.info;
	}

	public void setInfo(Information information) {
		this.info = information;
	}

	public Sensor getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Control getControl() {
		return this.control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void setPlantProgram(PlantProgram p) {
		this.plantProgram = p;
	}
/*	
	public PlanterInformation getPlanterInfo() {
		return this.planterInformation;
	}
*/
	public void setPlantInfo(PlantProgram p) {
		this.plantProgram = p;
	}

	public PlantProgram getPlantInfo() {
		return this.plantProgram;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		if(serial.equals("")==true ){
			String result=sendCom.sendcom(" "+(ITPlanterClass.getCurrentPlanterNo()+1)+" -e Z");
			if(result != null ){
			String[] r=result.split(System.getProperty("line.separator"));
			if(r.length>1){
/*
System.out.println("Serial="+result);
System.out.println("Serial="+r[0]);
System.out.println("Serial="+r[1]);
*/
				serial=r[1];
System.out.println("Serial="+serial);
				}
			}
		}
		this.serial = serial;
	}

	public int getPlanterNo() {
		return usbNo;
	}

	public void setPlanterNo(int planterNo) {
		this.usbNo = planterNo;
	}
	
	public String getStatue() {
		// command z
		String result=sendCom.sendcom(" "+(ITPlanterClass.getCurrentPlanterNo()+1)+" -e z");
		return result;
	}

}
