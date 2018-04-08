package com.pc.memberdepositactivity.service;

import com.tcsb.memberdepositactivity.entity.TcsbMemberDepositActivityEntity;

import java.io.Serializable;

public interface TcsbPCMemberDepositActivityServiceI {
    void delete(TcsbMemberDepositActivityEntity entity) throws Exception;

    Serializable save(TcsbMemberDepositActivityEntity entity) throws Exception;

    void saveOrUpdate(TcsbMemberDepositActivityEntity entity) throws Exception;
}
