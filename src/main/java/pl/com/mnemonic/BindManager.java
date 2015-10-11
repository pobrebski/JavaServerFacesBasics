package pl.com.mnemonic;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;




import pl.com.mnemonic.dao.CarbindDao;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Carbind;
import pl.com.mnemonic.entities.Caruser;

@ManagedBean
@ViewScoped
public class BindManager {
	private static final String PAGE_MAIN = "index?faces-redirect=true";
	private static final String PAGE_BIND_EDIT = "bindman?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_STATS = "stats?faces-redirect=true";
	@EJB
	CarbindDao bindao;
	//public List<Carbind> getList(){return bindao.getList();}
	private List<Carbind> list;	
	
	
	public List<Carbind> getList() {
		list = bindao.getgroupList();

		
		return list;
	}
	

	

	public void setList(List<Carbind> list) {
		this.list = list;
	}



	public String newBind(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		//Car car = new Car();
		Carbind bind = new Carbind();
		session.setAttribute("bind", bind);
				return PAGE_BIND_EDIT;
	}
	
	public String deleteBind(Carbind carbind){
		bindao.remove(carbind);
		return PAGE_MAIN;
	}
	
	public String BindStats(Carbind bind){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("bind", bind);
		return PAGE_STATS;
	}

}
