package com.imooc.ad.service.impl;

import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.exception.ExceptionCast;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.uitls.CommonUtils;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository adUserRepository;
    private final AdPlanRepository adPlanRepository;

    @Autowired
    public AdPlanServiceImpl(AdUserRepository adUserRepository, AdPlanRepository adPlanRepository) {
        this.adUserRepository = adUserRepository;
        this.adPlanRepository = adPlanRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            ExceptionCast.cast(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdUser> optional = adUserRepository.findById(request.getId());
        if (!optional.isPresent()) {
            ExceptionCast.cast(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldAdPlan = adPlanRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldAdPlan != null) {
            ExceptionCast.cast(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = adPlanRepository.save(new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.stringToLocalDateTime(request.getStartDate()),
                CommonUtils.stringToLocalDateTime(request.getEndDate())));

        AdPlanResponse adPlanResponse = new AdPlanResponse();
        BeanUtils.copyProperties(newAdPlan, adPlanResponse);

        return adPlanResponse;
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            ExceptionCast.cast(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return adPlanRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            ExceptionCast.cast(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan adPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (adPlan == null) {
            ExceptionCast.cast(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if (request.getPlanName() != null) {
            adPlan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            adPlan.setStartDate(CommonUtils.stringToLocalDateTime(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            adPlan.setEndDate(CommonUtils.stringToLocalDateTime(request.getEndDate()));
        }
        adPlan.setUpdateTime(LocalDateTime.now());
        adPlan = adPlanRepository.save(adPlan);

        AdPlanResponse adPlanResponse = new AdPlanResponse();
        BeanUtils.copyProperties(adPlan, adPlanResponse);

        return adPlanResponse;
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            ExceptionCast.cast(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan adPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (adPlan == null) {
            ExceptionCast.cast(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        adPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        adPlan.setUpdateTime(LocalDateTime.now());
        adPlanRepository.save(adPlan);
    }


}
