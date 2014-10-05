
public class PlanterInformation {
	private PlanterClass planterClass=null;
	
	// constructor
	public PlanterInformation(PlanterClass p) {
		setCurrentPlanterClass(p);
	}
	
	public PlanterClass getCurrentPlanterClass() {
		return planterClass;
	}
	
	public void setCurrentPlanterClass(PlanterClass currentPlanterClass) {
		this.planterClass = currentPlanterClass;
	}

	public Information getInfor()
	{
		return planterClass.getInformation();
	}
	
	public Sensor getSensor()
	{
		return planterClass.getSensor();
	}
	
	public Control getControl()
	{
		return planterClass.getControl();
	}
}
