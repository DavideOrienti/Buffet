package it.uniroma3.siw.controller;

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

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
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
		model.addAttribute("login",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			cs.savePersona(chef);
			//model.addAttribute("chef", model);
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

//richiede tute le persone perche non specifico id
//	@GetMapping("/chef")
//	public String getChef(Model model) {
//		List<Chef> chef = cs.FindAll();
//		model.addAttribute("chef",chef);
//		return "chefs.html";	
//	}
@GetMapping("/chef")
public String getBuffet(Model model) {
	model.addAttribute("login",AuthenticationController.loggato);
	model.addAttribute("chefs", this.cs.FindAll());
	if(AuthenticationController.loggato) {
		if(AuthenticationController.admin) {	
			model.addAttribute("credentials",AuthenticationController.admin);
			
		}}
		return "chefs.html";
	
}
	
	
	@GetMapping("/chef/{id}")
	  public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("login",AuthenticationController.loggato);
		model.addAttribute("chef", this.cs.FindById(id));
	    return "chef.html";

}
	@GetMapping("/chefForm")
	public String geChef(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("login",AuthenticationController.loggato);
		return "chefForm.html";
		
	}
	
	@GetMapping("/addChef")
	public String addChef(Model model) {
		//logger.debug("addCuratore");
		model.addAttribute("chef", new Chef());
		
		
		//model.addAttribute("login",AuthenticationController.loggato);
		return "chefForm.html";
	}
	

}
