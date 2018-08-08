package com.zxtech.gks.model.vo;

import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.gks.model.bean.ProjectDetailBean;

import java.util.List;

/**
 * Created by syp523 on 2017/12/6.
 */

public class ProjectRecord  {

    private RecordApproval Project;
    protected ProjectDetailBean ProjectDetail;
    protected ProjectBid ProjectBid;
    protected List<BidAttachment> DrawingFileList;

    public RecordApproval getProject() {
        return Project;
    }

    public void setProject(RecordApproval project) {
        this.Project = Project;
    }

    public ProjectDetailBean getProjectDetail() {
        return ProjectDetail;
    }

    public void setProjectDetail(ProjectDetailBean projectDetail) {
        ProjectDetail = projectDetail;
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
