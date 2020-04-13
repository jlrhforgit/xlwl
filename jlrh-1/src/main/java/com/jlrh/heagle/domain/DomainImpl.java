package com.jlrh.heagle.domain;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.joda.time.DateTime;

@MappedSuperclass
public class DomainImpl implements Domain {
	@Id
	@GeneratedValue(generator = "sequenceStyleGenerator")
	@GenericGenerator(name = "sequenceStyleGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "YC_ID_SEQUENCE"),
			@Parameter(name = "initial_value", value = "1000"), @Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "optimizer", value = "pooled")})
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate = new Date();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate == null ? null : createdDate.toDate();
	}

	public boolean isNew() {
		return this.getId() == null;
	}

	public DateTime getCreatedDate() {
		return this.createdDate == null ? null : new DateTime();
	}

	public String toString() {
		return String.format("Entity of type %s with id : %s ", this.getClass().getName(), this.getId());
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (this == obj) {
			return true;
		} else {
			Domain that = (Domain) obj;
			return this.getId() == null ? false : this.getId().equals(that.getId());
		}
	}

	public int hashCode() {
		int hashCode = 17;
		hashCode = hashCode + (this.getId() == null ? 0 : this.getId().hashCode() * 31);
		return hashCode;
	}
}