package kraheja.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the ReportMaster database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasourceMaster extends MetaData<String> {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique = true)
    private String name;
    
    @Column
    private String dbUrl;
    
    @Column
    private String dbUsername;
    
    @Column
    private String dbPassword;

    @Column
    private String dbDriver;
}