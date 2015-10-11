package pl.com.mnemonic;

import java.io.Serializable;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.dao.CarDao;
import pl.com.mnemonic.dao.RefuelingDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Refueling;



@ManagedBean
@ViewScoped
public class carEdit implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CAR_LIST = "carman?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_BIND = "bind_car?faces-redirect=true";
	
	@EJB
	CarDao cardao;
	@EJB
	RefuelingDao refdao;
	
	
	private String idCar;
	private String carname;
	private String carrmake;
	private int carregistrationyear;
	private List<Carbind> carbinds;
	private List<Refueling> refs;
	
	
	private float average;
	
	public float getAverage() {
		if(refs!=null){
			float kil = 0f;
			float lit = 0f;
			for(Refueling r:refs){
				kil += r.getKilometers();
				lit += r.getLitre();
				}
			average = lit/kil*100;
			
		} else {average= 0f;}
		
		return average;
	}
	public void setAverage(float average) {
		this.average = average;
	}
	public List<Refueling> getRefs() {
		refs = refdao.getCarRefs(car);
		return refs;
	}
	public void setRefs(List<Refueling> refs) {
		this.refs = refs;
	}
	public String getCarname() {
		return carname;
	}
	public void setCarname(String carname) {
		this.carname = carname;
	}
	public String getCarrmake() {
		return carrmake;
	}
	public void setCarrmake(String carrmake) {
		this.carrmake = carrmake;
	}
	public String getCarregistrationyear() {
		return carregistrationyear+"";
	}
	public void setCarregistrationyear(String carregistrationyear) {
		int idcarset = Integer.parseInt(carregistrationyear);
		this.carregistrationyear = idcarset;
	}
	
	
	public List<Carbind> getCarbinds() {
		return carbinds;
	}
	public void setCarbinds(List<Carbind> carbinds) {
		this.carbinds = carbinds;
	}

	//private Caruser user = null;
	private Car car = null;
	//Carbind carbind = null;
	
	
	@PostConstruct
	public void postConstruct(){
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		car = (Car) session.getAttribute("car");
		//carbind = (Carbind) session.getAttribute("carbind");
		//user = carbind.getCaruser();
		/*
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		carbind = (Carbind) sessionMap.get("carbind");
		car = (Car) session.getAttribute("car");
		user = carbind.getCaruser();
		*/
		if (car != null) {
			session.removeAttribute("car");
		}
		
		// B. if object not loaded try to get Person by id passed as GET
				// parameter
				if (car == null) {
					HttpServletRequest req = (HttpServletRequest) FacesContext
							.getCurrentInstance().getExternalContext().getRequest();
					idCar = req.getParameter("p");
					if (idCar != null) {
						// parse id
						//Integer id = null;
						Integer id=null;
						try {
							id = Integer.parseInt(idCar);
						} catch (NumberFormatException e) {
						}
						if (id!=null) {
							// if parsing successful
							car = cardao.find(id);
						}
					}
				}
				
				if (car != null && car.getIdcar()>=0) {
					setCarname(car.getCarname());
					setCarrmake(car.getCarrmake());
					setCarregistrationyear(car.getCarregistrationyear());
					setCarbinds(car.getCarbinds());
					setRefs(car.getRefuelings());
					//getAverage();
					//String dateString = ((Integer) car.getCarregistrationyear()).toString();
					// appropriately convert date to string
					/*String dateString = new SimpleDateFormat("dd-MM-yyyy")
							.format(car.getCarregistrationyear());
					setCarregistrationyear(dateString);*/
					
					
				}
		
		
	}
	
	
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (carname == null || carname.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("nazwa wymagana"));
		}
		if (carrmake == null || carrmake.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("marka wymagana"));
		}

		int date=-1;
		try {
			date = Integer.valueOf(carregistrationyear);
		} catch (Exception e) {
			ctx.addMessage(null, new FacesMessage(
					"niepoprawny format datnych"));
		}

		// if no errors
		if (ctx.getMessageList().isEmpty()) {
			car.setCarname(carname.trim());
			car.setCarrmake(carrmake.trim());
			car.setCarregistrationyear(date+"");
			car.setCarbinds(carbinds);
			car.setRefuelings(refs);
			result = true;
		}

		return result;
	}

	
	public String saveData() {

		// no Person object passed
		if (car == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Błędne użycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (car.getIdcar()<0) {
				// new record
				//bind = new Carbind();
				//bind.setCar(car);
				cardao.create(car);
				
			} else {
				// existing record
				cardao.merge(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("wystąpił błąd podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		
		return PAGE_CAR_LIST;
		//return PAGE_STAY_AT_THE_SAME;
	}
	
	
	
	
	public String saveBindData() {

		// no Person object passed
		if (car == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Błędne użycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (car.getIdcar()<0) {
				
				// new record
				//bind = new Carbind();
				//bind.setCar(car);
				cardao.create(car);
				
			} else {
				// existing record
				cardao.merge(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("wystąpił błąd podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		if (carrmake != null && carrmake.length() > 0){
			searchParams.put("carrmake", carrmake);
		}
		
		
		List<Car> carzzzz = cardao.getList(searchParams);
		car = carzzzz.get(0);
		
		//carbind.setCaruser(user);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Carbind bind = new Carbind();
		bind.setCar(car);
		session.setAttribute("bind", bind);
		
		return PAGE_BIND;
	}
	
	

}
