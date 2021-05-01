package br.com.fbsoftware.sample.spring.boot.springbootangularweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fabio Blanco
 * @since 30/04/2021
 */
@RestController
public class LanguageController {

    private List<Language> languages;

    private void initObjects() {
        languages = Arrays.asList(new Language("Java", false, "high level"),
                                  new Language("Javascript", true, "high level"),
                                  new Language("C#", false, "high level"),
                                  new Language("Groovy", true, "high level"),
                                  new Language("Assembly", false, "lower level"),
                                  new Language("C++", false, "middle level"),
                                  new Language("Clojure", false, "higher level"),
                                  new Language("Python", true, "higher level"),
                                  new Language("Shell script", true, "higher level"));
    }

    @GetMapping("/languages")
    public List<Language> getLanguages() {
        if (languages == null) {
            initObjects();
        }

        return languages;
    }

}
