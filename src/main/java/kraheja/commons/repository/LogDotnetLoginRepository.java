package kraheja.commons.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.bean.MaxLogEntry;
import kraheja.commons.entity.LogDotnetLogin;
import kraheja.commons.entity.LogDotnetLoginCK;

@Repository
public interface LogDotnetLoginRepository extends JpaRepository<LogDotnetLogin, LogDotnetLoginCK>{

	@Query("select new kraheja.commons.bean.MaxLogEntry(max(e.logDotnetLoginCK.logonDay), e.logoffDay) from LogDotnetLogin e where e.logDotnetLoginCK.userId=:userId and e.logoffDay is null group by e.logoffDay")
	MaxLogEntry findMaxLogonDayByUserId(String userId);
	
	LogDotnetLogin findByLogDotnetLoginCK_UserIdAndLogDotnetLoginCK_LogonDay(String username, LocalDateTime logonday);
}
