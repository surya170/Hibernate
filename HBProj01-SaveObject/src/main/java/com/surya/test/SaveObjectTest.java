package com.surya.test;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.surya.entity.Product;

public class SaveObjectTest {

	public static void main(String[] args) {

		// Bootstrap / Activate the hibernate
		Configuration cfg = new Configuration();
		// specify the hibernate cfg file
		cfg.configure("com/surya/cfgs/hibernate.cfg.xml");
		// build SessionFactory having all services specified in cfg file and mapping file.
		SessionFactory factory = cfg.buildSessionFactory();
		//create Session object
		Session ses = factory.openSession();
		//Every Non-select  query is work on  TXmgmt Principle i.e We should be in a position to commit or  rollback persistance Activity.
		Transaction tx = null;
		try{
			 //begin Transaction
			tx=ses.beginTransaction(); // Internally calls  con.setAutoCommit(false) to disable autoCommit mode on DB s/w
			//prepare entity object
			Product prod = new Product();
			prod.setPid(1001);
			prod.setPname("table");
			prod.setPrice(5678.5f);
			prod.setQty(1.0f);
			//save object
			ses.save(prod);  // Gives persistance instruction to hibernate to save object  (insert to save object data as the record ) 
			tx.commit();  // internally calls con.commit() method to make insertion execution result  permanent.
			System.out.println("Object is  saved [ Record is inserted ]");
		}catch(HibernateException he) {
			 he.printStackTrace();
			 tx.rollback();
			System.out.println("Object is  saved [ Record is not inserted ]");

		}
       //close session object
		ses.close();
	 //close sessionFactory object
		factory.close();
	} // main method

} // class
