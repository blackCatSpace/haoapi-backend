package com.hao.project.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.project.common.BaseResponse;
import com.hao.project.common.ResultUtils;
import com.hao.project.constant.UserConstant;
import com.hao.project.model.vo.InterfaceInfoVO;
import com.hao.project.service.InterfaceInfoService;
import com.hao.project.service.UserInterfaceInfoService;
import com.hao.project.annotation.AuthCheck;
import com.hao.haoapicommon.service.model.entity.InterfaceInfo;
import com.hao.haoapicommon.service.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        // 查询调用次数最多的接口信息列表
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.listTopInvokeInterfaceInfo(3);
        // 将接口信息按照接口ID分组，便于关联查询
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        // 创建查询接口信息的条件包装器
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        // 设置查询条件，使用接口信息ID在接口信息映射中的键集合进行条件匹配
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        // 调用接口信息服务的list方法，传入条件包装器，获取符合条件的接口信息列表
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        // 构建接口信息VO列表，使用流式处理将接口信息映射为接口信息VO对象，并加入列表中
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            // 创建一个新的接口信息VO对象
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            // 将接口信息复制到接口信息VO对象中
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            // 从接口信息ID对应的映射中获取调用次数
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            // 将调用次数设置到接口信息VO对象中
            interfaceInfoVO.setTotalNum(totalNum);
            // 返回构建好的接口信息VO对象
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        // 返回处理结果
        return ResultUtils.success(interfaceInfoVOList);

      /*  // 1.查询排名前几的各个接口总调动次数
        // interfaceInfoId 1 3 2
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.listTopInvokeInterfaceInfo(3);
        // 2.根据排名前几的接口id查询接口信息

        // 将接口信息按照接口ID分组，便于关联查询
        Map<Long, List<UserInterfaceInfo>> interfaceInfoObjMap = userInterfaceInfoList
                .stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        // 根据接口ID批量查询接口信息
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, interfaceInfoObjMap.keySet());
        // interfaceInfoId 1 2 3
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);

        // 将InterfaceInfo转换为InterfaceInfoVO
        List<InterfaceInfoVO> interfaceInfoVOList = BeanUtil.copyToList(interfaceInfoList, InterfaceInfoVO.class);

        // 对每个InterfaceInfoVO补充TotalNum字段信息
        for (int i = 0; i < interfaceInfoList.size(); i++) {
            InterfaceInfoVO interfaceInfoVO = interfaceInfoVOList.get(i);
            interfaceInfoVO.setTotalNum(interfaceInfoObjMap.get(interfaceInfoVO.getId()).get(0).getTotalNum());
//            interfaceInfoVOList.get(i).setTotalNum( userInterfaceInfoList.get(i).getTotalNum());
        }
        return ResultUtils.success(interfaceInfoVOList);*/

    }
}
