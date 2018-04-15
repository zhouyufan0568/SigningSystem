package global;

public class CheckInfo {
	
	private String id = null;
	private String date = null;
	private String signin = null;
	private String signout = null;

	public CheckInfo(String id, String date, String signin, String signout) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.date = date;
		this.signin = signin;
		this.signout = signout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSignin() {
		return signin;
	}

	public void setSignin(String signin) {
		this.signin = signin;
	}

	public String getSignout() {
		return signout;
	}

	public void setSignout(String signout) {
		this.signout = signout;
	}

}
