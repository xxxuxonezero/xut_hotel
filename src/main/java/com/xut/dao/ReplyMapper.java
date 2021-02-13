package com.xut.dao;

import com.xut.bean.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {

    List<Reply> getByCommentIds(List<Integer> commentIds);

    void create(Reply reply);

    void delete(@Param("id") Integer id, @Param("commentId") Integer commentId);

    Reply getById(Integer id);
}
