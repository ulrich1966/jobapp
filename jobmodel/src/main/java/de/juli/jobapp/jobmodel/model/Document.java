package de.juli.jobapp.jobmodel.model;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.apache.commons.io.FilenameUtils;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Document extends Model {
	private String template;
	private String target;
	private String weblink;
	private String name;
	private String extension;

	public Document() {
		super();
	}

	public Document(String name) {
		this.name = name;
		setExtension(name);
	}
	
	public Document(String template, String name) {
		this(name);
		this.template = template;
	}

	public Document(String template, String target, String name) {
		this(template, name);
		this.target = target;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setExtension(name);
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String name) {
		String ext = FilenameUtils.getExtension(name);
		if(ext != null && !ext.isEmpty()) {
			this.extension = ext;			
		} else {
			this.extension = name;						
		}
	}

	@Override
	public String toString() {
		return "Document ["+super.toString()+ " template=" + template + ", target=" + target + ", weblink=" + weblink + ", name=" + name + ", extension=" + extension + "]";
	}
}
