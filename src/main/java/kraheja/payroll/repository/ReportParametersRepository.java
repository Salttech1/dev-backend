package kraheja.payroll.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.payroll.entity.Paymast;
import kraheja.payroll.entity.PaymastCK;

@Repository
public interface ReportParametersRepository extends JpaRepository<Paymast, PaymastCK>{
	@Query(value = "select \r\n"
			+ "(select min(company_code) from v_salarycoy) as fromcoy,\r\n"
			+ "(select max(company_code) from v_salarycoy) as tocoy,\r\n"
			+ "(SELECT min(pmst_mastcode) FROM 	 PAYMAST WHERE  PMST_MASTTYPE = '3') as fromdept,\r\n"
			+ "(SELECT max(pmst_mastcode) FROM 	 PAYMAST WHERE  PMST_MASTTYPE = '3') as todept,\r\n"
			+ "(select min(ent_id) from entity where ent_class = 'EMPTY' and ent_id <>'00000') as fromEmptype,\r\n"
			+ "(select max(ent_id) from entity where ent_class = 'EMPTY' and ent_id <>'00000') as toEmptype,\r\n"
			+ "(select min(ent_name) from entity where ent_class = 'PAYRL' and Ent_id='PAYMO') as fromPayType,\r\n"
			+ "(select max(ent_name) from entity where ent_class = 'PAYRL' and Ent_id='PAYMO') as toPayType,\r\n"
			+ "(select min(eper_empcode) from emppersonal) as fromEmpcode,\r\n"
			+ "(select max(eper_empcode) from emppersonal) as toEmpcode,\r\n"
			+ "(SELECT max(cmst_paymonth) FROM coymthsaltypes) as frompaymonth,\r\n"
			+ "(SELECT max(cmst_paymonth) FROM coymthsaltypes) as topaymonth,\r\n"
			+ "(SELECT min(pmst_mastcode) FROM 	 PAYMAST WHERE  PMST_MASTTYPE = '9') as fromLocation,\r\n"
			+ "(SELECT max(pmst_mastcode) FROM 	 PAYMAST WHERE  PMST_MASTTYPE = '9') as toLocation,\r\n"
			+ "(SELECT min(pmst_mastcode) FROM 	 PAYMAST WHERE  PMST_MASTTYPE = 'A') as fromWorksite,\r\n"
			+ "(SELECT max(pmst_mastcode) FROM 	 PAYMAST WHERE  PMST_MASTTYPE = 'A') as toWorksite,\r\n"
			+ "(select trim(ent_char1) from entity where ent_class = 'PAYRL' and ent_id = 'HOTEL') as hotelYN \r\n"
			+ "from dual", nativeQuery = true)
	Tuple GetReportParameters();
	
	@Query(value = "select 	ejin_gratuitypaymonth \r\n"
			+ "from 		empjobinfo \r\n"
			+ "where 	ejin_jobstatus = 'L' \r\n"
			+ "and 		(ejin_gratuitypaymonth <> '' or ejin_gratuitypaymonth is not NULL) \r\n"
			+ "and 		trim(ejin_empcode) = :empCode" , nativeQuery = true)
	String GetGratuityMonthForEmployee(String empCode);

	
	@Query(value = "select trim(ent_char1) as hotelYN from entity where ent_class = 'PAYRL' and ent_id = 'HOTEL' ", nativeQuery = true) 
	String GetHotelPropYN();

	
}
