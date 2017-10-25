package com.lxinet.jeesns.service.group.impl;

import com.lxinet.jeesns.core.dto.ResponseModel;
import com.lxinet.jeesns.core.model.Page;
import com.lxinet.jeesns.core.utils.StringUtils;
import com.lxinet.jeesns.model.group.GroupTopic;
import com.lxinet.jeesns.model.group.GroupTopicComment;
import com.lxinet.jeesns.model.member.Member;
import com.lxinet.jeesns.service.group.IGroupTopicCommentService;
import com.lxinet.jeesns.dao.group.IGroupTopicCommentDao;
import com.lxinet.jeesns.service.group.IGroupTopicService;
import com.lxinet.jeesns.common.utils.ScoreRuleConsts;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("groupTopicCommentService")
public class GroupTopicCommentServiceImpl implements IGroupTopicCommentService {
    @Resource
    private IGroupTopicCommentDao groupTopicCommentDao;
    @Resource
    private IGroupTopicService groupTopicService;

    @Override
    public GroupTopicComment findById(int id) {
        return groupTopicCommentDao.findById(id);
    }

    @Override
    public ResponseModel save(Member loginMember, String content, Integer groupTopicId,Integer commentId) {
        GroupTopic groupTopic = groupTopicService.findById(groupTopicId,loginMember);
        if(groupTopic == null){
            return new ResponseModel(-1,"帖子不存在");
        }
        if(StringUtils.isEmpty(content)){
            return new ResponseModel(-1,"内容不能为空");
        }
        GroupTopicComment groupTopicComment = new GroupTopicComment();
        groupTopicComment.setMemberId(loginMember.getId());
        groupTopicComment.setGroupTopicId(groupTopicId);
        groupTopicComment.setContent(content);
        groupTopicComment.setCommentId(commentId);
        int result = groupTopicCommentDao.save(groupTopicComment);
        if(result == 1){
            return new ResponseModel(1,"评论成功");
        }else {
            return new ResponseModel(-1,"评论失败");
        }
    }

    @Override
    public ResponseModel listByGroupTopic(Page page, int groupTopicId) {
        List<GroupTopicComment> list = groupTopicCommentDao.listByGroupTopic(page, groupTopicId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public void deleteByTopic(int groupTopicId) {
        groupTopicCommentDao.deleteByTopic(groupTopicId);
    }

    @Override
    public ResponseModel delete(Member loginMember,int id){
        GroupTopicComment groupTopicComment = this.findById(id);
        if(groupTopicComment == null){
            return new ResponseModel(-1,"评论不存在");
        }
        int result = groupTopicCommentDao.delete(id);
        if(result == 1){
            return new ResponseModel(1,"删除成功");
        }
        return new ResponseModel(-1,"删除失败");
    }

}
