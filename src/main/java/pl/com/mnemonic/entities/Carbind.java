package pl.com.mnemonic.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the carbind database table.
 * 
 */
@Entity
@NamedQuery(name="Carbind.findAll", query="SELECT s FROM Carbind s")
public class Carbind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcarbind;

	private byte archived;

	@Temporal(TemporalType.DATE)
	@Column(name="date_from")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="date_to")
	private Date dateTo;

	//bi-directional many-to-one association to Car
	@ManyToOne
	@JoinColumn(name="car")
	private Car car;

	//bi-directional many-to-one association to Caruser
	@ManyToOne
	@JoinColumn( name="caruser")
	private Caruser caruser;

	public Carbind() {
	}

	public int getIdcarbind() {
		return this.idcarbind;
	}

	public void setIdcarbind(int idcarbind) {
		this.idcarbind = idcarbind;
	}

	public byte getArchived() {
		return this.archived;
	}

	public void setArchived(byte archived) {
		this.archived = archived;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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