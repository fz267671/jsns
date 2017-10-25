package com.lxinet.jeesns.service.common;

import com.lxinet.jeesns.model.common.Archive;
import com.lxinet.jeesns.core.dto.ResponseModel;
import com.lxinet.jeesns.model.member.Member;


public interface IArchiveService {

    Archive findByArchiveId(int id);

    boolean save(Member member, Archive archive);

    boolean update(Member member, Archive archive);

    boolean delete(int id);

    void updateViewCount(int id);

    ResponseModel favor(Member loginMember, int archiveId);
}
