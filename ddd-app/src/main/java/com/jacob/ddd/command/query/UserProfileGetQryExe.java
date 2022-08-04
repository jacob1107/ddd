package com.jacob.ddd.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.jacob.ddd.dto.UserProfileGetQry;
import com.jacob.ddd.dto.clientobject.UserProfileCO;
import com.jacob.ddd.gatewayimpl.database.UserProfileMapper;
import com.jacob.ddd.gatewayimpl.database.dataobject.UserProfileDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserProfileGetQryExe {

    @Resource
    private UserProfileMapper userProfileMapper;

    public SingleResponse<UserProfileCO> execute(UserProfileGetQry qry) {
        UserProfileDO userProfileDO = userProfileMapper.getByUserId(qry.getUserId());
        UserProfileCO userProfileCO = new UserProfileCO();
        BeanUtils.copyProperties(userProfileDO, userProfileCO);
        return SingleResponse.of(userProfileCO);
    }

}
