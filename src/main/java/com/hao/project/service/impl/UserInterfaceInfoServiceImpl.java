package com.hao.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.haoapicommon.service.model.entity.UserInterfaceInfo;
import com.hao.project.mapper.UserInterfaceInfoMapper;
import com.hao.project.common.ErrorCode;
import com.hao.project.exception.BusinessException;
import com.hao.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2024-08-02 15:05:11
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 如果是添加操作
        if (add) {
            if (userInterfaceInfo.getUserId() <= 0 || userInterfaceInfo.getInterfaceInfoId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        // 剩余次数不能少于0
       if (userInterfaceInfo.getLeftNum() < 0) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能少于0");
       }

    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 1.判断数据库中是否存在该条数据
        //  存在，修改数据
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.setSql("totalNum = totalNum + 1, leftNum  = leftNum - 1");
        updateWrapper.ge(UserInterfaceInfo::getUserId, 1);
        updateWrapper.ge(UserInterfaceInfo::getInterfaceInfoId, 1);
        return this.update(updateWrapper);
        //  不存在，用户为第一次调用，则新增数据
    }

    @Override
    public List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit) {
        return baseMapper.listTopInvokeInterfaceInfo(limit);
    }
}




