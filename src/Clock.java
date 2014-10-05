
public class Clock  {
	int hour=0;
	int minutes=0;
	int second=0;
	
	public Clock(int h, int m, int s)
	{
		hour=h;
		minutes=m;
		second=s;
	}
	
	public String getClock()
	{
		return "h "+hour+" m "+minutes+" s "+second;
	}

	void setClock(int h, int m, int s)
	{
		hour=h;
		minutes=m;
		second=s;
	}

}
