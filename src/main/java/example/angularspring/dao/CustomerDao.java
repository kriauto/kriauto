package example.angularspring.dao;

import java.util.List;

import example.angularspring.dto.Customer;

public interface CustomerDao {
	public void addCustomer(Customer customer);
	public void deleteCustomer(Integer id);
	public Customer getCustomer(Integer id);
	public void updateCustomer(Customer customer);
	public List<Customer> getAllCustomers();
	public Integer getCustomerSeq();

}
