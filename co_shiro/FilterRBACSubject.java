package co_shiro;

import java.util.Set;

import org.apache.shiro.web.subject.WebSubject;

import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;

public interface FilterRBACSubject extends WebSubject{
	
	void AddActiveRole(String roleIdentifier);
	
	void ChangeAttributeValue(String attributeId,String attributeValue) throws UnknownIdentifierException, ParsingException;
	
	boolean hasRole(String roleIdentifier);
	
	Set<String> AssignedRoles();
	
	Set<String> FilteredRoles();
}