package pl.com.mnemonic;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.dao.CarDao;
import pl.com.mnemonic.dao.CarbindDao;
import pl.com.mnemonic.dao.RefuelingDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;
import pl.com.mnemonic.entities.Refueling;


@ManagedBean
@ViewScoped
public class CarManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_CAR_EDIT = "carman?faces-redirect=true";
	private static final String PAGE_BIND = "carman?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_INDEX = "index?faces-redirect=true";
	private static final String PAGE_REF_EDIT = "refman?faces-redirect=true";

	@EJB
	CarDao cardao;
	@EJB
	CarbindDao bindao;
	@EJB
	RefuelingDao refdao;
	private List<Carbind> usbinds;
	private List<Refueling> refs;
	
	
public List<Car> getList(){
		
		return cardao.getList();	
	}
	

	
		
	/*
	public List<Car> getList(){
		List<Car> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (carrmake != null && carrmake.length() > 0){
			searchParams.put("carrmake", carrmake);
		}
		
		//2. Get list
		list = cardao.getList(searchParams);
		
		return list;
	}
*/
	
	
	
	public String newCar(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Car car = new Car();
		session.setAttribute("car", car);
		return PAGE_CAR_EDIT;
	}
	
	
	public String addCarBind(Caruser user){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		session.setAttribute("user", user);
		return PAGE_BIND;
	}
	
	
	public String editCar(Car car){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("car", car);
		return PAGE_CAR_EDIT;
	}
	
	public String deleteCar(Car car){
	
		usbinds = bindao.getCarUsers(car);
		if(usbinds!=null){
		for(Carbind bind : usbinds){
			bindao.remove(bind);
		}
		refs = refdao.getCarRefs(car);
		for(Refueling re:refs){
			refdao.remove(re);
		}}
		cardao.remove(car);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String newRefueling(Carbind bind){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Car car = bind.getCar();
		Caruser user = bind.getCaruser();
		Refueling ref = new Refueling();
		ref.setCar(car);
		ref.setCaruser(user);
		session.setAttribute("ref", ref);
				return PAGE_REF_EDIT;
	}
	
	

}
