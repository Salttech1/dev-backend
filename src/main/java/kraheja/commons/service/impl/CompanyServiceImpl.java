package kraheja.commons.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ProprietorResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Proprietor;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.ProprietorRepository;
import kraheja.commons.service.CompanyService;
import kraheja.commons.utils.CommonUtils;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ProprietorRepository proprietorRepository;

	@Override
	public ResponseEntity<?> fecthCoyCloseDate(String companyCode) {
		LocalDateTime coyCloseDate = this.companyRepository.findCloseDateByCompanyCK_CoyCode(companyCode.trim());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(CommonUtils.INSTANCE.localDateTimeDDMMYYYYFormatter(coyCloseDate)).build());
	}

	@Override
	public ResponseEntity<?> fetchPropByCoyAndClosedate(String companyCode, String closedate) {
		String companyProp;
		try {
			companyProp = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(companyCode, new SimpleDateFormat("dd/MM/yyyy").parse(closedate) );
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(companyProp).build());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("No data found for company.").build());
	}

	@Override
	public ResponseEntity<?> fetchPropDetailsByCoycode(String companyCode) {
		String companyProp;
		companyProp = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(companyCode.trim(), CommonUtils.INSTANCE.closeDate());
		if(StringUtils.isNotBlank(companyProp)) {
			Proprietor proprietorEntity = this.proprietorRepository.findByProprietorCK_PropCode(companyProp);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(ProprietorResponseBean.builder().propCode(proprietorEntity.getProprietorCK().getPropCode().trim()).propName(proprietorEntity.getPropName().trim()).build()).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found for company.").build());

	}
}