package Search;

import android.content.Context; //we pass the context to display a toast and relaunch GetGyms if no internet avaliable

import android.view.View; //we pass the result to the views on the UI
import android.widget.ImageView; //we display or remove underlines on the UI
import android.widget.TextView; // we display the results on the UI
import android.widget.Toast; //we display a toast if the search list is null

//Regex is used to search through the list
import java.util.regex.Matcher; //check if the query match part of result
import java.util.regex.Pattern; //used to construct both simple and complex patterns

import downloadLectio.AsyncResponse; //needed to pass delegate on to GetGyms
import downloadLectio.GetGyms; //needed to execute the asynctask in Getgyms



public class Search {  // a class to search both Gyms and Names
    public Context delegate = null; // this delegate is the context of the activity and is used to both run the getgyms method and to produce a toast to the user which says "no internet connection"
    private String[] IDs = new String[4]; // a string array with the IDs of the options in the TextView Array
    private int q = 0; //q is used to show the "query". it is used to determine how many answers have been found and which textview to change the value of
    private boolean dupe = true; //used to check if this value already has been found

    //text- and imageviewarray to set value and visibility of these
    //result is the list it searches through and args is what it searches for
    public String[] Search(ImageView[] imageView, TextView[] textView, String result, String args) { //the entire search function
        String pattern = null;// the pattern is defined here to avoid defining it twice inside the function
        if (result != null) { // checks for internet connection (look for the else statement in the bottom)

            String[] name = result.split("£");//splits the lsit into the base component
            //name==ID

            //the query must start with the input and can have any ending
            if (!args.contains(" ")) { //checks if the search query consist of multiple names
                loop: // a loop to stop the function if it finds the max amount of result before it finishes the list
                // this way we conserve a lot of power and the function returns values faster.
                for (int i = 0; i < name.length; i++) { //runs through the entire list
                    String[] list = name[i].split("=="); //splits in name, ID
                    Pattern noteRegex = Pattern.compile("^" + args.toLowerCase() + ".*?"); //compiles a pattern where the query must start with the input and can have any ending
                    Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase()); // checks for pattern matches in the list
                    if (noteMatcher.find()) { //calls on match
                        IDs[q] = list[1]; //sets the ID of the match into StringArray
                        textView[q].setText(list[0]); //sets the text in the option
                        textView[q].setVisibility(View.VISIBLE); //sets the text to be visible
                        imageView[q].setVisibility(View.VISIBLE); //sets the underline to be visible
                        q++; //adds one to the amount of results gathered
                        if (q == textView.length) { //checks if we are at max number of results
                            break loop; //if yes then stop testing for results
                        }
                    }
                }

                //the query can start with the input or have it inside and can have any ending
                loop://starts new loop
                if (q<4){ //checks if we still need more results
                    for (int i = 0; i < name.length; i++) { //runs through list again with new patter for more results
                        dupe = true; //is used to reset dupe upon completion of a loop if last loop was a duplicate
                        String[] list = name[i].split("=="); //splits in name, ID
                        Pattern noteRegex = Pattern.compile(".*?" + args.toLowerCase() + ".*?"); //compiles a pattern where the query can start with the input or have it inside and can have any ending
                        Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase()); // checks for pattern matches in the list
                        for (int k = 0; k < q; k++) { //a loop to check if the match is identical to any of the other matches found in the previous function
                            if (list[1].equals(IDs[k])) { //checks for equal IDs
                                dupe = false; //if ID was already found sets dupe to false
                            } //dupe resets when next result is tested
                        }
                        if (noteMatcher.find() && dupe) {//checks if the pattern fitted and if it was not a dupe
                            IDs[q] = list[1]; //sets the ID of the match into StringArray
                            textView[q].setText(list[0]); //sets the text in the option
                            textView[q].setVisibility(View.VISIBLE); //sets the text to be visible
                            imageView[q].setVisibility(View.VISIBLE); //sets the underline to be visible
                            q++; //adds one to the amount of results gathered
                            if (q == textView.length) { //checks if we are at max number of results
                                break loop; //if yes then stop testing for results
                            }
                        }
                    }
                }
            }

            // for perfomance reasons multiple words only works if the query starts with the first word
            else { //if search query contains a space
                String[] myLast = args.toLowerCase().split(" "); //splits the string into multiple words
                pattern = "^";//sets the pattern to the code of the regex needing to start with the first word
                for (int i = 0; i < myLast.length; i++) { //loops for as many words inputted
                    pattern = pattern + myLast[i]+ ".*?"; //constructs the pattern for the search
                }

                loop: // loop again initialized to enable a quick return
                for (int i = 0; i < name.length; i++) { //runs through the entire list
                    String[] list = name[i].split("=="); //splits in name, ID
                    Pattern noteRegex = Pattern.compile(pattern); //compiles the patter created earlier
                    Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase()); // matches the list against the pattern
                    if (noteMatcher.find()) { //calls on match
                        IDs[q] = list[1]; //sets the ID of the match into StringArray
                        textView[q].setText(list[0]); //sets the text in the option
                        textView[q].setVisibility(View.VISIBLE); //sets the text to be visible
                        imageView[q].setVisibility(View.VISIBLE); //sets the underline to be visible
                        q++; //adds one to the amount of results gathered
                        if (q == textView.length) { //checks if we are at max number of results
                            break loop; //if yes then stop testing for results
                        }
                    }
                }
            }

            //from this point on we don´t search for matches anymore
            if (q < textView.length) { //checks if we still need results. and sets the remaining results to gone
                //this prevents anyone from clicking and thinking that the old result who would otherwise be there would be options
                while (q < textView.length) { //runs until all results have been set
                    IDs[q] = "null"; //returns an ID of null for the non existing result
                    textView[q].setVisibility(View.GONE); //removes the result from the UI
                    imageView[q].setVisibility(View.GONE); //removes underline from the UI
                    q++; //adds one to the amount of results
                }
            }
            if (args.isEmpty()) { //if the Search was empty the pattern will say all matches.
                //therefor we set them to disappear from the UI
                for (int i = 0; i < textView.length; i++) {//for as many textviews as there is
                    textView[i].setVisibility(View.GONE); //remove the result from the UI
                    imageView[i].setVisibility(View.GONE); //remove underline from the UI
                }
            }
        }

        //if else is called there were no list to search in
        else { //if the list is empty something went wrong in the retrieval of the list
            return null;
        }
        return IDs; //returns the ID´s of the results in order
    }
}