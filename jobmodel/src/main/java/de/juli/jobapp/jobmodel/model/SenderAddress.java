package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	  @NamedQuery(name="SenderAddress.findAll", query="SELECT model FROM SenderAddress model"),
	  @NamedQuery(name="SenderAddress.findByName", query="SELECT model FROM SenderAddress model WHERE model.firstName = :firstName"),
	  @NamedQuery(name="SenderAddress.findByLastName", query="SELECT model FROM SenderAddress model WHERE model.lastName = :firstName")
	})
public class SenderAddress extends Model{
	private String firstName;
	private String lastName;
	private String street;
	private Integer zip;
	private String city;
	private String phone;
	private String mail;
	
	
	public SenderAddress() {
		super();		
	}

	public SenderAddress(String firstName, String lastName, String street, Integer zip, String city, String phone, String mail) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.phone = phone;
		this.mail = mail;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SenderAddress [firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", street=");
		builder.append(street);
		builder.append(", zip=");
		builder.append(zip);
		builder.append(", city=");
		builder.append(city);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", mail=");
		builder.append(mail);
		builder.append("]");
		return builder.toString();
	}
}
