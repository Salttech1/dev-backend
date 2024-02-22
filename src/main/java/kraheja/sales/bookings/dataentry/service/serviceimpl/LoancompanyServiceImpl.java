//package kraheja.sales.bookings.dataentry.service.serviceimpl;
//
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
//import kraheja.sales.bean.request.LoancompanyRequestBean;
//import kraheja.sales.bookings.dataentry.mappers.LoancompanyEntityPojoMapper;
//import kraheja.sales.bookings.dataentry.mappers.LoancompanyaddressEntityPojoMapper;
//import kraheja.sales.bookings.dataentry.service.LoancompanyService;
//import kraheja.sales.entity.Loancompany;
//import kraheja.sales.entity.Loancompanyaddress;
//import kraheja.sales.repository.LoancompanyRepository;
//import kraheja.sales.repository.LoancompanyaddressRepository;
//
//@Service
//@Transactional
//public class LoancompanyServiceImpl implements LoancompanyService {
//
//	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//
//	@Autowired
//	private LoancompanyRepository loancompanyRepository;
//
//	@Autowired
//	private EntityRepository entityRepository;
//	
//
//	@Autowired
//	private LoancompanyaddressRepository loancompanyaddressRepository;
//
//	@Override
//	public ResponseEntity<?> fetchLoancompanyByCode(String loancoycode, String brachcode) {
//		Loancompany loancompanyEntity = this.loancompanyRepository.findByLoancompanyCK_LcoyCode(loancoycode);
//		logger.info("LoancompanyEntity :: {}", loancompanyEntity);
//		if (Objects.nonNull(loancompanyEntity)) {
//
//			Loancompanyaddress loancompanyaddressEntity = loancompanyaddressRepository
//					.findByLoancompanyaddressCK_LcaLoancoycodeAndLoancompanyaddressCK_LcaLoanbranchcode(loancoycode,
//							brachcode);
//
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
//					.data(LoancompanyEntityPojoMapper.fetchLoancompanyEntityPojoMapper
//							.apply(new Object[] { loancompanyEntity, loancompanyaddressEntity }))
//					.build());
//
//		}
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
//	}
//
//    @SuppressWarnings("unchecked")
//	@Override
//	public ResponseEntity<?> addLoancompany(LoancompanyRequestBean loancompanyRequestBean) {
//		// TODO Auto-generated method stub
//    	Loancompany loancompanyEntity = this.loancompanyRepository.findByLoancompanyCK_LcoyCode(loancompanyRequestBean.getCode());
//		logger.info("LoancompanyEntity :: {}", loancompanyEntity);
//		if (Objects.isNull(loancompanyEntity)) {
//			
//			this.loancompanyRepository.save(LoancompanyEntityPojoMapper
//					.addLoancompanyPojoEntityMapper
//					.apply(loancompanyRequestBean));
//			
//			this.loancompanyaddressRepository.save(LoancompanyaddressEntityPojoMapper
//					.addLoancompanyaddressPojoEntityMapper
//					.apply(loancompanyRequestBean.getLoancompanyaddressRequestBean()));
//			
//			return ResponseEntity.ok(
//					ServiceResponseBean.builder().status(Boolean.TRUE).message("Loan company Added Successfully").build());
//			}
//
//		return ResponseEntity
//				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Saved.").build());
//	}
//
//	@Override
//	public ResponseEntity<?> updateLoancompany(
//			kraheja.sales.bean.request.LoancompanyRequestBean loancompanyRequestBean) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<?> deleteLoancompany(String code) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<?> checkCodeExists(String code) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//}
