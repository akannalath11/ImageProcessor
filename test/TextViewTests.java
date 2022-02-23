import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import view.ImageProcessorView;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the view for the ImageProcessorWorks as expected.
 */
public class TextViewTests {
  private ImageProcessorView textView;
  private Appendable ap;


  @Before
  public void initData() {
    ap = new StringBuilder();
    textView = new TextView(ap);
  }

  @Test
  public void testRenderMessage() throws IOException {
    // Checks the view is empty first
    assertEquals("", ap.toString());
    String testMessage = "Brighten an image by 100";
    textView.renderMessage(testMessage);

    // Checks the appendable is updated when renderMessage is called
    assertEquals(testMessage, ap.toString());

    textView.renderMessage("");
    // Checks the nothing happens when an empty string is rendered
    assertEquals(testMessage, ap.toString());
  }

  // Checks that textView works for second type of Appendable
  @Test
  public void testRenderMessage2() throws IOException {
    Appendable ap2 = new StringBuffer();
    textView = new TextView(ap2);
    // Checks the view is empty first
    assertEquals("", ap2.toString());
    String testMessage = "Brighten an image by 100";
    textView.renderMessage(testMessage);

    // Checks the appendable is updated when renderMessage is called
    assertEquals(testMessage, ap2.toString());

    textView.renderMessage("");
    // Checks the nothing happens when an empty string is rendered
    assertEquals(testMessage, ap2.toString());
  }
}
