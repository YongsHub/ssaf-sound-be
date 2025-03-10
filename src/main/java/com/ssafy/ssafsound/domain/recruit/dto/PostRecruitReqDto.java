package com.ssafy.ssafsound.domain.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ssafsound.domain.recruit.validator.CheckRecruitLimitElement;
import com.ssafy.ssafsound.domain.recruit.validator.CheckRecruitType;
import com.ssafy.ssafsound.domain.meta.validator.CheckSkills;
import com.ssafy.ssafsound.domain.recruit.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRecruitReqDto {

    @NotEmpty
    private String category;

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate recruitEnd;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String contactURI;

    @CheckRecruitType
    private String registerRecruitType;

    @CheckSkills
    private List<String> skills;

    private List<String> questions;

    @CheckRecruitLimitElement
    private List<RecruitLimitElement> limitations = new ArrayList<>();

    public Recruit to() {
        Recruit recruit = Recruit.builder()
                .view(0L)
                .category(Category.valueOf(category))
                .title(title)
                .content(content)
                .startDateTime(LocalDateTime.now())
                .endDateTime(recruitEnd.atTime(LocalTime.MAX))
                .contactURI(contactURI)
                .deletedRecruit(false)
                .build();

        setRecruitQuestions(recruit);
        return recruit;
    }

    private void setRecruitQuestions (Recruit recruit) {
        if(questions == null) return;

        List<RecruitQuestion> recruitQuestions = questions.stream().map((question)-> RecruitQuestion.builder()
                .recruit(recruit)
                .content(question)
                .build()
        ).collect(Collectors.toList());
        recruit.setRecruitQuestions(recruitQuestions);
    }
}
