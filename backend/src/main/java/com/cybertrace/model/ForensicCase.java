package com.cybertrace.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="forensic_cases")
public class ForensicCase {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String status="OPEN";
 private String subject;
 private String sender;
 private String threatLevel;
 private Integer threatScore;
 private String evidenceHash;
 private Instant createdAt=Instant.now();
 @Column(columnDefinition="text") private String findings;
 public Long getId(){return id;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public String getSubject(){return subject;} public void setSubject(String v){subject=v;} public String getSender(){return sender;} public void setSender(String v){sender=v;}
 public String getThreatLevel(){return threatLevel;} public void setThreatLevel(String v){threatLevel=v;} public Integer getThreatScore(){return threatScore;} public void setThreatScore(Integer v){threatScore=v;}
 public String getEvidenceHash(){return evidenceHash;} public void setEvidenceHash(String v){evidenceHash=v;} public Instant getCreatedAt(){return createdAt;}
 public String getFindings(){return findings;} public void setFindings(String v){findings=v;}
}
