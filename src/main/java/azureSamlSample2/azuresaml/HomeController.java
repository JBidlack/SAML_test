/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package azureSamlSample2.azuresaml;

/**
 *
 * @author jbidlack
 */
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Properties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.ui.Model;



@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		
		return "home";
	}
	 @RequestMapping("/Admin")
     @PreAuthorize("hasAuthority('APPROLE_admin')")
     public String Admin() {
         
//        model.addAttribute("name", principal.getName());
//        model.addAttribute("emailAddress", principal.getFirstAttribute("email"));
//        model.addAttribute("userAttributes", principal.getAttributes());
         return "Admin";
     }
     
	 @RequestMapping("/logout")
     public String logout(HttpServletRequest request, HttpServletResponse response){
         Properties prop = new Properties();
         FileInputStream file = null;
         String endSessionEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/logout";
         String redirect = "https://localhost:8080/";
         
         String idToken = (String) request.getSession().getAttribute("login_hint");
                 
        try{
            file = new FileInputStream("src/main/resources/application.properties");
        
	         request.getSession().invalidate();
	         
	         response.sendRedirect(endSessionEndpoint + "?post_logout_redirect_uri=" +
	        		 URLEncoder.encode(redirect, "UTF-8") + "&id_token_hint=" + URLEncoder.encode(idToken, "UTF-8"));
	         
        }
        catch(Exception e){
            e.printStackTrace();
        }
         return "logout";
     }
	 
     
     
     
     public String error(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
//         model.addAttribute("userAttributes", principal.getAttributes());
         
         return "error";
     }
}