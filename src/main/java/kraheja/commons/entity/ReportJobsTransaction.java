package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class ReportJobsTransaction implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column
    private String value;
    
    @Column
    private long reportMasterId;
    
    @Column
    private String status;

    @Column(name = "REPORT_LOCATION")
    private String reportLocation;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
    
    @Column(name = "REPORT_NAME")
    private String reportName;
    
    @Column(name = "EXPORT_TYPE")
    private String exportType;
}