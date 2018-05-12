package com.wukong.rbac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PolicyFinder {

	private Map<String,Policy> policies;
	
	private Set<String> policyLocation;
	
	public PolicyFinder(){
		policies=new HashMap();
		policyLocation=new HashSet();
	}
	public PolicyFinder(Set<String> location){
		policyLocation=location;
		policies=new HashMap();
	}
	public void init(){
		loadPolicies();
	}
	
	public Policy getRolePolicy(String role){
		Set<String> set=policies.keySet();
		if(set.contains(role))
			return policies.get(role);
		return null;
	}
	private void loadPolicies(){
		policies.clear();
		for(String location:policyLocation){
			File file=new File(location);
			if(!file.exists())
				continue;
			if(file.isDirectory()){
				String[] files=file.list();
				if(files!=null){
					for(String each:files){
						File policyFile=new File(location+File.separator+each);
						if(!policyFile.isDirectory()&&!policyFile.isHidden())
							loadPolicy(location+File.separator+each);
					}
				}
			}else{
				loadPolicy(location);
			}
		}
	}
	
	private void loadPolicy(String fileLocation){
		String policyId=null;
		Policy policy=null;
		InputStream stream=null;
		try{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			 factory.setIgnoringComments(true);
	         factory.setNamespaceAware(true);
	         factory.setValidating(false);
	         DocumentBuilder db=factory.newDocumentBuilder();
	         stream=new FileInputStream(fileLocation);
	         Document doc=db.parse(stream);
	         Element root=doc.getDocumentElement();
	         String name=DomHelper.getLocalName(root);
	         if(name.equals("Policy"))
	        	 policy=Policy.getInstance(root);
	         policyId=root.getAttribute("PolicyId");
		}catch(Exception e){
			System.out.println("wrong with parsing policy"+policyId);
		}finally{
			if(stream!=null){
				try{
					stream.close();
					}catch (IOException e){
						System.out.println("wrong with closing IO");
					}
			}
		}
		policies.put(policyId, policy);
	}
}
