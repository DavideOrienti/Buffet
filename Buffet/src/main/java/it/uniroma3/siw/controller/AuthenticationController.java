package it.uniroma3.siw.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validator.CredentialsValidator;
import it.uniroma3.siw.validator.UserValidator;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;


	public static boolean loggato=false;
	public static boolean admin=false;
	public static boolean staLoggando=false;
	


	private static Credentials credentials;
	private static UserDetails userDetails;
	


	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;


	
	
	

	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerUser";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		staLoggando=true;
		return "loginForm";
	}
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public String LoginForm(@Valid @ModelAttribute("credentials")Credentials credentials,BindingResult br,Model model) {
//		if(!br.hasErrors())	{
//			if(AuthenticationController.loggato) {
//				if(AuthenticationController.admin) {
//					model.addAttribute("credentials",AuthenticationController.admin);
//				}}
//			return "index";
//		}
//		else {return "registerForm";}
//	}
	@RequestMapping(value = "/loginConferma", method = RequestMethod.GET) 
	public String loginConferma(Model model) {
		if(staLoggando) {
		loggato=true;
		return "loginSuccesso";
		}
		
		return "loginForm";
		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		loggato=false;
		admin=false;
		return "index";
	}
	
	

	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultAfterLogin(Model model ,HttpSession httpSession) {
		staLoggando=false;
		
		//caso positivo devo creare tutto se sono tutti e due a null voglio effettuare l'accesso
		if(userDetails==null && loggato ==true) {
			userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			credentials = credentialsService.getCredentials(userDetails.getUsername());
			model.addAttribute("loggato",true);
			httpSession.setAttribute("user",credentials.getUser());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				admin=true;
			}
			//ho effettuato l'accesso ora
			loggato =true;
		}
		// secondo caso , clicko su default senza aver effettuato l'accesso
		else if(userDetails!= null && loggato ==false) {
			model.addAttribute("loggato",false);
			userDetails=null;
			return"index";
		}
		//caso impossibile
		else if(userDetails== null && loggato ==true) {
			model.addAttribute("loggato",false);
			return"index";
		}
		//caso impossibile
		else if(userDetails!= null && loggato ==true) {
			model.addAttribute("loggato",true);
			return"index";
		}
		return"index";
	}


@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
public String registerUser(@ModelAttribute("user") User user,BindingResult userBindingResult,
		@Valid @ModelAttribute("credentials") Credentials credentials,
		BindingResult credentialsBindingResult,
		Model model) {

	// validate user and credentials fields
	this.userValidator.validate(user, userBindingResult);
	this.credentialsValidator.validate(credentials, credentialsBindingResult);

	// if neither of them had invalid contents, store the User and the Credentials into the DB
	if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
		// set the user and store the credentials;
		// this also stores the User, thanks to Cascade.ALL policy
		credentials.setUser(user);
		credentialsService.saveCredentials(credentials);
		return "registrationSuccessful";
	}
	
	
	return "registerUser";
}

@RequestMapping(value = {"index","/"}, method = RequestMethod.GET)
public String index(Model model) {
    model.addAttribute("loggato",AuthenticationController.loggato);
//    model.addAttribute("loggato",AuthenticationController.admin);
        return "index";
}



}