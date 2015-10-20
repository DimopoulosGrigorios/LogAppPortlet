package com.gnt.worklogger.entities;

import java.io.Serializable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WORK_PERFORMED database table.
 * 
 */
@Entity
@Table(name="WORK_PERFORMED")
@NamedQueries({
	@NamedQuery(name="WorkPerformed.findAll", query="SELECT w FROM WorkPerformed w"),
	@NamedQuery(name = "WorkPerformed.findWorkPerformedByUserAndDate", query = "select w from WorkPerformed w where (w.wpDate=?1 and w.user=?2)"),
	@NamedQuery(name = "WorkPerformed.findWorkPerformedByUser", query = "select w from WorkPerformed w where w.user=?1")
})
public class WorkPerformed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id  
    @SequenceGenerator(name = "WorkPerformedSequence", sequenceName = "WP_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "WorkPerformedSequence")
	@Column(name="WP_ID")
	private long wpId;

	@Column(name="WP_COMMENTS")
	private String wpComments;

	@Temporal(TemporalType.DATE)
	@Column(name="WP_DATE")
	private Date wpDate;

	@Column(name="WP_MHRS")
	private Float wpMhrs = 0F;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="PJ_ID")
	private Project project;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USR_ID")
	private User user;

	//bi-directional many-to-one association to WorkCategory
	@ManyToOne
	@JoinColumn(name="WC_ID")
	private WorkCategory workCategory;

	public WorkPerformed() {
	}

	public long getWpId() {
		return this.wpId;
	}

	public void setWpId(long wpId) {
		this.wpId = wpId;
	}

	public String getWpComments() {
		return this.wpComments;
	}

	public void setWpComments(String wpComments) {
		this.wpComments = wpComments;
	}

	public Date getWpDate() {
		return this.wpDate;
	}

	public void setWpDate(Date wpDate) {
		this.wpDate = wpDate;
	}

	public Float getWpMhrs() {
		return this.wpMhrs;
	}

	public void setWpMhrs(Float wpMhrs) {
		this.wpMhrs = wpMhrs;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WorkCategory getWorkCategory() {
		return this.workCategory;
	}

	public void setWorkCategory(WorkCategory workCategory) {
		this.workCategory = workCategory;
	}

}