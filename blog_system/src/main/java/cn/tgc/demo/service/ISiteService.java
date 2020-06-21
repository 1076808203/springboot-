package cn.tgc.demo.service;

import cn.tgc.demo.ResponseData.StatisticsBo;
import cn.tgc.demo.domain.Article;
import cn.tgc.demo.domain.Comment;

import java.util.List;

public interface ISiteService {
    // 最新收到的评论
    public List<Comment> recentComments(int count);
    // 最新发表的文章
    public List<Article> recentArticles(int count);
    // 获取后台统计数据
    public StatisticsBo getStatistics();
    // 更新某个文章的统计数据
    public void updateStatistics(Article article);

}
