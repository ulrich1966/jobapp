package de.juli.jobapp.jobmodel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	  @NamedQuery(name="Source.findAll", query="SELECT model FROM Source model"),
	  @NamedQuery(name="Source.findByName", query="SELECT model FROM Source model WHERE model.name = :name")
	})
public class Source extends Model{
	@Column(unique=true)
	private String name;
	private String pronomen;

	public Source() {
		super();
	}
	
	public Source(String pronomen, String name) {
		this();
		this.name = name;
		this.pronomen = pronomen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPronomen() {
		return pronomen;
	}

	public void setPronomen(String pronomen) {
		this.pronomen = pronomen;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Source [name=");
		builder.append(name);
		builder.append(", pronomen=");
		builder.append(pronomen);
		builder.append("]");
		return builder.toString();
	}

	
}
