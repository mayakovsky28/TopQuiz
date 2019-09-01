package com.lewisgreaves.topquiz.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * Created by @Mayakovsky28 on 8/23/19.
 */public class QuestionBank {
     private List<Question> mQuestionList;
     private int mNextQuestionIndex;

     public QuestionBank(List<Question> questionList) {
         mQuestionList = questionList;
         Collections.shuffle(mQuestionList);
         mNextQuestionIndex = 0;
     }

     public Question getQuestion() {
         if (mNextQuestionIndex == mQuestionList.size()) {
             mNextQuestionIndex = 0;
         }
         return mQuestionList.get(mNextQuestionIndex++);
    }
}
