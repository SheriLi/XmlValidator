package xmlvalidator;

import static java.lang.System.*;

public class BasicStringStack implements StringStack {
	private int count;
	private String[] items;


	public BasicStringStack() {
		count = 0;
		items = new String[100];
	}


	@Override
	public void push(String item) {
		if (count == items.length) {
			int newLength = items.length * 2;
			String[] tempAry = new String[newLength];
			arraycopy(items, 0, tempAry, 0, items.length);
			items = tempAry;
		}
		items[count++] = item;
	}


	@Override
	public String pop() {
		String result;
		if (count == 0) {
			return null;
		} else {
			result = items[count - 1];
			items[--count] = null;
		}
		return result;
	}


	@Override
	public String peek(int position) {
		if ((position > count - 1) || (position < 0)) {
			return null;
		} else
			return items[count - position - 1];
	}


	@Override
	public int getCount() {

		return count;
	}

}
