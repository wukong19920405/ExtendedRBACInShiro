package com.wukong.attribute;

import java.net.URI;

import org.w3c.dom.Node;

public class IntegerAttribute extends AttributeValue {
	
	public static final String identifier = "http://www.w3.org/2001/XMLSchema#integer";
	
	private static URI identifierURI;
	
	private static RuntimeException earlyException;
	
	static {
        try {
            identifierURI = new URI(identifier);
        } catch (Exception e) {
            earlyException = new IllegalArgumentException();
            earlyException.initCause(e);
        }
    };
    
    private long value;
    
    public IntegerAttribute(long value) {
        super(identifierURI);

        // Shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        this.value = value;
    }
    
    public static IntegerAttribute getInstance(Node root) throws NumberFormatException {
        return getInstance(root.getFirstChild().getNodeValue());
    }
    
    public static IntegerAttribute getInstance(String value) throws NumberFormatException {
        // Leading '+' is allowed per XML schema and not
        // by Long.parseLong. Strip it, if present.
        if ((value.length() >= 1) && (value.charAt(0) == '+'))
            value = value.substring(1);
        return new IntegerAttribute(Long.parseLong(value));
    }
    
    public long getValue() {
        return value;
    }
    
    public void setValue(String value){
    	if ((value.length() >= 1) && (value.charAt(0) == '+'))
            value = value.substring(1);
    	this.value=Long.parseLong(value);
    }

    /**
     * Returns true if the input is an instance of this class and if its value equals the value
     * contained in this class.
     * 
     * @param o the object to compare
     * 
     * @return true if this object and the input represent the same value
     */
    public boolean equals(Object o) {
        if (!(o instanceof IntegerAttribute))
            return false;

        IntegerAttribute other = (IntegerAttribute) o;

        return (value == other.value);
    }
    
    public String encode() {
        return String.valueOf(value);
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

}