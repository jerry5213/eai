package com.zxtech.ecs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/1/2.
 */

public class ChatMessage {

	private String content;
	private String id;
	private String message;
	private int type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private List<String> categorys = new ArrayList<>();

	private List<Options> options = new ArrayList<>();

	private List<Map<String,String>> knowledges = new ArrayList<>();

	private Map<String,String> dataMap = new HashMap<>();

	public static final int QUESTION = 1;
	public static final int ANSWER = 2;
	public static final int KNOWLEDGE = 3;
	public static final int FEEDBACKLIST = 6;
	public static final int TRACKING = 7;
	public static final int RECOMMEND = 4;
	public static final int IMAGE = 5;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Options> getOptions() {
		return options;
	}

	public void setOptionss(List<Options> options) {
		this.options = options;
	}

	public ChatMessage(String content, int type) {
		this.content = content;
		this.type = type;
	}

	public ChatMessage(String content, int type, List<String> categorys) {
		this.content = content;
		this.type = type;
		this.categorys = categorys;
	}

	public List<String> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<String> categorys) {
		this.categorys = categorys;
	}

	public List<Map<String,String>> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<Map<String,String>> knowledges) {
		this.knowledges = knowledges;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public static class Options {

		private String price;
		private String id;
		private String type;
		private String text;
		private String url;

		private int drawable;

		public Options(String price) {
			this.price = price;
		}

		public Options(String price, String id, String type, String text, String url) {
			this.price = price;
			this.id = id;
			this.type = type;
			this.text = text;
			this.url = url;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public int getDrawable() {
			return drawable;
		}

		public void setDrawable(int drawable) {
			this.drawable = drawable;
		}

		public Options() {
		}

		public Options(String price, int drawable) {
			this.price = price;
			this.drawable = drawable;
		}
	}

}
