# Easy_Search_Widget_Android
A easy to use search widget for Android. Just add module easysearchwidget in your project. It takes an array list of strings which serves as a list of possible search results. At the top, it allows an input. Based on the input, the list of strings (that was passed to it) shows up, ordered according to how close each of them is to the input query. When the user taps on one of the results, it is returned to the activity which called this widget. The main motivation behind writing this widget, especially when android has its own stock android search widget, was that using the already-present search widget is cumbersome. It involves alot of steps, changes in the manifest, etc. A simple widget, which simply took an array list as the list of possible results and returned the selection felt to be the need of the hour. 

This uses a 'word-distance' concept to order the results. A distance between two words is defined as the number of 'edits' that a word must undergo to be transformed into another word. An edit is defined as:

1. Deletion of a character
2. Addition of a character
3. Changing a character into another 

This calculates the shortest distance between the search query and each possible result and then orders the result according to this value. 

To use the easy search widget: 

1. Create an intent 

		final Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
SearchActivity is the widget screen
2. Pass the array list to the intent, as a stringArrayListExtra

		searchIntent.putStringArrayListExtra(SearchActivity.ITEM_LIST_KEY, stringList);
The stringList above is an ArrayList of strings which serve as the list of possible search results. 
3. Start the activity, listening for the result. 

		startActivityForResult(searchIntent, searchRequestCode);
The searchRequestCode is any integer code that you can use.
4. Override the onActivityResult of your own activity, to retrieve the result of the search widget. 

		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
				if (requestCode == searchRequestCode) {
					switch (resultCode) {
						case RESULT_OK:
							String chosenString = data.getStringExtra(SearchActivity.CHOSEN_VALUE_KEY);
							testTextView.setText(chosenString);
							break;
						default:
							Toast.makeText(this, "No string chosen", Toast.LENGTH_SHORT).show();
					}
				}
		}
To retrive the result, first check forthe requestCode. This should be same as the searchRequestCode that you used to start the search activity. Now, to get the string that was chosen from the list of search results inside the SearchActivity, use SearchActivity.CHOSEN_VALUE_KEY to retrieve a string value. 

A screenshot of the component with some country names as input strings. 

![Alt text](/Screenshot.jpg?raw=true "Optional Title")
