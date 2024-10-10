package MusicPlatform.domain.oauth2.entity;

import MusicPlatform.domain.oauth2.adapter.AuthenticationAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser implements AuthenticationAdapter {

    private String uuid;

    public GoogleUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User.getAttributes(), oAuth2User, clientRegistration);
    }

    @Override
    public String getId() {
        return getAttributes().get("sub").toString();
    }

    @Override
    public String getName() {
        return getAttributes().get("name").toString();
    }

    @Override
    public String getPicture() {
        return getAttributes().get("picture").toString();
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }
}
