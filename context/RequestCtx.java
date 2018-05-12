package com.wukong.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.attribute.AttributeValue;
import com.wukong.exception.ParsingException;
import com.wukong.rbac.Condition;
import com.wukong.rbac.DomHelper;
import com.wukong.rbac.Policy;
import com.wukong.rbac.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class RequestCtx {
	
	private Set<String> locations;
	
	private Map<String,Set<Attributes>> attrs;
	
	public RequestCtx(Set<String> locations){
		this.locations=locations;
		attrs=new HashMap();
	}
	
	public void init(){
		loadReqCtx();
	}
	public Set<Attributes> getAttributes(){
		Set<Attributes> result=new HashSet<Attributes>();
		for(Map.Entry<String,Set<Attributes>> entry:attrs.entrySet()){
			result.addAll(entry.getValue());
		}
		return result;
	}
	
	public Set<Attributes> getRoleAttributes(String role){
		Set<String> set=attrs.keySet();
		if(set.contains(role))
			return attrs.get(role);
		return null;
	}
	private void loadReqCtx(){
		attrs.clear();
		for(String location:locations){
			File file=new File(location);
			if(!file.exists())
				continue;
			if(file.isDirectory()){
				String[] files=file.list();
				if(files!=null){
					for(String each:files){
						File policyFile=new File(location+File.separator+each);
						if(!policyFile.isDirectory()&&!policyFile.isHidden())
							loadReq(location+File.separator+each);
					}
				}
			}else{
				loadReq(location);
			}
		}
	}
	
	private void loadReq(String location){
		String roleName=null;
		Set<Attributes> attributes=new HashSet<Attributes>();
		InputStream stream=null;
		try{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			 factory.setIgnoringComments(true);
	         factory.setNamespaceAware(true);
	         factory.setValidating(false);
	         DocumentBuilder db=factory.newDocumentBuilder();
	         stream=new FileInputStream(location);
	         Document doc=db.parse(stream);
	         Element root=doc.getDocumentElement();
	         String name=DomHelper.getLocalName(root);
	         if(name.equals("ContextAttribute")){
	        	 roleName=root.getAttribute("RoleName");
	        	 if(roleName==null)
	        		 throw new ParsingException("RoleName cann't be null");
	        	 NodeList children=root.getChildNodes();
	        	 for(int i=0;i<children.getLength();++i){
	        		 Attributes attrs=null;
	        		 Node node=children.item(i);
	        		 String name1=DomHelper.getLocalName(node);
	        		 if(name1.equals("Attributes"))
	        			 {
	        			 attrs=Attributes.getInstance(node);
	        			 attributes.add(attrs);
	        		 }
	 		 }
	 		 }
		 }catch(Exception e){
			 System.out.println("wrong with parsing policy"+e.getMessage());
		 }finally{
			 if(stream!=null){
				 try{
					 stream.close();
					 }catch (IOException e){
						 System.out.println("wrong with closing IO");
					 }
			}
		}
		attrs.put(roleName, attributes);
	}
}

