package com.healthbank.classes;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	public Object parent;
	public List<Object> childs = new ArrayList<Object>();
	//String[] subchildsStrings;
	public List<DBTimeSlot> subchilds;
	
	public TreeNode(Object parent, List<Object> childs , List<DBTimeSlot> subchilds)
	{	
		// TODO Auto-generated constructor stub
		this.parent=parent;
		this.childs=childs;
		this.subchilds=subchilds;
	}
public TreeNode(){}
	
}
