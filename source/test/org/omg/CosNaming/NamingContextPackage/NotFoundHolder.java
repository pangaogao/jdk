package test.org.omg.CosNaming.NamingContextPackage;

/**
* test.org/omg/CosNaming/NamingContextPackage/NotFoundHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/CosNaming/nameservice.idl
* Wednesday, March 15, 2017 1:33:57 AM PDT
*/

public final class NotFoundHolder implements test.org.omg.CORBA.portable.Streamable
{
  public test.org.omg.CosNaming.NamingContextPackage.NotFound value = null;

  public NotFoundHolder ()
  {
  }

  public NotFoundHolder (test.org.omg.CosNaming.NamingContextPackage.NotFound initialValue)
  {
    value = initialValue;
  }

  public void _read (test.org.omg.CORBA.portable.InputStream i)
  {
    value = test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read (i);
  }

  public void _write (test.org.omg.CORBA.portable.OutputStream o)
  {
    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write (o, value);
  }

  public test.org.omg.CORBA.TypeCode _type ()
  {
    return test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type ();
  }

}
