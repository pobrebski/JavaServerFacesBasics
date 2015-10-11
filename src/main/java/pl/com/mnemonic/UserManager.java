package pl.com.mnemonic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.dao.CarUserDao;
import pl.com.mnemonic.dao.CarbindDao;
import pl.com.mnemonic.dao.RefuelingDao;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;
import pl.com.mnemonic.entities.Refueling;

@ManagedBean
@ViewScoped
public class UserManager implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_USER_EDIT = "userman?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String surname;
	

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	private List<Carbind> usbinds;
	private List<Refueling> refs;
	
	@EJB	
	CarUserDao userdao;
	@EJB
	CarbindDao bindao;
	@EJB
	RefuelingDao refdao;
	
	public List<Caruser> getFullList(){return userdao.getFullList();}
	
	public List<Caruser> getList(String sur){
		List<Caruser> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (sur != null && sur.length() > 0){
			searchParams.put("surname", sur);
		}
		
		list = userdao.getList(searchParams);
		
		return list;
	}
	
	public String newUser(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Caruser user = new Caruser();
		session.setAttribute("user", user);
		return PAGE_USER_EDIT;
	}
	
	public String editUser(Caruser user){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("user", user);
		return PAGE_USER_EDIT;
	}
	
	public String deleteUser(Caruser user){
		usbinds = bindao.getUserCars(user);
		if(usbinds!=null){
		for(Carbind bind : usbinds){
			bindao.remove(bind);
		}
		refs = refdao.getUserRefs(user);
		for(Refueling re:refs){
			refdao.remove(re);
		}}
		userdao.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}


}
