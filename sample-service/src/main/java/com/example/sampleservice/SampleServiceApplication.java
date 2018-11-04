package com.example.sampleservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/sample")
public class SampleServiceApplication {
	public static void main(String[] args) {
        SpringApplication.run(SampleServiceApplication.class, args);
    }
 
   
    @GetMapping("")
    public SampleContent findContent(){
    	SampleContent s = new SampleContent();
    	s.setId(1L);
    	s.setContent("Hi.  I'm some content");
        return s;
    }
 
  
}
