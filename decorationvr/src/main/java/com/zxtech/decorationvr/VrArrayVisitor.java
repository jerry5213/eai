package com.zxtech.decorationvr;

/**
 * Created by syp466 on 2018/1/29.
 */

public final class VrArrayVisitor {
    public static int COMPOUND_TYPE_VECTOR2 = 17;
    public static int COMPOUND_TYPE_VECTOR3 = 12;
    public static int COMPOUND_TYPE_VECTOR4 = 15;
    public static int COMPOUND_TYPE_QUATERNION = 14;
    public static int COMPOUND_TYPE_MATRIX = 18;
    public static int COMPOUND_TYPE_COLOR = 19;

    private VrArrayVisitor() { }

    public native String GetName();

    public native int GetRowCount();
    public native int GetColumnCount();

    public native int AddRow();
    public native void DeleteRow(int row);
    public native void Clear();

    public native int GetIntValue(int row, int col);
    public native void SetIntValue(int row, int col, int val);

    public native boolean GetBoolValue(int row, int col);
    public native void SetBoolValue(int row, int col, boolean val);

    public native float GetFloatValue(int row, int col);
    public native void SetFloatValue(int row, int col, float val);

    public native String GetStringValue(int row, int col);
    public native void SetStringValue(int row, int col, String val);

    public native String GetCompoundValue(int row, int col, int compound_type);
    public native void SetCompoundValue(int row, int col, int compound_type, String val);

    private long nativeID;
}
