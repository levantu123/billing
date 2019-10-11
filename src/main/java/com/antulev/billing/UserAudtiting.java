package com.antulev.billing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAudtiting implements AuditorAware<String>{
	@Override
    public Optional<String> getCurrentAuditor() {

        String uname = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(uname);
    }
}
