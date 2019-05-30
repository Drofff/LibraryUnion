package com.appserve.Library.repository;

import com.appserve.Library.entity.BlockedIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedIpRepository extends JpaRepository<BlockedIp, Long> {

    BlockedIp findByIp(String ip);

}
