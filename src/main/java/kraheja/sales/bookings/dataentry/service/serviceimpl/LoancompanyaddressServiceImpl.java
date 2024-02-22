//package kraheja.sales.bookings.dataentry.service.serviceimpl;

//import java.lang.invoke.MethodHandles;
//import java.util.Objects;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import kraheja.commons.bean.response.ServiceResponseBean;
//import kraheja.commons.repository.EntityRepository;
//import kraheja.sales.bean.request.LoancompanyaddressRequestBean;
//import kraheja.sales.bookings.dataentry.mappers.LoancompanyaddressEntityPojoMapper;
//import kraheja.sales.bookings.dataentry.service.LoancompanyaddressService;
//import kraheja.sales.entity.Loancompanyaddress;
//import kraheja.sales.repository.LoancompanyaddressRepository;
//
//@Service
//@Transactional
//public class LoancompanyaddressServiceImpl implements LoancompanyaddressService {
//
//	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//
//	@Autowired
//	private LoancompanyaddressRepository loancompanyaddressRepository;
//
//	@Autowired
//	private EntityRepository entityRepository;

//	@Override
//	public ResponseEntity<?> fetchLoancompanyaddressByLoancoycodeAndLoanbranchcode(String loancoycode,
//			String loanbranchcode) {
//		Loancompanyaddress loancompanyaddressEntity = this.loancompanyaddressRepository
//				.findByLoancompanyaddressCK_LcaLoancoycodeAndLoancompanyaddressCK_LcaLoanbranchcode(loancoycode,
//						loanbranchcode);
//		logger.info("LoancompanyaddressEntity :: {}", loancompanyaddressEntity);
//		if (Objects.nonNull(loancompanyaddressEntity)) {
//
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
//					.data(LoancompanyaddressEntityPojoMapper.fetchLoancompanyaddressEntityPojoMapper
//							.apply(new Object[] { loancompanyaddressEntity }))
//					.build());
//
//		}
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
//	}

//	@Override
//	public ResponseEntity<?> addLoancompanyaddress(LoancompanyaddressRequestBean loancompanyaddressRequestBean) {
//		// TODO Auto-generated method stub
//	
//		this.loancompanyaddressRepository
//		.save(LoancompanyaddressEntityPojoMapper.addLoancompanyaddressPojoEntityMapper
//				.apply(loancompanyaddressRequestBean));
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
//				.message("Loan company Address Added Sucessfully").build());
//
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public ResponseEntity<?> addLoancompanyaddress(LoancompanyaddressRequestBean loancompanyaddressRequestBean) {
//		String ser = "";
////if(loancompanyaddressRequestBean.getIsUpdate()) {
////
//		Loancompanyaddress loancompanyaddressEntity = this.loancompanyaddressRepository
//				.findByLoancompanyaddressCK_LcaLoancoycodeAndLoancompanyaddressCK_LcaLoanbranchcode(
//						loancompanyaddressRequestBean.getLoancoycode(),
//						loancompanyaddressRequestBean.getLoanbranchcode());
//
//		if (Objects.nonNull(loancompanyaddressEntity)) {
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
//					.data(LoancompanyaddressEntityPojoMapper.addLoancompanyaddressPojoEntityMapper
//							.apply(loancompanyaddressRequestBean)
//							
//							.apply(new Object[] { loancompanyaddressEntity }))
//					.build());		
//		}
////
//this.loancompanyaddressRepository.save(LoancompanyaddressEntityPojoMapper.updateLoancompanyaddressPojoEntityMapper.apply(loancompanyaddressEntity, loancompanyaddressRequestBean));
//
//
//} 
//		return null;
//	}

//	@Override
//	public ResponseEntity<?> checkLoancoycodeAndLoanbranchcodeExists(String loancoycode, String loanbranchcode) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
