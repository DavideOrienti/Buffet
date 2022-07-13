package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.PiattoService;
import it.uniroma3.siw.validator.BuffetValidator;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService bs;
	@Autowired
	private BuffetValidator bv;
	
	private Buffet buffetCorrente;
	
	@Autowired
	private PiattoService ps;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
//	@GetMapping("/")
//    public String defaultMapping(Model model)
//    {
//		if(AuthenticationController.loggato) {
//			if(AuthenticationController.admin) {
//				model.addAttribute("credentials",AuthenticationController.admin);
//			}}
//        return "index.html";
//    }	
//	
	
	//GET per operazioni di lettura
	//POST per operazioni di scrittura
	
	@PostMapping("/buffet")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addBuffet(@Valid @ModelAttribute("buffet")Buffet buffet,BindingResult br,Model model) {
		
		
		bv.validate(buffet, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		model.addAttribute("loggato",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			bs.saveBuffet(buffet);
			//model.addAttribute("buffet", model);
			model.addAttribute("buffets", this.bs.FindAll());
		
			
			
			
			//model.addAttribute("piatti", this.bs.getPiattoService().FindById(buffet.getId()));
			
			
			if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {
				model.addAttribute("credentials",AuthenticationController.admin);
			}}
			return "buffets.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		model.addAttribute("chefs", this.bs.getChefService().FindAll());
	     return "buffetForm.html";
		
	}

	
	@GetMapping("/buffet")
	public String getBuffet(Model model,Buffet buffet) {
		model.addAttribute("loggato",AuthenticationController.loggato);
		model.addAttribute("buffets", this.bs.FindAll());
		
		
		
		if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {	
				model.addAttribute("credentials",AuthenticationController.admin);
				
			}}
			return "buffets.html";
		
	}
	
	@GetMapping("/buffet/{id}")
	  public String getBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("loggato",AuthenticationController.loggato);
		buffetCorrente = this.bs.FindById(id);
	    model.addAttribute("buffet", this.bs.FindById(id));
	    model.addAttribute("listapiatti", this.ps.PiattiPerBuffet(buffetCorrente));
	    return "buffet.html";

}
	@GetMapping("/buffetForm")
	public String addBuffet(Model model) {
		 if(AuthenticationController.admin) {
		logger.debug("buffetForm");
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chefs", this.bs.getChefService().FindAll());
		model.addAttribute("loggato",AuthenticationController.loggato);
		return "buffetForm.html";}
		 else {
			 return "loginForm";
		 }
		
	}
	
//	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
//	public String registerUser(@ModelAttribute("user") User user,BindingResult userBindingResult,
//			@Valid @ModelAttribute("credentials") Credentials credentials,
//			BindingResult credentialsBindingResult,
//			Model model) {
//	
//		this.userValidator.validate(user, userBindingResult);
//		this.credentialsValidator.validate(credentials, credentialsBindingResult);
//		
//		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
//			
//			credentials.setUser(user);
//			credentialsService.saveCredentials(credentials);
//			return "registrationSuccessful";
//		}
//		return "registerUser";
//	}
//	@PostMapping("/buffetForm")
//	public String addBuffet(@ModelAttribute("buffet")Buffet buffet,Model model,BindingResult br) {
//       bv.validate(buffet, br);
//       // model.addAttribute("buffet",buffet);
//        
//        model.addAttribute("chefs", this.bs.getChefService().FindAll());
//        model.addAttribute("loggato",AuthenticationController.loggato);
//        if(!br.hasErrors() && buffet.getChef()!=null )	{
//        	bs.saveBuffet(buffet);
//        	model.addAttribute("buffet",bs.FindById(buffet.getId()));
//			return "index";
//		}
//		else{return "buffetForm";}
//		
//	}
//	
//	@PostMapping("/remove/{id}")
//	
//	public String getOepra(@PathVariable("id") Long id, Model model) {
//		//model.addAttribute("login",AuthenticationController.loggato);
//		//model.addAttribute("buffets", this.bs.FindAll());
//		
//		Buffet buffetnuovo = new Buffet("BuffetSconosciuto");
//		model.addAttribute("buffet",buffetnuovo);
//		List<Piatto> listaPiatti = new ArrayList<Piatto>();
//		listaPiatti= bs.FindById(id).getPiatti();
//		for(int i=0;i<listaPiatti.size();i++) {
//			listaPiatti.get(i).setBuffet(buffetnuovo);
//		}
//		//bs.rimuovi(this.bs.FindById(id));
//		if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);
//			//model.addAttribute("credentials",credentials.getRole());
//		}
//		return "index.html";
//	}
//	
	
//	@GetMapping("/remove/{id}")
//	
//	public String getOpera(@PathVariable("id") Long id, Model model) {
//		//model.addAttribute("login",AuthenticationController.loggato);
//		//model.addAttribute("buffets", this.bs.FindAll());
//		
//		this.bs.FindById(id).setPiatti(null);
//		this.bs.rimuovi(this.bs.FindById(id));
//		
//		//bs.rimuovi(this.bs.FindById(id));
//		if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);
//			//model.addAttribute("credentials",credentials.getRole());
//		}
//		return "index.html";
//	}

//	@GetMapping("/remove/{id}")
//	public String removeBuffet(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("login",AuthenticationController.loggato);
//		model.addAttribute("piatti", this.bs.FindById(id).getPiatti()); 
//		//model.addAttribute(model)
//		bs.rimuovi(this.bs.FindById(id));
//		if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);
//			//model.addAttribute("credentials",credentials.getRole());
//		}
//		return "collezioni.html";
//	}
////	
	
//	@GetMapping("/remove/{id}")
//	public String getOepra(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("login",AuthenticationController.loggato);
//		model.addAttribute("buffets", this.bs.FindAll());
//		bs.rimuovi(this.bs.FindById(id));
//		if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);
//			//model.addAttribute("credentials",credentials.getRole());
//		}
//		return "index.html";
//	}

}
