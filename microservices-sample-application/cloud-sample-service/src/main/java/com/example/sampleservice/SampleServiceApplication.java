package com.example.sampleservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/sample")
public class SampleServiceApplication implements ApplicationListener<WebServerInitializedEvent>{
	 
	private Integer port = null; 
	
	public static void main(String[] args) {
        SpringApplication.run(SampleServiceApplication.class, args);
    }
 
   
    @GetMapping("")
    public SampleContent findContent(){
    	SampleContent s = new SampleContent();
    	s.setId(1L);
    	s.setContent("Hi.  I'm some content from sample service on port ("+port+" )");
    	
        return s;
        
    }


	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		port = event.getSource().getPort();
		
	}


	
 
  
}
