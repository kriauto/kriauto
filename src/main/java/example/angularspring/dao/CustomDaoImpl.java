package example.angularspring.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import example.angularspring.dto.Customer;;


@Repository
@Qualifier("customerDao")
public class CustomDaoImpl implements CustomerDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		System.out.println("addCustomer ");
		Timestamp date_modification = new Timestamp(System.currentTimeMillis());
	    jdbcTemplate.update(" INSERT INTO customer(id, login, password, last_name, first_name, city, address, mail, phone, cin, scan_cin, scan_driver_license,createdby, modifiedby,date_modification) "
	    		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] {customer.getId(),customer.getLogin(),customer.getPassword(),customer.getLast_name(),customer.getFirst_name(),customer.getCity(),customer.getAddress(),customer.getMail(),customer.getPhone(),customer.getCin(),customer.getScan_cin(),customer.getScan_driver_license(),customer.getCreatedby(),customer.getModifiedby(),date_modification});
	}

	@Override
	public void deleteCustomer(Integer id) { 
		// TODO Auto-generated method stub
		System.out.println("deleteCustomer ");
	    jdbcTemplate.update("DELETE FROM customer WHERE id = ? ", new Object[] {id});
		
	}

	@Override
	public Customer getCustomer(Integer id) {
		// TODO Auto-generated method stub
		System.out.println("getCustomer ");
		try
        {
        	Customer customer = (Customer)jdbcTemplate.queryForObject(" SELECT * FROM Customer WHERE id = ? ",new Object[] { id}, new BeanPropertyRowMapper(Customer.class));
        	return customer;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		System.out.println("updateCustomer ");
	    jdbcTemplate.update(" UPDATE customer SET last_name = ? , first_name = ? , city = ? , address = ? , mail = ? , phone = ? , cin = ? , scan_cin = ? , scan_driver_license = ? WHERE id =  ? ", new Object[] {customer.getLast_name(),customer.getFirst_name(),customer.getCity(),customer.getAddress(),customer.getMail(),customer.getPhone(),customer.getCin(),customer.getScan_cin(),customer.getScan_driver_license(),customer.getId()});
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		List<Customer> customers = new ArrayList<Customer>();
		customers = jdbcTemplate.query(" SELECT * FROM customer ",new Object[] {}, new BeanPropertyRowMapper(Customer.class));
		return customers;
	}

	@Override
	public Integer getCustomerSeq() {
		// TODO Auto-generated method stub
		try
          {
		   Integer seq = (Integer) jdbcTemplate.queryForObject(" SELECT nextval('customer_seq_id') ",Integer.class);
		   return seq;
          }catch(EmptyResultDataAccessException e) {
		   return null;
		  }
	}

}
