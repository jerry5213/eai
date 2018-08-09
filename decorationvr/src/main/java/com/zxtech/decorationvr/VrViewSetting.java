package com.zxtech.decorationvr;

/**
 * Created by syp466 on 2016/11/17.
 */

public final class VrViewSetting {
    public VrViewSetting(){
        version = 2;
        is_translucent = false;
    }
    public int version;
    public int width;
    public int height;
    public int refresh_rate;
    public int color_bits; //rgb
    public int alpha_bits;
    public int depth_bits;
    public int stencil_bits;
    public int multi_sample_count;
    public int adaptor_id;
    public int vertical_sync;
    public boolean is_double_buffer;
    public boolean is_full_screen;
    public boolean is_translucent;
}
