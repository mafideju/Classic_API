package org.mafideju.API_Kargopolov.service.implementation;

import org.mafideju.API_Kargopolov.entity.AddressEntity;
import org.mafideju.API_Kargopolov.entity.UserEntity;
import org.mafideju.API_Kargopolov.repository.AddressRepository;
import org.mafideju.API_Kargopolov.repository.UserRepository;
import org.mafideju.API_Kargopolov.service.AddressService;
import org.mafideju.API_Kargopolov.vo.AddressVo;
import org.mafideju.API_Kargopolov.vo.UserVo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImplementation implements AddressService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;
    public List<AddressVo> getAddress(String userID) {
        List<AddressVo> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = userRepository.findByUserId(userID);

        List<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        for(AddressEntity addressEntity : addresses) {
            AddressVo tal = modelMapper.map(addressEntity, AddressVo.class);
            returnValue.add(tal);
        }

        return returnValue;
    }

    @Override
    public AddressVo getAddressByAddressId(String addressId) {
        AddressVo returnValue = new AddressVo();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if (addressEntity != null) {
            returnValue = modelMapper.map(addressEntity, AddressVo.class);
        }

        return returnValue;
    }
}
