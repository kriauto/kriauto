package example.angularspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.angularspring.dao.CustomerDao;
import example.angularspring.dto.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired 
	CustomerDao customerdao;

	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerdao.addCustomer(customer);
	}

	@Override
	public void deleteCustomer(Integer id) {
		// TODO Auto-generated method stub
		customerdao.deleteCustomer(id);
	}

	@Override
	public Customer getCustomer(Integer id) {
		// TODO Auto-generated method stub
		return customerdao.getCustomer(id);
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerdao.updateCustomer(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerdao.getAllCustomers();
	}

	@Override
	public Integer getCustomerSeq() {
		// TODO Auto-generated method stub
		return customerdao.getCustomerSeq();
	}

}
