package kraheja.commons.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public ResponseEntity<?> fetchByClass(String clazz) {
		List<Tuple> tuplesList = this.entityRepository.findIdAndNameByEntClass(clazz);

		if(CollectionUtils.isNotEmpty(tuplesList)) {
			List<EntityBean> entityBeanList = tuplesList.stream().map(t -> {return new EntityBean(
					t.get(0, String.class),
					t.get(1, String.class));
			}).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(entityBeanList).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please enter correct entity class value").build());
	}

	@Override
	public ResponseEntity<?> fetchNum1ByClassAndId(String clazz, String id) {
		Double num1 = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId(clazz, id);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(num1).build());
	}
}
