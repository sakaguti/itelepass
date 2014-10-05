import java.sql.Time;
import java.util.ArrayList;


public class Control {

	private Time[] lampTime=null;
	private ArrayList<Time[]> lampSchedule;
	private ArrayList<Time> pumpSchedule;
	private ArrayList<Duty> dutySchedule;
	private ArrayList<Time> cameraSchedule;
	private PlanterClass currentPlanter=null;


	public Control(PlanterClass planterClass) {
		this.currentPlanter = planterClass;// 親クラスを保持する　　親クラスは子クラスから見えるのか？
	}

	//
	public void setLamp(boolean b)
	{
		// Lamp status b false:OFF true:ON
	}
	
	public void setPump(boolean b)
	{
		// Pump status b false:OFF, true:ON
	}
	
	public void setMode(int i)
	{
		// set mode  0:AUTO, 1:PC, 2:Manual
	}
	
	public void setPrgMode(int n)
	{
		// 0: Daily mode, 1:Night mode
	}
	
	//
	//	addLampTime( Lamp ON Time, Lamp OFF Time)
	//
	public void addLampTime(Time tON, Time tOFF)
	{
		lampTime=new Time[2];
		lampTime[0]=tON;
		lampTime[1]=tOFF;
		lampSchedule.add(lampTime);
	}
	
	//
	// addPumpTIme(Pump ON Time)
	//
	public void addPumpTime(Time t)
	{
		pumpSchedule.add(t);
	}
	
	//
	// addDutyTime((duty.setDuty(duty), duty.setTime(time)))
	//
	public void addDutyTime(Duty d)
	{
		dutySchedule.add(d);
	}
	
	public int getLampScheduleSize()
	{
		return lampSchedule.size();
	}
	
	public int getPumpScheduleSize()
	{
		return pumpSchedule.size();
	}

	public ArrayList<Time> getCameraSchedule() {
		return cameraSchedule;
	}

	public void setCameraSchedule(ArrayList<Time> cameraSchedule) {
		this.cameraSchedule = cameraSchedule;
	}

	public int getCurrentPlanterNo() {
		return this.currentPlanter.getPlanterNo();
	}

	public PlanterClass getCurrentPlanter() {
		return currentPlanter;
	}
	
}
