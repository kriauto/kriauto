package example.angularspring.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import example.angularspring.dto.Car;
import example.angularspring.dto.Consumption;
import example.angularspring.dto.Course;
import example.angularspring.dto.Item;
import example.angularspring.dto.Location;
import example.angularspring.dto.Notification;
import example.angularspring.dto.Speed;
import example.angularspring.dto.Statistic;

@Repository
@Qualifier("carDao")
public class CarDaoImpl implements CarDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static int countkey = 0 ;
	
	@Override
	public Car getCarByDevice(Integer deviceid) {
		System.out.println("getCarByDevice "+deviceid);
		try{
		   Car car = (Car) jdbcTemplate.queryForObject("SELECT * FROM car where deviceid = ?", new Object[] { deviceid }, new BeanPropertyRowMapper(Car.class));
		   return car;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void updateCar(Car car) {
	     System.out.println("updateCar "+car);
	     jdbcTemplate.update("UPDATE car set agencyid =  ?, imei =  ?, simnumber =  ?, immatriculation =  ?, vin =  ?, mark =  ?, model =  ?, color =  ?, photo =  ?, status =  ?, deviceid =  ? , maxspeed =  ? , mileage =  ? , fuel =  ?, latitude1 =  ?, longitude1 =  ?, latitude2 =  ?, longitude2 =  ?, latitude3 =  ?, longitude3 =  ?, latitude4 =  ?, longitude4 =  ?, latitude5 =  ?, longitude5 =  ?, latitude6 =  ?, longitude6 =  ?, isgeofence =  ?, isnotifgeofence =  ?, isnotifdefaultgeofence =  ?  WHERE id = ?  ", new Object[] { car.getAgencyid(), car.getImei(), car.getSimnumber(), car.getImmatriculation(), car.getVin(), car.getMark(), car.getModel(), car.getColor(), car.getPhoto(), car.getStatus(), car.getDeviceid(), car.getMaxspeed(), car.getMileage(), car.getFuel(), car.getLatitude1(), car.getLongitude1(), car.getLatitude2(), car.getLongitude2(), car.getLatitude3(), car.getLongitude3(), car.getLatitude4(), car.getLongitude4(), car.getLatitude5(), car.getLongitude5(), car.getLatitude6(), car.getLongitude6(), car.isIsgeofence(), car.isIsnotifgeofence(), car.isIsnotifdefaultgeofence(), car.getId()});
	}

	@Override
	public List<Car> getAllCarsByToken(boolean group, String token) {
		System.out.println("getAllCarsByToken " + group + "," + token);
		List<Car> cars = new ArrayList<Car>();
		List<Car> carstmp = new ArrayList<Car>();
		if (group) {
			cars.add(new Car("Toutes", 111111));
		}
		carstmp = jdbcTemplate.query("select c.* "
				+ " from profile p, agency a, car c " + " where p.token = ? "
				+ " and p.agencyid = a.id " + " and a.id = c.agencyid order by c.immatriculation",
				new Object[] { token }, new BeanPropertyRowMapper(Car.class));
		cars.addAll(carstmp);
		return cars;
	}

	@Override
	public List<Item> getAllDatesByCar(Integer deviceid) {
		System.out.println("getAllDatesByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Item> dates = new ArrayList<Item>();
		dates = jdbcTemplate.query(" select distinct to_char(servertime,'YYYY-MM-DD') as code, to_char(servertime,'YYYY-MM-DD') as label"
						+ "  from  positions" 
				        + "  where deviceid = ? "
				        + "  and   valid = true "
						+ "  and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day + "'" + " order by code desc",
				new Object[] { deviceid }, new BeanPropertyRowMapper(
						Item.class));
		return dates;
	}

	@Override
	public List<Item> getAllDatesByToken(String token) {
		System.out.println("getAllDatesByToken " + token);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Item> dates = new ArrayList<Item>();

		dates = jdbcTemplate.query(" select distinct to_char(ps.servertime,'YYYY-MM-DD') as code,to_char(ps.servertime,'YYYY-MM-DD') as label"
						+ " from car c, agency a, positions ps, profile p "
						+ " where p.token = ? "
						+"  and   p.agencyid = a.id "
						+"  and   a.id = c.agencyid "
						+"  and   c.deviceid = ps.deviceid  "
						+ " and   ps.valid = true "
						+ " and   to_char(ps.servertime,'YYYY-MM-DD') >= '"+ day + "'"
						+ " order by code desc",
						new Object[] { token }, new BeanPropertyRowMapper(Item.class));
		return dates;
	}

	@Override
	public List<Location> getAllLocationsByToken(String token, String date) {
		System.out.println("getAllLocationsByToken " + token);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct c.*, ps.* "
				+ " from profile p, agency a, car c, devices d, positions ps "
				+ " where p.token = ? "
				+ " and   p.agencyid = a.id "
				+ " and   a.id = c.agencyid "
				+ " and   c.deviceid = ps.deviceid "
				+ " and   to_char(ps.servertime,'YYYY-MM-DD') = '"+ date + "'"
				+ " and   ps.valid = true "
				+ " and   (ps.deviceid, ps.servertime) IN ( "
				+ " select distinct ps.deviceid, max(ps.servertime) "
				+ " from profile p, agency a, car c, devices d, positions ps "
				+ " where p.token = ? "
				+ " and   p.agencyid = a.id "
				+ " and   a.id = c.agencyid "
				+ " and   c.deviceid = ps.deviceid "
				+ " and   to_char(ps.servertime,'YYYY-MM-DD') = '"+ date + "'"
				+ " and   ps.valid = true "
				+ " group by ps.deviceid) ",new Object[] { token, token }, new BeanPropertyRowMapper(Location.class));
		for(int i = 0; i<locations.size(); i++){
			Location loc = locations.get(i);
			loc.setAddress(getGoodleAdresse(loc.getLatitude(), loc.getLongitude()));
		}
		return locations;
	}

	@Override
	public List<Location> getAllLocationsByCar(Integer deviceid, String date) {
		System.out.println("getAllLocationsByCar " + deviceid);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct c.*, ps.* "
				+ " from positions ps, car c "
				+ " where c.deviceid = ps.deviceid"
				+ " and   ps.deviceid = ? "
				+ " and   ps.valid = true "
				+ " and   to_char(ps.servertime,'YYYY-MM-DD') = '"+ date + "'"
				+ " and   ps.valid = true "
				+ " order by ps.deviceid ",new Object[] { deviceid}, new BeanPropertyRowMapper(Location.class));
		return locations;
	}

	@Override
	public List<Course> getTotalCourseByCar(Integer deviceid) {
		System.out.println("getTotalCourseByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Course> cours = new ArrayList<Course>();
		cours = jdbcTemplate
				.query(" select distinct to_char(servertime,'YYYY-MM-DD') AS day, SUM(course) AS totalCourse "
						+ " from positions "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day
						+ "' "
						+ " and   valid = true "
						+ " group by deviceid,to_char(servertime,'YYYY-MM-DD') "
						+ " order by day desc", new Object[] { deviceid },
						new BeanPropertyRowMapper(Course.class));
		for(int i = 0; i<cours.size(); i++){
			Course cor = cours.get(i);
			cor.setTotalCourse(String.valueOf(Integer.valueOf(cor.getTotalCourse())/1000));
		}
		return cours;
	}

	@Override
	public List<Speed> getMaxSpeedByCar(Integer deviceid) {
		System.out.println("getMaxSpeedByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Speed> speed = new ArrayList<Speed>();
		speed = jdbcTemplate
				.query(" select distinct to_char(servertime,'YYYY-MM-DD') AS day, MAX(speed) AS maxSpeed "
						+ " from positions ps "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day
						+ "' "
						+ " and   valid = true "
						+ " group by to_char(servertime,'YYYY-MM-DD') "
						+ " order by day desc", new Object[] { deviceid },
						new BeanPropertyRowMapper(Speed.class));
		for(int i = 0; i<speed.size(); i++){
			Speed spe = speed.get(i);
			spe.setMaxSpeed(String.valueOf(Integer.valueOf(spe.getMaxSpeed())*1.85));
		}
		return speed;
	}

	@Override
	public List<Consumption> getTotalConsumptionByCar(Integer deviceid) {
		System.out.println("getTotalConsumptionByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Consumption> consumption = new ArrayList<Consumption>();
		consumption = jdbcTemplate
				.query(" select distinct to_char(servertime,'YYYY-MM-DD') AS day, SUM(course) AS consumption "
						+ " from positions ps "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day
						+ "' "
						+ " and   valid = true "
						+ " group by to_char(servertime,'YYYY-MM-DD') "
						+ " order by day desc", new Object[] { deviceid },
						new BeanPropertyRowMapper(Consumption.class));
		for(int i = 0; i<consumption.size(); i++){
			Consumption con = consumption.get(i);
			con.setConsumption(String.valueOf((Float.valueOf(con.getConsumption())/100000)*5));
		}
		return consumption;
	}
	
	@Override
	public Statistic getCarStatistic(Integer deviceid, String date) {
		System.out.println("getCarStatistic " + deviceid+date);
		Statistic statistic = new Statistic();
		double speed = 0, cours=0;
		try{
		speed = jdbcTemplate
				.queryForLong(" select MAX(speed) "
						+ " from positions ps "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') = ? "
						+ " and   valid = true ", new Object[] { deviceid, date });
		} catch (EmptyResultDataAccessException e) {

		}
		try{
		cours = jdbcTemplate
				.queryForLong(" select SUM(course) "
						+ " from positions "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') = ? "
						+ " and   valid = true ", new Object[] { deviceid, date });
		} catch (EmptyResultDataAccessException e) {

		}
		statistic.setConsumption((double)Math.round(((cours/100000)*5)*100)/100);
		statistic.setSpeed((double)Math.round((speed*1.85)*100)/100);
		statistic.setCourse((double)Math.round((cours/1000)*100)/100);
		return statistic;
	}
	
	public String getGoodleAdresse(Double Lat, Double Lng){
		GeoApiContext gtx = new GeoApiContext().setApiKey("AIzaSyD-w27Lhidw00LPBW7UWHp1TBPv4O3v650");
		GeocodingResult[] gResp = null ;
		try 
		  {
		    gResp = GeocodingApi.newRequest(gtx).latlng(new LatLng(Lat, Lng)).await();
		    System.out.println(gResp[0].formattedAddress);
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	    return gResp[0].formattedAddress;
	}

	@Override
	public List<Notification> getDataNotification(int type) {
		String query = "";
		if(type == 1){
			query = " select distinct p.*, c.* "
					+ " from profile p, car c, agency a  "
					+ " where p.agencyid = a.id "
					+ " and   a.id = c.agencyid "
					+ " and   c.status = 2 ";
		}
		if(type == 2){
			query = " select distinct p.*, c.* "
					+ " from profile p, car c, agency a  "
					+ " where p.agencyid = a.id "
					+ " and   a.id = c.agencyid "
					+ " and   c.isgeofence = true "
					+ " and   c.isnotifgeofence = false ";
		}
		if(type == 3){
			query = " select distinct p.*, c.* "
					+ " from profile p, car c, agency a  "
					+ " where p.agencyid = a.id "
					+ " and   a.id = c.agencyid "
					+ " and   c.isnotifdefaultgeofence = false ";
		}
		
		List<Notification> notifications = new ArrayList<Notification>();
		notifications = jdbcTemplate.query(query, new Object[] {},new BeanPropertyRowMapper(Notification.class));
		// TODO Auto-generated method stub
		return notifications;
	}

	@Override
	public Location getLastLocationByCar(Integer deviceid) {
		System.out.println("getAllLocationsByCar " + deviceid);
        try
        {
        	Location location = jdbcTemplate.queryForObject(" select distinct c.*, ps.* "
				    + " from positions ps, car c "
				    + " where ps.id =  (select MAX(id) from positions where deviceid = ? ) "
				    + " and   ps.deviceid = c.deviceid ",new Object[] { deviceid}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Location> getAllLocationByCarTime(Integer deviceid, String time) {
		System.out.println("getAllLocationsByCar " + deviceid);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.* "
				+ " from  positions ps "
				+ " where ps.deviceid = ? "
				+ " and   ps.valid = true "
				+ " and   ps.servertime  >= '"+ time + "'"
				+ " order by ps.servertime ",new Object[] { deviceid }, new BeanPropertyRowMapper(Location.class));
		return locations;
	}

	@Override
	public void initGeoFence() {
		System.out.println("initGeoFence ");
	    jdbcTemplate.update("UPDATE car set isnotifgeofence =  false, isnotifdefaultgeofence =  false ", new Object[] {});
		
	}
}
