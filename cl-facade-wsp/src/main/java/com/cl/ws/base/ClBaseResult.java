package com.cl.ws.base;

import com.cl.common.lang.util.StringUtil;

/**
 *
 * @Filename ClBaseResult.java
 *
 * @Description
 *
 * @Version 1.0
 *
 */
public class ClBaseResult extends ResultBase {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 5156892170604621621L;
    /** 返回结果码 */
    ClResultEnum microbankResultEnum = ClResultEnum.UN_KNOWN_EXCEPTION;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isExecuted() {

        return ClResultEnum.EXECUTE_SUCCESS == microbankResultEnum ? true : false;
    }

    public ClResultEnum getCreditsysResultEnum() {
        return microbankResultEnum;
    }

    public void setCreditsysResultEnum(ClResultEnum esupplierResultEnum) {
        this.microbankResultEnum = esupplierResultEnum;
        if (this.microbankResultEnum != null) {
            if (StringUtil.isEmpty(this.getMessage())) {
                this.setMessage(this.microbankResultEnum.getMessage());
            }

        }
    }

    @Override
    public void setSuccess(boolean success) {
        super.setSuccess(success);
        if (success)
            microbankResultEnum = ClResultEnum.EXECUTE_SUCCESS;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClBaseResult [estateResultEnum=");
        builder.append(microbankResultEnum);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }

}
