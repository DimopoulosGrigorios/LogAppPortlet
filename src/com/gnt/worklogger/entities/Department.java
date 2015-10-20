package com.gnt.worklogger.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DEPARTMENTS database table.
 * 
 */
@Entity
@Table(name="DEPARTMENTS")
@NamedQuery(name="Department.findAll", query="SELECT d FROM Department d")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="DEPARTMENTS_DEPTID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEPARTMENTS_DEPTID_GENERATOR")
	@Column(name="DEPT_ID")
	private long deptId;

	@Column(name="DEPT_DESCR")
	private String deptDescr;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="department")
	private List<User> users;

	public Department() {
	}

	public long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getDeptDescr() {
		return this.deptDescr;
	}

	public void setDeptDescr(String deptDescr) {
		this.deptDescr = deptDescr;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setDepartment(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setDepartment(null);

		return user;
	}

}