package com.gnt.worklogger.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the USERS_PROJECTS database table.
 * 
 */
@Entity
@Table(name="USERS_PROJECTS")
@NamedQueries({
	@NamedQuery(name="UsersProject.findAll", query="SELECT u FROM UsersProject u"),
	@NamedQuery(name="UsersProject.findProjectsByUser", query="SELECT u.project FROM UsersProject u WHERE u.user=?1")
})
public class UsersProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_PROJECTS_USPRID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_PROJECTS_USPRID_GENERATOR")
	@Column(name="USPR_ID")
	private long usprId;

	@Column(name="USPR_COMMENTS")
	private String usprComments;

	@Temporal(TemporalType.DATE)
	@Column(name="USPR_END_DATE")
	private Date usprEndDate;

	@Column(name="USPR_PERCENT")
	private BigDecimal usprPercent;

	@Temporal(TemporalType.DATE)
	@Column(name="USPR_START_DATE")
	private Date usprStartDate;

	@Column(name="USPR_TYPE")
	private BigDecimal usprType;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="PJ_ID")
	private Project project;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USR_ID")
	private User user;

	public UsersProject() {
	}

	public long getUsprId() {
		return this.usprId;
	}

	public void setUsprId(long usprId) {
		this.usprId = usprId;
	}

	public String getUsprComments() {
		return this.usprComments;
	}

	public void setUsprComments(String usprComments) {
		this.usprComments = usprComments;
	}

	public Date getUsprEndDate() {
		return this.usprEndDate;
	}

	public void setUsprEndDate(Date usprEndDate) {
		this.usprEndDate = usprEndDate;
	}

	public BigDecimal getUsprPercent() {
		return this.usprPercent;
	}

	public void setUsprPercent(BigDecimal usprPercent) {
		this.usprPercent = usprPercent;
	}

	public Date getUsprStartDate() {
		return this.usprStartDate;
	}

	public void setUsprStartDate(Date usprStartDate) {
		this.usprStartDate = usprStartDate;
	}

	public BigDecimal getUsprType() {
		return this.usprType;
	}

	public void setUsprType(BigDecimal usprType) {
		this.usprType = usprType;
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

}