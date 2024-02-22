package kraheja.payroll.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "v_empsalarypaid")
@Immutable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class v_empsalarypaid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id; // The row number!
	
//	vspd_jobtype,vspd_deptcode,vspd_deptname,vspd_desigpost,vspd_designame,vspd_salarygrpcode,vspd_salarygrpname,vspd_fromdate,vspd_todate,vspd_paymonth,vspd_salarytype,vspd_paystatus,vspd_dayspaid,vspd_daysencashed,vspd_daysunionfund,vspd_daysabsent,vspd_daysadjlastmth,vspd_daysadjprvlastmth,vspd_daysnhpay,vspd_othrsprvlastmth,vspd_othrslastmth,vspd_othours,vspd_instrumenttype,vspd_instrumentno,vspd_instrumentdate,vspd_instrumentbank,vspd_paydate,vspd_actranser,vspd_adv,vspd_arrears,vspd_attire,vspd_basic,vspd_stipend,vspd_conspay,vspd_bonus,vspd_ca,vspd_cha,vspd_commission,vspd_incentive,vspd_srvchg,vspd_srvchgded,vspd_convriemb,vspd_coupons,vspd_da,vspd_ea,vspd_enta,vspd_exgratia,vspd_giftvouch,vspd_gratuity,vspd_ha,vspd_hra,vspd_ja,vspd_leaveenc,vspd_lta,vspd_ma,vspd_meala,vspd_medical,vspd_nhpay,vspd_noticepay,vspd_oa,vspd_ot,vspd_othearn,vspd_soca,vspd_spa,vspd_soccontriw,vspd_srva,vspd_ta,vspd_tela,vspd_uniform,vspd_advreco,vspd_birla,vspd_bloan,vspd_cess,vspd_cloan,vspd_cloanint,vspd_drf,vspd_esic,vspd_empyesic,vspd_fineded,vspd_fpf,vspd_gloan,vspd_gloanint,vspd_hloan,vspd_hotelbills,vspd_it,vspd_lic,vspd_lunchded,vspd_spiritserv,vspd_socretcont,vspd_lwf,vspd_notpayreco,vspd_oloan,vspd_othded,vspd_emplpf,vspd_empypenf,vspd_empypf,vspd_pf,vspd_pt,vspd_sloan,vspd_sloanint,vspd_soccontri,vspd_surchg,vspd_tata,vspd_transpded,vspd_uf,vspd_vpf,vspd_netpay,vspd_pfwages,vspd_reg_settle
//	@Column(name="vspd_empcode")
//	private String vspdEmpcode;
//	
//	@Column(name="vspd_coy")
//	private String vspdCoy;
//	
//	@Column(name="vspd_emptype")
//	private String vspdEmptype ;
//	
//	@Column(name="vspd_emptype")
//	private String vspdEmptype ;
	
	@Column(name="VSPD_ACTRANSER")
	private String vspdActranser ;

	@Column(name="VSPD_ADV")
	private Integer vspdAdv ;

	@Column(name="VSPD_ADVRECO")
	private Integer vspdAdvreco ;

	@Column(name="VSPD_ARREARS")
	private Integer vspdArrears ;

	@Column(name="VSPD_ATTIRE")
	private Integer vspdAttire ;

	@Column(name="VSPD_BASIC")
	private Integer vspdBasic ;

	@Column(name="VSPD_BIRLA")
	private Integer vspdBirla ;

	@Column(name="VSPD_BLOAN")
	private Integer vspdBloan ;

	@Column(name="VSPD_BONUS")
	private Integer vspdBonus ;

	@Column(name="VSPD_CA")
	private Integer vspdCa ;

	@Column(name="VSPD_CESS")
	private Integer vspdCess ;

	@Column(name="VSPD_CHA")
	private Integer vspdCha ;

	@Column(name="VSPD_CLOAN")
	private Integer vspdCloan ;

	@Column(name="VSPD_CLOANINT")
	private Integer vspdCloanint ;

	@Column(name="VSPD_COMMISSION")
	private Integer vspdCommission ;

	@Column(name="VSPD_CONSPAY")
	private Integer vspdConspay ;

	@Column(name="VSPD_CONVRIEMB")
	private Integer vspdConvriemb ;

	@Column(name="VSPD_COUPONS")
	private Integer vspdCoupons ;

	@Column(name="VSPD_COY")
	private String vspdCoy ;

	@Column(name="VSPD_DA")
	private Integer vspdDa ;

	@Column(name="VSPD_DAYSABSENT")
	private Double vspdDaysabsent ;

	@Column(name="VSPD_DAYSADJLASTMTH")
	private Double vspdDaysadjlastmth ;

	@Column(name="VSPD_DAYSADJPRVLASTMTH")
	private Double vspdDaysadjprvlastmth ;

	@Column(name="VSPD_DAYSENCASHED")
	private Double vspdDaysencashed ;

	@Column(name="VSPD_DAYSNHPAY")
	private Double vspdDaysnhpay ;

	@Column(name="VSPD_DAYSPAID")
	private Double vspdDayspaid ;

	@Column(name="VSPD_DAYSUNIONFUND")
	private Double vspdDaysunionfund ;

	@Column(name="VSPD_DEPTCODE")
	private String vspdDeptcode ;

	@Column(name="VSPD_DEPTNAME")
	private String vspdDeptname ;

	@Column(name="VSPD_DESIGNAME")
	private String vspdDesigname ;

	@Column(name="VSPD_DESIGPOST")
	private String vspdDesigpost ;

	@Column(name="VSPD_DRF")
	private Integer vspdDrf ;

	@Column(name="VSPD_EA")
	private Integer vspdEa ;

	@Column(name="VSPD_EMPCODE")
	private String vspdEmpcode ;

	@Column(name="VSPD_EMPLPF")
	private Integer vspdEmplpf ;

	@Column(name="VSPD_EMPTYPE")
	private String vspdEmptype ;

	@Column(name="VSPD_EMPYESIC")
	private Integer vspdEmpyesic ;

	@Column(name="VSPD_EMPYPENF")
	private Integer vspdEmpypenf ;

	@Column(name="VSPD_EMPYPF")
	private Integer vspdEmpypf ;

	@Column(name="VSPD_ENTA")
	private Integer vspdEnta ;

	@Column(name="VSPD_ESIC")
	private Integer vspdEsic ;

	@Column(name="VSPD_EXGRATIA")
	private Integer vspdExgratia ;

	@Column(name="VSPD_FINEDED")
	private Integer vspdFineded ;

	@Column(name="VSPD_FPF")
	private Integer vspdFpf ;

	@Column(name="VSPD_FROMDATE")
	private LocalDate vspdFromdate ;

	@Column(name="VSPD_GIFTVOUCH")
	private Integer vspdGiftvouch ;

	@Column(name="VSPD_GLOAN")
	private Integer vspdGloan ;

	@Column(name="VSPD_GLOANINT")
	private Integer vspdGloanint ;

	@Column(name="VSPD_GRATUITY")
	private Integer vspdGratuity ;

	@Column(name="VSPD_HA")
	private Integer vspdHa ;

	@Column(name="VSPD_HLOAN")
	private Integer vspdHloan ;

	@Column(name="VSPD_HOTELBILLS")
	private Integer vspdHotelbills ;

	@Column(name="VSPD_HRA")
	private Integer vspdHra ;

	@Column(name="VSPD_INCENTIVE")
	private Integer vspdIncentive ;

	@Column(name="VSPD_INSTRUMENTBANK")
	private String vspdInstrumentbank ;

	@Column(name="VSPD_INSTRUMENTDATE")
	private LocalDate vspdInstrumentdate ;

	@Column(name="VSPD_INSTRUMENTNO")
	private String vspdInstrumentno ;

	@Column(name="VSPD_INSTRUMENTTYPE")
	private String vspdInstrumenttype ;

	@Column(name="VSPD_IT")
	private Integer vspdIt ;

	@Column(name="VSPD_JA")
	private Integer vspdJa ;

	@Column(name="VSPD_JOBTYPE")
	private String vspdJobtype ;

	@Column(name="VSPD_LEAVEENC")
	private Integer vspdLeaveenc ;

	@Column(name="VSPD_LIC")
	private Integer vspdLic ;

	@Column(name="VSPD_LTA")
	private Integer vspdLta ;

	@Column(name="VSPD_LUNCHDED")
	private Integer vspdLunchded ;

	@Column(name="VSPD_LWF")
	private Integer vspdLwf ;

	@Column(name="VSPD_MA")
	private Integer vspdMa ;

	@Column(name="VSPD_MEALA")
	private Integer vspdMeala ;

	@Column(name="VSPD_MEDICAL")
	private Integer vspdMedical ;

	@Column(name="VSPD_NETPAY")
	private Integer vspdNetpay ;

	@Column(name="VSPD_NHPAY")
	private Integer vspdNhpay ;

	@Column(name="VSPD_NOTICEPAY")
	private Integer vspdNoticepay ;

	@Column(name="VSPD_NOTPAYRECO")
	private Integer vspdNotpayreco ;

	@Column(name="VSPD_OA")
	private Integer vspdOa ;

	@Column(name="VSPD_OLOAN")
	private Integer vspdOloan ;

	@Column(name="VSPD_OT")
	private Integer vspdOt ;

	@Column(name="VSPD_OTHDED")
	private Integer vspdOthded ;

	@Column(name="VSPD_OTHEARN")
	private Integer vspdOthearn ;

	@Column(name="VSPD_OTHOURS")
	private Double vspdOthours ;

	@Column(name="VSPD_OTHRSLASTMTH")
	private Double vspdOthrslastmth ;

	@Column(name="VSPD_OTHRSPRVLASTMTH")
	private Double vspdOthrsprvlastmth ;

	@Column(name="VSPD_PAYDATE")
	private LocalDate vspdPaydate ;

	@Column(name="VSPD_PAYMONTH")
	private String vspdPaymonth ;

	@Column(name="VSPD_PAYSTATUS")
	private String vspdPaystatus ;

	@Column(name="VSPD_PF")
	private Integer vspdPf ;

	@Column(name="VSPD_PFWAGES")
	private Integer vspdPfwages ;

	@Column(name="VSPD_PT")
	private Integer vspdPt ;

	@Column(name="VSPD_REG_SETTLE")
	private String vspdReg_Settle ;

	@Column(name="VSPD_SALARYGRPCODE")
	private String vspdSalarygrpcode ;

	@Column(name="VSPD_SALARYGRPNAME")
	private String vspdSalarygrpname ;

	@Column(name="VSPD_SALARYTYPE")
	private String vspdSalarytype ;

	@Column(name="VSPD_SLOAN")
	private Integer vspdSloan ;

	@Column(name="VSPD_SLOANINT")
	private Integer vspdSloanint ;

	@Column(name="VSPD_SOCA")
	private Integer vspdSoca ;

	@Column(name="VSPD_SOCCONTRI")
	private Integer vspdSoccontri ;

	@Column(name="VSPD_SOCCONTRIW")
	private Integer vspdSoccontriw ;

	@Column(name="VSPD_SOCRETCONT")
	private Integer vspdSocretcont ;

	@Column(name="VSPD_SPA")
	private Integer vspdSpa ;

	@Column(name="VSPD_SPIRITSERV")
	private Integer vspdSpiritserv ;

	@Column(name="VSPD_SRVA")
	private Integer vspdSrva ;

	@Column(name="VSPD_SRVCHG")
	private Integer vspdSrvchg ;

	@Column(name="VSPD_SRVCHGDED")
	private Integer vspdSrvchgded ;

	@Column(name="VSPD_STIPEND")
	private Integer vspdStipend ;

	@Column(name="VSPD_SURCHG")
	private Integer vspdSurchg ;

	@Column(name="VSPD_TA")
	private Integer vspdTa ;

	@Column(name="VSPD_TATA")
	private Integer vspdTata ;

	@Column(name="VSPD_TELA")
	private Integer vspdTela ;

	@Column(name="VSPD_TODATE")
	private LocalDate vspdTodate ;

	@Column(name="VSPD_TRANSPDED")
	private Integer vspdTranspded ;

	@Column(name="VSPD_UF")
	private Integer vspdUf ;

	@Column(name="VSPD_UNIFORM")
	private Integer vspdUniform ;

	@Column(name="VSPD_VPF")
	private Integer vspdVpf ;


}
