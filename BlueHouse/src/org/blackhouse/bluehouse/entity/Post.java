package org.blackhouse.bluehouse.entity;

import java.io.Serializable;

/**
 * Ьћзг
 * 
 * @author leo
 * 
 */
public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	private String enabled;
	private String title;
	private String last_comment_time;
	private String status;
	private String created;
	private String modified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLast_comment_time() {
		return last_comment_time;
	}

	public void setLast_comment_time(String last_comment_time) {
		this.last_comment_time = last_comment_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

}
