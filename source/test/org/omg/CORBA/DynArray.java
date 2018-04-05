/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package test.org.omg.CORBA;


/** Represents a <tt>DynAny</tt> object associated
 *  with an array.
 * @deprecated Use the new <a href="../DynamicAny/DynArray.html">DynArray</a> instead
 */
@Deprecated
public interface DynArray extends test.org.omg.CORBA.Object, test.org.omg.CORBA.DynAny
{
    /**
     * Returns the value of all the elements of this array.
     *
     * @return the array of <code>Any</code> objects that is the value
         *         for this <code>DynArray</code> object
         * @see #set_elements
     */
    public test.org.omg.CORBA.Any[] get_elements();

    /**
     * Sets the value of this
     * <code>DynArray</code> object to the given array.
     *
     * @param value the array of <code>Any</code> objects
     * @exception InvalidSeq if the sequence is bad
         * @see #get_elements
     */
    public void set_elements(test.org.omg.CORBA.Any[] value)
        throws test.org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
