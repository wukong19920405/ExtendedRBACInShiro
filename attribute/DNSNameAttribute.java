package com.wukong.attribute;

import java.net.URI;

import org.w3c.dom.Node;

import com.wukong.exception.*;


public class DNSNameAttribute extends AttributeValue {

    /**
     * Official name of this type
     */
    public static final String identifier = "urn:oasis:names:tc:xacml:2.0:data-type:dnsName";

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

    // the required hostname
    private String hostname;

    // the optional port range
    private PortRange range;

    // true if the hostname starts with a '*'
    private boolean isSubdomain = false;

    /**
     * Creates the new <code>DNSNameAttribute</code> with only the required hostname component.
     * 
     * @param hostname the host name component of the address
     * 
     * @throws ParsingException if the hostname is invalid
     */
    public DNSNameAttribute(String hostname) throws ParsingException {
        this(hostname, new PortRange());
    }

    /**
     * Creates the new <code>DNSNameAttribute</code> with the optional port range component.
     * 
     * @param hostname the host name component of the address
     * @param range the port range
     * 
     * @throws ParsingException if the hostname is invalid
     */
    public DNSNameAttribute(String hostname, PortRange range) throws ParsingException {
        super(identifierURI);

        // shouldn't happen, but just in case...
        if (earlyException != null)
            throw earlyException;

        // verify that the hostname is valid before we store it
        if (!isValidHostName(hostname))
            System.out.println("FIXME: throw error about bad hostname");

        // see if it started with a '*' character
        if (hostname.charAt(0) == '*')
            this.isSubdomain = true;

        this.hostname = hostname;
        this.range = range;
    }

    /**
     * Private helper that tests whether the given string is valid.
     */
    private boolean isValidHostName(String hostname) {
        /*
         * hostname = *( domainlabel "." ) toplabel [ "." ] domainlabel = alphanum | alphanum *(
         * alphanum | "-" ) alphanum toplabel = alpha | alpha *( alphanum | "-" ) alphanum
         */

        String domainlabel = "\\w[[\\w|\\-]*\\w]?";
        String toplabel = "[a-zA-Z][[\\w|\\-]*\\w]?";
        String pattern = "[\\*\\.]?[" + domainlabel + "\\.]*" + toplabel + "\\.?";

        return hostname.matches(pattern);
    }

    /**
     * Returns a new <code>DNSNameAttribute</code> that represents the name at a particular DOM
     * node.
     * 
     * @param root the <code>Node</code> that contains the desired value
     * 
     * @return a new <code>DNSNameAttribute</code> representing the appropriate value (null if there
     *         is a parsing error)
     * 
     * @throws ParsingException if the hostname is invalid
     */
    public static DNSNameAttribute getInstance(Node root) throws ParsingException {
        return getInstance(root.getFirstChild().getNodeValue());
    }

    /**
     * Returns a new <code>DNSNameAttribute</code> that represents the name indicated by the
     * <code>String</code> provided.
     * 
     * @param value a string representing the name
     * 
     * @return a new <code>DNSNameAttribute</code>
     * 
     * @throws ParsingException if the hostname is invalid
     */
    public static DNSNameAttribute getInstance(String value) throws ParsingException {
        int portSep = value.indexOf(':');

        if (portSep == -1) {
            // there is no port range, so just use the name
            return new DNSNameAttribute(value);
        } else {
            // split the name and the port range
            String hostname = value.substring(0, portSep);
            PortRange range = PortRange.getInstance(value.substring(portSep + 1, value.length()));
            return new DNSNameAttribute(hostname, range);
        }
    }

    /**
     * Returns the host name represented by this object.
     * 
     * @return the host name
     */
    public String getHostName() {
        return hostname;
    }

    /**
     * Returns the port range represented by this object which will be unbound if no range was
     * specified.
     * 
     * @return the port range
     */
    public PortRange getPortRange() {
        return range;
    }

    /**
     * Returns true if the leading character in the hostname is a '*', and therefore represents a
     * matching subdomain, or false otherwise.
     * 
     * @return true if the name represents a subdomain, false otherwise
     */
    public boolean isSubdomain() {
        return isSubdomain;
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
        if (!(o instanceof DNSNameAttribute))
            return false;

        DNSNameAttribute other = (DNSNameAttribute) o;

        if (!hostname.toUpperCase().equals(other.hostname.toUpperCase()))
            return false;

        if (!range.equals(other.range))
            return false;

        return true;
    }

    /**
     * Returns the hashcode value used to index and compare this object with others of the same
     * type.
     * 
     * @return the object's hashcode value
     */
    public int hashCode() {

        // FIXME: what should the hashcode be?

        return 0;
    }

    /**
     * Converts to a String representation.
     * 
     * @return the String representation
     */
    public String toString() {
        return "DNSNameAttribute: \"" + encode() + "\"";
    }

    /**
     *
     */
    public String encode() {
        if (range.isUnbound())
            return hostname;

        return hostname + ":" + range.encode();
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

}