package com.hao.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hao.project.exception.BusinessException;
import com.hao.project.common.ErrorCode;
import com.hao.project.mapper.InterfaceInfoMapper;
import com.hao.haoapicommon.service.InnerInterfaceInfoService;
import com.hao.haoapicommon.service.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    InterfaceInfoMapper interfaceInfoMapper;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterfaceInfo::getUrl, url);
        queryWrapper.eq(InterfaceInfo::getMethod, method);

        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
