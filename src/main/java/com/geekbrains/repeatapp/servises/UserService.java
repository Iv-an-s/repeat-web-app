package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.dtos.UserRegDto;
import com.geekbrains.repeatapp.entities.Role;
import com.geekbrains.repeatapp.entities.User;
import com.geekbrains.repeatapp.exceptions.DataValidationException;
import com.geekbrains.repeatapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public User saveUser(UserRegDto userRegDto) {
        if (findByUsername(userRegDto.getUsername()).isPresent()){
            throw new DataValidationException(Collections.singletonList("Пользователь с таким именем уже существует"));
        }
        User user = new User();
        user.setUsername(userRegDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegDto.getPassword()));
        user.setEmail(userRegDto.getEmail());
        user.setRoles(Collections.singletonList(roleService.getStandartUserRole()));

        return userRepository.save(user);
    }
}
