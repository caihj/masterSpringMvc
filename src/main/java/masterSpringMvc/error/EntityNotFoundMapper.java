package masterSpringMvc.error;

import masterSpringMvc.authentication.AuthLoginListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class EntityNotFoundMapper {

    private static Logger logger = LoggerFactory.getLogger(EntityNotFoundMapper.class);

    @ExceptionHandler(EntityNotFoundException.class)
    //@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity could not be found")
    public void handleNotFound(EntityNotFoundException e,Model model) {

        model.addAttribute("message",e.getMessage());
    }

}
