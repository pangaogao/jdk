/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.dynamicany;

import test.org.omg.CORBA.Any;
import test.org.omg.CORBA.TypeCode;
import test.org.omg.CORBA.TypeCodePackage.BadKind;
import test.org.omg.CORBA.TCKind;

import test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
import test.org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import test.org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;

import com.sun.corba.se.spi.orb.ORB ;

public class DynAnyBasicImpl extends DynAnyImpl
{
    //
    // Constructors
    //

    private DynAnyBasicImpl() {
        this(null, (Any)null, false);
    }

    protected DynAnyBasicImpl(ORB orb, Any any, boolean copyValue) {
        super(orb, any, copyValue);
        // set the current position to 0 if any has components, otherwise to -1.
        index = NO_INDEX;
    }

    protected DynAnyBasicImpl(ORB orb, TypeCode typeCode) {
        super(orb, typeCode);
        // set the current position to 0 if any has components, otherwise to -1.
        index = NO_INDEX;
    }

    //
    // DynAny interface methods
    //

    public void assign (test.org.omg.DynamicAny.DynAny dyn_any)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        super.assign(dyn_any);
        index = NO_INDEX;
    }

    public void from_any (test.org.omg.CORBA.Any value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        super.from_any(value);
        index = NO_INDEX;
    }

    // Spec: Returns a copy of the internal Any
    public test.org.omg.CORBA.Any to_any() {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        return DynAnyUtil.copy(any, orb);
    }

    public boolean equal (test.org.omg.DynamicAny.DynAny dyn_any) {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (dyn_any == this) {
            return true;
        }
        // If the other DynAny is a constructed one we don't want it to have
        // to create its Any representation just for this test.
        if ( ! any.type().equal(dyn_any.type())) {
            return false;
        }
        //System.out.println("Comparing anys");
        return any.equal(getAny(dyn_any));
    }

    public void destroy() {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (status == STATUS_DESTROYABLE) {
            status = STATUS_DESTROYED;
        }
    }

    public test.org.omg.DynamicAny.DynAny copy() {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        // The flag "true" indicates copying the Any value
        try {
            return DynAnyUtil.createMostDerivedDynAny(any, orb, true);
        } catch (InconsistentTypeCode ictc) {
            return null; // impossible
        }
    }

    public test.org.omg.DynamicAny.DynAny current_component()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        return null;
    }

    public int component_count() {
        return 0;
    }

    public boolean next() {
        return false;
    }

    public boolean seek(int index) {
        return false;
    }

    public void rewind() {
    }

    public void insert_boolean(boolean value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_boolean)
            throw new TypeMismatch();
        any.insert_boolean(value);
    }

    public void insert_octet(byte value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_octet)
            throw new TypeMismatch();
        any.insert_octet(value);
    }

    public void insert_char(char value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_char)
            throw new TypeMismatch();
        any.insert_char(value);
    }

    public void insert_short(short value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_short)
            throw new TypeMismatch();
        any.insert_short(value);
    }

    public void insert_ushort(short value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_ushort)
            throw new TypeMismatch();
        any.insert_ushort(value);
    }

    public void insert_long(int value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_long)
            throw new TypeMismatch();
        any.insert_long(value);
    }

    public void insert_ulong(int value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_ulong)
            throw new TypeMismatch();
        any.insert_ulong(value);
    }

    public void insert_float(float value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_float)
            throw new TypeMismatch();
        any.insert_float(value);
    }

    public void insert_double(double value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_double)
            throw new TypeMismatch();
        any.insert_double(value);
    }

    public void insert_string(String value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_string)
            throw new TypeMismatch();
        if (value == null)
            throw new InvalidValue();
        // Throw InvalidValue if this is a bounded string and the length is exceeded
        try {
            if (any.type().length() > 0 && any.type().length() < value.length())
                throw new InvalidValue();
        } catch (BadKind bad) { // impossible
        }
        any.insert_string(value);
    }

    public void insert_reference(test.org.omg.CORBA.Object value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_objref)
            throw new TypeMismatch();
        any.insert_Object(value);
    }

    public void insert_typecode(test.org.omg.CORBA.TypeCode value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_TypeCode)
            throw new TypeMismatch();
        any.insert_TypeCode(value);
    }

    public void insert_longlong(long value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_longlong)
            throw new TypeMismatch();
        any.insert_longlong(value);
    }

    public void insert_ulonglong(long value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_ulonglong)
            throw new TypeMismatch();
        any.insert_ulonglong(value);
    }

    public void insert_wchar(char value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_wchar)
            throw new TypeMismatch();
        any.insert_wchar(value);
    }

    public void insert_wstring(String value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_wstring)
            throw new TypeMismatch();
        if (value == null)
            throw new InvalidValue();
        // Throw InvalidValue if this is a bounded string and the length is exceeded
        try {
            if (any.type().length() > 0 && any.type().length() < value.length())
                throw new InvalidValue();
        } catch (BadKind bad) { // impossible
        }
        any.insert_wstring(value);
    }

    public void insert_any(test.org.omg.CORBA.Any value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_any)
            throw new TypeMismatch();
        any.insert_any(value);
    }

    public void insert_dyn_any (test.org.omg.DynamicAny.DynAny value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_any)
            throw new TypeMismatch();
        // _REVISIT_ Copy value here?
        any.insert_any(value.to_any());
    }

    public void insert_val(java.io.Serializable value)
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        int kind = any.type().kind().value();
        if (kind != TCKind._tk_value && kind != TCKind._tk_value_box)
            throw new TypeMismatch();
        any.insert_Value(value);
    }

    public java.io.Serializable get_val()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        int kind = any.type().kind().value();
        if (kind != TCKind._tk_value && kind != TCKind._tk_value_box)
            throw new TypeMismatch();
        return any.extract_Value();
    }

    public boolean get_boolean()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_boolean)
            throw new TypeMismatch();
        return any.extract_boolean();
    }

    public byte get_octet()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_octet)
            throw new TypeMismatch();
        return any.extract_octet();
    }

    public char get_char()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_char)
            throw new TypeMismatch();
        return any.extract_char();
    }

    public short get_short()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_short)
            throw new TypeMismatch();
        return any.extract_short();
    }

    public short get_ushort()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_ushort)
            throw new TypeMismatch();
        return any.extract_ushort();
    }

    public int get_long()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_long)
            throw new TypeMismatch();
        return any.extract_long();
    }

    public int get_ulong()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_ulong)
            throw new TypeMismatch();
        return any.extract_ulong();
    }

    public float get_float()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_float)
            throw new TypeMismatch();
        return any.extract_float();
    }

    public double get_double()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_double)
            throw new TypeMismatch();
        return any.extract_double();
    }

    public String get_string()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_string)
            throw new TypeMismatch();
        return any.extract_string();
    }

    public test.org.omg.CORBA.Object get_reference()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_objref)
            throw new TypeMismatch();
        return any.extract_Object();
    }

    public test.org.omg.CORBA.TypeCode get_typecode()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_TypeCode)
            throw new TypeMismatch();
        return any.extract_TypeCode();
    }

    public long get_longlong()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_longlong)
            throw new TypeMismatch();
        return any.extract_longlong();
    }

    public long get_ulonglong()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_ulonglong)
            throw new TypeMismatch();
        return any.extract_ulonglong();
    }

    public char get_wchar()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_wchar)
            throw new TypeMismatch();
        return any.extract_wchar();
    }

    public String get_wstring()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_wstring)
            throw new TypeMismatch();
        return any.extract_wstring();
    }

    public test.org.omg.CORBA.Any get_any()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_any)
            throw new TypeMismatch();
        return any.extract_any();
    }

    public test.org.omg.DynamicAny.DynAny get_dyn_any()
        throws test.org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
               test.org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (any.type().kind().value() != TCKind._tk_any)
            throw new TypeMismatch();
        // _REVISIT_ Copy value here?
        try {
            return DynAnyUtil.createMostDerivedDynAny(any.extract_any(), orb, true);
        } catch (InconsistentTypeCode ictc) {
            // The spec doesn't allow us to throw back this exception
            // incase the anys any if of type Principal, native or abstract interface.
            return null;
        }
    }
}
