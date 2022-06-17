package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.validator.BuffetValidator;

public class BuffetController {
	
	@Autowired
	private BuffetService bs;
	@Autowired
	private BuffetValidator bv;
	
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
	@GetMapping("/")
    public String defaultMapping(Model model)
    {
        return "index.html";
    }
	
	//GET per operazioni di lettura
	//POST per operazioni di scrittura
	
	@PostMapping("/buffet")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addBuffet(@Valid @ModelAttribute("buffet")Buffet buffet,BindingResult br,Model model) {
		bv.validate(buffet, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			bs.savePersona(buffet);
			model.addAttribute("buffet", model);
			//model.addAttribute("buffet", this.bs.FindAll());
			
//			if(AuthenticationController.admin) {
//				model.addAttribute("credentials",AuthenticationController.admin);
//			}
			return "index.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		else return "buffetForm.html";
		
	}

}
