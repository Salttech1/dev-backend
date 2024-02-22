package kraheja.commons.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the PASSWD database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "Passwd.findAll", query = "SELECT p FROM Passwd p")
public class Passwd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String userlog;

	private String active;

	private String chgpswdnxt;

	private Date expiredate;

	private String exppswddays;

	private String logexp;

	private String modsup;

	private String modules;

	private String origsite;

	private String password;

	private String site;

	private String staffid;

	private Date today;

	private String userid;

	private String usersht;

	private String usersup;
	
	@Column(name = "STARTUP_REPORT")
	private String startupReport;
	
	@Column(name = "USER_SITE")
	private String userSite;
	
	@Column(name = "ANGULAR_USER")
	private String angularUser;
	
	@Column(name = "ANGULAR_PASSWORD")
	private String angularPassword;
}