package com.zxtech.decorationvr;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;

public final class VrView extends GLSurfaceView {
    public static int GS_CHANGED = 2;
    public static int GT_PINCH = 3;//缩放
    public static int GT_PAN = 6;//平移

    private GestureDetector gesture_detector;
    private ScaleGestureDetector scale_gesture_detector;

    private boolean is_init = false;
    private boolean is_lost_device_ = false;

    private ElevatorSpec es;
    private VrViewSetting vr_setting;

    Surface surf;
    private HttpDecorationVrCallBack vr_call_back = null;

    public VrView(Context context) {
        super(context);
        Init();
    }

    public VrView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }


    @Override
    public void onPause() {

        if (is_init && !is_lost_device_) {
            is_lost_device_ = true;
            queueEvent(new Runnable() {
                @Override
                public void run() {
                    DecorationVrWrapper.OnLostDevice();
                }
            });
        }

        super.onPause();

    }

    private void Init() {
        gesture_detector = new GestureDetector(getContext(), new VrGestureListener());
        scale_gesture_detector = new ScaleGestureDetector(getContext(), new VrScaleGestureListener());

        vr_call_back = new HttpDecorationVrCallBack();

        vr_setting = new VrViewSetting();
        vr_setting.version = 3;
        vr_setting.color_bits = 24;
        vr_setting.alpha_bits = 8;
        vr_setting.depth_bits = 24;
        vr_setting.stencil_bits = 8;

        this.setEGLContextClientVersion(vr_setting.version);
        this.setEGLConfigChooser(new VrConfigChooser());
        //this.setPreserveEGLContextOnPause(true);
        this.setRenderer(new VrRender());
    }

    public void VrInit(ElevatorSpec es) {
        this.es = es;
    }

    public void SetVrCompletedCallBack(IVrChangeCompleted cb) {
        vr_call_back.SetUICallBack(cb);
    }


    public void VrRelease() {
        if (is_init) {
            is_init = false;
            DecorationVrWrapper.FinalizeVr();
        }
    }

    public void VrCreate() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                if (!is_init) {
                    surf = getHolder().getSurface();
                    DecorationVrWrapper.InitializeVr(getHolder().getSurface(), es, 0);
                    DecorationVrWrapper.SetDecorationVrCallBack(vr_call_back);
                    DecorationVrWrapper.Resize(getWidth(), getHeight());
                    //DecorationVrWrapper.SetPart(200000, 2, false);
                    //DecorationVrWrapper.SetPart(100000, 2, true);
                    //DecorationVrWrapper.SetPartByGoods(PartType.PartTypeCarConfig, 2, true);
                    is_init = true;
                }
            }
        });
    }

    private class VrConfigChooser implements EGLConfigChooser {
        protected int mRedSize;
        protected int mGreenSize;
        protected int mBlueSize;
        protected int mAlphaSize;
        protected int mDepthSize;
        protected int mStencilSize;
        protected int mSampleBuffers;
        protected int mSample;
        protected int mEGLContextClientVersion;
        private int[] mConfigSpec;
        private int[] mValue;

        public VrConfigChooser() {
            //if (vr_setting.color_bits == 32 || vr_setting.color_bits == 24) {
            mRedSize = 8;
            mGreenSize = 8;
            mBlueSize = 8;
            // } else if (vr_setting.color_bits == 16) {
            //     mRedSize = 5;
            //     mGreenSize = 6;
            //     mBlueSize = 5;
            // }

            mAlphaSize = vr_setting.alpha_bits;

            mDepthSize = vr_setting.depth_bits;

            mStencilSize = vr_setting.stencil_bits;

            if (vr_setting.multi_sample_count > 1) {
                mSampleBuffers = 1;
                mSample = vr_setting.multi_sample_count;
            }

            mEGLContextClientVersion = vr_setting.version;

            mValue = new int[1];
            mConfigSpec = filterConfigSpec(new int[]{
                    EGL10.EGL_RED_SIZE, mRedSize,
                    EGL10.EGL_GREEN_SIZE, mGreenSize,
                    EGL10.EGL_BLUE_SIZE, mBlueSize,
                    EGL10.EGL_ALPHA_SIZE, mAlphaSize,
                    EGL10.EGL_DEPTH_SIZE, mDepthSize,
                    EGL10.EGL_STENCIL_SIZE, mStencilSize,
                    EGL10.EGL_SAMPLE_BUFFERS, mSampleBuffers,
                    EGL10.EGL_SAMPLES, mSample,
                    EGL10.EGL_SURFACE_TYPE, EGL10.EGL_WINDOW_BIT,
                    EGL10.EGL_NONE
            });
        }

        @Override
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[1];
            if (!egl.eglChooseConfig(display, mConfigSpec, null, 0, num_config)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }

            int numConfigs = num_config[0];
            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }

            EGLConfig[] configs = new EGLConfig[numConfigs];
            if (!egl.eglChooseConfig(display, mConfigSpec, configs, numConfigs, num_config)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }

            EGLConfig config = chooseConfig(egl, display, configs);
            if (config == null) {
                throw new IllegalArgumentException("No config chosen");
            }
            return config;
        }

        private EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {

            for (EGLConfig config : configs) {
                int d = findConfigAttrib(egl, display, config,
                        EGL10.EGL_DEPTH_SIZE, 0);
                int s = findConfigAttrib(egl, display, config,
                        EGL10.EGL_STENCIL_SIZE, 0);
                if ((d >= mDepthSize) && (s >= mStencilSize)) {
                    int r = findConfigAttrib(egl, display, config,
                            EGL10.EGL_RED_SIZE, 0);
                    int g = findConfigAttrib(egl, display, config,
                            EGL10.EGL_GREEN_SIZE, 0);
                    int b = findConfigAttrib(egl, display, config,
                            EGL10.EGL_BLUE_SIZE, 0);
                    int a = findConfigAttrib(egl, display, config,
                            EGL10.EGL_ALPHA_SIZE, 0);
                    if ((r == mRedSize) && (g == mGreenSize)
                            && (b == mBlueSize) && (a == mAlphaSize)) {
                        return config;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config,
                                     int attribute, int defaultValue) {

            if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
                return mValue[0];
            }
            return defaultValue;
        }

        private int[] filterConfigSpec(int[] configSpec) {
            if (mEGLContextClientVersion != 2) {
                return configSpec;
            }
            /* We know none of the subclasses define EGL_RENDERABLE_TYPE.
             * And we know the configSpec is well formed.
             */
            int len = configSpec.length;
            int[] newConfigSpec = new int[len + 2];
            System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
            newConfigSpec[len - 1] = EGL10.EGL_RENDERABLE_TYPE;
            newConfigSpec[len] = 4; /* EGL_OPENGL_ES2_BIT */
            newConfigSpec[len + 1] = EGL10.EGL_NONE;
            return newConfigSpec;
        }


    }


    private class VrRender implements Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            gl10.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
            if (!is_init) {
                VrCreate();
            } else if (is_lost_device_) {
                is_lost_device_ = false;
                DecorationVrWrapper.OnDeviceRecreated(getHolder().getSurface());
            }

        }

        @Override
        public void onSurfaceChanged(GL10 gl10, final int w, final int h) {

            gl10.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
            if(is_init) {
                DecorationVrWrapper.Resize(w, h);
            }
        }


        @Override
        public void onDrawFrame(GL10 gl10) {
            if(is_init && !is_lost_device_){
                DecorationVrWrapper.Render();
            }
            else{
                gl10.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean ret = scale_gesture_detector.onTouchEvent(e);
        ret = gesture_detector.onTouchEvent(e) || ret;
        return ret || super.onTouchEvent(e);
    }

    private class VrGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
            if (is_init) {
                final float delta_x = var3;
                final float delta_y = var4;
                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        DecorationVrWrapper.Gesture(GT_PAN, GS_CHANGED, delta_x, delta_y);
                    }
                });
            }
            return true;
        }
    }

    private class VrScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (is_init) {
                final float delta_factor = GetDeltaScaleFactor(detector);
                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        DecorationVrWrapper.Gesture(GT_PINCH, GS_CHANGED, delta_factor, 0);
                    }
                });
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }

        private float GetDeltaScaleFactor(ScaleGestureDetector detector) {
            float delta = (detector.getScaleFactor() - 1.0f) * 100;
            if (delta > 100)
                delta = 100;
            if (delta < -100)
                delta = -100;
            return delta;
        }
    }
}
