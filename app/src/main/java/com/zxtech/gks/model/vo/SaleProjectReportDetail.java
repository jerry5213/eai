package com.zxtech.gks.model.vo;

import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.gks.model.bean.ProjectDetailBean;

import java.util.List;

/**
 * Created by SYP521 on 2018/1/1.
 */

public class SaleProjectReportDetail {

    private RecordApproval Project;
    protected ProjectDetailBean ProjectDetail;
    protected ProjectBid ProjectBid;
    protected List<BidAttachment> DrawingFileList;
    private String Tip;

    public RecordApproval getProject() {
        return Project;
    }

    public String getTip() {
        return Tip;
    }

    public ProjectDetailBean getProjectDetail() {
        return ProjectDetail;
    }

    public com.zxtech.ecs.model.ProjectBid getProjectBid() {
        return ProjectBid;
    }

    public void setProjectBid(com.zxtech.ecs.model.ProjectBid projectBid) {
        ProjectBid = projectBid;
    }

    public List<BidAttachment> getDrawingFileList() {
        return DrawingFileList;
    }

    public void setDrawingFileList(List<BidAttachment> drawingFileList) {
        DrawingFileList = drawingFileList;
    }
}
