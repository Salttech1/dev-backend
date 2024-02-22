package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.math.BigInteger;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.sales.bean.request.LoanhistoryRequestBean;
import kraheja.sales.entity.Loanhistory;
import kraheja.sales.bookings.dataentry.service.LoanhistoryService;
import kraheja.sales.bookings.dataentry.mappers.FlatcharEntityPojoMapper;
import kraheja.sales.bookings.dataentry.mappers.LoanhistoryEntityPojoMapper;
import kraheja.sales.repository.LoanhistoryRepository;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;

@Service
@Transactional
public class LoanhistoryServiceImpl implements LoanhistoryService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final LocalDateTime NULL = null;

	@Autowired
	private LoanhistoryRepository loanhistoryRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> findByLoanhistoryCK_LhistOwnerid( String ownerid) {
		
		List<Loanhistory> loanhistoryEntity = this.loanhistoryRepository
				.findByLoanOwnerid(ownerid);
		
		logger.info("LoanhistoryEntity :: {}", loanhistoryEntity);
		
		if (Objects.nonNull(loanhistoryEntity)) {

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
					LoanhistoryEntityPojoMapper.fetchLoanhistoryEntityPojoMapper
					.apply(loanhistoryEntity))
					.build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}
	
	@Override
	public ResponseEntity<?> fetchLoanhistoryByLoancoAndLoannumAndLoanclosedateAndOwnerid(String loanco, String loannum,
			LocalDateTime loanclosedate, String ownerid) {

//		List<Loanhistory> loanhistoryEntity = this.loanhistoryRepository
//				.findByLoanhistoryCK_LhistLoancoAndLoanhistoryCK_LhistLoannumAndLoanhistoryCK_LhistLoanclosedateAndLoanhistoryCK_LhistOwnerid(
//						loanco, loannum, loanclosedate, ownerid);
		
		//findByLoanhistoryCK_LhistOwnerid
		
		List<Loanhistory> loanhistoryEntity = this.loanhistoryRepository
				.findByLoanOwnerid(ownerid);
				
		
		logger.info("LoanhistoryEntity :: {}", loanhistoryEntity);
		
		if (Objects.nonNull(loanhistoryEntity)) {

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
					LoanhistoryEntityPojoMapper.fetchLoanhistoryEntityPojoMapper
					.apply(loanhistoryEntity))
					.build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> addLoanhistory(List<LoanhistoryRequestBean> loanhistoryRequestBean) {
		// TODO Auto-generated method stub
		this.loanhistoryRepository
		.saveAll(LoanhistoryEntityPojoMapper.addLoanhistoryPojoEntityMapper
				.apply(loanhistoryRequestBean));
	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("Loan Histry Data Added Successfully").build());
		
		
	}

	@Override
public ResponseEntity<?> updateLoanhistory(List<LoanhistoryRequestBean> loanhistoryRequestBeanList) {
		
		List<Loanhistory> loanhistoryEntityList=new ArrayList<>();
		
		if(Objects.nonNull(loanhistoryRequestBeanList)) {
			loanhistoryRequestBeanList.stream().map(loanhistoryRequestBean->{
				if(loanhistoryRequestBean.getIsUpdate())
				{
					deleteLoanhistory(
							loanhistoryRequestBean.getLoanco(),
							loanhistoryRequestBean.getLoannum(),
							NULL,
							loanhistoryRequestBean.getOwnerid()
							);
					
				}
				else
				{
					Loanhistory loanhistoryEntity = this.loanhistoryRepository
							.findByLoanhistoryCK_LhistLoancoAndLoanhistoryCK_LhistLoannumAndLoanhistoryCK_LhistLoanclosedateAndLoanhistoryCK_LhistOwnerid(
									loanhistoryRequestBean.getLoanco(),
									loanhistoryRequestBean.getLoannum(),
									NULL,
									loanhistoryRequestBean.getOwnerid()
									);
						
						if(Objects.nonNull(loanhistoryEntity )) {
							loanhistoryEntityList.add(LoanhistoryEntityPojoMapper
									.updateLoanhistoryEntityPojoMapper.apply(loanhistoryEntity, loanhistoryRequestBean));
									
						}
						else
							{
							loanhistoryEntityList.addAll(LoanhistoryEntityPojoMapper
									.addLoanhistoryPojoEntityMapper.apply(Arrays.asList(loanhistoryRequestBean)));
															
							}
				}
				
   				return loanhistoryRequestBean ;
			}).collect(Collectors.toList());
		}
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());

	}
	
	@Override
	public ResponseEntity<?> deleteLoanhistory(String loanco, String loannum, LocalDateTime loanclosedate,
			String ownerid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkLoancoAndLoannumAndLoanclosedateAndOwneridExists(String loanco, String loannum,
			LocalDateTime loanclosedate, String ownerid) {
		// TODO Auto-generated method stub
		return null;
	}
}
