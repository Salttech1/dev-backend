package kraheja.enggsys.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDetailBean {

    private String certNum;
    private Integer revNum;
    private Integer runSer;
    private String certType;
    private LocalDate certDate;
    private String certStatus;
    private String bldgCode;
    private String wing;
    private String domain;
    private String workGroup;
    private String workCode;
    private String cfgGroup;
    private String cfCode;
    private String socId;
    private String equipId;
    private String partyType;
    private String partyCode;
    private String contract;
    private Double conAmount;
    private LocalDate durFrom;
    private LocalDate durTo;
    private Double certAmount;
    private Double perDone;
    private String billRef;
    private Double billAmount;
    private String payRef;
    private LocalDate payDate; // payDateTime chagned
    private Double payAmount;
    private String transer;
    private Double mwctaxAmount;
    private Double tdsAmount;
    private Double tdsSur;
    private Double advAdjusted;
    private Double retained;
    private String payTender;
    private Integer trips;
    private String remarks;
    private String originator;
    private Double tPayment;
    private Double debit;
    private String debSocYN;
    private LocalDate asstaxFrom;
    private LocalDate asstaxUpto;
    private String taxLevel;
    private String prop;
    private String coy;
    private String city;
    private String project;
    private String proprietor;
    private String company;
    private String cityName;
    private String projectName;
    private String buildingName;
    private String workName;
    private String partyName;
    private String panNo;
    private String gstNo;
    private String mainRecId;
    private String mainParty;
    private String mainMatGroup;
    private Integer serviceTaxPerc;
    private Integer serviceTaxAmt;
    private String description;
    private String debitingParty;
    private String debitingReason;
    private Integer swachhCessPerc;
    private Integer swachhCessAmt;
    private Integer vatPerc;
    private Integer vatAmt;
    private Integer basicAmt;
    private Integer krishiCessPerc;
    private Integer krishiCessAmt;
    private Double cgstAmt;
    private Double sgstAmt;
    private Double igstAmt;
    private Double ugstAmt;
    

}

