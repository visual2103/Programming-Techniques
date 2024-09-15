package DataModels;

import BusinessLogic.Monomial;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractFromStrings {

    private String stringToProcess;
    public ExtractFromStrings(String stringToProcess){
        this.stringToProcess = stringToProcess;
    }

    public List<String> extractMonoms(){ //1.cauta perechi de monoame care sunt in text field
        List<String> q = new ArrayList<>(); // salvez aici coef +pow
        // extrag "substring-uri" dintr un string
        Pattern patt = Pattern.compile("[-]?[0-9]*(\\.[0-9]+)?(x(\\^[-]?[0-9]+)?)?");
        Matcher match = patt.matcher(stringToProcess);
        while (match.find()){
            if(match.group().isEmpty())
                continue;
            q.add(match.group());
        }
        return q;
    }
}
