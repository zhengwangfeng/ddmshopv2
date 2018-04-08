package com.pc.security.config;

/**
 * @author XY
 */
public class Constant {

    /**
     *
     */
    /**
     *
     */
    public static final int RESCODE_SUCCESS = 1000;
    public static final int RESCODE_SUCCESS_DATA = 1001;        //
    public static final int RESCODE_NOEXIST = 1004;                //
    /**
     *
     */
    public static final int RESCODE_EXCEPTION = 1002;            //
    public static final int RESCODE_EXCEPTION_DATA = 1008;        //
    public static final int RESCODE_NOLOGIN = 1003;                //
    public static final int RESCODE_NOAUTH = 1005;                //
    public static final int RESCODE_LOGINEXPIRE = 1009;            //
    /**
     *
     */
    public static final int RESCODE_REFTOKEN_MSG = 1006;        //
    public static final int RESCODE_REFTOKEN = 1007;            //

    public static final int JWT_ERRCODE_EXPIRE = 4001;            //
    public static final int JWT_ERRCODE_FAIL = 4002;            //

    /**
     * jwt
     */
    public static final String JWT_ID = "3D58C";                                        //
    public static final String JWT_SECERT = "4028802560d4562d0160d4562d9e0000";            //
    public static final long JWT_TTL = 7 * 24 * 60 * 1000;//

    public static final long JWT_APPLET_TTL = 2 * 60 * 1000;//

}
