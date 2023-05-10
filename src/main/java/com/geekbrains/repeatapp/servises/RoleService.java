package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.entities.Role;
import com.geekbrains.repeatapp.exceptions.DataValidationException;
import com.geekbrains.repeatapp.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getStandartUserRole(){
//        Role role = new Role();
//        role.setName("ROLE_USER");
        return roleRepository.findByName("ROLE_USER").orElseThrow(()-> new DataValidationException(Collections.singletonList("Стандартной записи ROLE_USER в БД не существует")));
    }
}
