package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.juli.jobapp.jobmodel.enums.Title;

@Entity
@NamedQueries({
	  @NamedQuery(name="Contact.findAll", query="SELECT model FROM Contact model"),
	  @NamedQuery(name="Contact.findByName", query="SELECT model FROM Contact model WHERE model.lastName = :name")
	})
public class Contact extends Model {
	private Title title;
	private String firstName;
	private String lastName;
	private String phone;
	private String mobile;
	private String email;

	public Contact() {
		super();
	}

	public Contact(Title title, String firstName, String lastName, String phone, String mobile, String email) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ID: "+super.getId()+"\nContact [title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", mobile=" + mobile + ", email=" + email + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Contact other = (Contact) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (title != other.title)
			return false;
		return true;
	}
	
	
}
