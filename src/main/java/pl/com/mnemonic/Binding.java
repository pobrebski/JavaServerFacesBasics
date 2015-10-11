package pl.com.mnemonic;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import pl.com.mnemonic.dao.CarDao;
import pl.com.mnemonic.dao.CarUserDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;




@ManagedBean(name = "binding")
public class Binding {
	private static final String PAGE_USER_EDIT = "userEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	private CarUserDao userdao;
	private CarDao cardao;
	
	private String idcaruser;

	private String name;

	private byte permanent;

	private String surname;
	
	private String idcar;

	private String carname;

	private int carregistrationyear;

	private String carrmake;
	
	private int bindcar;
	private int binduser;

	private boolean carchecked;
	private boolean userchecked;
	
	
	
	
	
	
	
	public boolean isCarchecked() {
		return carchecked;
	}


	public void setCarchecked(boolean carchecked) {
		this.carchecked = carchecked;
	}


	public boolean isUserchecked() {
		return userchecked;
	}


	public void setUserchecked(boolean userchecked) {
		this.userchecked = userchecked;
	}


	public int getBindcar() {
		return bindcar;
	}


	public void setBindcar(int bindcar) {
		this.bindcar = bindcar;
	}


	public int getBinduser() {
		return binduser;
	}


	public void setBinduser(int binduser) {
		this.binduser = binduser;
	}


	public String getIdcar() {
		return idcar;
	}


	public void setIdcar(String idcar) {
		this.idcar = idcar;
	}


	public String getCarname() {
		return carname;
	}


	public void setCarname(String carname) {
		this.carname = carname;
	}


	public int getCarregistrationyear() {
		return carregistrationyear;
	}


	public void setCarregistrationyear(int carregistrationyear) {
		this.carregistrationyear = carregistrationyear;
	}


	public String getCarrmake() {
		return carrmake;
	}


	public void setCarrmake(String carrmake) {
		this.carrmake = carrmake;
	}


	public CarUserDao getUserdao() {
		return userdao;
	}


	public void setUserdao(CarUserDao userdao) {
		this.userdao = userdao;
	}


	public String getIdcaruser() {
		return idcaruser;
	}


	public void setIdcaruser(String idcaruser) {
		this.idcaruser = idcaruser;
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

	Caruser caruser = null;
	Car car = null;
	Carbind bind = null;
	
	
	public Caruser getCaruser() {
		return caruser;
	}


	public void setCaruser(Caruser caruser) {
		this.caruser = caruser;
	}


	@PostConstruct
	public void postConstruct() {
		
		
	HttpServletRequest req = (HttpServletRequest) FacesContext
			.getCurrentInstance().getExternalContext().getRequest();
	

	
	idcaruser = req.getParameter("p");
	idcar = req.getParameter("r");
	

	
	if (idcaruser != null && idcar !=null ) {
		
	//else if(idcar!=null){idcaruser = req.getParameter("p");}
	
		
		Integer id = null;
		Integer idc = null;
		try {
			id = Integer.parseInt(idcaruser);
			idc = Integer.parseInt(idcar);
		} catch (NumberFormatException e) {
		}
		if (id != null && idc != null) {
			
			caruser = userdao.find(id);
			car = cardao.find(idc);
			bind.setCar(car);
			bind.setCaruser(caruser);
			bindcar = id;
			binduser = idc;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ok"));
			
		}
		
	}
	
	}
	
	
	
	
	

	public String editCaruser(Caruser caruser){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("caruser", caruser);
		return PAGE_USER_EDIT;
	}
	
	
	public String deleteCaruser(Caruser caruser){
		userdao.remove(caruser);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	
}
