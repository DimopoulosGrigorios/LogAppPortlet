package com.gnt.worklogger.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PROJECTS database table.
 * 
 */
@Entity
@Table(name="PROJECTS")
@NamedQueries({
	@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p"),
	@NamedQuery(name="Project.findAllActive", query="SELECT p FROM Project p WHERE p.pjIsActive = 1"),
	@NamedQuery(name="Project.findAllActiveByUser", query="SELECT p FROM Project p, UsersProject up WHERE p.pjIsActive = 1 AND p.pjId = up.usprId AND up.user = ?1")
})
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="PROJECTS_PJID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECTS_PJID_GENERATOR")
	@Column(name="PJ_ID")
	private long pjId;

	@Temporal(TemporalType.DATE)
	@Column(name="PJ_ACT_END_DATE")
	private Date pjActEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="PJ_ACT_START_DATE")
	private Date pjActStartDate;

	@Column(name="PJ_COMMENTS")
	private String pjComments;

	@Column(name="PJ_CONSUMED_MHRS")
	private BigDecimal pjConsumedMhrs;

	@Column(name="PJ_DESCR")
	private String pjDescr;

	@Temporal(TemporalType.DATE)
	@Column(name="PJ_EST_END_DATE")
	private Date pjEstEndDate;

	@Column(name="PJ_EST_MHRS")
	private BigDecimal pjEstMhrs;

	@Temporal(TemporalType.DATE)
	@Column(name="PJ_EST_START_DATE")
	private Date pjEstStartDate;

	@Column(name="PJ_ID_PAR")
	private BigDecimal pjIdPar;

	@Column(name="PJ_IS_ACTIVE")
	private BigDecimal pjIsActive;

	@Column(name="PJ_SUBPJ_CONS_MHRS")
	private BigDecimal pjSubpjConsMhrs;

	//bi-directional many-to-one association to ProjectType
	@ManyToOne
	@JoinColumn(name="PRT_ID")
	private ProjectType projectType;

	//bi-directional many-to-one association to UsersProject
	@OneToMany(mappedBy="project")
	private List<UsersProject> usersProjects;

	//bi-directional many-to-one association to WorkPerformed
	@OneToMany(mappedBy="project")
	private List<WorkPerformed> workPerformeds;

	public Project() {
	}

	public long getPjId() {
		return this.pjId;
	}

	public void setPjId(long pjId) {
		this.pjId = pjId;
	}

	public Date getPjActEndDate() {
		return this.pjActEndDate;
	}

	public void setPjActEndDate(Date pjActEndDate) {
		this.pjActEndDate = pjActEndDate;
	}

	public Date getPjActStartDate() {
		return this.pjActStartDate;
	}

	public void setPjActStartDate(Date pjActStartDate) {
		this.pjActStartDate = pjActStartDate;
	}

	public String getPjComments() {
		return this.pjComments;
	}

	public void setPjComments(String pjComments) {
		this.pjComments = pjComments;
	}

	public BigDecimal getPjConsumedMhrs() {
		return this.pjConsumedMhrs;
	}

	public void setPjConsumedMhrs(BigDecimal pjConsumedMhrs) {
		this.pjConsumedMhrs = pjConsumedMhrs;
	}

	public String getPjDescr() {
		return this.pjDescr;
	}

	public void setPjDescr(String pjDescr) {
		this.pjDescr = pjDescr;
	}

	public Date getPjEstEndDate() {
		return this.pjEstEndDate;
	}

	public void setPjEstEndDate(Date pjEstEndDate) {
		this.pjEstEndDate = pjEstEndDate;
	}

	public BigDecimal getPjEstMhrs() {
		return this.pjEstMhrs;
	}

	public void setPjEstMhrs(BigDecimal pjEstMhrs) {
		this.pjEstMhrs = pjEstMhrs;
	}

	public Date getPjEstStartDate() {
		return this.pjEstStartDate;
	}

	public void setPjEstStartDate(Date pjEstStartDate) {
		this.pjEstStartDate = pjEstStartDate;
	}

	public BigDecimal getPjIdPar() {
		return this.pjIdPar;
	}

	public void setPjIdPar(BigDecimal pjIdPar) {
		this.pjIdPar = pjIdPar;
	}

	public BigDecimal getPjIsActive() {
		return this.pjIsActive;
	}

	public void setPjIsActive(BigDecimal pjIsActive) {
		this.pjIsActive = pjIsActive;
	}

	public BigDecimal getPjSubpjConsMhrs() {
		return this.pjSubpjConsMhrs;
	}

	public void setPjSubpjConsMhrs(BigDecimal pjSubpjConsMhrs) {
		this.pjSubpjConsMhrs = pjSubpjConsMhrs;
	}

	public ProjectType getProjectType() {
		return this.projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public List<UsersProject> getUsersProjects() {
		return this.usersProjects;
	}

	public void setUsersProjects(List<UsersProject> usersProjects) {
		this.usersProjects = usersProjects;
	}

	public UsersProject addUsersProject(UsersProject usersProject) {
		getUsersProjects().add(usersProject);
		usersProject.setProject(this);

		return usersProject;
	}

	public UsersProject removeUsersProject(UsersProject usersProject) {
		getUsersProjects().remove(usersProject);
		usersProject.setProject(null);

		return usersProject;
	}

	public List<WorkPerformed> getWorkPerformeds() {
		return this.workPerformeds;
	}

	public void setWorkPerformeds(List<WorkPerformed> workPerformeds) {
		this.workPerformeds = workPerformeds;
	}

	public WorkPerformed addWorkPerformed(WorkPerformed workPerformed) {
		getWorkPerformeds().add(workPerformed);
		workPerformed.setProject(this);

		return workPerformed;
	}

	public WorkPerformed removeWorkPerformed(WorkPerformed workPerformed) {
		getWorkPerformeds().remove(workPerformed);
		workPerformed.setProject(null);

		return workPerformed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (pjId ^ (pjId >>> 32));
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
		Project other = (Project) obj;
		if (pjId != other.pjId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return pjDescr;
	}

}