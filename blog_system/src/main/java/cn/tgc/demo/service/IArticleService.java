package cn.tgc.demo.service;

import cn.tgc.demo.domain.Article;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IArticleService {
    // 分页查询文章列表
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count);
    // 统计前10的热度文章信息
    public List<Article> getHeatArticles();
    //根据id查文章
    public Article selectArticleWithId(Integer id);
    //发布文章
    public void publish(Article article);
    //修改文章
    public void updateArticleWithId(Article article);
    //删除文章
    public void deleteArticleWithId(int id);

}
