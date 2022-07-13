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
import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.service.IngredientiService;
import it.uniroma3.siw.service.PiattoService;
import it.uniroma3.siw.validator.IngredientiValidator;


@Controller
public class IngredientiController {
	
	@Autowired
	private IngredientiService is;
	@Autowired
	private IngredientiValidator iv;
	@Autowired
	private PiattoService ps;
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
@PostMapping("/ingrediente")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addIngredienti(@Valid @ModelAttribute("ingrediente")Ingredienti ingredienti,BindingResult br,Model model) {
		iv.validate(ingredienti, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		model.addAttribute("loggato",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			is.savePersona(ingredienti);
			
			model.addAttribute("ingredienti", this.is.FindAll());
//			model.addAttribute("ingrediente", model);
			if(AuthenticationController.loggato) {
				if(AuthenticationController.admin) {
					model.addAttribute("credentials",AuthenticationController.admin);
				}}
			return "index.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		
		else 
			model.addAttribute("listapiatti", this.ps.FindAll());
			return "ingredienteForm.html";
		
	}


@GetMapping("/ingrediente")
public String getBuffet(Model model) {
	model.addAttribute("loggato",AuthenticationController.loggato);
	model.addAttribute("ingredienti", this.is.FindAll());
	if(AuthenticationController.loggato) {
		if(AuthenticationController.admin) {	
			model.addAttribute("credentials",AuthenticationController.admin);
			
		}}
		return "ingredienti.html";
	
}

@GetMapping("/ingrediente/{id}")
  public String getIngrediente(@PathVariable("id") Long id, Model model) {
	model.addAttribute("loggato",AuthenticationController.loggato);
	model.addAttribute("ingrediente", this.is.FindById(id));
    return "ingrediente.html";

}
@GetMapping("ingredienteForm")
public String getIngredient(Model model) {
	 if(AuthenticationController.admin) {
	logger.debug("ingredienteForm");
	model.addAttribute("ingrediente", new Ingredienti());
	model.addAttribute("listapiatti", this.ps.FindAll());
	model.addAttribute("loggato",AuthenticationController.loggato);
	return "ingredienteForm.html";
	 }
	 else {
		 return "loginForm";
	 }
}


}
