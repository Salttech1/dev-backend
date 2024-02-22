package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* The persistent class for the INFRSAOGRP01_PRINT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "INFRSAOGRP01_PRINT", indexes = {
		@Index(name = "INFRSAOGRP01_PRINT", columnList = "saogrpBillnum Asc, saogrpSessid Asc, saogrpInvoiceno Asc", unique = true)
})

public class Print implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PrintCK printCK;

	@Column(name="SAOGRP_ADMINCHARGES")
	private Double saogrpAdmincharges ;

	@Column(name="SAOGRP_BILLAMT")
	private Double saogrpBillamt ;

	@Column(name="SAOGRP_BILLARREARS")
	private Double saogrpBillarrears ;

	@Column(name="SAOGRP_BILLDATE")
	private LocalDate saogrpBilldate ;

	@Column(name="SAOGRP_BILLFROM")
	private LocalDate saogrpBillfrom ;

	@Column(name="SAOGRP_BILLMODE")
	private String saogrpBillmode ;

	@Column(name="SAOGRP_BILLMONTH")
	private String saogrpBillmonth ;

	@Column(name="SAOGRP_BILLTO")
	private LocalDate saogrpBillto ;

	@Column(name="SAOGRP_BLDGCODE")
	private String saogrpBldgcode ;

	@Column(name="SAOGRP_CGST")
	private Double saogrpCgst ;

	@Column(name="SAOGRP_CGSTPERC")
	private Double saogrpCgstperc ;

	@Column(name="SAOGRP_FLATNUM")
	private String saogrpFlatnum ;

	@Column(name="SAOGRP_IGST")
	private Double saogrpIgst ;

	@Column(name="SAOGRP_IGSTPERC")
	private Double saogrpIgstperc ;

	@Column(name="SAOGRP_INTARREARS")
	private Double saogrpIntarrears ;

	@Column(name="SAOGRP_INTEREST")
	private Double saogrpInterest ;

	@Column(name="SAOGRP_IRNNO")
	private String saogrpIrnno ;

	@Column(name="SAOGRP_KRISHICESS")
	private Double saogrpKrishicess ;

	@Column(name="SAOGRP_OUTRATE")
	private Double saogrpOutrate ;

	@Column(name="SAOGRP_OWNERID")
	private String saogrpOwnerid ;

	@Column(name="SAOGRP_SERVICETAX")
	private Double saogrpServicetax ;

	@Column(name="SAOGRP_SGST")
	private Double saogrpSgst ;

	@Column(name="SAOGRP_SGSTPERC")
	private Double saogrpSgstperc ;

	@Column(name="SAOGRP_SWACHHCESS")
	private Double saogrpSwachhcess ;

	@Column(name="SAOGRP_WING")
	private String saogrpWing ;

}
