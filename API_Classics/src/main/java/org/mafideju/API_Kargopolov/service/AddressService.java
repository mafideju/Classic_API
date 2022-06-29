package org.mafideju.API_Kargopolov.service;

import org.mafideju.API_Kargopolov.vo.AddressVo;

import java.util.List;

public interface AddressService {
    List<AddressVo> getAddress(String userID);

    AddressVo getAddressByAddressId(String addressId);
}
