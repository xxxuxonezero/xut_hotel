import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xut.bean.RoomType;
import com.xut.dao.RoomTypeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomTypeTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
    @Test
    public void create() throws JsonProcessingException {
        RoomTypeMapper roomTypeMapper = ac.getBean(RoomTypeMapper.class);
        RoomType roomType = new RoomType();
        roomType.setType("大床房1");
        roomType.setPrice(199.99);
        roomType.setMaxPeople(2);
        List<String> list = new ArrayList<>();
        list.add("http://qkjs7zxyw.hn-bkt.clouddn.com/xut_160731641800063_1571399780081_079F4FB55B755F6F198BEE97D7C95390.png");
        roomType.setImgs(FormatList(list));
        roomTypeMapper.create(roomType);
        System.out.println(roomType.getId());
    }

    @Test
    public void getByid() {
        RoomTypeMapper roomTypeMapper = ac.getBean(RoomTypeMapper.class);
        RoomType roomType = roomTypeMapper.getById(6);
        System.out.println(roomType.getImgList().size());
    }

    @Test
    public void get() {
        RoomTypeMapper roomTypeMapper = ac.getBean(RoomTypeMapper.class);
        List<RoomType> roomTypes =
                roomTypeMapper.get("情趣房",1, 10);
        System.out.println(roomTypes.size());
    }

    @Test
    //对象和字符串互转
    public void parseString() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        RoomType type = new RoomType();
        type.setBed("nngjgow");
        String s = om.writeValueAsString(type);
        System.out.println(s);
        RoomType roomType = om.readValue(s, RoomType.class);
        System.out.println(roomType.getBed());
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        s = om.writeValueAsString(list);
        List<Integer> list1 = om.readValue(s, List.class);
        System.out.println(list1.size());
        System.out.println(s);
    }

    @Test
    public void delete () {
        RoomTypeMapper bean = ac.getBean(RoomTypeMapper.class);
        bean.delete(Collections.singletonList(2));
        RoomType byId = bean.getById(2);
        System.out.println(byId);
    }

    @Test
    public void update() {
        RoomTypeMapper bean = ac.getBean(RoomTypeMapper.class);
        RoomType type = bean.getById(1);
        type.setBed("一张大床");
        type.setType("情趣房");
        type.setPrice(299.99);
        bean.update(type);
    }

    public String FormatList(List<String> list) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(list);
        return s;

    }

}
