package com.wukong.rbac;

import java.util.Set;

import com.wukong.context.Attributes;
import com.wukong.context.EvaluationCtx;

public interface RoleSecurityManager {

	public boolean evaluate(String roleName,Context context) ;
	
	public Set<Attributes> getRoleAttributes(String role);
	
	public Context getContext(Set<String> roleNames);
}
