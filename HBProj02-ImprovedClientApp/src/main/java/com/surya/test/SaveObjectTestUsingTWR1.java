package com.surya.test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.surya.entity.Product;

public class SaveObjectTestUsingTWR1 {

	public static void main(String[] args) {

		// Bootstrap / Activate the hibernate
		Configuration cfg = new Configuration();
		// specify the hibernate cfg file
		cfg.configure("com/surya/cfgs/hibernate.cfg.xml");
		// build SessionFactory having all services specified in cfg file and mapping
		// file.
		Transaction tx = null;
		try (SessionFactory factory = cfg.buildSessionFactory(); Session ses = factory.openSession()) {
			// begin Transcation
			tx = ses.beginTransaction();
			// Prepare Entity Object
			Product prod = new Product();
			prod.setPid(1234);
			prod.setPname("spoon");
			prod.setPrice(1234.5f);
			prod.setQty(1.0f);

			// save object
			Integer idVal = (Integer) ses.save(prod);
			System.out.println("The Geenrator id Value ::: " + idVal);
			tx.commit();

		} // The SessionFactory , Session objs are Automatically will be closed here.
		catch (HibernateException he) {
			he.printStackTrace();
			if (tx != null && tx.getRollbackOnly() && tx.getStatus() != null) {
				tx.rollback();
				System.out.println("object is saved");
			}
		}
	} // main method

} // class
