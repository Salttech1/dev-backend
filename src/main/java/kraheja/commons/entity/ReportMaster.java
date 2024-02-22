package kraheja.commons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
public class ReportMaster extends MetaData<String> {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique = true)
    private String name;
    
    @Column(length = 4000)
    private String reportMetaData;
    
    @Column(length = 4000)
    private String condition;
    
    @Column
    private String rptPath;
    
    @Column
    private String reportType;
    
    @Column
    private String exportType;
    
    @Column
    @Lob 
    private String ttxQuery;
    
    @Column
    private Boolean isInstantReport;
}