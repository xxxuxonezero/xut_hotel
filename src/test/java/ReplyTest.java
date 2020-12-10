import com.xut.bean.Reply;
import com.xut.dao.ReplyMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReplyTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
    ReplyMapper bean = ac.getBean(ReplyMapper.class);

    @Test
    public void create() {
        Reply reply = new Reply();
        reply.setCommentId(1);
        reply.setDescription("这是一条回复");
        reply.setReplyUserId(1);
        reply.setReplyUserName("xut");
        reply.setUserId(1);
        bean.create(reply);
    }
}
