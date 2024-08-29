package com.hao.haoapicommon.service;

import com.hao.haoapicommon.service.model.entity.User;

public interface InnerUserService {
    /**
     * 查询用户是否分配accessKey、secretKey
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
