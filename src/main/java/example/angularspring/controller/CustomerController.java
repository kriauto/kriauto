package example.angularspring.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.angularspring.dto.Customer;
import example.angularspring.dto.Profile;
import example.angularspring.dto.ResponseMessage;
import example.angularspring.service.CustomerService;
import example.angularspring.service.ProfileService;
import example.angularspring.util.Constant;

@Controller
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
	
	@Autowired
    private ProfileService profileService;
	
	private static final String rootDirectory = "C:\\PlateForme\\WorkspaceGit\\kriauto\\src\\main\\webapp\\upload\\";
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage addCustomer(@RequestHeader(value="Authorization") String authorization, @RequestParam(value = "customer") String customerin, @RequestParam(value = "scan_cin") MultipartFile scancin, @RequestParam(value = "scan_license") MultipartFile scanlicense,HttpServletRequest request) {
    	System.out.println("Begin addCustomer -->"+customerin);
    	
		try {
			// check authetication.
			String token = authorization.replaceAll("Basic", "");
			Profile profile = profileService.getProfileByToken(token);
			if(null == profile){
	    		throw new IllegalArgumentException("ACTION_FAILED");
	    	}
			
			// mapping customer.
    	    ObjectMapper mapper = new ObjectMapper();
    	    Customer customer = mapper.readValue(customerin, Customer.class);
    	    
    	    // validation request.
    	    if(null == customer || null == customer.getLast_name()){
    	    	throw new IllegalArgumentException("LASTNAME_REQUIRED");
    	    }
    	    if(null == customer || null == customer.getFirst_name()){
    	    	throw new IllegalArgumentException("FIRSTNAME_REQUIRED");
    	    }
    	    if(null == customer || null == customer.getCity()){
    	    	throw new IllegalArgumentException("CITY_REQUIRED");
    	    }
    	    if(null == customer || null == customer.getAddress()){
    	    	throw new IllegalArgumentException("ADDRESS_REQUIRED");
    	    }
    	    if(null == customer || null == customer.getCin()){
    	    	throw new IllegalArgumentException("CIN_REQUIRED");
    	    }
    	    
    	    // 
            int seq = customerService.getCustomerSeq();
            customer.setId(seq);

    	    if (null != scancin && null != scancin.getOriginalFilename()){
    	    	 String cin = String.valueOf(seq)+"_cin";
    	    	 String cinextension = "", cinname = "";
    	    	 int i = scancin.getOriginalFilename().lastIndexOf('.');
    	    	    if (i >= 0) {
    	    	    	cinextension = scancin.getOriginalFilename().substring(i+1);
    	    	    	cinname = cin+"."+cinextension;
    	    	    	customer.setScan_cin(cinname);
    	    	    	scancin.transferTo(new File(rootDirectory  + cinname));
    	    	    }
    	    }
    	   
    	    if (null != scanlicense && null != scanlicense.getOriginalFilename()){
   	    	     String license = String.valueOf(seq)+"_license";
   	    	     String licenseextension = "", licensename = "";
   	    	     int i = scanlicense.getOriginalFilename().lastIndexOf('.');
   	    	       if (i >= 0) {
   	    	    	   licenseextension = scanlicense.getOriginalFilename().substring(i+1);
   	    	    	   licensename = license+"."+licenseextension;
   	    	    	   customer.setScan_driver_license(licensename);
   	    	    	   scanlicense.transferTo(new File(rootDirectory  + licensename));
   	    	       }
   	        }
	    	customerService.addCustomer(customer);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateCustomer(@RequestHeader(value="Authorization") String authorization, @RequestParam(value = "delcin") String deletecin, @RequestParam(value = "dellicense") String dellicense, @RequestParam(value = "customer") String customerin, @RequestParam(value = "scan_cin") MultipartFile scancin, @RequestParam(value = "scan_license") MultipartFile scanlicense,HttpServletRequest request) {
    	System.out.println("Begin addCustomer -->"+customerin);
    	
		try {
			String token = authorization.replaceAll("Basic", "");
			Profile profile = profileService.getProfileByToken(token);
			if(null == profile){
	    		throw new IllegalArgumentException("ACTION_FAILED");
	    	}
    	    ObjectMapper mapper = new ObjectMapper();
    	    Customer customer = mapper.readValue(customerin, Customer.class);

    	    if ( "true".equals(deletecin) && null != scancin && null != scancin.getOriginalFilename()){
    	    	 String cin = String.valueOf(customer.getId())+"_cin";
    	    	 String cinextension = "", cinname = "";
    	    	 int i = scancin.getOriginalFilename().lastIndexOf('.');
    	    	    if (i >= 0) {
    	    	    	cinextension = scancin.getOriginalFilename().substring(i+1);
    	    	    	cinname = cin+"."+cinextension;
    	    	    	customer.setScan_cin(cinname);
    	    	    	scancin.transferTo(new File(rootDirectory  + cinname));
    	    	    }
    	    }else{
    	    	customer.setScan_cin(null);
    	    }
    	   
    	    if ("true".equals(dellicense) && null != scanlicense && null != scanlicense.getOriginalFilename()){
   	    	     String license = String.valueOf(customer.getId())+"_license";
   	    	     String licenseextension = "", licensename = "";
   	    	     int i = scanlicense.getOriginalFilename().lastIndexOf('.');
   	    	       if (i >= 0) {
   	    	    	   licenseextension = scanlicense.getOriginalFilename().substring(i+1);
   	    	    	   licensename = license+"."+licenseextension;
   	    	    	   customer.setScan_driver_license(licensename);  
   	    	    	   scanlicense.transferTo(new File(rootDirectory  + licensename));
   	    	       }
   	        }else{
   	        	customer.setScan_driver_license(null);  
   	        }
	    	customerService.updateCustomer(customer);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage deleteCustomer(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer id) {
    	System.out.println("Begin deleteCustomer -->"+id);
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
    	System.out.println("Begin getCustomer -->"+id);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token); 
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Customer customer = customerService.getCustomer(id);
    	return customer;
    }
	
	@RequestMapping(value = "/getCustomers", method = RequestMethod.POST)
    @ResponseBody
    public List<Customer> getCustomers(@RequestHeader(value="Authorization") String authorization) {
    	System.out.println("Begin getCustomers -->");
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Customer> customers = customerService.getAllCustomers();
    	return customers;
    }
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveUserDataAndFile(@RequestParam(value = "user") String userInfo, @RequestParam(value = "uploadfile") MultipartFile file,HttpServletRequest request) {
		
		System.out.println("Inside File upload" + userInfo);
		ObjectMapper mapper = new ObjectMapper();
//		try {
			//Login login = mapper.readValue(userInfo, Login.class);
			//userService.addUser(login);
			System.out.println("toto size"+file.getSize()+" Type "+file.getContentType());
//		} catch (JsonParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (JsonMappingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String rootDirectory = "C:\\PlateForme\\WorkspaceGit\\kriauto\\src\\main\\webapp\\upload\\";
		System.out.println("Root Directory "+rootDirectory);
		try {
			file.transferTo(new File(rootDirectory  + file.getOriginalFilename()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseMessage(ResponseMessage.Type.success, null,null);
		
	}
	
	private final Path rootLocation = Paths.get("C:\\temp");
	 
	public void store(MultipartFile file){
		try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
        	throw new RuntimeException("FAIL!");
        }
	}
 
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
        	throw new RuntimeException("FAIL!");
        }
    }
    
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
 
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

}
