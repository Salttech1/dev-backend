package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * The persistent class for the LOANCOMPANY database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "LOANCO1", columnList = "lcoyCode ASC", unique = true)
})

public class Loancompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LoancompanyCK loancompanyCK;

	@Column(name="LCOY_NAME")
	private String lcoyName;

	@Column(name="LCOY_NOCYN")
	private String lcoyNocyn;

	@Column(name="LCOY_SITE")
	private String lcoySite;

	@Column(name="LCOY_TODAY")
	private LocalDateTime lcoyToday;

	@Column(name="LCOY_USERID")
	private String lcoyUserid;

}