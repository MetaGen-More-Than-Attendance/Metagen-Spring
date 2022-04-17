package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Role;
import com.hst.metagen.entity.User;
import com.hst.metagen.repository.UserRepository;
import com.hst.metagen.service.abstracts.RoleService;
import com.hst.metagen.service.abstracts.UserService;
import com.hst.metagen.service.dtos.UserDto;
import com.hst.metagen.util.exception.NotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Lazy
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder bcryptEncoder;
    private RoleService roleService;
    private ModelMapper modelMapper;
    private ModelMapperService modelMapperService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bcryptEncoder, RoleService roleService, ModelMapper modelMapper, ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        User user = userRepository.getUserByUserMail(userMail);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        for(Role role : user.getUserRoles()){
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserMail(), user.getUserPassword(), roles);
    }

    @Override
    public UserDto save(UserDto userDto) {

        Role adminUser = roleService.getByRoleName("ADMIN_USER");
        Role endUser = roleService.getByRoleName("END_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(endUser);
        User newUser = new User();
        newUser.setUserName(userDto.getUserName());
        newUser.setUserSurname(userDto.getUserSurname());
        newUser.setUserMail(userDto.getUserMail());
        newUser.setUserPassword(bcryptEncoder.encode(userDto.getUserPassword()));
        newUser.setUserRoles(roles);
        return modelMapper.map(userRepository.save(newUser),UserDto.class);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.getUserByUserMail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.getByResetPasswordToken(token);
    }

    @Override
    public User getUserByUserMail(String username) {
        return userRepository.getUserByUserMail(username);
    }

    @Override
    public void updatePassword(User user, String newPassword) {

        String encodedPassword = bcryptEncoder.encode(newPassword);
        user.setUserPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public UserDto getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        if (! (authentication.getPrincipal() instanceof User)) {
            return null;
        }

        User authenticatedUser = (User) authentication.getPrincipal();
        return modelMapperService.forRequest().map(authenticatedUser, UserDto.class);

    }

}
