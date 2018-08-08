package com.zxtech.ecs.model;

public class JsonSystemCodeSXMSEntity {

	private String ZXT;
	private String ZJ;
	private String BJ;
	private String LJ;
	private String EquipmentType;
	private String Language;

	public JsonSystemCodeSXMSEntity(String ZXT, String ZJ, String BJ, String LJ, String equipmentType, String language) {
		this.ZXT = ZXT;
		this.ZJ = ZJ;
		this.BJ = BJ;
		this.LJ = LJ;
		EquipmentType = equipmentType;
		Language = language;
	}
}
