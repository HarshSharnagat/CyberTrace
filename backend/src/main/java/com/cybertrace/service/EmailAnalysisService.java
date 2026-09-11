package com.cybertrace.service;

import com.cybertrace.model.ForensicCase;
import com.cybertrace.repository.ForensicCaseRepository;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.*;

@Service
public class EmailAnalysisService {
 private final ForensicCaseRepository cases;
 public EmailAnalysisService(ForensicCaseRepository cases){this.cases=cases;}
 public ForensicCase analyze(MultipartFile file) throws Exception {
   byte[] data=file.getBytes();
   String hash=sha256(data);
   Session session=Session.getInstance(new Properties());
   MimeMessage msg=new MimeMessage(session,new ByteArrayInputStream(data));
   String subject=Optional.ofNullable(msg.getSubject()).orElse("");
   String from=Optional.ofNullable(msg.getFrom()).map(a->a.toString()).orElse("");
   String body=extractText(msg);
   String combined=(subject+" "+from+" "+body);
   List<String> urls=extract(combined,"https?://[^\\s<>\"]+");
   List<String> ips=extract(combined,"\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
   int score=0; List<String> findings=new ArrayList<>(); String low=combined.toLowerCase();
   if(low.matches(".*\\b(urgent|verify|suspend|password|account|click)\\b.*")){score+=25; findings.add("Urgency/credential language detected");}
   if(!urls.isEmpty()){score+=25; findings.add("URL indicator(s) extracted: "+urls.size());}
   if(!ips.isEmpty()){score+=20; findings.add("IP indicator(s) extracted: "+ips.size());}
   if(from.toLowerCase().contains("reply-to")){score+=10; findings.add("Reply-To should be reviewed");}
   score=Math.min(score,100);
   ForensicCase c=new ForensicCase(); c.setSubject(subject); c.setSender(from); c.setThreatScore(score); c.setThreatLevel(score>=70?"HIGH":score>=40?"MEDIUM":"LOW"); c.setEvidenceHash(hash); c.setFindings(String.join("; ",findings)+" | URLs="+urls+" | IPs="+ips); return cases.save(c);
 }
 private String extractText(Part p)throws Exception{ if(p.isMimeType("text/plain")) return String.valueOf(p.getContent()); if(p.isMimeType("text/html")) return String.valueOf(p.getContent()).replaceAll("<[^>]*>"," "); if(p.isMimeType("multipart/*")){Multipart m=(Multipart)p.getContent(); StringBuilder s=new StringBuilder(); for(int i=0;i<m.getCount();i++)s.append(extractText(m.getBodyPart(i))).append(' '); return s.toString();} return ""; }
 private List<String> extract(String text,String regex){Matcher m=Pattern.compile(regex,Pattern.CASE_INSENSITIVE).matcher(text); List<String> out=new ArrayList<>(); while(m.find()&&out.size()<100)out.add(m.group()); return out.stream().distinct().toList();}
 private String sha256(byte[] data)throws Exception{byte[] h=MessageDigest.getInstance("SHA-256").digest(data); StringBuilder s=new StringBuilder(); for(byte b:h)s.append(String.format("%02x",b)); return s.toString();}
}
