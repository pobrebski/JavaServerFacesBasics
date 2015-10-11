package pl.com.mnemonic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;



@Stateless
public class CarDao {
	
	private final static String UNIT_NAME = "fuelCalc";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	CarbindDao bindao;
	Carbind bind;
	
	public void create(Car car) {
		em.persist(car);
		// em.flush();
	}

	public Car merge(Car car) {
		return em.merge(car);
	}

	public void remove(Car car) {
		em.remove(em.merge(car));
	}

	public Car find(int a) {
		
		return em.find(Car.class, a);
	}
	
	public Car wez(int a){
		Car car = null;
		Query query = em.createQuery("select p from Car p where idcar="+a+";");
		try{
			car = (Car) query.getSingleResult();
		}catch(Exception e){}
		return car;
	}
	
	
	public List<Car> getList() {
		List<Car> list = null;
		Query query = em.createQuery("select p from Car p");

		try {
			list = query.getResultList();}
		catch (Exception e) {
		e.printStackTrace();
			}
		
		return list;
		}
	
	
	
	
	
	public List<Car> getFullList() {
		List<Car> list = null;
		
	
		List<Carbind> binds = null;
		
	//	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
	//			.getExternalContext().getSession(true); //TO BEDZIE KLUCZ DO DZIALAJACEGO PRZEKAZYWANIA LISTY BINDOW?
		//session.setAttribute("car", car);

		Query query = em.createQuery("select p from Car p");

		try {
			list = query.getResultList();
			
			//przygotuj parametry dla carbindDao hasmap:
			//1. Prepare search params
			Map<Car,Carbind> searchParams = new HashMap<Car, Carbind>();
			
			for(int i=0; i<list.size(); i++){
				searchParams.put((list.get(i)), bind);
								
				//bind = (Carbind) bindao.getListCar(searchParams);
				
			}
			
			for(int i=0; i<list.size(); i++){
			
			list.get(i).setCarbinds(bindao.getListCar(searchParams)); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		return list;
	}
	
	

	
	public List<Car> getList(Map<String, Object> searchParams) {
		List<Car> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from Car p ";
		String where = "";
		String orderby = "order by p.carrmake asc, p.carname";

		// search for surname
		String carrmake = (String) searchParams.get("carrmake");
		if (carrmake != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.carrmake like :carrmake ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

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
	
}


/*
package com.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Person;
*/
//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.






