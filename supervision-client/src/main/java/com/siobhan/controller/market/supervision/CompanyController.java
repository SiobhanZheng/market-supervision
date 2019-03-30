package com.siobhan.controller.market.supervision;

import com.siobhan.proxy.MyResponse;
import com.siobhan.proxy.dto.PageDTO;
import com.siobhan.proxy.dto.UserDTO;
import com.siobhan.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by siobhan.zheng on 2019/3/28
 */
@RestController
@RequestMapping("/supervision")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping("/getAllCompany")
    public MyResponse getAllCompany(@RequestBody PageDTO pageDTO, HttpServletRequest request){
        String userCode = ((UserDTO)request.getSession().getAttribute("user")).getUserCode();
        return new MyResponse("1",userCode,companyService.getAllCompany(pageDTO));
    }
}
