package com.digis01.MMarinCajeroClient.Controller;

import com.digis01.MMarinCajeroClient.Model.Fondo;
import com.digis01.MMarinCajeroClient.Model.Result;
import com.digis01.MMarinCajeroClient.Model.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cajero")
public class CajeroController {

    private RestTemplate restTemplate;
    private String URLAPI = "http://localhost:8081";

    @GetMapping()
    public String Login(Model model) {
        try {
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario);
            return "Login";
        } catch (Exception e) {
            return "redirect:/cajero/error";
        }

    }

    @PostMapping()
    public ModelAndView LoginPost(@ModelAttribute Usuario usuario, HttpSession session, ModelMap model) {
        try {
            this.restTemplate = new RestTemplate();
            ResponseEntity<Result<Usuario>> responseAuth = this.restTemplate.exchange(
                    this.URLAPI + "/usuarioapi/login",
                    HttpMethod.POST,
                    new HttpEntity(usuario),
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });
            Usuario usuarioAuth = responseAuth.getBody().object;
            session.setAttribute("idUsuario", usuarioAuth.getIdUsuario());
            if (usuarioAuth.rol.getNombre().equals("Administrador")) {
                return new ModelAndView("redirect:/cajero/admin");
            }
            return new ModelAndView("redirect:/cajero/retiro");
        } catch (HttpStatusCodeException e) {
            if (HttpStatus.UNAUTHORIZED == e.getStatusCode()) {
                model.addAttribute("error", "Credenciales invalidas");
                return new ModelAndView("redirect:/cajero", model);
            }
            return new ModelAndView("redirect:/cajero/error");
        } catch (Exception e) {
            return new ModelAndView("redirect:/cajero/error");
        }

    }

    @GetMapping("/retiro")
    public String RetiroGet(Model model, HttpSession session) {
        try {
            this.restTemplate = new RestTemplate();
            if (session.getAttribute("idUsuario") == null) {
                return "redirect:/cajero";
            }
            String idUsuario = session.getAttribute("idUsuario").toString();
            ResponseEntity<Result<Usuario>> responseUsuario = this.restTemplate.exchange(
                    this.URLAPI + "/usuarioapi/usuario/" + idUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });
            model.addAttribute("usuario", responseUsuario.getBody().object);
            model.addAttribute("result", new Result());
            return "Retiro";
        } catch (HttpStatusCodeException e) {

            return "redirect:/cajero/error";
        } catch (Exception e) {
            return "redirect:/cajero/error";
        }
    }

    @PostMapping("/retiro")
    @ResponseBody
    public Result RetiroPost(@ModelAttribute Result monto, HttpSession session) {
        try {
            double retiro = Double.parseDouble((String) monto.object);
            this.restTemplate = new RestTemplate();
            if (session.getAttribute("idUsuario") == null) {
                Result<String> result = new Result();
                result.correct = false;
                result.errorMessage = "No autenticado";
                return result;
            }
            String idUsuario = session.getAttribute("idUsuario").toString();
            ResponseEntity<Result<Usuario>> responseUsuario = this.restTemplate.exchange(
                    this.URLAPI + "/usuarioapi/usuario/" + idUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });
            Usuario usuario = (Usuario) responseUsuario.getBody().object;
            ResponseEntity<Result<Double>> responseFondo = this.restTemplate.exchange(
                    this.URLAPI + "/cajeroapi/fondo",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Double>>() {
            });
            if (retiro > usuario.getSaldo()) {
                Result result = new Result();
                result.correct = false;
                result.errorMessage = "El retiro es superior a tu saldo";
                return result;
            }
            Double fondo = responseFondo.getBody().object;
            if (retiro > fondo) {
                Result result = new Result();
                result.correct = false;
                result.errorMessage = "Los fondos no son suficientes en este cajero";
                return result;
            }
            session.setAttribute("error", null);
            ResponseEntity<Result<List<Fondo>>> responseRetiro = this.restTemplate.exchange(
                    URLAPI + "/cajeroapi/" + idUsuario,
                    HttpMethod.PUT,
                    new HttpEntity(monto),
                    new ParameterizedTypeReference<Result<List<Fondo>>>() {
            });
            Result<List<Fondo>> resultRetiro = responseRetiro.getBody();           
            return resultRetiro;
        } catch (HttpStatusCodeException e) {
            Result<String> result = new Result();
                result.correct = false;
                result.object = "redirect:/cajero";
                return result;
        } 
    }

    @GetMapping("/error")
    public String PaginaError() {
        return "Error";
    }

}
