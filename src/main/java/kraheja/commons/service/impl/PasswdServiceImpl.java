package kraheja.commons.service.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.request.UserRequestBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Passwd;
import kraheja.commons.repository.PasswdRepository;
import kraheja.commons.service.PasswdService;

@Service
public class PasswdServiceImpl implements PasswdService {
	private static final Logger logger = LoggerFactory.getLogger(PasswdServiceImpl.class);


	@Autowired
	private PasswdRepository passwdRepository;

	@Override
	public ResponseEntity<?> createNewUserPassword(UserRequestBean userRequestBean) {
		Passwd passwdEntity = this.passwdRepository.findByAngularUserIgnoreCase(userRequestBean.getUsername().trim());
		logger.info("Passwd :: {}", passwdEntity);

		if(Objects.nonNull(passwdEntity)) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			passwdEntity.setAngularPassword(passwordEncoder.encode(userRequestBean.getPassword()));
			this.passwdRepository.save(passwdEntity);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Your new password is set.").build());		
			}
	else
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please check your username.").build());		}
}