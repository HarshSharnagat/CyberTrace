package com.cybertrace.service;
import com.cybertrace.model.ForensicCase; import com.cybertrace.repository.ForensicCaseRepository; import org.springframework.stereotype.Service; import java.util.*;
@Service public class CaseService { private final ForensicCaseRepository repo; public CaseService(ForensicCaseRepository repo){this.repo=repo;} public List<ForensicCase> all(){return repo.findAll();} public Optional<ForensicCase> get(Long id){return repo.findById(id);} }
