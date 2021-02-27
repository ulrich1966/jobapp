package de.juli.jobmodel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	  @NamedQuery(name="Company.findAll", query="SELECT model FROM Company model"),
	  @NamedQuery(name="Company.findByName", query="SELECT model FROM Company model WHERE model.name = :name")
	})
public class Company extends Model{
	@Column(unique=true)
	private String name;
	private String zip;
	private String city;
	private String street;
	private String addressee;
	@Column(unique=true)
	private String web;
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL)
	private List<Job> jobs;
	@OneToOne(cascade=CascadeType.ALL)
	private Contact contact;

	public Company() {
		super();
	}

	public Company(String name, String zip, String city, String street, Contact contact) {
		super();
		this.name = name;
		this.zip = zip;
		this.city = city;
		this.street = street;
		this.contact = contact;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void addJob(Job job) {
		if(this.jobs == null) {
			this.jobs = new ArrayList<>();
		}
		this.jobs.add(job);
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", zip=" + zip + ", city=" + city + ", street=" + street + ", web=" + web
				+ ", jobs=" + jobs + ", contact=" + contact + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((web == null) ? 0 : web.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (web == null) {
			if (other.web != null)
				return false;
		} else if (!web.equals(other.web))
			return false;
		return true;
	}
}
