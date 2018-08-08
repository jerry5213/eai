package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.CompanyProduct;
import com.zxtech.ecs.util.OfficePoiUtil;

import java.io.File;
import java.util.List;

import static com.zxtech.ecs.APPConfig.DOWN_LOAD_PATH;

/**
 * Created by syp523 on 2018/5/4.
 */

public class ProductTitleAdapter extends CommonAdapter<CompanyProduct.ResultInfoBean> {

    private ProductTitleAdapterCallBack callBack;

    public ProductTitleAdapter(Context context, int layoutId, List<CompanyProduct.ResultInfoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final CompanyProduct.ResultInfoBean resultInfoBean, int position) {
        holder.setText(R.id.category_tv, resultInfoBean.getSeatName());
        RecyclerView file_rv = holder.getView(R.id.file_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        file_rv.setLayoutManager(layoutManager);
        CommonAdapter<CompanyProduct.ResultInfoBean.CompanyInfoListBean> adapter = new CommonAdapter<CompanyProduct.ResultInfoBean.CompanyInfoListBean>(mContext, R.layout.item_product_elevator, resultInfoBean.getCompanyInfoList()) {

            @Override
            protected void convert(ViewHolder holder, CompanyProduct.ResultInfoBean.CompanyInfoListBean item, int position) {
                ImageView imageView = holder.getView(R.id.iv_img);
                if (!item.getFileType().contains("pdf")) {
                    holder.setVisible(R.id.iv_type, false);
                } else {
                    holder.setVisible(R.id.iv_type, true);
                }
                Glide.with(mContext)
                        .load(item.getCoverPath())
                        .error(R.drawable.default_image)
                        .placeholder(R.drawable.default_image)
                        .into(imageView);
            }
        };
        file_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                CompanyProduct.ResultInfoBean.CompanyInfoListBean companyInfoListBean = resultInfoBean.getCompanyInfoList().get(position);
                switch (companyInfoListBean.getFileType()) {
                    case "pdf":
                        if (companyInfoListBean.getFileInfoList().size() > 0) {
                            File file = new File(DOWN_LOAD_PATH, companyInfoListBean.getFileInfoList().get(0).getFileName());
                            if (file.exists()) {//如果已存在,直接打开
                                OfficePoiUtil.openFile(mContext, file);
                            } else {//文件不存在,下载后打开
                                if (callBack != null) {
                                    callBack.download(companyInfoListBean.getFileInfoList().get(0).getFileName(), companyInfoListBean.getFileInfoList().get(0).getPath());
                                }
                            }
                        }

                        break;
                    case "jpg":
                    case "png":
//                        intent.setClass(mContext, ShowBigImageActivity.class);
//                        intent.putParcelableArrayListExtra("images", (ArrayList<? extends Parcelable>) fileInfoList);
//                        startActivity(intent);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    public void setCallBack(ProductTitleAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ProductTitleAdapterCallBack {
        void download(String fileName, String path);
    }

}
