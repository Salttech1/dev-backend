package kraheja.commons.service.impl;

import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kraheja.commons.entity.Passwd;
import kraheja.commons.repository.PasswdRepository;

@Service("userDetailsService")
@Transactional
public class AuthenticationService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final PasswdRepository passwdRepository;
    
    private final Integer passwordExpiryDate;

    @Autowired
    public AuthenticationService(PasswdRepository passwdRepository, @Value("${password.expiry}") Integer passwordExpiryDate) {
        this.passwdRepository = passwdRepository;
        this.passwordExpiryDate = passwordExpiryDate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Passwd passwdEntity = this.passwdRepository.findByAngularUserIgnoreCase(username);
    	if (Objects.isNull(passwdEntity)) {
            throw new UsernameNotFoundException(username);
        }
    	UserDetails user = User.withUsername(passwdEntity.getAngularUser()).password(passwdEntity.getAngularPassword()).authorities("USER").build();
        return user;

    	//
//        Optional<Users> user = userRepository.findByUsername(username);
//        if (!user.isPresent()) {
//            throw new UsernameNotFoundException("User does not exist");
//        }

//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//        Timestamp updatedPasswordTimestamp = passwdEntity.getPasswordUpdate();
//        
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(updatedPasswordTimestamp);
//        cal.add(Calendar.DAY_OF_WEEK, passwordExpiryDate);
//        updatedPasswordTimestamp.setTime(cal.getTime().getTime());
//        if(currentTimestamp.after(updatedPasswordTimestamp)) {
//        	throw new AccountExpiredException("Account password is expired");
//        }
        
//        return new org.springframework.security.core.userdetails.User(
//                user.get().getUsername(), user.get().getPassword(), user.get().isEnabled(),
//                user.get().isAccountNonExpired(), user.get().isCredentialsNonExpired(),
//                user.get().isAccountNonLocked(), getAuthorities(user.get().getRoles()));
    }

}