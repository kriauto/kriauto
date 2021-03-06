package example.angularspring.service;

import java.util.List;

import example.angularspring.dto.Car;
import example.angularspring.dto.Consumption;
import example.angularspring.dto.Course;
import example.angularspring.dto.Item;
import example.angularspring.dto.Location;
import example.angularspring.dto.Notification;
import example.angularspring.dto.Speed;
import example.angularspring.dto.Statistic;

public interface CarService {
	public Car getCarByDevice(Integer deviceid);
	public void updateCar(Car car);
	public List<Car> getAllCarsByToken(boolean group, String token);
	public List<Item> getAllDatesByCar(Integer deviceid);
	public List<Item> getAllDatesByToken(String token);
	public List<Location> getAllLocationsByToken(String token, String date);
	public List<Location> getAllLocationsByCar(Integer deviceid, String date);
	public List<Course> getTotalCourseByCar(Integer deviceid);
	public List<Speed> getMaxSpeedByCar(Integer deviceid);
	public List<Consumption> getTotalConsumptionByCar(Integer deviceid);
	public Statistic getCarStatistic(Integer deviceid, String date);
	public List<Notification> getDataNotification(int type);
	public Location getLastPositionByCar(Integer deviceid);
	public List<Location> getAllLocationByCarTime(Integer deviceid, String time);
	public void initGeoFence();
}
