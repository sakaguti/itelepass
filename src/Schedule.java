

public class Schedule {
	private String startTime="";
	private String endTime="";
	private String continueTime="";
	private String value="";
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime() {
		/*
		String[] hm=startTime.split(":");
		int m=Integer.valueOf(hm[0])*60+Integer.valueOf(hm[1]);// •ªŒ`Ž®‚É•ÏŠ·‚·‚é
		return String.valueOf(m);
		*/
		return startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime() {
		/*
		String[] hm=endTime.split(":");
		int m=Integer.valueOf(hm[0])*60+Integer.valueOf(hm[1]);// •ªŒ`Ž®‚É•ÏŠ·‚·‚é
		return String.valueOf(m);
		*/
		return endTime;
	}
	public void setContinueTime(String continueTime) {
		this.continueTime = continueTime;
	}
	public String getContinueTime() {
		return continueTime;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	
}
