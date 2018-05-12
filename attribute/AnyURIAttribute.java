package com.wukong.attribute;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.w3c.dom.Node;

import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;


public class AnyURIAttribute extends AttributeValue {

    /**
     * Official name of this type
     */
    public static final String identifier = "http://www.w3.org/2001/XMLSchema#anyURI";

    // URI version of name for this type
    private static URI identifierURI;

    // RuntimeException that wraps an Exception thrown during the
    // creation of identifierURI, null if none
    private static RuntimeException earlyException;

    /**
     * Static initializer that initializes the identifierURI class field so that we can catch any
     * exceptions thrown by URI(String) and transform them into a RuntimeException. Such exceptions
     * should never happen but should be reported properly if they ever do.
     */
    static {
        try {
            identifierURI = new URI(identifier);
        } catch (Exception e) {
            earlyException = new IllegalArgumentException();
            earlyException.initCause(e);
        }
    };

    // the URI value that this class represents
    private URI value;

    /**
     * Creates a new <code>AnyURIAttribute</code> that represents the URI value supplied.
     * 
     * @param value the <code>URI</code> value to be represented
     */
    public AnyURIAttribute(URI value) {
        super(identifierURI);

        // Shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        this.value = value;
    }

    /**
     * Returns a new <code>AnyURIAttribute</code> that represents the xs:anyURI at a particular DOM
     * node.
     * 
     * @param root the <code>Node</code> that contains the desired value
     * 
     * @return a new <code>AnyURIAttribute</code> representing the appropriate value (null if there
     *         is a parsing error)
     */
    public static AnyURIAttribute getInstance(Node root) throws URISyntaxException {
        return getInstance(root.getFirstChild().getNodeValue());
    }

    /**
     * Returns a new <code>AnyURIAttribute</code> that represents the xs:anyURI value indicated by
     * the <code>String</code> provided.
     * 
     * @param value a string representing the desired value
     * 
     * @return a new <code>AnyURIAttribute</code> representing the appropriate value
     */
    public static AnyURIAttribute getInstance(String value) throws URISyntaxException {
        return new AnyURIAttribute(new URI(value));
    }

    /**
     * Returns the <code>URI</code> value represented by this object.
     * 
     * @return the <code>URI</code> value
     */
    public URI getValue() {
        return value;
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
        if (!(o instanceof AnyURIAttribute))
            return false;

        AnyURIAttribute other = (AnyURIAttribute) o;

        return value.equals(other.value);
    }

    /**
     * Returns the hashcode value used to index and compare this object with others of the same
     * type. Typically this is the hashcode of the backing data object.
     * 
     * @return the object's hashcode value
     */
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Converts to a String representation.
     * 
     * @return the String representation
     */
    public String toString() {
        return "AnyURIAttribute: \"" + value.toString() + "\"";
    }

    /**
     *
     */
    public String encode() {
        return value.toString();
    }


	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

}