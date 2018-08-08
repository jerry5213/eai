package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class JsonSubSystemEntity {
	private String ParentCode;
	private String ParentCodeType;
	private String CodeType;
	private String EquipmentType;
	private String Language;

	public JsonSubSystemEntity(String parentCode,String parentCodeType, String codeType, String equipmentType,String language) {
		ParentCodeType = parentCodeType;
		ParentCode = parentCode;
		CodeType = codeType;
		EquipmentType = equipmentType;
		Language = language;
	}
}
