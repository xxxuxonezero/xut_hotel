package com.xut.dao;

import com.xut.bean.RoomType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface RoomTypeMapper {

    void create(RoomType roomType);

    RoomType getById(@Param("id") Integer id);

    List<RoomType> get(@Param("type") String type, @Param("offset") int offset, @Param("pageSize") int pageSize);

    void delete(List<Integer> ids);

    void update(RoomType roomType);
}
