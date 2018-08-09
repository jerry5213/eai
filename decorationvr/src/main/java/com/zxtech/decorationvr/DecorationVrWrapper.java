package com.zxtech.decorationvr;

/**
 * Created by syp466 on 2018/1/23.
 */

public final class DecorationVrWrapper {

    static  {
        System.loadLibrary("decoration_vr_wrapper");
    }

    public static native void SetDownloadServerURL(String url, String download_url);

    public static native void InitializeVr(Object surface, ElevatorSpec es, int flag);
    public static native void FinalizeVr();

    public static native boolean InitializeVrEngine(Object surface);
    public static native void FinalizeVrEngine();

    public static native void LoadMainScene();
    public static native void UnloadMainScene();

    public static native void SetElevatorSpec(ElevatorSpec es);
    public static native void SetAppDirectory(String app_dir, String home_dir);

    public static native void SetPart(int part_type, long part_id, boolean notify);
    public static native void SetMaterial(int part_type, long material_id, boolean notify);
    public static native void SetMaterial(int part_type, int mark, long material_id, boolean notify);

    public static native void SetPartByGoods(int part_type, long goods_id, boolean notify);
    public static native void SetMaterialByGoods(int part_type, long goods_id, boolean notify);
    public static native void SetContentString(String content);

    public static native void Resize(float width, float height);
    public static native void Render();

    public static native void SendVrMessage(String msg, int delay_frame);
    public static native void SendVrMessage(Msg msg, int delay_frame);

    public static native void SetDecorationVrCallBack(IDecorationVrCallBack cb);

    public static native void Gesture(int type, int state, float x, float y);

    public static native VrArrayVisitor GetVrArrayVisitor(String name);

    public static native void PrintData();

    public static native void OnLostDevice();

    public static native void OnDeviceRecreated(Object surface);
}
