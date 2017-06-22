package pl.com.curiopedia.domain.user.service;

import pl.com.curiopedia.domain.user.dto.AuthorityDTO;

import java.util.List;

/**
 * Created by jakub on 22.06.17.
 */
public interface AuthorityService {
    List<AuthorityDTO> findAllAuthorities();
}
