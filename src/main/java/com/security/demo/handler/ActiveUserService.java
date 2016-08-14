package com.security.demo.handler;

import com.security.demo.handler.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ActiveUserService {


    @Autowired
    SessionRegistry sessionRegistry;


    public int getActiveUserCount(){
        return sessionRegistry.getAllPrincipals().size();
    }

    public int getActiveUserAllSessionsCount(){

        List<Object> list = sessionRegistry.getAllPrincipals();

        int count = 0;

        for (Object o : list) {
            LoginUser user = (LoginUser)o;

            List<SessionInformation> userAllSessions = sessionRegistry.getAllSessions(user, false);

            count  = count + userAllSessions.size();

        }

        return count;
    }

}
