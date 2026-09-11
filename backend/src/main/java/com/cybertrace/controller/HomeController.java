package com.cybertrace.controller;
import org.springframework.web.bind.annotation.*;
@RestController public class HomeController { @GetMapping("/") public String home(){return "CyberTrace Backend is Running 🚀";} }
