package com.riyagayasen.easysearchwidget.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.Collection;

/**
 * Created by riyagayasen on 22/08/16.
 */
public class AppHelper {


    /***
     * This function returns the 'distance' between two words. The distance is defined as the number of 'edits' that
     * must be done one a word to transform it into another. An 'edit' can be
     * 1. Deletion of a character
     * 2. Addition of a character
     * 3. Replacement of a character
     *
     * I learnt this concept from GeeksForGeeks
     * @param first The first word
     * @param second The second word
     * @param firstLength The length of the substring we want to consider from the first word
     * @param secondLength The length of the substring we want to consider from the second word
     * @return
     */
    public static int distanceBetween(String first, String second, int firstLength, int secondLength) {
        //If the first word to be considered is blank, that is, the length is zero, then the number of steps is simply the length
        //of the second string
        if (firstLength == 0)
            return secondLength;

        //Ditto for the second string
        if (secondLength == 0)
            return firstLength;

        //if the last characters are the same, no edit required, hence we don't consider it
        if (Character.toLowerCase(first.charAt(firstLength - 1)) ==  Character.toLowerCase(second.charAt(secondLength - 1)))
            return distanceBetween(first, second, firstLength - 1, secondLength - 1);

        //In case both the strings are non-blank, we consider that changing the last character of the first into the last character of the second is one edit.
        //We then try to look at the number of steps required if that character were to be removed, and then add 1 for the last character
        return 1 + min(distanceBetween(first, second, firstLength, secondLength - 1),
                distanceBetween(first, second, firstLength - 1, secondLength),
                distanceBetween(first, second, firstLength - 1, secondLength - 1)
        );
    }

    /***
     * Returns the minimum of three numbers
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static int min(int x, int y, int z) {
        return (x<y)?(x<z?x:z):(y<z?y:z);

    }

    /***
     * This function checks if an object is null or blank
     * @param object
     * @return
     */
    public static boolean isNullOrBlank(Object object) {
        if (object instanceof String)
            return isNullOrBlank((String) object);
        if (object == null)
            return true;
        if (object instanceof Collection) {
            if (((Collection) object).isEmpty())
                return true;

        }

        return false;
    }

    /***
     * A function to check if a string is null or blank
     *
     * @param string
     * @return
     */
    public static boolean isNullOrBlank(String string) {
        if (string == null)
            return true;
        if (string.equals(""))
            return true;
        if (string.length() == 0)
            return true;
        return false;
    }

    /***
     * Retrieves a drawable
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawableFromRes(Context context, int id) {
        return context.getResources().getDrawable(id);

    }


}
