package test.org.omg.CosNaming;

/**
* test.org/omg/CosNaming/BindingHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/CosNaming/nameservice.idl
* Wednesday, March 15, 2017 1:33:57 AM PDT
*/

public final class BindingHolder implements test.org.omg.CORBA.portable.Streamable
{
  public test.org.omg.CosNaming.Binding value = null;

  public BindingHolder ()
  {
  }

  public BindingHolder (test.org.omg.CosNaming.Binding initialValue)
  {
    value = initialValue;
  }

  public void _read (test.org.omg.CORBA.portable.InputStream i)
  {
    value = test.org.omg.CosNaming.BindingHelper.read (i);
  }

  public void _write (test.org.omg.CORBA.portable.OutputStream o)
  {
    test.org.omg.CosNaming.BindingHelper.write (o, value);
  }

  public test.org.omg.CORBA.TypeCode _type ()
  {
    return test.org.omg.CosNaming.BindingHelper.type ();
  }

}
