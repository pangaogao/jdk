package test.org.omg.CosNaming.NamingContextExtPackage;

/**
* test.org/omg/CosNaming/NamingContextExtPackage/InvalidAddressHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/CosNaming/nameservice.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/

public final class InvalidAddressHolder implements test.org.omg.CORBA.portable.Streamable
{
  public test.org.omg.CosNaming.NamingContextExtPackage.InvalidAddress value = null;

  public InvalidAddressHolder ()
  {
  }

  public InvalidAddressHolder (test.org.omg.CosNaming.NamingContextExtPackage.InvalidAddress initialValue)
  {
    value = initialValue;
  }

  public void _read (test.org.omg.CORBA.portable.InputStream i)
  {
    value = test.org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.read (i);
  }

  public void _write (test.org.omg.CORBA.portable.OutputStream o)
  {
    test.org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.write (o, value);
  }

  public test.org.omg.CORBA.TypeCode _type ()
  {
    return test.org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.type ();
  }

}
