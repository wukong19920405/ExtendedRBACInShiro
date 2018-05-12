package co_shiro;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubjectContext;

import com.wukong.rbac.Context;
import com.wukong.rbac.RoleSecurityManager;
import com.wukong.rbac.RoleSecurityManagerImp;

public class DefaultFilterRBACSubjectFactory extends DefaultWebSubjectFactory {

	public DefaultFilterRBACSubjectFactory() {
		super();
	}

	public Subject createSubject(SubjectContext context) {// 需要完善我们改进模型的subjectcontext

		if (!(context instanceof WebSubjectContext)) {
			return super.createSubject(context);
			}
		WebSubjectContext wsc = (WebSubjectContext) context;
		SecurityManager securityManager = wsc.resolveSecurityManager();
		Session session = wsc.resolveSession();
		
		
		
		boolean sessionEnabled = wsc.isSessionCreationEnabled();
		PrincipalCollection principals = wsc.resolvePrincipals();
		boolean authenticated = wsc.resolveAuthenticated();
		String host = wsc.resolveHost();
		ServletRequest request = wsc.resolveServletRequest();
		ServletResponse response = wsc.resolveServletResponse();
		Subject subject=new DefaultFilterRBACSubject(principals, authenticated, host, session, sessionEnabled,request, response, securityManager);
		Session session1=subject.getSession();
		
		RoleSecurityManager manager = new RoleSecurityManagerImp();
		Set<String> roleNames = new HashSet();
		roleNames.add("User");
		Context environmentcontext = manager.getContext(roleNames);
		session1.setAttribute("AssignedRoles", roleNames);
		session1.setAttribute("context", environmentcontext);
		return new DefaultFilterRBACSubject(principals, authenticated, host, session, sessionEnabled,request, response, securityManager);
	}
}