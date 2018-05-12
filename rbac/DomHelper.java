package com.wukong.rbac;

import org.w3c.dom.Node;

public class DomHelper {

	public static String getLocalName(Node root){
		String localName=root.getLocalName();
		if(localName==null) return root.getNodeName();
		return localName;
	}
}
