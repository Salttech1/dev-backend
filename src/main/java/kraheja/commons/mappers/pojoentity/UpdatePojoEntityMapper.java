package kraheja.commons.mappers.pojoentity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.ReportMaster;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface UpdatePojoEntityMapper {

	final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static BiFunction<ReportMaster, ReportMasterRequestBean, ReportMaster> updateReportPojoEntityMapping = (reportEntity, reportRequestBean) -> {
		reportEntity.setReportType(Objects.nonNull(reportRequestBean.getReportType()) ? reportRequestBean.getReportType() : reportEntity.getReportType());
		reportEntity.setReportMetaData(Objects.nonNull(reportRequestBean.getReportMetaData()) ? reportRequestBean.getReportMetaData() : reportEntity.getReportMetaData());
		reportEntity.setReportType(Objects.nonNull(reportRequestBean.getReportType()) ? reportRequestBean.getReportType() : reportEntity.getReportType());
		reportEntity.setRptPath(Objects.nonNull(reportRequestBean.getRptPath()) ? reportRequestBean.getRptPath() : reportEntity.getRptPath());
		return reportEntity;
	};

	public static BiFunction<Actranh, ActranhBean, Actranh> updateActranhEntityPojoMapper = (actranhEntity, actranhRequestBean) -> {
		actranhEntity.setActhBalancedyn(Objects.nonNull(actranhRequestBean.getBalancedyn()) ? actranhRequestBean.getBalancedyn().trim() : actranhEntity.getActhBalancedyn());
		actranhEntity.setActhProprietor(Objects.nonNull(actranhRequestBean.getProprietor()) ? actranhRequestBean.getProprietor().trim() : actranhEntity.getActhProprietor());
		actranhEntity.setActhBankcode(Objects.nonNull(actranhRequestBean.getBankcode()) ? actranhRequestBean.getBankcode().trim() : actranhEntity.getActhBankcode());
		actranhEntity.setActhBbookyn(Objects.nonNull(actranhRequestBean.getBbookyn()) ? actranhRequestBean.getBbookyn().trim() : actranhEntity.getActhBbookyn());
		actranhEntity.setActhCbookyn(Objects.nonNull(actranhRequestBean.getCbookyn()) ? actranhRequestBean.getCbookyn().trim() : actranhEntity.getActhCbookyn());
		actranhEntity.setActhClearacyn(Objects.nonNull(actranhRequestBean.getClearacyn()) ? actranhRequestBean.getClearacyn().trim() : actranhEntity.getActhClearacyn());
		actranhEntity.setActhClosingjvyn(Objects.nonNull(actranhRequestBean.getClosingjvyn()) ? actranhRequestBean.getClosingjvyn().trim() : actranhEntity.getActhClosingjvyn());
		actranhEntity.setActhCrepno(Objects.nonNull(actranhRequestBean.getCrepno()) ? actranhRequestBean.getCrepno() : actranhEntity.getActhCrepno());
		actranhEntity.setActhLedgcode(Objects.nonNull(actranhRequestBean.getLedgcode()) ? actranhRequestBean.getLedgcode().trim() : actranhEntity.getActhLedgcode());
		actranhEntity.setActhNarrative(Objects.nonNull(actranhRequestBean.getNarrative()) ? actranhRequestBean.getNarrative().trim() : actranhEntity.getActhNarrative());
		actranhEntity.setActhPartycode(Objects.nonNull(actranhRequestBean.getPartycode()) ? actranhRequestBean.getPartycode().trim() : actranhEntity.getActhPartycode());
		actranhEntity.setActhPartytype(Objects.nonNull(actranhRequestBean.getPartytype()) ? actranhRequestBean.getPartytype().trim() : actranhEntity.getActhPartytype());
		actranhEntity.setActhPostedyn(Objects.nonNull(actranhRequestBean.getPostedyn()) ? actranhRequestBean.getPostedyn().trim() : actranhEntity.getActhPostedyn());
		actranhEntity.setActhProvyn(Objects.nonNull(actranhRequestBean.getProvyn()) ? actranhRequestBean.getProvyn().trim() : actranhEntity.getActhProvyn());
		actranhEntity.setActhReverseyn(Objects.nonNull(actranhRequestBean.getReverseyn()) ? actranhRequestBean.getReverseyn().trim() : actranhEntity.getActhReverseyn());
		actranhEntity.setActhSite(GenericAuditContextHolder.getContext().getSite());
		actranhEntity.setActhTranamt(Objects.nonNull(actranhRequestBean.getTranamt()) ? actranhRequestBean.getTranamt() : actranhEntity.getActhTranamt());
		actranhEntity.setActhUserid(GenericAuditContextHolder.getContext().getUserid());
		actranhEntity.setActhToday(LocalDateTime.now());
		actranhEntity.setActhVounum(Objects.nonNull(actranhRequestBean.getVounum()) ? actranhRequestBean.getVounum().trim() : actranhEntity.getActhVounum());
		actranhEntity.setActhVoudate(Objects.nonNull(actranhRequestBean.getVoudate()) ? LocalDate.parse(actranhRequestBean.getVoudate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actranhEntity.getActhVoudate());


		return actranhEntity;
	};

}