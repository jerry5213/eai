package com.zxtech.esp.data;

import android.text.TextUtils;

import com.zxtech.esp.AppConfig;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class URL {
    private static URL instance;

    private URL() {
    }

    public static URL getInstance() {
        if (instance == null)
            instance = new URL();
        return instance;
    }

    /**
     * login
     *
     * @param userName
     * @param pwd
     * @return
     */
    public String getLoginUrl(String userName, String pwd) {

        return AppConfig.getHOST() + "login_m?login_id=" + userName + "&login_pwd=" + pwd +"&platform=android";
    }

    public String setUserLearnCourseInfoUrl() {

        return AppConfig.getHOST() + "ui/mobile/setUserLearnCourseInfo.do";
    }

    public String getReplyCommentUrl() {

        return AppConfig.getHOST() + "ui/mobile/replyComment.do";
    }

    public String replyBbsUrl() {

        return AppConfig.getHOST() + "ui/mobile/replyBbs.do";
    }

    public String getCourseCommentUrl(String course_id) {

        return AppConfig.getHOST() + "ui/mobile/getCourseComment.do?course_id="+course_id;
    }

    public String getCourseTypeUrl() {

        return AppConfig.getHOST() + "ui/mobile/findCourseCate.do";
    }

    public String getLearningCourseTypeUrl() {

        return AppConfig.getHOST() + "ui/mobile/findLearningCourseType.do";
    }

    public String getUserInfoUrl(String userId) {

        return AppConfig.getHOST() + "ui/mobile/getUserInfo.do?userId="+userId;
    }

    public String getRankingAllUrl() {

        return AppConfig.getHOST() + "ui/mobile/findRankingAll.do";
    }

    public String getBbsPostUrl() {

        return AppConfig.getHOST() + "ui/mobile/getBbsPost.do";
    }

    public String getPostCommentUrl(String post_id) {

        return AppConfig.getHOST() + "ui/mobile/getPostComment.do?post_id="+post_id;
    }

    public String lookPostUrl(String post_id) {

        return AppConfig.getHOST() + "ui/mobile/lookPost.do?post_id="+post_id;
    }

    public String releasePostUrl() {

        return AppConfig.getHOST() + "ui/mobile/releasePost.do";
    }

    public String registerUser() {

        return AppConfig.getHOST() + "ui/mobile/registerUser.do";
    }

    public String getBbsTypeUrl() {

        return AppConfig.getHOST() + "ui/mobile/getBbsType.do";
    }

    public String getExamInfoUrl() {

        return AppConfig.getHOST() + "ui/mobile/selectExamInfo.do";
    }

    public String getPaperOptions() {

        return AppConfig.getHOST() + "ui/mobile/selectPaperOptions.do";
    }

    public String saveExamDetails() {

        return AppConfig.getHOST() + "ui/mobile/saveExamDetails.do";
    }

    public String saveExamProblemState() {

        return AppConfig.getHOST() + "ui/mobile/saveExamProblemState.do";
    }

    public String handExamPaper() {

        return AppConfig.getHOST() + "ui/mobile/handExamPaper.do";
    }

    public String uploadHeaderPhotoUrl() {

        return AppConfig.getHOST() + "ui/mobile/uploadHeaderPhoto.do";
    }

    public String updateUserInfoUrl() {

        return AppConfig.getHOST() + "ui/mobile/updateUserInfo.do";
    }

    public String praiseCourseUrl() {

        return AppConfig.getHOST() + "ui/mobile/praiseCourse.do";
    }

    public String getCourseUrl(String typeId){
        return AppConfig.getHOST() + "ui/mobile/findCourse.do?typeId="+typeId;
    }

    public String getCourseUrl(String typeId,String userId){
        return AppConfig.getHOST() + "ui/mobile/findCourse.do?typeId="+typeId+"&userId="+userId;
    }

    public String getFullUrl(String subUrl) {
        if (!TextUtils.isEmpty(subUrl) && !subUrl.startsWith("http")) {
            return AppConfig.getFileHost() + subUrl;
        }
        return AppConfig.getFileHost();
    }
}
