package masterSpringMvc.controller;

import masterSpringMvc.date.ChinaDateFormatter;
import masterSpringMvc.dto.ProfileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ProfileController {

    @Autowired
    private ApplicationContext context;

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return ChinaDateFormatter.getPattern(locale);
    }

    @RequestMapping("/profile")
    public String displayProfile(@Valid ProfileForm profileForm,BindingResult bindingResult){
        profileForm.setTwitterHandle("John");
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile" , method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileForm profileForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("模型有错误");
            System.out.println(context.getMessage("Size.profileForm.twitterHandle",null,Locale.getDefault()));
            return "profile/profilePage";
        }
        System.out.println("save ok"+ profileForm);
        return "redirect:/profile";
    }


}
