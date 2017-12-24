package voter.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import voter.model.entities.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean isEnabled;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails (User user) {
        this.username = user.getName();
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();

        Set<GrantedAuthority> authorityList = new HashSet<>();
        for (Role role: user.getRoles()) {
            authorityList.add(new SimpleGrantedAuthority(role.name()));
        }
        this.authorities = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
