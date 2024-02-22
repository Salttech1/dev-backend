package kraheja.commons.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the PAYEEBANK database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payeebank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="PYBK_CLOSEDATE")
	private Date pybkClosedate;

	@Id
	@Column(name="PYBK_CODE")
	private String pybkCode;

	@Column(name="PYBK_IFSC")
	private String pybkIfsc;

	@Column(name="PYBK_NAME")
	private String pybkName;

	@Column(name="PYBK_OPENDATE")
	private Date pybkOpendate;

	@Column(name="PYBK_ORIGSITE")
	private String pybkOrigsite;

	@Column(name="PYBK_SITE")
	private String pybkSite;

	@Column(name="PYBK_TODAY")
	private Date pybkToday;

	@Column(name="PYBK_USERID")
	private String pybkUserid;

}