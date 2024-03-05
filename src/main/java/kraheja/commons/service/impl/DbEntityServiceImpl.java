package kraheja.commons.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.EntityBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.service.DbEntityService;

@Service
@Transactional
public class DbEntityServiceImpl implements DbEntityService {

	@Autowired
	private EntityRepository entityRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseEntity<?> fetchByClass(String clazz) {
		List<Tuple> tuplesList = this.entityRepository.findIdAndNameByEntClass(clazz);

		if (CollectionUtils.isNotEmpty(tuplesList)) {
			List<EntityBean> entityBeanList = tuplesList.stream().map(t -> {
				return new EntityBean(t.get(0, String.class), t.get(1, String.class));
			}).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(entityBeanList).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Please enter correct entity class value").build());
	}

	@Override
	public ResponseEntity<?> fetchNum1ByClassAndId(String clazz, String id) {
		Double num1 = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId(clazz, id);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(num1).build());
	}

	@Override
	public ResponseEntity<?> fetchChar1ByClassAndId(String clazz, String id, String extraWhereClause) { // 21.12.23 RS

		Query query = this.entityManager
				.createNativeQuery("select nvl(ent_char1,' ') AS char1 from entity where ent_class = '" + clazz
						+ "' AND ent_id = '" + id + "' " + extraWhereClause, Tuple.class);
		List<Tuple> bldgExclList = query.getResultList();
		String bldgCodeExcl = "";
		for (Tuple bldgCode : bldgExclList) {
			bldgCodeExcl = bldgCodeExcl + "'" + bldgCode.get("char1", String.class) + "',";
		}
		bldgCodeExcl = bldgCodeExcl.substring(0, bldgCodeExcl.length() - 1);
		if (CollectionUtils.isNotEmpty(bldgExclList)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(bldgCodeExcl).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Please enter correct entity class and id").build());
	}

}
