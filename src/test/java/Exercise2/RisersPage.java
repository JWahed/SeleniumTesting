package Exercise2;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.stream.Collectors;
import java.util.List;


public class RisersPage {

    @FindBy(css = "#ls-perc-RS1-L")
    private WebElement highestRiser;

    @FindBy(css = "#view-constituents > div.table-overflow-wrapper > table > tbody > tr > td:nth-child(5) > span")
    private List<WebElement> risersCell;

    public Float getHighestRiser() {
        String s = this.highestRiser.getText();
        s = s.substring(0, s.length() - 1);

        Float f = Float.parseFloat(s);

        return f;
    }

    public List<Float> getRiserPercents() {
        return this.risersCell.stream()
                .map(WebElement::getText)
                .map(text -> text.substring(0, text.length() - 1))
                .map(Float::parseFloat)
                .collect(Collectors.toList());
    }
}
