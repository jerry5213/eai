package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class JsonEvaluateEntity {

    private String AutoGuid;
    private String E_WLDHSJ;
    private String E_ZGWCSJ;
    private String E_JJQK;
    private String E_MYDDF;
    private String E_XXPJ;

    public JsonEvaluateEntity(String autoGuid, String e_WLDHSJ, String e_ZGWCSJ, String e_JJQK, String e_MYDDF, String e_XXPJ) {

        AutoGuid = autoGuid;
        E_WLDHSJ = e_WLDHSJ;
        E_ZGWCSJ = e_ZGWCSJ;
        E_JJQK = e_JJQK;
        E_MYDDF = e_MYDDF;
        E_XXPJ = e_XXPJ;
    }
}
