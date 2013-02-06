package org.jsystemtest.integration;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.infra.report.Reporter.Color;
import org.jsystemtest.infra.report.Reporter.Style;
import org.testng.annotations.Test;

public class ReporterTests {


	@Test
	public void testManyLines() {
		Reporter.log("This is the first line");
		Reporter.log("This is the second line");
		Reporter.log("This is the third line");
	}

	@Test
	public void testReportOutput() throws IOException {
		File myFile = new File("myFile.txt");
		myFile.createNewFile();
		Reporter.logFile("Link to my file", myFile);
	}

	@Test
	public void testReportColors() {
		Reporter.log("In red", Style.REGULAR, Color.RED);
		Reporter.log("In blue", Style.REGULAR, Color.BLUE);
		Reporter.log("In yellow", Style.REGULAR, Color.YELLOW);
		Reporter.log("In green", Style.REGULAR, Color.GREEN);

		Reporter.log("In red", Style.BOLD, Color.RED);
		Reporter.log("In blue", Style.BOLD, Color.BLUE);
		Reporter.log("In yellow", Style.BOLD, Color.YELLOW);
		Reporter.log("In green", Style.BOLD, Color.GREEN);

		Reporter.log("In red", Style.ITALIC, Color.RED);
		Reporter.log("In blue", Style.ITALIC, Color.BLUE);
		Reporter.log("In yellow", Style.ITALIC, Color.YELLOW);
		Reporter.log("In green", Style.ITALIC, Color.GREEN);

	}

	@Test
	public void testToggle() {
		String body = generateLines(50, 10);
		Reporter.log("This is the title", body.toString());
		Reporter.log("Null body", "", Color.BLUE);
		Reporter.log(null, "", Color.BLUE);
	}

	private String generateLines(int numOfLines, int lengthOfLine) {
		StringBuilder body = new StringBuilder();
		;
		for (int i = 0; i < numOfLines; i++) {
			body.append("Line ").append(i).append(" ");
			Random r = new Random();
			for (int j = 0; j < lengthOfLine; j++) {
				body.append((char) (r.nextInt(26) + 'a'));
			}
			body.append("\n");
		}
		return body.toString();
	}

	@Test
	public void testToggleWithColor() {
		final String body = "Body with one <b>bold</b> element";
		Reporter.log("The title should appear in GREEN", body, Color.GREEN);
		Reporter.log("The title should appear in RED", body, Color.RED);
		Reporter.log("The title should appear in YELLOW", body, Color.YELLOW);
	}

	@Test
	public void testStyle() {
		Reporter.log("In bold", Style.BOLD);
		Reporter.log("In italic", Style.ITALIC);
	}

}
