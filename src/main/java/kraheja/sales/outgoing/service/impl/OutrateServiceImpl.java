package kraheja.sales.outgoing.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.ValueContainer;
import kraheja.sales.bean.request.OutrateRequestBean;
import kraheja.sales.entity.Outrate;
import kraheja.sales.outgoing.mappers.OutrateEntityPojoMapper;
import kraheja.sales.outgoing.service.OutrateService;
import kraheja.sales.repository.OutrateRepository;

@Service
@Transactional
public class OutrateServiceImpl implements OutrateService {

	private static final Logger logger = LoggerFactory.getLogger(OutrateServiceImpl.class);

	@Autowired
	private OutrateRepository outrateRepository;

	@Autowired
	private EntityRepository entityRepository;
	
	@Override
	public ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWingAndStartdate(String bldgcode, String flatnum,
			String wing, String startdate) {
		Outrate outrateEntity = this.outrateRepository
				.findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWingAndOutrateCK_OutrStartdate(
						bldgcode, flatnum, wing, startdate);
		logger.info("OutrateEntity :: {}", outrateEntity);
		if (outrateEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(OutrateEntityPojoMapper.fetchOutrateEntityPojoMapper
							.apply(new Object[] { Arrays.asList(outrateEntity) }).get(BigInteger.ZERO.intValue()))
					.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("No record found for your selections in Outrate").build());
	}

	// @Override
	// public ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWing(String
	// bldgcode, String flatnum, String wing) {
	// Outrate outrateEntity =
	// this.outrateRepository.findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWing(bldgcode,
	// flatnum, wing);
	// logger.info("OutrateEntity :: {}", outrateEntity);
	// if (outrateEntity != null) {
	// return ResponseEntity.ok(ServiceResponseBean.builder()
	// .data(OutrateEntityPojoMapper.fetchOutrateEntityPojoMapper
	// .apply(new Object[] { Arrays.asList(outrateEntity) })
	// .get(BigInteger.ZERO.intValue()))
	// .status(Boolean.TRUE).build());
	// }
	// return
	// ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No
	// record found for your selections in Outrate").build());
	// }

	@Override
	public ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWing(String bldgcode, String flatnum, String wing) {
		List<Outrate> outrateEntity = this.outrateRepository
				.findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWing(bldgcode, flatnum, wing);
		logger.info("OutrateEntity :: {}", outrateEntity);
		if (outrateEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(OutrateEntityPojoMapper.fetchOutrateEntityPojoMapper.apply(new Object[] { outrateEntity }))
					.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("No record found for your selections in Outrate").build());
	}

//	@Override
//	public ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnum(String  bldgcode, String  flatnum) {
//		List<Outrate> outrateEntity = this.outrateRepository.findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnum(bldgcode, flatnum);
//		logger.info("OutrateEntity :: {}", outrateEntity);
//		if (outrateEntity != null) {
//			return ResponseEntity.ok(ServiceResponseBean.builder()
//					.data(OutrateEntityPojoMapper.fetchOutrateEntityPojoMapper
//							.apply(new Object[] { outrateEntity })
//							)
//					.status(Boolean.TRUE).build());
//		}
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Outrate").build());
//	}

//	@Override
//	public ResponseEntity<?> addOutrate(List <OutrateRequestBean> outrateRequestBean) throws ParseException {
//		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
//
//		this.outrateRepository.saveAll(OutrateEntityPojoMapper.addOutrateEntityPojoMapper.apply(outrateRequestBean));
//
//		//this.outrateRepository.save(OutrateEntityPojoMapper.addOutrateEntityPojoMapper.apply(new Object[] {Arrays.asList(outrateRequestBean),siteFromDBEntity,outrateRequestBean.getBldgcode(),outrateRequestBean.getFlatnum(),outrateRequestBean.getWing() }));
//
//		//		GenericAuditContextHolder.getContext().setTransactionNo(outrateRequestBean.getBldgcode() + outrateRequestBean.getFlatnum() + outrateRequestBean.getWing() + outrateRequestBean.getStartdate());
//		//		GenericAuditContextHolder.getContext().setAuditable(Boolean.FALSE);
//
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());
//	}

	@Override
	public ResponseEntity<?> updateOutrate(List<OutrateRequestBean> outrateRequestBeanList) throws ParseException {
		List<Outrate> outrateEntityList = new ArrayList<>();

		if (Objects.nonNull(outrateRequestBeanList)) {

			ValueContainer<String> auditTranser = new ValueContainer<>();
			outrateRequestBeanList.stream().map(outrateRequestBean -> {

				if (outrateRequestBean.getIsDelete()) {
					deleteOutrate(outrateRequestBean.getBldgcode().trim(), outrateRequestBean.getFlatnum().trim(),
							outrateRequestBean.getWing().trim(), outrateRequestBean.getStartdate().trim());
				} else {
					Outrate outrateEntity = this.outrateRepository
							.findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWingAndOutrateCK_OutrStartdate(
									outrateRequestBean.getBldgcode(), outrateRequestBean.getFlatnum(),
									outrateRequestBean.getWing(), outrateRequestBean.getStartdate());

					if (Objects.nonNull(outrateEntity)) {
						outrateEntityList.add(OutrateEntityPojoMapper.updateOutrateEntityPojoMapper.apply(outrateEntity,
								outrateRequestBean));
					} else {
						outrateEntityList.addAll(OutrateEntityPojoMapper.addOutrateEntityPojoMapper
								.apply(Arrays.asList(outrateRequestBean)));

					}
				}

				auditTranser.setValue(outrateRequestBean.getBldgcode().trim() + outrateRequestBean.getFlatnum().trim()
						+ outrateRequestBean.getWing().trim());
//
				return outrateRequestBean;
			}).collect(Collectors.toList());

			this.outrateRepository.saveAll(outrateEntityList);

			GenericAuditContextHolder.getContext().setTransactionNo(auditTranser.getValue());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> deleteOutrate(String bldgcode, String flatnum, String wing, String startdate) {
		Outrate outrateEntity = this.outrateRepository
				.findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWingAndOutrateCK_OutrStartdate(
						bldgcode, flatnum, wing, startdate);

		if (Objects.nonNull(outrateEntity)) {

			this.outrateRepository.delete(outrateEntity);
			// GenericAuditContextHolder.getContext().setTransactionNo(outrateEntity.getOutrateCK().getOutrBldgcode()
			// + outrateEntity.getOutrateCK().getOutrFlatnum() +
			// outrateEntity.getOutrateCK().getOutrWing() +
			// outrateEntity.getOutrateCK().getOutrStartdate());
			// GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
		} else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}
//
//
//	@Override
//	public ResponseEntity<?> checkBldgcodeAndFlatnumAndWingAndStartdateExists(String  bldgcode, String  flatnum, String  wing, String  startdate) {
//		return null;
//	}
}
