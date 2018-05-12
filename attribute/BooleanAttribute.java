package com.wukong.attribute;

import java.net.URI;

import org.w3c.dom.Node;

import com.wukong.exception.*;

public class BooleanAttribute extends AttributeValue {

    /**
     * Official name of this type
     */
    public static final String identifier = "http://www.w3.org/2001/XMLSchema#boolean";

    /**
     * URI version of name for this type
     * <p>
     * This field is initialized by a static initializer so that we can catch any exceptions thrown
     * by URI(String) and transform them into a RuntimeException, since this should never happen but
     * should be reported properly if it ever does.
     */
    private static URI identifierURI;

    /**
     * RuntimeException that wraps an Exception thrown during the creation of identifierURI, null if
     * none.
     */
    private static RuntimeException earlyException;

    /**
     * Single instance of BooleanAttribute that represents true. Initialized by the static
     * initializer below.
     */
    private static BooleanAttribute trueInstance;

    /**
     * Single instance of BooleanAttribute that represents false. Initialized by the static
     * initializer below.
     */
    private static BooleanAttribute falseInstance;

    /**
     * Static initializer that initializes many static fields.
     * <p>
     * It is possible identifierURI class field so that we can catch any exceptions thrown by
     * URI(String) and transform them into a RuntimeException. Such exceptions should never happen
     * but should be reported properly if they ever do.
     */
    static {
        try {
            identifierURI = new URI(identifier);
            trueInstance = new BooleanAttribute(true);
            falseInstance = new BooleanAttribute(false);
        } catch (Exception e) {
            earlyException = new IllegalArgumentException();
            earlyException.initCause(e);
        }
    };

    /**
     * The actual boolean value that this object represents.
     */
    private boolean value;

    /**
     * Creates a new <code>BooleanAttribute</code> that represents the boolean value supplied.
     * <p>
     * This constructor is private because it should not be used by anyone other than the static
     * initializer in this class. Instead, please use one of the getInstance methods, which will
     * ensure that only two BooleanAttribute objects are created, thus avoiding excess object
     * creation.
     */
    private BooleanAttribute(boolean value) {
        super(identifierURI);

        this.value = value;
    }

    /**
     * Returns a <code>BooleanAttribute</code> that represents the xs:boolean at a particular DOM
     * node.
     * 
     * @param root the <code>Node</code> that contains the desired value
     * @return a <code>BooleanAttribute</code> representing the appropriate value (null if there is
     *         a parsing error)
     */
    public static BooleanAttribute getInstance(Node root) throws ParsingException {
        return getInstance(root.getFirstChild().getNodeValue());
    }

    /**
     * Returns a <code>BooleanAttribute</code> that represents the xs:boolean value indicated by the
     * string provided.
     * 
     * @param value a string representing the desired value
     * @return a <code>BooleanAttribute</code> representing the appropriate value (null if there is
     *         a parsing error)
     */
    public static BooleanAttribute getInstance(String value) throws ParsingException {
        // Shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        if (value.equals("true"))
            return trueInstance;
        if (value.equals("false"))
            return falseInstance;

        throw new ParsingException("Boolean string must be true or false");
    }

    /**
     * Returns a <code>BooleanAttribute</code> that represents the boolean value provided.
     * 
     * @param value a boolean representing the desired value
     * @return a <code>BooleanAttribute</code> representing the appropriate value
     */
    public static BooleanAttribute getInstance(boolean value) {

        // Shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        if (value)
            return trueInstance;
        else
            return falseInstance;
    }

    /**
     * Returns a <code>BooleanAttribute</code> that represents a true value.
     * 
     * @return a <code>BooleanAttribute</code> representing a true value
     */
    public static BooleanAttribute getTrueInstance() {

        // Shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        return trueInstance;
    }

    /**
     * Returns a <code>BooleanAttribute</code> that represents a false value.
     * 
     * @return a <code>BooleanAttribute</code> representing a false value
     */
    public static BooleanAttribute getFalseInstance() {

        // Shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        return falseInstance;
    }

    /**
     * Returns the <code>boolean</code> value represented by this object.
     * 
     * @return the <code>boolean</code> value
     */
    public boolean getValue() {
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
        if (!(o instanceof BooleanAttribute))
            return false;

        BooleanAttribute other = (BooleanAttribute) o;

        return (value == other.value);
    }

    /**
     * Returns the hashcode value used to index and compare this object with others of the same
     * type. Typically this is the hashcode of the backing data object.
     * 
     * @return the object's hashcode value
     */
    public int hashCode() {
        // these numbers come from the javadoc for java.lang.Boolean...no,
        // really, they do. I can't imagine what they were thinking...
        return (value ? 1231 : 1237);
    }

    /**
     *
     */
    public String encode() {
        return (value ? "true" : "false");
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

}
