package com.wukong.attribute;

import java.net.URI;
import java.net.UnknownHostException;

import org.w3c.dom.Node;


import com.wukong.exception.*;

import java.net.InetAddress;
public abstract class IPAddressAttribute extends AttributeValue{
	
	public static final String identifier="urn:oasis:names:tc:xacml:2.0:data-type:ipAddress";
	
	private static URI identifierURI;
	
	private static RuntimeException earlyException;
	
	static{
		try{
			identifierURI= new URI(identifier);
		}catch(Exception e){
			earlyException=new IllegalArgumentException();
			earlyException.initCause(e);
		}
	}
	
	private InetAddress value;
	
	private InetAddress mask;
	
	private PortRange range;
	
	public IPAddressAttribute(InetAddress addr, InetAddress mask, PortRange range){
		super(identifierURI);
		if(earlyException!=null)
			throw earlyException;
		this.value=addr;
		this.mask=mask;
		this.range=range;
	}
	
	public static IPAddressAttribute getInstance(Node root) throws ParsingException{
		return getInstance(root.getFirstChild().getNodeValue());
	}
	
	public static IPAddressAttribute getInstance(String value) throws ParsingException{
		try{
			if(value.indexOf("[")==0)
				return IPv6AddressAttribute.getV6Instance(value);
			else
				return IPv4AddressAttribute.getV4Instance(value);
		} catch (UnknownHostException e){
			throw new ParsingException("Failed to parse an IPAddress", e);
		}
	}
	
	public InetAddress getAddress() {
        return value;
    }
	
	public InetAddress getMask() {
        return mask;
    }

    /**
     * Returns the port range represented by this object which will be unbound if no range was
     * specified.
     * 
     * @return the range
     */
    public PortRange getRange() {
        return range;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof IPAddressAttribute))
            return false;

        IPAddressAttribute other = (IPAddressAttribute) o;

        if (!value.equals(other.value))
            return false;

        if (mask != null) {
            if (other.mask == null)
                return false;

            if (!mask.equals(other.mask))
                return false;
        } else {
            if (other.mask != null)
                return false;
        }

        if (!range.equals(other.range))
            return false;

        return true;
    }
    
    public String toString() {
        return "IPAddressAttribute: \"" + encode() + "\"";
    }
}
