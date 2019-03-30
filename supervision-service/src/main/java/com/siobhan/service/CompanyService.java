package com.siobhan.service;

import com.siobhan.entity.CompanyDO;
import com.siobhan.proxy.dto.PageDTO;
import org.springframework.data.domain.Page;

/**
 * Created by siobhan.zheng on 2019/3/28.
 */
public interface CompanyService {
    /**
     * 分页查询所有的公司
     * @param pageDTO
     * @return
     */
    Page<CompanyDO> getAllCompany(PageDTO pageDTO);
}
