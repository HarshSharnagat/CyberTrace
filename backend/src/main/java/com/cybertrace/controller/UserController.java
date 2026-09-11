package com.cybertrace.controller;
import com.cybertrace.service.UserService; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/users") public class UserController { private final UserService service; public UserController(UserService service){this.service=service;} @GetMapping("/count") public Map<String,Long> count(){return Map.of("count",service.count());} }
