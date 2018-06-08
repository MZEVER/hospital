package dto;

public class patient {
	private String id;
	private String name;
	private String pno;
	private String address;
	private String sex;
	private String email;
	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getpno() {
		return pno;
	}

	public void setpno(String pno) {
		this.pno = pno;
	}
	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}
	
	public String getsex() {
		return sex;
	}

	public void setsex(String sex) {
		this.sex = sex;
	}
	public String getemail() {
		return email;
	}

	public void setmail(String email) {
		this.email = email;
	}
}
