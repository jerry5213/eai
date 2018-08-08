package com.zxtech.ecs.ui.home.qmsmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PhotoDetailAdapter;
import com.zxtech.ecs.model.FileList;
import com.zxtech.ecs.model.QmsFeedbackInfoEntity;
import com.zxtech.ecs.model.QmsMaterialRequirement;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageSimpleActivity;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.ItemDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @auth: hexl
 * @date: 2018/2/28
 * @desc:订单详情
 */

public class QmsMyFeedBackDetailActivity extends BaseActivity {

	@BindView(R.id.toolbar_title)
	TextView mToolbarTitle;
	@BindView(R.id.recycler_view_qms_photo)
	RecyclerView mRecyclerViewQmsPhoto;
	@BindView(R.id.iv_qms_contract_status)
	ImageView mIvQmsContractStatus;
	@BindView(R.id.tv_qms_contract_no)
	TextView mTvQmsContractNo;
	@BindView(R.id.tv_qms_elevator_source)
	TextView mTvQmsElevatorSource;
	@BindView(R.id.tv_qms_service_status)
	TextView mTvQmsServiceStatus;
	@BindView(R.id.tv_qms_open_box_time)
	TextView mTvQmsOpenBoxTime;
	@BindView(R.id.tv_qms_product_no)
	TextView mTvQmsProductNo;
	@BindView(R.id.tv_qms_support_type)
	TextView mTvQmsSupportType;
	@BindView(R.id.iv_qms_invalid_status)
	ImageView mIvQmsInvalidStatus;
	@BindView(R.id.tv_qms_sub_system)
	TextView mTvQmsSubSystem;
	@BindView(R.id.tv_qms_assembly)
	TextView mTvQmsAssembly;
	@BindView(R.id.tv_qms_parts)
	TextView mTvQmsParts;
	@BindView(R.id.tv_qms_spare_parts)
	TextView mTvQmsSpareParts;
	@BindView(R.id.tv_qms_invalid_mode)
	TextView mTvQmsInvalidMode;
	@BindView(R.id.iv_qms_un_materiel_status)
	ImageView mIvQmsUnMaterielStatus;
	@BindView(R.id.tv_qms_cause_feedback)
	TextView mTvQmsCauseFeedback;
	@BindView(R.id.tv_qms_happen_rate)
	TextView mTvQmsHappenRate;
	@BindView(R.id.tv_qms_severity)
	TextView mTvQmsSeverity;
	@BindView(R.id.iv_qms_patch_status)
	ImageView mIvQmsPatchStatus;
	@BindView(R.id.tv_qms_site_contact)
	TextView mTvQmsSiteContact;
	@BindView(R.id.tv_qms_site_contact_mobile)
	TextView mTvQmsSiteContactMobile;
	@BindView(R.id.tv_qms_patch_address)
	TextView mTvQmsPatchAddress;
	@BindView(R.id.tv_qms_patch_contact)
	TextView mTvQmsPatchContact;
	@BindView(R.id.tv_qms_patch_contact_mobile)
	TextView mTvQmsPatchContactMobile;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.common_layout)
	LinearLayout mCommonLayout;
	@BindView(R.id.tv_qms_materiel_arrival_time)
	TextView mTvQmsMaterielArrivalTime;
	@BindView(R.id.tv_qms_rectify_finish_time)
	TextView mTvQmsRectifyFinishTime;
	@BindView(R.id.tv_qms_solve)
	TextView mTvQmsSolve;
	@BindView(R.id.tv_qms_question_desc)
	TextView tv_qms_question_desc;
	@BindView(R.id.rating_bar_qms)
	RatingBar mRatingBarQms;
	@BindView(R.id.tv_qms_detail_evaluate)
	TextView mTvQmsDetailEvaluate;
	@BindView(R.id.tl_contract)
	TableLayout mTlContract;
	@BindView(R.id.tl_invalid)
	TableLayout mTlInvalid;
	@BindView(R.id.tl_un_materiel)
	TableLayout mTlUnMateriel;
	@BindView(R.id.tl_patch)
	TableLayout mTlPatch;
	@BindView(R.id.ll_qms_contract_status)
	LinearLayout mLlQmsContractStatus;
	@BindView(R.id.ll_qms_invalid_status)
	LinearLayout mLlQmsInvalidStatus;
	@BindView(R.id.ll_qms_un_materiel_status)
	LinearLayout mLlQmsUnMaterielStatus;
	@BindView(R.id.ll_un_materiel)
	LinearLayout mLlUnMateriel;
	@BindView(R.id.ll_qms_patch_status)
	LinearLayout mLlQmsPatchStatus;
	@BindView(R.id.iv_qms_materiel)
	ImageView mIvQmsMateriel;
	@BindView(R.id.tl_materiel)
	LinearLayout mTlMateriel;
	@BindView(R.id.ll_qms_materiel)
	LinearLayout mLlQmsMateriel;
	@BindView(R.id.recycleView_mr)
	RecyclerView recycleView;

	private List<QmsMaterialRequirement> qmsMrs = new ArrayList<>();
	private QmsNotMrAdapter qmsNotMrAdapter;
	//附件适配器
	private PhotoDetailAdapter mPhotoAdapter;
	private List<FileList> mFileList;
	private boolean isContactOpen = false;
	private boolean isInvalidOpen = false;
	private boolean isUnMaterielOpen = false;
	private boolean isMaterielOpen = false;
	private boolean isPatchOpen = false;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_qms_my_feed_back_detail;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

		initTitleBar(mToolbar);
		String autoGuid = getIntent().getStringExtra("autoGuid");
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressedSupport();
			}
		});

		mRecyclerViewQmsPhoto.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
		mRecyclerViewQmsPhoto.addItemDecoration(new ItemDivider().setDividerWith(20).setDividerColor(Color.TRANSPARENT));
		mPhotoAdapter = new PhotoDetailAdapter(R.layout.item_qms_img, mFileList);
		mRecyclerViewQmsPhoto.setAdapter(mPhotoAdapter);

		mPhotoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				String url = mPhotoAdapter.getData().get(position).getFileUrl();
				Intent intent = getIntent();
				intent.putExtra("url",url);
				startActivity(ShowBigImageSimpleActivity.class,intent);
			}
		});

		//物料反馈
		qmsNotMrAdapter = new QmsNotMrAdapter(this, R.layout.item_qms_mr2, qmsMrs);
		recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		//添加自定义分割线
		/*DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
		divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_line_transparent));
		recycleView.addItemDecoration(divider);*/
		recycleView.setAdapter(qmsNotMrAdapter);

		requestNet(autoGuid);
	}

	@OnClick({R.id.ll_qms_contract_status, R.id.ll_qms_invalid_status,
			R.id.ll_qms_un_materiel_status, R.id.ll_qms_materiel, R.id.ll_qms_patch_status})
	void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_qms_contract_status:
				if (!isContactOpen) {
					mIvQmsContractStatus.setImageResource(R.drawable.open);
					mTlContract.setVisibility(View.VISIBLE);
					isContactOpen = true;
				} else {
					mIvQmsContractStatus.setImageResource(R.drawable.close);
					mTlContract.setVisibility(View.GONE);
					isContactOpen = false;
				}
				break;
			case R.id.ll_qms_invalid_status:
				if (!isInvalidOpen) {
					mIvQmsInvalidStatus.setImageResource(R.drawable.open);
					mTlInvalid.setVisibility(View.VISIBLE);
					isInvalidOpen = true;
				} else {
					mIvQmsInvalidStatus.setImageResource(R.drawable.close);
					mTlInvalid.setVisibility(View.GONE);
					isInvalidOpen = false;
				}
				break;
			case R.id.ll_qms_un_materiel_status:
				if (!isUnMaterielOpen) {
					mIvQmsUnMaterielStatus.setImageResource(R.drawable.open);
					mTlUnMateriel.setVisibility(View.VISIBLE);
					isUnMaterielOpen = true;
				} else {
					mIvQmsUnMaterielStatus.setImageResource(R.drawable.close);
					mTlUnMateriel.setVisibility(View.GONE);
					isUnMaterielOpen = false;
				}
				break;
			case R.id.ll_qms_materiel:
				if (!isMaterielOpen) {
					mIvQmsMateriel.setImageResource(R.drawable.open);
					mTlMateriel.setVisibility(View.VISIBLE);
					isMaterielOpen = true;
				} else {
					mIvQmsMateriel.setImageResource(R.drawable.close);
					mTlMateriel.setVisibility(View.GONE);
					isMaterielOpen = false;
				}
				break;
			case R.id.ll_qms_patch_status:
				if (!isPatchOpen) {
					mIvQmsPatchStatus.setImageResource(R.drawable.open);
					mTlPatch.setVisibility(View.VISIBLE);
					isPatchOpen = true;
				} else {
					mIvQmsPatchStatus.setImageResource(R.drawable.close);
					mTlPatch.setVisibility(View.GONE);
					isPatchOpen = false;
				}
				break;
		}
	}


	private void requestNet(String id) {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("AutoGuid",id);
		paramsMap.put("Language", Util.convertQmsLanguage(language));
		String params = GsonUtils.toJson(paramsMap, false);
		HttpFactory.getApiService()
				.getAPPFeedbackInfo(params)
				.compose(RxHelper.<BaseResponse<QmsFeedbackInfoEntity>>rxSchedulerHelper())
				.subscribe(new DefaultObserver<BaseResponse<QmsFeedbackInfoEntity>>(this, true) {
					@Override
					public void onSuccess(BaseResponse<QmsFeedbackInfoEntity> response) {
						QmsFeedbackInfoEntity.FeedbackInfoBean bean = response.getData().getFeedbackInfo();
						List<QmsMaterialRequirement> qmsMaterialRequirements = response.getData().getFeedbackPartList();
						mFileList = response.getData().getFileList();
						if(mFileList!=null&&mFileList.size()>0){
							for(FileList fileList:mFileList){
								if("temp.png".equals(fileList.getFileName())){
									mFileList.remove(fileList);
									break;
								}
							}
						}
						if (bean.getW_ZCLX().equals("1")) {//物料
							mLlUnMateriel.setVisibility(View.GONE);
							mLlQmsMateriel.setVisibility(View.VISIBLE);
							qmsMrs.clear();
							qmsMrs.addAll(qmsMaterialRequirements);
							qmsNotMrAdapter.notifyDataSetChanged();
						} else {//非物料
							mLlUnMateriel.setVisibility(View.VISIBLE);
							mLlQmsMateriel.setVisibility(View.GONE);
						}
						mPhotoAdapter.setNewData(mFileList);

						tv_qms_question_desc.setText(bean.getW_WTMS());
						mTvQmsSupportType.setText(bean.getW_ZCLXValue());

						mTvQmsContractNo.setText(bean.getC_ContractNo());
						mTvQmsElevatorSource.setText(bean.getC_DTLY());
						mTvQmsServiceStatus.setText(bean.getC_FWZTValue());
						mTvQmsOpenBoxTime.setText(TextUtils.isEmpty(bean.getC_KXSJ())?"":bean.getC_KXSJ());
						mTvQmsProductNo.setText(bean.getC_CPXH());

						mTvQmsSubSystem.setText(bean.getF_ZXTValue());
						mTvQmsAssembly.setText(bean.getF_ZJValue());
						mTvQmsParts.setText(bean.getF_BJValue());
						mTvQmsSpareParts.setText(bean.getF_LJValue());
						mTvQmsInvalidMode.setText(bean.getF_FailureModeValue());

						mTvQmsCauseFeedback.setText(bean.getNM_FKYY());
						mTvQmsHappenRate.setText(bean.getNM_YZD());
						mTvQmsSeverity.setText(bean.getNM_FSPL());
						//mTvQmsSiteContact.setText(bean.getC_ContractNo());
						mTvQmsSiteContactMobile.setText("");
						mTvQmsPatchAddress.setText(bean.getP_BJDZ());
						mTvQmsPatchContact.setText(bean.getP_BJLXR());
						mTvQmsPatchContactMobile.setText(bean.getP_BJLXDH());

						mTvQmsMaterielArrivalTime.setText(bean.getE_WLDHSJ());
						mTvQmsRectifyFinishTime.setText(bean.getE_ZGWCSJ());
						mTvQmsSolve.setText(bean.getE_JJQK());
						if (!TextUtils.isEmpty(bean.getE_MYDDF())) {
							mRatingBarQms.setRating(Float.valueOf(bean.getE_MYDDF()));
						}
						mTvQmsDetailEvaluate.setText(bean.getE_XXPJ());
					}
				});
	}

}
