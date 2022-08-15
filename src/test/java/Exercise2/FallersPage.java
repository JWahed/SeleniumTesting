package Exercise2;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class FallersPage {

    @FindBy(css = "#ls-perc-HLN-L")
    private WebElement lowestFaller;

    @FindBy(css = "#view-constituents > div.table-overflow-wrapper > table > tbody > tr > td:nth-child(5) > span")
    private List<WebElement> fallerCell;

    public Float getLowestFaller() {
        String s = this.lowestFaller.getText();
        s = s.substring(0, s.length() - 1);

        Float f = Float.parseFloat(s);

        return f;
    }

    public List<Float> getFallerPercents() {
        return this.fallerCell.stream()
                .map(WebElement::getText)
                .map(text -> text.substring(0, text.length() - 1))
                .map(Float::parseFloat)
                .collect(Collectors.toList());
    }

}
