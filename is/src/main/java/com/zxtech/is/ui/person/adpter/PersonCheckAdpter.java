package com.zxtech.is.ui.person.adpter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.zxtech.is.widget.GlideCircleTransform;
import com.zxtech.is.BuildConfig;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;

import com.zxtech.is.model.person.PersonMember;

import java.util.List;

/**
 * Created by duomi on 2018/4/4.
 */

public class PersonCheckAdpter extends BaseQuickAdapter<PersonMember, BaseViewHolder> {

    public PersonCheckAdpter(int layoutResId, @Nullable List<PersonMember> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonMember item) {
        helper.setText(R.id.is_item_person_2, item.getName());

        if(item.getBlackflag()>0)
        {
            helper.setText(R.id.is_item_person_4, R.string.is_black_list);
        }
        else
        {
            helper.setText(R.id.is_item_person_4, R.string.is_normal);
        }

//           http://192.168.56.1:8080/s1/downloadAttach?guid=615A4702-107B-46C5-AD77-D0678ADB6E34
           String guid = item.getAttachguid();
        if(guid==null ||"".equals(guid)){
            Glide.with(mContext).load(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).transform(new GlideCircleTransform(mContext)).into(((ImageView) helper.getView(R.id.is_item_person_1)));

        }
          else
        {
            String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid="+guid;

            Glide.with(mContext).load(url).error(R.drawable.is_contact_grey).transform(new GlideCircleTransform(mContext)).into(((ImageView) helper.getView(R.id.is_item_person_1)));
        }


    }
}
