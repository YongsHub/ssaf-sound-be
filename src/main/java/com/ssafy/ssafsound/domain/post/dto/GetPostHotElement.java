package com.ssafy.ssafsound.domain.post.dto;

import com.ssafy.ssafsound.domain.post.domain.HotPost;
import com.ssafy.ssafsound.domain.post.domain.Post;
import com.ssafy.ssafsound.domain.post.domain.PostImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetPostHotElement {
    private String boardTitle;
    private String title;
    private String content;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createAt;
    private String nickname;
    private Boolean anonymous;
    private String thumbnail;

    public static GetPostHotElement from(HotPost hotPost) {
        Post post = hotPost.getPost();
        String thumbnail = findThumbnailUrl(post);
        Boolean anonymous = post.getAnonymous();

        return GetPostHotElement.builder()
                .boardTitle(post.getBoard().getTitle())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikes().size())
                .commentCount(post.getComments().size())
                .createAt(post.getCreatedAt())
                .nickname(anonymous ? "익명" : post.getMember().getNickname())
                .anonymous(anonymous)
                .thumbnail(thumbnail)
                .build();
    }

    private static String findThumbnailUrl(Post post) {
        List<PostImage> images = post.getImages();
        if (images.size() >= 1)
            return images.get(0).getImageUrl();
        return null;
    }
}
