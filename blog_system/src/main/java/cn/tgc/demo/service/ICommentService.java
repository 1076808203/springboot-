package cn.tgc.demo.service;

import cn.tgc.demo.domain.Article;
import cn.tgc.demo.domain.Comment;
import com.github.pagehelper.PageInfo;

public interface ICommentService {
    // 获取文章下的评论
    public PageInfo<Comment> getComments(Integer aid, int page, int count);
    //发布文章评论
    public void pushComment(Comment comment);

    //获取凭论列表
    public PageInfo<Comment> selectArticleWithPage(Integer page, Integer count);
    //根据id查凭论
    public Comment selectCommentWithId(int parseInt);

    public  void deleteArticleWithIda(int id);
}
