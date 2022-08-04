package com.jacob.ddd.command;

import com.alibaba.cola.dto.Response;
import com.jacob.ddd.convertor.UserProfileConvertor;
import com.jacob.ddd.domain.user.UserProfile;
import com.jacob.ddd.dto.UserProfileUpdateCmd;
import com.jacob.ddd.domain.gateway.UserProfileGateway;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserProfileUpdateCmdExe{

    @Resource
    private UserProfileGateway userProfileGateway;

    public Response execute(UserProfileUpdateCmd cmd) {
        UserProfile userProfile = UserProfileConvertor.toEntity(cmd.getUserProfileCO());
        userProfileGateway.update(userProfile);
        return Response.buildSuccess();
    }
}