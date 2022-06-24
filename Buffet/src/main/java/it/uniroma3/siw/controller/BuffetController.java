package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.validator.BuffetValidator;

@Controller
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
			bs.saveBuffet(buffet);
			model.addAttribute("buffet", model);
			//model.addAttribute("buffet", this.bs.FindAll());
			
//			if(AuthenticationController.admin) {
//				model.addAttribute("credentials",AuthenticationController.admin);
//			}
			return "buffets.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		else return "buffetForm.html";
		
	}
	
//	//@RequestMapping(value="/addBuffet", method = RequestMethod.GET)
//	@GetMapping("/addBuffet")
//	public String addFilm(Model model) {
//		//logger.debug("addArtista");
//		model.addAttribute("buffet", new Buffet());
//		//
//		//model.addAttribute("login",AuthenticationController.loggato);
//		return "artistaForm.html";
//	}
//	
	
	//richiede tute le persone perche non specifico id
	@GetMapping("/buffet")
	public String getBuffet(Model model) {
		List<Buffet> buffet = bs.FindAll();
		model.addAttribute("buffet",buffet);
		return "buffets.html";
		
		
	}
	
	@GetMapping("/buffet/{id}")
	  public String getBuffet(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("buffet", this.bs.FindById(id));
	    return "buffet.html";

}
	@GetMapping("/buffetForm")
	public String geBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "buffetForm.html";
		
	}
	
	

}
