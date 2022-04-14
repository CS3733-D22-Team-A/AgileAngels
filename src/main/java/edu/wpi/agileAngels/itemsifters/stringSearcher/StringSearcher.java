package edu.wpi.agileAngels.itemsifters.stringSearcher;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.agileAngels.itemsifters.ItemSearch;
import java.awt.*;
import java.util.Locale;
import javafx.scene.control.Label;

public class StringSearcher {
  private final ItemSearch<String, Label> itemSearch;

  public StringSearcher(JFXTextField searchBar, JFXListView<Label> resultsList) {

    ItemSearch.TextMatcher<String> matcher =
        (stringItem, searchText) -> {
          String lowercaseSearchText = searchText.toLowerCase(Locale.ROOT);
          String lowercaseItem = stringItem.toLowerCase(Locale.ROOT);
          return lowercaseItem.contains(lowercaseSearchText);
        };

    ItemSearch.CellCreator<String, Label> cellCreator =
        (string) -> {
          Label label = new Label(string);
          return label;
        };

    this.itemSearch = new ItemSearch<>(searchBar, resultsList, matcher, cellCreator);
  }

  public void addItem(String item) {
    this.itemSearch.addItem(item);
  }

  public void update() {
    this.itemSearch.update();
    ;
  }
}
