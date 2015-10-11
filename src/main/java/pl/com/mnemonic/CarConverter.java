package pl.com.mnemonic;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import pl.com.mnemonic.dao.CarDao;
import pl.com.mnemonic.entities.Car;



@ManagedBean
@RequestScoped
public class CarConverter  implements Converter{
	
	

	    @EJB
	    private CarDao cardao;

	    @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
	        if (submittedValue == null || submittedValue.isEmpty()) {
	            return null;
	        }

	        try {
	            return cardao.find(Integer.valueOf(submittedValue));
	        } catch (NumberFormatException e) {
	            throw new ConverterException(new FacesMessage(String.format("%s is not a valid User ID", submittedValue)), e);
	        }
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
	        if (modelValue == null) {
	            return "";
	        }

	        if (modelValue instanceof Car) {
	            return String.valueOf(((Car) modelValue).getIdcar());
	        } else {
	            throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", modelValue)));
	        }
	    }

	}

