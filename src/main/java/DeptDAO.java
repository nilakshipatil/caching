import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import demo.Dept;
import util.HibernateUtil;

public class DeptDAO {
	private static SessionFactory sf = HibernateUtil.getSessionfactory();

	public void list() {
		Session session = null;
		try {
			session = sf.openSession();
			// First Level Caching - Simple Demo
			 Query<Dept> query = session.createQuery("select d from Dept d where d.deptno < 30");
				List<Dept> list = query.list();
				for (Dept dept : list) {
					System.out.println(dept);
				}
				
				
			Dept d = session.get(Dept.class, 30);
			System.out.println(" after get " + d);
			Dept d1 = session.get(Dept.class, 20);
			System.out.println(" after get " + d1);
			
		   
		} catch (Exception e) {

			System.out.println("Exception " + e);
		} finally {
			session.close();
		}
	}

	
	public void insert(Dept d) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			// d - transient
			System.out.println(session.save(d));
			// d - persistent/attached
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

	public static void main(String[] args) {
		DeptDAO dao = new DeptDAO();
		/*for (int i = 10; i < 50; i += 10) {
			Dept d = new Dept();
			d.setDeptno(i);
			d.setDname("HR" + i);
			if ((i % 20) == 0)
				d.setLoc("Hyd");
			else
				d.setLoc("Blr");

			dao.insert(d);
		}*/
	
		dao.list();
		sf.close();
	}
}
