package kraheja.sales.lessorrent.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.sales.bean.request.LunitdtlsRequestBean;
import kraheja.sales.entity.Lunitdtls;
import kraheja.sales.lessorrent.mappers.LunitdtlsEntityPojoMapper;
import kraheja.sales.lessorrent.service.LunitdtlsService;
import kraheja.sales.repository.LunitdtlsRepository;

@Service
@Transactional
public class LunitdtlsServiceImpl implements LunitdtlsService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private LunitdtlsRepository lunitdtlsRepository;

//	@Autowired
//	private EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchLunitdtlsByPropcodeAndUnitidAndUnitno(String propcode, String unitid, String unitno) {
		Lunitdtls lunitdtlsEntity = this.lunitdtlsRepository
				.findByLunitdtlsCK_LessorPropcodeAndLunitdtlsCK_LessorUnitidAndLunitdtlsCK_LessorUnitno(propcode,
						unitid, unitno);
		logger.info("LunitdtlsEntity :: {}", lunitdtlsEntity);
		if (Objects.nonNull(lunitdtlsEntity)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
					LunitdtlsEntityPojoMapper.fetchLunitdtlsEntityPojoMapper.apply(new Object[] { lunitdtlsEntity }))
					.build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@Override
	public ResponseEntity<?> addLunitdtls(LunitdtlsRequestBean lunitdtlsRequestBean) {
		String ser = "";
		String propcode = "";
		String unitid = "";
		String unitno = "";

		if (lunitdtlsRequestBean.getIsUpdate()) {
			Lunitdtls lunitdtlsEntity = this.lunitdtlsRepository
					.findByLunitdtlsCK_LessorPropcodeAndLunitdtlsCK_LessorUnitidAndLunitdtlsCK_LessorUnitno(propcode,
							unitid, unitno);
			this.lunitdtlsRepository.save(LunitdtlsEntityPojoMapper.updateLunitdtlsEntityPojoMapper
					.apply(lunitdtlsEntity, lunitdtlsRequestBean));

		} else {

			ser = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "#NSER",
					GenericAuditContextHolder.getContext().getSite());
			this.lunitdtlsRepository
					.save(LunitdtlsEntityPojoMapper.addLunitdtlsPojoEntityMapper.apply(lunitdtlsRequestBean));

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.message("Transaction saved successfully. Entry No. " + ser).data(ser).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Transaction failed " + ser).data(ser).build());
	}

	@Override
	public ResponseEntity<?> updateLunitdtls(LunitdtlsRequestBean lunitdtlsRequestBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteLunitdtls(String propcode, String unitid, String unitno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkPropcodeAndUnitidAndUnitnoExists(String propcode, String unitid, String unitno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> findCountPropcode(String propcode) {
		// TODO Auto-generated method stub
		return null;
	}
}
