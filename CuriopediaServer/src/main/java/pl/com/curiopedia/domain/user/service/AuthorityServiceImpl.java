package pl.com.curiopedia.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.user.dto.AuthorityDTO;
import pl.com.curiopedia.domain.user.repository.AuthorityRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jakub on 22.06.17.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<AuthorityDTO> findAllAuthorities() {
        return authorityRepository
                .findAll()
                .stream()
                .map(a -> new AuthorityDTO(a.getName()))
                .collect(Collectors.toList());
    }
}
