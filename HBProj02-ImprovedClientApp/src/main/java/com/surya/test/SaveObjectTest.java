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
		System.out.println(cfg.getProperties());
		// build SessionFactory having all services specified in cfg file and mapping file.
		SessionFactory factory = cfg.buildSessionFactory();
		System.out.println(factory.getProperties());
		System.out.println("Session factory object class name:: " + factory.getClass());

		// create Session object
		Session ses = factory.openSession();
		System.out.println("Session object class name:: " + ses.getClass());
		// Every Non-select query is work on TXmgmt Principle i.e We should be in a
		// position to commit or rollback persistance Activity.
		Transaction tx = null;
		boolean flag = false;
		try {
			// begin Transaction
			tx = ses.beginTransaction(); // Internally calls con.setAutoCommit(false) to disable autoCommit mode on DB
											// s/w
			// prepare entity object
			Product prod = new Product();
			prod.setPid(1006);
			prod.setPname("cot4");
			prod.setPrice(2458.5f);
			prod.setQty(1.0f);
			// save object
			Integer idVal = (Integer) ses.save(prod); // Gives persistance instruction to hibernate to save object
														// (insert to save object data as the record )
			System.out.println("The Gernerated id value :: " + idVal);
			flag = true;
		} catch (HibernateException he) {
			he.printStackTrace();
			flag = false;
		} finally {
			if (flag) {
				tx.commit(); // internally calls con.commit() method to make insertion execution result
								// permanent.
				System.out.println("Object is  saved [ Record is inserted ]");
			} else {
				tx.rollback(); // internally calls con.rollback() method to rollback the results of query
								// execution.
				System.out.println("Object is  saved [ Record is not inserted ]");
			}
			// Close Session obj
			try {
				if (ses != null)
					ses.close();
			} catch (HibernateException he1) {
				he1.printStackTrace();
			}
			try {
				if (factory != null)
					factory.close();

			} catch (HibernateException he2) {
				he2.printStackTrace();
			}
		}

	} // main method

} // class
