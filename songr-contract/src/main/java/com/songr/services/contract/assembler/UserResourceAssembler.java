package com.songr.services.contract.assembler;

import com.mysql.cj.jdbc.Blob;
import com.songr.services.contract.resource.UserResource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserResourceAssembler {

    public UserResource toResource(Map<String, Object> data) {
        UserResource userResource = new UserResource();

        userResource.setId((int) data.get("id"));
        userResource.setUserName((String) data.get("userName"));
        userResource.setFirstName((String) data.get("firstName"));
        userResource.setMiddleName((String) data.get("middleName"));
        userResource.setLastName((String) data.get("lastName"));
        userResource.setEmail((String) data.get("email"));
        userResource.setPassword((String) data.get("password"));
        userResource.setDateOfBirth((String) data.get("dateOfBirth"));
        userResource.setGender((String) data.get("gender"));
        userResource.setProfileImage((Blob) data.get("profileImage"));
        userResource.setToken((String) data.get("token"));
        userResource.setSpotify_refresh_token((String) data.get("spotify_refresh_token"));

        return userResource;
    }
}