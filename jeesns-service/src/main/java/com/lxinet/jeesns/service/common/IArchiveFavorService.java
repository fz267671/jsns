package com.lxinet.jeesns.service.common;


import com.lxinet.jeesns.model.common.ArchiveFavor;

/**
 * 文章点赞Service接口
 */
public interface IArchiveFavorService {

    ArchiveFavor find(Integer archiveId, Integer memberId);

    void save(Integer archiveId, Integer memberId);

    void delete(Integer archiveId, Integer memberId);
}