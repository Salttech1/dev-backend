// Developed By  - 	vikram.p
// Developed on - 22-11-23
// Mode  - Data Entry
// Purpose - Lprop Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

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
import kraheja.constant.ApiResponseCode;
import kraheja.constant.Result;
import kraheja.payload.GenericResponse;
import kraheja.sales.bean.request.LpropRequestBean;
import kraheja.sales.entity.Lprop;
import kraheja.sales.lessorrent.mappers.LpropEntityPojoMapper;
import kraheja.sales.lessorrent.service.LpropService;
import kraheja.sales.repository.LpropRepository;
import kraheja.sales.repository.LunitdtlsRepository;
@Service
@Transactional
public class LpropServiceImpl implements LpropService {

private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

@Autowired private LpropRepository lpropRepository;
@Autowired private LunitdtlsRepository lunitdtlsRepository;
@Autowired private EntityRepository entityRepository;


@Override
public ResponseEntity<?> fetchLpropByPropcode(String  propcode) {
Lprop lpropEntity = this.lpropRepository.findByLpropCK_LessorPropcode(propcode);
logger.info("LpropEntity :: {}", lpropEntity);
if(Objects.nonNull(lpropEntity)) {
return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(LpropEntityPojoMapper.fetchLpropEntityPojoMapper.apply(new Object [] {lpropEntity})).build());
}
return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
}

@Override
public ResponseEntity<?> addLprop(LpropRequestBean lpropRequestBean) {
String propcode = "";

if(lpropRequestBean.getIsUpdate()) {
	propcode = lpropRequestBean.getPropcode();
	Lprop lpropEntity = this.lpropRepository
			.findByLpropCK_LessorPropcode(propcode);
this.lpropRepository.save(LpropEntityPojoMapper.updateLpropEntityPojoMapper.apply(lpropEntity, lpropRequestBean));
return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Property Updated successfully. Property Code : "+ propcode).data(propcode).build());
//return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Property saved successfully.").build());

} 
else {

	propcode = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "LPROP", GenericAuditContextHolder.getContext().getSite());
	lpropRequestBean.setPropcode(propcode);
	
	this.lpropRepository.save(LpropEntityPojoMapper.addLpropPojoEntityMapper.apply(lpropRequestBean));

return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Property added successfully. Property Code : "+ propcode).data(propcode).build());
//return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Property saved successfully.").build());
}
}
//
//@SuppressWarnings("unchecked")
//@Override
//public deleteLprop(String  propcode) {
//
////	Lprop lpropEntity = this.lpropRepository
////			.deleteByLpropCK_LessorPropcode(propcode);
////this.lpropRepository.delete(LpropEntityPojoMapper.deleteLpropEntityPojoMapper.apply(lpropEntity, lpropRequestBean));
////return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Property Deleted successfully. Property Code : "+ propcode).data(propcode).build());
////return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Property saved successfully.").build());
//
//}

@Override
	public GenericResponse deleteLprop(String propcode) {
	Integer lunitdCount = lunitdtlsRepository.findCountPropcode(propcode);
	System.out.println("lunitdCount : "+ lunitdCount);
	
	if (lunitdCount < 1 ) {
		lpropRepository.deleteByLpropCK_LessorPropcode(propcode);
		return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message("Success").build();
	}else {
		return GenericResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED).message("Can't delete as " + lunitdCount + " units are entered for Property Code : ").build();
	}
}

}

