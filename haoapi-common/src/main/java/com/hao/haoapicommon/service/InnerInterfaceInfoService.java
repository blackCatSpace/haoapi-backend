package com.hao.haoapicommon.service;

import com.hao.haoapicommon.service.model.entity.InterfaceInfo;

public interface InnerInterfaceInfoService {
    /**
     * 查询是否存在调用的接口
     * @param url
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String url, String method);
}
