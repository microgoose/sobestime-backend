package net.microgoose.mocknet.auth.security;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.auth.repository.RoleRepository;
import net.microgoose.mocknet.auth.repository.UserPrincipalRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static net.microgoose.mocknet.auth.config.MessageDictionary.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final UserPrincipalRepository repository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        userPrincipal.setRoles(roleRepository.findByUsersContains(userPrincipal));
        return new UserDetailsImpl(userPrincipal);
    }
}
