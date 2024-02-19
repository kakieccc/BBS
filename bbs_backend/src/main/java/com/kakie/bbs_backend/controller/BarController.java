package com.kakie.bbs_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kakie.bbs_backend.common.BaseResponse;
import com.kakie.bbs_backend.common.ErrorCode;
import com.kakie.bbs_backend.common.ResultUtils;
import com.kakie.bbs_backend.dto.BarDTO;
import com.kakie.bbs_backend.exception.BusinessException;
import com.kakie.bbs_backend.model.domain.Bar;
import com.kakie.bbs_backend.model.domain.User;
import com.kakie.bbs_backend.model.request.BarAddRequest;
import com.kakie.bbs_backend.model.request.BarJoinRequest;
import com.kakie.bbs_backend.model.request.BarQuitRequest;
import com.kakie.bbs_backend.model.request.BarUpdateRequest;
import com.kakie.bbs_backend.service.BarService;
import com.kakie.bbs_backend.service.UserService;
import com.kakie.bbs_backend.vo.UserBarVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bar")
@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true")
@Tag(name = "分区接口")
@Slf4j
public class BarController {
    @Resource
    private BarService barService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Long> addBar(@RequestBody BarAddRequest barAddRequest, HttpServletRequest request) {
        if (barAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Bar bar = new Bar();
        BeanUtils.copyProperties(barAddRequest,bar);
        long barId = barService.addBar(bar, loginUser);
        return ResultUtils.success(barId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBar(@RequestBody long id,HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = barService.deleteBar(id,loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateBar(@RequestBody BarUpdateRequest barUpdateRequest, HttpServletRequest request) {
        if (barUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = barService.updateBar(barUpdateRequest,loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get")
    public BaseResponse<Bar> getBarById(@RequestBody long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Bar bar = barService.getById(id);
        if (bar == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(bar);
    }

    @GetMapping("/list")
    public BaseResponse<List<UserBarVO>> listBars(BarDTO barDTO, HttpServletRequest request){
        if (barDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isAdmin = userService.isAdmin(request);
        List<UserBarVO> barList = barService.listBars(barDTO,isAdmin);
        return ResultUtils.success(barList);
    }

    @GetMapping("/list/page")
    public BaseResponse<Page<Bar>> listPageBars(BarDTO barDTO) {
        if (barDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Bar bar = new Bar();
        BeanUtils.copyProperties(barDTO, bar);
        Page<Bar> page = new Page<>(barDTO.getPageNum(),barDTO.getPageSize());
        QueryWrapper<Bar> queryWrapper = new QueryWrapper<>(bar);
        Page<Bar> resultPage = barService.page(page,queryWrapper);
        return ResultUtils.success(resultPage);
    }

    @PostMapping("/join")
    public BaseResponse<Boolean> joinBar(@RequestBody BarJoinRequest barJoinRequest, HttpServletRequest request){
        if (barJoinRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = barService.joinBar(barJoinRequest, loginUser);
        return ResultUtils.success(result);
    }

    @PostMapping("/quit")
    public BaseResponse<Boolean> quitBar(@RequestBody BarQuitRequest barQuitRequest, HttpServletRequest request){
        if (barQuitRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = barService.quitBar(barQuitRequest, loginUser);
        return ResultUtils.success(result);
    }
}
