import com.xut.bean.Client;
import com.xut.dao.ClientMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ClientTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
    ClientMapper mapper;

    @Before
    public void before() {
        mapper = ac.getBean(ClientMapper.class);
    }

    @Test
    public void create() {
        Client client = new Client();
        client.setOrderId(1);
        client.setPhone("15845637852");
        client.setIdentificationId("111111111111111");
        client.setRealName("jjjj");
        Client client1 = new Client();
        client1.setOrderId(1);
        client1.setPhone("15845637853");
        client1.setIdentificationId("12222222222222");
        client1.setRealName("gw");
        List<Client> list = new ArrayList<>();
        list.add(client);
        list.add(client1);
        mapper.create(list);
    }
}
