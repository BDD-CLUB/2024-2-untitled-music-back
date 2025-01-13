package MusicPlatform.domain.oauth2.entity;

import MusicPlatform.domain.artist.entity.Role;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 모든 서비스 제공자가 동일하게 제공하는 속성들
 */
public abstract class OAuth2ProviderUser implements ProviderUser {

    private String uuid;
    private Role role;

    private final Map<String, Object> attributes;
    private final OAuth2User oAuth2User;
    private final ClientRegistration clientRegistration;

    public OAuth2ProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User,
                              ClientRegistration clientRegistration) {
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getEmail() {
        return getAttributes().get("email").toString();
    }

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }
}
