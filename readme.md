# Readme

### NOTE:

It will take somewhere between 30-50 seconds to initially load up the application as it needs time to make multiple requests to retrieve every character name and store them in memory for a much faster auto-complete feature.

You can shorten the startup time by going to src/main/java/major_project/model/marvelparser/OnlineMarvelParser.java and reactivating the break at line 46. This will start the app after retrieving names from just the first array of results. Downside is you'll only get name suggestions from the first array of 100 names.

---

### HOW TO USE

- CHARACTER SEARCH BAR - Press ENTER to get suggestions based on current input.

- SEARCH BUTTON -  Click this button to search for information on the entered character. Any character that does not exist will show an error.

- COLLECTION LIST - DOUBLE CLICK to go browse that collection.

- BREAD CRUMB BAR - Click the relevant bread crumbs to explore recent history. The bar can be panned left and right using the mouse once filled up.

- SEND REPORT BUTTON - Click this to open up a form to send an email of the currently viewed information in a rearranged format. All fields must be filled before clicking submit. There will be a feedback before closing on whether the report was successfully or not.
