package test.org.omg.DynamicAny;


/**
* test.org/omg/DynamicAny/NameDynAnyPairSeqHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/DynamicAny/DynamicAny.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/

abstract public class NameDynAnyPairSeqHelper
{
  private static String  _id = "IDL:omg.test.org/DynamicAny/NameDynAnyPairSeq:1.0";

  public static void insert (test.org.omg.CORBA.Any a, test.org.omg.DynamicAny.NameDynAnyPair[] that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static test.org.omg.DynamicAny.NameDynAnyPair[] extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.DynamicAny.NameDynAnyPairHelper.type ();
      __typeCode = test.org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_alias_tc (test.org.omg.DynamicAny.NameDynAnyPairSeqHelper.id (), "NameDynAnyPairSeq", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static test.org.omg.DynamicAny.NameDynAnyPair[] read (test.org.omg.CORBA.portable.InputStream istream)
  {
    test.org.omg.DynamicAny.NameDynAnyPair value[] = null;
    int _len0 = istream.read_long ();
    value = new test.org.omg.DynamicAny.NameDynAnyPair[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = test.org.omg.DynamicAny.NameDynAnyPairHelper.read (istream);
    return value;
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, test.org.omg.DynamicAny.NameDynAnyPair[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      test.org.omg.DynamicAny.NameDynAnyPairHelper.write (ostream, value[_i0]);
  }

}