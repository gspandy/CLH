package com.cl.ws.base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Filename ClResultEnum.java
 *
 * @Description
 *
 * @Version 1.0
 *
 *
 */
public enum ClResultEnum {

    /** 未知异常 */
    UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),
    /** 请求参数不完整 */
    INCOMPLETE_REQ_PARAM("INCOMPLETE_REQ_PARAM", "请求参数不完整"),
    /** 数据库异常 */
    DATABASE_EXCEPTION("DATABASE_EXCEPTION", "数据库异常"),
    /** 没有数据 */
    HAVE_NOT_DATA("HAVE_NOT_DATA", "没有数据"),

    /** 该用户对该数据无访问权限 */
    NO_ACCESS("NO_ACCESS", "该用户对该数据无访问权限"),
    /** 执行OPENAPI失败 */
    OPENAPI_ACCESS_FAILURE("OPENAPI_ACCESS_FAILURE", "执行OPENAPI失败"),
    /** OPENAPI重复回执 */
    OPENAPI_REPEAT_NOTIFY("OPENAPI_ACCESS_FAILURE", "OPENAPI重复回执"),
    /** 数据处理状态不对或已经处理完成 */
    DO_ACTION_STATUS_ERROR("DO_ACTION_STATUS_ERROR", "数据处理状态不对或已经处理完成"),

    /** 签名不正确 */
    ILLEGAL_SIGN("ILLEGAL_SIGN", "签名不正确"),

    /** 冻结异常 */
    FREEZE_ERROR("FREEZE_ERROR", "冻结异常"),

    /** 余额不足 */
    NO_BALANCE("NO_BALANCE", "余额不足"),

    /** 资金账户未开通 */

    FUNDING_ACCOUNT_NO_OPEN("FUNDING_ACCOUNT_NO_OPEN", "资金账户未开通"),

    /** 快捷投资-投资已满 */
    INVEST_FULL("INVEST_FULL", "投资已满"),

    /** 快捷投资-投资已满 */
    TOO_EARLY("TOO_EARLY", "未到投资时间"),

    /** 贷款超限制 */
    TOO_MUCH("TOO_MUCH", "贷款超限制"),
    /** 未实名认证 */
    NO_REAL_NAME("NO_REAL_NAME", "未实名认证"),

    /** 密码错误 */
    PASSWORD_ERROR("PASSWORD_ERROR", "密码错误"),
    /** 用户冻结 */
    USER_DISABLE("USER_DISABLE", "用户冻结"),

    /** 功能为开通 */
    FUNCTION_NOT_OPEN("FUNCTION_NOT_OPEN", "功能未开通"),

    /** 执行成功 */
    EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),

    /** 运行脚本出错 */
    RUN_JAVCSCRIPT_ERROR("RUN_JAVCSCRIPT_ERROR", "运行脚本出错"),
    /** 用户已经存在 */
    USER_EXIST("USER_EXIST", "用户已经存在"),
    /** 不存在该用户 */
    USER_NOEXIST("USER_EXIST", "不存在该用户"),
    /** 已开通供应商 */
    HAVE_SUPPLIER("HAVE_SUPPLIER", "已开通供应商"),

    /** 交易状态未完成 */
    TRADE_STATUS_NO_FINISHED("TRADE_STATUS_NO_FINISHED", "交易状态未完成"),

    INVENTORY_SHORTAGE("INVENTORY_SHORTAGE", "库存不足"),

    /** 执行失败 */
    EXECUTE_FAILURE("EXECUTE_FAILURE", "执行失败"),
    PAY_SUCCESS("PAY_SUCCESS", "支付成功");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>EstateResultEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private ClResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return EstateResultEnum
     */
    public static ClResultEnum getByCode(String code) {
        for (ClResultEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<EstateResultEnum>
     */
    public List<ClResultEnum> getAllEnum() {
        List<ClResultEnum> list = new ArrayList<ClResultEnum>();
        for (ClResultEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public List<String> getAllEnumCode() {
        List<String> list = new ArrayList<String>();
        for (ClResultEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
