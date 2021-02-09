package com.xut.service;

import com.xut.bean.Comment;
import com.xut.dao.CommentMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.CertSelector;
import java.util.List;

@Service
public class CommentService {
    Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    CommentMapper commentMapper;

    public Result<List<Comment>> getComments(int typeId, int offset, int pageSize) {
        Result<List<Comment>> result = new Result<>();
        try {
            List<Comment> comments = commentMapper.getByTypeId(typeId, offset, pageSize);
            if (CollectionUtils.isEmpty(comments)) {
                return result;
            }
//            int count = commentMapper.getByTypeIdCount(typeId);
//            Page<Comment> commentPage = new Page<>();
//            commentPage.setList(comments);
//            commentPage.setTotalCount(count);
            result.setData(comments);
        } catch (Exception e) {
            logger.error("CommentService getComments error ", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public NoneDataResult create(Comment comment) {
        NoneDataResult result = new NoneDataResult();
        try {
            commentMapper.create(comment);
        } catch (Exception e) {
            logger.error("CommentService create error", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(int id) {
        NoneDataResult result = new NoneDataResult();
        try {
            commentMapper.delete(id);
        } catch (Exception e) {
            logger.error("CommentService delete error", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }

    public int geTotalCount(int typeId) {
        return commentMapper.getByTypeIdCount(typeId);
    }

    public Result<Comment> getById(Integer id) {
        Result<Comment> result = new Result<>();
        if (id == null) {
            return result;
        }
        try {
            Comment comment = commentMapper.getById(id);
            result.setData(comment);
        } catch (Exception e) {
            logger.error("CommentService getById error", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<List<Comment>> getByUserId(Integer userId, Integer pageSize, Integer offset) {
        Result<List<Comment>> result = new Result<>();
        if (userId == null || userId <= 0) {
            return result;
        }
        try {
            List<Comment> comments = commentMapper.getByUserId(userId, pageSize, offset);
            result.setData(comments);
        } catch (Exception e) {
            logger.error("CommentService getByUserId error", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }
}
