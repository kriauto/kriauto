package example.angularspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.angularspring.dao.CarDao;
import example.angularspring.dto.Car;
import example.angularspring.dto.Consumption;
import example.angularspring.dto.Course;
import example.angularspring.dto.Item;
import example.angularspring.dto.Location;
import example.angularspring.dto.Notification;
import example.angularspring.dto.Speed;
import example.angularspring.dto.Statistic;

@Service("carService")
public class CarServiceImpl implements CarService {
	
	@Autowired
	CarDao cardao;
	
	@Override
	public Car getCarByDevice(Integer deviceid) {
		// TODO Auto-generated method stub
		return cardao.getCarByDevice(deviceid);
	}
	
	@Override
	public void updateCar(Car car) {
		// TODO Auto-generated method stub
		cardao.updateCar(car);
	}

	@Override
	public List<Car> getAllCarsByToken(boolean group, String token) {
		// TODO Auto-generated method stub
		return cardao.getAllCarsByToken(group, token);
	}

	@Override
	public List<Item> getAllDatesByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		return cardao.getAllDatesByCar(deviceid);
	}
	
	@Override
	public List<Item> getAllDatesByToken(String token) {
		// TODO Auto-generated method stub
		return cardao.getAllDatesByToken(token);
	}

	@Override
	public List<Location> getAllLocationsByToken(String token, String date) {
		// TODO Auto-generated method stub
		return cardao.getAllLocationsByToken(token,date);
	}

	@Override
	public List<Location> getAllLocationsByCar(Integer deviceid, String date) {
		// TODO Auto-generated method stub
		return cardao.getAllLocationsByCar(deviceid,date);
	}

	@Override
	public List<Course> getTotalCourseByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		return cardao.getTotalCourseByCar(deviceid);
	}

	@Override
	public List<Speed> getMaxSpeedByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		return cardao.getMaxSpeedByCar(deviceid);
	}

	@Override
	public List<Consumption> getTotalConsumptionByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		return cardao.getTotalConsumptionByCar(deviceid);
	}

	@Override
	public Statistic getCarStatistic(Integer deviceid, String date) {
		// TODO Auto-generated method stub
		return cardao.getCarStatistic(deviceid, date);
	}

	@Override
	public List<Notification> getDataNotification(int type) {
		// TODO Auto-generated method stub
		return cardao.getDataNotification(type);
	}

	@Override
	public Location getLastPositionByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		return cardao.getLastLocationByCar(deviceid);
	}

	@Override
	public List<Location> getAllLocationByCarTime(Integer deviceid, String time) {
		// TODO Auto-generated method stub
		return cardao.getAllLocationByCarTime(deviceid, time);
	}

	@Override
	public void initGeoFence() {
		// TODO Auto-generated method stub
		cardao.initGeoFence();
	}
}
