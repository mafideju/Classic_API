package org.mafideju.API_Kargopolov.service.implementation;

import org.mafideju.API_Kargopolov.exception.ErrorMessagesEnum;
import org.mafideju.API_Kargopolov.exception.UserServiceException;
import org.mafideju.API_Kargopolov.entity.UserEntity;
import org.mafideju.API_Kargopolov.repository.UserRepository;
import org.mafideju.API_Kargopolov.service.UserService;
import org.mafideju.API_Kargopolov.helper.GeneratePublicID;
import org.mafideju.API_Kargopolov.vo.AddressVo;
import org.mafideju.API_Kargopolov.vo.UserVo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GeneratePublicID generatePublicID;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserVo createUser(UserVo user) {

        UserEntity storedUserEmailDetails = userRepository.findByEmail(user.getEmail());
        if (storedUserEmailDetails != null) throw new RuntimeException("Email já existe em nossa base!");

        for (int i = 0; i < user.getAddresses().size(); i++) {
            AddressVo address = user.getAddresses().get(i);
            address.setUserDetails(user);
            address.setAddressId(generatePublicID.generateAddressId(5));
            user.getAddresses().set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(generatePublicID.generateUserId(10));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        return modelMapper.map(storedUserDetails, UserVo.class);
    }

    @Override
    public UserVo getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserVo returnValue = new UserVo();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserVo getUserByUserId(String userId) {
        UserVo returnValue = new UserVo();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Usuário não encontrado " + userId);
        }
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserVo updateUser(String id, UserVo user) {
        UserVo returnValue = new UserVo();
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) {
            throw new UserServiceException(ErrorMessagesEnum.NO_RECORD_FOUND.getErrorMessage());
        }

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());

        UserEntity updatedUser = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessagesEnum.NO_RECORD_FOUND.getErrorMessage());
        }

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserVo> getUsers() {
        List<UserVo> returnValue = new ArrayList<>();

        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();

        for (UserEntity user : users) {
            UserVo userModel = new UserVo();
            BeanUtils.copyProperties(user, userModel);
            returnValue.add(userModel);
        }

        System.out.println("QUANTIDADE DE REGISTROS -> " + users.size());

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
