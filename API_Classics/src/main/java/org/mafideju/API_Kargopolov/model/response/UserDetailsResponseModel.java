package org.mafideju.API_Kargopolov.model.response;

import org.mafideju.API_Kargopolov.model.request.AddressRequestModel;

import java.util.List;

public class UserDetailsResponseModel {
    // UserRest

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressResponseModel> addresses;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressResponseModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponseModel> addresses) {
        this.addresses = addresses;
    }
}
