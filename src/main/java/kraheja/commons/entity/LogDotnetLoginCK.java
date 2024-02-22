package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDotnetLoginCK implements Serializable{
	
	private Integer sessionId;

	private String userId;
	
	private LocalDateTime logonDay;
}
