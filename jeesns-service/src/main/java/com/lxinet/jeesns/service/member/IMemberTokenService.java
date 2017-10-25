package com.lxinet.jeesns.service.member;


import com.lxinet.jeesns.model.member.MemberToken;


public interface IMemberTokenService {

    MemberToken getByToken(String token);

    void save(Integer memberId,String token);

}