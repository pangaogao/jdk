/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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

/**
* The Helper for <tt>Object</tt>.  For more information on
* Helper files, see <a href="doc-files/generatedfiles.html#helper">
* "Generated Files: Helper Files"</a>.<P>
*/

/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 */

package test.org.omg.CORBA;

abstract public class ObjectHelper
{
    private static String  _id = "";

    public static void insert (test.org.omg.CORBA.Any a, test.org.omg.CORBA.Object that)
    {
        test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
        a.type (type ());
        write (out, that);
        a.read_value (out.create_input_stream (), type ());
    }

    public static test.org.omg.CORBA.Object extract (test.org.omg.CORBA.Any a)
    {
        return read (a.create_input_stream ());
    }

    private static test.org.omg.CORBA.TypeCode __typeCode = null;
    synchronized public static test.org.omg.CORBA.TypeCode type ()
    {
        if (__typeCode == null)
            {
                __typeCode = test.org.omg.CORBA.ORB.init ().get_primitive_tc (TCKind.tk_objref);
            }
        return __typeCode;
    }

    public static String id ()
    {
        return _id;
    }

    public static test.org.omg.CORBA.Object read (test.org.omg.CORBA.portable.InputStream istream)
    {
        return istream.read_Object ();
    }

    public static void write (test.org.omg.CORBA.portable.OutputStream ostream, test.org.omg.CORBA.Object value)
    {
        ostream.write_Object (value);
    }

}
