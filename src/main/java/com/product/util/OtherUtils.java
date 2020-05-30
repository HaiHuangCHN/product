package com.product.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.product.costant.ScheduleJobEnum;
import com.product.entity.HousekeepSummary;
import com.product.entity.HousekeepSummary.StatusEnum;
import com.product.exception.ApplicationException;

public class OtherUtils {
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static <T> T toSnakeObject(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T castedJson = mapper.readValue(json, clazz);
        return castedJson;
    }

    public static Map<String, Object> convertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null) {
            return null;
        }
        String[] fieldNames = getFiledName(obj);
        for (int j = 0; j < fieldNames.length; j++) {
            String name = humpToLine(fieldNames[j]);
            Object value = getFieldValueByName(fieldNames[j], obj);
            if (null != value) {
                reMap.put(name, value);
            }

        }
        return reMap;
    }

    private static String[] getFiledName(Object obj) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = obj.getClass();
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        String[] fieldNames = new String[fieldList.size()];
        for (int i = 0; i < fieldList.size(); i++) {
            fieldNames[i] = fieldList.get(i).getName();
        }
        return fieldNames;
    }

    private static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer strBuf = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(strBuf, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(strBuf);
        return strBuf.toString();
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This function is used to encode the sign and signature of the order API
     * 
     * @param str key, str message
     * @return str encode
     * @throws ApplicationException
     */
    public String encodeBase64(String key, String message) throws ApplicationException {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String encode = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
            return encode;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), "Failed to encode sign string");
        }
    }

    /**
     * Generate housekeep result
     * 
     * @param copyTo
     * @param removeFrom
     * @param noOfRecordsUpdated
     * @param status
     * @param statusMessage
     * @param scheduledJobStartedAt
     * @return housekeep instance
     */
    public static HousekeepSummary genHousekeepResult(String copyTo, String removeFrom, long noOfRecordsUpdated, StatusEnum status, String statusMessage,
            LocalDateTime scheduledJobStartedAt) {
        LocalDateTime scheduledJobFinishedAt = TimeUtils.getUtcTime();
        HousekeepSummary housekeepSummary = new HousekeepSummary(HostUtils.getHostName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getJobName(), scheduledJobStartedAt,
                scheduledJobFinishedAt, status, HousekeepSummary.TypeEnum.DATABASE, removeFrom, copyTo, noOfRecordsUpdated, scheduledJobStartedAt, scheduledJobFinishedAt);
        housekeepSummary.setStatusMessage(statusMessage);
        return housekeepSummary;
    }

}
