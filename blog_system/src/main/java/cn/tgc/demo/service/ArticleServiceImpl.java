package cn.tgc.demo.service;


import cn.tgc.demo.dao.ArticleMapper;
import cn.tgc.demo.dao.CommentMapper;
import cn.tgc.demo.dao.StatisticMapper;
import cn.tgc.demo.domain.Article;
import cn.tgc.demo.domain.Statistic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Article> articleList = articleMapper.selectArticleWithPage();
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            Statistic statistic =statisticMapper.selectStatisticWithArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> pageInfo=new PageInfo<>(articleList);
        return pageInfo;}
    //统计前十热度的文章
    @Override
    public List<Article> getHeatArticles( ) {
        List<Statistic> list = statisticMapper.getStatistic();
        List<Article> articlelist=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Article article =  articleMapper.selectArticleWithId(list.get(i).getArticleId());
            article.setHits(list.get(i).getHits());
            article.setCommentsNum(list.get(i).getCommentsNum());
            articlelist.add(article);
            if(i>=9){
                break;
            }}
        return articlelist;}

    @Override
    public Article selectArticleWithId(Integer id) {
        Article article = null;

        article = articleMapper.selectArticleWithId(id);


        return article;
    }

    @Override
    public void publish(Article article) {
        //去除表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);
        articleMapper.publishArticle(article);
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleWithId(Article article) {
        article.setModified(new Date());
        articleMapper.updateArticleWithId(article);

    }

    @Override
    public void deleteArticleWithId(int id) {
        articleMapper.deleteArticleWithId(id);
        statisticMapper.deleteStatisticWithId(id);
        commentMapper.deleteCommentWithId(id);
    }


}
