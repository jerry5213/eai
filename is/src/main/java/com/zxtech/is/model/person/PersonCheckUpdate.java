package com.zxtech.is.model.person;

import com.zxtech.is.model.common.IsMstOptions;

import java.util.List;

/**
 * Created by syp692 on 2018/4/21.
 */

public class PersonCheckUpdate {



    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getMemberGuid() {
        return memberGuid;
    }

    public void setMemberGuid(String memberGuid) {
        this.memberGuid = memberGuid;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<com.zxtech.is.model.common.IsMstOptions> getIsMstOptions() {
        return IsMstOptions;
    }

    public void setIsMstOptions(List<com.zxtech.is.model.common.IsMstOptions> isMstOptions) {
        IsMstOptions = isMstOptions;
    }
    private String projectGuid;
    private String memberGuid;
    private String questionText;
    private List<IsMstOptions> IsMstOptions;







}
