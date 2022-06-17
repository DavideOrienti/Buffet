package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.service.IngredientiService;
import it.uniroma3.siw.validator.IngredientiValidator;


@Controller
public class IngredientiController {
	
	@Autowired
	private IngredientiService is;
	@Autowired
	private IngredientiValidator iv;
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	@GetMapping("/")
    public String defaultMapping(Model model)
    {
        return "index.html";
    }
	
@PostMapping("/ingredienti")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addIngredienti(@Valid @ModelAttribute("ingredienti")Ingredienti ingredienti,BindingResult br,Model model) {
		iv.validate(ingredienti, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			is.savePersona(ingredienti);
			model.addAttribute("ingredienti", model);
			//model.addAttribute("buffet", this.bs.FindAll());
			
//			if(AuthenticationController.admin) {
//				model.addAttribute("credentials",AuthenticationController.admin);
//			}
			return "index.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		else return "ingredientiForm.html";
		
	}

}
