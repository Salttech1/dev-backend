package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.request.UserRequestBean;

public interface PasswdService {
	ResponseEntity<?> createNewUserPassword(UserRequestBean userRequestBean);

}
