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
 * The Helper for <tt>ServiceInformation</tt>.  For more information on
* Helper files, see <a href="doc-files/generatedfiles.html#helper">
* "Generated Files: Helper Files"</a>.<P>
*/

package test.org.omg.CORBA;


public abstract class ServiceInformationHelper {

    public static void write(test.org.omg.CORBA.portable.OutputStream out, test.org.omg.CORBA.ServiceInformation that)
    {
        out.write_long(that.service_options.length);
        out.write_ulong_array(that.service_options, 0, that.service_options.length);
        out.write_long(that.service_details.length);
        for (int i = 0 ; i < that.service_details.length ; i += 1) {
            test.org.omg.CORBA.ServiceDetailHelper.write(out, that.service_details[i]);
        }
    }

    public static test.org.omg.CORBA.ServiceInformation read(test.org.omg.CORBA.portable.InputStream in) {
        test.org.omg.CORBA.ServiceInformation that = new test.org.omg.CORBA.ServiceInformation();
        {
            int __length = in.read_long();
            that.service_options = new int[__length];
            in.read_ulong_array(that.service_options, 0, that.service_options.length);
        }
        {
            int __length = in.read_long();
            that.service_details = new test.org.omg.CORBA.ServiceDetail[__length];
            for (int __index = 0 ; __index < that.service_details.length ; __index += 1) {
                that.service_details[__index] = test.org.omg.CORBA.ServiceDetailHelper.read(in);
            }
        }
        return that;
    }
    public static test.org.omg.CORBA.ServiceInformation extract(test.org.omg.CORBA.Any a) {
        test.org.omg.CORBA.portable.InputStream in = a.create_input_stream();
        return read(in);
    }
    public static void insert(test.org.omg.CORBA.Any a, test.org.omg.CORBA.ServiceInformation that) {
        test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
        write(out, that);
        a.read_value(out.create_input_stream(), type());
    }
    private static test.org.omg.CORBA.TypeCode _tc;
    synchronized public static test.org.omg.CORBA.TypeCode type() {
        int _memberCount = 2;
        test.org.omg.CORBA.StructMember[] _members = null;
        if (_tc == null) {
            _members= new test.org.omg.CORBA.StructMember[2];
            _members[0] = new test.org.omg.CORBA.StructMember(
                                                         "service_options",
                                                         test.org.omg.CORBA.ORB.init().create_sequence_tc(0, test.org.omg.CORBA.ORB.init().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_ulong)),
                                                         null);

            _members[1] = new test.org.omg.CORBA.StructMember(
                                                         "service_details",
                                                         test.org.omg.CORBA.ORB.init().create_sequence_tc(0, test.org.omg.CORBA.ServiceDetailHelper.type()),
                                                         null);
            _tc = test.org.omg.CORBA.ORB.init().create_struct_tc(id(), "ServiceInformation", _members);
        }
        return _tc;
    }
    public static String id() {
        return "IDL:omg.test.org/CORBA/ServiceInformation:1.0";
    }
}
