import com.xut.bean.Room;
import com.xut.bean.RoomData;
import com.xut.dao.RoomMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
    RoomMapper bean = ac.getBean(RoomMapper.class);
    @Test
    public void create() {
        Room room = new Room();
        room.setFloorNum(1);
        room.setRoomNumber("101");
        room.setTypeId(1);
        bean.create(room);
    }

    @Test
    public void delete() {
        bean.delete(Collections.singletonList(1));
    }

    @Test
    public void update() {
        List<RoomData> list = bean.search(null, 1, Integer.MAX_VALUE);
        RoomData roomData = list.get(0);
        System.out.println(roomData.getTypeId());
        Room room = new Room(roomData);
        room.setTypeId(3);
        bean.update(room);
    }

    @Test
    public void search() {
        List<Integer> ids = new ArrayList<>();
        ids.add(2);
        List<RoomData> list = bean.search( ids, 1, Integer.MAX_VALUE);
        System.out.println(list.size());

    }

}
