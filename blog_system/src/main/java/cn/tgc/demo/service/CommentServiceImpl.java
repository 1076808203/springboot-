package cn.tgc.demo.service;

import cn.tgc.demo.dao.ArticleMapper;
import cn.tgc.demo.dao.CommentMapper;
import cn.tgc.demo.dao.StatisticMapper;
import cn.tgc.demo.domain.Article;
import cn.tgc.demo.domain.Comment;
import cn.tgc.demo.domain.Queryvo;
import cn.tgc.demo.domain.Statistic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class CommentServiceImpl implements ICommentService {
    // 获取文章下的评论
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    // 根据文章id分页查询评论
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> commentList = commentMapper.selectCommentWithPage(aid);
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    @Override
    public void pushComment(Comment comment) {
        commentMapper.pushComment(comment);
        Statistic statistic =statisticMapper.selectStatisticWithArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }
    //查询所有凭论
    @Override
    public PageInfo<Comment> selectArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Comment> commentList= commentMapper.selectArticleWithPage();
        /*for (int i = 0; i < commentList.size(); i++) {
            Comment comment = commentList.get(i);
            int artid=comment.getArticleId();

        }*/
        PageInfo<Comment> pageInfo=new PageInfo<Comment>(commentList);
        return pageInfo;
    }

    @Override
    public Comment selectCommentWithId(int parseInt) {
        Comment comment = commentMapper.selectCommentWithId(parseInt);
        return comment;
    }

    @Override
    public void deleteArticleWithIda(int id) {
        statisticMapper.deleteStatisticWithId(id);
        commentMapper.deleteCommentWithIda(id);
    }
}



