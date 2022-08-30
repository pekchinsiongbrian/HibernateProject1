package com.dxc.HibernateProject1;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.dxc.entity.Employee;

/**
 * Hello world!
 *
 */
public class App {
	private static SessionFactory factory;
    public static void main( String[] args ) {
        try {
        	// create factory object
        	factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
        	System.err.println("Failed to create session instance.");
        	throw new ExceptionInInitializerError(e);
        }
        
        App employee = new App();
        
        // Add employee details
        Integer empId1 = employee.addEmployee("Nuthan", "Preeth", 1000);
        Integer empId2 = employee.addEmployee("Rudresh", "Aradya", 5000);
        Integer empId3 = employee.addEmployee("Akhil", "Banagiri", 3000);
    }
    
    public Integer addEmployee(String fname, String lname, int salary) {
    	// session object
    	Session session = factory.openSession();
    	Transaction tx = null;
    	Integer employeeId = null;
    	
    	try {
    		tx = session.beginTransaction();
    		Employee employee = new Employee(fname, lname, salary);
    		employeeId = (Integer) session.save(employee);
    		tx.commit();
    	} catch (HibernateException e) {
    		if (tx != null) {
    			tx.rollback();
    			e.printStackTrace();
    		}
    	} finally {
    		session.close();
    	}
    	return employeeId;
    }
}
