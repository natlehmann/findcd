package ar.com.natlehmann.cdcatalogue.business.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


@Entity
public class Resource implements Serializable {


	private static final long serialVersionUID = -2408097098444063060L;

	@Id
	@GeneratedValue
	private Integer resourceId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volumeId", nullable = false)
	@NotNull
	private Volume volume;
	
	@Column(name = "resourceName", nullable = false, length = 512)
	@NotNull
	@Length(max = 512)
	private String resourceName;
	
	@Column(name = "path", nullable = false, length = 2048)
	@NotNull
	@Length(max = 2048)
	private String path;
	
	@Column(name = "resourceType", length = 20)
	@Length(max = 20)
	private String resourceType;
	
	@Column(name = "fileSize", length = 50)
	@Length(max = 50)
	private String fileSize;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name = "comments", length = 512)
	@Length(max = 512)
	private String comments;

	public Resource() {
	}

	public Resource(Volume volume, String resourceName, String path) {
		this.volume = volume;
		this.resourceName = resourceName;
		this.path = path;
	}
	public Resource(Volume volume, String resourceName, String path,
			String resourceType, String fileSize, Date modifiedDate,
			String comments) {
		this.volume = volume;
		this.resourceName = resourceName;
		this.path = path;
		this.resourceType = resourceType;
		this.fileSize = fileSize;
		this.modifiedDate = modifiedDate;
		this.comments = comments;
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Volume getVolume() {
		return this.volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((resourceId == null) ? 0 : resourceId.hashCode());
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
		final Resource other = (Resource) obj;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Resource " + this.resourceName 
				+ " - id: " + this.resourceId + " - path: " + this.path;
	}

}
