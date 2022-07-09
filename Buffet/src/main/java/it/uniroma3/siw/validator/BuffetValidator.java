package it.uniroma3.siw.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;


@Component
public class BuffetValidator implements Validator {
	
	 @Autowired
	 private BuffetService buffetService;
	  private static final Logger logger = LoggerFactory.getLogger(BuffetValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Buffet.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (!errors.hasErrors()) {
			 logger.debug("confermato: valori non nulli");
		 if(buffetService.alreadyExist((Buffet)obj)) {
			 logger.debug("e' un duplicato");
	            errors.reject("buffet.duplicato");
	        }
//		 if(buffetService.ChefNonInserito((Buffet)obj)) {
//			 logger.debug("Chef non puo essere nullo");
//	            errors.reject("buffet.chef.nullo");
//	        }
		}
	}

}
