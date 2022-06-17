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
	@GetMapping("/")
    public String defaultMapping(Model model)
    {
        return "index.html";
    }
	
@PostMapping("/ingredienti")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addChef(@Valid @ModelAttribute("chef")Chef chef,BindingResult br,Model model) {
		cv.validate(chef, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			cs.savePersona(chef);
			model.addAttribute("chef", model);
			//model.addAttribute("buffet", this.bs.FindAll());
			
//			if(AuthenticationController.admin) {
//				model.addAttribute("credentials",AuthenticationController.admin);
//			}
			return "index.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		else return "buffetForm.html";
		
	}

}
