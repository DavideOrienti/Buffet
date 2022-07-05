package it.uniroma3.siw.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Component
public class ChefValidator implements Validator {
	
	 @Autowired
	 private ChefService chefService;

	 private static final Logger logger = LoggerFactory.getLogger(ChefValidator.class);
	 
	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
		 if(chefService.alreadyExist((Chef)obj)) {
			 logger.debug("e' un duplicato");
		 
	            errors.reject("chef.duplicato");
		 }   }
	}

}
