package com.sarielx.study.xr.controller;

import com.sarielx.study.xr.entity.Support;
import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("support")
public class SupportController {
    @Autowired
    SupportService supportService;

    @CheckToken
    @RequestMapping(value = "dosupport",method = RequestMethod.POST)
    private Map<String, Object> doSupport(@RequestBody Support support){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("sucess",supportService.doSupport(support));
        return modelMap;
    }

}
