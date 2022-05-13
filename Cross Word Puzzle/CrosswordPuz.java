
/** DEHA AY **/


import jdk.swing.interop.SwingInterOpUtils;
import java.util.ArrayList;
import java.util.Hashtable;

public class CrosswordPuz {

    //gets the reverse of any hashtable inputted
    public static Hashtable<Integer, ArrayList<String>> get_reverse (Hashtable<Integer, ArrayList<String>> hashtable) {
        Hashtable<Integer, ArrayList<String>> reversed = new Hashtable<>();

        for( int i : hashtable.keySet()){
            ArrayList<String> values = hashtable.get(i);
            ArrayList<String> reversed_values = new ArrayList<>();

            for (String j : values){
                reversed_values.add(new StringBuffer(j).reverse().toString());
            }
            reversed.put(i,reversed_values);

        }


        return reversed;

    }

    //Gets all the variations of starting from various indexes for all cases
    public static ArrayList<String> get_all_combinations_vh(String line) {

        ArrayList<String> word_vector = new ArrayList<>();

        //it used to be <= but I believe the first if statment is just useless
        for(int i = 0; i < line.length(); i++){
            String new_word = "";
            if ( i == (line.length()) ) {

                String a = line.substring(line.length() - 1, line.length());
                String b = line.substring(0, line.length() - 1);
                new_word = a + b;

            } else {
                String a = line.substring(i, line.length());
                String b = line.substring(0, i);
                new_word = a + b;
            }
            word_vector.add(new_word);
        }

        return word_vector;

    }


    public static Hashtable<Integer, ArrayList<String>> get_horizontal_options(final char[][] crossword) {

        Hashtable<Integer, ArrayList<String>> horizontal_opt = new Hashtable<>();
        int max_index = crossword.length - 1; //starting from 0 the max index of row and colum (square matrix)

        for (int row = 0; row <= max_index; row++ ){
            ArrayList<String> word_vector = new ArrayList<>();
            String word = String.valueOf(crossword[row]);
            horizontal_opt.put(row,get_all_combinations_vh(word));
        }

        return horizontal_opt;
    }


    public static Hashtable<Integer, ArrayList<String>> get_vertical_options(final char[][] crossword) {

        Hashtable<Integer, ArrayList<String>> vertical_opt = new Hashtable<>();
        int max_index = crossword.length - 1; //starting from 0 the max index of row and colum (square matrix)


        for (int col = 0; col <= max_index; col++ ){
            ArrayList<String> word_list = new ArrayList<>();
            String String_builder = "";

            for (int row = 0; row <= max_index; row++ ){

                String_builder += crossword[row][col];
            }
            vertical_opt.put(col,get_all_combinations_vh(String_builder));

            //horizontal_opt.put(row,get_all_combinations_vh(word));
        }

        return vertical_opt;

    }

    public static Hashtable<Integer, ArrayList<String>> get_diagonal_left_t_r(final char[][] crossword) {

        Hashtable<Integer, ArrayList<String>> diag_opt = new Hashtable<>();
        int max_index = crossword.length - 1; //starting from 0 the max index of row and colum (square matrix)

        int row = 0;
        int col = 0;
        String string_builder = "";
        for (int columns = 0; columns <= max_index; columns++) {
            row = 0;
            col = columns;
            string_builder = "";

            //System.out.println(col);

            while (row <= max_index) {
                //System.out.println(col);
                string_builder += crossword[row][col];

                row += 1;
                col += 1;

                if (col > max_index) {
                    col = 0;
                }

            }
            diag_opt.put(columns,get_all_combinations_vh(string_builder));
        }



        return diag_opt;

        }


    public static Hashtable<Integer, ArrayList<String>> get_diagonal_right_t_l(final char[][] crossword) {

        Hashtable<Integer, ArrayList<String>> diagd_opt = new Hashtable<>();
        int max_index = crossword.length - 1; //starting from 0 the max index of row and colum (square matrix)

        int row = 0;
        int col = 0;
        String string_builder = "";
        for (int columns = 0; columns <= max_index; columns++) {
            col = columns;
            row = 0;
            string_builder = "";

            //System.out.println(col);

            while (row <= max_index) {
                //System.out.println(col);
                string_builder += crossword[row][col];

                row += 1;
                col -= 1;

                if (col < 0) {
                    col = max_index;
                }

            }
            diagd_opt.put(columns,get_all_combinations_vh(string_builder));
        }



        return diagd_opt;

    }


