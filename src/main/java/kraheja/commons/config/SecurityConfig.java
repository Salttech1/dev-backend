package kraheja.commons.config;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;
import org.json.JSONObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import kraheja.commons.bean.MaxLogEntry;
import kraheja.commons.entity.LogDotnetLogin;
import kraheja.commons.entity.LogDotnetLoginCK;
import kraheja.commons.entity.Passwd;
import kraheja.commons.repository.LogDotnetLoginRepository;
import kraheja.commons.repository.PasswdRepository;
import kraheja.commons.service.impl.AuthenticationService;
import kraheja.commons.utils.CommonConstraints;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Value("${permitted.filters}")
	private String[] permittedFilters;

	@Value("${permitted.resources}")
	private String[] permittedResources;
	
	@Value("${cors.allowed.domains}")
	private String[] corsAllowedomains;
	
	@Value("${spring.datasource.name}")
	private String databaseName;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private LogDotnetLoginRepository logDotnetLoginRepository;

	@Autowired
	private PasswdRepository passwdRepository;

	@Resource
	private AuthenticationService authenticationService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.authorizeRequests().antMatchers(this.permittedFilters).permitAll().anyRequest().authenticated().and()
				.formLogin().successHandler(successHandler()).failureHandler(failureHandler()).and().logout()
				.addLogoutHandler(logoutHandler())
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
				.invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true).permitAll();
		httpSecurity.headers().frameOptions().sameOrigin();
		httpSecurity.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		return httpSecurity.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers(this.permittedResources);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(corsAllowedomains).allowCredentials(true)
						.allowedMethods("*");
			}
		};
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(authenticationService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// auth.authenticationProvider(authProvider());
	// }

	private AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, Authentication authentication)
					throws IOException, ServletException {

				MaxLogEntry maxDateRecord = logDotnetLoginRepository
						.findMaxLogonDayByUserId(authentication.getName().trim());
				logger.info("maxDateRecord  :: {}", maxDateRecord);

				if (Objects.nonNull(maxDateRecord)) {
					if (Objects.isNull(maxDateRecord.getLogoffDay())) {
						LogDotnetLogin logDotnetLoginEntity = logDotnetLoginRepository
								.findByLogDotnetLoginCK_UserIdAndLogDotnetLoginCK_LogonDay(
										authentication.getName().trim(), maxDateRecord.getLogonDay());
						logger.info("LogDotnetLogin :: {}", logDotnetLoginEntity);

						logDotnetLoginEntity.setElapsedMinutes(ChronoUnit.MINUTES.between(
								logDotnetLoginEntity.getLogDotnetLoginCK().getLogonDay(), LocalDateTime.now()));
						logDotnetLoginEntity.setLogoffDay(LocalDateTime.now());
						logDotnetLoginRepository.save(logDotnetLoginEntity);
					}
				}

			    String remoteAddress = httpServletRequest.getRemoteAddr();
			    String computerName;
			    
			    InetAddress inetAddress = InetAddress.getByName(remoteAddress);
		        System.out.println("inetAddress: " + inetAddress);
		        computerName = inetAddress.getHostName();
		        System.out.println("computer name: " + computerName);
				if (computerName.equalsIgnoreCase("localhost")) {
		            computerName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
		            System.out.println("INside IF of computer name: " + computerName);
		        } 
				computerName = StringUtils.isNotBlank(computerName) && computerName.trim().length() > 10 ? InetAddress.getLocalHost().getHostName() : computerName.trim();
				InetAddress localhost = InetAddress.getLocalHost();
				String ipaddress = httpServletRequest.getRemoteAddr().startsWith("0:0:0:0") ? localhost.getHostAddress().trim() : httpServletRequest.getRemoteAddr();
				logDotnetLoginRepository
						.save(LogDotnetLogin.builder().ipAddress(ipaddress)
								.host(computerName)
								.logDotnetLoginCK(LogDotnetLoginCK.builder().userId(authentication.getName().trim())
										.logonDay(LocalDateTime.now()).sessionId(BigInteger.ZERO.intValue()).build())
								.build());

				Passwd passwdEntity = passwdRepository.findByAngularUserIgnoreCase(authentication.getName().trim());
				logger.info("Passwd :: {}", passwdEntity);
				httpServletResponse.setContentType("application/json");
				JSONObject json = new JSONObject();
				try {
					json.put("startupReportName", passwdEntity.getStartupReport());
					json.put("userid", authentication.getName().trim());
					json.put("site", passwdEntity.getSite().trim());
					json.put("message", "Successfully logged in");
					json.put("databaseName", databaseName.trim());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				httpServletResponse.setContentType("application/json");
				httpServletResponse.getWriter().write(json.toString());
				httpServletResponse.setStatus(200);
			}
		};
	}

	private AuthenticationFailureHandler failureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, AuthenticationException exception)
					throws IOException, ServletException {

				httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
				Map<String, Object> data = new HashMap<>();
				data.put("timestamp", Calendar.getInstance().getTime());
				data.put("exception", exception.getMessage());
				httpServletResponse.getOutputStream().println(objectMapper.writeValueAsString(data));
			}
		};
	}

	private LogoutHandler logoutHandler() {
		return new LogoutHandler() {
			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) {
				try {
					MaxLogEntry maxDateRecord = logDotnetLoginRepository
							.findMaxLogonDayByUserId(authentication.getName().trim());
					logger.info("maxDateRecord  :: {}", maxDateRecord);
					if (Objects.nonNull(maxDateRecord)) {
						if (Objects.isNull(maxDateRecord.getLogoffDay())) {
							LogDotnetLogin logDotnetLoginEntity = logDotnetLoginRepository
									.findByLogDotnetLoginCK_UserIdAndLogDotnetLoginCK_LogonDay(
											authentication.getName().trim(), maxDateRecord.getLogonDay());

							logDotnetLoginEntity.setElapsedMinutes(ChronoUnit.MINUTES.between(
									logDotnetLoginEntity.getLogDotnetLoginCK().getLogonDay(), LocalDateTime.now()));
							logDotnetLoginEntity.setLogoffDay(LocalDateTime.now());
							logDotnetLoginRepository.save(logDotnetLoginEntity);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}
}