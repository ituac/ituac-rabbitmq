import org.itauc.rabbitmq.message.*;
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
    @Test
    public void reportUserCurrentTime() {
        // 对象传输，对象必须实现Serializable序列化
        UserMq user = new UserMq();
        user.setAge(30);
        user.setId(2);
        user.setMessage("I'm topic");
        user.setName("波罗星");
        user.setSex(2);
        user.setTime(new Date());

        sender.sendUser(user);
        System.out.println("now time：" + date.format(new Date()));
    }

    @Test
    public void reportCityCurrentTime() {
        // 对象传输，对象必须实现Serializable序列化
        CityMq city = new CityMq();
        city.setId(2);
        city.setCode("200");
        city.setMsg("城市重要通知");
        city.setTime(new Date());

        sender.sendCity(city);
        System.out.println("now time：" + date.format(new Date()));
    }



    @Test
    public void reportDeadMessageCurrentTime() {
        // 对象传输，对象必须实现Serializable序列化
        MailMq mail = new MailMq();
        mail.setId(1);
        mail.setTime(new Date());
        mail.setMail("907788997@qq.com");
        sender.sendDead(mail);
        System.out.println("now time：" + date.format(new Date()));
    }

    @Test
    public void reportHostCurrentTime() {
        // 对象传输，对象必须实现Serializable序列化
        HotMq hot = new HotMq();
        hot.setId(1);
        hot.setDescp("博客：blog.ituac.com");
        hot.setLike(100000);
        hot.setName("波罗星");
        sender.sendHotSource(hot);	//发送给正常的
        hot.setName("波罗星AE测试");
        sender.sendHotBack(hot);		//发送给坏的
        System.out.println("now time：" + date.format(new Date()));
    }

}