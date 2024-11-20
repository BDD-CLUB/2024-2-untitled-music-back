package MusicPlatform.domain.oauth2.adapter;

import MusicPlatform.domain.artist.entity.Role;

public interface AuthenticationAdapter {
    String getUuid();
    Role getRole();
}
