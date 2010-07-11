package ar.com.natlehmann.cdcatalogue.business.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
public class Volume implements Serializable {

	private static final long serialVersionUID = -7647286822722983463L;
	
	@Id
	@GeneratedValue
	private Integer volumeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@Column(name = "volumeName", nullable = false, length = 100, unique=true)
	@NotNull
	@Length(max = 100)
	private String volumeName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "volume")
	private List<Resource> resources = new LinkedList<Resource>();

	public Volume() {
	}

	public Volume(String volumeName) {
		this.volumeName = volumeName;
	}
	public Volume(Category category, String volumeName, List<Resource> resources) {
		this.category = category;
		this.volumeName = volumeName;
		this.resources = resources;
	}

	public Volume(Integer volumeId) {
		this.volumeId = volumeId;
	}

	public Volume(Category category, String volumeName) {
		this.category = category;
		this.volumeName = volumeName;
	}

	public Volume(Integer volumeId, Category category, String volumeName) {
		this(category,volumeName);
		this.volumeId = volumeId;
	}

	public Integer getVolumeId() {
		return this.volumeId;
	}

	public void setVolumeId(Integer volumeId) {
		this.volumeId = volumeId;
	}
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getVolumeName() {
		return this.volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	public List<Resource> getResources() {
		return this.resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	@Override
	public String toString() {
		return volumeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((volumeId == null) ? 0 : volumeId.hashCode());
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
		final Volume other = (Volume) obj;
		if (volumeId == null) {
			if (other.volumeId != null)
				return false;
		} else if (!volumeId.equals(other.volumeId))
			return false;
		return true;
	}

}
