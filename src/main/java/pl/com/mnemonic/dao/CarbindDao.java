package pl.com.mnemonic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;

@Stateless
public class CarbindDao {

	
	private final static String UNIT_NAME = "fuelCalc";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager eb;
	CarDao cardao;
	Car car;
	
	public void create(Carbind carbind) {
		eb.persist(carbind);
		// em.flush();
	}

	public Carbind merge(Carbind carbind) {
		return eb.merge(carbind);
	}

	public void remove(Carbind carbind) {
		eb.remove(eb.merge(carbind));
	}

	public Carbind find(Object id) {
		return eb.find(Carbind.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Carbind> getList() {
		List<Carbind> list = null;
		//List<Car> cars = null;
		
		Query query = eb.createQuery("SELECT s FROM Carbind s");
		try {
			list = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/*

		try {
			list = query.getResultList();
			
			Map<Carbind, Car> mapa = new HashMap<Carbind, Car>();
			for(int i = 0; i<list.size(); i++){
				mapa.put((list.get(i)), car);
			}
			
			for(int i = 0; i<list.size();i++){
				list.get(i).setCar(cardao.getList().get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			*/
		return list;
	}
	
	
	
	public List<Carbind> getgroupList() {
		List<Carbind> list = null;
		//List<Car> cars = null;
		
		Query query = eb.createQuery("SELECT s FROM Carbind s group by caruser");
		try {
			list = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} return list;
		}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Carbind> getListCar(Map<Car, Carbind> search) {
		List<Carbind> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Carbind s ";
		String where = "";
		String orderby = "order by s.caruser asc, s.car";
		
	
		
		// search for surname
		Carbind car = (Carbind) search.get("car");
		if (car != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.car like :car ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = eb.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (car != null) {
			query.setParameter("car", car+"%");
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
	public List<Carbind> getListUser(Map<Caruser, Carbind> search) {
		List<Carbind> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Carbind s ";
		String where = "";
		String orderby = "order by s.car asc, s.caruser";
		
	
		
		// search for surname
		Carbind user = (Carbind) search.get("caruser");
		if (user != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.caruser like :user ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = eb.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (user != null) {
			query.setParameter("caruser", user+"%");
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
	
	
	
	
	/*
	public List<Carbind> getListUser(Map<String, Object> searchParams) {
		List<Carbind> list = null;

		// 1. Build query string with parameters
		String select = "select b ";
		String from = "from Carbind b ";
		String where = "";
		String orderby = "order by b.Car asc, b.Caruser";

		// search for surname
	Caruser user = (Caruser) searchParams.get("Caruser");
		if (user != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "b.Caruser like :Caruser ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = eb.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (user != null) {
			query.setParameter("Caruser", user+"%");
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
	
	public List<Carbind> getListCars(Map<String, Object> searchParams) {
		List<Carbind> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from Carbind p ";
		String where = "";
		String orderby = "order by p.idcarbind asc, p.caruser";

		// search for surname
	/*	String carrmake = (String) searchParams.get("carrmake");
		if (carrmake != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.carrmake like :carrmake ";
		}*/
		
		// ... other parameters ... 

		// 2. Create query object
	/*	Query query = eb.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (carrmake != null) {
			query.setParameter("carrmake", carrmake+"%");
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
	
	
	*/
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Carbind> getUserCars(Caruser user) {
		List<Carbind> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Carbind s ";
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
		Query query = eb.createQuery(select + from + where + orderby);

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
	public List<Carbind> getCarUsers(Car car) {
		List<Carbind> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Carbind s ";
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
		Query query = eb.createQuery(select + from + where + orderby);

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
