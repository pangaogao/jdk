package test.org.omg.DynamicAny;


/**
* test.org/omg/DynamicAny/FieldNameHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/DynamicAny/DynamicAny.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/

abstract public class FieldNameHelper
{
  private static String  _id = "IDL:omg.test.org/DynamicAny/FieldName:1.0";

  public static void insert (test.org.omg.CORBA.Any a, String that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static String extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().create_string_tc (0);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_alias_tc (test.org.omg.DynamicAny.FieldNameHelper.id (), "FieldName", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static String read (test.org.omg.CORBA.portable.InputStream istream)
  {
    String value = null;
    value = istream.read_string ();
    return value;
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, String value)
  {
    ostream.write_string (value);
  }

}