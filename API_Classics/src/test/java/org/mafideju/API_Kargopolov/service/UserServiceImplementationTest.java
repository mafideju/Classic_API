package org.mafideju.API_Kargopolov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mafideju.API_Kargopolov.entity.UserEntity;
import org.mafideju.API_Kargopolov.helper.GeneratePublicID;
import org.mafideju.API_Kargopolov.repository.UserRepository;
import org.mafideju.API_Kargopolov.service.implementation.UserServiceImplementation;
import org.mafideju.API_Kargopolov.vo.AddressVo;
import org.mafideju.API_Kargopolov.vo.UserVo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImplementationTest {

    @InjectMocks
    UserServiceImplementation userService;

    @Mock
    UserRepository userRepository;

    @Mock
    GeneratePublicID utils;

    @Mock
    BCryptPasswordEncoder bCrypt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
        UserEntity userEntity = new UserEntity();
        UserVo userVo = new UserVo();
        AddressVo addressTransporter = new AddressVo();
        List<AddressVo> addresses = new ArrayList<>();

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateUserId(anyInt())).thenReturn("USER_ID");
        when(utils.generateAddressId(anyInt())).thenReturn("ADDRESS_ID");
        when(bCrypt.encode(anyString())).thenReturn("ENCRYPTED_PASSWORD");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        addresses.add(addressTransporter);
        userVo.setAddresses(addresses);

        UserVo storedUserDetails = userService.createUser(userVo);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getEmail(), storedUserDetails.getEmail());
    }

    @Test
    void createUserThrowsException() {

        UserEntity userEntity = new UserEntity();
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserVo user = new UserVo();
        user.setEmail("test@test.com");
        user.setEncryptedPassword("ENCRYPTED_PASSWORD");

        assertThrows(RuntimeException.class, () -> userService.createUser(user));
    }

    @Test
    void getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserId("ABC");
        userEntity.setFirstName("Linux");

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserVo user = userService.getUser("test@test.com");

        assertNotNull(user);
        assertEquals("Linux", user.getFirstName());
    }

    @Test
    void getUserThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.getUser("test@test.com"));
    }

    @Test
    void getUserByUserId() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void loadUserByUsername() {
    }
}