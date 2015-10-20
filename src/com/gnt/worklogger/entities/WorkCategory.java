package com.gnt.worklogger.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the WORK_CATEGORIES database table.
 * 
 */
@Entity
@Table(name="WORK_CATEGORIES")
@NamedQuery(name="WorkCategory.findAll", query="SELECT w FROM WorkCategory w")
public class WorkCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="WORK_CATEGORIES_WCID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WORK_CATEGORIES_WCID_GENERATOR")
	@Column(name="WC_ID")
	private long wcId;

	@Column(name="WC_COMMENTS")
	private String wcComments;

	@Column(name="WC_DESCR")
	private String wcDescr;

	//bi-directional many-to-one association to WorkPerformed
	@OneToMany(mappedBy="workCategory")
	private List<WorkPerformed> workPerformeds;

	public WorkCategory() {
	}

	public long getWcId() {
		return this.wcId;
	}

	public void setWcId(long wcId) {
		this.wcId = wcId;
	}

	public String getWcComments() {
		return this.wcComments;
	}

	public void setWcComments(String wcComments) {
		this.wcComments = wcComments;
	}

	public String getWcDescr() {
		return this.wcDescr;
	}

	public void setWcDescr(String wcDescr) {
		this.wcDescr = wcDescr;
	}

	public List<WorkPerformed> getWorkPerformeds() {
		return this.workPerformeds;
	}

	public void setWorkPerformeds(List<WorkPerformed> workPerformeds) {
		this.workPerformeds = workPerformeds;
	}

	public WorkPerformed addWorkPerformed(WorkPerformed workPerformed) {
		getWorkPerformeds().add(workPerformed);
		workPerformed.setWorkCategory(this);

		return workPerformed;
	}

	public WorkPerformed removeWorkPerformed(WorkPerformed workPerformed) {
		getWorkPerformeds().remove(workPerformed);
		workPerformed.setWorkCategory(null);

		return workPerformed;
	}

	@Override
	public String toString() {
		return  wcDescr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (wcId ^ (wcId >>> 32));
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
		WorkCategory other = (WorkCategory) obj;
		if (wcId != other.wcId)
			return false;
		return true;
	}

}