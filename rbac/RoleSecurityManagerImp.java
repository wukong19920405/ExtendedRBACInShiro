package com.wukong.rbac;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wukong.context.Attribute;
import com.wukong.context.Attributes;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationCtxImp;
import com.wukong.context.RequestCtx;

public class RoleSecurityManagerImp implements RoleSecurityManager{
	
	public PolicyFinder finder=null;
	
	public RequestCtx req=null;
	
	public RoleSecurityManagerImp(){
		Set<String> policyLocations=new HashSet();
		Set<String> attributeLocations=new HashSet();
		policyLocations.add("C:\\Users\\Yuan\\Desktop\\policy.xml");
		attributeLocations.add("C:\\Users\\Yuan\\Desktop\\attribute.xml");
		finder=new PolicyFinder(policyLocations);
		req=new RequestCtx(attributeLocations);
		finder.init();
		req.init();
	}

	@Override
	public boolean evaluate(String roleName,Context context) {
		EvaluationCtx eva=null;
		boolean result=false;
		Set<Attributes> attributes=context.getRoleAttributes(roleName);
		eva=new EvaluationCtxImp(attributes);
		try{
		result=finder.getRolePolicy(roleName).evaluate(eva);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}

	@Override
	public Set<Attributes> getRoleAttributes(String role) {
		return req.getRoleAttributes(role);
	}

	public Context getContext(Set<String> roleNames){
		Map<String,Set<Attributes>> attributes=new HashMap();
		Iterator it=roleNames.iterator();
		while(it.hasNext()){
			String name=(String) it.next();
			Set<Attributes> tmp=req.getRoleAttributes(name);
			Set<Attributes> value=new HashSet();
			if(tmp!=null){
				
				Iterator ite=tmp.iterator();
				while(ite.hasNext()){
					
					try{
						ByteArrayOutputStream out=new ByteArrayOutputStream();
						ObjectOutputStream ous=new ObjectOutputStream(out);
						ous.writeObject((Attributes)ite.next());
						ous.close();
						
						ByteArrayInputStream in=new ByteArrayInputStream(out.toByteArray());
						ObjectInputStream ois=new ObjectInputStream(in);
						value.add((Attributes)ois.readObject());
						ois.close();
					}catch(Exception e){
						e.printStackTrace(); 
					}
				}
			}
			attributes.put(name, value);
		}
		
		return new ContextImp(attributes);
	}
}
