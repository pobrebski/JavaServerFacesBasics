package pl.com.mnemonic;


import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import pl.com.mnemonic.dao.CarUserDao;
import pl.com.mnemonic.dao.CarbindDao;
import pl.com.mnemonic.dao.RefuelingDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;
import pl.com.mnemonic.entities.Refueling;


@ManagedBean
@ViewScoped
public class BindEdit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_BIND_LIST = "index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	@EJB
	CarbindDao bindao;
	@EJB
	CarDao cardao;
	@EJB
	CarUserDao userdao;
	@EJB
	RefuelingDao refdao;

	    
	private String idcarbind;
	private Car car;
	private Caruser user;
	private boolean archived;
	
	private List<Car> cars;
	private List<Caruser> users;
	


	private int idcar;
	
	private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
	
	

	private List<Car> compared;
	private List<Caruser> comparedu;

	private List<Carbind> usercars;
	
	private List<Refueling> carrefs;
	private List<Refueling> userrefs;
	
private float averageuser;
private float averagecar;
	
	public float getAveragecar() {
		
		float kil = 0f;
		float lit = 0f;
		float k;
		float l;
	
			
			for(Refueling r:carrefs){
				k =  r.getKilometers();
				l = r.getLitre();
				kil = kil+k;
				lit = lit+l;
				}
			
			averagecar = lit/kil*100;
	return averagecar;
}

public void setAveragecar(float averagecar) {
	this.averagecar = averagecar;
}

	public float getAverageuser() {
		float kil = 0f;
		float lit = 0f;
		float k;
		float l;
	
			
			for(Refueling r:userrefs){
				k =  r.getKilometers();
				l = r.getLitre();
				kil = kil+k;
				lit = lit+l;
				}
			
			averageuser = lit/kil*100;
			
		
		

		return averageuser;
	}
	
	public void setAverageuser(float averageuser) {
		this.averageuser = averageuser;
	}
	
	
	public List<Refueling> getCarrefs() {
		carrefs = refdao.getCarRefs(car);
		return carrefs;
	}


	public void setCarrefs(List<Refueling> carrefs) {
		this.carrefs = carrefs;
	}


	public List<Refueling> getUserrefs() {
		userrefs = refdao.getUserRefs(user);
		return userrefs;
	}


	public void setUserrefs(List<Refueling> userrefs) {
		this.userrefs = userrefs;
	}

	
	public List<Carbind> getUsercars() {
		usercars = bindao.getUserCars(user);
		return usercars;
	}


	public void setUsercars(List<Carbind> usercars) {
		this.usercars = usercars;
	}


	public List<Caruser> getComparedu() {
		return comparedu;
	}


	public void setComparedu(List<Caruser> comparedu) {
		this.comparedu = comparedu;
	}


	public List<Caruser> getUsers() {
		users = userdao.getFullList();
		return users;
	}


	public void setUsers(List<Caruser> users) {
		this.users = users;
	}


	public Map<Integer, Boolean> getChecked() {
		return checked;
	}


	public void setChecked(Map<Integer, Boolean> checked) {
		this.checked = checked;
	}


	public List<Car> getCompared() {
		return compared;
	}


	public void setCompared(List<Car> compared) {
		this.compared = compared;
	}


	public int getIdcar() {
		return idcar;
	}


	public void setIdcar(int idcar) {
		this.idcar = idcar;
	}


	public List<Car> getCars() {
		cars = cardao.getList();
		return cars;
	}


	public void setCars(List<Car> cars) {
		this.cars = cars;
	}


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

	private Carbind carbind;
	

	@PostConstruct
	public void postConstruct(){
		
		getCars();
		getUsers();
		
		compared = new ArrayList<Car>();
		comparedu = new ArrayList<Caruser>();
		
	
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
					getUserrefs();
					getCarrefs();
					
					}
			
					
			
					 for (Car cart: cars) 
					    {
					        // If there is a checkbox mapping for the current product then...
					        if(checked.get(cart.getIdcar()) != null)
					        {
					           // If checkbox is ticked then...
					        if (checked.get(cart.getIdcar())) 
					            {
					                // Add product to list of products to be compared.
					                compared.add(cart);
					                setCar(cart);
					            } 
					        }if(compared!=null && compared.size()>0){setCar(compared.get(0));}
					    }
							
							
	}
		
	
	private boolean validate() {
		
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		
		
		if (user == null) {
			if(comparedu!=null && comparedu.size()>0){setUser(comparedu.get(0));}
			else {ctx.addMessage(null, new FacesMessage("user required"));}
		}
			
		if(car == null){
			if(compared!=null && compared.size()>0){setCar(compared.get(0));}
			else{	ctx.addMessage(null, new FacesMessage("car required"));}
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
	
	
	public String Back(){
		
		return PAGE_BIND_LIST;
	}
	
	


}
