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
 * The persistent class for the BROKER database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "BROK1", columnList = "brokCode ASC", unique = true)
})

public class Broker implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BrokerCK brokerCK;

	@Column(name="BROK_BROKLASTYR")
	private Double brokBroklastyr;

	@Column(name="BROK_BROKTHISYR")
	private Double brokBrokthisyr;

	@Column(name="BROK_BROKTODATE")
	private Double brokBroktodate;

	@Column(name="BROK_BUSSLASTYR")
	private Double brokBusslastyr;

	@Column(name="BROK_BUSSTHISYR")
	private Double brokBussthisyr;

	@Column(name="BROK_BUSSTODATE")
	private Double brokBusstodate;

	@Column(name="BROK_CITY")
	private String brokCity;

	@Column(name="BROK_CONTACTPERSON")
	private String brokContactperson;

	@Column(name="BROK_DESIGNATION")
	private String brokDesignation;

	@Column(name="BROK_NAME")
	private String brokName;

	@Column(name="BROK_ORIGSITE")
	private String brokOrigsite;

	@Column(name="BROK_RERA")
	private String brokRera;

	@Column(name="BROK_SITE")
	private String brokSite;

	@Column(name="BROK_TDSLASTYR")
	private Double brokTdslastyr;

	@Column(name="BROK_TDSTHISYR")
	private Double brokTdsthisyr;

	@Column(name="BROK_TDSTODATE")
	private Double brokTdstodate;

	@Column(name="BROK_TITLE")
	private String brokTitle;

	@Column(name="BROK_TODAY")
	private LocalDateTime brokToday;

	@Column(name="BROK_TYPE")
	private String brokType;

	@Column(name="BROK_USERID")
	private String brokUserid;
}