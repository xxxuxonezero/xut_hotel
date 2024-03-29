import com.xut.bean.User;
import com.xut.dao.UserMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
    UserMapper bean = ac.getBean(UserMapper.class);

    @Test
    public void getUserByIdAndPwd() {
        User user = bean.getUserByIdAndPwd("320211199908263256", "123456");
        System.out.println(user.getUserName());
    }

    @Test
    public void create() {
        User user = new User();
        user.setIdentificationId("32021119990826325");
        user.setPassword("Aa123456");
        user.setUserName("xutian");
        user.setRealName("xutian");
        user.setPhone("158123569852");
        bean.create(user);
    }

    @Test
    public void update() {
        User user = bean.getUserByIdAndPwd("320211199908263256", "123456");
        user.setUserName("admin1");
        bean.update(user);
    }

    @Test
    public void search() {
        /**
         * old: 0--新用户， 1--老用户
         */
        List<List<?>> search =
                bean.search(null, 0, 1, Integer.MAX_VALUE);
        System.out.println(search);
    }
}
