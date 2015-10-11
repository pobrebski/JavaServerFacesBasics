package pl.com.mnemonic;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.dao.CarDao;
import pl.com.mnemonic.dao.CarUserDao;
import pl.com.mnemonic.dao.CarbindDao;
import pl.com.mnemonic.dao.RefuelingDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;
import pl.com.mnemonic.entities.Refueling;


@ManagedBean
@ViewScoped
public class RefuelingEdit implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String PAGE_BIND_LIST = "index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	@EJB
	RefuelingDao refdao;
	@EJB
	CarDao cardao;
	@EJB
	CarUserDao userdao;
	

	private Refueling ref;
	private String idrefueling;
	private float litre;
	private float kilometers;
	//private Date dateref;
	private Car car;
	private Caruser user;
	private List<Refueling> cars;
	private List<Refueling> users;
	
	
	public List<Refueling> getCars() {
		cars = refdao.getCarRefs(car);
		return cars;
	}


	public void setCars(List<Refueling> cars) {
		this.cars = cars;
	}


	public List<Refueling> getUsers() {
		users = refdao.getUserRefs(user);
		return users;
	}


	public void setUsers(List<Refueling> users) {
		this.users = users;
	}


	public float getLitre() {
		return litre;
	}


	public void setLitre(float litre) {
		this.litre = litre;
	}


	public float getKilometers() {
		return kilometers;
	}


	public void setKilometers(float kilometers) {
		this.kilometers = kilometers;
	}



	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public Caruser getUser() {
		return user;
	}


	public void setUser(Caruser user) {
		this.user = user;
	}


	@PostConstruct
	public void postConstruct(){
		
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
			ref = (Refueling) session.getAttribute("ref");
			
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
		
		if (ref != null) {
			session.removeAttribute("ref");			
		}
		
		if (ref == null) {
					//HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
					idrefueling = req.getParameter("rf");
					if (ref != null) {

						Integer id=null;
						try {
							id = Integer.parseInt(idrefueling);
						} catch (NumberFormatException e) {
						}
						if (id!=null) {
							ref = refdao.find(id);
						}
						
					}

				}
				
		
				if (ref != null && ref.getIdrefueling()>=0) {
					setCar(ref.getCar());
					setUser(ref.getCaruser());
					setLitre(ref.getLitre());
					setKilometers(ref.getKilometers());
					//setDateref(ref.getDate());
				
					
					}
						
							
	}
		
	
	private boolean validate() {
		
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		
		
		if (user == null) {
			ctx.addMessage(null, new FacesMessage("user required"));
		}
			
		if(car == null){ctx.addMessage(null, new FacesMessage("car required"));}
		
		if (litre <=0) {
			ctx.addMessage(null, new FacesMessage("litry"));
		}
		
		if (kilometers <=0) {
			ctx.addMessage(null, new FacesMessage("kilometry to gdzie?"));
		}
	
		if (ctx.getMessageList().isEmpty()) {
			ref.setCar(car);
			ref.setCaruser(user);
			ref.setKilometers(kilometers);
			ref.setLitre(litre);
			result = true;
		}

		return result;
	}

	
	public String saveData() {
		if (ref == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("bledne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("rak walidacji"));
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (ref.getIdrefueling()<=0) {
				
				refdao.create(ref);
				
			} else {
				// existing record
				refdao.merge(ref);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("wystąpił błąd podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_BIND_LIST;
	}
	
	
	public String Back(){
		
		return PAGE_BIND_LIST;
	}
	
	}

	
