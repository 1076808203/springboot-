package cn.tgc.demo.web.scheduletask;

import cn.tgc.demo.dao.StatisticMapper;
import cn.tgc.demo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private MailUtils mailUtils;
    @Value("${spring.mail.username}")
    private String mailto;
    @Scheduled(cron = "0 */3 * * * ?")
    public void sendEmail(){
        //  定制邮件内容
        long totalvisit = statisticMapper.getTotalVisit();
        long totalComment = statisticMapper.getTotalComment();
        StringBuffer content = new StringBuffer();
        content.append("博客系统总访问量为："+totalvisit+"人次").append("\n");
        content.append("博客系统总评论量为："+totalComment+"人次").append("\n");
        mailUtils.sendSimpleEmail(mailto,"个人博客系统流量统计情况",content.toString());
    }

}
