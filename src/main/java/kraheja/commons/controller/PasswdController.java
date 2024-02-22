package kraheja.commons.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.UserRequestBean;
import kraheja.commons.service.PasswdService;


@RestController
@RequestMapping("/passwd")
public class PasswdController {

    private final PasswdService passwdService;
    
    @Autowired
    public PasswdController(PasswdService passwdService) {
        this.passwdService = passwdService;
    }
	 @PostMapping("/register-user")
	    public ResponseEntity<?> createNewUserPassword(@RequestBody UserRequestBean userRequestBean) {
	    	if(Objects.nonNull(userRequestBean))
	    		return this.passwdService.createNewUserPassword(userRequestBean);
	    	return ResponseEntity.badRequest().build();
	    }
}
