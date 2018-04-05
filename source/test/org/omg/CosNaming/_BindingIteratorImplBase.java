/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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
/*
 * File: ./test.org/omg/CosNaming/_BindingIteratorImplBase.java
 * From: nameservice.idl
 * Date: Tue Aug 11 03:12:09 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 * @deprecated Deprecated in JDK 1.4.
 */

package test.org.omg.CosNaming;
public abstract class _BindingIteratorImplBase extends test.org.omg.CORBA.DynamicImplementation implements test.org.omg.CosNaming.BindingIterator {
    // Constructor
    public _BindingIteratorImplBase() {
        super();
    }
    // Type strings for this class and its superclases
    private static final String _type_ids[] = {
        "IDL:omg.test.org/CosNaming/BindingIterator:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    private static java.util.Dictionary _methods = new java.util.Hashtable();
    static {
        _methods.put("next_one", new java.lang.Integer(0));
        _methods.put("next_n", new java.lang.Integer(1));
        _methods.put("destroy", new java.lang.Integer(2));
    }
    // DSI Dispatch call
    public void invoke(test.org.omg.CORBA.ServerRequest r) {
        switch (((java.lang.Integer) _methods.get(r.op_name())).intValue()) {
        case 0: // test.org.omg.CosNaming.BindingIterator.next_one
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _b = _orb().create_any();
                _b.type(test.org.omg.CosNaming.BindingHelper.type());
                _list.add_value("b", _b, test.org.omg.CORBA.ARG_OUT.value);
                r.params(_list);
                test.org.omg.CosNaming.BindingHolder b;
                b = new test.org.omg.CosNaming.BindingHolder();
                boolean ___result;
                ___result = this.next_one(b);
                test.org.omg.CosNaming.BindingHelper.insert(_b, b.value);
                test.org.omg.CORBA.Any __result = _orb().create_any();
                __result.insert_boolean(___result);
                r.result(__result);
            }
            break;
        case 1: // test.org.omg.CosNaming.BindingIterator.next_n
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _how_many = _orb().create_any();
                _how_many.type(test.org.omg.CORBA.ORB.init().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_ulong));
                _list.add_value("how_many", _how_many, test.org.omg.CORBA.ARG_IN.value);
                test.org.omg.CORBA.Any _bl = _orb().create_any();
                _bl.type(test.org.omg.CosNaming.BindingListHelper.type());
                _list.add_value("bl", _bl, test.org.omg.CORBA.ARG_OUT.value);
                r.params(_list);
                int how_many;
                how_many = _how_many.extract_ulong();
                test.org.omg.CosNaming.BindingListHolder bl;
                bl = new test.org.omg.CosNaming.BindingListHolder();
                boolean ___result;
                ___result = this.next_n(how_many, bl);
                test.org.omg.CosNaming.BindingListHelper.insert(_bl, bl.value);
                test.org.omg.CORBA.Any __result = _orb().create_any();
                __result.insert_boolean(___result);
                r.result(__result);
            }
            break;
        case 2: // test.org.omg.CosNaming.BindingIterator.destroy
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                r.params(_list);
                this.destroy();
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        default:
            throw new test.org.omg.CORBA.BAD_OPERATION(0, test.org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        }
    }
}
