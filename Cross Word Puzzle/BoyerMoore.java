/** Deha Ay **/

import java.util.Hashtable;
import java.util.Set;

public class BoyerMoore
{
	/**
	 * The lastOccurance function
	 */
	public static Hashtable<Character, Integer> lastOccurrenceFunction(String S) {
		char[] S_arr = S.toCharArray();
		//char[] used_chars = new char[S_arr.length];

		Hashtable last_occ_table = new Hashtable<>();

		for (int i = S_arr.length - 1; i >= 0 ; i-- ){

			if (!last_occ_table.keySet().contains(S_arr[i])) {
				last_occ_table.put(S_arr[i],i);
			}

			//System.out.println(S_arr.length);
		}




		return last_occ_table;
	}



	/**
	 * Run the Boyer Moore Pattern Matching
	 */
	public static int find(String T, String P)
	{

		if (P.length() > T.length()) {
			return -1;
		}
		int n = T.length();
		int m = P.length();

		Hashtable<Character, Integer> L = lastOccurrenceFunction(P);

		int i = m -1;
		int j = m - 1;

		do {
			if ( T.charAt(i) == P.charAt(j)){
				if(j == 0){
					return i;
				} else {
					i -= 1;
					j -= 1;
				}
			} else {
				int l = L.getOrDefault(T.charAt(i), -1);
				i = i + m - Math.min(j, i + l);
				j = m - 1;
			}
		} while (!(i > n-1));


		return -1;
	}



}
