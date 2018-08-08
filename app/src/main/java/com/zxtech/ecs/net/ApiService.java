package com.zxtech.ecs.net;


import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.zxtech.ecs.model.AccountPayment;
import com.zxtech.ecs.model.AccountPaymentProject;
import com.zxtech.ecs.model.Agent;
import com.zxtech.ecs.model.AgentOrg;
import com.zxtech.ecs.model.AppVersion;
import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.BidDetail;
import com.zxtech.ecs.model.BidReview;
import com.zxtech.ecs.model.ChatMessage2;
import com.zxtech.ecs.model.Collection;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.model.CompanyProduct;
import com.zxtech.ecs.model.CompanySubTitleEntity;
import com.zxtech.ecs.model.ContractChange;
import com.zxtech.ecs.model.ContractChangeSummary;
import com.zxtech.ecs.model.ContractDeliveryPoints;
import com.zxtech.ecs.model.ContractInfo;
import com.zxtech.ecs.model.ContractReview;
import com.zxtech.ecs.model.DesignApply;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.Engineering;
import com.zxtech.ecs.model.GenerateAnswer;
import com.zxtech.ecs.model.InstallUnit;
import com.zxtech.ecs.model.LogisticsInfoEntity;
import com.zxtech.ecs.model.NewProductDropDown;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.PayMethod;
import com.zxtech.ecs.model.PayNode;
import com.zxtech.ecs.model.PriceApproval;
import com.zxtech.ecs.model.ProductCancel;
import com.zxtech.ecs.model.ProductDetail;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.model.ProjectDetail;
import com.zxtech.ecs.model.ProjectDropDown;
import com.zxtech.ecs.model.ProjectProductInfo;
import com.zxtech.ecs.model.ProjectQuote;
import com.zxtech.ecs.model.ProjectTrack;
import com.zxtech.ecs.model.QmsAddressList;
import com.zxtech.ecs.model.QmsFeedbackInfoEntity;
import com.zxtech.ecs.model.QmsMyFeedBackEntity;
import com.zxtech.ecs.model.QuotePrice;
import com.zxtech.ecs.model.SaveAPPFeedbackInfoEntity;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.model.SystemCodeListEntity;
import com.zxtech.ecs.ui.home.bid.BidResultInfo;
import com.zxtech.gks.model.bean.CityBean;
import com.zxtech.gks.model.bean.DistrictBean;
import com.zxtech.gks.model.bean.DxmZY;
import com.zxtech.gks.model.bean.GetProjectDetailDropDownList;
import com.zxtech.gks.model.bean.Province;
import com.zxtech.gks.model.bean.ReserveMoneyDetail;
import com.zxtech.gks.model.bean.SaveResult;
import com.zxtech.gks.model.bean.SpecialNonStandard;
import com.zxtech.gks.model.vo.CommissionRate;
import com.zxtech.gks.model.vo.Customer;
import com.zxtech.gks.model.vo.FbParamBean;
import com.zxtech.gks.model.vo.PageParamBean;
import com.zxtech.gks.model.vo.PrProduct;
import com.zxtech.gks.model.vo.PrProductDetail.PrProductDetail;
import com.zxtech.gks.model.vo.PrProductDetail.WorkFlowNodeListBean;
import com.zxtech.gks.model.vo.ProjectRecord;
import com.zxtech.gks.model.vo.RecordApproval;
import com.zxtech.gks.model.vo.SaleProjectReportDetail;
import com.zxtech.gks.model.vo.contract.ContractData;
import com.zxtech.gks.model.vo.contract.ContractDetail;
import com.zxtech.gks.model.vo.login.LoginVO;
import com.zxtech.gks.model.vo.type.BuildingCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * Created by dell on 2017/4/1.
 */

