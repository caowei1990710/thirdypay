package com.example.demo.Repository;

import com.example.demo.Model.PayProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by snsoft on 4/7/2019.
 */
public interface PayProposalRespositpory extends JpaRepository<PayProposal, Integer>, JpaSpecificationExecutor<PayProposal> {

    @Query(value = "select * from pay_proposal where remark='default' ORDER BY create_time DESC limit 1", nativeQuery = true)
    PayProposal getPlatformDeposit();

    @Query(value = "select * from pay_proposal where proposal_id=?1 ", nativeQuery = true)
    PayProposal getPlatformDepositbydepositNumber(String proposalId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update  pay_proposal p set p.remark=?1 where p.proposal_id =?2", nativeQuery = true)
    int updatePlatformDeposit(String remark, String proposalId);
}
