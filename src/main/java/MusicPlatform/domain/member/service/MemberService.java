package MusicPlatform.domain.member.service;

import MusicPlatform.domain.member.entity.Member;
import MusicPlatform.domain.member.repository.MemberRepository;
import MusicPlatform.domain.oauth2.entity.ProviderUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void register(String registrationId, ProviderUser providerUser) {
        if (memberRepository.findByEmail(providerUser.getEmail()) != null) { // 이미 가입한 회원 검증
            return;
        }

        Member member = Member.builder()
                .email(providerUser.getEmail())
                .nickname(providerUser.getUsername())
                .provider(registrationId)
                .profileImage("") //기본 이미지 등록
                .build();

        memberRepository.save(member);
    }
}
