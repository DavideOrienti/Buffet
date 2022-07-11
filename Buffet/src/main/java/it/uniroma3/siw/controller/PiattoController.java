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

////richiede tute le persone perche non specifico id
//	@GetMapping("/piatto")
//	public String getPiatto(Model model) {
//		List<Piatto> piatto = ps.FindAll();
//		model.addAttribute("piatto",piatto);
//		return "piatti.html";
//	}

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
	
	
//	@GetMapping("/remove/{id}")
//	public String removePiatto(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("login",AuthenticationController.loggato);
//		model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
//		
//		ps.rimuovi(this.ps.FindById(id));
//	
//		if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);
//			//model.addAttribute("credentials",credentials.getRole());
//		}
//		return "index.html";
//	}
	


	
//	@GetMapping("/modifica/{id}")
//	
//	public String ModificaPiatto(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("login",AuthenticationController.loggato);
//		model.addAttribute("piatto", this.ps.FindById(id));
//		model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
//		if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);
//			//model.addAttribute("credentials",credentials.getRole());
//		}
//		return "piattoForm.html";
//	}
//	@PostMapping("/modifica/{id}")
//	
//	public String newCollezione( Model model,@ModelAttribute("piatto")Piatto piatto, BindingResult bindingResult) {
//		this.pv.validate(piatto, bindingResult);
//		//model.addAttribute("login",AuthenticationController.loggato);
//		if (!bindingResult.hasErrors()) {
//			Piatto piattoCorrente=this.ps.FindById(piatto.getId());  //<-----------
//			
//		piattoCorrente.setDescrizione(piattoCorrente.getDescrizione());
//		piattoCorrente.setIngredienti(piattoCorrente.getIngredienti());
//		piattoCorrente.setNome(piattoCorrente.getNome());
//		
//		
//			
//			
//			return "index.html";
//			//}
//		}
//		
//		return "piattoForm.html";
//	}

//	@GetMapping("/modifica/{id}")
//    public String modificaConcerto(Model model,@PathVariable("id") Long id) {
//        Piatto p= ps.FindById(id);
//        model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
//        model.addAttribute("piatto", p);
//        
//        return "piattoForm.html";
//    }
//
//	@PostMapping("/modifica/{id}")
//  public String modificaPiatto(Model model,@PathVariable("id") Long id,@RequestBody Piatto piatto) {
//	Piatto p=ps.update(piatto,id);
//		model.addAttribute("piatto",p);
//		model.addAttribute("piatti", this.ps.FindAll());
//		model.addAttribute("buffet", p.getBuffet());
//		model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
//		model.addAttribute("IngredientiPiatto", this.ps.FindById(id).getIngredienti());
//		 if(AuthenticationController.admin) {	
//				model.addAttribute("credentials",AuthenticationController.admin);
//				//model.addAttribute("credentials",credentials.getRole());
//			}
//      
//      return "piattoForm.html";
//  }
//	
//	@GetMapping("/modifica/{id}")
//    public String modificaPiatto(Model model,@PathVariable("id") Long id) {
//        Piatto p= ps.FindById(id);
//        model.addAttribute("piatto", p);
//        model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
//        return "piattoForm.html";
//    }
//
//	@PostMapping("/modifica/{id}")
//    public String modificaPiatto(@ModelAttribute("piatto") Piatto piatto, Model model,BindingResult bindingResult, @PathVariable("id") Long Id) {
//       
//        piatto.setBuffet(ps.piattoPerId(Id).getBuffet());
//        piatto.setDescrizione(ps.FindById(Id).getDescrizione());
//        piatto.setImmagine(ps.FindById(Id).getImmagine());
//        piatto.setIngredienti(ps.FindById(Id).getIngredienti());
//        piatto.setNome(ps.FindById(Id).getNome());
//        piatto.setOrigine(ps.FindById(Id).getOrigine());
////        piatto.setId(Id);
//        ps.savePiatto(piatto);
//       
//        
//        if(AuthenticationController.admin) {	
//			model.addAttribute("credentials",AuthenticationController.admin);}
//        
//       // model.addAttribute("buffet" ps.FindById(Id).getBuffet());
//        model.addAttribute("buffets", this.ps.getBuffetService().FindAll());
//
//        return "index.html";
//         }
//
//	
//	@GetMapping("/modifica/{id}")
//    public String modificaPiatto(Model model,@PathVariable("id") Long id) {
//        Piatto p= ps.FindById(id);
//        model.addAttribute("piatto", p);
//        if(AuthenticationController.loggato) {
//     		if(AuthenticationController.admin) {	
//     			model.addAttribute("credentials",AuthenticationController.admin);
//     			
//     		}}
//
//           
//        return "ModificaPiatto.html";
//        }
//	
//	@PostMapping("/piatto/{id}")
//    public String modificaPiatto(@ModelAttribute("piatto") Piatto piatto, Model model,BindingResult bindingResult,
//            @PathVariable("id") Long Id) {
//	
//	//Chef c = cs.FindById(Id);
//     piatto.setId(Id);
//     ps.savePiatto(piatto);
//     //cs.cancellaChef(c);
//   
//     //chef.setId(Id);
//     piatto=ps.FindById(Id);
//     model.addAttribute("piatto", piatto);
//     if(AuthenticationController.loggato) {
// 		if(AuthenticationController.admin) {	
// 			model.addAttribute("credentials",AuthenticationController.admin);
// 			
// 		}}
//
//        return "piatto.html";
//         }
//
//	

}
