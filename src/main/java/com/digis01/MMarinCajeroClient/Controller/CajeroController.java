package com.digis01.MMarinCajeroClient.Controller;

import com.digis01.MMarinCajeroClient.Model.Result;
import com.digis01.MMarinCajeroClient.Model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cajero")
public class CajeroController {
    
    @GetMapping()
    public String Login(Model model){        
        try {
            Usuario usuario = new Usuario();
            model.addAttribute("usuario",usuario);
            return "Login";
        } catch (Exception e) {
            return "redirect:/cajero/error";
        }
    
    }
    @PostMapping()
    public String LoginPost(@ModelAttribute Usuario usuario, HttpSession session){
        try {
            
            
            return "redirect:/cajero/retiro";
        } catch (Exception e) {
            
            return "redirect:/cajero/error";
        }
    
    }
    @GetMapping("/retiro")
    public String RetiroGet(Model model){
        try {
            model.addAttribute("result", new Result());
            return "Retiro";            
        } catch (Exception e) {
            return "";
        }
    }
    
    
    
    
}
