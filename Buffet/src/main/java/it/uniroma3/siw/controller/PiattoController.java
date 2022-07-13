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

import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.IngredientiService;
import it.uniroma3.siw.service.PiattoService;
import it.uniroma3.siw.validator.PiattoValidator;



@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService ps;
	@Autowired
	private PiattoValidator pv;
	
	@Autowired
	private IngredientiService is;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
@PostMapping("/piatto")
	
	//bilding result gestische i casi di errore
	//model Attriubute associa cio che c edentro al modello con l oggetto persona
		public String addPiatto(@Valid @ModelAttribute("piatto")Piatto piatto,BindingResult br,Model model) {
		pv.validate(piatto, br); /* "aggiunge il caso di errore a br quindi nel if oltre a controllare i classici 
		                              errori contro anche che non ci siano duplicati*/
		model.addAttribute("loggato",AuthenticationController.loggato);
		if(!br.hasErrors())	{
			ps.savePiatto(piatto);
			//model.addAttribute("piatti", model);
			model.addAttribute("piatti", this.ps.FindAll());
			
			//model.addAttribute("listaIngredienti" ,this.ps.FindAllIngredientiById(id));
			//model.addAttribute("listaIngredienti" ,this.ps.FindIngredienti());
			
			
			if(AuthenticationController.loggato) {
				if(AuthenticationController.admin) {
					model.addAttribute("credentials",AuthenticationController.admin);
				}}
			return "piatti.html";  // se il problema non ha trovato errori torna alla pagina iniziale
		}
		
		else 
			model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
			return "piattoForm.html";
		
	}



@GetMapping("/piatto")
public String getBuffet(Model model,Piatto piatto) {
	 if(AuthenticationController.loggato) {
	model.addAttribute("loggato",AuthenticationController.loggato);
	model.addAttribute("piatti", this.ps.FindAll());
	//model.addAttribute("listaIngredienti" ,this.ps.FindAllIngredientiById(id));
	//model.addAttribute("listaIngredienti" ,this.ps.FindIngredienti());
	if(AuthenticationController.loggato) {
		if(AuthenticationController.admin) {	
			model.addAttribute("credentials",AuthenticationController.admin);
			
		}}
		return "piatti.html";
	
}
	 else {
		 return "loginForm";}
	 }
	
	@GetMapping("/piatto/{id}")
	  public String getPiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("loggato",AuthenticationController.loggato);
	    model.addAttribute("piatto", this.ps.FindById(id));
	    model.addAttribute("IngredientiPiatto", this.ps.FindById(id).getIngredienti());
	   
	    if(AuthenticationController.loggato) {
			if(AuthenticationController.admin) {	
				model.addAttribute("credentials",AuthenticationController.admin);
				
			}}
	    
	    return "piatto.html";

}
	@GetMapping("/piattoForm")
	public String gePiatto(Model model) {
		if(AuthenticationController.admin) {
		model.addAttribute("loggato",AuthenticationController.loggato);
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
		return "piattoForm.html";}
		else {return "loginForm";}
	}
	
	@PostMapping("/remove/{id}")
	
    public String removePiatto(Model model, @PathVariable("id") Long idPiatto) {
		
		 if(AuthenticationController.admin) {
            Piatto p= ps.piattoPerId(idPiatto);

            List<Ingredienti> ingredientiP = new ArrayList<>( p.getIngredienti());

            for(Ingredienti i: ingredientiP) {

                p.removeIngrediente(i);
                i.removePiatto(p);
            }

            ps.rimuovi(p);
//            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Credentials credentials = this.ps.get.getCredentials(userDetails.getUsername());
//
//            model.addAttribute("credentials", credentials);
            if(AuthenticationController.admin) {	
    			model.addAttribute("credentials",AuthenticationController.admin);
    			//model.addAttribute("credentials",credentials.getRole());
    		}

            
            return "index.html";
    }
		 else {
			 return "loginForm";
		 }}
	
	


}
