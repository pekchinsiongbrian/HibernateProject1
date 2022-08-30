package com.dxc.HibernateProject1;

import java.util.Iterator;
import java.util.List;

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
//        Integer empId1 = employee.addEmployee("Nuthan", "Preeth", 1000);
//        Integer empId2 = employee.addEmployee("Rudresh", "Aradya", 5000);
        Integer empId3 = employee.addEmployee("Akhil", "Banagiri", 3000);
        
        // List employee details
//        employee.listEmployees();
        
        // Update employee details
//        employee.updateEmployee(1, 5000);
        
        // Delete employee details
//        employee.deleteEmployee(3);
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
    		}
    		e.printStackTrace();
    	} finally {
    		session.close();
    	}
    	return employeeId;
    }
    
    public void listEmployees() {
    	Session session = factory.openSession();
        Transaction tx = null;
        
        try {
           tx = session.beginTransaction();
           List employees = session.createQuery("FROM Employee").list();
           Iterator iter = employees.iterator();
           while (iter.hasNext()) {
              Employee employee = (Employee) iter.next(); 
              System.out.print("First Name: " + employee.getFirstName()); 
              System.out.print(" | Last Name: " + employee.getLastName()); 
              System.out.println(" | Salary: " + employee.getSalary()); 
           }
           tx.commit();
        } catch (HibernateException e) {
           if (tx != null) {
        	   tx.rollback();
           }
           e.printStackTrace();
        } finally {
           session.close(); 
        }
    }
    
	public void updateEmployee(Integer employeeId, int salary){
	    Session session = factory.openSession();
	    Transaction tx = null;
	    
	    try {
	       tx = session.beginTransaction();
	       Employee employee = (Employee) session.get(Employee.class, employeeId); 
	       employee.setSalary(salary);
	       session.update(employee); 
	       tx.commit();
	    } catch (HibernateException e) {
	       if (tx != null) {
	    	   tx.rollback();
	       }
	       e.printStackTrace(); 
	    } finally {
	       session.close(); 
	    }
	 }
    
	public void deleteEmployee(Integer employeeId){
	    Session session = factory.openSession();
	    Transaction tx = null;
	    
	    try {
	       tx = session.beginTransaction();
	       Employee employee = (Employee) session.get(Employee.class, employeeId); 
	       session.delete(employee); 
	       tx.commit();
	    } catch (HibernateException e) {
	       if (tx != null) {
	    	   tx.rollback();
	       }
	       e.printStackTrace(); 
	    } finally {
	       session.close(); 
	    }
	 }
}
