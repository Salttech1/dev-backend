package kraheja.commons.bean.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestBean {
	
	@NotBlank(message = "Username cannot be empty")
	@JsonProperty(value ="username")
	private String username;
	
	@NotBlank(message = "Password cannot be empty")
	@JsonProperty(value ="password")
	private String password;
}
