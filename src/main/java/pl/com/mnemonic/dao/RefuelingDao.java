package pl.com.mnemonic.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;
import pl.com.mnemonic.entities.Refueling;

@Stateless
public class RefuelingDao {
	
	private final static String UNIT_NAME = "fuelCalc";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Refueling ref) {
		em.persist(ref);
	}

	public Refueling merge(Refueling ref) {
		return em.merge(ref);
	}

	public void remove(Refueling ref) {
		em.remove(em.merge((ref)));
	}

	public Refueling find(Object a) {
		return em.find(Refueling.class, a);
	}

	public List<Refueling> getFullList() {
		List<Refueling> list = null;
		Query qu = em.createQuery("select r from Refueling r");

		try {
			list = qu.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/*public List<Refueling> getList(Map<String, Object> searchParams){
		List<Refueling> reflist=null;
		String select = "select r ";
		String from = "from Refueling r ";
		String where = "";
		String orderby = "order by r.date asc, r.litre desc";

		// search for surname
		String surname = (String) searchParams.get("date");
		if (date != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.surname like :surname ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			userlist = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userlist;
	}

	*/
	
	
	@SuppressWarnings("unchecked")
	public List<Refueling> getUserRefs(Caruser user) {
		List<Refueling> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Refueling s ";
		String where = "";
		String orderby = "order by s.car asc, s.caruser";
		
	
		
		// search for surname
		
		if (user !=null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.caruser like :user ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (user!=null) {
			query.setParameter("user", user);
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Refueling> getCarRefs(Car car) {
		List<Refueling> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Refueling s ";
		String where = "";
		String orderby = "order by s.car asc, s.caruser";
		
	
		
		// search for surname
		
		if (car !=null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.car like :car ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (car!=null) {
			query.setParameter("car", car);
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


}
