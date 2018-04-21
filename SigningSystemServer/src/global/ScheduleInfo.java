package global;

public class ScheduleInfo {
	private String classname = null;
	private int day = 0;
	private int cindex = 0;
	private String cname = null;

	public ScheduleInfo(String classname, int day, int cindex, String cname) {
		// TODO Auto-generated constructor stub
		this.classname = classname;
		this.day = day;
		this.cindex = cindex;
		this.cname = cname;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getCindex() {
		return cindex;
	}

	public void setCindex(int cindex) {
		this.cindex = cindex;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
