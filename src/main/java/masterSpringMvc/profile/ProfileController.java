package masterSpringMvc.profile;

import masterSpringMvc.date.ChinaDateFormatter;
import masterSpringMvc.profile.ProfileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ProfileController {

    @Autowired
    private ApplicationContext context;

    private UserProfileSession userProfileSession;

    public ProfileController(UserProfileSession userProfileSession){
        this.userProfileSession = userProfileSession;
    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return ChinaDateFormatter.getPattern(locale);
    }

    @RequestMapping("/profile")
    public String displayProfile(Model model){
        model.addAttribute("profileForm",userProfileSession.toForm());
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile" , params = {"save"}, method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileForm profileForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("模型有错误");
            System.out.println(context.getMessage("Size.profileForm.twitterHandle",null,Locale.getDefault()));
            return "profile/profilePage";
        }
        System.out.println("save ok"+ profileForm);
        userProfileSession.saveForm(profileForm);
        if(!profileForm.getTastes().isEmpty()){
            return "redirect:/search/mixed;keywords="+String.join(",",profileForm.getTastes());
        }

        return "redirect:/profile";
    }

    @RequestMapping(value = "/profile",params = {"addTaste"})
    public String addRow( ProfileForm profileForm){
        profileForm.getTastes().add(null);
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile",params = {"removeTaste"})
    public String removeRow(ProfileForm profileForm, HttpServletRequest req){
        Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
        profileForm.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }


}
