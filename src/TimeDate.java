import java.util.Calendar;


public class TimeDate {
    Calendar calender = Calendar.getInstance();
    public int getHour()
    {
    	return calender.get(Calendar.HOUR_OF_DAY);
    }
    
    public int getMin()
    {
    	return calender.get(Calendar.MINUTE);
    }
    
    public int getDate()
    {
    	return calender.get(Calendar.DATE);
    }

    public int getMonth()
    {
    	return calender.get(Calendar.MONTH)+1;
    }

    public int getYear()
    {
    	return calender.get(Calendar.YEAR);
    }

	public int getDay() {
		return calender.get(Calendar.DAY_OF_MONTH);
	}


    public static void main(String[] args) {
	TimeDate td=new TimeDate();
	System.out.println("year="+td.getYear());
	System.out.println("month="+td.getMonth());
	System.out.println("hour="+td.getHour());
	System.out.println("min="+td.getMin());
	}

    
}
