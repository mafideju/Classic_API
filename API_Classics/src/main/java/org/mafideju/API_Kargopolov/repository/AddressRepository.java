package org.mafideju.API_Kargopolov.repository;

import org.mafideju.API_Kargopolov.entity.AddressEntity;
import org.mafideju.API_Kargopolov.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

    AddressEntity findByAddressId(String addressId);
}
