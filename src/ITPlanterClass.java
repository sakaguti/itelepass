import java.util.ArrayList;

public class ITPlanterClass {
	private static 	ArrayList<PlanterClass> planterList=null;
	private static int 		systemPlanterNo=0;// �V�X�e���ɐڑ�����Ă���v�����^�[�̐�
	private static int 		systemCameraNo=0;//�@�V�X�e���ɐڑ�����Ă���J�����̐�
	static int 		    	currentPlanterNo=0;// ���݂̃v�����^�[�̔ԍ�
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
	
	// ���݁A�J�����g�ɂȂ��Ă���v�����^�[�ԍ��𓾂�
	public static int getCurrentPlanterNo()
	{
		return currentPlanterNo;
	}

	// ���݁A�J�����g�ɂȂ��Ă���v�����^�[�ԍ��𓾂�
	public static void setCurrentPlanterNo(int i)
	{
		currentPlanterNo=i;
	}
	
	public static void setCurrentPlanterClass(PlanterClass planterClass) {
		currentPlanterClass=planterClass;
	}

	// ���݁A�V�X�e���Ɍq�����Ă���J�����̐���ݒ肷��
	public static void setSystemCameraSize(int i)
	{
		systemCameraNo=i;
	}


	// ���݁A�V�X�e���Ɍq�����Ă���v�����^�[�̐���ݒ肷��
	public static void setSystemPlanterSize(int i)
	{
		//systemPlanterNo=i;
	}
	
	// ���݁A�V�X�e���Ɍq�����Ă���v�����^�[�̐��𓾂�
	public static int getSystemPlanterSize()
	{
		// 
		// �V���Ƀv�����^�[���ڑ�����Ă��Ȃ����m���߂�B
		if(systemPlanterNo <= 0) systemPlanterNo=getNplanter();
		//
		return systemPlanterNo;
	}
	
	
	public  static PlanterClass getCurrentPlanterClass()
	{
			return currentPlanterClass;
	}
	
	// �N���E�h�E�K�[�f���̏�����͂���
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
		
		// ��d�N�����Ȃ�
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
		// �i�[���������m��
		planterList=new ArrayList<PlanterClass>();
		//	USB�ɐڑ����ꂽ�A�C�e�B�v�����^�[�̐��𒲂ׂ�
		
		//systemPlanterNo=getNplanter();
		systemPlanterNo=getSystemPlanterNumber();
System.out.println("planter="+systemPlanterNo);

		int spn=systemPlanterNo;
		
		if(spn==0){
			planterList.add(new PlanterClass("DUMMY_PLANTER"));
System.out.println("�_�~�[�v�����^�[���v�����^�[���X�g�ɓo�^ DUMMY_PLANTER");
		} else {
		// �����łł͂P��̃A�C�e�B�v�����^�[�����F�����Ȃ�
		if(Version.getFreeOrPaid().contains("Free")==true) spn=1;
		
		//  �v�����^�[���X�g�ɓo�^
		for(int i=0;i<spn;i++){
			planterList.add(new PlanterClass("ITPLANTER-"+i));
System.out.println("�v�����^�[���X�g�ɓo�^ ITPLANTER-"+i);
			}
		}
// BUG : �ʃv���Z�X�� systemPlanerNo���O�ɂ��Ă��܂�?
//	System.out.println("systemPlanterNo 1= "+systemPlanterNo);
		systemPlanterNo=spn;
//	System.out.println("systemPlanterNo 2= "+systemPlanterNo);
	
		//�@USB�ɐڑ����ꂽ�J�����̐��𒲂ׂ�
		systemCameraNo=1;// default
	    // if there are no camera entry then DUMMY_CAM should be entry.
		String oldCamName="CAM-0";
		PlanterClass p=getPlanterList().get(ITPlanterClass.getCurrentPlanterNo());
		Information inf=p.getInformation();
		inf.setCameraName(0, oldCamName);

		
		// �f�t�H���g�̃J�����g��ݒ肷��
		currentPlanterNo=0;
		currentPlanterClass=planterList.get(0);
		
//		System.out.println("systemCamNo="+systemCameraNo);
		//
//		state=true;// ITPlanterClass can be used.
	}
	
	// �V�X�e���Ɍq�����Ă���J�����̐���Ԃ�
	public static int getSystemCameraSize()
	{
		return systemCameraNo;
	}

	

	// PlanterList���擾����
	public static ArrayList<PlanterClass> getPlanterList()
	{
		
		return planterList;
	}
	
	private static boolean status=false;
	// PC�ɐڑ�����Ă���A�C�e�B�v�����^�[�̐��𒲂ׂ�B
	// USB�̏�Ԃ��ω������璲�ג����悤�ɂ��邱��
	
	public static void reSearchPlanter()
	{
		status=false; // reSearch Planter No
	}
	
	public static int getSystemPlanterNumber()
	{
		String result="";
		
		if( status==false ){// status��false�̎������A�v�����^�[�̉����𒲂ׂ�
			
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
			System.out.println("�v�����^�[��������Ȃ�������������܂���B\n�v�����^�[�̓d�������꒼���Ă݂Ă��������B");
			itp_Logger.logger.info("can not find planter");
			return 0;
				}
			}	
		} 
//		systemPlanterNo=2;
		return systemPlanterNo;
	}
	
	// DUMMYPLANTER���܂߂āA�����̃v�����^�[�����邩��Ԃ��B���ۂɐڑ�����Ă���v�����^�[�̐��ł͂Ȃ��B
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
		//  ����USB�ɐڑ����ꂽ�A�C�e�B�v�����^�[���P����Ȃ���΃_�~�[�v�����^�[���P��A�o�^����
		if(spn==0){
			if(planterList.size()>0)
			planterList.set(0,new PlanterClass("DUMMY_PLANTER"));
			else
			planterList.add(new PlanterClass("DUMMY_PLANTER"));
		} else {
		//  �v�����^�[���X�g�ɓo�^
				for(int i=0;i<spn;i++){
					if(spn > planterList.size()){
					planterList.set(i,new PlanterClass("ITPLANTER-"+i));
					} 
			}
		}
		*/
		
	//}



	
}
