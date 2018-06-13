# Bottom Navigation Activity Tutorial for Android

This tutorial will be going over how to create and use the Bottom Navigation Activity within your Android app.

### Key points and Benefits

Before we get started I just wanted to highlight some key points and guidelines when using the Bottom Navigation. First off the Bottom Navigation Activity should only be used when you have 3-5 key top level views, if you have more than that it is recommended to use other methods such as the Navigation Drawer. One of the key benefits of the Bottom Navigation is the fact that it is always on screen regardless of which of your "screens" that the user visits. The reason I say "screens" brings us to the second key point in that the Bottom Navigation Activity is largely used alongside fragments. This allows us the easily change the content within our main activity by just displaying another fragment.



[More info on Fragments](https://developer.android.com/guide/components/fragments)

[Design Principles for Bottom Navigation](https://material.io/design/components/bottom-navigation.html#theming)

## Getting Started

1. Create a new android project with your desired API (I dont recommend lower than 21)
2. Select the Bottom Navigation Activity
    - This provides us with all of the starter code and set up needed
3. Name your activity, I recommend keeping it as MainActivity if you are only going to have 1 activity
4. Next let us take a look at what Android Studio has done for us

#### Added Dependencies
The support design library version 25 or higher is required for the Bottom Navigation
```
dependencies {
    ...
    implementation 'com.android.support:design:27.1.1'
    // Other Dependencies
}
```
#### Added BottomNavigationView to activity_main.xml
```
    <android.support.design.widget.BottomNavigationView
        android:id="@+id/navigation"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginEnd="0dp"
        android:layout_marginStart="0dp"
        android:background="?android:attr/windowBackground"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:menu="@menu/navigation"
        app:itemIconTint="@color/bottom_nav_icon_color_selector"
        app:itemTextColor="@color/bottom_nav_icon_color_selector"/>
```
The code that Android Studio provides may not contain the last two lines but in short they are used to change the colour of the text and icons inside the BottomNavigationView. I will go further in depth on those two lines later in this tutorial.

#### Added /res/menu and navigation.xml
This is where you define the items that will go inside your BottomNavigationView
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/navigation_home"
        android:icon="@drawable/ic_home_black_24dp"
        android:title="@string/title_home" />

    <item
        android:id="@+id/navigation_dashboard"
        android:icon="@drawable/ic_dashboard_black_24dp"
        android:title="@string/title_dashboard" />

    <item
        android:id="@+id/navigation_notifications"
        android:icon="@drawable/ic_notifications_black_24dp"
        android:title="@string/title_notifications" />

</menu>
```
#### Java Code Provided
Inside your MainActivity.java you will find two snippets of code added, one to initialize the BottomActivityView and a second to listen and react to items selected through the Bottom Navigation.
```
// UI initialization
 BottomNavigationView navigation = findViewById(R.id.navigation);
 navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
```
```
// item selected listener
private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
```
We will be coming back to the listener to alter it once we have added a fragment to our project.

## Customizing our project to better fulfill our needs
1. Create a new fragment, this can be done by going File>New>Fragment which will also create your fragment xml
2. For simplicity I got rid of as much code as possible and created one fragment where the only things that would be changing are  a TextView and the background colour

```
public class BottomNavigationFragment extends Fragment {

        public static final String ARG_TITLE = "arg_title";
        public static final String ARG_COLOR = "arg_color";

    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
            TextView textView = root.findViewById(R.id.fragment_text);
            FrameLayout frameLayout = root.findViewById(R.id.fragFrameLayout);

            String title = null;
            int color = -1;

            if (getArguments() != null) {
                title = getArguments().getString(ARG_TITLE, "");
                color = getArguments().getInt(ARG_COLOR, -1);
            }

            frameLayout.setBackgroundColor(color);
            textView.setText(title);
            return root;
        }
    }
```
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/fragFrameLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".BottomNavigationFragment">

    <TextView
        android:id="@+id/fragment_text"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:textSize="30sp"
        android:text="@string/hello_blank_fragment" />

</FrameLayout>
```

3. Now inside your activity_main.xml you will need to create a space for your fragment to be loaded. You can replace the default TextView with the following

```
    <FrameLayout
        android:id="@+id/frame"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="?attr/actionBarSize"/>
```

4. Inside your MainActivity.java you will now need to handle a few things
    -Creating an ArrayList to hold the fragments for your BottomNavigationView
    -Creating a method that will initialize each fragment and have it have it pass through the correct TextView text and background color
    -Populate our list with the three fragments that match our BottomNavigationView
    
```
// Create an ArrayList of our fragment type
private ArrayList<BottomNavigationFragment> fragmentList = new ArrayList<>();
```
```
// Method to initialize fragments and pass in desired title and color
    private BottomNavigationFragment createFragment(String title, int color) {

        BottomNavigationFragment fragment = new BottomNavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BottomNavigationFragment.ARG_TITLE, title);
        bundle.putInt(BottomNavigationFragment.ARG_COLOR, color);
        fragment.setArguments(bundle);
        return fragment;
    }
```
```
// Creating fragments and adding them to our ArrayList
    private void populateFragmentList() {

        BottomNavigationFragment homeFragment = createFragment("Home", Color.RED);
        BottomNavigationFragment dashboardFragment = createFragment("Dashboard", Color.GREEN);
        BottomNavigationFragment notificationsFragment = createFragment("Notifications", Color.BLUE);

        fragmentList.add(homeFragment);
        fragmentList.add(dashboardFragment);
        fragmentList.add(notificationsFragment);
    }
```

5. Next we will need to create a method that will inflate the desired fragment based on the BottomNavigationView

```
    private void switchFragment(int pos) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragmentList.get(pos))
                .commit();
    }
```

6. Next we will want to update our onCreate to populate our list and also to initialize a fragment by default. This can be done by adding the following two lines of code

```
populateFragmentList();
switchFragment(0);
```

7. We now need to update our item selected listener to switch between the fragments in our ArrayList. This is done by replacing
the default code in the switch statement to our switchFragment method

 ```
 private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(2);
                    return true;
            }
            return false;
        }
    };
 ```
    
8. To revisit Getting Started step 4 on how to change the colour of the BottomNavigationView icon and text
    1. Create a new Drawable Resource File inside /res/drawable/
    2. Name it something along the lines of selector_bottombar_color.xml or bottom_nav_color_selector.xml
    3. Inside the new xml you will need to define a `state_checked="true"` and a default state
    4. Something to note is that the selected icon should display as your app's primary color, for the sake of this tutorial I used a more standout color
    
    
```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true" android:color="@color/colorAccent" />
    <item android:color="@color/InactiveBottomNavIconColor" />
</selector>
```

9. Inside your activity_main.xml you will need to add the following two lines to your BottomNavigationView if you have not already done so
```
app:itemIconTint="@drawable/bottom_nav_icon_color_selector"
app:itemTextColor="@drawable/bottom_nav_icon_color_selector"/>
```

10. If you would like your text to be a different color or to not show you can create another selector xml and update the itemTextColor field
    
    
