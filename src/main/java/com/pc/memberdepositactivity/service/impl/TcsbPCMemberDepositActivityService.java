package com.pc.memberdepositactivity.service.impl;

import com.pc.memberdepositactivity.service.TcsbPCMemberDepositActivityServiceI;
import com.tcsb.memberdepositactivity.entity.TcsbMemberDepositActivityEntity;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("tcsbPCMemberDepositActivityService")
@Transactional
public class TcsbPCMemberDepositActivityService extends CommonServiceImpl implements TcsbPCMemberDepositActivityServiceI {
    @Override
    public void delete(TcsbMemberDepositActivityEntity entity) throws Exception {

    }

    @Override
    public Serializable save(TcsbMemberDepositActivityEntity entity) throws Exception {
        return null;
    }

    @Override
    public void saveOrUpdate(TcsbMemberDepositActivityEntity entity) throws Exception {

    }
}
