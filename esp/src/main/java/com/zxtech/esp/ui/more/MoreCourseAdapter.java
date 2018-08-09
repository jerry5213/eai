package com.zxtech.esp.ui.more;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zxtech.common.download.DownloadUtils;
import com.zxtech.common.download.FileDownloadDialog;
import com.zxtech.common.download.SimpleDialog;
import com.zxtech.common.util.F;
import com.zxtech.common.util.T;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.CourseVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.ZipExtractorTask;

import java.io.File;

import g.api.adapter.GBaseAdapter;
import g.api.adapter.GViewHolder;

/**
 * Created by SYP521 on 2017/7/4.
 */

public class MoreCourseAdapter extends GBaseAdapter<CourseVO.Data> {

    private Context mContext;

    public MoreCourseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(mContext);
            convertView = viewHolder.getConvertView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.showData(getItem(position), position, getCount());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    static class ViewHolder extends GViewHolder implements View.OnClickListener {

        private TextView courseName;
        //private TextView courseDesc;
        private TextView tv_score;
        private TextView openBtn;
        private ImageView iv_course;
        private TextView tv_praise;
        private TextView tv_praise_val;
        private TextView tv_comment;
        private TextView tv_comment_val;
        private TextView tv_line;

        DisplayImageOptions opts;

        public ViewHolder(Context context) {
            super(context);

            opts = new DisplayImageOptions.Builder()
                    .showImageOnFail(R.mipmap.img_null)
                    .showImageForEmptyUri(R.mipmap.img_null)//设置图片Uri为空或是错误的时候显示的图片
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(T.dip2px(context, 15.0f)))
                    .build();

            iv_course = findView(R.id.iv_course);
            courseName = findView(R.id.tv_coursename);
            tv_score = findView(R.id.tv_score);
            openBtn = findView(R.id.tv_open);
            openBtn.setOnClickListener(this);
            tv_praise = findView(R.id.tv_praise);
            tv_praise_val = findView(R.id.tv_praise_val);
            tv_comment = findView(R.id.tv_comment);
            tv_comment_val = findView(R.id.tv_comment_val);
            tv_line = findView(R.id.tv_line);
        }

        @Override
        protected int onSetConvertViewResId() {
            return R.layout.adapter_more_list_course;
        }

        public void showData(CourseVO.Data data, int position, int count) {

            if (position == count - 1) {
                tv_line.setVisibility(View.GONE);
            } else {
                tv_line.setVisibility(View.VISIBLE);
            }

            ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(data.getImage_url()), iv_course, opts);
            courseName.setText(data.getCourse_name());
            //courseDesc.setText(data.getCourse_desc());
            tv_score.setText(data.getCredits());
            openBtn.setTag(data);
            tv_praise.setTag(data.getCourse_id());
            BizUtil.setIconFont(tv_praise, "\ue870");
            BizUtil.setIconFont(tv_comment, "\ue611");
            if ("1".equals(data.getIs_praise())) {
                tv_praise.setTextColor(Color.parseColor("#FF4081"));
            } else {
                tv_praise.setTextColor(Color.parseColor("#323232"));
            }
            tv_praise.setOnClickListener(this);
            tv_praise_val.setText(data.getPraise_num());
            tv_comment_val.setText(data.getComment_num());
        }

        @RequiresApi(api = Build.VERSION_CODES.FROYO)
        @Override
        public void onClick(final View v) {
            int i = v.getId();
            if (i == R.id.tv_open) {
                final CourseVO.Data data = (CourseVO.Data) v.getTag();
                File dir = new File(context.getExternalCacheDir(), F.getNoExName(DownloadUtils.getFileName(URL.getInstance().getFullUrl(data.getFile_url()))));
                if (null != dir && dir.exists()) {
                    if (dir.isDirectory()) {
                        Intent intent = new Intent(context, CourseDetailActivity.class);
                        intent.putExtra(C.DATA, data);
                        context.startActivity(intent);
                    }
                } else {
                    File file = new File(context.getExternalCacheDir(), DownloadUtils.getFileName(URL.getInstance().getFullUrl(data.getFile_url())));
                    if (file.exists()) {
                        new ZipExtractorTask(file, dir, context, true) {
                            @Override
                            protected void onPostExecute(Long result) {
                                super.onPostExecute(result);
                                Intent intent = new Intent(context, CourseDetailActivity.class);
                                intent.putExtra(C.DATA, data);
                                context.startActivity(intent);
                            }
                        }.execute();
                    } else {

                        Context ctx = v.getContext();
                        final String invalid = ctx.getResources().getString(R.string.invalid_download_address);
                        String tip = ctx.getResources().getString(R.string.tip);
                        String course_ware_nothing = ctx.getResources().getString(R.string.course_ware_nothing);
                        final String downloading = ctx.getResources().getString(R.string.downloading);
                        final String complete = ctx.getResources().getString(R.string.complete);
                        final String failed = ctx.getResources().getString(R.string.failed);
                        String ok = ctx.getResources().getString(R.string.upgrade_dialog_ok);
                        String cancel = ctx.getResources().getString(R.string.upgrade_dialog_cancel);

                        final String url = URL.getInstance().getFullUrl(data.getFile_url());

                        AlertDialog dialog = SimpleDialog.createDialog(context,tip,course_ware_nothing,ok,cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                if(TextUtils.isEmpty(data.getFile_url())){
                                    T.showToast(v.getContext(),invalid);
                                    return;
                                }

                                AlertDialog alertDialog = FileDownloadDialog.createDialog(context,downloading, (CharSequence) null, new DownloadUtils.DownloadListener() {
                                    public void onDownloadComplete(String downloadUrl, String tag) {
                                        T.showToast(context,complete);
                                    }

                                    public void onDownloadFailed(String downloadUrl, String tag, int errorCode) {
                                        if (errorCode == 1008) {
                                            //T.showToast(context, "下载取消");
                                        } else {
                                            T.showToast(context,failed);
                                        }

                                    }

                                    public void onProgress(long totalBytes, long downloadedBytes, int progress) {
                                    }
                                }, url, context.getExternalCacheDir().getAbsolutePath(), DownloadUtils.getFileName(url), url);
                                if (alertDialog != null) {
                                    alertDialog.show();
                                }
                            }
                        },new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, false);
                        dialog.show();
                    }
                }
            } else if (i == R.id.tv_praise) {
                String courseId = v.getTag().toString();
                ((MoreCourseActivity) getActivityFromView(v)).praiseCourse(courseId);

            } else {
            }
        }
    }

    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
