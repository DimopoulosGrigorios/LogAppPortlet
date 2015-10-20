package com.gnt.worklogger.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "ROLES" database table.
 * 
 */
@Entity
@Table(name="\"ROLES\"")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name=""ROLES"_ROLECODE_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=""ROLES"_ROLECODE_GENERATOR")
	@Column(name="ROLE_CODE")
	private long roleCode;

	@Column(name="ROLE_DESCR")
	private String roleDescr;

//	//bi-directional many-to-one association to RolesPrivil
//	@OneToMany(mappedBy="role")
//	private List<RolesPrivil> rolesPrivils;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="role")
	private List<User> users;

	public Role() {
	}

	public long getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(long roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDescr() {
		return this.roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

//	public List<RolesPrivil> getRolesPrivils() {
//		return this.rolesPrivils;
//	}
//
//	public void setRolesPrivils(List<RolesPrivil> rolesPrivils) {
//		this.rolesPrivils = rolesPrivils;
//	}
//
//	public RolesPrivil addRolesPrivil(RolesPrivil rolesPrivil) {
//		getRolesPrivils().add(rolesPrivil);
//		rolesPrivil.setRole(this);
//
//		return rolesPrivil;
//	}
//
//	public RolesPrivil removeRolesPrivil(RolesPrivil rolesPrivil) {
//		getRolesPrivils().remove(rolesPrivil);
//		rolesPrivil.setRole(null);
//
//		return rolesPrivil;
//	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setRole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setRole(null);

		return user;
	}

}