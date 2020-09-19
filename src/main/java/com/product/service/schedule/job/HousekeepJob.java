package com.product.service.schedule.job;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.product.config.ScheduleJobConfig;
import com.product.entity.User;
import com.product.entity.UserArch;
import com.product.repository.UserArchRepository;
import com.product.repository.UserRepository;

@Service
public class HousekeepJob {

    private long userTotal = 0L, cartTotal = 0L, orderDetailTotal = 0L, productItemTotal = 0L;

    private long userArchTotal = 0L, cartArchTotal = 0L, orderDetailArchTotal = 0L, productItemArchTotal = 0L;

    private long totalDeleted = 0L;

    @Autowired
    private ScheduleJobConfig scheduleJobConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserArchRepository userArchRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void archiveData(LocalDateTime now, Map<String, StringBuilder> resultMap) throws Exception {
        long count = 0L;
        List<User> userListToBeDeleted = null;
        List<UserArch> userArchListInserted = new LinkedList<>();
        UserArch userArch = null;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
        LocalDateTime updatedAt = now.minusHours(scheduleJobConfig.getActiveTableRecordRetentionPeriod());
        userListToBeDeleted = userRepository.findTop10000ByUpdatedAtBefore(updatedAt);

        // step 1: archive records into archive tables
        for (User user : userListToBeDeleted) {
            userArch = modelMapper.map(user, UserArch.class);
            userArchRepository.save(userArch);
//            TODO not work, but why?
//            entityManager.merge(userArch);
//            entityManager.persist(userArch);
            entityManager.flush();
            userArchListInserted.add(userArch);
            count++;
            if (count % 100 == 0) {
                entityManager.clear();
            }
            if (count == 1L) {
                resultMap.put("First userId been processed", new StringBuilder().append(user.getUserId()));
            } else {
                resultMap.put("Last userId been processed", new StringBuilder().append(user.getUserId()));
            }
        }

        if (count == 1L) {
            resultMap.put("Last bookingId been processed", resultMap.get("First bookingId been processed"));
        }

        // step 2: delete records from active tables
        userRepository.deleteAll(userListToBeDeleted);
        entityManager.flush();

        // step 3: verify archived VS inserted
        verifyResult(userListToBeDeleted, userArchListInserted);

        resultMap.put("userTotal", new StringBuilder(String.valueOf(userTotal)));
        resultMap.put("cartTotal", new StringBuilder(String.valueOf(cartTotal)));
        resultMap.put("orderDetailTotal", new StringBuilder(String.valueOf(orderDetailTotal)));
        resultMap.put("productItemTotal", new StringBuilder(String.valueOf(productItemTotal)));
        resultMap.put("userArchTotal", new StringBuilder(String.valueOf(userArchTotal)));
        resultMap.put("cartArchTotal", new StringBuilder(String.valueOf(cartArchTotal)));
        resultMap.put("orderDetailArchTotal", new StringBuilder(String.valueOf(orderDetailArchTotal)));
        resultMap.put("productItemArchTotal", new StringBuilder(String.valueOf(productItemArchTotal)));

        // simulate roll back
//      throw new ErrorResponseException("test", "test", "test");
    }

    /**
     * Verify if records deleted are successfully archived
     * 
     * @param userListDeleted
     * @param userArchListInserted
     * @throws Exception
     */
    private void verifyResult(List<User> userListDeleted, List<UserArch> userArchListInserted) throws Exception {

        userTotal += userListDeleted.size();
        cartTotal += userListDeleted.size();
        for (User user : userListDeleted) {
            orderDetailTotal += user.getOrderDetailList().size();
            productItemTotal += user.getCart().getProductItemList().size();
        }

        userArchTotal += userArchListInserted.size();
        cartArchTotal += userArchListInserted.size();
        for (UserArch userArch : userArchListInserted) {
            orderDetailArchTotal += userArch.getOrderDetailList().size();
            productItemArchTotal += userArch.getCart().getProductItemList().size();
        }

        if (userTotal == userArchTotal && cartTotal == cartArchTotal && orderDetailTotal == orderDetailArchTotal && productItemTotal == productItemArchTotal) {
            totalDeleted = userTotal + cartTotal + orderDetailTotal + productItemTotal;
        } else {
            throw new Exception("Deleted total not match archived total!");
        }

    }

}
