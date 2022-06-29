package org.mafideju.API_Kargopolov.controller;


import org.junit.jupiter.api.Test;
import org.mafideju.API_Kargopolov.model.response.UserDetailsResponseModel;
import org.mafideju.API_Kargopolov.service.implementation.UserServiceImplementation;
import org.mafideju.API_Kargopolov.vo.UserVo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserControllerTest {

  @Mock
  UserServiceImplementation userService;

  @InjectMocks
  UserController userController;

  UserVo userVo;
  final String USER_ID = "MOCKED_USER_ID";

  @Test
  void testGetUser() {
    MockitoAnnotations.openMocks(this);
    userVo = new UserVo();
    userVo.setFirstName("Linus");
    userVo.setEmail("linux@gatitos.com");
    userVo.setPassword("test");
    userVo.setUserId(USER_ID);
    when(userService.getUserByUserId(anyString())).thenReturn(userVo);

    UserDetailsResponseModel user = userController.getUser(USER_ID);

    assertNotNull(user);
    assertEquals(USER_ID, user.getUserId());
  }
}
