package cn.tgc.demo.web.client;

import cn.tgc.demo.domain.Article;
import cn.tgc.demo.domain.Comment;
import cn.tgc.demo.service.CommentServiceImpl;
import cn.tgc.demo.service.IArticleService;
import cn.tgc.demo.service.SiteServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    private SiteServiceImpl siteServiceImpl;
    @GetMapping(value = "/")
    private String index(HttpServletRequest request) {
        return this.index(request, 1, 5);
    }
    @GetMapping(value = "/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page,
                        @RequestParam(value = "count", defaultValue = "5") int count) {
        PageInfo<Article> articles =
                articleServiceImpl.selectArticleWithPage(page, count);
        List<Article> articleList = articleServiceImpl.getHeatArticles();
        request.setAttribute("articles", articles);
        request.setAttribute("articleList", articleList);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/index";
    }
    @GetMapping(value = "/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id,
                                 HttpServletRequest request){
        Article article = articleServiceImpl.selectArticleWithId(id);
        if(article!=null){
            getArticleComments(request, article);
            siteServiceImpl.updateStatistics(article);
            request.setAttribute("article",article);
            return "client/articleDetails";
        }else {logger.warn("查询文章详情结果为空，查询文章id: "+id);
            return "comm/error_404";}}

    private void getArticleComments(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments =
                    commentServiceImpl.getComments(article.getId(),Integer.parseInt(cp),3);
            request.setAttribute("cp", cp);
            request.setAttribute("comments", comments);
        }
    }

}
