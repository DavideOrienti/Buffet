package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.validator.ChefValidator;



@Controller
public class ChefController {
	
	@Autowired
	private ChefService cs;
	@Autowired
	private ChefValidator cv;
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	
@PostMapping("/chef")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addChef(@Valid @ModelAttribute("chef")Chef chef,BindingResult br,Model model) {
		cv.validate(chef, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		model.addAttribute("loggato",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			cs.savePersona(chef);
			//model.addAttribute("chef", model);immobile
			this.cs.savePersona(chef);
			model.addAttribute("chefs", this.cs.FindAll());
			
			if(AuthenticationController.loggato) {
				if(AuthenticationController.admin) {
					model.addAttribute("credentials",AuthenticationController.admin);
				}}
			return "chefs.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		
		 return "chefForm.html";
		
	}


@GetMapping("/chef")
public String getBuffet(Model model) {
	model.addAttribute("loggato",AuthenticationController.loggato);
	model.addAttribute("chefs", this.cs.FindAll());
	if(AuthenticationController.loggato) {
		if(AuthenticationController.admin) {	
			model.addAttribute("credentials",AuthenticationController.admin);
			
		}}
		return "chefs.html";
	
}
	
	
	@GetMapping("/chef/{id}")
	  public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("loggato",AuthenticationController.loggato);
		model.addAttribute("chef", this.cs.FindById(id));
		if(AuthenticationController.loggato) {
	 		if(AuthenticationController.admin) {	
	 			model.addAttribute("credentials",AuthenticationController.admin);
	 			
	 		}}

	       
	    return "chef.html";

}
	@GetMapping("/chefForm")
	public String geChef(Model model) {
		 if(AuthenticationController.admin) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("loggato",AuthenticationController.loggato);
		return "chefForm.html";}
		 else {
			 return "loginForm";
		 }
		
	}
	
//	@GetMapping("/addChef")
//	public String addChef(Model model) {
//		//logger.debug("addCuratore");
//		model.addAttribute("chef", new Chef());
//		
//		
//		//model.addAttribute("login",AuthenticationController.loggato);
//		return "chefForm.html";
//	}
//	
	@GetMapping("/modifica/{id}")
    public String modificaChef(Model model,@PathVariable("id") Long id) {
		 if(AuthenticationController.admin) {
        Chef c= cs.FindById(id);
        model.addAttribute("chef", c);
        if(AuthenticationController.loggato) {
     		if(AuthenticationController.admin) {	
     			model.addAttribute("credentials",AuthenticationController.admin);
     			
     		}}

           
        return "ModificaChef.html";
        }else {
        	return "loginForm";
        }
	}

	@PostMapping("/chef/{id}")
    public String modificaChef(@ModelAttribute("chef") Chef chef, Model model,BindingResult bindingResult,
            @PathVariable("id") Long Id) {
	
	//Chef c = cs.FindById(Id);
     chef.setId(Id);
     cs.salvaChef(chef);
     //cs.cancellaChef(c);
   
     //chef.setId(Id);
     chef=cs.FindById(Id);
     model.addAttribute("chef", chef);
     if(AuthenticationController.loggato) {
 		if(AuthenticationController.admin) {	
 			model.addAttribute("credentials",AuthenticationController.admin);
 			
 		}}

        return "chef.html";
         }

}
