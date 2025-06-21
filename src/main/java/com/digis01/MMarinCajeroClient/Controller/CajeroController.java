package com.digis01.MMarinCajeroClient.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cajero")
public class CajeroController {
    
    @GetMapping()
    public String Index(){
        try {            
            return "Login";
        } catch (Exception e) {
            return "Error";
        }
    
    }
    
    
}
