package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import sample.FileParser;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private final Queue<Problems> mAllQuestion = new LinkedList<>();
    private final FileParser fileParser = new FileParser();

    private final Queue<Problems> mQuestions = new LinkedList<>();
    private Problems mCurrentDisplayQuestion;
    private int mNumberOfCorrectAnswers = 0;
    private String mCorrectAnswer;
    private int mNumOfQuestions = 0;

    @FXML
    private Label question;

    @FXML
    private RadioButton answer1;

    @FXML
    private RadioButton answer2;

    @FXML
    private RadioButton answer3;

    @FXML
    private RadioButton answer4;

    @FXML
    private Button submitButton;

    @FXML
    private TextField userDialog;

    @FXML
    private Button continueButton;


    private static final String FILE_NAME = "triviaPlay.txt";

    //initialize the program
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mAllQuestion.addAll(fileParser.parseFileAngGetProblems(FILE_NAME));
        setNewQuestions();
        displayNextQuestion();
    }

    //sets a new question on the screen
    public void displayNextQuestion() {
        mCurrentDisplayQuestion = mQuestions.poll();
        question.setText(mCurrentDisplayQuestion.getQuestion());
        mCorrectAnswer = mCurrentDisplayQuestion.getCorrectAnswer();
        setRadioButtons(mCurrentDisplayQuestion);
        deselectRadioButtons(answer1, answer2, answer3, answer4);
        shouldDisable(true, continueButton);
        mNumOfQuestions++;


    }

    //set answers , and shuffle them
    public void setRadioButtons(Problems problems) {
        RadioButton[] radioButtons = {answer1, answer2, answer3, answer4};
        question.setText(problems.getQuestion());
        Collections.shuffle(problems.getAnswers());
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].setText(problems.getAnswers().get(i));
        }
    }
    //logic when submit button is pressed
    public void onSubmitButtonPressed(ActionEvent actionEvent) {
        RadioButton[] radioButtons = {answer1, answer2, answer3, answer4};
        String answer = checkForSelectedRadioButton(radioButtons);
        if (answer != null) {
            shouldDisable(false, continueButton);
            shouldDisable(true, submitButton);
            boolean isCorrectAnswer = checkForRightAnswer(mCorrectAnswer, answer);
            if (isCorrectAnswer) {
                mNumberOfCorrectAnswers++;
                userDialog.setText("correct");
            } else {
                userDialog.setText("incorrect");
            }
        }


    }
    //return the selected answer
    private String checkForSelectedRadioButton(RadioButton[] radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                return radioButton.getText();
            }
        }
        return null;
    }
    //checks for right answer
    private boolean checkForRightAnswer(String rightAnswer, String givenAnswer) {
        for (int i = 0; i < rightAnswer.length(); i++) {
            if (rightAnswer.charAt(i) != givenAnswer.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void setNewQuestions() {
        mQuestions.clear();
        mQuestions.addAll(mAllQuestion);
        Collections.shuffle((List<?>) mQuestions);
    }

    //logic after continue button is pressed , updates the fields that should be updated
    public void onContinueButtonPressed(ActionEvent actionEvent) {
        if (continueButton.getText().equals("new game")) {
            setNewQuestions();
            displayNextQuestion();
            userDialog.setText("");
            continueButton.setText("continue");
            mNumOfQuestions = 0;
            mNumberOfCorrectAnswers = 0;
            shouldDisable(false, submitButton);
        } else if (mQuestions.size() == 0) {
            int finalScore = (int) ((double) mNumberOfCorrectAnswers / (double) mNumOfQuestions * 100);
            userDialog.setText(String.valueOf("your final score is : " + finalScore));
            continueButton.setText("new game");
        } else {
            shouldDisable(false, submitButton);
            userDialog.setText("");
            displayNextQuestion();
        }


    }

    private void shouldDisable(boolean bol, Button button) {
        button.setDisable(bol);
    }


    private void deselectRadioButtons(RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(false);
        }
    }

}




