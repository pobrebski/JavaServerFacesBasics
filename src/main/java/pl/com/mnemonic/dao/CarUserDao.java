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
public class CarUserDao {
	private final static String UNIT_NAME = "fuelCalc";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	CarbindDao bindao;
	Carbind bind;

	public void create(Caruser user) {
		em.persist(user);
	}

	public Caruser merge(Caruser user) {
		return em.merge(user);
	}

	public void remove(Caruser user) {
		//em.remove(user);
		em.remove(em.contains(user) ? user : em.merge(user));
	}

	public Caruser find(int a) {
		return em.find(Caruser.class, a);
	}

	
	
	public List<Caruser> getFullList() {
		List<Caruser> list = null;
		Query qu = em.createQuery("select u from Caruser u");

		try {
			list = qu.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Caruser> getList(Map<String, Object> searchParams){
		List<Caruser> userlist=null;
		String select = "select p ";
		String from = "from Caruser p ";
		String where = "";
		String orderby = "order by p.surname asc, p.idcaruser";

		String surname = (String) searchParams.get("surname");
		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.surname like :surname ";
		}
		
		
		Query query = em.createQuery(select + from + where + orderby);

		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}

		
		try {
			userlist = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userlist;
	}
	
	
	
	
	public List<Caruser> getListUser() {
		List<Caruser> list = null;
		
	
		List<Carbind> binds = null;
		
	//	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
	//			.getExternalContext().getSession(true); //TO BEDZIE KLUCZ DO DZIALAJACEGO PRZEKAZYWANIA LISTY BINDOW?
		//session.setAttribute("car", car);

		Query query = em.createQuery("select p from Caruser p");

		try {
			list = query.getResultList();
			
			//przygotuj parametry dla carbindDao hasmap:
			//1. Prepare search params
			Map<Caruser,Carbind> searchParams = new HashMap<Caruser, Carbind>();
			
			for(int i=0; i<list.size(); i++){
				searchParams.put((list.get(i)), bind);
								
				//bind = (Carbind) bindao.getListCar(searchParams);
				
			}
			
			for(int i=0; i<list.size(); i++){
			
			list.get(i).setCarbinds(bindao.getListUser(searchParams)); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		return list;
	}
	
	
	

}
