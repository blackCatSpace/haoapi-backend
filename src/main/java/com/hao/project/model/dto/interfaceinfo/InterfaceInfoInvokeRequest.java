package com.hao.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author hao
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;

    private static final long serialVersionUID = 1L;
}