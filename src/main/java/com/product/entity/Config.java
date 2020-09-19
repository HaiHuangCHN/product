package com.product.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config", schema = "product")
public class Config extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int configId;

    @Enumerated(EnumType.STRING)
    private EnvEnum env;

    @Enumerated(EnumType.STRING)
    private CatagoryEnum catagory;

    private String desc;

    private String configName;

    private String configValue;

    private Config() {
    }

    public static enum EnvEnum {
        D0, T0, P0;
        private EnvEnum() {
        }
    }

    public static enum CatagoryEnum {
        GENERAL, SPRING, RESTCLIENT, RABBITMQ, SWAGGER, QUARTZ, SCHEDULE_JOB;
        private CatagoryEnum() {
        }
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public EnvEnum getEnv() {
        return env;
    }

    public void setEnv(EnvEnum env) {
        this.env = env;
    }

    public CatagoryEnum getScope() {
        return catagory;
    }

    public void setScope(CatagoryEnum catagory) {
        this.catagory = catagory;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Override
    public String toString() {
        return "Config [cofigId=" + configId + ", env=" + env + ", catagory=" + catagory + ", desc=" + desc + ", configName=" + configName + ", configValue=" + configValue
                + ", createdAt()=" + getCreatedAt() + ", updatedAt()=" + getUpdatedAt() + "]";
    }

}
