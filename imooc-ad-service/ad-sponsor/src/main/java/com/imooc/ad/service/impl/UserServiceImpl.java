package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.exception.ExceptionCast;
import com.imooc.ad.service.IUserService;
import com.imooc.ad.uitls.CommonUtils;
import com.imooc.ad.vo.CreateUserRequest;
import com.imooc.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository adUserRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (!request.validate()) {
            ExceptionCast.cast(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser adUser = adUserRepository.findByUsername(request.getUsername());
        if (adUser != null) {
            ExceptionCast.cast(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = adUserRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));

        CreateUserResponse createUserResponse = new CreateUserResponse();
        BeanUtils.copyProperties(newUser, createUserResponse);

        return createUserResponse;
    }

}
