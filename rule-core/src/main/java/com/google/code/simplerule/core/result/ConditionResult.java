package com.google.code.simplerule.core.result;

import java.io.Serializable;

/**
 * @Description:ConditionResult
 * @author:sunny
 * @since:2013-01-30
 * @version:1.0.0
 */
public class ConditionResult implements Serializable {

    private Object connector;

    private Boolean result;

    public ConditionResult(Object connector, Boolean result) {
        this.connector = connector;
        this.result = result;
    }

    public Object getConnector() {
        return connector;
    }

    public void setConnector(Object connector) {
        this.connector = connector;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
