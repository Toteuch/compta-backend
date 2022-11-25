package com.toteuch.compta.backend.service;

import com.toteuch.compta.backend.dto.UserDTO;
import com.toteuch.compta.backend.exception.ComptaException;
import com.toteuch.compta.backend.repository.UserRepository;

import static javax.transaction.Transactional.TxType.SUPPORTS;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional(SUPPORTS)
public class UserService {

    @Inject
    UserRepository repo;

    public UserDTO create(UserDTO dto) {
        dto.id = null;
        validate(dto);
        return repo.create(dto);
    }

    public void delete(Long id) {
        UserDTO userToDelete = repo.get(id);
        if (userToDelete == null) throw new ComptaException(Response.Status.NOT_FOUND);
        repo.delete(id);
    }

    public UserDTO update(UserDTO dto) {
        UserDTO userToUpdate = repo.get(dto.id);
        if (userToUpdate == null) throw new ComptaException(Response.Status.NOT_FOUND);
        validate(dto);
        return repo.update(dto);
    }

    public UserDTO get(Long id) {
        UserDTO user = repo.get(id);
        if (user == null) throw new ComptaException(Response.Status.NOT_FOUND);
        return user;
    }

    public List<UserDTO> getAll() {
        return repo.getAll();
    }

    private void validate(UserDTO dto) {
        UserDTO existingDTO = null;
        if (dto.id != null) existingDTO = repo.get(dto.id);
        if (dto.id != null && existingDTO == null) throw new ComptaException(Response.Status.NOT_FOUND);
        if (dto.name == null || dto.name.trim().length() == 0) throw new ComptaException(Response.Status.BAD_REQUEST);
        if ((existingDTO == null || !Objects.equals(existingDTO.name, dto.name)) && repo.getByName(dto.name) != null) throw new ComptaException(Response.Status.CONFLICT);
    }
}
