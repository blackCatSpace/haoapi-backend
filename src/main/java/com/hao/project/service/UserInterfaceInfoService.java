package com.hao.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.haoapicommon.service.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2024-08-02 15:05:11
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 查排名前几的各个接口总调动次数
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}
