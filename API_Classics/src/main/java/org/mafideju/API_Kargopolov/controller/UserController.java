package org.mafideju.API_Kargopolov.controller;

import org.mafideju.API_Kargopolov.exception.UserServiceException;
import org.mafideju.API_Kargopolov.model.request.UserDetailsRequestModel;
import org.mafideju.API_Kargopolov.model.response.AddressResponseModel;
import org.mafideju.API_Kargopolov.model.response.UserDetailsResponseModel;
import org.mafideju.API_Kargopolov.model.status.OperationStatusModel;
import org.mafideju.API_Kargopolov.service.AddressService;
import org.mafideju.API_Kargopolov.service.UserService;
import org.mafideju.API_Kargopolov.vo.AddressVo;
import org.mafideju.API_Kargopolov.vo.UserVo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;

    @GetMapping
    public List<UserDetailsResponseModel> getUsers() {
        List<UserDetailsResponseModel> returnValue = new ArrayList<>();

        List<UserVo> users = userService.getUsers();
        for (UserVo user : users) {
            UserDetailsResponseModel userModel = new UserDetailsResponseModel();
            BeanUtils.copyProperties(user, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDetailsResponseModel getUser(@PathVariable String id) {
        UserDetailsResponseModel returnValue = new UserDetailsResponseModel();

        UserVo user = userService.getUserByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(user, UserDetailsResponseModel.class);

        return returnValue;
    }

    @GetMapping(path = "/{id}/address")
    public List<AddressResponseModel> getAddressByUser(@PathVariable String id) {
        List<AddressResponseModel> returnValue = new ArrayList<>();

        List<AddressVo> addressVo = addressService.getAddress(id);

        if (addressVo != null && !addressVo.isEmpty()) {
            Type listType = new TypeToken<List<AddressResponseModel>>() {}.getType();
            returnValue = new ModelMapper().map(addressVo, listType);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}/address/{addressId}")
    public AddressResponseModel getAddressOfSingleUser(@PathVariable String id, @PathVariable String addressId) {
        AddressVo addressVo = addressService.getAddressByAddressId(addressId);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        AddressResponseModel returnValue = modelMapper.map(addressVo, AddressResponseModel.class);

        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
        Link userAddressLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).slash("addresses").withRel("addresses");
        Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).slash("addresses").slash(addressId).withSelfRel();

        returnValue.add(userLink);
        returnValue.add(userAddressLink);
        returnValue.add(selfLink);

        return returnValue;
    }

    @PostMapping
    public UserDetailsResponseModel createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {

        ModelMapper modelMapper = new ModelMapper();
        UserVo userVo = modelMapper.map(userDetails, UserVo.class);

        UserVo createdUser = userService.createUser(userVo);
        UserDetailsResponseModel returnValue = modelMapper.map(createdUser, UserDetailsResponseModel.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public UserDetailsResponseModel updateUser(@PathVariable final String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserDetailsResponseModel returnValue = new UserDetailsResponseModel();

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDetails, userVo);

        UserVo updatedUser = userService.updateUser(id, userVo);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable final String id) {
        OperationStatusModel returnValue = new OperationStatusModel();

        returnValue.setOperationName("DELETE");
        userService.deleteUser(id);
        returnValue.setOperationResult("SUCCESS");

        return returnValue;
    }
}
