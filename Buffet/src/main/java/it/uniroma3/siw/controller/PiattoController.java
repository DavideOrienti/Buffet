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
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.PiattoService;
import it.uniroma3.siw.validator.PiattoValidator;



@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService ps;
	@Autowired
	private PiattoValidator pv;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//quando non mi arriva nulla oppure caso base vado in index pagina iniziale
//	@GetMapping("/")
//    public String defaultMapping(Model model)
//    {
//        return "index.html";
//    }
	
@PostMapping("/piatto")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addPiatto(@Valid @ModelAttribute("piatto")Piatto piatto,BindingResult br,Model model) {
		pv.validate(piatto, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		if(!br.hasErrors())	{
			ps.savePersona(piatto);
			//model.addAttribute("piatti", model);
			model.addAttribute("piatti", this.ps.FindAll());
		
			
			if(AuthenticationController.loggato) {
				if(AuthenticationController.admin) {
					model.addAttribute("credentials",AuthenticationController.admin);
				}}
			return "piatti.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		else return "piattoForm.html";
		
	}

////richiede tute le persone perche non specifico id
//	@GetMapping("/piatto")
//	public String getPiatto(Model model) {
//		List<Piatto> piatto = ps.FindAll();
//		model.addAttribute("piatto",piatto);
//		return "piatti.html";
//	}

@GetMapping("/piatto")
public String getBuffet(Model model,Piatto piatto) {
	model.addAttribute("login",AuthenticationController.loggato);
	model.addAttribute("piatti", this.ps.FindAll());
	
	if(AuthenticationController.loggato) {
		if(AuthenticationController.admin) {	
			model.addAttribute("credentials",AuthenticationController.admin);
			
		}}
		return "piatti.html";
	
}
	
	@GetMapping("/piatto/{id}")
	  public String getPiatto(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("piatto", this.ps.FindById(id));
	    model.addAttribute("login",AuthenticationController.loggato);
	    return "piatto.html";

}
	@GetMapping("/piattoForm")
	public String gePiatto(Model model) {
		model.addAttribute("login",AuthenticationController.loggato);
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
		return "piattoForm.html";
		
	}

}
