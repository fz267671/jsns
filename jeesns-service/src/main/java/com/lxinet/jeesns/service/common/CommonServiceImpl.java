package com.lxinet.jeesns.service.common;

import com.lxinet.jeesns.dao.common.ICommonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("commonService")
public class CommonServiceImpl implements ICommonService {
    @Resource
    private ICommonDao commonDao;

    @Override
    public String getMysqlVsesion() {
        return commonDao.getMysqlVsesion();
    }
}
