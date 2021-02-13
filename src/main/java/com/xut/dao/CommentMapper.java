package com.xut.dao;

import com.xut.bean.Comment;
import com.xut.model.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CommentMapper {
    void create(Comment comment);

    List<Comment> getByTypeId(@Param("typeId") Integer typeId,
                              @Param("offset") int offset,
                              @Param("pageSize") int pageSize);

    int getByTypeIdCount(int typeId);

    void delete(Integer id);

    Comment getById(Integer id);

    List<Comment> getByUserId(@Param("userId") int userId,
                              @Param("offset") int offset,
                              @Param("pageSize") int pageSize);

    void update(Comment comment);

}
