package test.org.omg.CosNaming;


/**
* test.org/omg/CosNaming/_BindingIteratorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/CosNaming/nameservice.idl
* Wednesday, March 15, 2017 1:33:57 AM PDT
*/


/**
   * The BindingIterator interface allows a client to iterate through
   * the bindings using the next_one or next_n operations.
   * 
   * The bindings iterator is obtained by using the <tt>list</tt>
   * method on the <tt>NamingContext</tt>. 
   * @see test.org.omg.CosNaming.NamingContext#list
   */
public class _BindingIteratorStub extends test.org.omg.CORBA.portable.ObjectImpl implements test.org.omg.CosNaming.BindingIterator
{


  /**
       * This operation returns the next binding. If there are no more
       * bindings, false is returned.
       * 
       * @param b the returned binding
       */
  public boolean next_one (test.org.omg.CosNaming.BindingHolder b)
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("next_one", true);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                b.value = test.org.omg.CosNaming.BindingHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return next_one (b        );
            } finally {
                _releaseReply ($in);
            }
  } // next_one


  /**
       * This operation returns at most the requested number of bindings.
       * 
       * @param how_many the maximum number of bindings tro return <p>
       * 
       * @param bl the returned bindings
       */
  public boolean next_n (int how_many, test.org.omg.CosNaming.BindingListHolder bl)
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("next_n", true);
                $out.write_ulong (how_many);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                bl.value = test.org.omg.CosNaming.BindingListHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return next_n (how_many, bl        );
            } finally {
                _releaseReply ($in);
            }
  } // next_n


  /**
       * This operation destroys the iterator.
       */
  public void destroy ()
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("destroy", true);
                $in = _invoke ($out);
                return;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                destroy (        );
            } finally {
                _releaseReply ($in);
            }
  } // destroy

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.test.org/CosNaming/BindingIterator:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     test.org.omg.CORBA.ORB orb = test.org.omg.CORBA.ORB.init (args, props);
   try {
     test.org.omg.CORBA.Object obj = orb.string_to_object (str);
     test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     test.org.omg.CORBA.ORB orb = test.org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _BindingIteratorStub
