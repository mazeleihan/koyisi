package com.example.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Post;
import com.example.mapper.PostMapper;
import com.example.service.PostService;
import com.example.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-07-06
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    PostMapper postMapper;
/*/*
 * 功能描述: <br>
 * 〈从数据库读出访问大于七天前的所有文章，存入〉缓存最近7天的文章评论数
 * @Param:
 * @Return: void
 * @Author: 007
 * @Date: 2020/7/7 10:57
 */
    @Override
    public void initWeekRank() {
        List<Post> posts=this.list(new QueryWrapper<Post>()
        .ge("create",DateUtil.offsetDay(new Date(),-7).toJdkDate())
        .select("id,title,user_id,comment_count,view_count,created"));
        for(Post post:posts){
            String key="day_rank:"+DateUtil.format(post.getCreated(),DatePattern.PURE_DATE_FORMAT);
            redisUtil.zSet(key,post.getId(),post.getCommentCount());
            long between=DateUtil.between(new Date(),post.getCreated(), DateUnit.DAY);
            long expireTime=(7-between)*24*60*60;

            redisUtil.expire(key,expireTime);
        }
    }

    /**
     * 缓存文章的基本信息
     * @param post
     * @param expireTime
     */
    private void hashCachePostIdAndTitle(Post post, long expireTime) {
        String key = "rank:post:" + post.getId();
        boolean hasKey = redisUtil.hasKey(key);
        if(!hasKey) {

            redisUtil.hset(key, "post:id", post.getId(), expireTime);
            redisUtil.hset(key, "post:title", post.getTitle(), expireTime);
            redisUtil.hset(key, "post:commentCount", post.getCommentCount(), expireTime);
            redisUtil.hset(key, "post:viewCount", post.getViewCount(), expireTime);
        }
    }

    /**
     * 本周合并每日评论数量操作
     */
    private void zunionAndStoreLast7DayForWeekRank() {
        String  currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);

        String destKey = "week:rank";
        List<String> otherKeys = new ArrayList<>();
        for(int i=-6; i < 0; i++) {
            String temp = "day:rank:" +
                    DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);

            otherKeys.add(temp);
        }

        redisUtil.zUnionAndStore(currentKey, otherKeys, destKey);
    }

}
