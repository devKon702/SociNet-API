package com.example.socinet.service;

import com.example.socinet.dto.UserDto;
import com.example.socinet.entity.User;
import com.example.socinet.repository.UserRepository;
import com.example.socinet.security.AccountDetail;
import com.example.socinet.util.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final FirebaseStorageService storageService;
    private final long MAX_IMAGE_SIZE = 3 * 1024 * 1024;
    public UserDto getUserInfo(){
        AccountDetail accountDetail = Helper.getAccountDetail();
        return new UserDto(accountDetail.getUser());
    }

    public UserDto getUserInfoById(Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return new UserDto(user.get());
        } else{
            throw new Exception("User not found");
        }
    }

    public UserDto updateUserInfo(String name,
                                  String phone,
                                  String school,
                                  String address,
                                  Boolean isMale,
                                  MultipartFile avatar) throws Exception{
        AccountDetail accountDetail = Helper.getAccountDetail();
        User user = accountDetail.getUser();
        if(user == null) throw new Exception("Cannot find user info");
        if(!name.isEmpty()) user.setName(name);
        if(!phone.isEmpty()) user.setPhone(phone);
        if(!school.isEmpty()) user.setSchool(school);
        if(!address.isEmpty()) user.setAddress(address);
        if(isMale != null) user.setMale(isMale);
        // Save avatar to firebase
        if(avatar != null){
            if(avatar.getSize() > MAX_IMAGE_SIZE) throw new Exception("Avatar size must <= 3MB");
            String avatarUrl = storageService.upload("images", avatar);
            user.setAvatarUrl(avatarUrl);
        }
        return new UserDto(userRepo.save(user));
    }


}
