package cn.tgc.demo.service;

import cn.tgc.demo.ResponseData.StatisticsBo;
import cn.tgc.demo.dao.ArticleMapper;
import cn.tgc.demo.dao.CommentMapper;
import cn.tgc.demo.dao.StatisticMapper;
import cn.tgc.demo.domain.Article;
import cn.tgc.demo.domain.Comment;
import cn.tgc.demo.domain.Statistic;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional

public class SiteServiceImpl implements ISiteService {

        @Autowired
        private CommentMapper commentMapper;
        @Autowired
        private ArticleMapper articleMapper;
        @Autowired
        private StatisticMapper statisticMapper;
        @Override
        public void updateStatistics(Article article) {
            Statistic statistic =
                    statisticMapper.selectStatisticWithArticleId(article.getId());
            statistic.setHits(statistic.getHits()+1);
            statisticMapper.updateArticleHitsWithId(statistic);
        }
        @Override
        public List<Comment> recentComments(int limit) {
            PageHelper.startPage(1, limit>10 || limit<1 ? 10:limit);
            List<Comment> byPage = commentMapper.selectNewComment();
            return byPage;
        }
        @Override
        public List<Article> recentArticles(int limit) {
            PageHelper.startPage(1, limit>10 || limit<1 ? 10:limit);
            List<Article> list = articleMapper.selectArticleWithPage();
            // 封装文章统计数据
            for (int i = 0; i < list.size(); i++) {
                Article article = list.get(i);
                Statistic statistic =
                        statisticMapper.selectStatisticWithArticleId(article.getId());
                article.setHits(statistic.getHits());
                article.setCommentsNum(statistic.getCommentsNum());
            }
            return list;
        }
        @Override
        public StatisticsBo getStatistics() {
            StatisticsBo staticticsBo = new StatisticsBo();
            Integer articles = articleMapper.countArticle();
            Integer comments = commentMapper.countComment();
            staticticsBo.setArticles(articles);
            staticticsBo.setComments(comments);
            return staticticsBo;
        }
}
