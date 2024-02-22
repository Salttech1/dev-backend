package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the LOG_DOTNET_LOGIN database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="LOG_DOTNET_LOGIN", indexes = {
		@Index(name = "LOG_DOTNET_LOGIN1", columnList = "userId ASC, sessionId ASC, logonDay ASC", unique = true),
})
public class LogDotnetLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public LogDotnetLoginCK logDotnetLoginCK;
	
	@Column(name="ELAPSED_MINUTES")
	private Long elapsedMinutes;
	
	private String host;

	@Column(name="IP_ADDRESS")
	private String ipAddress;

//	@Temporal(TemporalType.DATE)
	@Column(name="LOGOFF_DAY")
	private LocalDateTime logoffDay;
}