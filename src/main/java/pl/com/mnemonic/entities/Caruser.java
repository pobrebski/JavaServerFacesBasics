package pl.com.mnemonic.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;
import java.util.Set;


/**
 * The persistent class for the caruser database table.
 * 
 */
@Entity
@NamedQuery(name="Caruser.findAll", query="SELECT u FROM Caruser u")
public class Caruser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcaruser;

	private String name;

	private byte permanent;

	private String surname;

	private byte temporary;

	//bi-directional many-to-one association to Carbind
	@OneToMany(mappedBy="caruser")
	private List<Carbind> carbinds;

	//bi-directional many-to-one association to Refueling
	@OneToMany(mappedBy="caruser")
	private List<Refueling> refuelings;

	public Caruser() {
	}

	public int getIdcaruser() {
		return this.idcaruser;
	}

	public void setIdcaruser(int idcaruser) {
		this.idcaruser = idcaruser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getPermanent() {
		return this.permanent;
	}

	public void setPermanent(byte permanent) {
		this.permanent = permanent;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public byte getTemporary() {
		return this.temporary;
	}

	public void setTemporary(byte temporary) {
		this.temporary = temporary;
	}

	public List<Carbind> getCarbinds() {
		return this.carbinds;
	}

	public void setCarbinds(List<Carbind> carbinds) {
		
		this.carbinds = carbinds;
	}

	public Carbind addCarbind(Carbind carbind) {
		getCarbinds().add(carbind);
		carbind.setCaruser(this);

		return carbind;
	}

	public Carbind removeCarbind(Carbind carbind) {
		getCarbinds().remove(carbind);
		carbind.setCaruser(null);

		return carbind;
	}

	@SuppressWarnings("unchecked")
	public List<Refueling> getRefuelings() {
		return this.refuelings;
	}

	public void setRefuelings(List<Refueling> refuelings) {
		this.refuelings = refuelings;
	}

	public Refueling addRefueling(Refueling refueling) {
		getRefuelings().add(refueling);
		refueling.setCaruser(this);

		return refueling;
	}

	public Refueling removeRefueling(Refueling refueling) {
		getRefuelings().remove(refueling);
		refueling.setCaruser(null);

		return refueling;
	}

}