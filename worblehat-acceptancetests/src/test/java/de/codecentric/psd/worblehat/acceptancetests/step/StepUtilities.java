package de.codecentric.psd.worblehat.acceptancetests.step;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.function.Consumer;

public class StepUtilities {
  public static void doWithEach(String list, Consumer<String> f) {
    List<String> elements =
        Splitter.on(CharMatcher.anyOf(";, ")).omitEmptyStrings().splitToList(list);
    elements.forEach(f);
  }
}
