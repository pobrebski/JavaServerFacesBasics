package pl.com.mnemonic.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the refueling database table.
 * 
 */
@Entity
@NamedQuery(name="Refueling.findAll", query="SELECT u FROM Refueling u")
public class Refueling implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrefueling;

	@Temporal(TemporalType.DATE)
	private Date date;

	private float kilometers;

	private float litre;

	//bi-directional many-to-one association to Car
	@ManyToOne
	private Car car;

	//bi-directional many-to-one association to Caruser
	@ManyToOne
	private Caruser caruser;

	public Refueling() {
	}

	public int getIdrefueling() {
		return this.idrefueling;
	}

	public void setIdrefueling(int idrefueling) {
		this.idrefueling = idrefueling;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getKilometers() {
		return this.kilometers;
	}

	public void setKilometers(float kilometers) {
		this.kilometers = kilometers;
	}

	public float getLitre() {
		return this.litre;
	}

	public void setLitre(float litre) {
		this.litre = litre;
	}

	public Car getCar() {
		return this.car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Caruser getCaruser() {
		return this.caruser;
	}

	public void setCaruser(Caruser caruser) {
		this.caruser = caruser;
	}

}