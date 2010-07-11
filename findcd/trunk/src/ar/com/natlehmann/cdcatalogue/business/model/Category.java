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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


@Entity
@Table(name="Category")
public class Category implements Serializable {

	private static final long serialVersionUID = -5951451132757461862L;

	@Id
	@GeneratedValue
	private Integer categoryId;
	
	@Column(unique=true, nullable=false, length=50)
	@NotNull
	@Length(max = 50)
	private String categoryName;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "category")
	private List<Volume> volumes = new LinkedList<Volume>();

	public Category() {
	}

	public Category(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category(String categoryName, List<Volume> volumes) {
		this.categoryName = categoryName;
		this.volumes = volumes;
	}

	public Category(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Category(Integer categoryId, String categoryName) {
		this(categoryId);
		this.categoryName = categoryName;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<Volume> getVolumes() {
		return this.volumes;
	}

	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
	}

	@Override
	public String toString() {
		return categoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoryId == null) ? 0 : categoryId.hashCode());
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
		final Category other = (Category) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		return true;
	}
}
