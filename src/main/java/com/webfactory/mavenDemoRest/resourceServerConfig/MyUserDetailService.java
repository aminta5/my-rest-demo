package com.webfactory.mavenDemoRest.resourceServerConfig;

import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserDaoService userDaoService;

    public MyUserDetailService(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDaoService.findUserByNickname(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
