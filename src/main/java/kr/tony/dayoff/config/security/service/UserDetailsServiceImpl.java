package kr.tony.dayoff.config.security.service;

import kr.tony.dayoff.exception.CustomException;
import kr.tony.dayoff.exception.ExceptionStatus;
import kr.tony.dayoff.user.domain.User;
import kr.tony.dayoff.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 이메일을 통해 유저 정보를 담은 UserDetails 을 반환 하는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND_USER));

        return new UserDetailsImpl(user, user.getEmail());
    }
}
