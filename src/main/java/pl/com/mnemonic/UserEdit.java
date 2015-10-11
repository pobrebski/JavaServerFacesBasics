package pl.com.mnemonic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.dao.CarUserDao;
import pl.com.mnemonic.dao.RefuelingDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;
import pl.com.mnemonic.entities.Refueling;



@ManagedBean
@ViewScoped
public class UserEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CAR_LIST2 = "carList.jsf?faces-redirect=true";
	private static final String PAGE_USER_EDIT = "userman?faces-redirect=true";
	private static final String PAGE_BIND = "bind_user?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	@EJB
	CarUserDao userdao;
	@EJB
	RefuelingDao refdao;
	
	//RefuelingEdit manager;
	
	private String idcaruser;
	private String name;
	private byte permanent;
	private String surname;
	private byte temporary;
	private List<Carbind> carbinds;
	private List<Refueling> refuelings;
	
	
	private float average;
	
	public float getAverage() {
		float kil = 0f;
		float lit = 0f;
		float k;
		float l;
		if(refuelings!=null){
			
			for(Refueling r:refuelings){
				k =  r.getKilometers();
				l = r.getLitre();
				kil = kil+k;
				lit = lit+l;
				}
			
			average = lit/kil*100;
			
		} else {average= 0f;}
		

		return average;
	}
	
	public void setAverage(float average) {
		this.average = average;
	}
	
	
	

	public List<Refueling> getRefuelings() {
		refuelings = refdao.getUserRefs(user) ;

		return refuelings;
	}

	public void setRefuelings(List<Refueling> refuelings) {
		this.refuelings = refuelings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getPermanent() {
		return permanent;
	}

	public void setPermanent(byte permanent) {
		this.permanent = permanent;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public byte getTemporary() {
		return temporary;
	}

	public void setTemporary(byte temporary) {
		this.temporary = temporary;
	}
	
	
	public List<Carbind> getCarbinds() {
		return carbinds;
	}

	public void setCarbinds(List<Carbind> carbinds) {
		this.carbinds = carbinds;
	}

	
	private Caruser user = null;
	
	@PostConstruct
	public void postConstruct(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	
		user =(Caruser)  session.getAttribute("user");
		if (user != null) {
			session.removeAttribute("user");
		}
		
		// B. if object not loaded try to get Person by id passed as GET				
			if (user == null) {
					HttpServletRequest req = (HttpServletRequest) FacesContext
							.getCurrentInstance().getExternalContext().getRequest();
					idcaruser = req.getParameter("t");
					//idcaruser = Integer.parseInt(req.getParameter("p"));
					
					if (idcaruser!=null) {
						// parse id
						Integer id = null;
						
						try {
							id = Integer.parseInt(idcaruser);
						} catch (NumberFormatException e) {
						}
						if (id>=0) {
							// if parsing successful
							user = userdao.find(id);
						}
					}
				}
				
				if (user != null && user.getIdcaruser()>=0) {
					//setIdcaruser(user.getIdcaruser());
					setName(user.getName());
					setSurname(user.getSurname());
					setPermanent(user.getPermanent());
					setTemporary(user.getTemporary());
					setRefuelings(user.getRefuelings());
					setCarbinds(user.getCarbinds());
					//setCarbinds((List<Carbind>) user.getCarbinds());
					//setRefs(user.getRefuelings());
					//E e = list.get(list.size() - 1);
					//bind = carbinds.get(carbinds.size()-1) ;
					//setidcarbind(user.getCarbinds());
					//setCarregistrationyear(car.getCarregistrationyear());
					//String dateString = ((Integer) car.getCarregistrationyear()).toString();
					// appropriately convert date to string
					/*String dateString = new SimpleDateFormat("dd-MM-yyyy")
							.format(car.getCarregistrationyear());
					//setCarregistrationyear(dateString);*/
					
					
				}		
				
	}
	
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (name == null || name.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("imie wymagane"));
		}
		if (surname == null || surname.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("nazwisko wymagane"));
		}

		/*int date=-1;
		try {
			date = carregistrationyear;
		} catch (Exception e) {
			ctx.addMessage(null, new FacesMessage(
					"niepoprawny format datnych"));
		}*/

		// if no errors
		if (ctx.getMessageList().isEmpty()) {
			permanent = 1;
			temporary = 0;
			//carbinds = new List<Carbind>;
			user.setName(name.trim());
			user.setSurname(surname.trim());
			user.setPermanent(permanent);
			user.setTemporary(temporary);
			user.setCarbinds(carbinds);
			user.setRefuelings(refuelings);
			
			
			result = true;
		}
		
		return result;
	}

	
	public String saveData() {
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("bladd nulla w userze nie przekazany"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (user.getIdcaruser()<0) {
				userdao.create(user);
				
				
			} else {
				userdao.merge(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("wystąpił błąd podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_USER_EDIT;
	}
	
	
	
	public String saveBindData() {

		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("brak uzytkownika do edycji - blad systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (user.getIdcaruser()<0) {
			
				userdao.create(user);
				
			} else {
				userdao.merge(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("wystąpił błąd podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		if (surname != null && surname.length() > 0){
			searchParams.put("surname", surname);
		}
		
		
		List<Caruser> surnames = userdao.getList(searchParams);
		user = surnames.get(0);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Carbind bind = new Carbind();
		bind.setCaruser(user);
		session.setAttribute("bind", bind);
		
		return PAGE_BIND;
	}
	
	

}
