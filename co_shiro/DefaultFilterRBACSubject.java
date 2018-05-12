package co_shiro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;
import com.wukong.rbac.Context;
import com.wukong.rbac.RoleSecurityManager;
import com.wukong.rbac.RoleSecurityManagerImp;

public class DefaultFilterRBACSubject extends WebDelegatingSubject implements FilterRBACSubject{

	public DefaultFilterRBACSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
			boolean sessionEnabled, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
		super(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void AddActiveRole(String roleIdentifier) {
		System.out.println("¼¤»î½ÇÉ«");
		FilterRBACSubject subject = (FilterRBACSubject) SecurityUtils.getSubject();
		Session session=subject.getSession();
		session.setAttribute("ActivatedRoles", roleIdentifier);
		// TODO Auto-generated method stub
		
	}
	
	public boolean hasRole(String roleIdentifier){
		FilterRBACSubject subject = (FilterRBACSubject) SecurityUtils.getSubject();
		Session session=subject.getSession();
		String ActivatedRoles=(String) session.getAttribute("ActivatedRoles");
		if(roleIdentifier==ActivatedRoles)
			return true;
		return false;
	}

	@Override
	public void ChangeAttributeValue(String attributeId, String attributeValue) throws UnknownIdentifierException, ParsingException {
		FilterRBACSubject subject = (FilterRBACSubject) SecurityUtils.getSubject();
		Session session=subject.getSession();
		Context context=(Context) session.getAttribute("context");
		context.Change(attributeId, attributeValue);
		Set<String> filteredroles=(Set<String>) session.getAttribute("FilteredRoles");
		if(filteredroles==null)
			filteredroles=new HashSet();
		RoleSecurityManager manager = new RoleSecurityManagerImp();
		Set<String> assignedroles=(Set<String>) session.getAttribute("AssignedRoles");
		
		Iterator it=assignedroles.iterator();
		while(it.hasNext()){
			String rolename=(String) it.next();
			boolean result = manager.evaluate(rolename, context);
			if(result==true)
				filteredroles.add(rolename);
			else if(filteredroles.contains(rolename))
				filteredroles.remove(rolename);
		}
		session.setAttribute("FilteredRoles", filteredroles);
		
	}

	@Override
	public Set<String> AssignedRoles() {
		FilterRBACSubject subject = (FilterRBACSubject) SecurityUtils.getSubject();
		Session session=subject.getSession();
		Set<String> assignedroles=(Set<String>) session.getAttribute("AssignedRoles");
		return assignedroles;
	}

	@Override
	public Set<String> FilteredRoles() {
		FilterRBACSubject subject = (FilterRBACSubject) SecurityUtils.getSubject();
		Session session=subject.getSession();
		Set<String> filteredroles=(Set<String>) session.getAttribute("FilteredRoles");
		return filteredroles;
	}
	
}