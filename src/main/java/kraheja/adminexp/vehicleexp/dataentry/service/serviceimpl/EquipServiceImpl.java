package kraheja.adminexp.vehicleexp.dataentry.service.serviceimpl;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Arrays;
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
import kraheja.commons.utils.CommonConstraints;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.EquipRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.service.EquipService;
import kraheja.adminexp.vehicleexp.dataentry.entity.Equip;
import kraheja.adminexp.vehicleexp.dataentry.mappers.EquipEntityPojoMapper;
import kraheja.adminexp.vehicleexp.dataentry.repository.EquipRepository;

@Service
@Transactional
public class EquipServiceImpl implements EquipService {

	private static final Logger logger = LoggerFactory.getLogger(EquipServiceImpl.class);

	@Autowired
	private EquipRepository equipRepository;

	@Autowired
	private  EntityRepository entityRepository;
	@Override
	public ResponseEntity<?> fetchEquipByEqptypeAndEqpnum(String  eqptype, String  eqpnum) {
		Equip equipEntity = this.equipRepository.findByEquipCK_EqpEqptypeAndEquipCK_EqpEqpnum(eqptype, eqpnum);
		logger.info("EquipEntity :: {}", equipEntity);
		if (equipEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(EquipEntityPojoMapper.fetchEquipEntityPojoMapper
									.apply(new Object[] { Arrays.asList(equipEntity) })
									.get(BigInteger.ZERO.intValue()))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Equip").build());
	}

	@Override
	public ResponseEntity<?> addEquip(EquipRequestBean equipRequestBean) throws ParseException {
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String eqptype = equipRequestBean.getEqptype();
		String eqpnum = equipRequestBean.getEqpnum();

		this.equipRepository.save(EquipEntityPojoMapper.addEquipEntityPojoMapper.apply(new Object[] {equipRequestBean,siteFromDBEntity,eqptype,eqpnum}));
		GenericAuditContextHolder.getContext().setTransactionNo(equipRequestBean.getEqpnum());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Vehicle No: " + eqpnum + "  Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateEquip(EquipRequestBean equipRequestBean) throws ParseException {
		Equip equipEntity = this.equipRepository.findByEquipCK_EqpEqptypeAndEquipCK_EqpEqpnum(equipRequestBean.getEqptype() , equipRequestBean.getEqpnum() ) ; 

		if(Objects.nonNull(equipEntity)) {

//			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

			if(Objects.nonNull(equipRequestBean))
				this.equipRepository.save(EquipEntityPojoMapper.updateEquipEntityPojoMapper.apply(equipEntity, equipRequestBean));
				GenericAuditContextHolder.getContext().setTransactionNo(equipRequestBean.getEqpnum());
				GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> deleteEquip(String  eqptype, String  eqpnum) throws ParseException {
		Equip equipEntity = this.equipRepository.findByEquipCK_EqpEqptypeAndEquipCK_EqpEqpnum(eqptype , eqpnum ) ; 

		if(Objects.nonNull(equipEntity)) {

			this.equipRepository.delete(equipEntity);
			GenericAuditContextHolder.getContext().setTransactionNo(equipEntity.getEquipCK().getEqpEqpnum());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
		} 
		else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> checkEqptypeAndEqpnumExists(String eqptype, String eqpnum) {
		Equip equipEntity = this.equipRepository.findByEquipCK_EqpEqptypeAndEquipCK_EqpEqpnum(eqptype , eqpnum ) ; 
		logger.info("EquipEntity :: {}", equipEntity);
		if(equipEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Vehicle number already exist").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("No Data Found").build());
	}


}

