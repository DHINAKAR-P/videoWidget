package com.npb.gb.utils;

import java.util.ArrayList;
import java.util.List;

public class GpRelationshipInfo {
	private List<GpChildRelationshipInfo> childs;

	public List<GpChildRelationshipInfo> getChilds() {
		return childs;
	}
	
	public void add_child(GpChildRelationshipInfo child_info){
		if(childs == null)
			childs = new ArrayList<>();
		childs.add(child_info);
	}
}
