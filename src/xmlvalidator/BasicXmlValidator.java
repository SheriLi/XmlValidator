package xmlvalidator;

import java.util.*;
import java.util.regex.*;

public class BasicXmlValidator implements XmlValidator {
	@Override
	public String[] validate(String xmlDocument) {
		BasicStringStack tagStack = new BasicStringStack();
		BasicStringStack lineNum = new BasicStringStack();
		String[] returnArray;
		String openTname = null;
		String closeTname = null;
		Scanner sx = new Scanner(xmlDocument);
		int n = 0;
		while (sx.hasNextLine()) {
			String line = sx.nextLine();
			n++;
			Pattern p = Pattern.compile("<[^<>]+>");
			Matcher m = p.matcher(line);
			while (m.find()) {
				String f = m.group();
				if (f.charAt(1) != '/' && f.charAt(1) != '?' && f.charAt(1) != '!') {
					Pattern p1 = Pattern.compile("\\s");
					Matcher m1 = p1.matcher(f);
					if (m1.find()) {
						String[] tagName = f.split("\\s");
						openTname = tagName[0].substring(1);
					} else {
						openTname = f.substring(1, f.length() - 1);
					}
					tagStack.push(openTname);
					lineNum.push(Integer.toString(n));
				} else if (f.charAt(1) == '/') {
					closeTname = f.substring(2, f.length() - 1);
					if (tagStack.getCount() == 0) {
						returnArray = new String[5];
						returnArray[0] = "Orphan closing tag";
						returnArray[1] = closeTname;
						returnArray[2] = Integer.toString(n);
						return returnArray;
					} else

					{
						if (closeTname.equals(tagStack.peek(0))) {
							tagStack.pop();
							lineNum.pop();
						} else {
							returnArray = new String[5];
							returnArray[0] = "Tag mismatch";
							returnArray[1] = openTname;
							returnArray[2] = lineNum.peek(0);
							returnArray[3] = closeTname;
							returnArray[4] = Integer.toString(n);
							return returnArray;
						}
					}
				}
			}
		}
		sx.close();
		if (tagStack.getCount() != 0) {
			returnArray = new String[5];
			returnArray[0] = "Unclosed tag at end";
			returnArray[1] = tagStack.peek(0);
			returnArray[2] = lineNum.peek(0);
			return returnArray;
		}
		return null;
	}

}