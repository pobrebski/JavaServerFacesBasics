package pl.com.mnemonic;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.dao.CarDao;
import pl.com.mnemonic.dao.CarUserDao;
import pl.com.mnemonic.dao.CarbindDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;


@ManagedBean
@SessionScoped
public class BindEditOLD implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_BIND_LIST = "index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	@EJB
	CarbindDao bindao;
	@EJB
	CarDao cardao;
	@EJB
	CarUserDao userdao;

	    
	private String idcarbind;
	private Car car;
	private Caruser user;
	private boolean archived;
	
	
	public String getIdcarbind() {
		return idcarbind;
	}


	public void setIdcarbind(String idcarbind) {
		this.idcarbind = idcarbind;
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


	public boolean isArchived() {
		return archived;
	}


	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	
	private List<Car> cars;
	private List<Caruser> users;



	public List<Caruser> getUsers() {
		
		return users;
	}


	public void setUsers(List<Caruser> users) {
		this.users = users;
	}


	public List<Car> getCars() {
		
		return cars;
	}


	public void setCars(List<Car> cars) {
		this.cars = cars;
	}


	private Carbind carbind = null;
	private Map<Integer, Boolean> checkedc = new HashMap<Integer, Boolean>();
	
	List<Car>checkedcars;
	
    public Map<Integer, Boolean> getCheckedc() {
		return checkedc;
	}


	public void setCheckedc(Map<Integer, Boolean> checkedc) {
		this.checkedc = checkedc;
	}


	private List<Car> carz;
	
	
	@PostConstruct
	public void postConstruct(){
		
	cars = cardao.getList();
	users = userdao.getFullList();
	checkedcars = new ArrayList<Car>();

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
			carbind = (Carbind) session.getAttribute("bind");
			
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
		
		if (carbind != null) {
			session.removeAttribute("bind");			
		}
		
		if (carbind == null) {
					//HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
					idcarbind = req.getParameter("cc");
					if (idcarbind != null) {

						Integer id=null;
						try {
							id = Integer.parseInt(idcarbind);
						} catch (NumberFormatException e) {
						}
						if (id!=null) {
							carbind = bindao.find(id);
						}
						
					}

				}
				
		
				if (carbind != null && carbind.getIdcarbind()>=0) {
					setCar(carbind.getCar());
					setUser(carbind.getCaruser());
					
					
						
					 		if(car==null & checkedc.size()>0 ){
					 		
					        for (Car carc : cars) {
					        	
					            if (checkedc.get(carc.getIdcar())) {
					                checkedcars.add(carc);
							
					            		}setCar(carc);
					        		}
					        	}
					 		
					 		
					 		if(checkedcars.size()>0){
					 			
					 				setCar(checkedcars.get(0));
					 			}
					 		
					 		
					
					/*
										if(cars!= null){
											car = cars.get(0);
											user = carbind.getCaruser();
										}
										if (users!=null){
											car = carbind.getCar();
											user = users.get(0);
										}*/
															
					}
				
					
	
	}
		
	private boolean validate() {
		
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		
		
		if (user == null) {ctx.addMessage(null, new FacesMessage("user required"));
		}
			
		if(car == null){
				ctx.addMessage(null, new FacesMessage("car required"));
		}
	   
	
		if (ctx.getMessageList().isEmpty()) {
			carbind.setCar(car);
			carbind.setCaruser(user);
			result = true;
		}

		return result;
	}

	
	public String saveData() {
		if (carbind == null) {
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
			if (carbind.getIdcarbind()<=0) {
				
				bindao.create(carbind);
				
			} else {
				// existing record
				bindao.merge(carbind);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("wystąpił błąd podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		//return PAGE_CAR_LIST;
		return PAGE_BIND_LIST;
	}
	
	


}
