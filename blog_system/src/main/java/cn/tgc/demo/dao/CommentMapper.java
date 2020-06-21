package cn.tgc.demo.dao;

import cn.tgc.demo.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 分页展示某个文章的评论
    @Select("SELECT * FROM t_comment WHERE article_id=#{aid} ORDER BY id DESC")
    public List<Comment> selectCommentWithPage(Integer aid);
    // 后台查询最新几条评论
    @Select("SELECT * FROM t_comment ORDER BY id DESC")
    public List<Comment> selectNewComment();

    // 站点服务统计，统计评论数量
    @Select("SELECT COUNT(1) FROM t_comment")
    public Integer countComment();
    // 通过文章id删除评论信息
    @Delete("DELETE FROM t_comment WHERE article_id=#{aid}")
    public void deleteCommentWithId(Integer aid);
    //发表评论
    @Insert("insert into t_comment (article_id,created,author,ip,content)" +
            "values (#{articleId},#{created},#{author},#{ip},#{content})")
    public void pushComment(Comment comment);
    @Select("SELECT * FROM t_comment ORDER BY id DESC")
    public  List<Comment> selectArticleWithPage();

    @Select("SELECT * FROM t_comment where id=#{id}")
    public Comment selectCommentWithId(int parseInt);
    @Delete("DELETE FROM t_comment WHERE id=#{id}")
    public void deleteCommentWithIda(int id);
}
