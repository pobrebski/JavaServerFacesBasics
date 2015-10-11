package pl.com.mnemonic.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the car database table.
 * 
 */
@Entity
@NamedQuery(name="Car.findAll", query="SELECT c FROM Car c")
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcar;

	private String carname;

	private int carregistrationyear;

	private String carrmake;

	//bi-directional many-to-one association to Carbind
	@OneToMany(mappedBy="car")
	private List<Carbind> carbinds;

	//bi-directional many-to-one association to Refueling
	@OneToMany(mappedBy="car")
	private List<Refueling> refuelings;

	public Car() {
	}

	public int getIdcar() {
		return this.idcar;
	}
	
	public void setIdcar(int idcar) {
		this.idcar = idcar;
	}

	public String getCarname() {
		return this.carname;
	}

	public void setCarname(String carname) {
		this.carname = carname;
	}

	public String getCarregistrationyear() {
		return this.carregistrationyear+"";
	}

	public void setCarregistrationyear(String carregistrationyear) {
		int idcarset = Integer.parseInt(carregistrationyear);
		this.carregistrationyear = idcarset;
	}

	public String getCarrmake() {
		return this.carrmake;
	}

	public void setCarrmake(String carrmake) {
		this.carrmake = carrmake;
	}

	public List<Carbind> getCarbinds() {
		
		return this.carbinds;
	}

	public void setCarbinds(List<Carbind> carbinds) {
		this.carbinds = carbinds;
	}

	public Carbind addCarbind(Carbind carbind) {
		getCarbinds().add(carbind);
		carbind.setCar(this);

		return carbind;
	}

	public Carbind removeCarbind(Carbind carbind) {
		getCarbinds().remove(carbind);
		carbind.setCar(null);

		return carbind;
	}

	public List<Refueling> getRefuelings() {
		return this.refuelings;
	}

	public void setRefuelings(List<Refueling> refuelings) {
		this.refuelings = refuelings;
	}

	public Refueling addRefueling(Refueling refueling) {
		getRefuelings().add(refueling);
		refueling.setCar(this);

		return refueling;
	}

	public Refueling removeRefueling(Refueling refueling) {
		getRefuelings().remove(refueling);
		refueling.setCar(null);

		return refueling;
	}

}