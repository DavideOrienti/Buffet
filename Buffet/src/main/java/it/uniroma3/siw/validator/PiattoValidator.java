package it.uniroma3.siw.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.PiattoService;

@Component
public class PiattoValidator implements Validator{

	 @Autowired
	 private PiattoService piattoService;
	 
	 private static final Logger logger = LoggerFactory.getLogger(PiattoValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Piatto.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
		 if(piattoService.alreadyExist((Piatto)obj)) {
	            errors.reject("persona.duplicato");
	        }}
	}
}
