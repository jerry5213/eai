package com.zxtech.mt.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * ViewHolder������
 * @author Chw
 *
 */
public class ViewHolder
{
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	
	public ViewHolder(Context context,ViewGroup parent,int layoutId,int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}
	
	public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position)
	{
		if(convertView == null)
		{
			return new ViewHolder(context,parent,layoutId,position);
		}else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}
	
	public View getConvertView()
	{
		return mConvertView;
	}
	/**
	 * ͨ��viewId��ȡ�ؼ�
	 * @param viewId
	 * @return
	 */
	public <T extends View>T getView(int viewId)
	{
		View view = mViews.get(viewId);
		
		if(view ==null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
		
	}
	/**
	 * ����TextView��ֵ
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId,String text)
	{
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	public ViewHolder setImageViewBackground(int viewId,int drawableId)
	{
		ImageView iv = getView(viewId);
		iv.setBackgroundResource(drawableId);
		return this;
	}
	
	public ViewHolder setLinearLayoutBackground(int viewId,int drawableId)
	{
		LinearLayout iv = getView(viewId);
		iv.setBackgroundResource(drawableId);
		return this;
	}
	
	public ViewHolder setTextBackground(int viewId,int drawableId)
	{
		TextView tv = getView(viewId);
		tv.setBackgroundResource(drawableId);
		return this;
	}
	
	public ViewHolder setImageResource(int viewId,int resId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	public ViewHolder setTextColor(int viewId,int colorId){
		TextView tv = getView(viewId);
		tv.setTextColor(colorId);
		return this;
	}
	
	public ViewHolder setTextViewVisable(int viewId,int visable){
		TextView tv = getView(viewId);
		tv.setVisibility(visable);
		return this;
	}

}

