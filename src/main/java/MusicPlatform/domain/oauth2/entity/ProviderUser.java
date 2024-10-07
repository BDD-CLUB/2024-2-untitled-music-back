package MusicPlatform.domain.oauth2.entity;

import MusicPlatform.domain.oauth2.adapter.AuthenticationAdapter;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface ProviderUser extends OAuth2User {
    String getId();
    String getName();
    String getPassword();
    String getEmail();
    String getProvider();
    String getPicture();

    List<? extends GrantedAuthority> getAuthorities();
    Map<String, Object> getAttributes();
    void setUuid(String uuid);

}
