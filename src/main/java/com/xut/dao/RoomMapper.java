package com.xut.dao;

import com.xut.bean.Room;
import com.xut.bean.RoomData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper {
    void create(Room room);

    void delete(List<Integer> ids);

    void update(Room room);

    List<List<?>> search(@Param("typeIds") List<Integer> typeIds,
                          @Param("id") Integer id,
                          @Param("offset") int offset, @Param("pageSize")int pageSize);

}
