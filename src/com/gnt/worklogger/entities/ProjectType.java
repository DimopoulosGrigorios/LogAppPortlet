package com.gnt.worklogger.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PROJECT_TYPES database table.
 * 
 */
@Entity
@Table(name="PROJECT_TYPES")
@NamedQuery(name="ProjectType.findAll", query="SELECT p FROM ProjectType p")
public class ProjectType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="PROJECT_TYPES_PRTID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_TYPES_PRTID_GENERATOR")
	@Column(name="PRT_ID")
	private long prtId;

	@Column(name="PRT_DESCR")
	private String prtDescr;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="projectType")
	private List<Project> projects;

	public ProjectType() {
	}

	public long getPrtId() {
		return this.prtId;
	}

	public void setPrtId(long prtId) {
		this.prtId = prtId;
	}

	public String getPrtDescr() {
		return this.prtDescr;
	}

	public void setPrtDescr(String prtDescr) {
		this.prtDescr = prtDescr;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setProjectType(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setProjectType(null);

		return project;
	}

}