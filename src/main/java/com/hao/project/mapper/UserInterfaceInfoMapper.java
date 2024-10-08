package com.hao.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.haoapicommon.service.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2024-08-02 15:05:11
* @Entity com.hao.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




