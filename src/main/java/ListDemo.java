import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import demo.Dept;
import util.HibernateUtil;

public class ListDemo {
	private static SessionFactory sf = HibernateUtil.getSessionfactory();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		list();
		System.out.println("\n\n\n");
		Dept d = new Dept(); d.setDeptno(50);
		//insert(d);
		list();
		sf.close();
	}

	public static void list() {
		Session session = null;
		try {
			session = sf.openSession();
			Query<Dept> query = session.createQuery("select d from Dept d");
			query.setCacheable(true);
			System.out.println("------------- Listing First Time -------------");
			List<Dept> list = query.list();
			
			for (Dept dept : list) {
				System.out.println(dept);
			}

			Query<Dept> query1 = session.createQuery("select d from Dept d");
			query1.setCacheable(true);
			System.out.println("------------- Listing Second Time -------------");
			List<Dept> list1 = query.list();
			
			for (Dept dept : list1) {
				System.out.println(dept);
			}

		} catch (Exception e) {

			System.out.println("Exception " + e);
		} finally {
			session.close();
		}
	}
	public static void insert(Dept d) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			session.save(d);
			System.out.println("inserted ....");
			tx.commit();
			d.setLoc("Pune");
			System.out.println(d);
		} catch (Exception e) {
			tx.rollback();
			System.out.println("Exception " + e);
		} finally {
			session.close();
		}
	}
	
}
