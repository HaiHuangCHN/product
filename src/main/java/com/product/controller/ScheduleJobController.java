package com.product.controller;

import org.apache.http.HttpStatus;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.costant.Constants;
import com.product.model.commom.ScheduleJobModel;
import com.product.service.schedule.job.ScheduleJobManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/schedule/job")
@Api(tags = "API to control schedule job")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobManager productScheduleJobManager;

    /**
     * Update schedule job
     * 
     * @param model
     * @return Success if no error
     */
    @PostMapping("update")
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = String.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR) })
    public ResponseEntity<?> scheduleUpdateCorn(@RequestBody ScheduleJobModel model) {
        productScheduleJobManager.scheduleUpdateCorn(model);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    /**
     * Pause schedule job
     * 
     * @param model
     * @return Success if no error
     */
    @PostMapping("/pause")
    public ResponseEntity<String> schedulePause(@RequestBody ScheduleJobModel model) {
        productScheduleJobManager.schedulePause(model);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    /**
     * Resume schedule job
     * 
     * @param model
     * @return Success if no error
     */
    @PostMapping("/resume")
    public ResponseEntity<String> scheduleResume(@RequestBody ScheduleJobModel model) {
        productScheduleJobManager.scheduleResume(model);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    /**
     * Delete one schedule job
     * 
     * @param model
     * @return Success if no error
     */
    @PostMapping("/delete")
    public ResponseEntity<String> scheduleDelete(@RequestBody ScheduleJobModel model) {
        productScheduleJobManager.scheduleDelete(model);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    /**
     * Delete ALL schedule jobs
     * 
     * @return Success if no error
     */
    @PostMapping("/deleteAll")
    public ResponseEntity<String> scheduleDeleteAll() {
        productScheduleJobManager.scheduleDeleteAll();
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    /**
     * Start scheduler
     * 
     * @return Success if no error
     * @throws SchedulerException
     */
    @PostMapping("/start/scheduler")
    public ResponseEntity<String> startScheduler() throws SchedulerException {
        productScheduleJobManager.startScheduler();
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    /**
     * Shutdown scheduler
     * 
     * @return Success if no error
     * @throws SchedulerException
     */
    @PostMapping("/shut/down/scheduler")
    public ResponseEntity<String> shutDownScheduler() throws SchedulerException {
        productScheduleJobManager.shutDownScheduler();
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

}