package com.google.code.simplerule.proxy.risk.entity.common;

import java.util.ArrayList;
import java.util.List;

public class ComboTreeEntity {
 private Long id;
 private String text;
 private List<ComboTreeEntity> children = new ArrayList<ComboTreeEntity>();

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public List<ComboTreeEntity> getChildren() {
	return children;
}
public void setChildren(List<ComboTreeEntity> children) {
	this.children.addAll(children);
}
 
}
