package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	  @NamedQuery(name="AppSetting.findAll", query="SELECT model FROM AppSetting model"),
	})
public class AppSetting extends Model{
	private String libreOfficeHome;
	private String cmd;
	
	public AppSetting() {
		super();
	}

	public String getLibreOfficeHome() {
		return libreOfficeHome;
	}

	public void setLibreOfficeHome(String libreOfficeHome) {
		this.libreOfficeHome = libreOfficeHome;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppSetting [libreOfficeHome=");
		builder.append(libreOfficeHome);
		builder.append(", cmd=");
		builder.append(cmd);
		builder.append(", style=");
		builder.append("]");
		return builder.toString();
	}
}
