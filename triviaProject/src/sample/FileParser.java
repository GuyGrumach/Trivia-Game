package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileParser {

    private Scanner mScanner = null;

    //parsing the passed file into que , with question and answer
    public Queue<Problems> parseFileAngGetProblems(String fileName) {
        final Queue<Problems> problems = new LinkedList<>();
        try {
            File file = new File(fileName);
            mScanner = new Scanner(file);
            String answers;
            while (mScanner.hasNextLine()) {
                String question = mScanner.nextLine();
                if (!mScanner.hasNextLine()) {
                    answers = mScanner.toString();
                } else {
                    answers = mScanner.nextLine();
                }
                List<String> catalogueIdList = Arrays.asList(answers.split(", [ ]*"));
                problems.add(new Problems(question, catalogueIdList));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return problems;
    }


}
