package com.zxtech.mt.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**�̳� CommonAdapter<Bean> */
/**
 * ͨ��Adapter
 * @author Chw
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter 
{
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	private int layoutId;
	
	public CommonAdapter(Context context,List<T> datas,int layoutId)
	{
		this.mContext = context;
		this.mDatas = datas;
		this.layoutId = layoutId;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() 
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position) 
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public  View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
		
		convert(holder,getItem(position),position);
		
		return holder.getConvertView();
	}
	
	public abstract void convert(ViewHolder holder,T t,int position);
	
}
