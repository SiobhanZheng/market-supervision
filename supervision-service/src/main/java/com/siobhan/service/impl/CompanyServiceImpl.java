package com.siobhan.service.impl;

import com.siobhan.entity.CompanyDO;
import com.siobhan.proxy.dto.PageDTO;
import com.siobhan.repository.CompanyRepository;
import com.siobhan.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by siobhan.zheng on 2019/3/28
 */
@Service
public class CompanyServiceImpl  implements CompanyService{
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Page<CompanyDO> getAllCompany(PageDTO pageDTO) {
        PageRequest pageRequest = null;
        String searchName = "";
        if (pageDTO != null){
           pageRequest = new PageRequest(pageDTO.getPage() - 1, pageDTO.getSize());
            searchName = pageDTO.getSearchName();
        }
        if (searchName != null && !"".equals(searchName)){
            return companyRepository.findByCompanyNameLike("%" + searchName + "%", pageRequest);
        } else {
            return companyRepository.findAll(pageRequest);
        }
    }

}