    public static int find_which_index(Hashtable<Integer, ArrayList<String>> hash_tab, int key, String target) {

        ArrayList<String> values = hash_tab.get(key);
        int count = 0;
        for(String i : values) {
            if(i.substring(0,target.length()).equals(target)){
                return count;
            }
            count += 1;

        }
        return -1;

    }

    public static int find_which_key(Hashtable<Integer, ArrayList<String>> hash_tab, String target) {
        int found_key = -1;
        BoyerMoore bm = new BoyerMoore();
        for( Integer i : hash_tab.keySet()) {

            for(String j : hash_tab.get(i)) {

                if( bm.find(j,target) != -1){
                    found_key = i;

                    return found_key;
                }
            }
        }
        return found_key;

    }

    //checks for very long targets
    public static boolean check_target_comp(String target,char[][] crossword) {

        if ( target.length() <= crossword.length ){
            return true;
        }

        char[] base = target.substring(0,crossword.length).toCharArray();
        char[] target_chars =target.toCharArray();

        int loop_count = 0;
        int base_count = 0;
        int same_chars = 0;

        while (loop_count < target.length()) {
            //System.out.println(target_chars[loop_count] + "  base:" + base_count + "  loop_count:" + loop_count);

            if( base[base_count] == target_chars[loop_count]) {
                same_chars+= 1;
                //System.out.println("same");
            }

            loop_count+=1;
            base_count += 1;

            if(base_count >= base.length) {
                base_count = 0;
            }

        }

        //System.out.println(same_chars);

        if(same_chars == target.length()) {
            return true;
        }

        return false;



    }



