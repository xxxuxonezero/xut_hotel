package com.xut.dao;

import com.xut.bean.RoomSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoomSettingMapper {
    RoomSetting getByTypeId(int typeId);

    void delete(List<Integer> typeIds);
}
