package com.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.product.model.request.LoginReq;
import com.product.service.schedule.job.ScheduleJob;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(path = "/common")
@Api(tags = "Common API")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    ScheduleJob scheduleJob;

    @RequestMapping(value = "/login/page", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpSession session, LoginReq loginReq) {
        logger.info(request.getRequestURL().toString() + ", method: " + request.getMethod() + loginReq.toString());
        return "login";
    }

    @RequestMapping(value = "/test/archive/data", method = RequestMethod.GET)
    public ResponseEntity<?> test() throws Exception {
        scheduleJob.executeArchiveDatabaseRecordJob();
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

}
