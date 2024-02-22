package kraheja.commons.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * The persistent class for the ENTITY database table.
 * 
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ENTITY",indexes = {
		@Index(name = "ENT1", columnList = "entClass ASC, entId ASC, entChar1 ASC, entChar2 ASC, entChar3 ASC", unique = true),
})
public class DbEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private EntityCK entityCk;

	@Column(name="ENT_BANNED")
	private String entBanned;

	@Column(name="ENT_BANNEDON")
	private Date entBannedon;

	@Column(name="ENT_CHAR4")
	private String entChar4;

	@Column(name="ENT_CHAR5")
	private String entChar5;

	@Column(name="ENT_DATE1")
	private LocalDate entDate1;

	@Column(name="ENT_DATE2")
	private LocalDate entDate2;

	@Column(name="ENT_DATE3")
	private LocalDate entDate3;

	@Column(name="ENT_DATE4")
	private LocalDate entDate4;

	@Column(name="ENT_DATE5")
	private LocalDate entDate5;

	@Column(name="ENT_NAME")
	private String entName;

	@Column(name="ENT_NUM1")
	private Double entNum1;

	@Column(name="ENT_NUM2")
	private Double entNum2;

	@Column(name="ENT_NUM3")
	private Double entNum3;

	@Column(name="ENT_NUM4")
	private Double entNum4;

	@Column(name="ENT_NUM5")
	private Double entNum5;

	@Column(name="ENT_REMARK")
	private String entRemark;

	@Column(name="ENT_SITE")
	private String entSite;

	@Column(name="ENT_TODAY")
	private Date entToday;

	@Column(name="ENT_USERID")
	private String entUserid;
}