    /**
     * Finds a word in a matrix.
     */
    public static int[] find(final char[][] crossword, final String target) {
        int[] coordinates = new int[3];
        BoyerMoore bm = new BoyerMoore();
        int max_index = crossword.length - 1;

        //System.out.println(check_target_comp("dgbbbdg",crossword));


        if ( check_target_comp(target,crossword) == false){
            coordinates[0] = -1;
            coordinates[1] = -1;
            coordinates[2] = -1;
            return coordinates;

        }

        //System.out.println(target + " " + crossword.length);
        String target_u = "";

        if(target.length() > crossword.length){
            target_u = target.substring(0,crossword.length);
        } else {
            target_u = target;
        }


        //System.out.println(target_u);

        //System.out.println("Horizontal");
        Hashtable<Integer, ArrayList<String>> horizontal_dict = get_horizontal_options(crossword);
        //System.out.println(horizontal_dict.toString());

        //System.out.println("Horizontal Reverse");
        Hashtable<Integer, ArrayList<String>> r_horizontal_dict = get_reverse(horizontal_dict);
        //System.out.println(r_horizontal_dict);

        //System.out.println("VERTICAL");
        Hashtable<Integer, ArrayList<String>> vertical_dict = get_vertical_options(crossword);
        //System.out.println(vertical_dict);

        //System.out.println("VERTICAL Reverse");
        Hashtable<Integer, ArrayList<String>> r_vertical_dict = get_reverse(vertical_dict);
        ///System.out.println(r_vertical_dict);

        //System.out.println("Diagonal L->R");
        Hashtable<Integer, ArrayList<String>> diagonal_left_t_r_dict = get_diagonal_left_t_r(crossword);
        //System.out.println(diagonal_left_t_r_dict);

        //System.out.println("Diagonal L-> R Reverse");
        Hashtable<Integer, ArrayList<String>> reverse_diagonal_left_t_r_dict = get_reverse(diagonal_left_t_r_dict);
        //System.out.println(reverse_diagonal_left_t_r_dict);

        //System.out.println("Diagonal R->L");
        Hashtable<Integer, ArrayList<String>> diagonal_right_t_l_dict = get_diagonal_right_t_l(crossword);
        //System.out.println(diagonal_right_t_l_dict);

        //System.out.println("Diagonal R->L Reverse");
        Hashtable<Integer, ArrayList<String>> reverse_diagonal_right_t_l_dict = get_reverse(diagonal_right_t_l_dict);
        //System.out.println(reverse_diagonal_right_t_l_dict);

        // find( fulltext , small_text )

        int direction = -1;
        int row = -1;
        int col = -1;

        int found_key = -1;
        int found_index = -1;


        /** Diagonal Left to Right Reverse **/

        found_key = -1;
        found_key = find_which_key(reverse_diagonal_left_t_r_dict,target_u);
        if ( found_key != -1){
            direction = 5;
            found_index = find_which_index(reverse_diagonal_left_t_r_dict,found_key,target_u);
            //System.out.println("$$$Diagonal Left to Right Reverse : " + " key: " +found_key + "  index:" + found_index );

            if (found_index == 0){
                row = max_index;
            } else {
                row = found_index - 1;
            }

            col = (found_key - 1) + found_index;

            if (col == -1) {
                col = 0;
            } else if (col > max_index ){
                col = col - (max_index + 1);

            }


        }

        /** Diagonal Left to Right **/

        found_key = -1;
        found_key = find_which_key(diagonal_left_t_r_dict,target_u);
        if ( found_key != -1){
            direction = 1;
            //System.out.println("Digonal L->R the found key is: " + found_key );
            //System.out.println("---> " + find_which_index(diagonal_left_t_r_dict,found_key,target_u));
            found_index = find_which_index(diagonal_left_t_r_dict,found_key,target_u);

            col = found_key + found_index;

            if(col > max_index) {
                col = col - crossword.length; //if it exceeds 6 , and becomes 6+1, length zeros the value
            }

            row = found_index;

        }

        /** Diagonal Right to Left Reverse **/

        found_key = -1;
        found_key = find_which_key(reverse_diagonal_right_t_l_dict,target_u);
        if ( found_key != -1){
            direction = 3;
            found_index = find_which_index(reverse_diagonal_right_t_l_dict,found_key,target_u);
            //System.out.println("$rl$$Diagonal Right to Left Reverse : " + " key: " +found_key + "  index:" + found_index );

            if (found_index == 0){
                row = max_index;
            } else {
                row = found_index - 1;
            }

            col = (found_key + 1) - found_index;

            if (col == -1) {
                col = 0;
            } else if (col > max_index ){
                col = col - (max_index + 1);

            }

        }

        /** Diagonal Right to Left **/

        found_key = -1;
        found_key = find_which_key(diagonal_right_t_l_dict,target_u);
        if ( found_key != -1){
            direction = 7;

            found_index = find_which_index(diagonal_right_t_l_dict,found_key,target_u);

            //System.out.println("#@****Right to Left : " + " key: " +found_key + "  index:" + found_index );
            row = found_index;

            col = found_key - found_index;

            if(col < 0) {
                col = col + (max_index + 1);
            }


        }



        /** Vertical **/

        found_key = -1;
        found_key = find_which_key(vertical_dict,target_u);
        if ( found_key != -1){
            direction = 0;

            found_index = find_which_index(vertical_dict,found_key,target_u);
            //System.out.println("*****Vertical FOUND : " + " key: " +found_key + "  index:" + found_index );

            col = found_key;
            row = found_index;

        }

        /** Reverse Vertical **/

        found_key = -1;
        found_key = find_which_key(r_vertical_dict,target_u);
        if ( found_key != -1){
            direction = 4;

            found_index = find_which_index(r_vertical_dict,found_key,target_u);
            //System.out.println("••••Reverse Vertical FOUND : " + " key: " +found_key + "  index:" + found_index );

            col = found_key;

            if (found_index == 0) {
                row = max_index;
            } else {
                row = found_index - 1;
            }



        }

        /** Horizontal **/

        found_key = -1;
        found_key = find_which_key(horizontal_dict,target_u);
        if ( found_key != -1){
            direction = 2;

            found_index = find_which_index(horizontal_dict,found_key,target_u);
            //System.out.println("----Horizantal FOUND : " + " key: " +found_key + "  index:" + found_index );

            col = found_index;
            row = found_key;

        }

        /** Reverse Horizontal **/

        found_key = -1;
        found_key = find_which_key(r_horizontal_dict,target_u);
        if ( found_key != -1){
            direction = 6;

            found_index = find_which_index(r_horizontal_dict,found_key,target_u);
            //System.out.println("••••Reverse Horizontal FOUND : " + " key: " +found_key + "  index:" + found_index );

            row = found_key;

            if (found_index == 0) {
                col = max_index;
            } else {
                col = found_index - 1;
            }

        }


        coordinates[0] = row;
        coordinates[1] = col;
        coordinates[2] = direction;


        return coordinates;
    }
}
