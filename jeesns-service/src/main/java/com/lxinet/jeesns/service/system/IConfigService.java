package com.lxinet.jeesns.service.system;

import com.lxinet.jeesns.core.dto.ResponseModel;
import com.lxinet.jeesns.model.system.Config;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface IConfigService {
    List<Config> allList();

    Map<String,String> getConfigToMap();

    String getValue(String key);

    ResponseModel update(Map<String,String> params, HttpServletRequest request);
}
