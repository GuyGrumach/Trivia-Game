package sample;

import java.util.List;

public class Problems {

    String question;
    List<String> answer;

    public Problems(String question, List<String> answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answer;
    }

    public String getCorrectAnswer(){
        return getAnswers().get(0);
    }
}
