package kraheja.commons.entity;

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
 * The persistent class for the TRANSACTIONLOG database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "TRANSACTIONLOG1", columnList = "tlogSrno Asc", unique = true)
})

public class Transactionlog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TransactionlogCK transactionlogCK;

	@Column(name="TLOG_FORMNAME")
	private String tlogFormname ;

	@Column(name="TLOG_NEUMONIC")
	private String tlogNeumonic ;

	@Column(name="TLOG_ORIGSITE")
	private String tlogOrigsite ;

	@Column(name="TLOG_SITE")
	private String tlogSite ;

	@Column(name="TLOG_TABLES")
	private String tlogTables ;

	@Column(name="TLOG_TODAY")
	private LocalDateTime tlogToday ;

	@Column(name="TLOG_TRANSACTIONMODE")
	private String tlogTransactionmode ;

	@Column(name="TLOG_TRANSACTIONNO")
	private String tlogTransactionno ;

	@Column(name="TLOG_USERID")
	private String tlogUserid ;

}