package com.imooc.ad.service;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.ad.Application;
import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.unit_condition.AdUnitItRepository;
import com.imooc.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.imooc.ad.dao.unit_condition.CreativeUnitRepository;
import com.imooc.ad.dump.DConstant;
import com.imooc.ad.dump.table.*;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.entity.Creative;
import com.imooc.ad.entity.unit_condition.AdUnitDistrict;
import com.imooc.ad.entity.unit_condition.AdUnitIt;
import com.imooc.ad.entity.unit_condition.AdUnitKeyword;
import com.imooc.ad.entity.unit_condition.CreativeUnit;
import com.imooc.ad.vo.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {
    @Autowired
    private AdPlanRepository adPlanRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;

    @Test
    public void dumpAdTableData() {

        dumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        dumpAdCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        dumpAdUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        dumpAdUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        dumpAdUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        dumpAdUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));

    }

    private void dumpAdPlanTable(String fileName) {
        List<AdPlan> adPlans = adPlanRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());

        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }

        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(
                        p.getId(),
                        p.getUserId(),
                        p.getPlanStatus(),
                        p.getStartDate(),
                        p.getEndDate()
                )
        ));

        this.generalWriting(planTables, fileName);
    }

    private void dumpAdUnitTable(String fileName) {
        final List<AdUnit> adUnits = adUnitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());

        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }

        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(
                        u.getId(),
                        u.getUnitStatus(),
                        u.getPositionType(),
                        u.getPlanId()
                )
        ));

        this.generalWriting(unitTables, fileName);
    }

    private void dumpAdCreativeTable(String fileName) {
        final List<Creative> creatives = creativeRepository.findAll();

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getMaterialType(),
                        c.getHeight(),
                        c.getWidth(),
                        c.getAuditStatus(),
                        c.getUrl()
                )
        ));
        this.generalWriting(creativeTables, fileName);
    }

    private void dumpAdCreativeUnitTable(String fileName) {
        final List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();

        if (CollectionUtil.isEmpty(creativeUnits)) {
            return;
        }

        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));

        this.generalWriting(creativeUnitTables, fileName);
    }

    private void dumpAdUnitDistrictTable(String fileName) {
        final List<AdUnitDistrict> unitDistricts = adUnitDistrictRepository.findAll();

        if (CollectionUtil.isEmpty(unitDistricts)) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(u -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        u.getUnitId(),
                        u.getProvince(),
                        u.getCity()
                )
        ));

        this.generalWriting(unitDistrictTables, fileName);
    }

    private void dumpAdUnitItTable(String fileName) {
        final List<AdUnitIt> unitIts = adUnitItRepository.findAll();

        if (CollectionUtil.isEmpty(unitIts)) {
            return;
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(u -> unitItTables.add(
                new AdUnitItTable(
                        u.getUnitId(),
                        u.getItTag()
                )
        ));
        this.generalWriting(unitItTables, fileName);
    }

    private void dumpAdUnitKeywordTable(String fileName) {
        final List<AdUnitKeyword> unitKeywords = adUnitKeywordRepository.findAll();

        if (CollectionUtil.isEmpty(unitKeywords)) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(u -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        u.getUnitId(),
                        u.getKeyword()
                )
        ));
        this.generalWriting(unitKeywordTables, fileName);
    }

    private <T> void generalWriting(List<T> initList, String fileName) {
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            for (T t : initList) {
                writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
                writer.newLine();
            }
        } catch (IOException exception) {
            log.error("dumpTable error");
        }
    }
}
