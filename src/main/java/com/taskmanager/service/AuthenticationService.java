package com.taskmanager.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    //Realizar a criacao do usuário repository para retornar

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Retornar o e-mail do usuario que será utilizado como login
        return null;
    }

    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
