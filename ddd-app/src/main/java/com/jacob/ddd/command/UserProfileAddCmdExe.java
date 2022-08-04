package com.jacob.ddd.command;

import com.alibaba.cola.dto.Response;
import com.jacob.ddd.convertor.UserProfileConvertor;
import com.jacob.ddd.domain.user.UserProfile;
import com.jacob.ddd.dto.UserProfileAddCmd;
import com.jacob.ddd.domain.gateway.UserProfileGateway;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * UserProfileAddCmdExe
 *
 * @author Frank Zhang
 * @date 2019-02-28 6:25 PM
 */
@Component
public class UserProfileAddCmdExe{

    @Resource
    private UserProfileGateway userProfileGateway;

    public Response execute(UserProfileAddCmd cmd) {
        UserProfile userProfile = UserProfileConvertor.toEntity(cmd.getUserProfileCO());
        userProfileGateway.create(userProfile);
        return Response.buildSuccess();
    }
}
