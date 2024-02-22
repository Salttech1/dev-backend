package kraheja.payroll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "v_salarycoy")
@Immutable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class v_salarycoy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id; // The row number!
	
	@Column(name="company_code")
	private String coyCode;
	
	@Column(name="company_name")
	private String coyName;
}
