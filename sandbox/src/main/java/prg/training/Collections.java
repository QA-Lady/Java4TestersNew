package prg.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QA Lady on 5/2/2017.
 */
public class Collections {

    public static void main(String[] args) {
        //одномерный аррей стрингов нужно сраху задавать размер в отличие от списка
        String[] langs = {"Java", "C#", "Python", "PHP"};

        for (String l : langs) {
            System.out.println("I'd like to learn " + l);
        }
        // one way to initialise list (actual values are added with .add()
        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("C#");
        languages.add("Python");

        // another way to initialize List
        List<String> list = Arrays.asList("Java", "C#", "Python", "PHP");

        for (int i = 0; i < languages.size(); i++) {
            System.out.println("I'd like to learn " + languages.get(i));
        }

        //or
        for (String l : languages) {
            System.out.println("I'd like to learn " + l);
        }

    }


}
