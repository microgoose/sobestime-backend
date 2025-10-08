package net.microgoose.mocknet.auth.security;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.model.AuthUser;
import net.microgoose.mocknet.auth.repository.AuthUserRepository;
import net.microgoose.mocknet.auth.repository.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final AuthUserRepository repository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser authUser = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));
        authUser.setRoles(roleRepository.findByUsersContains(authUser));
        return new UserDetailsImpl(authUser);
    }
}
