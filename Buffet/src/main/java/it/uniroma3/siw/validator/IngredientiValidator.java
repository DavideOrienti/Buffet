package it.uniroma3.siw.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.IngredientiService;

@Component
public class IngredientiValidator implements Validator{
	
	 @Autowired
	 private IngredientiService ingredientiService;
	 
	 private static final Logger logger = LoggerFactory.getLogger(IngredientiValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Ingredienti.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
		 if(ingredientiService.alreadyExist((Ingredienti)obj)) {
	            errors.reject("persona.duplicato");
	        }
	}}

}
