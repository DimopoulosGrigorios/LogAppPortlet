package com.gnt.worklogger.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
//@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
//@NamedQuery(name = "User.findUserToLogin", query = "select u from User u where (u.usrLogin=?1 and u.usrPwd=?2)")
@NamedQuery(name = "User.findUserToLogin", query = "select u from User u where (u.lfUsrId=?1)")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_USRID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_USRID_GENERATOR")
	@Column(name="USR_ID")
	private long usrId;

	@Column(name="LIFERAY_USER_ID")
	private long lfUsrId;
	
	@Column(name="USR_ADDRESS")
	private String usrAddress;

	@Column(name="USR_COMMENTS")
	private String usrComments;

	@Temporal(TemporalType.DATE)
	@Column(name="USR_HIRE_DATE")
	private Date usrHireDate;

	@Temporal(TemporalType.DATE)
	@Column(name="USR_LEAVE_DATE")
	private Date usrLeaveDate;

	@Column(name="USR_LOGIN")
	private String usrLogin;

	@Column(name="USR_NAME")
	private String usrName;

	@Column(name="USR_PWD")
	private String usrPwd;

	@Column(name="USR_TELS")
	private String usrTels;

	@Column(name="USR_TYPE")
	private BigDecimal usrType;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Department department;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="ROLE_CODE")
	private Role role;

	//bi-directional many-to-one association to UsersProject
	@OneToMany(mappedBy="user")
	private List<UsersProject> usersProjects;

	//bi-directional many-to-one association to WorkPerformed
	@OneToMany(mappedBy="user")
	private List<WorkPerformed> workPerformeds;
	

	public User() {
	}

	public long getUsrId() {
		return this.usrId;
	}

	public void setUsrId(long usrId) {
		this.usrId = usrId;
	}

	public String getUsrAddress() {
		return this.usrAddress;
	}

	public void setUsrAddress(String usrAddress) {
		this.usrAddress = usrAddress;
	}

	public String getUsrComments() {
		return this.usrComments;
	}

	public void setUsrComments(String usrComments) {
		this.usrComments = usrComments;
	}

	public Date getUsrHireDate() {
		return this.usrHireDate;
	}

	public void setUsrHireDate(Date usrHireDate) {
		this.usrHireDate = usrHireDate;
	}

	public Date getUsrLeaveDate() {
		return this.usrLeaveDate;
	}

	public void setUsrLeaveDate(Date usrLeaveDate) {
		this.usrLeaveDate = usrLeaveDate;
	}

	public String getUsrLogin() {
		return this.usrLogin;
	}

	public void setUsrLogin(String usrLogin) {
		this.usrLogin = usrLogin;
	}

	public String getUsrName() {
		return this.usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrPwd() {
		return this.usrPwd;
	}

	public void setUsrPwd(String usrPwd) {
		this.usrPwd = usrPwd;
	}

	public String getUsrTels() {
		return this.usrTels;
	}

	public void setUsrTels(String usrTels) {
		this.usrTels = usrTels;
	}

	public BigDecimal getUsrType() {
		return this.usrType;
	}

	public void setUsrType(BigDecimal usrType) {
		this.usrType = usrType;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<UsersProject> getUsersProjects() {
		return this.usersProjects;
	}

	public void setUsersProjects(List<UsersProject> usersProjects) {
		this.usersProjects = usersProjects;
	}

	public UsersProject addUsersProject(UsersProject usersProject) {
		getUsersProjects().add(usersProject);
		usersProject.setUser(this);

		return usersProject;
	}

	public UsersProject removeUsersProject(UsersProject usersProject) {
		getUsersProjects().remove(usersProject);
		usersProject.setUser(null);

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
		workPerformed.setUser(this);

		return workPerformed;
	}

	public WorkPerformed removeWorkPerformed(WorkPerformed workPerformed) {
		getWorkPerformeds().remove(workPerformed);
		workPerformed.setUser(null);

		return workPerformed;
	}

	public long getLfUsrId() {
		return lfUsrId;
	}

	public void setLfUsrId(long lfUsrId) {
		this.lfUsrId = lfUsrId;
	}

}