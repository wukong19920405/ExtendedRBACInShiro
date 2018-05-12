package com.wukong.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Status {
	
	

	public static final String STATUS_OK="urn:oasis:names:tc:xacml:1.0:status:ok";
	
	public static final String STATUS_MISSING_ATTRIBUTE="urn:oasis:names:tc:xacml:1.0:status:missing-attribute";
	
	public static final String STATUS_SYNTAX_ERROR = "urn:oasis:names:tc:xacml:1.0:status:syntax-error";

	public static final String STATUS_PROCESSING_ERROR = "urn:oasis:names:tc:xacml:1.0:status:processing-error";

	private List<String> code;
	
	private StatusDetail detail;
	
	private String message;//???
	
	private static Status okStatus;
	
	static{
		List<String> code=new ArrayList<String>();
		code.add(STATUS_OK);
		okStatus=new Status(code);
	}
	
	public Status(List<String> code) {
		this(code,null,null);
	}
	
	public Status(List<String> code,String message){
		this(code,null,message);
	}
	
	public Status(List<String> code,StatusDetail detail,String message){
		if(detail!=null){
			java.util.Iterator<String> it=code.iterator();
			while(it.hasNext()){
				String tmp=it.next();
				if(tmp.equals(STATUS_OK)||tmp.equals(STATUS_SYNTAX_ERROR)||tmp.equals(STATUS_PROCESSING_ERROR))
					throw new IllegalArgumentException("status detail cannot be " + "included with "+tmp);
			}
		}
		this.detail=detail;
		this.code=Collections.unmodifiableList(new ArrayList(code));
		this.message=message;
	}
	
	List<String> getCode(){
		return code;
	}
	
	String getMessage(){
		return message;
	}
	
	public Status getOkInstance(){
		return okStatus;
	}
}
