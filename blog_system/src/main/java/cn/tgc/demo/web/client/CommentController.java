package cn.tgc.demo.web.client;

import cn.tgc.demo.ResponseData.ArticleResponseData;
import cn.tgc.demo.domain.Comment;
import cn.tgc.demo.service.ICommentService;
import cn.tgc.demo.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ICommentService commentServcieImpl;


    @PostMapping(value = "/publish")
    @ResponseBody
    public ArticleResponseData publishComment(HttpServletRequest request,
                                              @RequestParam Integer aid, @RequestParam String text) {
        text = MyUtils.cleanXSS(text);text = EmojiParser.parseToAliases(text);
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comments = new Comment();
        comments.setArticleId(aid);
        comments.setIp(request.getRemoteAddr());
        comments.setCreated(new Date());
        comments.setAuthor(user.getUsername());
        comments.setContent(text);
        System.out.println(comments);
        try {
            commentServcieImpl.pushComment(comments);
            logger.info("发布评论成功，对应文章id: "+aid);
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("发布评论失败，对应文章id: "+aid +";错误描述: "+e.getMessage());
            return ArticleResponseData.fail();}}

}
