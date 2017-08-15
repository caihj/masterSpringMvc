package masterSpringMvc.authentication;

import masterSpringMvc.dao.UserDao;
import masterSpringMvc.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.EventListener;
import java.util.HashMap;

@Component
public class AuthLoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static Logger logger = LoggerFactory.getLogger(AuthLoginListener.class);

    @Autowired
    private UserDao userDao;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
            logger.info("user logined "+event.getAuthentication().getName());
            logger.info(event.getAuthentication().getClass().getName());
            if(event.getAuthentication() instanceof OAuth2Authentication){
                OAuth2Authentication oAuth2 = (OAuth2Authentication ) event.getAuthentication();
                HashMap<String,Object> hashMap = (HashMap) oAuth2.getUserAuthentication().getDetails();
                String oauth_id = hashMap.get("id").toString();
                String oauth_address = hashMap.get("url").toString();

                User u = userDao.findByUserName(event.getAuthentication().getName());
                if(u==null){
                    u = new User();
                    u.setUserName(event.getAuthentication().getName());
                    u.setOauthId(oauth_id);
                    u.setOauthAddress(oauth_address);
                    u.setType(1);
                    userDao.save(u);
                }
            }
    }

}
