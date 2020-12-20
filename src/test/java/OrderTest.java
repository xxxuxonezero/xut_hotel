import com.xut.bean.Order;
import com.xut.dao.CommentMapper;
import com.xut.dao.OrderMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.MAX_VALUE;

public class OrderTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
    OrderMapper bean = ac.getBean(OrderMapper.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void create() throws ParseException {
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setRoomTypeId(1);
        order.setUserId(1);
        order.setPrice(299.99);
        order.setNote("jjjj");
        String begin = "2020-12-15";
        String end = "2020-12-20";
        order.setCheckInTime(sdf.parse(begin));
        order.setCheckOutTime(sdf.parse(end));
        bean.create(order);
    }

    @Test
    public void search() {
        List<Order> search =
                bean.search(null, 1, null, 1,2, MAX_VALUE);
        System.out.println(search.size());
    }
}
