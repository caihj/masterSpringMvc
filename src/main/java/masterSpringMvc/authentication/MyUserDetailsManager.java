package masterSpringMvc.authentication;

import masterSpringMvc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户管理服务
 */
@Component
public class MyUserDetailsManager implements UserDetailsManager  {

    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        masterSpringMvc.dto.User u = userDao.findByUserName(username);
        if(u!=null) {
            return User.withUsername(u.getUserName()).password(u.getPassword()).roles("USER").build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}
