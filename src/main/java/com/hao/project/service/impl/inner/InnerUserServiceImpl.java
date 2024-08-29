package com.hao.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hao.project.exception.BusinessException;
import com.hao.project.common.ErrorCode;
import com.hao.project.mapper.UserMapper;
import com.hao.haoapicommon.service.InnerUserService;
import com.hao.haoapicommon.service.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    UserMapper userMapper;
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccessKey, accessKey);

        return userMapper.selectOne(queryWrapper);
    }
}