public interface ApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 45000;


    @GET("compareCollection")
    Observable<BaseResponse<List<Programme>>> compareCollection(@Query("ids") String ids);

    @GET("getCollectionDetail")
    Observable<BaseResponse<Programme>> getCollectionDetail(@Query("id") String id);

    @GET("getCollectionList")
    Observable<BaseResponse<List<Collection>>> getCollectionList(@Query("id") String id, @Query("typeId") String typeId);

    @GET("deleteCollection")
    Observable<BaseResponse<String>> deleteCollection(@Query("id") String id);

    @GET("getParams")
    Observable<BaseResponse<List<Parameters>>> getParams(@Query("typeId") String typeId, @Query("elevatorType") String elevatorType, @Query("elevatorProduct") String elevatorProduct, @Query("seatId") String seatId, @Query("proECode") String proECode);

    @GET("scriptParamsLink")
    Observable<BaseResponse<List<ScriptReturnParam>>> scriptParamsLink(@Query("params") String params);


    @POST("dimensionByParameter")
    Observable<BaseResponse<Programme>> dimensionByParameter(@QueryMap Map<String, String> paramsMap);


    @GET("getHomeAdvert")
    Observable<BaseResponse<List<Map<String, String>>>> getHomeAdvert(@Query("subSeatId") String subSeatId);

    @GET("getCompanyInfo")
    Observable<BaseResponse<CompanyEntity>> getCompanyInfo(
            @Query("type") String type,
            @Query("seatId") String seatId);

    @GET("getCompanySubTitle")
    Observable<BaseResponse<CompanySubTitleEntity>> getCompanySubTitle(
            @Query("type") String type,
            @Query("seatId") String seatId);

    @GET("getProductIntroductiono")
    Observable<BaseResponse<CompanyEntity>> getProductIntroductiono(
            @Query("type") String type,
            @Query("seatId") String seatId,
            @Query("subSeatId") String subSeatId);


    @FormUrlEncoded
    @POST("GetSystemCode")
    Observable<BaseResponse<SystemCodeListEntity>> getSystemCode(@NonNull @Field("params") String params);

    @POST("SaveAPPFeedbackInfo")
    Observable<BaseResponse<SaveAPPFeedbackInfoEntity>> saveAPPFeedbackInfo(@NonNull @Query("params") String params);

    @GET("GetProjectDetail.do")
    Observable<BasicResponse<ProjectRecord>> getProjectDetail(@Query("ProjectGuid") String projectGuid);

    @GET("getOptions")
    Observable<BaseResponse<List<Parameters.Option>>> getOptions(@Query("code") String code, @Query("elevatorType") String elevatorType, @Query("elevatorProduct") String elevatorProduct);

    @GET("SubmitProject.do")
    Observable<BasicResponse> submitProject(@Query("ProjectGuid") String projectGuid, @Query("submitDescription") String desc, @Query("UserNo") String userNo, @Query("submitResult") String submitResult);

    @GET("GetRepeatProjectByPage.do")
    Observable<BasicResponse<PageParamBean<RecordApproval>>> getRepeatProjectByPage(@Query("ProjectName") String projectName, @Query("CustomerName") String customerName, @Query("RelationType") String type, @Query("DeptNo") String deptNo);

    @GET("CloseProject.do")
    Observable<BasicResponse> closeProject(@Query("ProjectGuid") String projectGuid, @Query("UserId") String userId);

    @POST("getTodoProjectByPage.do")
    Observable<BasicResponse<PageParamBean<RecordApproval>>> getTodoProjectByPage(@QueryMap Map<String, String> params);

    @POST("getPRIndexNotSubmitByPage.do")
    Observable<BasicResponse<PageParamBean<PrProduct>>> getPRIndexNotSubmitByPage(@QueryMap Map<String, String> params);

    @GET("getSpecialNonStandardList.do")
    Observable<BasicResponse<FbParamBean<SpecialNonStandard>>> getSpecialNonStandardList(@Query("guid") String guid);

    @GET("getProjectPriceApprovalListByPage.do")
    Observable<BasicResponse<PageParamBean<PrProduct>>> getProjectPriceApprovalListByPage(@QueryMap Map<String, String> params);

    @GET("getContractById.do")
    Observable<BasicResponse<ContractDetail>> getContractById(@QueryMap Map<String, String> params);

    @POST("saveProject")
    Observable<BasicResponse> saveProject(@NonNull @Query("params") String params);

    @GET("submitCMSContractWorkFlow.do")
    Observable<BasicResponse> submitCMSContractWorkFlow(@QueryMap Map<String, String> params);

    @GET("getContractByPage.do")
    Observable<BasicResponse<PageParamBean<ContractReview>>> getContractByPage(@QueryMap Map<String, String> params);

    @GET("loginUser")
    Observable<BaseResponse<LoginVO>> getLoginInfo(@QueryMap Map<String, String> params);

    @GET("loginUser")
    Call<BaseResponse<LoginVO>> autoLoginInfo(@QueryMap Map<String, String> params);

    @GET("getCustomerByPage.do")
    Observable<BasicResponse<PageParamBean<Customer>>> getCustomerByPage(@QueryMap Map<String, String> params);

    @GET("GetProjectDetail.do")
    Observable<BasicResponse<SaleProjectReportDetail>> GetProjectDetail(@Query("ProjectGuid") String guid);

    @GET("deleteCustomer.do")
    Observable<BasicResponse> deleteCustomer(@Query("guidAtr") String guidAtr);

    @GET("savePriceReview.do")
    Observable<BasicResponse<SaveResult>> savePriceReview(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("GetSystemCodeSub")
    Observable<BaseResponse<SystemCodeListEntity>> getSystemCodeSub(@NonNull @Field("params") String params);

    @FormUrlEncoded
    @POST("GetAppSystemCodeSXMS")
    Observable<BaseResponse<SystemCodeListEntity>> getAppSystemCodeSXMS(@NonNull @Field("params") String params);

    @GET("getGoods")
    Observable<BaseResponse<Map<String, List<Map<String, String>>>>> getGoods(
            @Query("partId") int partId, @Query("spec") String spec, @Query("startPage") int startPage, @Query("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST("GetAPPFeedbackInfo")
    Observable<BaseResponse<QmsFeedbackInfoEntity>> getAPPFeedbackInfo(@NonNull @Field("params") String params);

    @Multipart
    @POST("photoGoods")
    Observable<BaseResponse<List<Map<String, String>>>> photoGoods(@Query("param") String param,
                                                                   @PartMap Map<String, RequestBody> bodyMap
    );

    @Multipart
    @POST("SaveAPPFeedbackInfo")
    Observable<BaseResponse<SaveAPPFeedbackInfoEntity>> SaveAPPFeedbackInfo(@Query("param") String param, @Query("param2") String param2,
                                                                            @Query("deleteFile") String deleteFile,
                                                                            @PartMap Map<String, RequestBody> bodyMap
    );

    @POST("qmsvoiceRequest")
    Observable<BaseResponse<ChatMessage2<Map<String, String>>>> qmsVoiceRequest(@NonNull @Query("voice") String voice);

    @FormUrlEncoded
    @POST("GetAPPFeedbackList")
    Observable<BaseResponse<QmsMyFeedBackEntity>> getAPPFeedbackList(@Field("params") String params);

    @POST("SaveAppFeedbackInfoEvaluate")
    Observable<BaseResponse<SaveAPPFeedbackInfoEntity>> saveFeedbackInfoEvaluate(@NonNull @Query("params") String params);

    @POST("actionRequest")
    Observable<BaseResponse<String>> actionRequest(@NonNull @Query("id") String id);

    @POST("GetAppLogisticsInfo")
    Observable<BaseResponse<LogisticsInfoEntity>> getLogisticsInfo(@NonNull @Query("params") String params);

    @GET("getVersion")
    Observable<BaseResponse<AppVersion>> getVersion(@NonNull @Query("platform") String platform);

    @POST("customScripts")
    Observable<BaseResponse<String>> customScripts(@NonNull @Query("params") String params);

    @GET("recommendScheme")
    Observable<BaseResponse<List<Programme>>> recommendScheme(@QueryMap Map<String, String> paramsMap);

    @GET("reduceConfigure")
    Observable<BaseResponse<List<Programme>>> reduceConfigure(@NonNull @Query("params") String params);

    @POST("processRequest")
    Observable<BaseResponse<String>> processRequest(
            @NonNull @Query("voice") String voice
    );

    @Streaming
    @POST("voiceRequest")
    Observable<BaseResponse<String>> voiceRequest(
            @NonNull @Query("voice") String voice
    );

    @GET("getSchemeParam")
    Observable<BaseResponse<Programme>> getSchemeParam(@QueryMap Map<String, String> paramsMap);

    @GET("recommendSchemeScript")
    Observable<BaseResponse<Map<String, String>>> recommendSchemeScript(@NonNull @Query("params") String params);

    @GET("getSchemeDimension")
    Observable<BaseResponse<Programme>> getSchemeDimension(@QueryMap Map<String, String> paramsMap);

    @GET("getProjectOfferList")
    Observable<BaseResponse<List<ProjectQuote>>> getProjectOfferList(@NonNull @Query("userNo") String userNo);

    @GET("getEngineeringList")
    Observable<BaseResponse<List<Engineering>>> getEngineeringList(@NonNull @Query("userNo") String userNo, @NonNull @Query("roleNo") String roleNo);

    @GET("getDesignApplyList")
    Observable<BaseResponse<List<DesignApply>>> getDesignApplyList(@NonNull @Query("userNo") String userNo);

    @GET("getProjectProductInfo")
    Observable<BaseResponse<ProjectProductInfo>> getProjectProductInfo(@NonNull @Query("projectGuid") String projectGuid);

    @GET("getProductList")
    Observable<BaseResponse<List<ProductInfo>>> getProductList(@NonNull @Query("projectGuid") String projectGuid,
                                                               @NonNull @Query("transactUserNo") String transactUserNo,@Query("contractChangeGuid") String contractGuid,@Query("EQSState") String EQSState,@Query("isChange") String isChange);

    @GET("getPriceTableList")
    Observable<BaseResponse<List<QuotePrice>>> getPriceTableList(@NonNull @Query("projectGuid") String projectGuid, @NonNull @Query("userNo") String userNo);

    @GET("getProjectDropDownList")
    Observable<BaseResponse<ProjectDropDown>> getProjectDropDownList();

    @GET("getUndistributedElevatorNoList")
    Observable<BaseResponse<Map<String, String>>> getUndistributedElevatorNoList(@NonNull @Query("EQSGuids") String EQSGuids);

    @GET("saveElevatorAssignInfo")
    Observable<BaseResponse<String>> saveElevatorAssignInfo(@NonNull @Query("jsonParams") String jsonParams);

    @GET("GetAppAddressList")
    Observable<BaseResponse<QmsAddressList>> getAppAddressList(@NonNull @Query("params") String params);

    @GET("SaveAppAddress")
    Observable<BaseResponse> saveAppAddress(@NonNull @Query("params") String params);

    @GET("GetAppContractInfo")
    Observable<BaseResponse<ContractInfo>> getAppContractInfo(@NonNull @Query("params") String params);

    @GET("GenerateAnswer")
    Observable<BaseResponse<GenerateAnswer>> generateAnswer(@NonNull @Query("params") String params);

    @GET("GetAppFailureModeInfo")
    Observable<BaseResponse<Map<String, String>>> getAppFailureModeInfo(@NonNull @Query("params") String params);

    @Multipart
    @POST("submitFeedback")
    Observable<BaseResponse<String>> submitFeedback(
            @PartMap Map<String, RequestBody> bodyMap
    );

    @POST("registeredUser")
    Observable<BaseResponse<String>> registeredUser(@QueryMap Map<String, String> paramsMap);

    @POST("updateUser")
    Observable<BaseResponse<String>> updateUser(@NonNull @Query("params") String params);

    @POST("getVerificationCode")
    Observable<BaseResponse<String>> getVerificationCode(@NonNull @Query("phoneNumber") String phoneNumber);

    @POST("applyDrawing")
    Observable<BaseResponse<String>> applyDrawing(@QueryMap Map<String, String> params);

    @POST("applyDecorationDesign")
    Observable<BaseResponse<String>> applyDecorationDesign(@NonNull @Query("params") String params);

    @POST("modifyPassword")
    Observable<BaseResponse<String>> modifyPassword(@Query("userId") String userId, @Query("userNo") String userNo, @Query("oldPassword") String oldPassword, @NonNull @Query("password") String password, @NonNull @Query("type") String type);

    @POST("getEscCollectionDetail")
    Observable<BaseResponse<Map<String, String>>> getEscCollectionDetail(@NonNull @Query("id") String id);

    @POST("calculateEscPrice")
    Observable<BaseResponse<String>> calculateEscPrice(@QueryMap Map<String, String> params);

    @POST("getSupportTelephone")
    Observable<BaseResponse<Map<String, String>>> getSupportTelephone(@NonNull @Query("agent") String agent);

    @POST("sendBomOrder")
    Observable<BaseResponse<String>> sendBomOrder(@NonNull @Query("guid") String guid);

    @Multipart
    @POST("addOrUpdateCollection")
    Observable<BaseResponse<Map<String, String>>> addOrUpdateCollection(@PartMap Map<String, RequestBody> bodyMap);

    @POST("delNonStandardFile")
    Observable<BaseResponse<String>> delNonStandardFile(@NonNull @Query("param") String param);

    @Multipart
    @POST("addNonStandardFile")
    Observable<BaseResponse<String>> addNonStandardFile(@PartMap Map<String, RequestBody> bodyMap);

    @POST("getProductDropDownList")
    Observable<BaseResponse<NewProductDropDown>> getProductDropDownList(@NonNull @Query("typeId") String typeId);

    @POST("saveChildProduct")
    Observable<BaseResponse> saveChildProduct(@NonNull @Query("param") String param);

    @GET("getCompanyProductInfo")
    Observable<BaseResponse<CompanyProduct>> getCompanyProductInfo(
            @Query("seatId") String seatId
    );

    @GET("getEquipmentPriceList")
    Observable<BaseResponse<ProductDetail>> getEquipmentPriceList(
            @NonNull @Query("guid") String guid
    );

    @GET("getInstallation")
    Observable<BaseResponse<Map<String, String>>> getInstallation(
            @NonNull @Query("EQSProductGuid") String EQSProductGuid
    );

    @POST("updateDiscount")
    Observable<BaseResponse> updateDiscount(
            @NonNull @Query("params") String params
    );

    @POST("confirmVersion")
    Observable<BaseResponse> confirmVersion(
            @NonNull @Query("EQSGuid") String EQSGuid
    );

    @GET("getElevatorTypeDropDownList")
    Observable<BaseResponse<NewProductDropDown>> getElevatorTypeDropDownList(@NonNull
                                                                             @Query("elevatorProduct") String elevatorProduct
    );

    @GET("getTypeIdAndOptionList")
    Observable<BaseResponse<NewProductDropDown>> getTypeIdAndOptionList(@NonNull
                                                                        @Query("elevatorProduct") String elevatorProduct, @NonNull
                                                                        @Query("elevatorType") String elevatorType
    );

    @POST("saveParameter")
    Observable<BaseResponse> saveParameter(@NonNull @Query("param") String param);

    @POST("getProjectPriceApprovalList")
    Observable<BaseResponse<List<PriceApproval>>> getProjectPriceApprovalList(@NonNull @Query("userNo") String userNo);


    @GET("getQuotationDocument")
    Observable<BaseResponse<String>> getQuotationDocument(@NonNull @Query("guid") String guid);


    @GET("getContractList")
    Observable<BaseResponse<List<ContractData>>> getContractList(@NonNull @Query("userNo") String userNo);

    @GET("getCityList")
    Observable<BaseResponse<ContractDeliveryPoints>> getCityList(@NonNull @Query("provinceCode") String provinceCode, @NonNull @Query("cityCode") String cityCode);

    @GET("getStandardParam")
    Observable<BaseResponse<JsonObject>> getStandardParam(@NonNull @Query("params") String params);


    @GET("getContractDocument")
    Observable<BaseResponse<String>> getContractDocument(@NonNull @Query("contractGuid") String contractGuid, @NonNull @Query("contractTypeId") int contractTypeId, @NonNull @Query("userId") String userId);

    ///////

    @POST("applyEngineerDraw")
    Observable<BaseResponse> applyEngineerDraw(@NonNull @Query("params") String params);

    @GET("getEngineeringDocument")
    Observable<BaseResponse<String>> getEngineeringDocument(@NonNull @Query("taskGuId") String taskGuId);

    @GET("getProductParams")
    Observable<BaseResponse<Map<String, String>>> getProductParams(@NonNull @Query("guid") String guid);

    @POST("applyDesignDraw")
    Observable<BaseResponse> applyDesignDraw(@NonNull @Query("params") String params);

    @GET("getContractByApply")
    Observable<BaseResponse<List<ContractData>>> getContractByApply(@NonNull @Query("userNo") String userNo);

    @GET("getContractByApplying")
    Observable<BaseResponse<List<ContractData>>> getContractByApplying(@NonNull @Query("userNo") String userNo);

    @GET("getDrawDocument")
    Observable<BaseResponse<ArrayList<String>>> getDrawDocument(@NonNull @Query("taskGuId") String taskGuId);

    @POST("getAllProjectListByPage.do")
    Observable<BasicResponse<PageParamBean<PrProduct>>> getAllProjectListByPage(@QueryMap Map<String, String> params);

    @POST("getWorkFlowList")
    Observable<BasicResponse<List<WorkFlowNodeListBean>>> getWorkFlowList(@NonNull @Query("taskId") String taskId);

    @POST("createStandardContract")
    Observable<BaseResponse<String[]>> createStandardContract(@NonNull @Query("contractGuid") String contractGuid, @NonNull @Query("roleNo") String roleNo, @NonNull @Query("userId") String userId);

    @POST("saveAndSubmitContract")
    Observable<BaseResponse> saveAndSubmitContract(@NonNull @Query("contractGuid") String contractGuid, @NonNull @Query("contractRemark") String contractRemark, @NonNull @Query("roleNo") String roleNo, @NonNull @Query("userNo") String userNo, @NonNull @Query("userName") String userName, @NonNull @Query("operateType") String operateType);

    @POST("getContactDetail")
    Observable<BaseResponse<ContractDetail>> getContactDetail(@NonNull @Query("guid") String guid, @NonNull @Query("userId") String userId, @NonNull @Query("userNo") String userNo, @NonNull @Query("userName") String userName, @NonNull @Query("roleNo") String roleNo, @NonNull @Query("deptNo") String deptNo);

    @GET("getProjectProgressInfo")
    Observable<BaseResponse<Map<String, String>>> getProjectProgressInfo(@NonNull @Query("projectNo") String projectNo);

    @GET("sendProjectData")
    Observable<BaseResponse> sendProjectData(@QueryMap Map<String, String> params);

    @GET("updatePriceVersionNum")
    Observable<BaseResponse<Map<String, String>>> updatePriceVersionNum(@NonNull @Query("productGuid") String productGuid, @NonNull @Query("userId") String userId);

    @GET("removeWorkFlow")
    Observable<BaseResponse> removeWorkFlow(@NonNull @Query("productGuid") String productGuid, @NonNull @Query("userNo") String userNo, @NonNull @Query("userName") String userName);

    @GET("copyChildProduct")
    Observable<BaseResponse> copyChildProduct(@NonNull @Query("productGuid") String productGuid, @NonNull @Query("copyCount") String copyCount, @NonNull @Query("projectGuid") String projectGuid, @NonNull @Query("projectNo") String projectNo, @NonNull @Query("userId") String userId);

    @GET("getPayTypeList")
    Observable<BaseResponse<List<PayMethod>>> getPayTypeList(@NonNull @Query("projectGuid") String projectGuid);

    @GET("getAgentList")
    Observable<BaseResponse<List<Agent>>> getAgentList(
            @Query("partnerName") String partnerName, @Query("org") String org, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize
    );

    @GET("getAgentOrgList")
    Observable<BaseResponse<List<AgentOrg>>> getAgentOrgList();

    @GET("getInstallationUnitList")
    Observable<BaseResponse<List<InstallUnit>>> getInstallationUnitList(@NonNull @Query("partnerNumber") String partnerNumber);

    @POST("getPriceTypeGenList")
    Observable<BasicResponse<List<ReserveMoneyDetail>>> getPriceTypeGenList(@QueryMap Map<String, String> params);

    @POST("getSaleBranchNoByDeptNo")
    Observable<BasicResponse<Map>> getSaleBranchNoByDeptNo(@QueryMap Map<String, String> params);

    @GET("getProjectPriceApprovalDetail")
    Observable<BasicResponse<PrProductDetail>> getProjectPriceApprovalDetail(@QueryMap Map<String, String> params);

    @GET("getCalculateAgentCommissionRateAndRealFloatingRate")
    Observable<BasicResponse<List<CommissionRate>>> getCalculateAgentCommissionRateAndRealFloatingRate(@QueryMap Map<String, String> params);

    @GET("getCalculateAgentCommissionAndRealFloatingRate")
    Observable<BasicResponse<List<CommissionRate>>> getCalculateAgentCommissionAndRealFloatingRate(@QueryMap Map<String, String> params);

    @GET("saveTransactoResult")
    Observable<BasicResponse> saveTransactoResult(@QueryMap Map<String, String> params);

    @GET("submitPriceReview")
    Observable<BasicResponse> submitPriceReview(@QueryMap Map<String, String> params);

    @POST("getBuildingCharacterList")
    Observable<BasicResponse<List<BuildingCharacter>>> getBuildingCharacterList();

    @POST("getDxmzyUserList")
    Observable<BasicResponse<List<DxmZY>>> getDxmzyUserList();

    @POST("getProvinceList")
    Observable<BasicResponse<List<Province>>> getProvinceList();

    @POST("getCityListByProvinceCode")
    Observable<BasicResponse<CityBean>> getCityListByProvinceCode(@QueryMap Map<String, String> params);

    @POST("getAreaList")
    Observable<BasicResponse<DistrictBean>> getAreaList(@QueryMap Map<String, String> params);

    @POST("saveCustomemr")
    Observable<BasicResponse> saveCustomemr(@QueryMap Map<String, String> params);

    @POST("getProjectNo")
    Observable<BasicResponse> getProjectNo();


    @GET("getContractChangeList")
    Observable<BaseResponse<List<ContractChange>>> getContractChangeList(@NonNull @Query("userNo") String userNo, @NonNull @Query("state") int state);

    @POST("getProjectDetailDropDownList")
    Observable<BasicResponse<GetProjectDetailDropDownList>> getProjectDetailDropDownList();

    @POST("createContractChangeInfo")
    Observable<BaseResponse<Map<String,String>>> createContractChangeInfo(@QueryMap Map<String, String> params);

    @POST("getEQSProductVersion")
    Observable<BaseResponse<Map<String,Object>>> getEQSProductVersion(@NonNull @Query("params") String params);

    @POST("saveChangeElevator")
    Observable<BaseResponse> saveChangeElevator(@NonNull @Query("params") String params,@NonNull @Query("state") int state);

    @POST("getChangeInfo")
    Observable<BaseResponse<ContractChangeSummary>> getChangeInfo(@NonNull @Query("contractChangeGuid") String contractChangeGuid);

    @POST("submitContractChange")
    Observable<BaseResponse> submitContractChange(@Query("contractChangeGuid") String contractChangeGuid,@Query("submitResult") String submitResult,@Query("submitDescription") String submitDescription,@Query("userNo") String userNo,@Query("userName") String userName,@Query("recoverTime") String recoverTime,@Query("changeReason") String changeReason);

    @POST("getProjectTrackList")
    Observable<BaseResponse<List<ProjectTrack>>> getProjectTracklList(@NonNull @Query("projectGuid") String projectGuid, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("getAllAccountListByPage")
    Observable<BaseResponse<List<AccountPayment>>> getAllAccountListByPage(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,@Query("accountGuid") String accountGuid);

    @POST("getProjectTrackDetail")
    Observable<BaseResponse<ProjectDetail>> getProjectTrackDetail(@NonNull @Query("projectGuid") String projectGuid);

    @POST("saveProjectDatailByProjectGuid")
    Observable<BaseResponse> saveProjectTrack(@NonNull @Query("params") String params);

    @POST("validateAllot")
    Observable<BaseResponse<String>> validateAllot(@Query("userId") String userId,@Query("userName") String userName,@Query("deptNo") String deptNo,@Query("accountGuid") String accountGuid,@Query("operateType") String operateType);

    @POST("saveSaleUser")
    Observable<BaseResponse<String>> saveSaleUser(@Query("userId") String userId,@Query("userName") String userName,@Query("deptNo") String deptNo,@Query("accountGuid") String accountGuid,@Query("allotType") String allotType);

    @POST("getProjectToSapListByPage")
    Observable<BaseResponse<List<AccountPaymentProject>>> getProjectToSapListByPage(@Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("getAccountDetail")
    Observable<BaseResponse<AccountPayment>> getAccountDetail(@NonNull @Query("accountGuid") String accountGuid);

    @POST("delSplitMoney")
    Observable<BaseResponse> delSplitMoney(@NonNull @Query("accountGuid") String accountGuid, @Query("userId") String userId);

    @POST("allotOrderToAccount")
    Observable<BaseResponse> allotOrderToAccount(@Query("accountGuid") String accountGuid, @Query("orderNumber") String orderNumber, @Query("userId") String userId);

    @POST("submitAllot")
    Observable<BaseResponse> submitAllot(@Query("accountGuid") String accountGuid, @Query("userId") String userId, @Query("userNo") String userNo, @Query("userName") String userName, @Query("deptId") String deptId);

    @POST("validateUpdateApply")
    Observable<BaseResponse> validateUpdateApply(@NonNull @Query("accountGuid") String accountGuid, @Query("userId") String userId);

    @POST("saveAccountInfo")
    Observable<BaseResponse> saveAccountInfo(@NonNull @Query("params") String params);

    @POST("getPayNodeList")
    Observable<BaseResponse<List<PayNode>>> getPayNodeList(@QueryMap Map<String, String> params);

    @POST("savePayOther")
    Observable<BaseResponse> savePayOther(@NonNull @Query("params") String params);

    @POST("getSelectList")
    Observable<BaseResponse<List<DropDown>>> getSelectList(@NonNull @Query("itemID") int itemID);

    @POST("saveAccountInfoForPayNode")
    Observable<BaseResponse> saveAccountInfoForPayNode(@NonNull @Query("params") String params);

    @POST("wechatLogin")
    Observable<BaseResponse<LoginVO>> wechatLogin(@NonNull @Query("code") String code, @Query("appid") String appid, @Query("secret") String secret);

    @POST("wechatAutoLogin")
    Call<BaseResponse<LoginVO>> wechatAutoLogin(@Query("appid") String appid, @Query("openid") String openid, @Query("access_token") String access_token,@Query("refresh_token") String refresh_token);

    @POST("updateLoginLog")
    Observable<BaseResponse> updateLoginLog(@Query("user_id") String user_id,@Query("latitude") String latitude,@Query("lontitude") String lontitude,@Query("address") String address);

    @POST("getBiddingByPage")
    Observable<BaseResponse<List<BidReview>>> getBiddingByPage(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("state") int state, @Query("userNo") String userNo);

    @POST("getProjectBidResultInfo")
    Observable<BaseResponse<BidResultInfo>> getProjectBidResultInfo(@NonNull @Query("projectGuid") String projectGuid);

    @POST("saveProjectBidInfo")
    Observable<BaseResponse> saveProjectBidInfo(@NonNull @Query("params") String params);

    @Multipart
    @POST("addBidFile")
    Observable<BaseResponse<List<BidAttachment>>> addBidFile(@PartMap Map<String, RequestBody> bodyMap);

    @POST("getBiddingReviewDetail")
    Observable<BaseResponse<BidDetail>> getBiddingReviewDetail(@NonNull @Query("projectGuid") String projectGuid);

    @POST("saveBiddingReviewInfo")
    Observable<BaseResponse<Map>> saveBiddingReviewInfo(@NonNull @Query("params") String params);

    @POST("getContractProdList")
    Observable<BaseResponse<List<ContractChange>>> getContractProdList(
            @Query("contractProdType") String contractProdType,
            @Query("transactUserNo") String transactUserNo);

    @POST("saveContractProd")
    Observable<BaseResponse> saveContractProd(@NonNull @Query("params") String params);

    @POST("getCancelContractInfoList")
    Observable<BaseResponse<List<ProductCancel>>> getCancelContractInfoList(@NonNull @Query("contractGuid") String contractGuid);

    @POST("deleteCancelContractInfo")
    Observable<BaseResponse> deleteCancelContractInfo(@NonNull @Query("cancelProductGuid") String cancelProductGuid);

    @POST("saveCanelContractInfo")
    Observable<BaseResponse> saveCanelContractInfo(@NonNull @Query("params") String params);

    @POST("submitCanelContractInfo")
    Observable<BaseResponse> submitCanelContractInfo(@NonNull @Query("params") String params,@Query("contractChangeGuid") String contractChangeGuid,@Query("userNo") String userNo,@Query("userName") String userName);
}