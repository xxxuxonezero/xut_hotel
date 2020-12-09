import com.xut.bean.Comment;
import com.xut.dao.CommentMapper;
import com.xut.model.Page;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class CommentTest {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
    CommentMapper bean = ac.getBean(CommentMapper.class);
    @Test
    public void create() {
        Comment comment = new Comment();
        comment.setTypeId(1);
        comment.setDescription("test");
        comment.setUserId(1);
        bean.create(comment);
    }

    @Test
    public void getByTypeId() {
        List<Comment> page = bean.getByTypeId(1, 1, 10);
        System.out.println(page.size());
    }

    @Test
    public void getCount() {
        System.out.println(bean.getByTypeIdCount(1));
    }
}
