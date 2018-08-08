package com.zxtech.ecs.ui.home.scheme.detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.MaterialItemAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by syp523 on 2018/2/1.
 */

public abstract class MaterialDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private ViewPager material_vp;
    private Button search_btn, close_btn;
    List<List<Parameters.Option>> pagers = new ArrayList<>();
    private Observable baseResponseObservable = null;
    private String parentCode;
    private Parameters currentParameter;
    private Map<String, String> allParams;
    private Map<String, ScriptReturnParam> scriptReturnParamMap;

    /**
     * @param context
     * @param code                 父级code
     * @param allParams            所有参数map 用于选中设置
     * @param scriptReturnParamMap 联动map 用于联动过滤
     */
    public MaterialDialog(@NonNull Context context, String code, Map<String, String> allParams, Map<String, ScriptReturnParam> scriptReturnParamMap) {
        super(context);
        this.context = context;
        this.parentCode = code;
        this.allParams = allParams;
        this.scriptReturnParamMap = scriptReturnParamMap;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_material);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initDialogSize();
        material_vp = findViewById(R.id.material_vp);
        ImageView last_page_btn = findViewById(R.id.last_page_btn);
        ImageView next_page_btn = findViewById(R.id.next_page_btn);
        search_btn = findViewById(R.id.search_btn);
        close_btn = findViewById(R.id.close_btn);
        search_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);

        last_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                material_vp.setCurrentItem(material_vp.getCurrentItem() - 1, true);
            }
        });

        next_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                material_vp.setCurrentItem(material_vp.getCurrentItem() + 1, true);
            }
        });


        initData();
    }


    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ELEVATOR, allParams.get("ElevatorType"), allParams.get(Constants.CODE_ELEVATORPRODUCT), Constants.DECORATION_PARAMETERS, parentCode);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>((Activity) context, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Parameters>> response) {
                        if (response.getData().size() > 0) {
                            currentParameter = response.getData().get(0);
                            if (scriptReturnParamMap.get(currentParameter.getProECode()) != null) {
                                ScriptReturnParam scriptReturnParam = scriptReturnParamMap.get(currentParameter.getProECode());
                                if (scriptReturnParam.getOperation() == ScriptReturnParam.RESET) { //重构
                                    currentParameter.getOption().clear();
                                    currentParameter.getOption().addAll(scriptReturnParam.getResetOptions());
                                }
                            }
                            pagers.addAll(createList(currentParameter.getOption(), 8));
                            MaterialAdatper materialAdatper = new MaterialAdatper(getContext(), pagers);
                            material_vp.setAdapter(materialAdatper);
                        }

                    }
                });

    }


    private List<List<Parameters.Option>> createList(List<Parameters.Option> targe, int size) {
        List<List<Parameters.Option>> listArr = new ArrayList<>();
        //获取被拆分的数组个数
        int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
        for (int i = 0; i < arrSize; i++) {
            List<Parameters.Option> sub = new ArrayList<>();
            //把指定索引数据放入到list中
            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
                if (j <= targe.size() - 1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (baseResponseObservable != null) {
            baseResponseObservable.unsubscribeOn(Schedulers.io());
        }
        selected(null, null);
    }

    private void initDialogSize() {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值

        p.height = (int) (d.getHeight() * 0.37);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.9
        getWindow().setAttributes(p);     //设置生效
        getWindow().setGravity(Gravity.BOTTOM);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                search(currentParameter);
                break;
            case R.id.close_btn:
                dismiss();
                break;
        }
    }

    public abstract void selected(String code, String value);

    public abstract void search(Parameters parameters);


    class MaterialAdatper extends PagerAdapter {
        //图片资源合集:ViewPager滚动的页面种类
        private Context mContext;
        List<List<Parameters.Option>> pagers = new ArrayList<>();

        public MaterialAdatper(Context context, List<List<Parameters.Option>> pagers) {
            super();
            this.mContext = context;
            this.pagers = pagers;
        }

        public MaterialAdatper(Context context) {
            super();
            this.mContext = context;
        }

        //返回填充ViewPager页面的数量
        @Override
        public int getCount() {
            return pagers.size();
        }

        //销毁ViewPager内某个页面时调用
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//固定是view == object
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_material, null);
            RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
            final MaterialItemAdapter adapter = new MaterialItemAdapter(mContext, R.layout.item_material_item, pagers.get(position));
            adapter.setSelectedValue(allParams.get(currentParameter.getProECode()));
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int p) {
                    String selectedValue = pagers.get(position).get(p).getValue();
                    if (adapter.getSelectedValue() != null && adapter.getSelectedValue().equals(selectedValue)) {
                        ToastUtil.showLong(mContext.getString(R.string.msg2));
                        return;
                    }
                    adapter.setSelectedValue(selectedValue);
                    adapter.notifyDataSetChanged();
                    selected(currentParameter.getProECode(), selectedValue);
                    dismiss();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            container.addView(inflate);
            return inflate;
        }
    }
}
