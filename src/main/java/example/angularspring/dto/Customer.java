package example.angularspring.dto;

import java.sql.Timestamp;

public class Customer {
	private Integer id;
	private String login ;
	private String password;
	private String last_name;
	private String first_name;
	private String city;
	private String address;
	private String mail;
	private String phone;
	private String cin; 
	private String scan_cin;
	private String scan_driver_license;
	private Timestamp date_creation;
	private Integer createdby;
	private Timestamp date_modification;
	private Integer modifiedby;
	
	public Customer() {
		super();
	}

	public Customer(Integer id, String login, String password,
			String last_name, String first_name, String city, String address,
			String mail, String phone, String cin, String scan_cin,
			String scan_driver_license, Timestamp date_creation,
			Integer createdby, Timestamp date_modification, Integer modifiedby) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.last_name = last_name;
		this.first_name = first_name;
		this.city = city;
		this.address = address;
		this.mail = mail;
		this.phone = phone;
		this.cin = cin;
		this.scan_cin = scan_cin;
		this.scan_driver_license = scan_driver_license;
		this.date_creation = date_creation;
		this.createdby = createdby;
		this.date_modification = date_modification;
		this.modifiedby = modifiedby;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getScan_cin() {
		return scan_cin;
	}

	public void setScan_cin(String scan_cin) {
		this.scan_cin = scan_cin;
	}

	public String getScan_driver_license() {
		return scan_driver_license;
	}

	public void setScan_driver_license(String scan_driver_license) {
		this.scan_driver_license = scan_driver_license;
	}

	public Timestamp getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Timestamp date_creation) {
		this.date_creation = date_creation;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Timestamp getDate_modification() {
		return date_modification;
	}

	public void setDate_modification(Timestamp date_modification) {
		this.date_modification = date_modification;
	}

	public Integer getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", login=" + login + ", password="
				+ password + ", last_name=" + last_name + ", first_name="
				+ first_name + ", city=" + city + ", address=" + address
				+ ", mail=" + mail + ", phone=" + phone + ", cin=" + cin
				+ ", scan_cin=" + scan_cin + ", scan_driver_license="
				+ scan_driver_license + ", date_creation=" + date_creation
				+ ", createdby=" + createdby + ", date_modification="
				+ date_modification + ", modifiedby=" + modifiedby + "]";
	}
	
}
