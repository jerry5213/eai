package com.zxtech.is.model.project;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class JsonSubSystemEntity {
    private String ParentCode;
    private String CodeType;
    private String ElevatorType;

    public JsonSubSystemEntity(String parentCode, String codeType, String elevatorType) {
        ParentCode = parentCode;
        CodeType = codeType;
        ElevatorType = elevatorType;
    }
}
