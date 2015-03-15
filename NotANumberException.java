import java.io.*;

public class NotANumberException extends Exception{
	
	public NotANumberException()
	{
	}
	
	public static int translate(String input) throws NotANumberException {
		if(input != null && input.equals("") == false)
		{
			String str = input;
			boolean check = true;
			char[] all = str.toCharArray();
			for(int i = 0; i < all.length;i++) {
				if(Character.isDigit(all[i])) {
				}
				else
				{
					check = false;
				}
			}
			if(check)
			{
				return Integer.parseInt(input);
			}
			else
			{
				throw new NotANumberException();
			}
		}
		else
		{
			throw new NotANumberException();
		}
	}
}
