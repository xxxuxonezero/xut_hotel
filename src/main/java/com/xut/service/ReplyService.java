package com.xut.service;

import com.xut.bean.Reply;
import com.xut.dao.ReplyMapper;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReplyService {
    Logger logger = LoggerFactory.getLogger(ReplyService.class);
    @Autowired
    ReplyMapper replyMapper;

    public NoneDataResult create(Reply reply) {
        NoneDataResult result = new NoneDataResult();
        try {
            replyMapper.create(reply);
        } catch (Exception e) {
            logger.error("ReplyService create error", e);
            result.setCode(Code.DATABASE_INSERT_ERROR);
        }
        return result;
    }

    public NoneDataResult delete(Integer id, Integer commentId) {
        NoneDataResult result = new NoneDataResult();
        try {
            replyMapper.delete(id, commentId);
        } catch (Exception e) {
            logger.error("ReplyService delete error", e);
            result.setCode(Code.DATABASE_DELETE_ERROR);
        }
        return result;
    }

    public Result<Reply> getById(Integer id) {
        Result<Reply> result = new Result<>();
        if (id == null) {
            return result;
        }
        try {
            Reply reply = replyMapper.getById(id);
            result.setData(reply);
        } catch (Exception e) {
            logger.error("ReplyService getById error", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }

    public Result<Map<Integer, List<Reply>>> getReplysMap(List<Integer> commentIds) {
        Result<Map<Integer, List<Reply>>> result = new Result<>();
        Result<List<Reply>> replysResult = getReplys(commentIds);
        if (replysResult.isNotValid()) {
            result.setCode(replysResult.getCode());
            return result;
        }
        Map<Integer, List<Reply>> map = replysResult.getData().stream().collect(Collectors.groupingBy(Reply::getCommentId));
        result.setData(map);
        return result;
    }

    public Result<List<Reply>> getReplys(List<Integer> commentIds) {
        Result<List<Reply>> result = new Result<>();
        try {
            List<Reply> replys = replyMapper.getByCommentIds(commentIds);
            if (CollectionUtils.isEmpty(replys)) {
                return result;
            }
            result.setData(replys);
        } catch (Exception e) {
            logger.error("ReplyService getReplys error", e);
            result.setCode(Code.DATABASE_SELECT_ERROR);
        }
        return result;
    }


}
