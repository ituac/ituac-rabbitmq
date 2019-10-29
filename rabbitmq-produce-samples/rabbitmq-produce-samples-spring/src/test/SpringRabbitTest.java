import org.itauc.rabbitmq.message.MessageMq;
import org.ituac.rabbitmq.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SpringRabbitTest extends AbstractJUnit4SpringContextTests {
    private static final SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private Sender sender;
    @Test
    public void sendMessage(){
        // 对象传输，对象必须实现Serializable序列化
        MessageMq message = new MessageMq();
        message.setId(1);
        message.setContent("这是消息内容");
        message.setLocked(true);
        message.setType(2);
        message.setCreate_time(new Date());
        message.setUpdate_time(new Date());
        for (int i=0;i<10;i++){
            sender.sendMessage(message);
        }
        System.out.println("发送完毕");
    }
}