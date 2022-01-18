package grid_playground;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
	public static int getRandomBetween(int min, int max, int... exclude)
	{
		int random = ThreadLocalRandom.current().nextInt(min, max + 1);
		
		for(int e : exclude)
		{
			if(random != e)
				break;
			
			random++;
			random%=(max+1);
			if(random == 0)
				random += min;
		}
		
		return random;
	}
}
