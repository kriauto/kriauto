package example.angularspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import example.angularspring.dto.Customer;
import example.angularspring.dto.Profile;
import example.angularspring.dto.ResponseMessage;
import example.angularspring.service.CustomerService;
import example.angularspring.service.ProfileService;
import example.angularspring.util.Constant;

public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
	
	@Autowired
    private ProfileService profileService;
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage addCustomer(@RequestHeader(value="Authorization") String authorization, @RequestBody Customer customer) {
    	System.out.println("Begin updateCar -->"+customer);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	customerService.addCustomer(customer);
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateCustomer(@RequestHeader(value="Authorization") String authorization, @RequestBody Customer customer) {
    	System.out.println("Begin updateCar -->"+customer);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	customerService.updateCustomer(customer);
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage deleteCustomer(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer id) {
    	System.out.println("Begin getCarByDevice -->"+id);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	customerService.deleteCustomer(id);
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/getCustomer", method = RequestMethod.POST)
    @ResponseBody
    public Customer getCustomer(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer id) {
    	System.out.println("Begin getCarByDevice -->"+id);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token); 
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Customer customer = customerService.getCustomer(id);
    	return customer;
    }
	
	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.POST)
    @ResponseBody
    public List<Customer> getAllCustomer(@RequestHeader(value="Authorization") String authorization) {
    	System.out.println("Begin getCarByDevice -->");
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Customer> customers = customerService.getAllCustomers();
    	return customers;
    }

}
