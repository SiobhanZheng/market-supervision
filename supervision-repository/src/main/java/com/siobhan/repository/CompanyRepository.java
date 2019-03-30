package com.siobhan.repository;

import com.siobhan.entity.CompanyDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by siobhan.zheng on 2019/3/27
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyDO,Long>{

    CompanyDO findByCompanyUrlAndStatus(String companyUrl, String status);

    /**
     * 根据公司名称模糊查询
     * @param companyName
     * @return
     */
    Page<CompanyDO> findByCompanyNameLike(String companyName, Pageable pageable);


}
