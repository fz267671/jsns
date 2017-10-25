package com.lxinet.jeesns.dao.common;

import com.lxinet.jeesns.model.common.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPictureDao extends IBaseDao<Picture> {

    List<Picture> find(@Param("id") Integer foreignId);

    int delete(@Param("id") Integer foreignId);

    int update(@Param("foreignId") Integer foreignId, @Param("ids") String[] ids);
}