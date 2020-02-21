package by.iba.aquastyle.tokenutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

public class TokenUtils {

  public static final String LBRACE = "{";
  public static final String RBRACE = "}";
  public static final String ARROW = "->";
  public static final String MULTIPLY = "*";
  public static final String PLUS = "+";
  public static final String ASSIGN = "=";
  public static final String DOT = ".";
  public static final String SEMI_COLUMN = ";";
  public static final String NEW_LINE = "\n";

  public static final Collection<String> JAVA_DOTS = new ArrayList<String>() {{
    add(SEMI_COLUMN);
    add(LBRACE);
    add(ARROW);
  }};

  public static final TokenSplitter CAMEL_CASE_SPLITTER = (String s) ->
     s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

  public static final Function<String, TokenSplitter> SPLITTER = (String s) -> (String delim) ->
      s.replaceFirst(delim + "+", "").split(delim + "+");


  public static final TokenNormalizer LOWERCASE_NORMALIZER = String::toLowerCase;

  public static final TokenNormalizer WORD_NORMALIZER = (String s) -> {
    if (JAVA_DOTS.contains(s.trim())) {
      return NEW_LINE;
    } else {
      return s;
    }
  };
}

