package masterSpringMvc.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

/**
 * 验证注解，校验日期比当前日期小
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
@Documented
public @interface PastLocalDate {

    String message() default "{javax.validation.constraints.Past.message}";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};

    public class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {
       public void initialize(PastLocalDate constraint) {
       }

       public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
           return date==null || date.isBefore(LocalDate.now());
       }
    }
}
