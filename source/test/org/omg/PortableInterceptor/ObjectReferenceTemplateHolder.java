package test.org.omg.PortableInterceptor;

/**
* test.org/omg/PortableInterceptor/ObjectReferenceTemplateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableInterceptor/Interceptors.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/


/** The object reference template.  An instance of this must
   * exist for each object adapter created in an ORB.  The server_id,
   * orb_id, and adapter_name attributes uniquely identify this template
   * within the scope of an IMR.  Note that adapter_id is similarly unique
   * within the same scope, but it is opaque, and less useful in many
   * cases.
   */
public final class ObjectReferenceTemplateHolder implements test.org.omg.CORBA.portable.Streamable
{
  public test.org.omg.PortableInterceptor.ObjectReferenceTemplate value = null;

  public ObjectReferenceTemplateHolder ()
  {
  }

  public ObjectReferenceTemplateHolder (test.org.omg.PortableInterceptor.ObjectReferenceTemplate initialValue)
  {
    value = initialValue;
  }

  public void _read (test.org.omg.CORBA.portable.InputStream i)
  {
    value = test.org.omg.PortableInterceptor.ObjectReferenceTemplateHelper.read (i);
  }

  public void _write (test.org.omg.CORBA.portable.OutputStream o)
  {
    test.org.omg.PortableInterceptor.ObjectReferenceTemplateHelper.write (o, value);
  }

  public test.org.omg.CORBA.TypeCode _type ()
  {
    return test.org.omg.PortableInterceptor.ObjectReferenceTemplateHelper.type ();
  }

}
