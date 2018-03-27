package example.angularspring.dto;

public class Location {
	private double longitude;
	private double latitude;
	private double speed;
	private double course;
	private String address;
	private String servertime;
	private String immatriculation;
	private String vin;
	private String mark;
	private String model;
	private String photo;
	private String color;
	
	public Location() {
		super();
	}

	public Location(double longitude, double latitude, double speed, double course,
			String address, String servertime, String immatriculation,
			String vin, String mark, String model, String photo, String color) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.speed = speed;
		this.course = course;
		this.address = address;
		this.servertime = servertime;
		this.immatriculation = immatriculation;
		this.vin = vin;
		this.mark = mark;
		this.model = model;
		this.photo = photo;
		this.color = color;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getCourse() {
		return course;
	}

	public void setCourse(double course) {
		this.course = course;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServertime() {
		return servertime;
	}

	public void setServertime(String servertime) {
		this.servertime = servertime;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Location [longitude=" + longitude + ", latitude=" + latitude
				+ ", speed=" + speed + ", course=" + course + ", address="
				+ address + ", servertime=" + servertime + ", immatriculation="
				+ immatriculation + ", vin=" + vin + ", mark=" + mark
				+ ", model=" + model + ", photo=" + photo + ", color=" + color
				+ "]";
	}
}
